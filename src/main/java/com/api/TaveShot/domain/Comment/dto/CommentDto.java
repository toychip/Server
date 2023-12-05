package com.api.TaveShot.domain.Comment.dto;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {

    /** 댓글 Service 요청을 위한 Request 클래스 **/

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String comment;
        private Member member;
        private Post post;
        private Comment parentComment;


        /* Dto -> Entity */
        public Comment toEntity() {

            return Comment.builder()
                    .id(id)
                    .comment(comment)
                    .member(member)
                    .post(post)
                    .parentComment(parentComment)
                    .build();
        }
    }


    /** 댓글 정보를 리턴할 Response 클래스 **/
    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private Long id;
        private String comment;
        private String gitName;
        private Long memberId;
        private Long postId;
        private Comment parentComment;
        private List<Response> replies; // 대댓글 리스트


        /* Entity -> Dto*/
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.gitName = comment.getMember().getGitName();
            this.memberId = comment.getMember().getId();
            this.postId = comment.getPost().getId();
            this.parentComment = comment.getParentComment();
            this.replies = comment.getChildComments().stream().map(Response::new).collect(Collectors.toList());
        }
    }

    public static class ResponseWithReplies extends Response {
        public ResponseWithReplies(Comment comment, List<Comment> replies) {
            super(comment);
        }
    }

}