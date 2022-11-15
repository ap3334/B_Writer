package com.example.ebookmarket.app.member.controller;

import com.example.ebookmarket.app.member.dto.JoinDto;
import com.example.ebookmarket.app.member.service.MemberService;
import com.example.ebookmarket.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/login")
    public String loginForm() {

        return "member/login";
    }

    @GetMapping("/join")
    public String joinForm(@RequestParam(value = "msg", required = false) String msg, Model model) {

        model.addAttribute("msg", msg);

        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto) {

        memberService.join(joinDto);

        String msg = Util.url.encode("회원가입이 완료되었습니다.");

        return "redirect:/member/login?msg=%s".formatted(msg);

    }


}
