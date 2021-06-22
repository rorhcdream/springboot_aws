package com.rorhcdream.springboot.web.dto.post;

import com.rorhcdream.springboot.domain.post.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
