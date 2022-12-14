package com.example.ebookmarket.app.post.repository;

import com.example.ebookmarket.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop100ByAuthorIdOrderByIdDesc(Long authorId);

    List<Post> findAllByAuthorIdOrderByIdDesc(Long authorId);

}
