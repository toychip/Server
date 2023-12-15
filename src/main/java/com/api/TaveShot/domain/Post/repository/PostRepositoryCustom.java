package com.api.TaveShot.domain.Post.repository;

import com.api.TaveShot.domain.Post.dto.response.PostResponse;
import com.api.TaveShot.domain.Post.dto.request.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostResponse> searchPagePost(final PostSearchCondition condition, final Pageable pageable);
}
