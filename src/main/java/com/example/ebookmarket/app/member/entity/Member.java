package com.example.ebookmarket.app.member.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.member.AuthLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private AuthLevel authLevel;


    public List<GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(AuthLevel.USER.name()));

        if (nickname != null) {
            authorities.add(new SimpleGrantedAuthority(AuthLevel.AUTHOR.name()));
        }

        return authorities;

    }

    public boolean hasAuthority(String authority) {

        if (this.authLevel.name().equals(authority)) {
            return true;
        }
        return false;

    }

    public void setNickname(String nickname) {

        if (this.nickname == null) {
            this.nickname = nickname;
        }

    }

    public void changePassword(String password) {

        this.password = password;
    }
}
