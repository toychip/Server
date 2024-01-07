package com.api.TaveShot.domain.post.post.repository;

import com.api.TaveShot.domain.post.post.domain.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {

    @Query("select p from Post p where p.id = :id")
    @EntityGraph(attributePaths = {"images"})
    Optional<Post> findPostFetchJoin(Long id);
}