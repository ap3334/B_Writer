package com.example.ebookmarket.app.member.service;

import com.example.ebookmarket.app.email.service.EmailSendService;
import com.example.ebookmarket.app.member.AuthLevel;
import com.example.ebookmarket.app.member.dto.JoinDto;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.member.exception.AlreadyExistNicknameException;
import com.example.ebookmarket.app.member.exception.AlreadyJoinException;
import com.example.ebookmarket.app.member.repository.MemberRepository;
import com.example.ebookmarket.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final EmailSendService emailSendService;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Member join(JoinDto joinDto) {

        if (memberRepository.findByUsername(joinDto.getUsername()).isPresent()) {
            throw new AlreadyJoinException();
        }

        Member member = Member.builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .email(joinDto.getEmail())
                .authLevel(AuthLevel.USER)
                .build();

        memberRepository.save(member);

        emailSendService.sendForJoin(member.getEmail());

        return member;
    }

    @Transactional
    public Member beAuthor(Member member, String nickname) {

        if (checkDuplicateNickname(nickname)) {
            throw new AlreadyExistNicknameException();
        }

        Optional<Member> opMember = memberRepository.findById(member.getId());
        opMember.get().setNickname(nickname);

        forceAuthentication(opMember.get());

        return opMember.get();
    }

    private void forceAuthentication(Member member) {
        MemberContext memberContext = new MemberContext(member, member.getAuthorities());

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberContext,
                        member.getPassword(),
                        memberContext.getAuthorities()
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    public boolean checkDuplicateNickname(String nickname) {

        Optional<Member> opMember = memberRepository.findMemberByNickname(nickname);

        if (opMember.isPresent()) {
            log.debug(opMember.get().getNickname());
            return true;
        } else {
            return false;
        }

    }
}
