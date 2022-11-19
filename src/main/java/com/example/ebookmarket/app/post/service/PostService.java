package com.example.ebookmarket.app.post.service;

import com.example.ebookmarket.app.member.entity.Member;
import com.example.ebookmarket.app.post.dto.PostFormDto;
import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.post.repository.PostRepository;
import com.example.ebookmarket.app.postHashTag.entity.PostHashTag;
import com.example.ebookmarket.app.postHashTag.service.PostHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Transactional
    public Post write(PostFormDto postFormDto, Member member) {

        Post post = Post.builder()
                .author(member)
                .content(postFormDto.getContent())
                .contentHtml(postFormDto.getContentHtml())
                .subject(postFormDto.getSubject())
                .build();

        Post savedPost = postRepository.save(post);

        applyPostTags(post, postFormDto.getPostTagContents());

        return savedPost;

    }

    private void applyPostTags(Post post, String postTagContents) {

        postHashTagService.applyPostTags(post, postTagContents);

    }

    public boolean actorCanHandle(Member member, Post post) {
        return member.getId().equals(post.getAuthor().getId());
    }

    public Optional<Post> findById(Long id) {
        Optional<Post> opPost = postRepository.findById(id);

        if (opPost.isEmpty()) return opPost;

        List<PostHashTag> postHashTags = getPostTags(opPost.get());

        opPost.get().getExtra().put("postHashTags", postHashTags);

        return opPost;
    }

    private List<PostHashTag> getPostTags(Post post) {

        return postHashTagService.getPostTags(post);
    }

    @Transactional
    public void modify(Post post, PostFormDto postFormDto) {

        post.modifyPost(postFormDto.getSubject(), postFormDto.getContent(), postFormDto.getContentHtml());
        applyPostTags(post, postFormDto.getPostTagContents());

    }

    @Transactional
    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
