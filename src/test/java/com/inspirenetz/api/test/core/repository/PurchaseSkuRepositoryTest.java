package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.core.repository.PurchaseSKURepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PurchaseSKUFixture;
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
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PurchaseSkuRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(PurchaseSkuRepositoryTest.class);

    @Autowired
    private PurchaseSKURepository purchaseSKURepository;


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void test1Create(){


        PurchaseSKU purchaseSKU = purchaseSKURepository.save(PurchaseSKUFixture.standardPurchaseSku());
        log.info(purchaseSKU.toString());
        Assert.assertNotNull(purchaseSKU.getPkuId());

    }

    @Test
    public void test2Update() {

        PurchaseSKU purchaseSKU = PurchaseSKUFixture.standardPurchaseSku();
        purchaseSKU = purchaseSKURepository.save(purchaseSKU);
        log.info("Original PurchaseSKU " + purchaseSKU.toString());

        PurchaseSKU updatedPurchaseSKU = PurchaseSKUFixture.updatedStandardPurchaseSku(purchaseSKU);
        updatedPurchaseSKU = purchaseSKURepository.save(updatedPurchaseSKU);
        log.info("Updated PurchaseSKU "+ updatedPurchaseSKU.toString());

    }


    @Test
    public void testFindByPkuPurchaseId() {

        Set<PurchaseSKU> purchaseSKUSet = PurchaseSKUFixture.standardPurchaseSkus();
        purchaseSKURepository.save(purchaseSKUSet);

        // Get the list
        List<PurchaseSKU> purchaseSKUList = purchaseSKURepository.findByPkuPurchaseId(1L);
        Assert.assertNotNull(purchaseSKUList);
        Assert.assertTrue(!purchaseSKUList.isEmpty());
        log.info("Purchase SKU List : " + purchaseSKUList.toString());

    }
}
