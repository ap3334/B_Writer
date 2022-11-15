package com.example.ebookmarket.app.email.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailDto {

    private String address;
    private String title;
    private String message;

}
