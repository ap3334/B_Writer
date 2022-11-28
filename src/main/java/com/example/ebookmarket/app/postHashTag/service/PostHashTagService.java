package com.example.ebookmarket.app.postHashTag.service;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import com.example.ebookmarket.app.postHashTag.repository.PostHashTagRepository;
import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.service.PostKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostHashTagService {

    private final PostHashTagRepository postHashTagRepository;

    private final PostKeywordService postKeywordService;

    public List<PostHashTag> getHashTagByPostIdIn(long[] ids) {
        return postHashTagRepository.findByPostIdIn(ids);
    }

    public void applyPostTags(Post post, String postTagContents) {

        List<PostHashTag> oldPostTags = getPostTags(post);

        List<String> postKeywordContents = Arrays.stream(postTagContents.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<PostHashTag> needToDelete = new ArrayList<>();

        for (PostHashTag oldPostTag : oldPostTags) {
            boolean contains = postKeywordContents.stream().anyMatch(s -> s.equals(oldPostTag.getPostKeyword().getContent()));

            if (contains == false) {
                needToDelete.add(oldPostTag);
            }
        }

        needToDelete.forEach(postTag -> postHashTagRepository.delete(postTag));

        postKeywordContents.forEach(postKeywordContent -> {
            savePostTag(post, postKeywordContent);
        });

    }

    public List<PostHashTag> getPostTags(Post post) {
        return postHashTagRepository.findAllByPostId(post.getId());
    }

    private PostHashTag savePostTag(Post post, String postKeywordContent) {
        PostKeyword postKeyword = postKeywordService.save(postKeywordContent);

        Optional<PostHashTag> opPostTag = postHashTagRepository.findByPostIdAndPostKeywordId(post.getId(), postKeyword.getId());

        if (opPostTag.isPresent()) {
            return opPostTag.get();
        }

        PostHashTag postHashTag = PostHashTag.builder()
                .post(post)
                .member(post.getAuthor())
                .postKeyword(postKeyword)
                .build();

        postHashTagRepository.save(postHashTag);

        return postHashTag;
    }

    public List<PostHashTag> getPostTags(Member member, String postKeywordContent) {
        return postHashTagRepository.findAllByMemberIdAndPostKeyword_contentOrderByPost_idDesc(member.getId(), postKeywordContent);

    }

    public List<PostHashTag> getPostTags(Long authorId, Long postKeywordId) {
        return postHashTagRepository.findAllByMemberIdAndPostKeywordIdOrderByPost_idDesc(authorId, postKeywordId);
    }
}
