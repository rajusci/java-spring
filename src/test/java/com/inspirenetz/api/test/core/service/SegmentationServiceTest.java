package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.segmentation.Segmentation;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class SegmentationServiceTest {



    private static Logger log = LoggerFactory.getLogger(SegmentationServiceTest.class);

    @Autowired
    private SegmentationService segmentationService;

    @Autowired
    private CustomerSegmentService customerSegmentService;

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomerSummaryArchiveService customerSummaryArchiveService;




    private CustomerSegment customerSegment;

    private Customer customer;

    private CustomerProfile customerProfile;



    @Before
    public void setUp() throws InspireNetzException {

        // Get the CustomerSegment
        customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment = customerSegmentService.saveCustomerSegment(customerSegment);

        // Create the Customer
        customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);

        // Create the customer profile
        customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

    }

    @Test
    public void test1AddSegmentMember() throws InspireNetzException {


        // Get the segment
        boolean isSegmentMemberAdded = segmentationService.addSegmentMember(customerSegment,customer);
        Assert.assertTrue(isSegmentMemberAdded);
        log.info("SegmentMember added");

    }


    @Test
    public void test2ProcessCustomer() throws InspireNetzException {

        // Make the segment as static
        customerSegment.setCsgSegmentType(CustomerSegmentType.STATIC);
        customerSegment.setCsgGenderEnabledInd(IndicatorStatus.YES);
        customerSegment.setCsgGenderValues(Gender.FEMALE);
        customerSegment = customerSegmentService.saveCustomerSegment(customerSegment);


        // Change the customer profile
        customerProfile.setCspGender(Gender.MALE);
        customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        // Get the Segmentation
        Segmentation segmentation = customerSegmentService.getSegmentationForCustomerSegment(customerSegment);

        // process the cusotmer
        boolean isProcessed = segmentationService.processCustomer(customerSegment,customer,segmentation);
        Assert.assertTrue(isProcessed);
        log.info("customer processed");


    }


    @Test
    public void test3ProcessStaticSegmentation() throws InspireNetzException {

        // Make the segment as static
        customerSegment.setCsgSegmentType(CustomerSegmentType.STATIC);
        customerSegment.setCsgGenderEnabledInd(IndicatorStatus.YES);
        customerSegment.setCsgGenderValues(Gender.MALE);
        customerSegment = customerSegmentService.saveCustomerSegment(customerSegment);


        // Change the customer profile
        customerProfile.setCspGender(Gender.MALE);
        customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        // Process
        boolean isProcessed = segmentationService.processSegmentation(customerSegment);
        Assert.assertTrue(isProcessed);
        log.info("Static Segmentation Processing completed");

    }


    @Test
    public void test4ProcessDynamicSegmentation() throws InspireNetzException {

        // Make the segment as static
        customerSegment.setCsgSegmentType(CustomerSegmentType.DYNAMIC);
        customerSegment.setCsgCriteriaType(CustomerSegmentCriteriaType.YEARLY_STATS);
        customerSegment.setCsgYear(2014);
        customerSegment.setCsgAmountCompType(CustomerSegmentComparisonType.MORE_THAN);
        customerSegment.setCsgAmountCompValue1(20000);
        customerSegment.setCsgVisitCompType(CustomerSegmentComparisonType.MORE_THAN);
        customerSegment.setCsgVisitCompValue1(5);

        customerSegment = customerSegmentService.saveCustomerSegment(customerSegment);

        // Process
        boolean isProcessed = segmentationService.processSegmentation(customerSegment);
        Assert.assertTrue(isProcessed);
        log.info("Static Segmentation Processing completed");

    }



    @Test
    public void test5RemoveLowerTiersSegments() throws InspireNetzException {

        // Create the second tier segment
        CustomerSegment customerSegment2 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment2.setCsgSegmentName("Test Tiered segment2");
        customerSegment2.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment2.setCsgAmountCompValue1(1001);
        customerSegment2.setCsgAmountCompValue2(2000);
        customerSegment2.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment2 = customerSegmentService.saveCustomerSegment(customerSegment2);



        // Create the fourth tier segment
        CustomerSegment customerSegment4 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment4.setCsgSegmentName("Test Tiered segment4");
        customerSegment4.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment4.setCsgAmountCompValue1(3001);
        customerSegment4.setCsgAmountCompValue2(4000);
        customerSegment4.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment4 = customerSegmentService.saveCustomerSegment(customerSegment4);

        // Create the first tier segment
        CustomerSegment customerSegment1 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment1.setCsgSegmentName("Test Tiered segment1");
        customerSegment1.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment1.setCsgAmountCompValue1(0);
        customerSegment1.setCsgAmountCompValue2(1000);
        customerSegment1.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment1 = customerSegmentService.saveCustomerSegment(customerSegment1);



        // Create the third tier segment
        CustomerSegment customerSegment3 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment3.setCsgSegmentName("Test Tiered segment3");
        customerSegment3.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment3.setCsgAmountCompValue1(2001);
        customerSegment3.setCsgAmountCompValue2(3000);
        customerSegment3.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment3 = customerSegmentService.saveCustomerSegment(customerSegment3);



        // Create the SegmentMember for the customer as customersegment2
        SegmentMember segmentMember = new SegmentMember();
        segmentMember.setSgmSegmentId(customerSegment2.getCsgSegmentId());
        segmentMember.setSgmCustomerNo(customer.getCusCustomerNo());
        segmentMember = segmentMemberService.saveSegmentMember(segmentMember);


        // Get the list of all the segments for the customer from the SegmentMemberService
        List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(customer.getCusCustomerNo());

        // Convert the list into map
        Map<Long,SegmentMember> segmentMemberMap = segmentMemberService.getSegmentMemberMapBySegmentId(segmentMemberList);


        // Get the lower tiers
        segmentationService.removeLowerTierSegments(customer,customerSegment4,segmentMemberMap);


    }


    @Test
    public void test6RefreshCustomerMemberSegments() throws InspireNetzException {


        // Create the second tier segment
        CustomerSegment customerSegment2 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment2.setCsgSegmentName("Test Tiered segment2");
        customerSegment2.setCsgCriteriaType(CustomerSegmentCriteriaType.OVERALL_STATS);
        customerSegment2.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment2.setCsgAmountCompValue1(1001);
        customerSegment2.setCsgAmountCompValue2(2000);
        customerSegment2.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment2 = customerSegmentService.saveCustomerSegment(customerSegment2);



        // Create the fourth tier segment
        CustomerSegment customerSegment4 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment4.setCsgSegmentName("Test Tiered segment4");
        customerSegment4.setCsgCriteriaType(CustomerSegmentCriteriaType.OVERALL_STATS);
        customerSegment4.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment4.setCsgAmountCompValue1(3001);
        customerSegment4.setCsgAmountCompValue2(4000);
        customerSegment4.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment4 = customerSegmentService.saveCustomerSegment(customerSegment4);

        // Create the first tier segment
        CustomerSegment customerSegment1 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment1.setCsgSegmentName("Test Tiered segment1");
        customerSegment1.setCsgCriteriaType(CustomerSegmentCriteriaType.OVERALL_STATS);
        customerSegment1.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment1.setCsgAmountCompValue1(0);
        customerSegment1.setCsgAmountCompValue2(1000);
        customerSegment1.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment1 = customerSegmentService.saveCustomerSegment(customerSegment1);



        // Create the third tier segment
        CustomerSegment customerSegment3 = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment3.setCsgSegmentName("Test Tiered segment3");
        customerSegment3.setCsgCriteriaType(CustomerSegmentCriteriaType.OVERALL_STATS);
        customerSegment3.setCsgAmountCompType(CustomerSegmentComparisonType.BETWEEN);
        customerSegment3.setCsgAmountCompValue1(2001);
        customerSegment3.setCsgAmountCompValue2(3000);
        customerSegment3.setCsgAutoUpgradeSegment(IndicatorStatus.YES);
        customerSegment3 = customerSegmentService.saveCustomerSegment(customerSegment3);



        // Create the SegmentMember for the customer as customersegment2
        SegmentMember segmentMember = new SegmentMember();
        segmentMember.setSgmSegmentId(customerSegment2.getCsgSegmentId());
        segmentMember.setSgmCustomerNo(customer.getCusCustomerNo());
        segmentMember = segmentMemberService.saveSegmentMember(segmentMember);



        // Create the customersummary archive
        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();
        customerSummaryArchive.setCsaLocation(0L);
        customerSummaryArchive.setCsaPeriodYyyy(0);
        customerSummaryArchive.setCsaPeriodQq(0);
        customerSummaryArchive.setCsaPeriodMm(0);
        customerSummaryArchive.setCsaTxnAmount(2500);
        customerSummaryArchiveService.saveCustomerSummaryArchive(customerSummaryArchive);



        // Refresh the segments
        segmentationService.refreshCustomerMemberSegments(customer);


    }

    @Test
    public void test4assignSegment() throws InspireNetzException {

        // Make the segment as static
        customerSegment.setCsgSegmentType(CustomerSegmentType.STATIC);
        customerSegment.setCsgGenderEnabledInd(IndicatorStatus.YES);
        customerSegment.setCsgGenderValues(Gender.MALE);
        customerSegment.setCsgSegmentName("Test Segment 123");
       /* customerSegment = customerSegmentService.saveCustomerSegment(customerSegment);*/

        SegmentMember segmentMember = segmentMemberService.assignCustomerToSegment(customer.getCusLoyaltyId(),customerSegment.getCsgSegmentName(),customer.getCusMerchantNo());
        Assert.assertTrue(segmentMember.getSgmId() != null);

        log.info("Created segment : "+segmentMember);

    }




    @After
    public void tearDown() throws Exception {


        Set<CustomerSegment> customerSegments = CustomerSegmentFixture.standardCustomerSegments();

        for(CustomerSegment customerSegment: customerSegments) {

            CustomerSegment delCustomerSegment = customerSegmentService.findByCsgMerchantNoAndCsgSegmentName(customerSegment.getCsgMerchantNo(),customerSegment.getCsgSegmentName());

            if ( delCustomerSegment != null ) {
                customerSegmentService.deleteCustomerSegment(delCustomerSegment.getCsgSegmentId());
            }

        }


        Set<CustomerProfile> customerProfiles = CustomerProfileFixture.standardCustomerProfiles();

        for(CustomerProfile customerProfile: customerProfiles) {

            customerProfileService.deleteCustomerProfile(customerProfile);

        }


        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: customers) {

            Customer delCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            if ( delCustomer != null ) {
                customerService.deleteCustomer(delCustomer.getCusCustomerNo());
            }

        }


        Set<SegmentMember> segmentMembers = SegmentMemberFixture.standardSegmentMembers();

        for(SegmentMember segmentMember: segmentMembers) {

            SegmentMember delSegmentMember = segmentMemberService.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(), segmentMember.getSgmCustomerNo());

            if ( delSegmentMember != null ) {
                segmentMemberService.deleteSegmentMember(delSegmentMember.getSgmId());
            }

        }

    }

}
