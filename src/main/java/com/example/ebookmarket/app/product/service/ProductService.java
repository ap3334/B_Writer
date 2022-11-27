package com.example.ebookmarket.app.product.service;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.service.PostKeywordService;
import com.example.ebookmarket.app.product.dto.ProductCreateDto;
import com.example.ebookmarket.app.product.entity.Product;
import com.example.ebookmarket.app.product.repository.ProductRepository;
import com.example.ebookmarket.app.productHashTag.entity.ProductHashTag;
import com.example.ebookmarket.app.productHashTag.service.ProductHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final PostKeywordService postKeywordService;
    
    private final ProductHashTagService productHashTagService;

    @Transactional
    public Product create(Member author, ProductCreateDto productCreateDto) {

        PostKeyword postKeyword = postKeywordService.findById(productCreateDto.getPostKeywordId());

        Product product = Product.builder()
                .author(author)
                .price(productCreateDto.getPrice())
                .postKeyword(postKeyword)
                .subject(productCreateDto.getSubject())
                .build();

        productRepository.save(product);

        applyProductHashTags(product, productCreateDto.getProductTagContents());

        return product;

    }

    @Transactional
    public void applyProductHashTags(Product product, String productTagContents) {

        productHashTagService.applyProductTags(product, productTagContents);
    }
}
