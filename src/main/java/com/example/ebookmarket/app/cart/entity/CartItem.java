package com.example.ebookmarket.app.cart.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CartItem extends BaseEntity {

    @ManyToOne
    private Member member;

    @ManyToOne
    private Product product;
}
