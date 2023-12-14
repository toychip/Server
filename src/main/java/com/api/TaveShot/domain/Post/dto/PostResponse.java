package com.api.TaveShot.domain.Post.dto;

import com.api.TaveShot.domain.Comment.dto.CommentDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/** 게시글 정보 리턴할 Response 클래스 **/
@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private String writer;
    private int view;
    private Long memberId;
    private List<CommentDto.Response> comments;
}