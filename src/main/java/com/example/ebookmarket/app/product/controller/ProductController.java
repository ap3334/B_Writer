package com.example.ebookmarket.app.product.controller;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.service.PostKeywordService;
import com.example.ebookmarket.app.product.dto.ProductCreateDto;
import com.example.ebookmarket.app.product.entity.Product;
import com.example.ebookmarket.app.product.service.ProductService;
import com.example.ebookmarket.app.security.dto.MemberContext;
import com.example.ebookmarket.util.Util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private final PostKeywordService postKeywordService;

    @PreAuthorize("isAuthenticated() and hasAuthority('AUTHOR')")
    @GetMapping("/create")
    public String createForm(Model model, @AuthenticationPrincipal MemberContext memberContext) {

        List<PostKeyword> postKeywords = postKeywordService.findByMemberId(memberContext.getId());

        model.addAttribute("postKeywords", postKeywords);

        return "product/create";
    }

    @PreAuthorize("isAuthenticated() and hasAuthority('AUTHOR')")
    @PostMapping("/create")
    public String create(@Valid ProductCreateDto productCreateDto, @AuthenticationPrincipal MemberContext memberContext) {

        Member author = memberContext.getMember();
        Product product = productService.create(author, productCreateDto);
        String msg = Util.url.encode("상품이 등록되었습니다.");

        return "redirect:/product/%d?msg=%s".formatted(product.getId(), msg);

    }

}
