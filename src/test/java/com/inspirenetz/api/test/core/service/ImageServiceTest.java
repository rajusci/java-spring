package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.dictionary.ImageType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.repository.ImageRepository;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.ImageFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class ImageServiceTest {


    private static Logger log = LoggerFactory.getLogger(ImageServiceTest.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;


    @Before
    public void setUp() {}


    @Test
    public void test1FindByImgImageId() {

        Image image = ImageFixture.standardImage();
        image = imageService.saveImage(image);
        log.info("Original Image " + image.toString());


        Image searchImage = imageRepository.findByImgImageId(image.getImgImageId());
        Assert.assertNotNull(searchImage);
        log.info("Found Image " + searchImage.toString());

    }


    @Test
    public void test2GetImagePathForTimestamp() {

        Timestamp timestamp = new Timestamp(new Date().getTime());

        String path = imageService.getImagePathForTimestamp(timestamp);

        log.info("Path is " + path);

    }


    @Test
    public void test3GetPathForImage() {

        Image image = ImageFixture.standardImage();
        image = imageService.saveImage(image);

        String imagePath  = imageService.getPathForImage(image, ImagePathType.STANDARD);
        log.info("ImagePath :" +  imagePath);

    }


    @Test
    public void test4GetDefaultImagePathForType() {

        Image image = ImageFixture.standardImage();
        String path = imageService.getDefaultImagePathForType(image.getImgImageType());
        log.info("Path is :" + path);

    }


    @Test
    public void test5GetUploadPathForImage() {

        Timestamp timestamp = new Timestamp(new Date().getTime());
        String datePath = imageService.getImagePathForTimestamp(timestamp);

        HashMap<String,String> pathMap = imageService.getUploadPathForImage(ImageType.COUPON_IMAGE,datePath);
        Assert.assertTrue(!pathMap.isEmpty());
        log.info("PathMap : " + pathMap.toString());


    }


    @Test
    public void test6GetPathForImageId() {

        Image image = ImageFixture.standardImage();
        image = imageService.saveImage(image);
        String path = imageService.getPathForImageId(image.getImgImageId(),ImagePathType.STANDARD);
        Assert.assertTrue(!path.isEmpty());
        log.info("Path is "+path);

    }

    @Test
    public void test7GetImageDimensionForPathType() {

        Dimension dimension = imageService.getImageDimensionForPathType(ImagePathType.STANDARD);
        Assert.assertNotNull(dimension);
        log.info("Dimension : " + dimension.toString());

    }


}
