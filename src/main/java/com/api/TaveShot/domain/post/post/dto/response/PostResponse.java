package com.api.TaveShot.domain.post.post.dto.response;

import com.api.TaveShot.domain.post.comment.dto.response.CommentListResponse;
import com.api.TaveShot.domain.post.image.converter.ImageConverter;
import com.api.TaveShot.domain.post.image.domain.Image;
import com.api.TaveShot.domain.post.image.dto.ImageResponse;
import com.api.TaveShot.global.util.TimeUtil;
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
    private int commentCount;
    private Long writerId;
    private String writerProfileImgUrl;
    private String writtenTime;
    private List<ImageResponse> imageUrls;
    private CommentListResponse commentListResponse;

    @Builder
    public PostResponse(Long postId, String title, String content, String writer, Integer view,
                        Integer commentCount, Long writerId, String writerProfileImgUrl, LocalDateTime createdDate, List<Image> images) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.commentCount = commentCount;
        this.writerId = writerId;
        this.writerProfileImgUrl = writerProfileImgUrl;
        writtenTime = TimeUtil.formatCreatedDate(createdDate);
        this.imageUrls = ImageConverter.imageToImageResponse(images);
    }
}