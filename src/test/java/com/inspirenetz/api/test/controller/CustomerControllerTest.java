package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.CustomerType;
import com.inspirenetz.api.core.dictionary.LinkedLoyaltyStatus;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.CustomerProfileRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;

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
import java.io.*;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class,IntegrationTestConfig.class,NotificationTestConfig.class})

public class
        CustomerControllerTest {


    private static Logger log = LoggerFactory.getLogger(CustomerControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private AccountTransferService accountTransferService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private PartyApprovalService partyApprovalService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerProfileService customerProfileService;

    // Customer object
    private Customer customer;

    private CustomerProfile customerProfile;

    private Set<Customer> customerSet = new HashSet<>(0);

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        useMerchantSession();
    }


    private void useCustomerSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

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


    private void useMerchantSession() {

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

        // Create the customer
        customer = CustomerFixture.standardCustomer();

        customerProfile =CustomerProfileFixture.standardCustomerProfile();


    }



    @Test
    public void saveCustomer() throws Exception {

        customer.setCusLoyaltyId("8947491408");
        customer.setCusMobile("8947491408");
        customer.setCusFName("abhi");
        customer.setCusLName("");
        customer.setCusEmail("abhi@gmail.com");
        customer.setCusMobileCountryCode("+91");


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cusLoyaltyId", customer.getCusLoyaltyId())
                .param("cusFName",customer.getCusFName())
                .param("cusLName",customer.getCusLName())
                .param("cusMobile",customer.getCusMobile())
                .param("cusEmail",customer.getCusEmail())
                .param("cusIdType",Integer.toString(customer.getCusIdType()))
                .param("cusIdNo",customer.getCusIdNo())
                .param("cspLoyaltyId", customer.getCusLoyaltyId())
                .param("cusMobileCountryCode", "+41")
                .param("referralCode","HB1YGk")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Customer created");


    }

    @Test
    public void searchCustomers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/search/loyaltyid/9538828853")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void listCustomers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customers?page=3")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void getCustomerInfo() throws Exception {

        // Save the customer
        customer = customerService.saveCustomer(customer);

        // Add to the customerSet
        customerSet.add(customer);

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/"+customer.getCusCustomerNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void registerForLoyalty() throws Exception {

        customer.setCusStatus(CustomerStatus.INACTIVE);

       /* User user = UserFixture.standardUser();
        user.setUsrLoginId(customer.getCusLoyaltyId());
        user.setUsrRegisterStatus(0);
        user = userService.saveUser(user);

        customer.setCusUserNo(user.getUsrUserNo());
        // Save the customer
        customer = customerService.saveCustomer(customer);
*/

        // Add to the customerSet
        customerSet.add(customer);

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/loyalty/register/12345")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);


        log.info("CustomerResponse: " + response);

        // Get the customer information and check
        Customer searchCustomer = customerService.findByCusCustomerNo(customer.getCusCustomerNo());
        Assert.assertNotNull(searchCustomer);
        Assert.assertTrue(searchCustomer.getCusStatus() == CustomerStatus.ACTIVE);

        // Log the information
        log.info("Customer status updated successfully ");

    }

    @Test
    public void unRegisterForLoyalty() throws Exception {

       /* // Save the customer
        customer = customerService.saveCustomer(customer);

        // Add to the customerSet
        customerSet.add(customer);
*/
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/loyalty/unregister/12345")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);


        log.info("CustomerResponse: " + response);

        // Get the customer information and check
        Customer searchCustomer = customerService.findByCusCustomerNo(customer.getCusCustomerNo());
        Assert.assertNotNull(searchCustomer);
        Assert.assertTrue(searchCustomer.getCusStatus() == CustomerStatus.INACTIVE);

        // Log the information
        log.info("Customer status updated successfully ");

    }


    @Test
    public void getCustomerInfoForSession() throws Exception {

        useCustomerSession();

        // Save the customer
        customer = customerService.saveCustomer(customer);

        // Add to the customerSet
        customerSet.add(customer);

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/info")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }


    @Test
    public void registerCustomer() throws Exception {

        useMerchantSession();

       /* Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9744375895");
        customerService.saveCustomer(customer);

        // Add to the set for delete
        customerSet.add(customer);*/


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/register")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName",customer.getCusFName())
                .param("lastName",customer.getCusLName())
                .param("password","sqlAd12@")
                .param("loyaltyId","9400651688")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }


    @Test
    public void testInvalidMin() throws Exception {

        useMerchantSession();

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("8884172977");
        /*customerService.saveCustomer(customer);

        // Add to the set for delete
        customerSet.add(customer);*/


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/register")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName",customer.getCusFName())
                .param("lastName",customer.getCusLName())
                .param("password","sqlAd12@")
                .param("loyaltyId",customer.getCusLoyaltyId())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }


    @Test
    public void transferAccounts() throws Exception {

        useMerchantSession();

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>) customers);
        customerService.saveAll(customerList);

        // Get the sourceCustomer
        Customer sourceCustomer = customerList.get(0);


        // Get the destCustomer
        Customer destCustomer = customerList.get(1);
        destCustomer.setCusStatus(CustomerStatus.INACTIVE);
        destCustomer = customerService.saveCustomer(destCustomer);

        // ADd to the set for delete
        customerSet.addAll(customerList);


        // Add the AccumulatedRewardBalance for the primary
        AccumulatedRewardBalance accumulatedRewardBalance1 = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance1.setArbLoyaltyId(customerList.get(0).getCusLoyaltyId());
        accumulatedRewardBalance1.setArbRewardBalance(20.0);
        accumulatedRewardBalance1 =  accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance1);

        // Add the LinkedRewardBalance for the primary
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance.setLrbPrimaryLoyaltyId(customerList.get(0).getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(120.0);
        linkedRewardBalance =  linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);


        // Add the CustomerrewardBalance for primary
        CustomerRewardBalance customerRewardBalance1 = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance1.setCrbLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardBalance1 =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance1);


        // Add the CustomerRewardExpiry for primary
        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry1.setCreLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardExpiry1 = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry1);


        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(customerList.get(0).getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(customerList.get(0).getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);


        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(customerList.get(0).getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer/loyalty/transfer")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("srcLoyaltyId",sourceCustomer.getCusLoyaltyId())
                .param("destLoyaltyId",destCustomer.getCusLoyaltyId())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void whiteListCustomer() throws Exception {

        useMerchantSession();

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9744385878");
        customer.setCusType(CustomerType.RETAILER);
        customerService.saveCustomer(customer);

        // Add to the set for delete
        customerSet.add(customer);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/whitelist")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyaltyId",customer.getCusLoyaltyId())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void getMembership() throws Exception {

        useCustomerSession();

        // Add to the set for delete
        customerSet.add(customer);


        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/memberships")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void registerCustomerCompatible() throws Exception {

        useMerchantSession();

       /* Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9744375895");
        customerService.saveCustomer(customer);

        // Add to the set for delete
        customerSet.add(customer);*/

        String key = userService.getRegistrationAuthenticationKey("9494949494","sqlAd12@");

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/register/compatible")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstname",customer.getCusFName())
                .param("lastname",customer.getCusLName())
                .param("password","sqlAd12@")
                .param("username","9494949494")
                .param("reg_key","1232123")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void updateCustomer() throws Exception {


        customerProfile.setCspBirthDayLastAwarded(DBUtils.covertToSqlDate("9999-12-31"));

        CustomerProfile customerProfile1 =customerProfileService.saveCustomerProfile(customerProfile);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cusLoyaltyId", customer.getCusLoyaltyId())
                .param("cusFName",customer.getCusFName())
                .param("cusLName",customer.getCusLName())
                .param("cusMobile",customer.getCusMobile())
                .param("cusEmail",customer.getCusEmail())
                .param("cusIdType",Integer.toString(customer.getCusIdType()))
                .param("cusIdNo",customer.getCusIdNo())
                .param("cspLoyaltyId", customer.getCusLoyaltyId())
                .param("cspAddress",customerProfile1.getCspAddress())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);

    }

    @Test
    public void getCustomerProfileCompatible() throws Exception  {

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
        customerSet.add(customer);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customerprofile")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("customer_search_text", customer.getCusLoyaltyId())
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("getCustomerProfileCompatible Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void saveCustomerCompatible() throws Exception {

        useMerchantSession();

        Customer customer = CustomerFixture.standardCustomer();

        // Add to the set for delete
        customerSet.add(customer);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customerregister")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("card_number", customer.getCusLoyaltyId())
                .param("customer_fname", customer.getCusFName())
                .param("customer_lname", customer.getCusLName())
                .param("customer_email", customer.getCusEmail())
                .param("customer_mobile", customer.getCusMobile())
                .param("customer_address", "#56")
                .param("customer_city", "Madiwala@")
                .param("customer_pincode", "473356")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("saveCustomerCompatibleResponse: " + response);

        Assert.assertTrue(response.contains("success"));

    }

    @Test
    public void PortalActivationForCustomer() throws Exception {

        useMerchantSession();

        Customer customer = CustomerFixture.standardCustomer();

        customer=customerService.saveCustomer(customer);

        // Add to the set for delete
        customerSet.add(customer);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customers/useractivation")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchantNo", customer.getCusMerchantNo().toString())
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("saveCustomerCompatibleResponse: " + response);

        Assert.assertTrue(response.contains("success"));

    }

    @Test
    public void parseXml() throws Exception {

        File file = new File("/home/ameen/Downloads/Batch Integration - XML Formats/CUSTOMER_MASTER.xml");
        InputStream inputStream= new FileInputStream(file);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/xml/sku/customermaster")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(ByteStreams.toByteArray(inputStream))
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("saveCustomerCompatibleResponse: " + response);

        Assert.assertTrue(response.contains("success"));

    }


    @After
    public void tearDown() throws InspireNetzException {

        for(Customer customer : customerSet ) {

            Customer tempCustomer =customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            List<AccumulatedRewardBalance> accumulatedRewardBalanceList = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyId(tempCustomer.getCusMerchantNo(),tempCustomer.getCusLoyaltyId());

            for(AccumulatedRewardBalance accumulatedRewardBalance : accumulatedRewardBalanceList ) {

                accumulatedRewardBalanceService.deleteAccumulatedRewardBalance(accumulatedRewardBalance.getArbId());

            }


            List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(tempCustomer.getCusLoyaltyId(), tempCustomer.getCusMerchantNo());

            for(LinkedRewardBalance linkedRewardBalance : linkedRewardBalanceList ) {

                linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());

            }


            List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(tempCustomer.getCusLoyaltyId(),tempCustomer.getCusMerchantNo());

            for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

                customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

            }


            List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(tempCustomer.getCusMerchantNo(),tempCustomer.getCusLoyaltyId());

            for (CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

                customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

            }


            List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(tempCustomer.getCusCustomerNo());

            for ( LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

                linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

            }


            PrimaryLoyalty  primaryLoyalty =  primaryLoyaltyService.findByPllLoyaltyId(tempCustomer.getCusLoyaltyId());

            if ( primaryLoyalty !=  null )
                primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());


            CustomerProfile customerProfile=customerProfileService.findByCspCustomerNo(tempCustomer.getCusCustomerNo());

            if(customerProfile!=null){
                customerProfileService.deleteCustomerProfile(customerProfile);
            }

            User user=userService.findByUsrLoginId(customer.getCusLoyaltyId());

            userService.deleteUser(user);

            customerService.deleteCustomer(tempCustomer.getCusCustomerNo());

        }



    }

}
