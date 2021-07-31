package com.rorhcdream.springboot.web.dto.post;

import com.rorhcdream.springboot.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
