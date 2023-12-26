package com.api.TaveShot.domain.post.image.converter;

import com.api.TaveShot.domain.post.image.domain.Image;
import com.api.TaveShot.domain.post.image.dto.ImageResponse;
import java.util.List;

public class ImageConverter {
    public static List<ImageResponse> imageToImageResponse(List<Image> images) {
        return images.stream()
                .map(ImageResponse::new)
                .toList();
    }
}
