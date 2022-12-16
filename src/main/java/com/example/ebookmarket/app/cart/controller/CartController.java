package com.example.ebookmarket.app.cart.controller;

import com.example.ebookmarket.app.cart.entity.CartItem;
import com.example.ebookmarket.app.cart.service.CartService;
import com.example.ebookmarket.app.security.dto.MemberContext;
import com.example.ebookmarket.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{productId}")
    public String addProduct(@PathVariable Long productId, @AuthenticationPrincipal MemberContext memberContext) {

        cartService.addProduct(memberContext.getMember(), productId);

        String msg = Util.url.encode("장바구니에 상품이 추가되었습니다.");

        return "redirect:/cart/list?msg=%s".formatted(msg);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String showLists(Model model, @AuthenticationPrincipal MemberContext memberContext) {

        List<CartItem> cartItems = cartService.getCartItems(memberContext.getMember());

        model.addAttribute("items", cartItems);

        return "cart/items";

    }

}
