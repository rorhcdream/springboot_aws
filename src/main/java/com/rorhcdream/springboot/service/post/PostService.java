package com.rorhcdream.springboot.service.post;

import com.rorhcdream.springboot.domain.post.Post;
import com.rorhcdream.springboot.domain.post.PostRepository;
import com.rorhcdream.springboot.web.dto.post.PostListResponseDto;
import com.rorhcdream.springboot.web.dto.post.PostResponseDto;
import com.rorhcdream.springboot.web.dto.post.PostSaveRequestDto;
import com.rorhcdream.springboot.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
        post.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );
        postRepository.delete(post);
    }
}
