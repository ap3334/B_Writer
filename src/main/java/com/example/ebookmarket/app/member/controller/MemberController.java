package com.example.ebookmarket.app.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public String loginForm() {

        return "member/login";
    }

    @GetMapping("/join")
    public String joinForm() {

        return "member/join";
    }


}
