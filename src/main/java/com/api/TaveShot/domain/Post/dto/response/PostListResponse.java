package com.api.TaveShot.domain.Post.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record PostListResponse(List<PostResponse> postResponses, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
