package com.api.TaveShot.domain.post.post.repository;

import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.domain.post.post.dto.request.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostResponse> searchPagePost(final PostSearchCondition condition, final Pageable pageable);
}
