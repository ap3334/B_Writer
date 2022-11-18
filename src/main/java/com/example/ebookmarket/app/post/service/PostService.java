package com.example.ebookmarket.app.post.service;

import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.post.repository.PostRepository;
import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import com.example.ebookmarket.app.postHashTag.service.PostHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostHashTagService postHashTagService;


    public List<Post> getAllPostsLatest100() {

        List allPosts = postRepository.findTop100ByOrderByIdDesc();

        loadForPrint(allPosts);

        return allPosts;
    }

    private void loadForPrint(List<Post> allPosts) {

        long[] ids = allPosts
                .stream()
                .mapToLong(Post::getId)
                .toArray();

        List<PostHashTag> postHashTagsByPostIds = postHashTagService.getHashTagByPostIdIn(ids);

        Map<Long, List<PostHashTag>> postHashTagsByPostIdsMap = postHashTagsByPostIds.stream().collect(groupingBy(
                postHashTag -> postHashTag.getPost().getId(), toList()
        ));

        allPosts.stream().forEach(post -> {
            List<PostHashTag> postHashTags = postHashTagsByPostIdsMap.get(post.getId());

            if (postHashTags == null || postHashTags.size() == 0) return;

            post.getExtra().put("postHashTags", postHashTags);
        });

    }
}
