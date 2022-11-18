package com.example.ebookmarket.app.postKeyword.service;

import com.example.ebookmarket.app.postKeyword.entity.PostKeyword;
import com.example.ebookmarket.app.postKeyword.repository.PostKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostKeywordService {

    private final PostKeywordRepository postKeywordRepository;

    public PostKeyword save(String content) {

        Optional<PostKeyword> optKeyword = postKeywordRepository.findByContent(content);

        if (optKeyword.isPresent()) {
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword
                .builder()
                .content(content)
                .build();

        postKeywordRepository.save(postKeyword);

        return postKeyword;

    }
}
