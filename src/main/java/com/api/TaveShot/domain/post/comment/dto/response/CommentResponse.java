package com.api.TaveShot.domain.post.comment.dto.response;

import lombok.Builder;


@Builder
public record CommentResponse(Long commentId, String content, String gitLoginId, Long postId, Long parentId) {
}
