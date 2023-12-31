package com.api.TaveShot.domain.comment.converter;

import com.api.TaveShot.domain.comment.domain.Comment;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.comment.dto.response.CommentListResponse;
import com.api.TaveShot.domain.comment.dto.response.CommentResponse;
import com.api.TaveShot.domain.post.post.domain.Post;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

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

    public static CommentListResponse toCommentListResponse(Page<Comment> commentPage,
                                                            List<CommentResponse> commentResponses) {
        return CommentListResponse.builder()
                .commentResponses(commentResponses)
                .totalPage(commentPage.getTotalPages())
                .totalElements(commentPage.getTotalElements())
                .isFirst(commentPage.isFirst())
                .isLast(commentPage.isLast())
                .build();
    }

    public static List<CommentResponse> commentsToResponses(List<Comment> comments) {
        return comments.stream()
                .map(CommentConverter::commentToResponse)
                .toList();
    }

    public static CommentResponse commentToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .gitLoginId(comment.getMember().getGitLoginId())
                .postId(comment.getPost().getId())
                .parentId(
                        Optional.ofNullable(comment.getParent())
                                .map(Comment::getId).orElse(null))
                .build();
    }
}