package com.api.TaveShot.domain.Post.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.domain.PostRepository;
import com.api.TaveShot.domain.Post.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /* CREATE */
    @Transactional
    public Long save(PostDto.Request dto, Long gitId) {
        /* Memeber 정보 -> dto에 담기. */
        Optional<Member> member = memberRepository.findByGitId(gitId);
        dto.setMember(member.orElse(null));

        log.info("PostsService save() 실행");
        Post post = dto.toEntity();
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
    public Page<Post> pageList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /* search */
    @Transactional(readOnly = true)
    public Page<Post> search(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword, pageable);
    }

}
