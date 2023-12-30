package com.api.TaveShot.domain.comment.dto.response;

import lombok.Builder;


@Builder
public record CommentResponse(Long id, String content, String gitLoginId, Long postId, Long parentId) {
}
