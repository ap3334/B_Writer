package com.example.ebookmarket.app.product.service;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import com.example.ebookmarket.app.postHashTag.service.PostHashTagService;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.service.PostKeywordService;
import com.example.ebookmarket.app.product.dto.ProductCreateDto;
import com.example.ebookmarket.app.product.entity.Product;
import com.example.ebookmarket.app.product.repository.ProductRepository;
import com.example.ebookmarket.app.productHashTag.entity.ProductHashTag;
import com.example.ebookmarket.app.productHashTag.service.ProductHashTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final PostKeywordService postKeywordService;
    
    private final ProductHashTagService productHashTagService;

    private final PostHashTagService postHashTagService;

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

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id의 상품이 존재하지 않습니다."));
    }

    public List<Post> findPostsByProduct(Product product) {
        Member author = product.getAuthor();
        PostKeyword postKeyword = product.getPostKeyword();
        List<PostHashTag> postHashTags = postHashTagService.getPostTags(author.getId(), postKeyword.getId());

        return postHashTags
                .stream()
                .map(PostHashTag::getPost)
                .collect(Collectors.toList());
    }

    public List<Product> findAllForPrintByOrderByIdDesc() {

        List<Product> products = findAllByOrderByIdDesc();

        loadForPrintData(products);

        return products;
    }

    private void loadForPrintData(List<Product> products) {
        long[] ids = products
                .stream()
                .mapToLong(Product::getId)
                .toArray();

        List<ProductHashTag> productTagsByProductIds = productHashTagService.getProductTagsByProductIdIn(ids);

        Map<Long, List<ProductHashTag>> productTagsByProductIdMap = productTagsByProductIds.stream()
                .collect(groupingBy(
                        productHashTag -> productHashTag.getProduct().getId(), toList()
                ));

        products.stream().forEach(product -> {
            List<ProductHashTag> productHashTags = productTagsByProductIdMap.get(product.getId());

            if (productHashTags == null || productHashTags.size() == 0) return;

            product.getExtra().put("productHashTags", productHashTags);
        });
    }

    private List<Product> findAllByOrderByIdDesc() {

        return productRepository.findAllByOrderByIdDesc();
    }

    public List<ProductHashTag> getProductTags(String tagContent) {

        List<ProductHashTag> productHashTags = productHashTagService.getProductHashTags(tagContent);

        loadForPrintDataOnProductHashTagList(productHashTags);

        return productHashTags;
    }

    private void loadForPrintDataOnProductHashTagList(List<ProductHashTag> productHashTags) {

        List<Product> products = productHashTags.stream()
                .map(ProductHashTag::getProduct)
                .collect(toList());

        loadForPrintData(products);

    }

}
