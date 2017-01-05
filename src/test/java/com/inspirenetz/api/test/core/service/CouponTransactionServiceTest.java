package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Coupon;
import com.inspirenetz.api.core.domain.CouponTransaction;
import com.inspirenetz.api.core.repository.CouponTransactionRepository;
import com.inspirenetz.api.core.service.CouponService;
import com.inspirenetz.api.core.service.CouponTransactionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CouponFixture;
import com.inspirenetz.api.test.core.fixture.CouponTransactionFixture;
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

import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CouponTransactionServiceTest {


    private static Logger log = LoggerFactory.getLogger(CouponTransactionServiceTest.class);

    @Autowired
    private CouponTransactionService couponTransactionService;

    @Autowired
    private CouponTransactionRepository couponTransactionRepository;

    @Autowired
    private CouponService couponService;


    @Before
    public void setUp() {}



    @Test
    public void test3FindByCptMerchantNo() {

        // Get the standard couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();

        Page<CouponTransaction> couponTransactions = couponTransactionService.findByCptMerchantNo(couponTransaction.getCptMerchantNo(),constructPageSpecification(0));
        log.info("couponTransactions by merchant no " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());

    }

    @Test
    public void test4FindByCptMerchantNoAndCptCouponCode() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionService.saveCouponTransaction(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        Page<CouponTransaction> couponTransactions = couponTransactionService.findByCptMerchantNoAndCptCouponCode(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), constructPageSpecification(0));
        log.info("couponTransactions by merchant no and coupon code " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());
    }

    @Test
    public void test5FindByCptMerchantNoAndCptLoyaltyId() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionService.saveCouponTransaction(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        Page<CouponTransaction> couponTransactions = couponTransactionService.findByCptMerchantNoAndCptLoyaltyId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptLoyaltyId(), constructPageSpecification(0));
        log.info("couponTransactions by merchant no and loyalty id " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());

    }

    @Test
    public void test6FindByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionService.saveCouponTransaction(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        Page<CouponTransaction> couponTransactions = couponTransactionService.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), couponTransaction.getCptLoyaltyId(), constructPageSpecification(0));
        log.info("couponTransactions by merchant no and coupon code and loyalty id " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>) couponTransactions);
        log.info("couponTransaction list " + couponTransactionSet.toString());

    }

    @Test
    public void test7FindByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionService.saveCouponTransaction(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        CouponTransaction couponTransaction1 = couponTransactionService.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), couponTransaction.getCptLoyaltyId(),couponTransaction.getCptPurchaseId());
        log.info("couponTransactions by merchant no and coupon code and loyalty id and purchase id " + couponTransaction1.toString());

    }

    @Test
    public void test8RecordCouponAccept() throws InspireNetzException {

        // Create the  coupon first
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);

        // Get the CouponTransaction object
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        boolean success = couponTransactionService.recordCouponAccept(couponTransaction);
        Assert.assertTrue(success);
        log.info("Coupon Transaction recorded as accepted");


    }

    @Test
    public void test8RevertCouponAccept() throws InspireNetzException {

        // Create the  coupon first
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);

        // Get the CouponTransaction object
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        boolean success = couponTransactionService.recordCouponAccept(couponTransaction);
        Assert.assertTrue(success);
        log.info("Coupon Transaction recorded as accepted");


        // Revert the transaction now
        success = couponTransactionService.revertCouponAccept(couponTransaction);
        Assert.assertTrue(success);
        log.info("Coupon Transaction reverted");

    }

    @Test
    public void test8GetCouponTransactionCount() throws InspireNetzException {

        // Create the  coupon first
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);


        // Get the CouponTransaction object
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        boolean success = couponTransactionService.recordCouponAccept(couponTransaction);
        Assert.assertTrue(success);
        log.info("Coupon Transaction recorded as accepted");

        // Get the transaction count
        Map<Integer,Integer> couponTransactionMap = couponTransactionService.getCouponTransactionCount(couponTransaction);
        Assert.assertNotNull(couponTransactionMap);
        log.info("Transaction Count content: " + couponTransactionMap.toString());


    }

    @After
    public void tearDown() throws InspireNetzException {

        /*
        Set<CouponTransaction> couponTransactions = CouponTransactionFixture.standardCouponTransactions();

        for(CouponTransaction couponTransaction: couponTransactions) {

            CouponTransaction delCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), couponTransaction.getCptLoyaltyId(), couponTransaction.getCptPurchaseId());

            if ( delCouponTransaction != null ) {
                couponTransactionRepository.delete(delCouponTransaction);
            }

        }
        */


        Set<Coupon> coupons = CouponFixture.standardCoupons();

        for(Coupon coupon: coupons) {

            Coupon delCoupon = couponService.findByCpnMerchantNoAndCpnCouponName(coupon.getCpnMerchantNo(), coupon.getCpnCouponName());

            if ( delCoupon != null ) {
                couponService.deleteCoupon(delCoupon.getCpnCouponId());
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
        return new Sort(Sort.Direction.ASC, "cptId");
    }

}
