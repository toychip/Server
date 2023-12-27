package com.api.TaveShot.domain.comment.controller;

import com.api.TaveShot.domain.comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.comment.dto.request.CommentEditRequest;
import com.api.TaveShot.domain.comment.dto.response.CommentResponse;
import com.api.TaveShot.domain.comment.service.CommentService;
import com.api.TaveShot.domain.post.post.dto.response.PostListResponse;
import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

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
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class)))
    })
    @PostMapping("/post/{postId}/comments")
    public SuccessResponse<Long> register(final @PathVariable Long postId, final @RequestBody CommentCreateRequest commentCreateRequest) {
        Long createdCommentId = commentService.register(postId, commentCreateRequest);
        return new SuccessResponse<>(createdCommentId);
    }



    /* READ */
    @Operation(summary = "특정 게시글 댓글 조회", description = "특정 게시글에 해당하는 댓글들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class)))
    })
    @GetMapping("/post/{postId}/comments")
    public SuccessResponse<Page<CommentResponse>> read(final @PathVariable Long postId, final Pageable pageable) {
        Page<CommentResponse> commentResponses = commentService.findAll(postId, pageable);
        return new SuccessResponse<>(commentResponses);
    }


    /* UPDATE */
    @Operation(summary = "댓글 수정", description = "지정된 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostListResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorType.class)))
    })
    @PatchMapping("/post/{postId}/comments/{commentId}")
    public SuccessResponse<Long> edit(final @PathVariable Long postId, final @PathVariable Long commentId, final @RequestBody CommentEditRequest commentEditRequest) {
        commentService.edit(postId, commentId, commentEditRequest);
        return new SuccessResponse<>(commentId);
    }


    /* DELETE */
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public SuccessResponse<Long> delete(final @PathVariable Long postId, final @PathVariable Long commentId) {
        commentService.delete(postId, commentId);
        return new SuccessResponse<>(commentId);
    }
}
