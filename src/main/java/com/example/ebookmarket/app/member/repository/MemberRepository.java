package com.example.ebookmarket.app.member.repository;

import com.example.ebookmarket.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByNickname(String nickname);

    Optional<Member> findByUsername(String username);

}
