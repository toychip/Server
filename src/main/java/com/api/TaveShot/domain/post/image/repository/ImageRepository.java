package com.api.TaveShot.domain.post.image.repository;

import com.api.TaveShot.domain.post.image.domain.Image;
import com.api.TaveShot.domain.post.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteByPost(Post post);
}
