package com.example.ebookmarket.app.member.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.member.AuthLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String email;

    private AuthLevel authLevel;

}
