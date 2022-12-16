package com.example.ebookmarket.app.cart.service;

import com.example.ebookmarket.app.cart.entity.CartItem;
import com.example.ebookmarket.app.cart.repository.CartItemRepository;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartItem addProduct(Member member, Long productId) {

        CartItem oldCartItem = cartItemRepository.findByMemberIdAndProductId(member.getId(), productId).orElse(null);

        if (oldCartItem != null) {
            return oldCartItem;
        }

        Product product = Product.builder()
                .id(productId).build();

        CartItem cartItem = CartItem.builder()
                .member(member)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return cartItem;

    }

    public List<CartItem> getCartItems(Member member) {

        List<CartItem> cartItems = cartItemRepository.findByMemberId(member.getId());

        return cartItems;

    }
}
