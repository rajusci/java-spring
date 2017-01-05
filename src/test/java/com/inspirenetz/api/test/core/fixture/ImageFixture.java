package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.ImageType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.test.core.builder.ImageBuilder;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 13/5/14.
 */
public class ImageFixture {

    public static Image standardImage() {

        Image image = ImageBuilder.anImage()
               .withImgImageExt("jpg")
                .withImgImageType(ImageType.COUPON_IMAGE)
                .withImgInUseIndicator(IndicatorStatus.YES)
                .withImgTimestamp(new Timestamp(new Date().getTime()))
                .build();

        return image;

    }



    public static Image updatedStandardImage(Image image) {

        image.setImgInUseIndicator(IndicatorStatus.NO);
        return image;

    }

}

