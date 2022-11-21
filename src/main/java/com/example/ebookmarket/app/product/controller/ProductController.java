package com.example.ebookmarket.app.product.controller;

import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.service.PostKeywordService;
import com.example.ebookmarket.app.product.service.ProductService;
import com.example.ebookmarket.app.security.dto.MemberContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private final PostKeywordService postKeywordService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createForm(Model model, @AuthenticationPrincipal MemberContext memberContext) {

        List<PostKeyword> postKeywords = postKeywordService.findByMemberId(memberContext.getId());

        model.addAttribute("postKeywords", postKeywords);

        return "product/create";
    }

}
