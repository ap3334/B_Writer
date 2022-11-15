package com.example.ebookmarket.app.member.controller;

import com.example.ebookmarket.app.member.dto.JoinDto;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.member.service.MemberService;
import com.example.ebookmarket.app.security.dto.MemberContext;
import com.example.ebookmarket.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * @return 로그인 페이지
     */

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginForm() {

        return "member/login";
    }

    /**
     * @return 회원가입 페이지
     */

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String joinForm() {

        return "member/join";
    }

    /**
     * 회원가입 로직
     * @param joinDto
     * @return 로그인 페이지로 redirect + 성공 메세지
     */

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto) {

        memberService.join(joinDto);

        String msg = Util.url.encode("회원가입이 완료되었습니다.");

        return "redirect:/member/login?msg=%s".formatted(msg);

    }

    /**
     * 아이디 중복 체크 로직
     * @param username
     * @return HTTP 상태 메세지
     */

    @PreAuthorize("isAnonymous()")
    @PostMapping("/checkUsername")
    public ResponseEntity checkUsername(@RequestBody String username) {

        boolean isUsable = memberService.checkUsername(username);

        if (isUsable) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.CONFLICT);

    }

    /**
     * @param member
     * @param model
     * @return 사용자 정보 페이지
     */

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal MemberContext member, Model model) {


        return "member/profile";
    }

    /**
     * @return 작가 등록 페이지
     */

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/beAuthor")
    public String beAuthorForm() {

        return "member/beAuthor";
    }

    /**
     * 작가 등록 로직
     * @param memberContext
     * @param nickname
     * @return 사용자 정보 페이지 redirect
     */

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/beAuthor")
    public String beAuthor(@AuthenticationPrincipal MemberContext memberContext,
                           String nickname) {

        Member member = memberContext.getMember();

        log.debug(nickname);

        memberService.beAuthor(member, nickname);

        return "member/profile";
    }

    /**
     * 작가명 중복 확인 로직
     * @param nickname
     * @return Http 상태 메시지
     */

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkNickname")
    public ResponseEntity checkNickname(@RequestBody String nickname) {

        boolean isDuplicated = memberService.checkDuplicateNickname(nickname);

        log.debug(nickname);

        if (isDuplicated) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @return 비밀번호 수정 페이지
     */

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String modifyPasswordForm() {

        return "member/modifyPassword";
    }

    /**
     * 기존 비밀번호 확인 로직
     * @param memberContext
     * @param password
     * @return Http 상태 메시지
     */

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkPassword")
    public ResponseEntity checkPassword(@AuthenticationPrincipal MemberContext memberContext, @RequestBody String password) {

        boolean isAuthenticated = memberService.checkPassword(memberContext.getUsername(), password);

        if (isAuthenticated) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    /**
     * 비밀번호 수정 로직
     * @param memberContext
     * @param password
     * @return 사용자 정보 페이지 redirect
     */

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal MemberContext memberContext, String password) {

        memberService.modifyPassword(memberContext.getUsername(), password);

        return "redirect:/member/profile";

    }





}
