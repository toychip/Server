package com.api.TaveShot.domain.post.dto.response;

import com.api.TaveShot.domain.Comment.dto.response.CommentResponse;
import com.api.TaveShot.global.util.TimeUtil;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
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
    private String writtenTime;
    private List<CommentResponse> comments;

    @QueryProjection
    public PostResponse(Long postId, String title, String content, String writer, int view, Long writerId, LocalDateTime createdDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.writerId = writerId;
        writtenTime = TimeUtil.formatCreatedDate(createdDate);
    }
}