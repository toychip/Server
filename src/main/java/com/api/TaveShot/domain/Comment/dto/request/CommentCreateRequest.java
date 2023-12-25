package com.api.TaveShot.domain.Comment.dto.request;

import com.api.TaveShot.domain.post.post.domain.PostTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentCreateRequest {

    @Schema(description = "댓글 내용", example = "댓글 내용 예시")
    private String comment;

    @Schema(description = "부모 댓글 ID (대댓글인 경우)", example = "1", nullable = true)
    private Long parentCommentId;

}
