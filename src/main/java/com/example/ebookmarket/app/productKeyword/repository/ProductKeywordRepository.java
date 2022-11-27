package com.example.ebookmarket.app.productKeyword.repository;

import com.example.ebookmarket.app.productKeyword.entity.ProductKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {

    Optional<ProductKeyword> findByContent(String content);
}
