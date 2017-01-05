package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.SegmentMemberFixture;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class SegmentMemberControllerTest {


    private static Logger log = LoggerFactory.getLogger(SegmentMemberControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // SegmentMember object
    private SegmentMember segmentMember;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the segmentMember
        segmentMember = SegmentMemberFixture.standardSegmentMember();


    }



    @Test
    public void saveSegmentMember() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/segmentation/segmentmember")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("sgmSegmentId",segmentMember.getSgmSegmentId().toString())
                                                .param("sgmCustomerNo",segmentMember.getSgmCustomerNo().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SegmentMemberResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("SegmentMember created");


    } 
    
    @Test
    public void deleteSegmentMember() throws  Exception {

        // Create the segmentMember
        segmentMember = segmentMemberService.saveSegmentMember(segmentMember);
        Assert.assertNotNull(segmentMember.getSgmId());
        log.info("SegmentMember created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/segmentation/segmentmember/delete/" + segmentMember.getSgmId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SegmentMemberResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("SegmentMember deleted");


    }

    @Test
    public void getSegmentMembersBySegmentId() throws Exception  {

        // Get the set of segmentMembers
        Set<SegmentMember> segmentMemberSet = SegmentMemberFixture.standardSegmentMembers();
        List<SegmentMember>  segmentMemberList = new ArrayList<SegmentMember>();
        segmentMemberList.addAll(segmentMemberSet);
        segmentMemberService.saveAll(segmentMemberList);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/segmentation/segmentmember/segment/"+segmentMember.getSgmSegmentId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SegmentMemberResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getSegmentMembersByCustomerNo() throws Exception  {

        // Get the set of segmentMembers
        Set<SegmentMember> segmentMemberSet = SegmentMemberFixture.standardSegmentMembers();
        List<SegmentMember>  segmentMemberList = new ArrayList<SegmentMember>();
        segmentMemberList.addAll(segmentMemberSet);
        segmentMemberService.saveAll(segmentMemberList);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/segmentation/segmentmember/customer/"+segmentMember.getSgmCustomerNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SegmentMemberResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() throws Exception {

        Set<SegmentMember> segmentMembers = SegmentMemberFixture.standardSegmentMembers();

        for(SegmentMember segmentMember: segmentMembers) {

            SegmentMember delSegmentMember = segmentMemberService.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(),segmentMember.getSgmCustomerNo());

            if ( delSegmentMember != null ) {
                segmentMemberService.deleteSegmentMember(delSegmentMember.getSgmId());
            }

        }
    }

}
