package com.api.TaveShot.domain.comment.converter;

import com.api.TaveShot.domain.comment.domain.Comment;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.comment.dto.response.CommentResponse;
import com.api.TaveShot.domain.post.post.domain.Post;

import java.util.List;

public class CommentConverter {

    public static Comment createDtoToEntity(final String comment, final Member member, final Post post) {
        return Comment.builder()
                .content(comment)
                .member(member)
                .post(post)
                .parent(null) // 대댓글이 아닌 경우 parentComment는 null
                .build();
    }

    public static Comment createDtoToEntity(final String comment, final Member member,
                                            final Post post, final Comment parentComment) {
        return Comment.builder()
                .content(comment)
                .member(member)
                .post(post)
                .parent(parentComment)
                .build();
    }

    public static CommentResponse entityToDto(final Comment commentEntity) {
        List<CommentResponse> replies = commentEntity.getChild().stream()
                .map(CommentConverter::entityToDto)
                .toList();

        CommentResponse parentResponse = getParentResponse(commentEntity);

        return CommentResponse.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .memberId(commentEntity.getMember().getGitLoginId())
                .postId(commentEntity.getPost().getId())
                .parent(parentResponse)
                .replies(replies)
                .build();
    }

    public static CommentResponse getParentResponse(Comment commentEntity) {
        if (commentEntity.getParent() != null) {
            return entityToDto(commentEntity.getParent());
        } else {
            return null;
        }
    }
}