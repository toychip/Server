package com.api.TaveShot.domain.comment.repository;

import com.api.TaveShot.domain.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}
