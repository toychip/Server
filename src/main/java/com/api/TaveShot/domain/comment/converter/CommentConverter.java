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
//
//    public static CommentResponse entityToDto(final Comment commentEntity) {
//        List<CommentResponse> replies = commentEntity.getChildComments().stream()
//                .map(CommentConverter::entityToDto)
//                .toList();
//
//        return new CommentResponse(
//                commentEntity.getId(),
//                commentEntity.getComment(),
//                commentEntity.getMember().getGitLoginId(),
//                commentEntity.getPost().getId(),
//                commentEntity.getParentComment() != null ? entityToDto(commentEntity.getParentComment()) : null,
//                replies);
//    }
}
