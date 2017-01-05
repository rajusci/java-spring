package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.RedemptionMerchantStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.RedemptionMerchantFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
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
 * Created by saneesh-ci on 9/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class RedemptionMerchantServiceTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantServiceTest.class);

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;


    Set<RedemptionMerchant> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchantFixture.standardRedemptionMerchant());
        log.info(redemptionMerchant.toString());

        // Add the items
        tempSet.add(redemptionMerchant);

        Assert.assertNotNull(redemptionMerchant.getRemNo());

    }

    @Test
    public void RedemptionStatusChanged() throws InspireNetzException {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();

        redemptionMerchant =redemptionMerchantService.validateAndSaveRedemptionMerchant(redemptionMerchant);
        log.info(redemptionMerchant.toString());


        User user = UserFixture.standardUser();
        user.setUsrThirdPartyVendorNo(redemptionMerchant.getRemNo());
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user = userService.saveUser(user);

        //for updating user

        redemptionMerchant.setRemStatus(RedemptionMerchantStatus.DEACTIVATED);

        redemptionMerchant = redemptionMerchantService.validateAndSaveRedemptionMerchant(redemptionMerchant);

        User userUpdated =userService.findByUsrUserNo(user.getUsrUserNo());

        log.info("userStatus:"+userUpdated);

        // Add the items
        tempSet.add(redemptionMerchant);

        Assert.assertNotNull(redemptionMerchant.getRemNo());

    }

    @Test
    public void test2Update() throws InspireNetzException {
        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();

        redemptionMerchantService.validateAndSaveRedemptionMerchant(redemptionMerchant);

        log.info("Original RedemptionMerchant " + redemptionMerchant.toString());

        // Add to the tempSet
        tempSet.add(redemptionMerchant);

        long rmlId = 0L;
        Set<RedemptionMerchantLocation> redemptionLocationSet = redemptionMerchant.getRedemptionMerchantLocations();
        for(RedemptionMerchantLocation redemptionMerchantLocation: redemptionLocationSet){

            rmlId = redemptionMerchantLocation.getRmlId();

        }
        RedemptionMerchantLocation redemptionMerchantLocation=new RedemptionMerchantLocation();
        redemptionMerchantLocation.setRmlMerNo(redemptionMerchant.getRemNo());
        redemptionMerchantLocation.setRmlId(rmlId);
        redemptionMerchantLocation.setRmlLocation("Bangalore");

        RedemptionMerchantLocation redemptionMerchantLocation1=new RedemptionMerchantLocation();
        redemptionMerchantLocation1.setRmlLocation("Kochi");


        Set<RedemptionMerchantLocation> redemptionMerchantLocations =new HashSet<>();
        redemptionMerchantLocations.add(redemptionMerchantLocation);

        redemptionMerchantLocations.add(redemptionMerchantLocation1);
        redemptionMerchant.setRedemptionMerchantLocations(redemptionMerchantLocations);

        RedemptionMerchant updatedRedemptionMerchant = new RedemptionMerchant();
        updatedRedemptionMerchant = redemptionMerchantService.validateAndSaveRedemptionMerchant(redemptionMerchant);
        log.info("Updated RedemptionMerchant "+ updatedRedemptionMerchant.toString());

    }

    @Test
    public void test3FindByRemNo() throws InspireNetzException {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        log.info("Original RedemptionMerchants " + redemptionMerchant.toString());

        // Add the items
        tempSet.add(redemptionMerchant);

        RedemptionMerchant searchRequest = redemptionMerchantService.findByRemNo(redemptionMerchant.getRemNo());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(redemptionMerchant.getRemNo().longValue() == searchRequest.getRemNo().longValue());



    }


    @Test
    public void test4SearchRedemptionMerchants() throws InspireNetzException {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        log.info("Original RedemptionMerchants " + redemptionMerchant.toString());

        // Add the items
        tempSet.add(redemptionMerchant);

        Page<RedemptionMerchant> redemptionMerchantPage = redemptionMerchantService.searchRedemptionMerchants("0","0",constructPageSpecification(0));
        Assert.assertNotNull(redemptionMerchantPage);
        List<RedemptionMerchant> redemptionMerchantList = Lists.newArrayList((Iterable<RedemptionMerchant>) redemptionMerchantPage);
        log.info("RedemptionMerchant List : "+ redemptionMerchantList.toString());

    }

    @Test
    public void test5DeleteRedemptionMerchants() throws InspireNetzException {

        // Create the redemptionMerchant

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant  redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        Assert.assertNotNull(redemptionMerchant.getRemNo());
        log.info("RedemptionMerchants created");

        // call the delete redemptionMerchant
        redemptionMerchantService.deleteRedemptionMerchant(redemptionMerchant.getRemNo());

        // Get the link request again
        RedemptionMerchant redemptionMerchant1 = redemptionMerchantService.findByRemNo(redemptionMerchant.getRemNo());
        Assert.assertNull(redemptionMerchant1);;


    }


    @Test
    public void test6SaveAndValidateRedemptionMerchant() throws InspireNetzException {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantService.validateAndSaveRedemptionMerchant(redemptionMerchantFixture.standardRedemptionMerchant());
        log.info(redemptionMerchant.toString());

        // Add the items
        tempSet.add(redemptionMerchant);

        Assert.assertNotNull(redemptionMerchant.getRemNo());

    }



    @Test
    public void test7DeleteRedemptionMerchants() throws InspireNetzException {

        // Create the redemptionMerchant
        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant  redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        Assert.assertNotNull(redemptionMerchant.getRemNo());
        log.info("RedemptionMerchants created");

        // call the delete redemptionMerchant
        redemptionMerchantService.validateAndDeleteRedemptionMerchant(redemptionMerchant.getRemNo());

        // Get the link request again
        RedemptionMerchant redemptionMerchant1 = redemptionMerchantService.findByRemNo(redemptionMerchant.getRemNo());
        Assert.assertNull(redemptionMerchant1);;


    }


    @After
    public void tearDown() {
        for(RedemptionMerchant redemptionMerchant: tempSet) {

            redemptionMerchantService.deleteRedemptionMerchant(redemptionMerchant.getRemNo());

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
        return new Sort(Sort.Direction.ASC, "remName");
    }

}
