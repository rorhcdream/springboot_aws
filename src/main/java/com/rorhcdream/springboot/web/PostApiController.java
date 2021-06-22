package com.rorhcdream.springboot.web;

import com.rorhcdream.springboot.service.post.PostService;
import com.rorhcdream.springboot.web.dto.post.PostResponseDto;
import com.rorhcdream.springboot.web.dto.post.PostSaveRequestDto;
import com.rorhcdream.springboot.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;

    @GetMapping("/api/v1/post/{id}")
    public PostResponseDto findById (@PathVariable Long id) {
        return postService.findById(id);
    }

    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @PostMapping("/api/v1/post")
    public long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }
}
