package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.repository.ImageRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class ImageRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(ImageRepositoryTest.class);

    @Autowired
    private ImageRepository imageRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Image image = imageRepository.save(ImageFixture.standardImage());
        log.info(image.toString());
        Assert.assertNotNull(image.getImgImageId());

    }

    @Test
    public void test2Update() {

        Image image = ImageFixture.standardImage();
        image = imageRepository.save(image);
        log.info("Original Image " + image.toString());

        Image updatedImage = ImageFixture.updatedStandardImage(image);
        updatedImage = imageRepository.save(updatedImage);
        log.info("Updated Image "+ updatedImage.toString());

    }


    @Test
    public void test3FindByImgImageId() {

        Image image = ImageFixture.standardImage();
        image = imageRepository.save(image);
        log.info("Original Image " + image.toString());


        Image searchImage = imageRepository.findByImgImageId(image.getImgImageId());
        Assert.assertNotNull(searchImage);
        log.info("Found Image " + searchImage.toString());

    }



}
