package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PointTransferRequestService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.PointTransferRequestFixture;
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
public class PointTransferRequestServiceTest {


    private static Logger log = LoggerFactory.getLogger(PointTransferRequestServiceTest.class);

    @Autowired
    private PointTransferRequestService pointTransferRequestService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<PointTransferRequest> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create() throws InspireNetzException {

        PointTransferRequest pointTransferRequest = pointTransferRequestService.savePointTransferRequest(PointTransferRequestFixture.standardPointTransferRequest());
        log.info(pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        Assert.assertNotNull(pointTransferRequest.getPtrId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        PointTransferRequest pointTransferRequest = pointTransferRequestService.savePointTransferRequest(PointTransferRequestFixture.standardPointTransferRequest());
        pointTransferRequest=pointTransferRequestService.findByPtrId(pointTransferRequest.getPtrId());
        tempSet.add(pointTransferRequest);
        log.info("pointTransferRequest select data"+pointTransferRequest.getPtrId());

        PointTransferRequest updatedPointTransferRequest = PointTransferRequestFixture.updatedStandardPointTransferRequest(pointTransferRequest);
        log.info("Updated PointTransferRequest "+ updatedPointTransferRequest.toString());
        updatedPointTransferRequest = pointTransferRequestService.savePointTransferRequest(updatedPointTransferRequest);
//        tempSet.add(pointTransferRequest);
        log.info("Updated PointTransferRequest "+ updatedPointTransferRequest.toString());

    }

    @Test
    public void test3FindByPtrId() throws InspireNetzException {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);
        log.info("Original PointTransferRequests " + pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        PointTransferRequest searchRequest = pointTransferRequestService.findByPtrId(pointTransferRequest.getPtrId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(pointTransferRequest.getPtrId().longValue() == searchRequest.getPtrId().longValue());



    }
    @Test
    public void test3FindByPtrMerchantNo() throws InspireNetzException {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);
        log.info("Original PointTransferRequests " + pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        Page<PointTransferRequest> searchRequest = pointTransferRequestService.findByPtrMerchantNo(pointTransferRequest.getPtrMerchantNo(), constructPageSpecification(0));
        Assert.assertTrue(searchRequest.hasContent());


    }

    @Test
    public void test3FindByPtrMerchantNoAndPtrStatus() throws InspireNetzException {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);
        log.info("Original PointTransferRequests " + pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        Page<PointTransferRequest> searchRequest = pointTransferRequestService.findByPtrMerchantNoAndPtrStatus(pointTransferRequest.getPtrMerchantNo(), pointTransferRequest.getPtrStatus(), constructPageSpecification(0));
        Assert.assertTrue(searchRequest.hasContent());


    }

    @Test
    public void test3FindByPtrMerchantNoAndPtrSourceAndPtrStatus() throws InspireNetzException {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);
        log.info("Original PointTransferRequests " + pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        Page<PointTransferRequest> searchRequest = pointTransferRequestService.findByPtrMerchantNoAndPtrSourceAndPtrStatus(pointTransferRequest.getPtrMerchantNo(), pointTransferRequest.getPtrSource(), pointTransferRequest.getPtrStatus(), constructPageSpecification(0));
        Assert.assertTrue(searchRequest.hasContent());


    }

    @Test
    public void test5DeletePointTransferRequests() throws InspireNetzException {

        // Create the pointTransferRequest

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest  pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);
        Assert.assertNotNull(pointTransferRequest.getPtrId());
        log.info("PointTransferRequests created");

        // call the delete pointTransferRequest
        pointTransferRequestService.deletePointTransferRequest(pointTransferRequest.getPtrId());

        // Get the link request again
        PointTransferRequest pointTransferRequest1 = pointTransferRequestService.findByPtrId(pointTransferRequest.getPtrId());
        Assert.assertNull(pointTransferRequest1);;


    }

    @Test
    public void test6FindByPtrMerchantNoAndPtrSource() throws InspireNetzException {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();

        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);

        log.info("Original PointTransferRequests " + pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        Page<PointTransferRequest> searchRequest = pointTransferRequestService.findByPtrMerchantNoAndPtrSource(pointTransferRequest.getPtrMerchantNo(), pointTransferRequest.getPtrSource(), constructPageSpecification(0));

        Assert.assertTrue(searchRequest.hasContent());

    }

    @Test
    public void test7SearchPointTransferRequest() throws InspireNetzException {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();

        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);

        log.info("Original PointTransferRequests " + pointTransferRequest.toString());

        // Add the items
        tempSet.add(pointTransferRequest);

        Page<PointTransferRequest> searchRequest = pointTransferRequestService.searchPointTransferRequest("source", pointTransferRequest.getPtrSource(), pointTransferRequest.getPtrMerchantNo(),constructPageSpecification(0) );

        log.info("Searched PointTransferRequests " + searchRequest.toString());

        Assert.assertTrue(searchRequest.hasContent());

    }



    @After
    public void tearDown(){
        for(PointTransferRequest pointTransferRequest: tempSet) {

            try {
                pointTransferRequestService.deletePointTransferRequest(pointTransferRequest.getPtrId());
            } catch (InspireNetzException e) {
                e.printStackTrace();
            }

        }

        for(Customer customer:customerSet){


            try {
                customerService.deleteCustomer(customer.getCusCustomerNo());
            } catch (InspireNetzException e) {
                e.printStackTrace();
            }
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
