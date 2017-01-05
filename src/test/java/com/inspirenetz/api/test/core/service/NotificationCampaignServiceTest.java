package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.NotificationCampaignService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.NotificationCampaignResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.NotificationCampaignFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class NotificationCampaignServiceTest {


    private static Logger log = LoggerFactory.getLogger(NotificationCampaignServiceTest.class);

    @Autowired
    private NotificationCampaignService notificationCampaignService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Mapper mapper ;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<NotificationCampaign> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        NotificationCampaign notificationCampaign = notificationCampaignService.saveNotificationCampaign(NotificationCampaignFixture.standardNotificationCampaign());
        log.info(notificationCampaign.toString());

        // Add the items
        tempSet.add(notificationCampaign);

        Assert.assertNotNull(notificationCampaign.getNtcId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        NotificationCampaign notificationCampaign = notificationCampaignService.saveNotificationCampaign(NotificationCampaignFixture.standardNotificationCampaign());
        notificationCampaign=notificationCampaignService.findByNtcId(notificationCampaign.getNtcId());
        tempSet.add(notificationCampaign);
        log.info("notificationCampaign select data"+notificationCampaign.getNtcId());

        NotificationCampaign updatedNotificationCampaign = NotificationCampaignFixture.updatedStandardNotificationCampaign(notificationCampaign);
        log.info("Updated NotificationCampaign "+ updatedNotificationCampaign.toString());
        updatedNotificationCampaign = notificationCampaignService.saveNotificationCampaign(updatedNotificationCampaign);
//        tempSet.add(notificationCampaign);
        log.info("Updated NotificationCampaign "+ updatedNotificationCampaign.toString());

    }

    @Test
    public void test3FindByNtcId() throws InspireNetzException {

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign notificationCampaign = notificationCampaignFixture.standardNotificationCampaign();
        notificationCampaign = notificationCampaignService.saveNotificationCampaign(notificationCampaign);
        log.info("Original NotificationCampaigns " + notificationCampaign.toString());

        // Add the items
        tempSet.add(notificationCampaign);

        NotificationCampaign searchRequest = notificationCampaignService.findByNtcId(notificationCampaign.getNtcId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(notificationCampaign.getNtcId().longValue() == searchRequest.getNtcId().longValue());



    }

    @Test
    public void test5DeleteNotificationCampaigns() throws InspireNetzException {

        // Create the notificationCampaign

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign  notificationCampaign = notificationCampaignFixture.standardNotificationCampaign();
        notificationCampaign = notificationCampaignService.saveNotificationCampaign(notificationCampaign);
        Assert.assertNotNull(notificationCampaign.getNtcId());
        log.info("NotificationCampaigns created");

        // call the delete notificationCampaign
        notificationCampaignService.deleteNotificationCampaign(notificationCampaign.getNtcId());

        // Get the link request again
        NotificationCampaign notificationCampaign1 = notificationCampaignService.findByNtcId(notificationCampaign.getNtcId());
        Assert.assertNull(notificationCampaign1);;


    }


    @Test
    public void test6SaveAndValidateNotificationCampaign() throws InspireNetzException {

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaignResource notificationCampaignResource=mapper.map(notificationCampaignFixture.standardNotificationCampaign(), NotificationCampaignResource.class);

        NotificationCampaign notificationCampaign = notificationCampaignService.validateAndSaveNotificationCampaign(notificationCampaignResource);
        log.info(notificationCampaign.toString());

        // Add the items
        tempSet.add(notificationCampaign);

        Assert.assertNotNull(notificationCampaign.getNtcId());

    }

    @Test
    public void test7FindByNtcNameAndNtcMerchantNo(){

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign notificationCampaign = notificationCampaignFixture.standardNotificationCampaign();
        notificationCampaign = notificationCampaignService.saveNotificationCampaign(notificationCampaign);
        tempSet.add(notificationCampaign);

        // Get the data
        Page<NotificationCampaign> searchNotificationCampaigns = notificationCampaignService.findByNtcNameAndNtcMerchantNo(notificationCampaign.getNtcName(), notificationCampaign.getNtcMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(searchNotificationCampaigns.hasContent());
        Set<NotificationCampaign> notificationCampaignSet = Sets.newHashSet((Iterable<NotificationCampaign>) searchNotificationCampaigns);
        log.info("Searched NotificationCampaign " + notificationCampaignSet.toString());


    }

    @After
    public void tearDown() throws InspireNetzException {
        for(NotificationCampaign notificationCampaign: tempSet) {

            notificationCampaignService.deleteNotificationCampaign(notificationCampaign.getNtcId());

        }

        for(Customer customer:customerSet){


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
        return new Sort(Sort.Direction.ASC, "ntcId");
    }

}
