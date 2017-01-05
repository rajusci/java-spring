package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.dictionary.ImageType;
import com.inspirenetz.api.core.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ImageService extends BaseService<Image> {

    public Image findByImgImageId(Long imgImageId);
    public String getImagePathForTimestamp(Timestamp timestamp);
    public String getPathForImage(Image image, String imgPathType);
    public String getDefaultImagePathForType(int imgType);
    public HashMap<String,String> getUploadPathForImage(int imgType,String datePath);
    public boolean saveImageByType(BufferedImage originalImage,Image image,String imagePathType);
    public BufferedImage getResizedImage(BufferedImage originalImage, int type,int width,int height);
    public String getPathForImageId(Long imgImageId,String pathType);
    public Dimension getImageDimensionForPathType(String imagePathType);

    public Image saveImage(Image image);
    public boolean deleteImage(Long imgImageId);

}
