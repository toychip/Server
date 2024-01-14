package com.api.TaveShot.domain.comment.repository;

import com.api.TaveShot.domain.comment.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    List<Comment> findByPostId(Long postId);
}
