package com.api.TaveShot.domain.post.post.converter;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.post.dto.request.PostCreateRequest;
import com.api.TaveShot.domain.post.post.dto.response.PostListResponse;
import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;

public class PostConverter {
    public static Post createDtoToEntity(final PostCreateRequest request, final Member currentMember) {
        return Post.builder()
                .title(request.getTitle())
                .member(currentMember)
                .writer(currentMember.getGitName())
                .postTier(request.getPostTier())
                .build();
    }

    public static PostResponse entityToResponse(final Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .view(post.getViewCount())
                .writerId(post.getMemberId())
                // ToDo 수정 시간으로 넣을지, 생성 시간을 넣을지 프론트와 협의
                .writtenTime(post.getCreatedTime())
                .imageUrls(post.getImages())
//                .comments() Comment 수정 후 처리
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
