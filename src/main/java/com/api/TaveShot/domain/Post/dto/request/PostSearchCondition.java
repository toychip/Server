package com.api.TaveShot.domain.Post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostSearchCondition {
    private String writer;
    private String content;
    private String title;
}
