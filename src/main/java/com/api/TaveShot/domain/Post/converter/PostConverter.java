package com.api.TaveShot.domain.Post.converter;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.dto.PostCreateRequest;
import com.api.TaveShot.domain.Post.dto.PostResponse;

public class PostConverter {
    public static Post createDtoToEntity(PostCreateRequest request, Member currentMember) {
        return Post.builder()
                .title(request.getTitle())
                .member(currentMember)
                .writer(request.getWriter())
                .build();
    }

    public static PostResponse toPostCreateResponse(Post post, Member currentMember) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .view(post.getViewCount())
                .memberId(currentMember.getId())
//                .comments() Comment 수정 후 처리
                .build();
    }
}
