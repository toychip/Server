package com.api.TaveShot.domain.post.post.dto.request;

import com.api.TaveShot.domain.post.post.domain.PostTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
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

    @Schema(description = "게시판 등급", example = "'POST_BRONZE_SILVER' or 'POST_GOLD' or 'POST_PLATINUM' or 'POST_HIGH'")
    @NotEmpty
    private String postTier;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            type = "string",
            format = "binary")
    private List<MultipartFile> attachmentFile;

    public PostTier getPostTier() {
        // 문자열을 enum으로 변환
        return PostTier.findTier(postTier);
    }
}