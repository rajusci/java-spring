package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherUpdateRequest;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.core.service.RedemptionVoucherService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionMerchantFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
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
public class RedemptionVoucherServiceTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherServiceTest.class);

    @Autowired
    private RedemptionVoucherService redemptionVoucherService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;


    Set<RedemptionVoucher> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<User> userSet = new HashSet<>(0);

    Set<RedemptionMerchant> tempSet1=new HashSet<>(0);

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

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucherFixture.standardRedemptionVoucher());
        log.info(redemptionVoucher.toString());

        // Add the items
        tempSet.add(redemptionVoucher);

        Assert.assertNotNull(redemptionVoucher.getRvrId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        RedemptionVoucher redemptionVoucher = new RedemptionVoucher();
        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucherFixture.standardRedemptionVoucher());
        redemptionVoucher=redemptionVoucherService.findByRvrId(redemptionVoucher.getRvrId());
        log.info("redemptionVoucher select data"+redemptionVoucher.getRvrId());

        RedemptionVoucher updatedRedemptionVoucher = RedemptionVoucherFixture.updatedStandardRedemptionVoucher(redemptionVoucher);
        log.info("Updated RedemptionVoucher "+ updatedRedemptionVoucher.toString());
        updatedRedemptionVoucher = redemptionVoucherService.validateAndSaveRedemptionVoucher(updatedRedemptionVoucher);
