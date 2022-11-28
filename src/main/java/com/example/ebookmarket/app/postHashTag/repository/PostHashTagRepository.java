package com.example.ebookmarket.app.postHashTag.repository;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
    List<PostHashTag> findByPostIdIn(long[] ids);

    List<PostHashTag> findAllByPostId(Long id);

    Optional<PostHashTag> findByPostIdAndPostKeywordId(Long postId, Long postKeywordId);

    List<PostHashTag> findAllByMemberIdAndPostKeyword_contentOrderByPost_idDesc(Long memberId, String postKeywordContent);

    List<PostHashTag> findAllByMemberIdAndPostKeywordIdOrderByPost_idDesc(Long authorId, Long postKeywordId);
}
