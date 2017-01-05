package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.repository.SaleRepository;
import com.inspirenetz.api.core.service.SaleSKUService;
import com.inspirenetz.api.core.service.SaleService;
import com.inspirenetz.api.core.service.TransactionService;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.EBillResource;
import com.inspirenetz.api.rest.resource.SaleSKUResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.SaleFixture;
import com.inspirenetz.api.test.core.fixture.SaleSKUFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import org.antlr.grammar.v3.ANTLRv3Parser;
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
import org.springframework.mock.web.MockHttpServletResponse;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class SaleControllerTest {


    private static Logger log = LoggerFactory.getLogger(SaleControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private SaleService saleService;

    // Only used for the teardown of test data.
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SaleSKUService saleSKUService;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Sale object
    private Sale sale;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    // TempSet
    Set<Sale> tempSet = new HashSet<>(0);

    // hashSet for tear down
    Set<Sale> hashSet = new HashSet<>(0);


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

        // Create the sale
        sale = SaleFixture.standardSale();

    }



    @Test
    public void saveSale() throws Exception {

        // Create a Sale object
        Sale sale = SaleFixture.standardSale();

        // Create a SaleSKU bomect
        SaleSKU saleSKU1 = SaleSKUFixture.standardSaleSku();
        saleSKU1.setSsuSaleId(null);
        SaleSKU saleSKU2 = SaleSKUFixture.standardSaleSku();
        saleSKU2.setSsuSaleId(null);

        // Add to the list
        Set<SaleSKU> saleSKUSet = new HashSet<>(0);
        saleSKUSet.add(saleSKU1);
        saleSKUSet.add(saleSKU2);

        sale.setSaleSKUSet(saleSKUSet);

        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String saleData = objectMapper.writeValueAsString(sale);
        log.info("JSON string : " + saleData);

        // Convert sale to

        /*
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/sale")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("salLoyaltyId", sale.getSalLoyaltyId())
                                                .param("salAmount", Double.toString(sale.getSalAmount()))
                                                .param("salDate","2014-07-02")
                                                .param("salChannel","My Channel")
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);
        */


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/sale")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(saleData)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        // Get the salID
        sale = saleService.findBySalId(Long.parseLong(map.get("data")));
        tempSet.add(sale);


        log.info("Sale created");


    }


    /*

    @Test
    public void saveSaleCompatible() throws Exception {



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/addsale")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardnumber", sale.getSalLoyaltyId())
                .param("sale_amount", Double.toString(sale.getSalAmount()))
                .param("date", sale.getSalDate().toString())
                .param("txnref", sale.getSalPaymentReference())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Sale created");


    }

    @Test
    public void deleteSale() throws  Exception {

        // Create the sale
        sale = saleService.saveSale(sale);
        Assert.assertNotNull(sale.getSalId());
        log.info("Sale created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/sale/delete")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("salId", sale.getSalId().toString())
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Sale deleted");


    }

    */

    @Test
    public void searchSale() throws Exception  {

        // Get the set of sales
        Set<Sale> saleSet = SaleFixture.standardSales();
        List<Sale>  saleList = new ArrayList<Sale>();
        saleList.addAll(saleSet);
        saleService.saveAll(saleList);

        tempSet.addAll(saleList);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sales/ontest/loyaltyid/" + sale.getSalLoyaltyId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getSaleInfo() throws Exception  {

        sale.setSalType(SaleType.ITEM_BASED_PURCHASE);
        SaleSKU saleSKU1 = SaleSKUFixture.standardSaleSku();
        saleSKU1.setSsuSaleId(null);

        // Add to the list
        Set<SaleSKU> saleSKUSet = new HashSet<>(0);
        saleSKUSet.add(saleSKU1);

        // Add to the sale
        sale.setSaleSKUSet(saleSKUSet);

        // Get the set of sales
        sale = saleService.saveSale(sale);

        tempSet.add(sale);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sale/ontest/"+sale.getSalId().toString())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @Test
    public void test() throws Exception  {


        List<SaleSKU> saleSKUList = new ArrayList<>(0);

        SaleSKU saleSKU1 = SaleSKUFixture.standardSaleSku();
        SaleSKU saleSKU2 = SaleSKUFixture.standardSaleSku();

        saleSKUList.add(saleSKU1);
        saleSKUList.add(saleSKU2);


        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(saleSKUList);
        data = "{\"mydata\":\"test\",\"skudata\":"+data+"}";
        log.info("JSOn value is : " + data );


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sale/ontest/save")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(data)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void saveSaleCompatible() throws Exception  {

        Sale sale1 =SaleFixture.standardSale();
        sale1.setSalLoyaltyId("8867987369");

        sale1.setSalAmount(-100.00);

        sale1.setSalDate(DBUtils.covertToSqlDate("2015-12-02"));

        sale1.setSalTime(DBUtils.convertToSqlTime("01:00:00"));

       // tempSet.add(sale1);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/purchase")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardnumber",sale1.getSalLoyaltyId())
                .param("purchase_amount",sale1.getSalAmount().toString())
                .param("date",sale1.getSalDate().toString())
                .param("time",sale1.getSalTime().toString())

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SaleResponse: " + response);

        Integer lastIndex =response.indexOf(',');

        Integer dataIndex =response.indexOf("data")+6;

        String saleId =response.substring(dataIndex, lastIndex);

         sale = saleService.findBySalId(Long.parseLong(saleId));
         tempSet.add(sale);
        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void readSaleXML() throws Exception{

        //Initialize file object for xml
        File  file=new File("/media/fayiz-ci/B474B6EA74B6AE8C/Source/SALES/sales.txt");

        FileInputStream fileInputStream=new FileInputStream(file);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/xml/sku/salesmaster")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(ByteStreams.toByteArray(fileInputStream))

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("SaleResponse: " + response);

        //List<Sale> saleList=saleService.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(1L,"",DBUtils.covertToSqlDate("2015-3-6"),"CA4526755");

        //Assert.assertNotNull(saleList);

        //hashSet.add(saleList.get(0));

       // saleList=saleService.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(1L,"",DBUtils.covertToSqlDate("2015-3-8"),"CA45df256");

        //Assert.assertNotNull(saleList);

        //hashSet.add(saleList.get(0));



    }

    @Test
    public void sendSalesEBill() throws Exception {

        CustomerResource customerResource=new CustomerResource();
        customerResource.setCusFName("FAYIZ");
        customerResource.setCusLName("KOYISSAN");
        customerResource.setCusMobile("8867987369");
        customerResource.setCusEmail("fayizmuhamed@gmail.com");
        customerResource.setCusMerchantNo(1L);


        List<SaleSKUResource> saleSKUResources=new ArrayList<SaleSKUResource>();

        SaleSKUResource saleSKUResource=new SaleSKUResource();


        saleSKUResource.setPrdName("PEPPER SALT");
        saleSKUResource.setSsuPrice(300);
        saleSKUResource.setSsuQty(2);
        saleSKUResources.add(saleSKUResource);

        saleSKUResource=new SaleSKUResource();
        saleSKUResource.setPrdName("BADAM MILK");
        saleSKUResource.setSsuPrice(250);
        saleSKUResource.setSsuQty(3);
        saleSKUResources.add(saleSKUResource);

        com.inspirenetz.api.rest.resource.SaleResource saleResource=new com.inspirenetz.api.rest.resource.SaleResource();
        saleResource.setSalAmount(1000.00);
        java.util.Date today = new java.util.Date();
        saleResource.setSalMerchantNo(1L);
        saleResource.setSalPaymentReference("BILL0001");

        saleResource.setSaleSKUResourceList(saleSKUResources);

        HashMap<String,Object> params=new HashMap<String,Object>();
        params.put("flightNo","SG0001");
        params.put("seatNo","1C");
        params.put("pnr","123000023");


        EBillResource eBillResource=new EBillResource();
        eBillResource.setCustomerResource(customerResource);
        eBillResource.setSaleResource(saleResource);
        eBillResource.setAdditionalParams(params);

        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String eBillResourceData = objectMapper.writeValueAsString(eBillResource);
        log.info("JSON string : " + eBillResourceData);



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/sale/sendebill")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(eBillResourceData)
        )
           .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("sendSalesEBill: " + response);

        log.info("E-Bill send successfully");


    }





    @After
    public void tearDown() {

        for(Sale sale : tempSet ) {

            saleService.deleteSale(sale.getSalId());

        }

        for(Sale sale : hashSet ) {

            Transaction transaction=transactionService.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(sale.getSalMerchantNo(),sale.getSalLoyaltyId(),sale.getSalId().toString(),sale.getSalLocation(),sale.getSalDate());

            transactionService.deleteTransaction(transaction.getTxnId());

            List<SaleSKU> saleSKUs=saleSKUService.findBySsuSaleId(sale.getSalId());

            saleSKUService.deleteSaleSKUSet(new HashSet<SaleSKU>(saleSKUs));

            saleService.deleteSale(sale.getSalId());

        }


    }
}
