package com.api.TaveShot.domain.Post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/** 게시글의 등록, 수정을 처리할 Request 클래스 **/
@Getter
@Builder
@AllArgsConstructor
public class PostCreateRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    private String content;

    @Schema(description = "게시글 작성자", example = "작성자 예시")
    private String writer;

    @Schema(description = "게시글 첨부 파일", type = "string", format = "binary")
    private MultipartFile attachmentFile;
}