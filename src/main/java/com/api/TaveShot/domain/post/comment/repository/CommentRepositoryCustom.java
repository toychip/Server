package com.api.TaveShot.domain.post.comment.repository;

import com.api.TaveShot.domain.post.comment.domain.Comment;
import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findByPostId(Long postId);
}
