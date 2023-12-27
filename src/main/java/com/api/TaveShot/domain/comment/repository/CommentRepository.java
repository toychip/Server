package com.api.TaveShot.domain.comment.repository;

import com.api.TaveShot.domain.comment.domain.Comment;
import com.api.TaveShot.domain.post.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> findByPost(Post post);

    Page<Comment> findByPostOrderByCreated(Post post, Pageable pageable);

    List<Comment> findByParentCommentIsNull(Post post);

    List<Comment> findByParentComment(Comment parentComment);

    Optional<Comment> findByPostIdAndId(Long postId, Long commentId);
}