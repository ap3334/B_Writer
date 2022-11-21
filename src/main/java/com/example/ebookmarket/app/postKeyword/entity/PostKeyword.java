package com.example.ebookmarket.app.postKeyword.entity;

import com.example.ebookmarket.app.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostKeyword extends BaseEntity {

    private String content;

    public Object getListUrl() {
        return "/post/tag/" + content;
    }

    public long getExtra_postTagsCount() {
        return (long) getExtra().get("postHashTagsCount");
    }

}
