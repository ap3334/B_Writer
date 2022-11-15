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

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginForm() {

        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String joinForm(@RequestParam(value = "msg", required = false) String msg, Model model) {

        model.addAttribute("msg", msg);

        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto) {

        memberService.join(joinDto);

        String msg = Util.url.encode("회원가입이 완료되었습니다.");

        return "redirect:/member/login?msg=%s".formatted(msg);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal MemberContext member, Model model) {


        return "member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/beAuthor")
    public String beAuthorForm() {

        return "member/beAuthor";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/beAuthor")
    public String beAuthor(@AuthenticationPrincipal MemberContext memberContext,
                           String nickname) {

        Member member = memberContext.getMember();

        log.debug(nickname);

        memberService.beAuthor(member, nickname);

        return "member/profile";
    }

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String modifyPasswordForm() {

        return "member/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkPassword")
    public ResponseEntity checkPassword(@AuthenticationPrincipal MemberContext memberContext, @RequestBody String password) {

        boolean isAuthenticated = memberService.checkPassword(memberContext.getUsername(), password);

        if (isAuthenticated) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal MemberContext memberContext, String password) {

        memberService.modifyPassword(memberContext.getUsername(), password);

        return "redirect:/member/profile";

    }


}
