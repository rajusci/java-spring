package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.LinkRequestInitiator;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.repository.LinkRequestRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkRequestService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LinkRequestFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class LinkRequestServiceTest {


    private static Logger log = LoggerFactory.getLogger(LinkRequestServiceTest.class);

    @Autowired
    private LinkRequestService linkRequestService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    Set<LinkRequest> tempSet = new HashSet<>(0);

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


        LinkRequest linkRequest = linkRequestService.saveLinkRequest(LinkRequestFixture.standardLinkRequest());
        log.info(linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        Assert.assertNotNull(linkRequest.getLrqId());

    }

    @Test
    public void test2Update() {

        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        LinkRequest updatedLinkRequests = LinkRequestFixture.updatedStandardLinkRequests(linkRequest);
        updatedLinkRequests = linkRequestService.saveLinkRequest(updatedLinkRequests);
        log.info("Updated LinkRequests "+ updatedLinkRequests.toString());

    }

    @Test
    public void test3FindByLrqId() {


        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        LinkRequest searchRequest = linkRequestService.findByLrqId(linkRequest.getLrqId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(linkRequest.getLrqId().longValue() == searchRequest.getLrqId().longValue());



    }


    @Test
    public void test4SearchLinkRequests() throws InspireNetzException {

        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        Page<LinkRequest> linkRequestPage = linkRequestService.searchLinkRequests("0","0",linkRequest.getLrqMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(linkRequestPage);
        List<LinkRequest> linkRequestList = Lists.newArrayList((Iterable<LinkRequest>) linkRequestPage);
        log.info("Link Request List : "+ linkRequestList.toString());

    }

    @Test
    public void test5DeleteLinkRequests() {

        // Create the linkRequest
        LinkRequest  linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        Assert.assertNotNull(linkRequest.getLrqId());
        log.info("LinkRequests created");

        // call the delete linkRequest
        linkRequestService.deleteLinkRequest(linkRequest.getLrqId());

        // Get the link request again
        LinkRequest linkRequest1 = linkRequestService.findByLrqId(linkRequest.getLrqId());
        Assert.assertNull(linkRequest1);;


    }


    @Test
    public void test6SaveAndValidateLinkRequest() throws InspireNetzException {


        LinkRequest linkRequest = linkRequestService.validateAndSaveLinkRequest(LinkRequestFixture.standardLinkRequest());
        log.info(linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        Assert.assertNotNull(linkRequest.getLrqId());

    }


    @Test
    public void test7getLinkRequestInfo() throws InspireNetzException {


        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        LinkRequest searchRequest = linkRequestService.getLinkRequestInfo(linkRequest.getLrqId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(linkRequest.getLrqId().longValue() == searchRequest.getLrqId().longValue());

    }


    @Test
    public void test8DeleteLinkRequests() throws InspireNetzException {

        // Create the linkRequest
        LinkRequest  linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        Assert.assertNotNull(linkRequest.getLrqId());
        log.info("LinkRequests created");

        // call the delete linkRequest
        linkRequestService.validateAndDeleteLinkRequest(linkRequest.getLrqId());

        // Get the link request again
        LinkRequest linkRequest1 = linkRequestService.findByLrqId(linkRequest.getLrqId());
        Assert.assertNull(linkRequest1);;


    }


    @Test
    public void test9AddUnLinkRequest() throws InspireNetzException {


        LinkedLoyalty linkedLoyalty;

        LinkRequest linkRequest;


        // Create primary
        Customer primary = CustomerFixture.standardCustomer();
        primary = customerService.saveCustomer(primary);
        customerSet.add(primary);

        // Create child
        Customer child = CustomerFixture.standardCustomer();
        child.setCusLoyaltyId("9999888877776662");
        child = customerService.saveCustomer(child);
        customerSet.add(child);


        // Get the customerset
        linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilParentCustomerNo(primary.getCusCustomerNo());
        linkedLoyalty.setLilChildCustomerNo(child.getCusCustomerNo());
        linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

        linkLoyaltySet.add(linkedLoyalty);

        // call the delete linkRequest
        boolean isUnlinked = linkRequestService.unlinkAllRequest(primary.getCusLoyaltyId(),primary.getCusMerchantNo(),false);

        Assert.assertTrue(isUnlinked);


    }



    @After
    public void tearDown() throws InspireNetzException {

        for(LinkRequest linkRequest: tempSet) {

            linkRequestService.deleteLinkRequest(linkRequest.getLrqId());

        }


        for(LinkedLoyalty linkedLoyalty : linkLoyaltySet ) {

            linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

        }

        for(Customer customer : customerSet ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

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
        return new Sort(Sort.Direction.ASC, "lrqId");
    }

}
