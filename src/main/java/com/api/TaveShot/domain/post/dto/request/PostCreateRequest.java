package com.api.TaveShot.domain.post.dto.request;

import com.api.TaveShot.domain.post.domain.PostTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class PostCreateRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    private String content;

    @Schema(description = "게시판 등급", example = "BronzeSilver or Gold or Platinum or High")
    @NotEmpty
    private String postTier;

    @Schema(description = "게시글 첨부 파일", type = "string", format = "binary")
    private MultipartFile attachmentFile;

    public PostTier getPostTier() {
        // 문자열을 enum으로 변환
        return PostTier.findTier(postTier);
    }
}