package com.example.ebookmarket.app.product.service;

import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

}
