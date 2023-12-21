package com.api.TaveShot.domain.post.post.dto.request;

import com.api.TaveShot.domain.post.post.domain.PostTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostSearchCondition {

    @Schema(description = "검색할 작성자 이름", example = "작성자1")
    private String writer;

    @Schema(description = "검색할 내용", example = "내용 예시")
    private String content;

    @Schema(description = "검색할 제목", example = "제목 예시")
    private String title;

    @NotEmpty
    @Schema(description = "게시판 티어. 사용 가능한 값: 'BronzeSilver', 'Gold', 'Platinum', 'High'",
            example = "Gold", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postTier; // 게시판 티어, 필수 필드

    public PostTier getPostTierEnum() {
        return PostTier.findTier(postTier);
    }
}
