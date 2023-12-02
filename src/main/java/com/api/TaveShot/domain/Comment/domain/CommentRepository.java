package com.api.TaveShot.domain.Comment.domain;

import com.api.TaveShot.domain.Post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostOrderById(Post post);

    Optional<Comment> findByPostIdAndId(Long postId, Long id);
}