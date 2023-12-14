package com.api.TaveShot.domain.Post.domain;

import com.api.TaveShot.domain.Post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    int updateView(Long id);

    @Query("SELECT new com.api.TaveShot.domain.Post.dto.PostDto.Response(p) FROM Post p LEFT JOIN p.comments c GROUP BY p.id")
    List<PostDto.Response> findAllWithCommentCount();



    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}