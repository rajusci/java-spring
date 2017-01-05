package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.core.service.PurchaseSKUService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.PurchaseSKUFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PurchaseSkuServiceTest {


    private static Logger log = LoggerFactory.getLogger(PurchaseSkuServiceTest.class);

    @Autowired
    private PurchaseSKUService purchaseSkuService;

    @Before
    public void setUp() {}



    @Test
    public void test1Create(){


        PurchaseSKU purchaseSKU = purchaseSkuService.savePurchaseSku(PurchaseSKUFixture.standardPurchaseSku());
        log.info(purchaseSKU.toString());
        Assert.assertNotNull(purchaseSKU.getPkuId());

    }

    @Test
    public void test2Update() {

        PurchaseSKU purchaseSKU = PurchaseSKUFixture.standardPurchaseSku();
        purchaseSKU = purchaseSkuService.savePurchaseSku(purchaseSKU);
        log.info("Original PurchaseSKU " + purchaseSKU.toString());

        PurchaseSKU updatedPurchaseSKU = PurchaseSKUFixture.updatedStandardPurchaseSku(purchaseSKU);
        updatedPurchaseSKU = purchaseSkuService.savePurchaseSku(updatedPurchaseSKU);
        log.info("Updated PurchaseSKU "+ updatedPurchaseSKU.toString());

    }


    @Test
    public void testFindByPkuPurchaseId() {

        Set<PurchaseSKU> purchaseSKUSet = PurchaseSKUFixture.standardPurchaseSkus();
        List<PurchaseSKU> purchaseSKUs = Lists.newArrayList((Iterable<PurchaseSKU>) purchaseSKUSet);
        purchaseSkuService.saveAll(purchaseSKUs);

        // Get the list
        List<PurchaseSKU> purchaseSKUList = purchaseSkuService.findByPkuPurchaseId(1L);
        Assert.assertNotNull(purchaseSKUList);
        Assert.assertTrue(!purchaseSKUList.isEmpty());
        log.info("Purchase SKU List : " + purchaseSKUList.toString());

    }

}
