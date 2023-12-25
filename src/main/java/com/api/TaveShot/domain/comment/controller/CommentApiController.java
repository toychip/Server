package com.api.TaveShot.domain.comment.controller;

import com.api.TaveShot.domain.comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.comment.dto.request.CommentUpdateRequest;
import com.api.TaveShot.domain.comment.service.CommentService;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /* CREATE */
    @Operation(summary = "댓글 등록", description = "지정된 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 등록 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class)))
    })
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
