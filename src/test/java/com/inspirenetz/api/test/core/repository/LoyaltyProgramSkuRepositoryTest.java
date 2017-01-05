package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.repository.LoyaltyProgramSkuRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramSkuFixture;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class LoyaltyProgramSkuRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramSkuRepositoryTest.class);

    @Autowired
    private LoyaltyProgramSkuRepository loyaltyProgramSkuRepository;


    @Before
    public void setup() {}


    @Test
    public void test1Create(){


        LoyaltyProgramSku loyaltyProgramSku = loyaltyProgramSkuRepository.save(LoyaltyProgramSkuFixture.standardLoyaltyProgramSku());
        log.info(loyaltyProgramSku.toString());
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());

    }

    @Test
    public void test2Update() {

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku = loyaltyProgramSkuRepository.save(loyaltyProgramSku);
        log.info("Original LoyaltyProgramSku " + loyaltyProgramSku.toString());

        LoyaltyProgramSku updatedLoyaltyProgramSku = LoyaltyProgramSkuFixture.updatedStandardLoyaltyProgramSku(loyaltyProgramSku);
        updatedLoyaltyProgramSku = loyaltyProgramSkuRepository.save(updatedLoyaltyProgramSku);
        log.info("Updated LoyaltyProgramSku "+ updatedLoyaltyProgramSku.toString());

    }



    @Test
    public void test3FindByLpuProgramId() {

        // Get the standard loyaltyProgramSku
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();
        loyaltyProgramSkuRepository.save(loyaltyProgramSkuSet);

        // Get the standard loyalty program sku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();

        Page<LoyaltyProgramSku> loyaltyProgramSkus = loyaltyProgramSkuRepository.findByLpuProgramId(loyaltyProgramSku.getLpuProgramId(),constructPageSpecification(0));
        log.info("loyaltyProgramSkus by merchant no " + loyaltyProgramSkus.toString());
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet1 = Sets.newHashSet((Iterable<LoyaltyProgramSku>)loyaltyProgramSkus);
        log.info("loyaltyProgramSku list "+loyaltyProgramSkuSet1.toString());

    }

    @Test
    public void test4FindByLpuId() {

        // Get the standard loyaltyProgramSku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku = loyaltyProgramSkuRepository.save(loyaltyProgramSku);

        // Get the standard loyalty program sku


        LoyaltyProgramSku fetchLoyaltyProgramSku = loyaltyProgramSkuRepository.findByLpuId(loyaltyProgramSku.getLpuId());
        Assert.assertNotNull(fetchLoyaltyProgramSku);
        log.info("Fetched loyaltyProgramSku info" + fetchLoyaltyProgramSku.toString());

    }


    @Test
    public void test5FindByLpuProgramIdAndLpuItemTypeAndLpuItemCode() {

        // Get the standard loyaltyProgramSku
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();
        loyaltyProgramSkuRepository.save(loyaltyProgramSkuSet);

        // Get the standard loyalty program sku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();


        LoyaltyProgramSku fetchLoyaltyProgramSku = loyaltyProgramSkuRepository.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(loyaltyProgramSku.getLpuProgramId(), loyaltyProgramSku.getLpuItemType(), loyaltyProgramSku.getLpuItemCode());
        Assert.assertNotNull(fetchLoyaltyProgramSku);
        log.info("Fetched loyaltyProgramSku info" + fetchLoyaltyProgramSku.toString());

    }


    @Test
    public void test6ListRulesForLineItem() {

        // Get the standard loyaltyProgramSku
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();
        loyaltyProgramSkuRepository.save(loyaltyProgramSkuSet);

        List<LoyaltyProgramSku> loyaltyProgramSkuList = loyaltyProgramSkuRepository.listRulesForLineItem(1L,"","CAT1003","","","PRD10001");
        Assert.assertTrue(!loyaltyProgramSkuList.isEmpty());
        log.info("LoyaltyProgramSKuList : " + loyaltyProgramSkuList.toString());

    }


    @After
    public void tearDown() {

        Set<LoyaltyProgramSku> loyaltyProgramSkus = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();

        for(LoyaltyProgramSku loyaltyProgramSku: loyaltyProgramSkus) {

            LoyaltyProgramSku delLoyaltyProgramSku = loyaltyProgramSkuRepository.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(loyaltyProgramSku.getLpuProgramId(), loyaltyProgramSku.getLpuItemType(), loyaltyProgramSku.getLpuItemCode());

            if ( delLoyaltyProgramSku != null ) {

                loyaltyProgramSkuRepository.delete(delLoyaltyProgramSku);

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
        return new Sort(Sort.Direction.ASC, "lpuId");
    }


}
