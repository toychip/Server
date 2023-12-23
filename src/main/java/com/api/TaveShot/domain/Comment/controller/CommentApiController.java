package com.api.TaveShot.domain.Comment.controller;

import com.api.TaveShot.domain.Comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.Comment.dto.request.CommentUpdateRequest;
import com.api.TaveShot.domain.Comment.dto.response.CommentResponse;
import com.api.TaveShot.domain.Comment.service.CommentService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/post/{postId}/comments")
    public SuccessResponse<Long> register(final @PathVariable Long postId, final @RequestBody CommentCreateRequest commentCreateRequest) {
        Long createdCommentId = commentService.register(postId, commentCreateRequest);
        return new SuccessResponse<>(createdCommentId);
    }

    /* READ */
//    @GetMapping("/post/{id}/comments")
//    public SuccessResponse<Page<CommentResponse>> read(@PathVariable Long id, Pageable pageable) {
//        Page<CommentResponse> responses = commentService.findAll(id, pageable);
//        return new SuccessResponse<>(responses);
//    }

    /* UPDATE */
    @PutMapping("/post/{postId}/comments/{id}")
    public SuccessResponse<Long> update(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        commentService.update(postId, commentId, commentUpdateRequest);
        return new SuccessResponse<>(commentId);
    }

    /* DELETE */
    @DeleteMapping("/post/{postId}/comments/{id}")
    public SuccessResponse<Long> delete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.delete(postId, commentId);
        return new SuccessResponse<>(commentId);
    }

//    /* CREATE REPLY */
//    @PostMapping("/post/{postId}/comments/{parentId}")
//    public SuccessResponse<Long> saveReply(@PathVariable Long postId, @PathVariable Long parentId, @RequestBody CommentCreateRequest commentCreateRequest) {
//        Long result = commentService.saveReply(postId, parentId, commentCreateRequest);
//        return new SuccessResponse<>(result);
//    }
//
//    /* READ WITH REPLIES */
//    @GetMapping("/post/{postId}/commentsWithReplies")
//    public SuccessResponse<List<CommentResponse>> readWithReplies(@PathVariable Long postId) {
//        List<CommentResponse> responses = commentService.findAllWithReplies(postId);
//        return new SuccessResponse<>(responses);
//    }
}
