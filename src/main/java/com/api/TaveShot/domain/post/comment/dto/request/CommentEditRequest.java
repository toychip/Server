package com.api.TaveShot.domain.post.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class CommentEditRequest {
    private String comment;
}
