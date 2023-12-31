package com.api.TaveShot.domain.comment.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CommentListResponse(List<CommentResponse> commentResponses, Integer totalPage,
                                  Long totalElements, Boolean isFirst, Boolean isLast) {
}
