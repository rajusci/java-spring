package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.dictionary.VoucherStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionVoucherSourceService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherSourceFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class RedemptionVoucherSourceServiceTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherSourceServiceTest.class);

    @Autowired
    private RedemptionVoucherSourceService redemptionVoucherSourceService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<RedemptionVoucherSource> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create() throws InspireNetzException {

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.validateAndSaveRedemptionVoucherSource(RedemptionVoucherSourceFixture.standardRedemptionVoucherSource());
        log.info(redemptionVoucherSource.toString());

        // Add the items
        tempSet.add(redemptionVoucherSource);

        Assert.assertNotNull(redemptionVoucherSource.getRvsId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.validateAndSaveRedemptionVoucherSource(RedemptionVoucherSourceFixture.standardRedemptionVoucherSource());
        redemptionVoucherSource=redemptionVoucherSourceService.findByRvsId(redemptionVoucherSource.getRvsId());
        tempSet.add(redemptionVoucherSource);
        log.info("redemptionVoucherSource select data"+redemptionVoucherSource.getRvsId());

        RedemptionVoucherSource updatedRedemptionVoucherSource = RedemptionVoucherSourceFixture.updatedStandardRedemptionVoucherSource(redemptionVoucherSource);
        log.info("Updated RedemptionVoucherSource "+ updatedRedemptionVoucherSource.toString());
        updatedRedemptionVoucherSource = redemptionVoucherSourceService.validateAndSaveRedemptionVoucherSource(updatedRedemptionVoucherSource);
//        tempSet.add(redemptionVoucherSource);
        log.info("Updated RedemptionVoucherSource "+ updatedRedemptionVoucherSource.toString());

    }

    @Test
    public void test3FindByDrcId() throws InspireNetzException {

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceFixture.standardRedemptionVoucherSource();
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);
        log.info("Original RedemptionVoucherSources " + redemptionVoucherSource.toString());

        // Add the items
        tempSet.add(redemptionVoucherSource);

        RedemptionVoucherSource searchRequest = redemptionVoucherSourceService.findByRvsId(redemptionVoucherSource.getRvsId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(redemptionVoucherSource.getRvsId().longValue() == searchRequest.getRvsId().longValue());



    }

    @Test
    public void test3FindByRvsMerchantNoAndRvsNameLike() throws InspireNetzException {

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceFixture.standardRedemptionVoucherSource();
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);
        log.info("Original RedemptionVoucherSources " + redemptionVoucherSource.toString());

        // Add the items
        tempSet.add(redemptionVoucherSource);

        Page<RedemptionVoucherSource> searchRequest = redemptionVoucherSourceService.findByRvsMerchantNoAndRvsNameLike(redemptionVoucherSource.getRvsMerchantNo(), redemptionVoucherSource.getRvsName(), constructPageSpecification(0));
        Assert.assertNotNull(searchRequest);


    }



    @Test
    public void test5DeleteRedemptionVoucherSources() throws InspireNetzException {

        // Create the redemptionVoucherSource

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource  redemptionVoucherSource = redemptionVoucherSourceFixture.standardRedemptionVoucherSource();
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);
        Assert.assertNotNull(redemptionVoucherSource.getRvsId());
        log.info("RedemptionVoucherSources created");

        // call the delete redemptionVoucherSource
        redemptionVoucherSourceService.deleteRedemptionVoucherSource(redemptionVoucherSource.getRvsId());

        // Get the link request again
        RedemptionVoucherSource redemptionVoucherSource1 = redemptionVoucherSourceService.findByRvsId(redemptionVoucherSource.getRvsId());
        Assert.assertNull(redemptionVoucherSource1);;


    }


    @Test
    public void test6SaveAndValidateRedemptionVoucherSource() throws InspireNetzException {

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.validateAndSaveRedemptionVoucherSource(redemptionVoucherSourceFixture.standardRedemptionVoucherSource());
        log.info(redemptionVoucherSource.toString());

        // Add the items
        tempSet.add(redemptionVoucherSource);

        Assert.assertNotNull(redemptionVoucherSource.getRvsId());

    }

    @Test
    public void test6updateVoucherSourceStatus() throws InspireNetzException {

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.validateAndSaveRedemptionVoucherSource(redemptionVoucherSourceFixture.standardRedemptionVoucherSource());
        log.info(redemptionVoucherSource.toString());

        redemptionVoucherSourceService.updateVoucherSourceStatus(redemptionVoucherSource, VoucherStatus.FAILED);
        RedemptionVoucherSource redemptionVoucherSource1 = redemptionVoucherSourceService.findByRvsId(redemptionVoucherSource.getRvsId());

        Assert.assertTrue(redemptionVoucherSource1.getRvsStatus() == VoucherStatus.FAILED);
        // Add the items
        tempSet.add(redemptionVoucherSource);

        Assert.assertNotNull(redemptionVoucherSource.getRvsId());

    }



    @After
    public void tearDown() throws InspireNetzException {
        for(RedemptionVoucherSource redemptionVoucherSource: tempSet) {

            redemptionVoucherSourceService.deleteRedemptionVoucherSource(redemptionVoucherSource.getRvsId());

        }





    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

}
