package com.api.TaveShot.domain.post.repository;

import com.api.TaveShot.domain.post.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
