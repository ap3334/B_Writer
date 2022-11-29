package com.example.ebookmarket.app.productHashTag.repository;

import com.example.ebookmarket.app.productHashTag.entity.ProductHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductHashTagRepository extends JpaRepository<ProductHashTag, Long> {
    List<ProductHashTag> findAllByProductId(Long productId);

    Optional<ProductHashTag> findByProductIdAndProductKeywordId(Long productId, Long productKeywordId);

    List<ProductHashTag> findAllByProductIdIn(long[] ids);

    List<ProductHashTag> findAllByProductKeyword_contentOrderByProduct_idDesc(String tagContent);
}
