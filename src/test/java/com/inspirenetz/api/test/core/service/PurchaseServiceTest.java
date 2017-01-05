package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.repository.PurchaseRepository;
import com.inspirenetz.api.core.service.PurchaseService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class PurchaseServiceTest {


    private static Logger log = LoggerFactory.getLogger(PurchaseServiceTest.class);

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRepository purchaseRepository;


    @Before
    public void setUp() {}




    @Test
    public void test3FindByPrcMerchantNoAndPrcDate() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        List<Purchase> purchaseList = Lists.newArrayList((Iterable<Purchase>)purchaseSet);
        purchaseService.saveAll(purchaseList);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseService.findByPrcMerchantNoAndPrcDate(purchase.getPrcMerchantNo(),purchase.getPrcDate(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }


    @Test
    public void test4FindByPrcMerchantNoAndPrcDateBetween() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        List<Purchase> purchaseList = Lists.newArrayList((Iterable<Purchase>) purchaseSet);
        purchaseService.saveAll(purchaseList);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseService.findByPrcMerchantNoAndPrcDateBetween(purchase.getPrcMerchantNo(), purchase.getPrcDate(),purchase.getPrcDate(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }


    @Test
    public void test5FindByPrcMerchantNoAndPrcLoyaltyId() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        List<Purchase> purchaseList = Lists.newArrayList((Iterable<Purchase>) purchaseSet);
        purchaseService.saveAll(purchaseList);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseService.findByPrcMerchantNoAndPrcLoyaltyId(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }


    @Test
    public void test6FindByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween() {

        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        List<Purchase> purchaseList = Lists.newArrayList((Iterable<Purchase>) purchaseSet);
        purchaseService.saveAll(purchaseList);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        Page<Purchase> purchases = purchaseService.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(), purchase.getPrcDate(), purchase.getPrcDate(), constructPageSpecification(0));
        Assert.assertTrue(purchases.hasContent());
        log.info("purchases by merchant no " + purchases.toString());
        Set<Purchase> purchaseSet1 = Sets.newHashSet((Iterable<Purchase>)purchases);
        log.info("purchase list "+purchaseSet1.toString());

    }

  
    @Test
    public void isDuplicatePurchase() throws InspireNetzException {

        // Add the purchase
        Purchase purchase1 = PurchaseFixture.standardPurchase();
        purchaseService.savePurchase(purchase1);

        Purchase purchase2 = PurchaseFixture.standardPurchase();
        boolean exists = purchaseService.isDuplicatePurchase(purchase2.getPrcMerchantNo(),purchase2.getPrcLoyaltyId(),purchase2.getPrcPaymentReference(),purchase2.getPrcDate(),purchase2.getPrcAmount());
        Assert.assertTrue(exists);
        log.info("purchase already exists");

    }


    @After
    public void tearDown() {

        Set<Purchase> purchases = PurchaseFixture.standardPurchases();

        for(Purchase purchase: purchases) {

            Purchase delPurchase = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(), purchase.getPrcDate(), purchase.getPrcAmount(), purchase.getPrcPaymentReference());

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
