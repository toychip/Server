package com.api.TaveShot.domain.Post.controller;

import com.api.TaveShot.domain.Post.dto.PostDto;
import com.api.TaveShot.domain.Post.service.PostService;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostIndexController {

    private final PostService postService;

    @GetMapping("/postlist")
    public ResponseEntity<Page<PostDto.Response>> index(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto.Response> list = postService.pageList(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/post/read/{id}")
    public ResponseEntity<PostDto.Response> read(@PathVariable Long id) {
        PostDto.Response dto = postService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/post/write")
    public ResponseEntity<String> write() {
        // 현재 로그인한 사용자의 gitLoginId 가져오기
        String currentUserId = SecurityUtil.getCurrentMember().getGitLoginId();
        return ResponseEntity.ok(currentUserId);
    }

    @GetMapping("/post/update/{id}")
    public ResponseEntity<PostDto.Response> update(@PathVariable Long id) {
        PostDto.Response dto = postService.findById(id);

        // 현재 로그인한 사용자의 gitLoginId 가져오기
        String currentUserId = SecurityUtil.getCurrentMember().getGitLoginId();

        // 게시글 작성자의 아이디
        String writerId = dto.getWriterId().toString();

        // 현재 로그인한 사용자와 게시글 작성자를 비교하여 권한 확인
        if (currentUserId.equals(writerId)) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/post/search")
    public ResponseEntity<Page<PostDto.Response>> search(@RequestParam String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto.Response> searchList = postService.search(keyword, pageable);
        return ResponseEntity.ok(searchList);
    }
}
