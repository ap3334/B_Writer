package com.example.ebookmarket.app.productHashTag.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.product.entity.Product;
import com.example.ebookmarket.app.productKeyword.entity.ProductKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ProductHashTag extends BaseEntity {

    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private ProductKeyword productKeyword;


}
