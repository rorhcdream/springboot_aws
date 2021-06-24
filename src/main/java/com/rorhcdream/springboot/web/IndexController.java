package com.rorhcdream.springboot.web;

import com.rorhcdream.springboot.config.auth.LoginUser;
import com.rorhcdream.springboot.config.auth.dto.SessionUser;
import com.rorhcdream.springboot.service.post.PostService;
import com.rorhcdream.springboot.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("post", postService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/post/save")
    public String postSave() {
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "post-update";
    }
}
