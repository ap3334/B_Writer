package com.example.ebookmarket.app.home;

import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {

        List<Post> posts = postService.getAllPostsLatest100();

        model.addAttribute("posts", posts);

        return "home/main";

    }

}
