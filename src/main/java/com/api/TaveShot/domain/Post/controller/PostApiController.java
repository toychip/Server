package com.api.TaveShot.domain.Post.controller;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.dto.PostDto;
import com.api.TaveShot.domain.Post.service.PostService;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/** REST API Controller **/
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    /* CREATE : PostService 내부에서 현재 로그인한 사용자의 gitLoginID 정보를 가져와 사용*/
    @PostMapping("/post")
    public ResponseEntity<Long> save(@RequestBody PostDto.Request dto) {
        // 게시글 저장
        return ResponseEntity.ok(postService.save(dto));
    }

    /* READ */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto.Response> read(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    /* UPDATE */
    @PutMapping("/post/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody PostDto.Request dto) {
        postService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(id);
    }

}