package com.api.TaveShot.domain.post.post.repository;

import com.api.TaveShot.domain.post.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {
}