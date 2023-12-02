package com.api.TaveShot.domain.Post.controller;

import com.api.TaveShot.domain.Comment.dto.CommentDto;
import com.api.TaveShot.domain.Member.dto.response.AuthResponse;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.dto.PostDto;
import com.api.TaveShot.domain.Post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/** 화면 연결 Controller **/

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostIndexController {

    private final PostService postService;

    @GetMapping("/postlist")
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> list = postService.pageList(pageable);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            AuthResponse memberId = (AuthResponse) authentication.getPrincipal();
            model.addAttribute("memberId", memberId);
        }

        //페이징 처리 부분 (변수명 -> 프론트)
        model.addAttribute("post", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "index";
    }


    /* 글 상세보기 */
    @GetMapping("/post/read/{id}")
    public String read(@PathVariable Long id, HttpSession session, Model model) {
        PostDto.Response dto = postService.findById(id);
        List<CommentDto.Response> comments = dto.getComments();


        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        /* 사용자 관련 */
        AuthResponse memberId = (AuthResponse) session.getAttribute("memberId");
        if (memberId != null) {
            model.addAttribute("memberId", memberId);

            /* 게시글 작성자 본인인지 확인 */
            if (dto.getMemberId().equals(memberId.memberId())) {
                model.addAttribute("writer", true);
            }

            /* 댓글 작성자 본인인지 확인 */
            assert comments != null;
            if (comments.stream().anyMatch(s -> s.getMemberId().equals(memberId.memberId()))) {
                model.addAttribute("isWriter", true);
            }
        }

        postService.updateView(id); // views ++
        model.addAttribute("post", dto);
        return "특정 게시물 페이지";
    }


    @GetMapping("/post/write") //게시글 생성
    public String write(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            AuthResponse memberId = (AuthResponse) authentication.getPrincipal();
            model.addAttribute("memberId", memberId);
        }
        return "게시글 생성 페이지";
    }


    /* 해당 글의 작성자인 경우에만 수정 가능
      (dto.getUserId() : 게시글 작성자 ID, memberId.getId() : 로그인한 사용자 ID)*/
    @GetMapping("/posts/update/{id}") //게시글 수정
    public String update(@PathVariable Long id, Model model) {
        PostDto.Response dto = postService.findById(id);

        /* 사용자 관련 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            AuthResponse memberId = (AuthResponse) authentication.getPrincipal();
            model.addAttribute("memberId", memberId);
        }

        model.addAttribute("post", dto);

        return "게시글 수정 페이지";
    }

    @GetMapping("/posts/search") //게시글 검색
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable) {
        Page<Post> searchList = postService.search(keyword, pageable);

        //model

        return "검색 페이지";
    }

}
