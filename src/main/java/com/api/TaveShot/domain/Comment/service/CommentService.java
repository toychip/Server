package com.api.TaveShot.domain.Comment.service;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.Comment.dto.request.CommentCreateRequest;
import com.api.TaveShot.domain.Comment.dto.request.CommentUpdateRequest;
import com.api.TaveShot.domain.Comment.dto.response.CommentResponse;
import com.api.TaveShot.domain.Comment.repository.CommentRepository;
import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Post.domain.Post;
import com.api.TaveShot.domain.Post.repository.PostRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional // 데이터 변경하는 메서드에만 명시적으로 적용
    public Long save(Long postId, CommentCreateRequest dto) {
        Member currentMember = SecurityUtil.getCurrentMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .member(currentMember)
                .post(post)
                .parentComment(null) // 대댓글이 아닌 경우 parentComment는 null
                .build();
        commentRepository.save(comment);

        return comment.getId();
    }

    public List<CommentResponse> findAll(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));
        List<Comment> comments = commentRepository.findByPostAndParentCommentIsNullOrderById(post);
        return comments.stream()
                .map(comment -> CommentResponse.fromEntity(comment))
                .toList();
    }

    @Transactional
    public void update(Long postId, Long commentId, CommentUpdateRequest dto) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + commentId));

        comment.update(dto.getComment());
    }

    @Transactional
    public void delete(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));

        commentRepository.delete(comment);
    }

    @Transactional
    public Long saveReply(Long postId, Long parentCommentId, CommentCreateRequest dto) {
        Member currentMember = SecurityUtil.getCurrentMember();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));

        Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() ->
                new IllegalArgumentException("부모 댓글이 존재하지 않습니다. id=" + parentCommentId));

        Comment replyComment = Comment.builder()
                .comment(dto.getComment())
                .member(currentMember)
                .post(post)
                .parentComment(parentComment)
                .build();
        commentRepository.save(replyComment);

        return replyComment.getId();
    }

    public List<CommentResponse> findAllWithReplies(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType._POST_NOT_FOUND));

        List<Comment> topLevelComments = commentRepository.findByPostAndParentCommentIsNullOrderById(post);

        return topLevelComments.stream()
                .map(comment -> CommentResponse.fromEntity(comment))
                .collect(Collectors.toList());
    }
}
