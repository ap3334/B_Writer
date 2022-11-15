package com.example.ebookmarket.app.member.controller;

import com.example.ebookmarket.app.member.dto.JoinDto;
import com.example.ebookmarket.app.member.service.MemberService;
import com.example.ebookmarket.app.security.dto.MemberContext;
import com.example.ebookmarket.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


}
