package com.api.TaveShot.domain.post.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/** 게시글의 등록, 수정을 처리할 Request 클래스 **/
@Getter
@Builder
@AllArgsConstructor
public class PostEditRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    private String content;

    @Schema(description = "게시판 등급", example = "BronzeSilver or Gold or Platinum or High")
    @NotEmpty
    private String postTier;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            type = "binary value",
            format = "binary")
    private List<MultipartFile> attachmentFile;


}