package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.FileUploadPath;
import com.inspirenetz.api.core.service.FileUploadService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class FileUploadServiceTest {


    private static Logger log = LoggerFactory.getLogger(FileUploadServiceTest.class);

    @Autowired
    private FileUploadService fileUploadService;


    @Before
    public void setUp() {}


    @Test
    public void getExtension() {

        String extension = fileUploadService.getExtension("my file.png");
        Assert.assertNotNull(extension);
        log.info("Extension is " + extension);


    }



    @Test
    public void getTempFile() {

        // Get the temp file
        File tempFile = fileUploadService.getTempFile("my file.png");
        Assert.assertNotNull(tempFile);
        log.info("File is " + tempFile.toString());
    }



    @Test
    public void getUploadRoot() {

        // Get the root file
        String uploadRoot = fileUploadService.getFileUploadPathForType(FileUploadPath.UPLOAD_ROOT);
        Assert.assertNotNull(uploadRoot);
        log.info("Upload Root : " + uploadRoot);

    }

}
