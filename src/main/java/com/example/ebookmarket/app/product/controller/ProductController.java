package com.example.ebookmarket.app.product.controller;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.service.PostKeywordService;
import com.example.ebookmarket.app.product.dto.ProductCreateDto;
import com.example.ebookmarket.app.product.entity.Product;
import com.example.ebookmarket.app.product.service.ProductService;
import com.example.ebookmarket.app.productHashTag.entity.ProductHashTag;
import com.example.ebookmarket.app.security.dto.MemberContext;
import com.example.ebookmarket.util.Util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberContext memberContext) {

        Member user = memberContext.getMember();
        Product product = productService.getProduct(id);
        List<Post> posts = productService.findPostsByProduct(product);


        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "product/detail";

    }

    @GetMapping("/list")
    public String list(Model model) {

        List<Product> products = productService.findAllForPrintByOrderByIdDesc();

        model.addAttribute("products", products);

        return "product/list";

    }

    @GetMapping("/tag/{tagContent}")
    public String tagList(Model model, @PathVariable String tagContent) {

        List<ProductHashTag> productHashTags = productService.getProductTags(tagContent);

        model.addAttribute("productHashTags", productHashTags);

        return "product/tagList";
    }

}
