package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.service.TransactionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.TransactionFixture;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class TransactionControllerTest {


    private static Logger log = LoggerFactory.getLogger(TransactionControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Transaction object
    private Transaction transaction;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<Transaction> tempSet =new HashSet<>();




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

        // Create the transaction
        transaction = TransactionFixture.standardTransaction();


    }




    @Test
    public void listTransactions() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/transactions/" + transaction.getTxnLoyaltyId() + "/1980-01-01/9999-12-31")

                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransactionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getTransactionInfo() throws Exception  {

        // Save the transaction
        transaction = transactionService.saveTransaction(transaction);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/transaction/1")

                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransactionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCustomerTransactionInfo() throws Exception  {

        // Save the transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionService.saveTransaction(transaction);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/transactions/1/9999-12-31/9999-12-31")

                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransactionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCompatibleTransaction() throws Exception  {

        // Save the transaction
        Transaction transaction = TransactionFixture.standardTransaction();

        transaction.setTxnLoyaltyId("3333444455556661");

        transaction.setTxnType(TransactionType.CASHBACK);

        transaction = transactionService.saveTransaction(transaction);

        tempSet.add(transaction);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/transaction/transactionlist")

                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardnumber",transaction.getTxnLoyaltyId())
                .param("list_type","2")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransactionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @Test
    public void sendTransactionListSMS() throws Exception  {

        // Save the transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionService.saveTransaction(transaction);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/transactions/sms/"+transaction.getTxnLoyaltyId())

                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransactionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCustomerTransaction() throws Exception  {

        // Save the transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionService.saveTransaction(transaction);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/transaction/"+transaction.getTxnId()+"/1")

                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransactionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() throws InspireNetzException {

        for(Transaction transaction1:tempSet){


            transactionService.deleteTransaction(transaction1.getTxnId());
        }

    }

}
