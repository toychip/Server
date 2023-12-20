package com.api.TaveShot.domain.post.image.service;

import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.image.domain.Image;
import com.api.TaveShot.domain.post.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void register(final Post post, final String imageUrl) {
        Image image = Image.builder()
                .post(post)
                .imageUrl(imageUrl)
                .build();
        imageRepository.save(image);
    }
}
