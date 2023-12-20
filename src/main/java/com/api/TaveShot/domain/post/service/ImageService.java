package com.api.TaveShot.domain.post.service;

import com.api.TaveShot.domain.post.domain.Post;
import com.api.TaveShot.domain.post.domain.Image;
import com.api.TaveShot.domain.post.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void register(Post post, String imageUrl) {
        Image image = Image.builder()
                .post(post)
                .imageUrl(imageUrl)
                .build();
        imageRepository.save(image);
    }
}
