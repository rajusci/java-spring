package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.DrawChanceService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.DrawChanceFixture;
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
 * Created by saneesh-ci on 27/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class DrawChanceServiceTest {


    private static Logger log = LoggerFactory.getLogger(DrawChanceServiceTest.class);

    @Autowired
    private DrawChanceService drawChanceService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<DrawChance> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        DrawChance drawChance = drawChanceService.saveDrawChance(DrawChanceFixture.standardDrawChance());
        log.info(drawChance.toString());

        // Add the items
        tempSet.add(drawChance);

        Assert.assertNotNull(drawChance.getDrcId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        DrawChance drawChance = drawChanceService.saveDrawChance(DrawChanceFixture.standardDrawChance());
        drawChance=drawChanceService.findByDrcId(drawChance.getDrcId());
        tempSet.add(drawChance);
        log.info("drawChance select data"+drawChance.getDrcId());

        DrawChance updatedDrawChance = DrawChanceFixture.updatedStandardDrawChance(drawChance);
        log.info("Updated DrawChance "+ updatedDrawChance.toString());
        updatedDrawChance = drawChanceService.validateAndSaveDrawChance(updatedDrawChance);
//        tempSet.add(drawChance);
        log.info("Updated DrawChance "+ updatedDrawChance.toString());

    }

    @Test
    public void test3FindByDrcId() throws InspireNetzException {

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceService.saveDrawChance(drawChance);
        log.info("Original DrawChances " + drawChance.toString());

        // Add the items
        tempSet.add(drawChance);

        DrawChance searchRequest = drawChanceService.findByDrcId(drawChance.getDrcId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(drawChance.getDrcId().longValue() == searchRequest.getDrcId().longValue());



    }
    @Test
    public void test3FindByDrcCustomerNoAndType() throws InspireNetzException {

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceService.saveDrawChance(drawChance);
        log.info("Original DrawChances " + drawChance.toString());

        // Add the items
        tempSet.add(drawChance);

        DrawChance searchRequest = drawChanceService.getCustomerDrawChances(drawChance.getDrcCustomerNo(), DrawType.RAFFLE_TICKET);
        Assert.assertNotNull(searchRequest);


    }


    @Test
    public void test5DeleteDrawChances() throws InspireNetzException {

        // Create the drawChance

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance  drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceService.saveDrawChance(drawChance);
        Assert.assertNotNull(drawChance.getDrcId());
        log.info("DrawChances created");

        // call the delete drawChance
        drawChanceService.deleteDrawChance(drawChance.getDrcId());

        // Get the link request again
        DrawChance drawChance1 = drawChanceService.findByDrcId(drawChance.getDrcId());
        Assert.assertNull(drawChance1);;


    }


    @Test
    public void test6SaveAndValidateDrawChance() throws InspireNetzException {

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceService.validateAndSaveDrawChance(drawChanceFixture.standardDrawChance());
        log.info(drawChance.toString());

        // Add the items
        tempSet.add(drawChance);

        Assert.assertNotNull(drawChance.getDrcId());

    }

    @Test
    public void test7GetDrcType(){

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceService.saveDrawChance(drawChance);
        tempSet.add(drawChance);

        drawChance.setDrcId(null);
        drawChance.setDrcChances(10L);
        drawChance = drawChanceService.saveDrawChance(drawChance);

        // Add to the tempSet
        tempSet.add(drawChance);

        // Get the data
        List<DrawChance> searchDrawChances = drawChanceService.findByDrcType( DrawType.RAFFLE_TICKET);
        Assert.assertTrue(searchDrawChances.size()>0);
        log.info("Searched DrawChance : " + searchDrawChances);


    }

    @Test
    public void expiringDrawChance() throws InspireNetzException {

        //saving customer
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusLoyaltyId("45601274910");

        customerService.saveCustomer(customer);

        customerSet.add(customer);

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();

        drawChance.setDrcCustomerNo(customer.getCusCustomerNo());

        drawChance.setDrcChances(30L);

        drawChance = drawChanceService.saveDrawChance(drawChance);
        tempSet.add(drawChance);

        //expiring draw chance
        drawChanceService.expiringDrawChance(customer);

        //get draw chance information after expiring
        DrawChance drawChance1=drawChanceService.findByDrcId(drawChance.getDrcId());

        log.info("No of chance:"+drawChance1.getDrcChances());



    }



    @Test
    public void getDrawChancesByLoyaltyId() throws InspireNetzException {



        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusLoyaltyId("4560127910");

        customerService.saveCustomer(customer);

        customerSet.add(customer);

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance.setDrcCustomerNo(customer.getCusCustomerNo());

        drawChance = drawChanceService.saveDrawChance(drawChance);


        log.info("Original DrawChances " + drawChance.toString());

        // Add the items
        tempSet.add(drawChance);

        DrawChance searchRequest = drawChanceService.getDrawChancesByLoyaltyId(customer.getCusLoyaltyId(),DrawType.RAFFLE_TICKET);

        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(drawChance.getDrcId().longValue() == searchRequest.getDrcId().longValue());



    }


    @After
    public void tearDown() throws InspireNetzException {
        for(DrawChance drawChance: tempSet) {

            drawChanceService.deleteDrawChance(drawChance.getDrcId());

        }

        for(Customer customer:customerSet){


            customerService.deleteCustomer(customer.getCusCustomerNo());
        }


    }


}
