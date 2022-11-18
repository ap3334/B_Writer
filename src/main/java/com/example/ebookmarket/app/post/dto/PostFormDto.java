package com.example.ebookmarket.app.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostFormDto {

    @NotBlank
    private String subject;
    @NotBlank
    private String content;
    @NotBlank
    private String contentHtml;
    @NotBlank
    private String postTagContents;

}
