package com.api.TaveShot.domain.post.post.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.domain.Tier;
import com.api.TaveShot.domain.comment.dto.response.CommentListResponse;
import com.api.TaveShot.domain.comment.service.CommentService;
import com.api.TaveShot.domain.post.image.service.ImageService;
import com.api.TaveShot.domain.post.post.PostValidator;
import com.api.TaveShot.domain.post.post.converter.PostConverter;
import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.post.domain.PostTier;
import com.api.TaveShot.domain.post.post.dto.request.PostCreateRequest;
import com.api.TaveShot.domain.post.post.dto.request.PostEditRequest;
import com.api.TaveShot.domain.post.post.dto.request.PostSearchCondition;
import com.api.TaveShot.domain.post.post.dto.response.PostListResponse;
import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.domain.post.post.editor.PostEditor;
import com.api.TaveShot.domain.post.post.repository.PostRepository;
import com.api.TaveShot.global.config.S3FileUploader;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final S3FileUploader s3Uploader;
    private final ImageService imageService;
    private final CommentService commentService;
    private final PostValidator postValidator;

    /* --------------------------------- CREATE --------------------------------- */
    @Transactional
    public Long register(final PostCreateRequest request) {
        Member currentMember = getCurrentMember();

        postValidator.validateAuthority(request.getPostTier(), currentMember);

        Post post = PostConverter.createDtoToEntity(request, currentMember);
        postRepository.save(post);

        registerImages(request.getAttachmentFile(), post);
        return post.getId();
    }

    private void registerImages(final List<MultipartFile> multipartFiles, final Post post) {
        List<String> uploadUrls = getImageUrls(multipartFiles);
        uploadUrls.forEach(uploadUrl -> imageService.register(post, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3Uploader.uploadMultipartFiles(multipartFiles);
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    private PostResponse postResponse(final Post post, final CommentListResponse commentListResponse) {
        return PostConverter.entityToResponse(post, commentListResponse);
    }


    /* --------------------------------- READ Single --------------------------------- */
    public PostResponse getSinglePost(final Long postId) {
        Post post = getPostFetchJoin(postId);
        PostTier postTier = post.getPostTier();

        postValidator.validateAuthority(postTier, getCurrentMember());
        CommentListResponse commentResponses = commentService.findComments(postId);
        return postResponse(post, commentResponses);
    }

    private Post getPostFetchJoin(final Long postId) {
        return postRepository.findPostFetchJoin(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));
    }


    /* --------------------------------- READ Paging --------------------------------- */
    public PostListResponse searchPostPaging(final PostSearchCondition condition, final Pageable pageable) {
        Page<PostResponse> postResponses = postRepository.searchPagePost(condition, pageable);

        PostTier postTier = condition.getPostTierEnum();
        postValidator.validateAuthority(postTier, getCurrentMember());

        PostListResponse postListResponse = PostConverter.pageToPostListResponse(postResponses);
        return postListResponse;
    }


    /* --------------------------------- EDIT --------------------------------- */
    @Transactional
    public void edit(final Long postId, final PostEditRequest request) {
        Post post = getPost(postId);
        validate(post);

        PostEditor postEditor = getPostEditor(request, post);

        post.edit(postEditor);

        // 이미지 수정
        editImages(request.getAttachmentFile(), post);
    }

    private void validate(final Post post) {
        validateWriter(post);
        PostTier postTier = post.getPostTier();
        postValidator.validateAuthority(postTier, getCurrentMember());
    }

    private Post getPost(final Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorType._POST_NOT_FOUND));
    }

    private void validateWriter(final Post post) {
        Member currentMember = getCurrentMember();

        Long postWriterId = post.getMemberId();
        Long currentMemberId = currentMember.getId();

        if (!postWriterId.equals(currentMemberId)) {
            throw new ApiException(ErrorType._UNAUTHORIZED);
        }
    }

    private PostEditor getPostEditor(final PostEditRequest request, final Post post) {
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        PostEditor postEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return postEditor;
    }

    private void editImages(final List<MultipartFile> multipartFiles, final Post post) {
        imageService.deleteAllByPost(post);

        // 새로운 이미지 업로드
        List<String> uploadUrls = getImageUrls(multipartFiles);
        uploadUrls.forEach(uploadUrl -> imageService.register(post, uploadUrl));
    }


    /* TODO (영구 삭제 안되도록 어쩌구는 추후에 다시..) */
    /* --------------------------------- DELETE --------------------------------- */
    @Transactional
    public void delete(final Long postId) {
        Post post = getPost(postId);
        PostTier postTier = post.getPostTier();
        postValidator.validateAuthority(postTier, getCurrentMember());
        postRepository.delete(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));
    }

}