//        tempSet.add(redemptionVoucher);
        log.info("Updated RedemptionVoucher "+ updatedRedemptionVoucher.toString());

    }

    @Test
    public void test3FindByRvrId() throws InspireNetzException {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        log.info("Original RedemptionVouchers " + redemptionVoucher.toString());

        // Add the items
        tempSet.add(redemptionVoucher);

        RedemptionVoucher searchRequest = redemptionVoucherService.findByRvrId(redemptionVoucher.getRvrId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(redemptionVoucher.getRvrId().longValue() == searchRequest.getRvrId().longValue());



    }


    @Test
    public void test4getPendingRedemptionVouchers() throws InspireNetzException {

        // Create customer
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);

        // Add to thecustomer set
        customerSet.add(customer);


        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrCustomerNo(customer.getCusCustomerNo());
        redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.NEW);
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        log.info("Original RedemptionVouchers " + redemptionVoucher.toString());

        // Add the items
        tempSet.add(redemptionVoucher);

        Page<RedemptionVoucher> redemptionVoucherPage = redemptionVoucherService.getPendingRedemptionVouchers(customer.getCusLoyaltyId(),1, constructPageSpecification(0));
        Assert.assertNotNull(redemptionVoucherPage);
        List<RedemptionVoucher> redemptionVoucherList = Lists.newArrayList((Iterable<RedemptionVoucher>) redemptionVoucherPage);
        log.info("RedemptionVoucher List : "+ redemptionVoucherList.toString());

    }

    @Test
    public void test5DeleteRedemptionVouchers() throws InspireNetzException {

        // Create the redemptionVoucher

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher  redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        Assert.assertNotNull(redemptionVoucher.getRvrId());
        log.info("RedemptionVouchers created");

        // call the delete redemptionVoucher
        redemptionVoucherService.deleteRedemptionVoucher(redemptionVoucher.getRvrId());

        // Get the link request again
        RedemptionVoucher redemptionVoucher1 = redemptionVoucherService.findByRvrId(redemptionVoucher.getRvrId());
        Assert.assertNull(redemptionVoucher1);;


    }

    @Test
    public void test5searchRedemptionVoucher() throws InspireNetzException {

        // Create the redemptionVoucher

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher  redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        Assert.assertNotNull(redemptionVoucher.getRvrId());
        log.info("RedemptionVouchers created");


        // Get the link request again
        Page<RedemptionVoucher> redemptionVoucher1 = redemptionVoucherService.searchRedemptionVoucherForMerchant(redemptionVoucher.getRvrVoucherCode(),redemptionVoucher.getRvrLoyaltyId(), DBUtils.covertToSqlDate("1970-01-01"),DBUtils.covertToSqlDate("9999-01-01"),constructPageSpecification(0));
        Assert.assertNotNull(redemptionVoucher1);


    }



    @Test
    public void test6SaveAndValidateRedemptionVoucher() throws InspireNetzException {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherService.validateAndSaveRedemptionVoucher(redemptionVoucherFixture.standardRedemptionVoucher());
        log.info(redemptionVoucher.toString());

        // Add the items
        tempSet.add(redemptionVoucher);

        Assert.assertNotNull(redemptionVoucher.getRvrId());

    }

    @Test
    public void test7searchRedemptionVoucher() throws InspireNetzException {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherService.validateAndSaveRedemptionVoucher(redemptionVoucherFixture.standardRedemptionVoucher());
        log.info(redemptionVoucher.toString());
        // Add the items
        tempSet.add(redemptionVoucher);

        Assert.assertNotNull(redemptionVoucher.getRvrId());

        Page<RedemptionVoucher> redemptionVoucherPage =redemptionVoucherService.searchRedemptionVoucher("voucherCode",redemptionVoucher.getRvrVoucherCode(),constructPageSpecification(0));

        Assert.assertNotNull(redemptionVoucherPage);

        log.info("RedemptionVoucher Search "+redemptionVoucherPage);
    }



    @Test
    public void test7DeleteRedemptionVouchers() throws InspireNetzException {

        // Create the redemptionVoucher
        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher  redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        Assert.assertNotNull(redemptionVoucher.getRvrId());
        log.info("RedemptionVouchers created");

        // call the delete redemptionVoucher
        redemptionVoucherService.validateAndDeleteRedemptionVoucher(redemptionVoucher.getRvrId());

        // Get the link request again
        RedemptionVoucher redemptionVoucher1 = redemptionVoucherService.findByRvrId(redemptionVoucher.getRvrId());
        Assert.assertNull(redemptionVoucher1);;


    }

    @Test
    public void test8CheckVoucherCodeExists() throws InspireNetzException {

        // Create a temporary redemptionm merchant user
        User user = UserFixture.standardUser();
        user.setUsrLoginId("8123591676");
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user.setUsrThirdPartyVendorNo(1000L);
        user = userService.saveUser(user);

        // Add the user to the set
        userSet.add(user);


        // Create the RedemptionVoucherMerchant
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();
        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrCustomerNo(customer.getCusCustomerNo());
        redemptionVoucher.setRvrMerchant(user.getUsrThirdPartyVendorNo());
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        log.info("Original RedemptionVouchers " + redemptionVoucher.toString());

        // Add the items
        tempSet.add(redemptionVoucher);
        customerSet.add(customer);

        RedemptionVoucher redemptionVoucher1 = redemptionVoucherService.redeemRedemptionVoucher(customer.getCusLoyaltyId(),user.getUsrLoginId(),redemptionVoucher.getRvrVoucherCode());
        Assert.assertNotNull(redemptionVoucher1);

    }


    @Test
    public void test8CheckVoucherCodeIsValid() throws InspireNetzException {

        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();


        redemptionVoucher.setRvrVoucherCode("EXT10000001111");

        RedemptionVoucher redemptionVoucher1=redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        RedemptionVoucher redemptionVoucherIsValid =redemptionVoucherService.redemptionVoucherIsValid(redemptionVoucher1.getRvrVoucherCode());

        Assert.assertNotNull(redemptionVoucherIsValid);

        log.info("Redemption voucher is valid");

    }

    @Test
    public void test8ClaimForMerchantUser() throws InspireNetzException {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchantFixture.standardRedemptionMerchant());

        log.info(redemptionMerchant.toString());

        // Add the items
        tempSet1.add(redemptionMerchant);

        Assert.assertNotNull(redemptionMerchant.getRemNo());

        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();

        redemptionVoucher.setRvrVoucherCode("EXT1001111111111");

        redemptionVoucher.setRvrMerchant(redemptionMerchant.getRemNo());

        redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.NEW);

        redemptionVoucher.setRvrExpiryDate(DBUtils.covertToSqlDate("2015-02-09"));

        RedemptionVoucher redemptionVoucher1=redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        Set<RedemptionMerchantLocation> location =new HashSet<>(0);

        location =redemptionMerchant.getRedemptionMerchantLocations();

        String merchantLocation="";

        for(RedemptionMerchantLocation redemptionMerchantLocation:location){


            merchantLocation =redemptionMerchantLocation.getRmlLocation();

            break;
        }

        RedemptionVoucher redemptionVoucher2=redemptionVoucherService.voucherClaimForMerchantUser(redemptionVoucher.getRvrVoucherCode(),merchantLocation);

        log.info("Claim redemption voucher"+redemptionVoucher2);

        Assert.assertNotNull(redemptionVoucher2);

    }

    @Test
    public void test9IsvoucherExpired() throws InspireNetzException {

        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();


        redemptionVoucher.setRvrVoucherCode("EXT1000111111");

        redemptionVoucher.setRvrExpiryDate(DBUtils.covertToSqlDate("2015-02-06"));
        RedemptionVoucher redemptionVoucher1=redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        boolean isVoucherExpired =redemptionVoucherService.isVoucherExpired(redemptionVoucher1);

        Assert.assertTrue(isVoucherExpired);

        log.info("Redemption voucher is valid");

    }

    @Test
    public void updateRedemptionVoucher() throws InspireNetzException {

        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrVoucherCode("EXT1000111111");
        redemptionVoucher.setRvrExpiryDate(DBUtils.covertToSqlDate("2015-02-06"));
        RedemptionVoucher redemptionVoucher1=redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        tempSet.add(redemptionVoucher);

        tempSet.add(redemptionVoucher1);

        RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest =new RedemptionVoucherUpdateRequest();

        redemptionVoucherUpdateRequest.setRvrReqId(redemptionVoucher1.getRvrId());
        redemptionVoucherUpdateRequest.setRvrExpiryOption(IndicatorStatus.NO);
        redemptionVoucherUpdateRequest.setRvrReqVoucherCode("Asghhh");

        redemptionVoucher =redemptionVoucherService.updateRedemptionVoucher(redemptionVoucherUpdateRequest);

        Assert.assertNotNull(redemptionVoucher);


        log.info("Redemption voucher is valid");

    }


    @After
    public void tearDown() throws InspireNetzException {
        for(RedemptionVoucher redemptionVoucher: tempSet) {

            redemptionVoucherService.deleteRedemptionVoucher(redemptionVoucher.getRvrId());

        }
        for(Customer customer: customerSet) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(RedemptionMerchant redemptionMerchant: tempSet1) {

            redemptionMerchantService.deleteRedemptionMerchant(redemptionMerchant.getRemNo());

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

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "rvrVoucherCode");
    }

}
