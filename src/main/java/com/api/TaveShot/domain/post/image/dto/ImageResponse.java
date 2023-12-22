package com.api.TaveShot.domain.post.image.dto;

import com.api.TaveShot.domain.post.image.domain.Image;
import lombok.Getter;

@Getter
public class ImageResponse {
    private final Long imageId;
    private final String imageUrl;

    public ImageResponse(Image image) {
        imageId = image.getId();
        imageUrl = image.getImageUrl();
    }
}
