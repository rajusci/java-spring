package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.repository.PurchaseRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PurchaseFixture;
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
public class PurchaseRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PurchaseRepositoryTest.class);

    @Autowired
    private PurchaseRepository purchaseRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Purchase purchase = purchaseRepository.save(PurchaseFixture.standardPurchase());
        log.info(purchase.toString());
        Assert.assertNotNull(purchase.getPrcId());

    }

    @Test
    public void test2Update() {

        Purchase purchase = PurchaseFixture.standardPurchase();
        purchase = purchaseRepository.save(purchase);
        log.info("Original Purchase " + purchase.toString());

        Purchase updatedPurchase = PurchaseFixture.updatedStandardPurchase(purchase);
        updatedPurchase = purchaseRepository.save(updatedPurchase);
        log.info("Updated Purchase "+ updatedPurchase.toString());

    }



    @Test
    public void test3FindByPrcMerchantNoAndPrcDate() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        purchaseRepository.save(purchaseSet);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseRepository.findByPrcMerchantNoAndPrcDate(purchase.getPrcMerchantNo(),purchase.getPrcDate(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }


    @Test
    public void test4FindByPrcMerchantNoAndPrcDateBetween() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        purchaseRepository.save(purchaseSet);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseRepository.findByPrcMerchantNoAndPrcDateBetween(purchase.getPrcMerchantNo(), purchase.getPrcDate(),purchase.getPrcDate(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }


    @Test
    public void test5FindByPrcMerchantNoAndPrcLoyaltyId() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        purchaseRepository.save(purchaseSet);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyId(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }


    @Test
    public void test6FindByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        purchaseRepository.save(purchaseSet);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(), purchase.getPrcDate(), purchase.getPrcDate(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }
 
    @Test
    public void test7FindByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        purchaseRepository.save(purchaseSet);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Purchase purchase1 = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(),purchase.getPrcDate(),purchase.getPrcAmount(),purchase.getPrcPaymentReference());
        Assert.assertNotNull(purchase1);
        log.info("purchase entry " + purchase1.toString()) ;

    }




    @After
    public void tearDown() {

        Set<Purchase> purchases = PurchaseFixture.standardPurchases();

        for(Purchase purchase: purchases) {

            Purchase delPurchase = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(),purchase.getPrcDate(),purchase.getPrcAmount(),purchase.getPrcPaymentReference());

            if ( delPurchase != null ) {
                purchaseRepository.delete(delPurchase);
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
        return new Sort(Sort.Direction.ASC, "prcDate");
    }


}
