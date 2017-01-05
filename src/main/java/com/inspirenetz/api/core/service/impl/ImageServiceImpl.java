package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.ImageRepository;
import com.inspirenetz.api.core.service.FileUploadService;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<Image> implements ImageService {

    private static Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);


    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private FileUploadService fileUploadService;



    public ImageServiceImpl() {

        super(Image.class);

    }


    @Override
    protected BaseRepository<Image,Long> getDao() {
        return imageRepository;
    }



    @Override
    public Image findByImgImageId(Long imgImageId) {

        // Get the image for the given image id
        Image image = imageRepository.findByImgImageId(imgImageId);

        // Return the image
        return image;

    }

    @Override
    public String getImagePathForTimestamp(Timestamp timestamp) {

        // Create the Dateformat object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        // Format the timestamp to the defined format
        String datePath = dateFormat.format(timestamp);

        // Return the datePath
        return datePath;

    }

    @Override
    public String getPathForImage(Image image, String imgPathType) {

        // Variable holding the imagePath
        String imagePath = "";

        // Check if the imgImageId is less than 50, then its a primary image id
        // and we need to return the default image for the type
        if  ( image.getImgImageId() < 50L ) {

            imagePath = getDefaultImagePathForType(image.getImgImageType());

        } else {

            // Get the datePath
            String datePath = getImagePathForTimestamp(image.getImgTimestamp());

            // Get the pathMap
            HashMap<String,String> pathMap = getUploadPathForImage(image.getImgImageType(),datePath);

            // Return the imagePathType requested
            imagePath = pathMap.get(imgPathType)  + image.getImgImageId().toString() + "."+image.getImgImageExt();

        }

        // Return the imagePath
        return imagePath;

    }

    @Override
    public HashMap<String,String> getUploadPathForImage(int imgType,String datePath) {

        // Get the imageProperties
        Properties imgProperties = ImageUtils.getImageProperites();

        // Get the path
        String path = "";

        // Check the passed imgType and get the path
        if ( imgType == ImageType.MERCHANT_LOGO ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_LOGOS_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_REWARD_CURRENCY ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_REWARD_CURRENCY_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_CATALOGUE ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_CATALOGUE_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_PROMOTION ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_PROMOTION_IMAGE_PATH);

        }  else if ( imgType == ImageType.MERCHANT_LOYALTY_PROGRAM ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_LOYALTY_PROGRAM_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_PURCHASE_AVAILED_SERVICE_IMAGE ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_PURCHASE_AVAILED_SERVICE_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_SERVICE_IMAGE ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_SERVICE_IMAGE_PATH);

        } else if ( imgType == ImageType.COUPON_IMAGE ) {

            path += imgProperties.getProperty(ImagePath.COUPON_IMAGE_PATH);

        } else if ( imgType == ImageType.MILESTONE_IMAGE ) {

            path += imgProperties.getProperty(ImagePath.MILESTONE_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_VENDOR ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_VENDOR_IMAGE_PATH);

        } else if ( imgType == ImageType.CATALOGUE_CATEGORY ) {

            path += imgProperties.getProperty(ImagePath.CATALOGUE_CATEGORY_IMAGE_PATH);

        } else if ( imgType == ImageType.MERCHANT_COVER_IMAGE ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_COVER_IMAGE_PATH);

        } else if ( imgType == ImageType.SUPER_ADMIN_PROFILE_PIC ) {

            path += imgProperties.getProperty(ImagePath.SUPER_ADMIN_PROFILE_PIC_PATH);

        } else if ( imgType == ImageType.ADMIN_USER_PROFILE_PIC ) {

            path += imgProperties.getProperty(ImagePath.ADMIN_USER_PROFILE_PIC_PATH);

        } else if ( imgType == ImageType.MERCHANT_ADMIN_PROFILE_PIC ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_ADMIN_PROFILE_PIC_PATH);

        } else if ( imgType == ImageType.MERCHANT_USER_PROFILE_PIC ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_USER_PROFILE_PIC_PATH);

        } else if ( imgType == ImageType.CUSTOMER_PROFILE_PIC ) {

            path += imgProperties.getProperty(ImagePath.CUSTOMER_PROFILE_PIC_PATH);

        } else if ( imgType == ImageType.TIER_IMAGE ) {

            path += imgProperties.getProperty(ImagePath.MERCHANT_TIER_IMAGE_PATH);

        }

        // Append the datepath
        path += datePath;

        // Create the HashMap holding the different path types and the corresponding paths
        HashMap<String,String> pathMap = new HashMap<>();

        // Set the standard path
        pathMap.put(ImagePathType.STANDARD,path + "/" + ImagePathType.STANDARD + "/");

        // Set the mobile path
        pathMap.put(ImagePathType.MOBILE,path + "/" + ImagePathType.MOBILE + "/");

        // Set the thumbnail path
        pathMap.put(ImagePathType.THUMBNAIL,path + "/" + ImagePathType.THUMBNAIL + "/");



        // Return the map object
        return pathMap;

    }

    @Override
    public String getDefaultImagePathForType(int imgType) {

        // Read the properties file
        Properties imgProperties = ImageUtils.getImageProperites();

        // Check the imageType and return the default image for that
        if (    imgType == ImageType.SUPER_ADMIN_PROFILE_PIC  ||
                imgType == ImageType.ADMIN_USER_PROFILE_PIC ||
                imgType == ImageType.MERCHANT_ADMIN_PROFILE_PIC ||
                imgType == ImageType.MERCHANT_USER_PROFILE_PIC ||
                imgType == ImageType.CUSTOMER_PROFILE_PIC ) {

                return imgProperties.getProperty(ImageDefaultPath.PROFILE_PIC);


         } else if ( imgType == ImageType.MERCHANT_LOGO ) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_LOGO);

        } else if ( imgType == ImageType.MERCHANT_REWARD_CURRENCY ) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_REWARD_CURRENCY);

        } else if ( imgType == ImageType.MERCHANT_CATALOGUE ) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_CATALOGUE);

        } else if ( imgType == ImageType.MERCHANT_PROMOTION) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_PROMOTION);

        } else if ( imgType == ImageType.MERCHANT_LOYALTY_PROGRAM) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_LOYALTY_PROGRAM);

        } else if ( imgType == ImageType.MERCHANT_PURCHASE_AVAILED_SERVICE_IMAGE) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_PURCHASE_AVAILED_SERVICE_IMAGE);

        } else if ( imgType == ImageType.MERCHANT_SERVICE_IMAGE) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_SERVICE_IMAGE);

        } else if ( imgType == ImageType.COUPON_IMAGE) {

            return imgProperties.getProperty(ImageDefaultPath.COUPON_IMAGE);

        } else if ( imgType == ImageType.MILESTONE_IMAGE) {

            return imgProperties.getProperty(ImageDefaultPath.MILESTONE_IMAGE);

        } else if ( imgType == ImageType.MERCHANT_VENDOR) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_VENDOR);

        } else if ( imgType == ImageType.CATALOGUE_CATEGORY) {

            return imgProperties.getProperty(ImageDefaultPath.CATALOGUE_CATEGORY);

        } else if ( imgType == ImageType.MERCHANT_COVER_IMAGE) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_COVER_IMAGE);

        } else if ( imgType == ImageType.ALERT) {

            return imgProperties.getProperty(ImageDefaultPath.ALERT);

        } else if ( imgType == ImageType.TIER_IMAGE) {

            return imgProperties.getProperty(ImageDefaultPath.MERCHANT_TIER_IMAGE);

        }


        return "";

    }

    @Override
    public String getPathForImageId(Long imgImageId,String pathType) {

        // Get the Image object for the given image Id
        Image image = imageRepository.findByImgImageId(imgImageId);

        // if the image is not found, then return empty string
        if ( image == null ) {

            return "";

        }


        // Get the path for the for the image and the given path type
        String path = getPathForImage(image,pathType);

        // Return the path
        return path;

    }

    @Override
    public BufferedImage getResizedImage(BufferedImage originalImage, int type,int width,int height) {

        // Create the new Buffered image with the specified dimensions and type
        BufferedImage resizedImage = new BufferedImage(width, height, type);

        // Get the graphcis object
        Graphics2D g = resizedImage.createGraphics();

        // Draw the image with specified withd and height
        g.drawImage(originalImage, 0, 0, width, height, null);

        // Release the resources
        g.dispose();

        // Return the resized image
        return resizedImage;
    }

    @Override
    public Dimension getImageDimensionForPathType(String imagePathType) {

        // Check the imagePathType
        if ( imagePathType.equals(ImagePathType.STANDARD )) {

            return new Dimension(800,600);

        } else if ( imagePathType.equals(ImagePathType.MOBILE) ) {

            return new Dimension(300,300);

        } else if ( imagePathType.equals(ImagePathType.THUMBNAIL) ) {

            return new Dimension(50,50);

        }


        // finally return the dimension as 500/500 default
        return new Dimension(500,500);

    }

    @Override
    public boolean saveImageByType(BufferedImage originalImage,Image image,String imagePathType) {

        // Get the standardPath
        String standardPath = getPathForImage(image,imagePathType);

        // Get the dimension for the imagepathType
        Dimension imgDimension = getImageDimensionForPathType(imagePathType);

        // Get the type for the BufferedImage
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        // Get the image
        BufferedImage bufImage = getResizedImage(originalImage, type, imgDimension.width, imgDimension.height);

        // Save the image
        try {

            // Get the uploadRoot
            String uploadRoot = fileUploadService.getFileUploadPathForType(FileUploadPath.UPLOAD_ROOT);

            // Create the File object
            File file = new File(uploadRoot+"/"+standardPath);

            // If the parent file does not exists, then create the path
            if ( !file.getParentFile().exists() ) {

                file.getParentFile().mkdirs();

            }


            // Write the image
            ImageIO.write(bufImage,image.getImgImageExt(),file);


        } catch (IOException e) {

            e.printStackTrace();

            return false;
        }


        // finally return true
        return true;

    }



    @Override
    public Image saveImage(Image image ){

        // Save the image
        return imageRepository.save(image);

    }

    @Override
    public boolean deleteImage(Long brnId) {

        // Delete the image
        imageRepository.delete(brnId);

        // return true
        return true;

    }
}
