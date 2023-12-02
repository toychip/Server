package com.api.TaveShot.domain.Post.dto;

import com.api.TaveShot.domain.Comment.dto.CommentDto;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.domain.Post;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class PostDto {

        /** 게시글의 등록,수정을 처리할 Request 클래스 **/
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Request {

            private Long id;
            private String title;
            private String writer;
            private String content;
            private MultipartFile attachmentFile;
            private String createdDate, modifiedDate;
            private int view;
            private Member member;

            public Post toEntity() {

                return Post.builder()
                        .id(id)
                        .title(title)
                        .writer(writer)
                        .content(content)
                        .view(0)
                        .member(member)
                        .build();
            }
        }

        /** 게시글 정보 리턴할 Response 클래스 **/
        @Getter
        public static class Response {
            private final Long id;
            private final String title;
            private final String writer;
            private final String content;
            private final MultipartFile attachmentFile;
            private final String createdDate, modifiedDate;
            private final int view;
            private final Long memberId;
            private final List<CommentDto.Response> comments;


            public Response(Post post) {
                this.id = post.getId();
                this.title = post.getTitle();
                this.writer = post.getWriter();
                this.content = post.getContent();
                this.attachmentFile = post.getAttachmentFile();
                this.createdDate = String.valueOf(post.getCreatedDate());
                this.modifiedDate = String.valueOf(post.getModifiedDate());
                this.view = post.getView();
                this.memberId = post.getMember().getId();
                this.comments = post.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            }
        }

}
