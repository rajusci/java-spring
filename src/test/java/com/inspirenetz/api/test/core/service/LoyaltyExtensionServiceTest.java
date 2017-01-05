package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LoyaltyExtensionService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LoyaltyExtensionFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
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
 * Created by saneesh-ci on 10/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class LoyaltyExtensionServiceTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyExtensionServiceTest.class);

    @Autowired
    private LoyaltyExtensionService loyaltyExtensionService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    Set<LoyaltyExtension> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){


        LoyaltyExtension loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(LoyaltyExtensionFixture.standardLoyaltyExtension());
        log.info(loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        Assert.assertNotNull(loyaltyExtension.getLexId());

    }

    @Test
    public void test2Update() {

        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        LoyaltyExtension updatedLoyaltyExtensions = LoyaltyExtensionFixture.updatedStandardLoyaltyExtensions(loyaltyExtension);
        updatedLoyaltyExtensions = loyaltyExtensionService.saveLoyaltyExtension(updatedLoyaltyExtensions);
        log.info("Updated LoyaltyExtensions "+ updatedLoyaltyExtensions.toString());

    }

    @Test
    public void test3FindByLexId() {


        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        LoyaltyExtension searchRequest = loyaltyExtensionService.findByLexId(loyaltyExtension.getLexId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(loyaltyExtension.getLexId().longValue() == searchRequest.getLexId().longValue());



    }


    @Test
    public void test4SearchLoyaltyExtensions() {

        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        Page<LoyaltyExtension> loyaltyExtensionPage = loyaltyExtensionService.searchLoyaltyExtensions("0","0",constructPageSpecification(0));
        Assert.assertNotNull(loyaltyExtensionPage);
        List<LoyaltyExtension> loyaltyExtensionList = Lists.newArrayList((Iterable<LoyaltyExtension>) loyaltyExtensionPage);
        log.info("Loyalty Extension List : "+ loyaltyExtensionList.toString());

    }

    @Test
    public void test5DeleteLoyaltyExtensions() {

        // Create the loyaltyExtension
        LoyaltyExtension  loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        Assert.assertNotNull(loyaltyExtension.getLexId());
        log.info("LoyaltyExtensions created");

        // call the delete loyaltyExtension
        loyaltyExtensionService.deleteLoyaltyExtension(loyaltyExtension.getLexId());

        // Get the link request again
        LoyaltyExtension loyaltyExtension1 = loyaltyExtensionService.findByLexId(loyaltyExtension.getLexId());
        Assert.assertNull(loyaltyExtension1);;


    }


    @Test
    public void test6SaveAndValidateLoyaltyExtension() throws InspireNetzException {


        LoyaltyExtension loyaltyExtension = loyaltyExtensionService.validateAndSaveLoyaltyExtension(LoyaltyExtensionFixture.standardLoyaltyExtension());
        log.info(loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        Assert.assertNotNull(loyaltyExtension.getLexId());

    }


    @Test
    public void test7getLoyaltyExtensionInfo() throws InspireNetzException {


        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        LoyaltyExtension searchRequest = loyaltyExtensionService.getLoyaltyExtensionInfo(loyaltyExtension.getLexId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(loyaltyExtension.getLexId().longValue() == searchRequest.getLexId().longValue());

    }


    @Test
    public void test8DeleteLoyaltyExtensions() throws InspireNetzException {

        // Create the loyaltyExtension
        LoyaltyExtension  loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        Assert.assertNotNull(loyaltyExtension.getLexId());
        log.info("LoyaltyExtensions created");

        // call the delete loyaltyExtension
        loyaltyExtensionService.validateAndDeleteLoyaltyExtension(loyaltyExtension.getLexId());

        // Get the link request again
        LoyaltyExtension loyaltyExtension1 = loyaltyExtensionService.findByLexId(loyaltyExtension.getLexId());
        Assert.assertNull(loyaltyExtension1);;


    }



    @After
    public void tearDown() {

        for(LoyaltyExtension loyaltyExtension: tempSet) {

            loyaltyExtensionService.deleteLoyaltyExtension(loyaltyExtension.getLexId());

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
        return new Sort(Sort.Direction.ASC, "lexName");
    }

}
