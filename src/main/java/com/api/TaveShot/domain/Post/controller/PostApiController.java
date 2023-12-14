package com.api.TaveShot.domain.Post.controller;

import com.api.TaveShot.domain.Post.dto.PostCreateRequest;
import com.api.TaveShot.domain.Post.dto.PostDto;
import com.api.TaveShot.domain.Post.dto.PostResponse;
import com.api.TaveShot.domain.Post.service.PostService;
import com.api.TaveShot.global.success.SuccessResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public SuccessResponse<PostResponse> save(@RequestBody PostCreateRequest request) {
        PostResponse postResponse = postService.save(request);
        return new SuccessResponse<>(postResponse);
    }

    /* READ */
    @GetMapping("/post/{postId}")
    public SuccessResponse<PostResponse> read(@PathVariable Long postId) {
        PostResponse postResponse = postService.findById(postId);
        return new SuccessResponse<>(postResponse);
    }

    /* UPDATE */
    @PutMapping("/post/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody PostDto.Request dto) {
        postService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Long> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok(postId);
    }

    @GetMapping("/postlistWithCommentCount")
    public List<PostDto.Response> postlistWithCommentCount() {
        return postService.findAllWithCommentCount();
    }


}