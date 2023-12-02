package com.api.TaveShot.domain.Post.controller;

import com.api.TaveShot.domain.Post.dto.PostDto;
import com.api.TaveShot.domain.Post.service.PostService;
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

    /* CREATE : 게시글을 현재 로그인한 사용자의 gitID를 가져와 관련된 정보로 저장 */
    @PostMapping("/post")
    public ResponseEntity<Long> save(@RequestBody PostDto.Request dto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();
        String gitIdAsString = authentication.getName();

        Long gitId = Long.valueOf(gitIdAsString);

        return ResponseEntity.ok(postService.save(dto, gitId));
    }

    /* READ */
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto.Response> read(@PathVariable Long id) {
        PostDto.Response postResponse = postService.findById(id);
        return ResponseEntity.ok(postResponse);
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