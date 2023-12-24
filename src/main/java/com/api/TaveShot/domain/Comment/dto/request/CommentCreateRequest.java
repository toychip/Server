package com.api.TaveShot.domain.Comment.dto.request;

import com.api.TaveShot.domain.post.post.domain.PostTier;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentCreateRequest {

    private String comment;

    private Long parentCommentId;

    @NotEmpty
    private String postTier;

    public PostTier getPostTier() {
        // 문자열을 enum으로 변환
        return PostTier.findTier(postTier);
    }
}
