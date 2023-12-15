package com.api.TaveShot.domain.Post.dto;

import com.api.TaveShot.domain.Comment.dto.CommentResponse;
import com.querydsl.core.annotations.QueryProjection;
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
    private Long writerId;
    private List<CommentResponse> comments;

    @QueryProjection
    public PostResponse(Long postId, String title, String content, String writer, int view, Long writerId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.writerId = writerId;
    }
}