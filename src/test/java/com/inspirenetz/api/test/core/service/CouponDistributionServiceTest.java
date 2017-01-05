package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.CouponDistributionStatus;
import com.inspirenetz.api.core.dictionary.CouponDistributionType;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.repository.CouponDistributionRepository;
import com.inspirenetz.api.core.service.CouponDistributionService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CouponDistributionResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CouponDistributionFixture;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CouponDistributionServiceTest {


    private static Logger log = LoggerFactory.getLogger(CouponDistributionServiceTest.class);

    @Autowired
    private CouponDistributionService couponDistributionService;

    @Autowired
    private CouponDistributionRepository couponDistributionRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    Mapper mapper;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }


    @Test
    public void test1ValidateAndSavePromotion() throws InspireNetzException{



        // Get the standard promotion
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();

        couponDistribution.setCodDistributionType(CouponDistributionType.MEMBERS);

        couponDistribution.setCodCustomerSegments("69");
        couponDistribution.setCodCustomerIds("8867987369");
        couponDistribution.setCodSmsContent("Your Coupon Code is <coupon>");
        couponDistribution.setCodEmailSubject("Coupon Code");
        couponDistribution.setCodEmailContent("<b>Your</b> Coupon Code is <coupon>");

        CouponDistributionResource couponDistributionResource=mapper.map(couponDistribution,CouponDistributionResource.class);

        couponDistribution=couponDistributionService.validateAndSaveCouponDistribution(couponDistributionResource);

        log.info(couponDistribution.toString());


        Assert.assertNotNull(couponDistribution.getCodId());

    }


    @Test
    public void test1FindByMerchantNo() {

        // Get the standard couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();

        Page<CouponDistribution> couponDistributions = couponDistributionService.findByCodMerchantNo(couponDistribution.getCodMerchantNo(),constructPageSpecification(0));
        log.info("couponDistributions by merchant no " + couponDistributions.toString());
        Set<CouponDistribution> couponDistributionSet = Sets.newHashSet((Iterable<CouponDistribution>) couponDistributions);
        log.info("couponDistribution list "+couponDistributionSet.toString());

    }



    @Test
    public void test2FindByCodMerchantNoAndCodCouponCode() throws InspireNetzException {

        // Create the couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");

        CouponDistribution fetchCouponDistribution = couponDistributionService.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(), couponDistribution.getCodCouponCode());
        Assert.assertNotNull(fetchCouponDistribution);
        log.info("Fetched couponDistribution info" + couponDistribution.toString());

    }

 
    @Test
    public void test4IsDuplicateCouponDistributionExisting() throws InspireNetzException {

        // Create the couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");

        // Create a new couponDistribution
        CouponDistribution newCouponDistribution = CouponDistributionFixture.standardCouponDistribution();
        boolean exists = couponDistributionService.isDuplicateCouponDistributionExisting(newCouponDistribution);
        Assert.assertTrue(exists);
        log.info("CouponDistribution exists");


    }


    @Test
    public void test5DeleteCouponDistribution() throws InspireNetzException {

        // Create the couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");

        // call the delete couponDistribution
        couponDistributionService.deleteCouponDistribution(couponDistribution.getCodId());

        // Try searching for the couponDistribution
        CouponDistribution checkCouponDistribution  = couponDistributionService.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(), couponDistribution.getCodCouponCode());

        Assert.assertNull(checkCouponDistribution);

        log.info("couponDistribution deleted");

    }
 
    

    @Test
    public void test6GetCouponDistributionForCustomers() throws InspireNetzException {

        // Get the standardCustomer
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);


        // Create the couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistribution.setCodDistributionType(CouponDistributionType.CUSTOMER_IDS);
        couponDistribution.setCodCustomerIds(customer.getCusCustomerNo().toString());
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");


        // Get the distributions for the customer
        List<CouponDistribution> couponDistributionList = couponDistributionService.getCouponDistributionForCustomers(customer);
        Assert.assertTrue(!couponDistributionList.isEmpty());
        log.info("Customer Coupon Distribution : " + couponDistributionList.toString());



    }


    @Test
    public void test7UpdateCopuonDistributionStatus() throws InspireNetzException {

        // Get the default object
        CouponDistribution couponDistribution =  CouponDistributionFixture.standardCouponDistribution();
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());

        // Set the status
        boolean updated = couponDistributionService.updateCouponDistributionStatus(couponDistribution.getCodId(), CouponDistributionStatus.SUSPENDED,1L,1L);
        Assert.assertTrue(updated);

        // Get the inforation
        couponDistribution = couponDistributionService.findByCodId(couponDistribution.getCodId());
        Assert.assertTrue(couponDistribution.getCodStatus() == CouponDistributionStatus.SUSPENDED);
        log.info("Coupon distribution status changed");


    }


    @After
    public void tearDown() throws InspireNetzException {

        Set<CouponDistribution> couponDistributions = CouponDistributionFixture.standardCouponDistributions();

        for(CouponDistribution couponDistribution: couponDistributions) {

            CouponDistribution delCouponDistribution = couponDistributionRepository.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(), couponDistribution.getCodCouponCode());

            if ( delCouponDistribution != null ) {
                couponDistributionRepository.delete(delCouponDistribution);
            }

        }



        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: customers) {

            Customer delCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            if ( delCustomer != null ) {
                customerService.deleteCustomer(delCustomer.getCusCustomerNo());
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
        return new Sort(Sort.Direction.ASC, "codId");
    }

}
