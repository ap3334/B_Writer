package com.example.ebookmarket.app.home;

import com.example.ebookmarket.app.post.entity.Post;
import com.example.ebookmarket.app.post.service.PostService;
import com.example.ebookmarket.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal MemberContext memberContext) {

        if (memberContext != null) {
            List<Post> posts = postService.getAllPostsLatest100ById(memberContext.getId());

            model.addAttribute("posts", posts);
        }

        //TODO 로그인 안되어있을 경우 상품이 보여지게 수정 필요

        return "home/main";

    }

}
