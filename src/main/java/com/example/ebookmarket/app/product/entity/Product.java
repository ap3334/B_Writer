package com.example.ebookmarket.app.product.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.cart.entity.CartItem;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.productHashTag.entity.ProductHashTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public String getExtra_productTagLinks() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("productHashTags") == false) {
            return "";
        }

        List<ProductHashTag> productHashTags = (List<ProductHashTag>) extra.get("productHashTags");

        if (productHashTags.isEmpty()) {
            return "";
        }

        return productHashTags
                .stream()
                .map(productTag -> {
                    String text = "#" + productTag.getProductKeyword().getContent();

                    return """
                            <a href="%s" class="text-link">%s</a>
                            """
                            .stripIndent()
                            .formatted(productTag.getProductKeyword().getListUrl(), text);
                })
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public CartItem getExtra_actor_cartItem() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("actor_cartItem") == false) {
            return null;
        }

        return (CartItem)extra.get("actor_cartItem");
    }

    public boolean getExtra_actor_hasInCart() {
        return getExtra_actor_cartItem() != null;
    }

}
