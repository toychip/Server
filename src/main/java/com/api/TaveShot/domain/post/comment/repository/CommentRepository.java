package com.api.TaveShot.domain.post.comment.repository;

import com.api.TaveShot.domain.post.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> , CommentRepositoryCustom {
}