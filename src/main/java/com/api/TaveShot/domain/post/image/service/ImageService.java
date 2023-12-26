package com.api.TaveShot.domain.post.image.service;

import com.api.TaveShot.domain.post.post.domain.Post;
import com.api.TaveShot.domain.post.image.domain.Image;
import com.api.TaveShot.domain.post.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void register(final Post post, final String imageUrl) {
        Image image = Image.builder()
                .post(post)
                .imageUrl(imageUrl)
                .build();
        imageRepository.save(image);
    }

    @Transactional
    public void deleteAllByPost(Post post) {
        imageRepository.deleteByPost(post);
    }
}
