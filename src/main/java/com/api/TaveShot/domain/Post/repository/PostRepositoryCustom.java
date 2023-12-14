package com.api.TaveShot.domain.Post.repository;

import com.api.TaveShot.domain.Post.dto.PostResponse;
import com.api.TaveShot.domain.Post.dto.PostSearchCondition;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    List<PostResponse> searchPagePost(PostSearchCondition condition, Pageable pageable);
}
