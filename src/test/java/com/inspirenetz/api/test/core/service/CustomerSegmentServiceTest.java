package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.repository.CustomerSegmentRepository;
import com.inspirenetz.api.core.service.CustomerSegmentService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerSegmentFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class CustomerSegmentServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerSegmentServiceTest.class);

    @Autowired
    private CustomerSegmentService customerSegmentService;

    @Autowired
    private CustomerSegmentRepository customerSegmentRepository;


    @Before
    public void setUp() {}



    @Test
    public void test3FindByCsgMerchantNo() {

        // Get the standard customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();

        Page<CustomerSegment> customerSegments = customerSegmentService.findByCsgMerchantNo(customerSegment.getCsgMerchantNo(),constructPageSpecification(0));
        log.info("customerSegments by merchant no " + customerSegments.toString());
        Set<CustomerSegment> customerSegmentSet = Sets.newHashSet((Iterable<CustomerSegment>)customerSegments);
        log.info("customerSegment list "+customerSegmentSet.toString());

    }

    @Test
    public void test4FindByCsgMerchantNoAndCsgSegmentName() throws InspireNetzException {

        // Create the customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegmentService.saveCustomerSegment(customerSegment);
        Assert.assertNotNull(customerSegment.getCsgSegmentId());
        log.info("CustomerSegment created");

        CustomerSegment fetchCustomerSegment = customerSegmentService.findByCsgMerchantNoAndCsgSegmentName(customerSegment.getCsgMerchantNo(),customerSegment.getCsgSegmentName());
        Assert.assertNotNull(fetchCustomerSegment);
        log.info("Fetched customerSegment info" + customerSegment.toString());

    }


    @Test
    public void test5FindByCsgMerchantNoAndCsgSegmentNameLike() throws InspireNetzException {

        // Create the customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegmentService.saveCustomerSegment(customerSegment);
        Assert.assertNotNull(customerSegment.getCsgSegmentId());
        log.info("CustomerSegment created");

        // Check the customerSegment name
        Page<CustomerSegment> customerSegments = customerSegmentService.findByCsgMerchantNoAndCsgSegmentNameLike(customerSegment.getCsgMerchantNo(),"%dynamic%",constructPageSpecification(0));
        Assert.assertTrue(customerSegments.hasContent());
        Set<CustomerSegment> customerSegmentSet = Sets.newHashSet((Iterable<CustomerSegment>)customerSegments);
        log.info("customerSegment list "+customerSegmentSet.toString());


    }


    @Test
    public void test6IsDuplicateSegmentNameExisting() throws InspireNetzException {

        // Create the customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegmentService.saveCustomerSegment(customerSegment);
        Assert.assertNotNull(customerSegment.getCsgSegmentId());
        log.info("CustomerSegment created");

        // Check the customerSegment name
        CustomerSegment newSegment = CustomerSegmentFixture.standardCustomerSegment();
        boolean exists = customerSegmentService.isDuplicateSegmentNameExisting(newSegment);
        Assert.assertTrue(exists);;
        log.info("Customer Segment Information: "+newSegment.toString());


    }


    @Test
    public void test7GetCustomerSegmentMap() throws InspireNetzException {

        // Create the customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegmentService.saveCustomerSegment(customerSegment);
        Assert.assertNotNull(customerSegment.getCsgSegmentId());
        log.info("CustomerSegment created");

        // Get the customer segment list
        List<CustomerSegment> customerSegmentList = customerSegmentService.findByCsgMerchantNo(customerSegment.getCsgMerchantNo());
        Map<Long,CustomerSegment>  customerSegmentMap = customerSegmentService.getCustomerSegmentMap(customerSegmentList);
        Assert.assertNotNull(customerSegmentMap);
        Assert.assertTrue(!customerSegmentMap.isEmpty());
        log.info("CustomerSegment Information : "+customerSegmentMap.toString());

    }


    @Test
    public void test8GetUpgradeTiers() throws InspireNetzException {


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

        // Now get the tiers for the customersegment 1
        List<CustomerSegment> tiers = customerSegmentService.getUpgradeTiers(customerSegment1);
        Assert.assertNotNull(tiers);
        log.info("Number of segments "+tiers.size());
        log.info("Tier segments" + tiers.toString());



    }


    @Test
    public void test9GetLowerTierSegments() throws InspireNetzException {

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

        // Now get the tiers for the customersegment 1
        List<CustomerSegment> tiers = customerSegmentService.getUpgradeTiers(customerSegment1);
        Assert.assertNotNull(tiers);
        log.info("Number of segments "+tiers.size());
        log.info("Tier segments" + tiers.toString());


        // Get the lower tiers for the segment 3
        List<CustomerSegment> lowerTiers = customerSegmentService.getLowerTierSegments(tiers,customerSegment2);
        Assert.assertNotNull(lowerTiers);
        log.info("Number of lower segments : " + lowerTiers.size());
        log.info("Lower tier segments : " + lowerTiers.toString());
    }


    @After
    public void tearDown() throws InspireNetzException {

        Set<CustomerSegment> customerSegments = CustomerSegmentFixture.standardCustomerSegments();

        for(CustomerSegment customerSegment: customerSegments) {

            CustomerSegment delCustomerSegment = customerSegmentService.findByCsgMerchantNoAndCsgSegmentName(customerSegment.getCsgMerchantNo(),customerSegment.getCsgSegmentName());

            if ( delCustomerSegment != null ) {
                customerSegmentService.deleteCustomerSegment(delCustomerSegment.getCsgSegmentId());
            }

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
        return new Sort(Sort.Direction.ASC, "csgSegmentName");
    }


}
