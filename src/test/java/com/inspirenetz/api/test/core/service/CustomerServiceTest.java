package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.core.repository.CustomerRepository;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    TierService tierService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    Mapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomerReferralService customerReferralService;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    private Set<CustomerProfile> tempSet = new HashSet<>(0);
    private Set<Customer> tempSet1 = new HashSet<>(0);

    private Set<User> tempSet2 = new HashSet<>(0);
    private Set<CustomerReferral> tempSet3 =new HashSet<>();
    private Set<RewardCurrency> tempSet4 =new HashSet<>();
    private Set<LoyaltyProgram> tempSet5 =new HashSet<>();
    private Set<LoyaltyProgramSku> tempSet6 =new HashSet<>();
    private Set<Tier> tempSet7 =new HashSet<>();



    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }


    @Test
    public void test3FindByCusMerchantNo() {

        // Get the standard customer
        Customer customer = CustomerFixture.standardCustomer();

        Page<Customer> customers = customerService.findByCusMerchantNo(customer.getCusMerchantNo(),constructPageSpecification(1));
        log.info("customers by merchant no " + customers.toString());
        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>)customers);
        log.info("customer list "+customerSet.toString());

        tempSet1.add(customer);

    }



    @Test
    public void test4FindByCusLoyaltyIdAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        Customer fetchCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(), customer.getCusMerchantNo());
        Assert.assertNotNull(fetchCustomer);
        log.info("Fetched customer info" + customer.toString());

        tempSet1.add(customer);
    }

    @Test
    public void test5findByCusMobileAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        Customer fetchCustomer = customerService.findByCusMobileAndCusMerchantNo(customer.getCusMobile(), customer.getCusMerchantNo());
        Assert.assertNotNull(fetchCustomer);
        log.info("Fetched customer info" + customer.toString());

        tempSet1.add(customer);

    }

    @Test
    public void test6FindByCusEmailAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        Customer fetchCustomer = customerService.findByCusEmailAndCusMerchantNo(customer.getCusEmail(), customer.getCusMerchantNo());
        Assert.assertNotNull(fetchCustomer);
        log.info("Fetched customer info" + customer.toString());

        tempSet1.add(customer);

    }

    @Test
    public void test7FindByCusMerchantNo() {

        // Create the Customers
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);
        log.info("Customers created");

        // Get the customer
        Customer customer = CustomerFixture.standardCustomer();


        Page<Customer> customerPage = customerService.findByCusMerchantNo(customer.getCusMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(customerPage);
        log.info("Fetched customer info" + customers.toString());

        tempSet1.add(customer);

    }

    @Test
    public void test8FindByCusFirstNameLikeAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        // Check the customer name
        Page<Customer> customers = customerService.findByCusFNameLikeAndCusMerchantNo("%sandeep%",customer.getCusMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(customers.hasContent());
        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>)customers);
        log.info("customer list "+customerSet.toString());

        tempSet1.add(customer);


    }

    @Test
    public void test9FindByCusLastNameLikeAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        // Check the customer name
        Page<Customer> customers = customerService.findByCusLNameLikeAndCusMerchantNo("%g%",customer.getCusMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(customers.hasContent());
        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>)customers);
        log.info("customer list "+customerSet.toString());

        tempSet1.add(customer);


    }

    @Test
    public void test10IsDuplicateCustomerExisting() {

        /*
        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");
*/
        // Create the customer
        Customer newCustomer = CustomerFixture.standardCustomer();
        newCustomer.setCusEmail("sdfsdfsdfp1@gmail.com");
        boolean exists = customerService.isDuplicateCustomerExisting(newCustomer);
        Assert.assertTrue(exists);
        log.info("Customer exists");

        tempSet1.add(newCustomer);



    }

    @Test
    public void test11FindByCusUserNoOrCusEmailOrCusMobile() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");


        // Get the customer information
        List<Customer> customerList = customerService.findByCusUserNoOrCusEmailOrCusMobile(customer.getCusUserNo(),customer.getCusEmail(),customer.getCusMobile());
        Assert.assertNotNull(customerList);
        log.info("Customer List : " + customerList.toString());


        tempSet1.add(customer);
    }


    @Test
    public void test12UpdateLoyaltyStatus() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9476656224");
        customer.setCusStatus(CustomerStatus.INACTIVE);
        //customerService.saveCustomer(customer);
        //Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        // Call the upateLoyaltySTatus
        boolean updated = customerService.updateLoyaltyStatus(customer.getCusLoyaltyId(),customer.getCusMerchantNo(), CustomerStatus.ACTIVE);
        Assert.assertTrue(updated);

        // Get the customer information and check
        Customer searchCustomer = customerService.findByCusCustomerNo(customer.getCusCustomerNo());
        Assert.assertNotNull(searchCustomer);
        Assert.assertTrue(searchCustomer.getCusStatus() == CustomerStatus.ACTIVE);

        // Log the information
        log.info("Customer status updated successfully ");

        tempSet1.add(customer);


    }

    @Test
    public void isDuplicateCustomerExistCompatible() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusMerchantNo(1L);

        customer.setCusLoyaltyId("9946693048");

        customer.setCusRegisterStatus(null);
        //Save customer
        customer=customerService.saveCustomer(customer);

        Assert.assertNotNull(customer.getCusCustomerNo());

        log.info("Customer created");

        // Fetch customer with CusMerchantNo,CusLoyaltyId,cusEmail,cusMobile
        Boolean isExist = customerService.isDuplicateCustomerExistCompatible(customer.getCusMerchantNo(), customer.getCusLoyaltyId(), customer.getCusEmail(), customer.getCusMobile(),customer.getCusCustomerNo());

        Assert.assertTrue(isExist);


    }


    @Test
    public void test13RegisterCustomer() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("1717171717");

        // Call the register customer
        boolean isValid = customerService.registerCustomer(customer.getCusLoyaltyId(),"welcome12","Sandeep","Menon");
        Assert.assertTrue(isValid);




    }

    @Test
    public void test13WhiteListCustomer() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusType(CustomerType.RETAILER);
        customer.setCusLoyaltyId("9744385878");

        // Call the register customer
        customer = customerService.saveCustomer(customer);
        Assert.assertTrue(customer.getCusIsWhiteListed().intValue() == IndicatorStatus.NO);
        boolean isWhiteListed = customerService.whiteListRetailer(customer.getCusLoyaltyId(),1L);
        Assert.assertTrue(isWhiteListed);

        tempSet1.add(customer);
    }

    @Test
    public void isCustomerValidForDTAwarding() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);

        // Create the customerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile(customer);
        customerProfile.setCspCustomerBirthday(DBUtils.covertToSqlDate("9999-12-31"));
        customerProfile.setCspBirthDayLastAwarded(DBUtils.covertToSqlDate("2013-12-08"));
        customerProfileService.saveCustomerProfile(customerProfile);
        Assert.assertNotNull(customerProfile);
        log.info("CustomerProfile created");

        // Add to tempSEt
        tempSet.add(customerProfile);

        boolean flag = customerService.isCustomerValidForDTAwarding(customerProfile, DateTriggeredAwardingType.BIRTHDAY);

        log.info("isCustomerValidForDTAwarding : " + flag);

        tempSet1.add(customer);
    }



    @Test
    public void connectMerchant() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusLoyaltyId("8947590408");
        customer.setCusMobile("8947590408");

        // Create the customerProfile
        CustomerProfile customerProfile =new CustomerProfile();

        customerProfile.setCspCustomerBirthday(DBUtils.covertToSqlDate("9999-12-31"));

        customer.setCustomerProfile(customerProfile);

        //customer =customerService.saveCustomerDetails(customerResource,customerProfileResource);
        customer =customerService.connectMerchant(customer.getCusMerchantNo(),customer, CustomerRegisterType.MERCHANT_PORTAL);

        Assert.assertNotNull(customer);

        tempSet1.add(customer);

        User user=userService.findByUsrLoginId("8947590408");

        Assert.assertNotNull(user);

        log.info("Customer object information" + customer);

        // Create the customer
        Customer secondCustomer = CustomerFixture.standardCustomer();

        secondCustomer.setCusLoyaltyId("8948590409");
        secondCustomer.setCusMobile("8948590409");
        secondCustomer.setReferralCode(user.getUsrUserCode());
        secondCustomer.setCusEmail("second@gmail.com");

        // Create the customerProfile
        CustomerProfile secondCustomerProfile =new CustomerProfile();

        secondCustomerProfile.setCspCustomerBirthday(DBUtils.covertToSqlDate("9999-12-31"));

        secondCustomer.setCustomerProfile(secondCustomerProfile);

        //customer =customerService.saveCustomerDetails(customerResource,customerProfileResource);
        secondCustomer =customerService.connectMerchant(secondCustomer.getCusMerchantNo(),secondCustomer, CustomerRegisterType.MERCHANT_PORTAL);


        Assert.assertNotNull(secondCustomer);

        tempSet1.add(secondCustomer);

        //unregister customer
        //customerService.unRegisterLoyaltyCustomer(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),CustomerStatus.INACTIVE,"",0);

        log.info("Customer object information" + secondCustomer);


    }




    @Test
    public void testReference() {

        Long a = 10L;
        Long b = 10L;

        Assert.assertTrue(a.longValue() == b);


    }

    @Test
    public void test13ChangeNotificationPreference() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");


        // Get the customer information
        boolean isChanged = customerService.changeNotificationStatus(customer.getCusLoyaltyId(), customer.getCusMerchantNo());
        Assert.assertTrue(isChanged);

        tempSet1.add(customer);

    }


    @Test
    public void test14ValidateCustomerServiceDetails() throws InspireNetzException {

        // Get the customer information
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo("9476656224",1L);

        // Check the information for an existing customer
        boolean isValid = customerService.validateCustomerServiceDetails(customer);

        // Assert the condition
        Assert.assertNotNull(isValid);



    }


    @Test
    public void getQuarterEndDate() {

        Date date = generalUtils.getFinancialQuarterEndDate(new Date());

        log.info("Date " + date);


    }

    @Test
    public void test16findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        //Save the customer
        customerService.saveCustomer(customer);

        log.info("Customer created");

        List<Customer> fetchCustomer= customerService.findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),customer.getCusEmail(),customer.getCusMobile());

        Assert.assertNotNull(fetchCustomer);

        log.info("Fetched customer info" + fetchCustomer.toString());
    }

    @Test
    public void test17getCustomerProfileCompatible() throws InspireNetzException {


        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        //Save the customer
        customerService.saveCustomer(customer);

        Assert.assertNotNull(customer);

        log.info("Customer created");

        // Create the customerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile(customer);

        //Save the customer profile
        customerProfileService.saveCustomerProfile(customerProfile);

        Assert.assertNotNull(customerProfile);

        log.info("CustomerProfile created");

        // Add to tempSEt
        tempSet.add(customerProfile);

        // Fetch customer with CusLoyaltyId,cusEmail,cusMobile
        Customer fetchCustomer= customerService.getCustomerProfileCompatible(customer.getCusLoyaltyId(),customer.getCusEmail(),customer.getCusMobile());

        Assert.assertNotNull(fetchCustomer);

        log.info("Fetched customer info" + fetchCustomer.toString());

    }

    @Test
    public void test13saveCustomerCompatible() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9476656224");

        // Call the saveCustomerCompatible
        boolean isValid = customerService.saveCustomerCompatible(0L,customer.getCusLoyaltyId(), "Sandeep", "Menon", "fa@gmail.com", "99999999", "#56", "Madiwala", "889999","12-2-2014","12-2-2014","");
        Assert.assertTrue(isValid);

 }

    @Test
    public void test7FindByCusUserNoAndCusStatus() {

        // Create the Customers
        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer:customers){

            customer.setCusUserNo(11111L);

            customerService.saveCustomer(customer);
        }

        log.info("Customers created");

        List<Customer> customerList = customerService.findByCusUserNoAndCusStatus(11111L, CustomerStatus.ACTIVE);

        Assert.assertNotNull(customerList);

        log.info("Fetched customer info" + customerList.toString());

    }

    @Test
    public void test7PortalActivationForCustomer() throws InspireNetzException{

        // Create the Customers
        Customer customer = CustomerFixture.standardCustomer();

        customer=customerService.saveCustomer(customer);

        log.info("Customers created");

        customerService.portalActivationForCustomer(customer.getCusMerchantNo());


        log.info("Portal Activation Success" );

    }


    @Test
    public void connectThroughPortal() throws InspireNetzException{

        // Create the Customers
        User user =new User();

        user.setUsrLoginId("1234567899");
        user.setUsrRegisterStatus(IndicatorStatus.YES);
        user.setUsrMobile("1234567899");
        user.setUsrStatus(UserStatus.ACTIVE);
        user.setUsrPassword("1234");

        //save user
        userService.saveUser(user);

        //connect merchant
        Customer customer =customerService.connectMerchantThroughPortal(3L,user.getUsrUserNo(),"");

        //add customer object and user object into temp
        tempSet1.add(customer);
        tempSet2.add(user);

        log.info("customer information:"+customer);
        Assert.assertNotNull(customer);




    }

    @Test
    public void processReferralAwarding() throws InspireNetzException {

        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();
        customerReferral.setCsrMerchantNo(3L);
        customerReferral.setCsrRefStatus(CustomerReferralStatus.NEW);
        customerReferral.setCsrRefMobile("123454391");
        customerReferral.setCsrFName("teggg");
        customerReferral =customerReferralService.saveCustomerReferral(customerReferral);
        tempSet3.add(customerReferral);


        Tier tier = tierService.saveTier(TierFixture.standardTier());
        tier.setTieName("asdfd");
        tierService.saveTier(tier);


        Customer customer =CustomerFixture.standardCustomer();
        customer.setCusMobile("123454391");
        customer.setCusLoyaltyId("123454391");
        customer.setCusFName("teggg");
        customer.setCusMerchantNo(3L);
        customer.setCusTier(tier.getTieId());

        customerService.saveCustomer(customer);



        // Create the product
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency.setRwdMerchantNo(3L);
        rewardCurrency.setRwdCurrencyName("ref1234test");
        rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        tempSet4.add(rewardCurrency);

        //loyalty program
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgCurrencyId(rewardCurrency.getRwdCurrencyId());
        loyaltyProgram.setPrgMerchantNo(3L);
        loyaltyProgram.setPrgProgramDriver(15);
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        //loyalty program skue
        LoyaltyProgramSku loyaltyProgramSku =LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku.setLpuProgramId(loyaltyProgram.getPrgProgramNo());
        loyaltyProgramSku.setLpuItemType(9);
        loyaltyProgramSku.setLpuItemCode("Referee Bonus");
        loyaltyProgramSku.setLpuPrgRatioNum(1);
        loyaltyProgramSku.setLpuPrgRatioDeno(1);
        loyaltyProgramSku.setLpuTier(0L);

        loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);
        // Add the created items to the tempset for removal on test completion
        tempSet5.add(loyaltyProgram);
        tempSet6.add(loyaltyProgramSku);
        customerService.saveCustomer(customer);
        tempSet1.add(customer);

        //award point for customer
        customerService.processReferralAwarding(customer);

        //get award point
        CustomerRewardBalance customerRewardBalance =customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(), 3L, rewardCurrency.getRwdCurrencyId());

        log.info(customerRewardBalance+"rewarBalance");

       // Assert.assertNotNull(customerRewardBalance);
    }

    @Test
    public void testUpdateCustomerStatus() throws InspireNetzException {

        Customer customer  = CustomerFixture.standardCustomer();

        //save the customer
        Customer tempCustomer = customerService.saveCustomer(customer);

        customer.setCusStatus(CustomerStatus.INACTIVE);

        //update the customer
        boolean isUpdate = customerService.updateCustomerStatus(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),customer.getCusStatus());

        //add customer for tear down process
        tempSet1.add(tempCustomer);

        //Assert
        Assert.assertTrue(isUpdate);
    }


    @After
    public void tearDown() throws InspireNetzException {

        Set<Customer> customers = CustomerFixture.standardCustomers();

        for (CustomerReferral customerReferral:tempSet3){

            customerReferralService.deleteCustomerReferral(customerReferral.getCsrId());
        }




        for (LoyaltyProgramSku loyaltyProgramSku:tempSet6){

            loyaltyProgramSkuService.deleteLoyaltyProgramSku(loyaltyProgramSku.getLpuId());

        }

        for (LoyaltyProgram loyaltyProgram:tempSet5){

            loyaltyProgramService.deleteLoyaltyProgram(loyaltyProgram.getPrgProgramNo());
        }

        for(CustomerProfile customerProfile: tempSet) {

            customerProfileService.deleteCustomerProfile(customerProfile);

        }

        for(Customer customer:tempSet1){

            customerService.deleteCustomer(customer.getCusCustomerNo());
        }

        for (User user:tempSet2){

            userService.deleteUser(user);
        }


        for (Tier tier:tempSet7){

            tierService.deleteTier(tier.getTieId());
        }

        for (RewardCurrency rewardCurrency:tempSet4){

            rewardCurrencyService.deleteRewardCurrency(rewardCurrency.getRwdCurrencyId());
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
        return new Sort(Sort.Direction.ASC, "cusLoyaltyId");
    }

}
