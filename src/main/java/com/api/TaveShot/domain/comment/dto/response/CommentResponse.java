package com.api.TaveShot.domain.comment.dto.response;

import com.api.TaveShot.domain.comment.converter.CommentConverter;
import com.api.TaveShot.domain.comment.domain.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponse {
    private final Long id;
    private final String comment;
    private final String memberId;
    private final Long postId;
    private final CommentResponse parentComment;
    private final List<CommentResponse> replies;

    @QueryProjection
    public CommentResponse(Long id, String comment, String memberId, Long postId, CommentResponse parentComment, List<CommentResponse> replies) {
        this.id = id;
        this.comment = comment;
        this.memberId = memberId;
        this.postId = postId;
        this.parentComment = parentComment;
        this.replies = replies;
    }

    public static CommentResponse fromEntity(Comment commentEntity) {
        return CommentConverter.entityToDto(commentEntity);
    }
}
