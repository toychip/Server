package com.api.TaveShot.domain.post.post.repository;

import com.api.TaveShot.domain.post.post.domain.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

    @EntityGraph(attributePaths = {"image"})
    Optional<Post> findPostFetchJoin(Long id);
}