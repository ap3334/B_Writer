package com.example.ebookmarket.app.member.service;

import com.example.ebookmarket.app.member.AuthLevel;
import com.example.ebookmarket.app.member.dto.JoinDto;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.member.exception.AlreadyJoinException;
import com.example.ebookmarket.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

        return member;
    }
}
