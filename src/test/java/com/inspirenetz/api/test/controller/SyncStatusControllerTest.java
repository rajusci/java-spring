package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.SyncType;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.core.service.SyncStatusService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MerchantFixture;
import com.inspirenetz.api.test.core.fixture.MerchantLocationFixture;
import com.inspirenetz.api.test.core.fixture.ModuleFixture;
import com.inspirenetz.api.test.core.fixture.SyncStatusFixture;
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
 * Created by fayizkci on 15/9/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class SyncStatusControllerTest {


    private static Logger log = LoggerFactory.getLogger(SyncStatusControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private SyncStatusService syncStatusService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantLocationService merchantLocationService;

    HashSet<SyncStatus> syncStatusHashSet=new HashSet<SyncStatus>();

    HashSet<Merchant> merchantHashSet=new HashSet<Merchant>();

    HashSet<MerchantLocation> merchantLocationsHashSet=new HashSet<MerchantLocation>();


    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Module object
    private Module module;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_ADMIN_USER_LOGINID,userDetailsService);

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

        // Create the module
        module = ModuleFixture.standardModule();


    }



    @Test
    public void listSyncStatus() throws Exception {

        Merchant merchant= MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant=merchantService.saveMerchant(merchant);
        merchantHashSet.add(merchant);

        MerchantLocation merchantLocation= MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelMerchantNo(merchant.getMerMerchantNo());
        merchantLocation=merchantLocationService.saveMerchantLocation(merchantLocation);
        merchantLocationsHashSet.add(merchantLocation);

        SyncStatus syncStatus1 = SyncStatusFixture.standardSyncStatus();
        syncStatus1.setSysType(SyncType.ITEMS);
        syncStatus1.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus1.setSysLocation(merchantLocation.getMelId());
        syncStatus1 = syncStatusService.saveSyncStatus(syncStatus1);
        syncStatusHashSet.add(syncStatus1);

        SyncStatus syncStatus2 =SyncStatusFixture.standardSyncStatus();
        syncStatus2.setSysType(SyncType.SALES);
        syncStatus2.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus2.setSysLocation(merchantLocation.getMelId());
        syncStatus2 = syncStatusService.saveSyncStatus(syncStatus2);
        syncStatusHashSet.add(syncStatus2);

        SyncStatus syncStatus3 =SyncStatusFixture.standardSyncStatus();
        syncStatus3.setSysType(SyncType.CUSTOMERS);
        syncStatus3.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus3.setSysLocation(merchantLocation.getMelId());
        syncStatus3 = syncStatusService.saveSyncStatus(syncStatus3);
        syncStatusHashSet.add(syncStatus3);

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sync/status")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("sysMerchantNo",syncStatus1.getSysMerchantNo().toString())
                                                .param("sysLocation",syncStatus1.getSysLocation().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Sync Status Response: " + response);


    }

    @Test
    public void listSyncStatusInfo() throws Exception {

        Merchant merchant= MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant=merchantService.saveMerchant(merchant);
        merchantHashSet.add(merchant);

        MerchantLocation merchantLocation= MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelMerchantNo(merchant.getMerMerchantNo());
        merchantLocation=merchantLocationService.saveMerchantLocation(merchantLocation);
        merchantLocationsHashSet.add(merchantLocation);

        SyncStatus syncStatus1 = SyncStatusFixture.standardSyncStatus();
        syncStatus1.setSysType(SyncType.ITEMS);
        syncStatus1.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus1.setSysLocation(merchantLocation.getMelId());
        syncStatus1 = syncStatusService.saveSyncStatus(syncStatus1);
        syncStatusHashSet.add(syncStatus1);

        SyncStatus syncStatus2 =SyncStatusFixture.standardSyncStatus();
        syncStatus2.setSysType(SyncType.SALES);
        syncStatus2.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus2.setSysLocation(merchantLocation.getMelId());
        syncStatus2 = syncStatusService.saveSyncStatus(syncStatus2);
        syncStatusHashSet.add(syncStatus2);

        SyncStatus syncStatus3 =SyncStatusFixture.standardSyncStatus();
        syncStatus3.setSysType(SyncType.CUSTOMERS);
        syncStatus3.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus3.setSysLocation(merchantLocation.getMelId());
        syncStatus3 = syncStatusService.saveSyncStatus(syncStatus3);
        syncStatusHashSet.add(syncStatus3);

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sync/info")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("sysMerchantNo",syncStatus1.getSysMerchantNo().toString())
                .param("sysLocation",syncStatus1.getSysLocation().toString())
                .param("sysDate",syncStatus1.getSysDate().toString())
                .param("sysType",syncStatus1.getSysType().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Sync Status Response: " + response);


    }


    @After
    public void tearDown() throws Exception {

        Set<SyncStatus> syncStatuses = SyncStatusFixture.standardSyncStatuses();

        for(SyncStatus syncStatus: syncStatusHashSet) {

            SyncStatus delSyncStatus = syncStatusService.findBySysId(syncStatus.getSysId());

            if ( delSyncStatus != null ) {
                syncStatusService.deleteSyncStatus(delSyncStatus);
            }

        }

        for(MerchantLocation merchantLocation:merchantLocationsHashSet){

            merchantLocationService.deleteMerchantLocationSet(merchantLocationsHashSet);
        }

        for(Merchant merchant:merchantHashSet){

            merchantService.deleteMerchant(merchant.getMerMerchantNo());
        }
    }

}
