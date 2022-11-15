package com.example.ebookmarket.app.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirm;

    @NotEmpty
    private String email;

}
