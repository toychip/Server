package com.api.TaveShot.domain.post.post.controller;

import com.api.TaveShot.domain.post.post.dto.request.PostCreateRequest;
import com.api.TaveShot.domain.post.post.dto.request.PostEditRequest;
import com.api.TaveShot.domain.post.post.dto.request.PostSearchCondition;
import com.api.TaveShot.domain.post.post.dto.response.PostListResponse;
import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.domain.post.post.service.PostService;
import com.api.TaveShot.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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

    @Operation(summary = "새로운 게시글 생성", description = "새로운 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = PostResponse.class)))
    })
    @PostMapping("/post")
    public SuccessResponse<PostResponse> register(final @Validated @ModelAttribute PostCreateRequest request) {
        PostResponse postResponse = postService.register(request);
        return new SuccessResponse<>(postResponse);
    }

    /* READ */
    @Operation(summary = "단일 게시글 조회", description = "게시글 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class)))
    })
    @GetMapping("/post/{postId}")
    public SuccessResponse<PostResponse> getSinglePost(final @PathVariable Long postId) {
        PostResponse postResponse = postService.getSinglePost(postId);
        return new SuccessResponse<>(postResponse);
    }

    @Operation(summary = "게시글 페이지 조회",
            description = "지정된 조건에 따라 게시글을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 페이지 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostListResponse.class)))
    })
    @GetMapping("/post")
    public SuccessResponse<PostListResponse> getPagePost(final @Validated @ModelAttribute PostSearchCondition condition,
                                                         final Pageable pageable) {
        PostListResponse postListResponse = postService.searchPostPaging(condition, pageable);
        return new SuccessResponse<>(postListResponse);
    }

    /* UPDATE */
    @Operation(summary = "게시글 수정",
            description = "게시글 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PatchMapping("/post/{postId}")
    public SuccessResponse<Long> edit(final @PathVariable Long postId,
                                        final @RequestBody PostEditRequest request) {
        postService.edit(postId, request);
        return new SuccessResponse<>(postId);
    }

    /* DELETE */
    @Operation(summary = "게시글 삭제",
            description = "게시글 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @DeleteMapping("/post/{postId}")
    public SuccessResponse<Long> delete(final @PathVariable Long postId) {
        postService.delete(postId);
        return new SuccessResponse<>(postId);
    }


}