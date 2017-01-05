package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Image;

import java.sql.Timestamp;

/**
 * Created by sandheepgr on 13/5/14.
 */
public class ImageBuilder {
    private Long imgImageId;
    private String imgImageExt;
    private int imgImageType;
    private Timestamp imgTimestamp;
    private int imgInUseIndicator;

    private ImageBuilder() {
    }

    public static ImageBuilder anImage() {
        return new ImageBuilder();
    }

    public ImageBuilder withImgImageId(Long imgImageId) {
        this.imgImageId = imgImageId;
        return this;
    }

    public ImageBuilder withImgImageExt(String imgImageExt) {
        this.imgImageExt = imgImageExt;
        return this;
    }

    public ImageBuilder withImgImageType(int imgImageType) {
        this.imgImageType = imgImageType;
        return this;
    }

    public ImageBuilder withImgTimestamp(Timestamp imgTimestamp) {
        this.imgTimestamp = imgTimestamp;
        return this;
    }

    public ImageBuilder withImgInUseIndicator(int imgInUseIndicator) {
        this.imgInUseIndicator = imgInUseIndicator;
        return this;
    }

    public Image build() {
        Image image = new Image();
        image.setImgImageId(imgImageId);
        image.setImgImageExt(imgImageExt);
        image.setImgImageType(imgImageType);
        image.setImgTimestamp(imgTimestamp);
        image.setImgInUseIndicator(imgInUseIndicator);
        return image;
    }
}
