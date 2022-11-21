package com.example.ebookmarket.app.product.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Product extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Member author;

    @ManyToOne(fetch = LAZY)
    private PostKeyword postKeyword;

    private String subject;

    private int price;

}
