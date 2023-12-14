package com.api.TaveShot.domain.Post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/** 게시글의 등록,을 처리할 Request 클래스 **/
@Getter
@Builder
@AllArgsConstructor
public class PostCreateRequest {

    private String title;
    private String content;
    private String writer;
    private MultipartFile attachmentFile;
}