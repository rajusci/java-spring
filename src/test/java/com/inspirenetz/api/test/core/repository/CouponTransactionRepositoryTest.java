package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CouponTransaction;
import com.inspirenetz.api.core.repository.CouponTransactionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CouponTransactionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CouponTransactionRepositoryTest.class);

    @Autowired
    private CouponTransactionRepository couponTransactionRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CouponTransaction couponTransaction = couponTransactionRepository.save(CouponTransactionFixture.standardCouponTransaction());
        log.info(couponTransaction.toString());
        Assert.assertNotNull(couponTransaction.getCptId());

    }

    @Test
    public void test2Update() {

        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransaction = couponTransactionRepository.save(couponTransaction);
        log.info("Original CouponTransaction " + couponTransaction.toString());

        CouponTransaction updatedCouponTransaction = CouponTransactionFixture.updatedStandardCouponTransaction(couponTransaction);
        updatedCouponTransaction = couponTransactionRepository.save(updatedCouponTransaction);
        log.info("Updated CouponTransaction "+ updatedCouponTransaction.toString());

    }



    @Test
    public void test3FindByCptMerchantNo() {

        // Get the standard couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();

        Page<CouponTransaction> couponTransactions = couponTransactionRepository.findByCptMerchantNo(couponTransaction.getCptMerchantNo(),constructPageSpecification(0));
        log.info("couponTransactions by merchant no " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());

    }

    @Test
    public void test4FindByCptMerchantNoAndCptCouponCode() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionRepository.save(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        Page<CouponTransaction> couponTransactions = couponTransactionRepository.findByCptMerchantNoAndCptCouponCode(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), constructPageSpecification(0));
        log.info("couponTransactions by merchant no and coupon code " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());
    }


    @Test
    public void test4FindByCptMerchantNoAndCptLoyaltyId() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionRepository.save(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        Page<CouponTransaction> couponTransactions = couponTransactionRepository.findByCptMerchantNoAndCptLoyaltyId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptLoyaltyId(), constructPageSpecification(0));
        log.info("couponTransactions by merchant no and loyalty id " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());

    }


    @Test
    public void test4FindByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionRepository.save(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        Page<CouponTransaction> couponTransactions = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), couponTransaction.getCptLoyaltyId(), constructPageSpecification(0));
        log.info("couponTransactions by merchant no and coupon code and loyalty id " + couponTransactions.toString());
        Set<CouponTransaction> couponTransactionSet = Sets.newHashSet((Iterable<CouponTransaction>)couponTransactions);
        log.info("couponTransaction list "+couponTransactionSet.toString());

    }


    @Test
    public void test4FindByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId() {

        // Create the couponTransaction
        CouponTransaction couponTransaction = CouponTransactionFixture.standardCouponTransaction();
        couponTransactionRepository.save(couponTransaction);
        Assert.assertNotNull(couponTransaction.getCptId());
        log.info("CouponTransaction created");


        CouponTransaction couponTransaction1 = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), couponTransaction.getCptLoyaltyId(),couponTransaction.getCptPurchaseId());
        log.info("couponTransactions by merchant no and coupon code and loyalty id and purchase id " + couponTransaction1.toString());

    }


    @After
    public void tearDown() {

        Set<CouponTransaction> couponTransactions = CouponTransactionFixture.standardCouponTransactions();

        for(CouponTransaction couponTransaction: couponTransactions) {

            CouponTransaction delCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(), couponTransaction.getCptCouponCode(), couponTransaction.getCptLoyaltyId(), couponTransaction.getCptPurchaseId());

            if ( delCouponTransaction != null ) {
                couponTransactionRepository.delete(delCouponTransaction);
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
