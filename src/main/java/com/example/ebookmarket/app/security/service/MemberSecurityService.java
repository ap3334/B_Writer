package com.example.ebookmarket.app.security.service;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.member.repository.MemberRepository;
import com.example.ebookmarket.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();

        return new MemberContext(member, member.getAuthorities());
    }
}
