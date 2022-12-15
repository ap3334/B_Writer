package com.example.ebookmarket.app.cart.repository;

import com.example.ebookmarket.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByMemberIdAndProductId(Long id, Long productId);

}
