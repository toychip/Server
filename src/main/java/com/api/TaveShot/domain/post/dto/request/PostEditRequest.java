package com.api.TaveShot.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/** 게시글의 등록, 수정을 처리할 Request 클래스 **/
@Getter
@Builder
@AllArgsConstructor
public class PostEditRequest {

    private String title;
    private String content;
    private MultipartFile attachmentFile;
}