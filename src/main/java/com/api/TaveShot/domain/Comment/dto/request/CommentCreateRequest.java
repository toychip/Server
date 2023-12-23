package com.api.TaveShot.domain.Comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentCreateRequest {
    private String comment;
    private Long parentCommentId;
}
