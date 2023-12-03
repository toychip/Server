package com.api.TaveShot.domain.Post.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.domain.PostRepository;
import com.api.TaveShot.domain.Post.dto.PostDto;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /* CREATE */
    @Transactional
    public Long save(PostDto.Request dto) {

        // 현재 로그인한 Member 정보 가져오기
        Member currentMember = SecurityUtil.getCurrentMember();

        // gitLoginId 가져오기
        String gitLoginId = currentMember.getGitLoginId();

        log.info("PostService save() 실행");
        Post post = dto.toEntity();
        post.setGitLoginId(gitLoginId);

        postRepository.save(post);

        return post.getId();
    }


    /* READ */
    @Transactional(readOnly = true)
    public PostDto.Response findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new PostDto.Response(post);
    }

    /* UPDATE */
    @Transactional
    public void update(Long id, PostDto.Request dto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        post.update(dto.getTitle(), dto.getContent(),dto.getAttachmentFile());
    }

    /* DELETE (영구 삭제 안되도록 어쩌구는 추후에 다시..) */
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        postRepository.delete(post);
    }

    /* Views Counting */
    @Transactional
    public int updateView(Long id) {
        return postRepository.updateView(id);
    }

    /* Paging and Sort */
    @Transactional(readOnly = true)
    public Page<PostDto.Response> pageList(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostDto.Response::new);
    }

    /* search */
    @Transactional(readOnly = true)
    public Page<PostDto.Response> search(String keyword, Pageable pageable) {
        Page<Post> searchResult = postRepository.findByTitleContaining(keyword, pageable);
        return searchResult.map(PostDto.Response::new);
    }

}
