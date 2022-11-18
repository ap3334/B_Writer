package com.example.ebookmarket.app.postHashTag.service;

import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import com.example.ebookmarket.app.postHashTag.repository.PostHashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostHashTagService {

    private final PostHashTagRepository postHashTagRepository;

    public List<PostHashTag> getHashTagByPostIdIn(long[] ids) {
        return postHashTagRepository.findByPostIdIn(ids);
    }
}
