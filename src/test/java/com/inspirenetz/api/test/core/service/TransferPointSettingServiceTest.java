package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.core.service.TransferPointSettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.TransferPointSettingFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class TransferPointSettingServiceTest {


    private static Logger log = LoggerFactory.getLogger(TransferPointSettingServiceTest.class);

    @Autowired
    private TransferPointSettingService transferPointSettingService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<TransferPointSetting> tempSet = new HashSet<>(0);


    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1Create(){


        TransferPointSetting transferPointSetting = transferPointSettingService.saveTransferPointSetting(TransferPointSettingFixture.standardTransferPointSetting());
        log.info(transferPointSetting.toString());

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        Assert.assertNotNull(transferPointSetting.getTpsId());

    }

    @Test
    public void test2Update() {

        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        log.info("Original TransferPointSetting " + transferPointSetting.toString());

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        TransferPointSetting updatedTransferPointSetting = TransferPointSettingFixture.updatedStandardTransferPointSetting(transferPointSetting);
        updatedTransferPointSetting = transferPointSettingService.saveTransferPointSetting(updatedTransferPointSetting);
        log.info("Updated TransferPointSetting " + updatedTransferPointSetting.toString());

    }



    @Test
    public void test3FindByTpsId() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        // Get the setting
        TransferPointSetting transferPointSetting1 = transferPointSettingService.findByTpsId(transferPointSetting.getTpsId());
        Assert.assertNotNull(transferPointSetting1);;
        log.info("Account Bundling Setting " + transferPointSetting);


    }

    @Test
    public void test4FindByTpsMerchantNoAndTpsLocation() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        TransferPointSetting fetchTransferPointSetting = transferPointSettingService.findByTpsMerchantNoAndTpsLocation(transferPointSetting.getTpsMerchantNo(), transferPointSetting.getTpsLocation());
        Assert.assertNotNull(fetchTransferPointSetting);
        log.info("Fetched transferPointSetting info" + transferPointSetting.toString());

    }



    @Test
    public void test5findByTpsMerchantNo() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        List<TransferPointSetting> fetchTransferPointSetting = transferPointSettingService.findByTpsMerchantNo(transferPointSetting.getTpsMerchantNo());
        Assert.assertNotNull(fetchTransferPointSetting);
        log.info("Fetched transferPointSetting info" + transferPointSetting.toString());

    }


    @Test
    public void test6SaveTransferPointSettingForUser() throws InspireNetzException {


        TransferPointSetting transferPointSetting = transferPointSettingService.saveTransferPointSettingForUser(TransferPointSettingFixture.standardTransferPointSetting());
        log.info(transferPointSetting.toString());

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        Assert.assertNotNull(transferPointSetting.getTpsId());

    }


    @Test
    public void test7GetDefaultTransferPointSetting() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting.setTpsLocation(0L);
        transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        TransferPointSetting fetchTransferPointSetting = transferPointSettingService.getDefaultTransferPointSetting(transferPointSetting.getTpsMerchantNo());
        Assert.assertNotNull(fetchTransferPointSetting);
        log.info("Fetched transferPointSetting info" + transferPointSetting.toString());

    }

    @After
    public void tearDown() throws InspireNetzException {

        for(TransferPointSetting transferPointSetting: tempSet) {

            transferPointSettingService.deleteTransferPointSetting(transferPointSetting.getTpsId());

        }
    }


    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "tpsName");
    }

}
