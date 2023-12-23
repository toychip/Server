package com.api.TaveShot.domain.Comment.converter;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.Comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.post.post.domain.Post;

public class CommentConverter {

    public static Comment createDtoToEntity(String comment, Member member, Post post) {
        return Comment.builder()
                .comment(comment)
                .member(member)
                .post(post)
                .parentComment(null) // 대댓글이 아닌 경우 parentComment는 null
                .build();
    }

    public static Comment createDtoToEntity(String comment, Member member, Post post, Comment parentComment) {
        return Comment.builder()
                .comment(comment)
                .member(member)
                .post(post)
                .parentComment(parentComment)
                .build();
    }
}
