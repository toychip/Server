package com.api.TaveShot.domain.post.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.post.converter.PostConverter;
import com.api.TaveShot.domain.post.domain.Post;
import com.api.TaveShot.domain.post.dto.request.PostCreateRequest;
import com.api.TaveShot.domain.post.dto.request.PostEditRequest;
import com.api.TaveShot.domain.post.dto.request.PostSearchCondition;
import com.api.TaveShot.domain.post.dto.response.PostListResponse;
import com.api.TaveShot.domain.post.dto.response.PostResponse;
import com.api.TaveShot.domain.post.editor.PostEditor;
import com.api.TaveShot.domain.post.repository.PostRepository;
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

    /* --------------------------------- CREATE --------------------------------- */
    @Transactional
    public PostResponse register(final PostCreateRequest request) {
        Member currentMember = getCurrentMember();
        Post post = PostConverter.createDtoToEntity(request, currentMember);
        postRepository.save(post);
        return postResponse(post);
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    private PostResponse postResponse(Post post) {
        return PostConverter.entityToResponse(post);
    }


    /* --------------------------------- READ Single --------------------------------- */
    public PostResponse getSinglePost(final Long postId) {
        Post post = getPost(postId);
        return postResponse(post);
    }


    private Post getPost(final Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorType._POST_NOT_FOUND));
    }


    /* --------------------------------- READ Paging --------------------------------- */
    public PostListResponse searchPostPaging(final PostSearchCondition condition, final Pageable pageable) {
        Page<PostResponse> postResponses = postRepository.searchPagePost(condition, pageable);
        PostListResponse postListResponse = PostConverter.pageToPostListResponse(postResponses);
        return postListResponse;
    }


    /* --------------------------------- EDIT --------------------------------- */
    @Transactional
    public void edit(final Long postId, final PostEditRequest request) {
        Post post = getPost(postId);
        validateAuthority(post);
        PostEditor postEditor = getPostEditor(request, post);

        post.edit(postEditor);
    }

    private void validateAuthority(final Post post) {
        Member currentMember = getCurrentMember();

        Long postWriterId = post.getMemberId();
        Long currentMemberId = currentMember.getId();

        if (!postWriterId.equals(currentMemberId)) {
            throw new ApiException(ErrorType._UNAUTHORIZED);
        }
    }

    private PostEditor getPostEditor(PostEditRequest request, Post post) {
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        PostEditor postEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return postEditor;
    }


    /* TODO (영구 삭제 안되도록 어쩌구는 추후에 다시..) */
    /* --------------------------------- DELETE --------------------------------- */
    @Transactional
    public void delete(final Long postId) {
        Post post = getPost(postId);
        postRepository.delete(post);
    }

}
