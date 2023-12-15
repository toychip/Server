package com.api.TaveShot.domain.Post.controller;

import com.api.TaveShot.domain.Post.dto.PostCreateRequest;
import com.api.TaveShot.domain.Post.dto.PostEditRequest;
import com.api.TaveShot.domain.Post.dto.PostListResponse;
import com.api.TaveShot.domain.Post.dto.PostResponse;
import com.api.TaveShot.domain.Post.dto.PostSearchCondition;
import com.api.TaveShot.domain.Post.service.PostService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/** REST API Controller **/
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/post")
    public SuccessResponse<PostResponse> save(final @ModelAttribute PostCreateRequest request) {
        PostResponse postResponse = postService.save(request);
        return new SuccessResponse<>(postResponse);
    }

    /* READ */
    @GetMapping("/post/{postId}")
    public SuccessResponse<PostResponse> getSinglePost(final @PathVariable Long postId) {
        PostResponse postResponse = postService.getSinglePost(postId);
        return new SuccessResponse<>(postResponse);
    }

    @GetMapping("/post")
    public SuccessResponse<PostListResponse> getPagePost(final @RequestBody PostSearchCondition condition,
                                                         final Pageable pageable) {
        PostListResponse postListResponse = postService.searchPostPaging(condition, pageable);
        return new SuccessResponse<>(postListResponse);
    }

//    /* UPDATE */
    @PatchMapping("/post/{postId}")
    public SuccessResponse<Long> update(final @PathVariable Long postId,
                                        final @RequestBody PostEditRequest request) {
        postService.edit(postId, request);
        return new SuccessResponse<>(postId);
    }

    /* DELETE */
    @DeleteMapping("/post/{postId}")
    public SuccessResponse<Long> delete(final @PathVariable Long postId) {
        postService.delete(postId);
        return new SuccessResponse<>(postId);
    }


}