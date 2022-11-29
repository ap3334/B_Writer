package com.example.ebookmarket.app.productHashTag.service;

import com.example.ebookmarket.app.product.entity.Product;
import com.example.ebookmarket.app.productHashTag.entity.ProductHashTag;
import com.example.ebookmarket.app.productHashTag.repository.ProductHashTagRepository;
import com.example.ebookmarket.app.productKeyword.entity.ProductKeyword;
import com.example.ebookmarket.app.productKeyword.service.ProductKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductHashTagService {

    private final ProductHashTagRepository productHashTagRepository;

    private final ProductKeywordService productKeywordService;

    public void applyProductTags(Product product, String productTagContents) {

        List<ProductHashTag> oldProductTags = getProductHashTags(product);

        List<String> productKeywordContents = Arrays.stream(productTagContents.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<ProductHashTag> needToDelete = new ArrayList<>();

        for (ProductHashTag oldProductTag : oldProductTags) {
            boolean contains = productKeywordContents.stream().anyMatch(s -> s.equals(oldProductTag.getProductKeyword().getContent()));

            if (contains == false) {
                needToDelete.add(oldProductTag);
            }
        }

        needToDelete.forEach(productTag -> productHashTagRepository.delete(productTag));

        productKeywordContents.forEach(productKeywordContent -> {
            saveProductHashTag(product, productKeywordContent);
        });

    }

    private ProductHashTag saveProductHashTag(Product product, String productKeywordContent) {
        ProductKeyword productKeyword = productKeywordService.save(productKeywordContent);

        Optional<ProductHashTag> opProductTag = productHashTagRepository.findByProductIdAndProductKeywordId(product.getId(), productKeyword.getId());

        if (opProductTag.isPresent()) {
            return opProductTag.get();
        }

        ProductHashTag productHashTag = ProductHashTag.builder()
                .product(product)
                .member(product.getAuthor())
                .productKeyword(productKeyword)
                .build();

        productHashTagRepository.save(productHashTag);

        return productHashTag;
    }

    private List<ProductHashTag> getProductHashTags(Product product) {
        return productHashTagRepository.findAllByProductId(product.getId());
    }

    public List<ProductHashTag> getProductTagsByProductIdIn(long[] ids) {
        return productHashTagRepository.findAllByProductIdIn(ids);
    }
}
