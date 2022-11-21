package com.example.ebookmarket.app.postKeyword.repository;

import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {

    List<PostKeyword> getQslAllByAuthorId(Long authorId);

}
