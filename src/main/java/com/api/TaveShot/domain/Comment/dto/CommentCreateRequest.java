package com.api.TaveShot.domain.Comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentCreateRequest {
    private final String comment;
    private final String memberId;
    private final Long postId;
    private final Long parentCommentId;
}
