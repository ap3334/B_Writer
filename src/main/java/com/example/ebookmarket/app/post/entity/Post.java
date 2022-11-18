package com.example.ebookmarket.app.post.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private Member author;

    private String subject;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;

    public String getExtra_postTagLinks() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("postHashTags") == false) {
            return "";
        }

        List<PostHashTag> postTags = (List<PostHashTag>) extra.get("postHashTags");

        if (postTags.isEmpty()) {
            return "";
        }

        return postTags
                .stream()
                .map(postTag -> {
                    String text = "#" + postTag.getPostKeyword().getContent();

                    return """
                            <a href="%s" class="text-link">%s</a>
                            """
                            .stripIndent()
                            .formatted(postTag.getPostKeyword().getListUrl(), text);
                })
                .sorted()
                .collect(Collectors.joining(" "));
    }

}
