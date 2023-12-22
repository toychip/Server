package com.api.TaveShot.domain.Comment.repository;

import com.api.TaveShot.domain.Comment.domain.Comment;
import com.api.TaveShot.domain.post.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPost(Post post);

    List<Comment> findByParentCommentIsNull(Post post);

    List<Comment> findByParentComment(Comment parentComment);

    Optional<Comment> findByPostIdAndId(Long postId, Long commentId);

    Page<Comment> findByParentCommentIsNull(Post post, Pageable pageable);
}