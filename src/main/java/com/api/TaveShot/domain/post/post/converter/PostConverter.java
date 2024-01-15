package com.api.TaveShot.domain.post.post.converter;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.post.comment.dto.response.CommentListResponse;
import com.api.TaveShot.domain.post.image.converter.ImageConverter;
import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.post.dto.request.PostCreateRequest;
import com.api.TaveShot.domain.post.post.dto.response.PostListResponse;
import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;

public class PostConverter {
    public static Post createDtoToEntity(final PostCreateRequest request, final Member currentMember) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(currentMember)
                .writer(currentMember.getGitLoginId())
                .postTier(request.getPostTier())
                .build();
    }

    public static PostResponse entityToResponse(final Post post, final CommentListResponse commentListResponse) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .view(post.getViewCount())
                .writerId(post.getMemberId())
                .commentCount(post.getComments().size())
                .writtenTime(post.getCreatedTime())
                .imageUrls(ImageConverter.imageToImageResponse(post.getImages()))
                .commentListResponse(commentListResponse)
                .build();
    }

    public static PostListResponse pageToPostListResponse(final Page<PostResponse> postResponses) {
        return PostListResponse.builder()
                .postResponses(postResponses.getContent())
                .totalPage(postResponses.getTotalPages())
                .totalElements(postResponses.getTotalElements())
                .isFirst(postResponses.isFirst())
                .isLast(postResponses.isLast())
                .build();
    }
}
