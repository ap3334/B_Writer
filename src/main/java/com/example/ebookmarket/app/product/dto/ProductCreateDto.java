package com.example.ebookmarket.app.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductCreateDto {

    @NotBlank
    private String subject;
    @NotNull
    private int price;
    @NotNull
    private Long postKeywordId;
    @NotBlank
    private String productTagContents;

}
