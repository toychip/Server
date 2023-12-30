package com.api.TaveShot.domain.comment.dto.response;

import com.api.TaveShot.domain.comment.converter.CommentConverter;
import com.api.TaveShot.domain.comment.domain.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class CommentResponse {
    private final Long id;
    private final String content;
    private final String memberId;
    private final Long postId;
    private final CommentResponse parent;
    private final List<CommentResponse> replies;

    @QueryProjection
    @Builder
    public CommentResponse(Long id, String content, String memberId, Long postId, Comment parent, List<CommentResponse> replies) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.postId = postId;
        this.parent = CommentConverter.getParentResponse(parent);
        this.replies = replies;
    }

    public static CommentResponse fromEntity(Comment commentEntity) {
        return CommentConverter.entityToDto(commentEntity);
    }
}
