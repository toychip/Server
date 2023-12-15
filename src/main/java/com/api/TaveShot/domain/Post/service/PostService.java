package com.api.TaveShot.domain.Post.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.converter.PostConverter;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.dto.PostCreateRequest;
import com.api.TaveShot.domain.Post.dto.PostListResponse;
import com.api.TaveShot.domain.Post.dto.PostResponse;
import com.api.TaveShot.domain.Post.dto.PostSearchCondition;
import com.api.TaveShot.domain.Post.repository.PostRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    /* CREATE */
    @Transactional
    public PostResponse save(final PostCreateRequest request) {

        // 현재 로그인한 Member 정보 가져오기
        Member currentMember = SecurityUtil.getCurrentMember();
        Post post = PostConverter.createDtoToEntity(request, currentMember);

        postRepository.save(post);

        return PostConverter.entityToResponse(post);
    }

    /* READ Single */
    public PostResponse findById(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorType._POST_NOT_FOUND));

        PostConverter.entityToResponse(post);

        return PostConverter.entityToResponse(post);
    }

    /* READ Paging */
    public PostListResponse searchPostPaging(final PostSearchCondition condition, final Pageable pageable) {
        Page<PostResponse> postResponses = postRepository.searchPagePost(condition, pageable);
        return PostListResponse.builder()
                .postResponses(postResponses.getContent())
                .totalPage(postResponses.getTotalPages())
                .totalElements(postResponses.getTotalElements())
                .isFirst(postResponses.isFirst())
                .isLast(postResponses.isLast())
                .build();
    }
//    /* UPDATE */
//    @Transactional
//    public void update(Long id, PostDto.Request dto) {
//        Post post = postRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
//
//        post.update(dto.getTitle(), dto.getContent(),dto.getAttachmentFile());
//    }

    /* DELETE (영구 삭제 안되도록 어쩌구는 추후에 다시..) */
    @Transactional
    public void delete(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorType._POST_NOT_FOUND));

        postRepository.delete(post);
    }

}
