package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.service.FileUploadService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class FileUploadControllerTest {


    private static Logger log = LoggerFactory.getLogger(FileUploadControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private FileUploadService brandService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;






    @Before
    public void setUp()
    {
        try {

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();



    }




    @Test
    public void handleImageUpload() throws Exception {

        FileInputStream fis = new FileInputStream("/home/sandheepgr/Downloads/ethnic.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);


        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/image")
                .file(multipartFile)
                .param("username", "smartuser")
                .param("authentication", "cee380e02daeee6eeb964032d115d56b")
                .param("imagetype", "8")
                .param("filename","ethnic.jpg")
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        log.info("FileUploadResponse: " + response);


    }

    @Test
    public void handleFileUpload() throws Exception {

        FileInputStream fis = new FileInputStream("/home/saneesh-ci/test123.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);


        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/merchant/vouchersorce/file")
                .file(multipartFile)
                .param("username", "smartuser")
                .param("authentication", "69662c06ac6600faf600d7a2923844fe")
                .param("fileType", "1")
                .param("filename","test123.csv")
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        log.info("FileUploadResponse: " + response);


    }

}
