package com.api.TaveShot.domain.Comment.service;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.Comment.domain.CommentRepository;
import com.api.TaveShot.domain.Comment.dto.CommentDto;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.domain.PostRepository;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /* CREATE */
    @Transactional
    public Long save(Long id, Long gitLoginId, CommentDto.Request dto) {
        Member currentMember = SecurityUtil.getCurrentMember();
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + id));

        dto.setMember(currentMember);
        dto.setPost(post);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return comment.getId();
    }

    /* READ */
    @Transactional(readOnly = true)
    public List<CommentDto.Response> findAll(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<Comment> comments = post.getComments();
        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }

    /* UPDATE */
    @Transactional
    public void update(Long postId, Long id, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPostIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.update(dto.getComment());
    }

    /* DELETE */
    @Transactional
    public void delete(Long postId, Long id) {
        Comment comment = commentRepository.findByPostIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }

    @Transactional
    public Long saveReply(Long postId, Long parentId, Long gitLoginId, CommentDto.Request dto) {
        Member currentMember = SecurityUtil.getCurrentMember();

        // 부모 댓글 가져오기
        Comment parentComment = commentRepository.findByPostIdAndId(postId, parentId).orElseThrow(() ->
                new IllegalArgumentException("부모 댓글이 존재하지 않습니다. id=" + parentId));

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + postId));

        dto.setMember(currentMember);
        dto.setPost(post);
        dto.setParentComment(parentComment);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return comment.getId();
    }
    @Transactional(readOnly = true)
    public List<CommentDto.ResponseWithReplies> findAllWithReplies(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + postId));

        List<Comment> topLevelComments = commentRepository.findByPostAndParentCommentIsNullOrderById(post);

        return topLevelComments.stream()
                .map(topLevelComment -> {
                    List<Comment> replies = commentRepository.findByParentCommentOrderById(topLevelComment);
                    return new CommentDto.ResponseWithReplies(topLevelComment, replies);
                })
                .collect(Collectors.toList());
    }
}