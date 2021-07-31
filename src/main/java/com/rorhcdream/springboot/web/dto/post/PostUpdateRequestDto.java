package com.rorhcdream.springboot.web.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateRequestDto {
    private String title;
    private String content;
}
