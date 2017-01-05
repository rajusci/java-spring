package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.core.service.LoyaltyProgramSkuService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class LoyaltyProgramSkuServiceTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramSkuServiceTest.class);

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private AttributeService attributeService;

    Set<LoyaltyProgramSku> tempSet = new HashSet<>(0);


    @Before
    public void setUp() {}




    @Test
    public void test1Create(){


        LoyaltyProgramSku loyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(LoyaltyProgramSkuFixture.standardLoyaltyProgramSku());

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        log.info(loyaltyProgramSku.toString());
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());

    }

    @Test
    public void test2Update() {

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);
        log.info("Original LoyaltyProgramSku " + loyaltyProgramSku.toString());

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        LoyaltyProgramSku updatedLoyaltyProgramSku = LoyaltyProgramSkuFixture.updatedStandardLoyaltyProgramSku(loyaltyProgramSku);
        updatedLoyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(updatedLoyaltyProgramSku);
        log.info("Updated LoyaltyProgramSku "+ updatedLoyaltyProgramSku.toString());

    }



    @Test
    public void test3FindByLpuProgramId() {

        // Get the standard loyaltyProgramSku
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();
        List<LoyaltyProgramSku> loyalatyProgramSkuList = Lists.newArrayList((Iterable<LoyaltyProgramSku>) loyaltyProgramSkuSet);
        loyaltyProgramSkuService.saveAll(loyalatyProgramSkuList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(loyalatyProgramSkuList);

        // Get the standard loyalty program sku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();

        Page<LoyaltyProgramSku> loyaltyProgramSkus = loyaltyProgramSkuService.findByLpuProgramId(loyaltyProgramSku.getLpuProgramId(),constructPageSpecification(0));
        log.info("loyaltyProgramSkus by merchant no " + loyaltyProgramSkus.toString());
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet1 = Sets.newHashSet((Iterable<LoyaltyProgramSku>)loyaltyProgramSkus);
        log.info("loyaltyProgramSku list "+loyaltyProgramSkuSet1.toString());

    }

    @Test
    public void test4FindByLpuId() {

        // Get the standard loyaltyProgramSku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        LoyaltyProgramSku fetchLoyaltyProgramSku = loyaltyProgramSkuService.findByLpuId(loyaltyProgramSku.getLpuId());
        Assert.assertNotNull(fetchLoyaltyProgramSku);
        log.info("Fetched loyaltyProgramSku info" + fetchLoyaltyProgramSku.toString());

    }


    @Test
    public void test5FindByLpuProgramIdAndLpuItemTypeAndLpuItemCode() {

        // Get the standard loyaltyProgramSku
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();
        List<LoyaltyProgramSku> loyalatyProgramSkuList = Lists.newArrayList((Iterable<LoyaltyProgramSku>) loyaltyProgramSkuSet);
        loyaltyProgramSkuService.saveAll(loyalatyProgramSkuList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(loyalatyProgramSkuList);

        // Get the standard loyalty program sku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();


        LoyaltyProgramSku fetchLoyaltyProgramSku = loyaltyProgramSkuService.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(loyaltyProgramSku.getLpuProgramId(), loyaltyProgramSku.getLpuItemType(), loyaltyProgramSku.getLpuItemCode());
        Assert.assertNotNull(fetchLoyaltyProgramSku);
        log.info("Fetched loyaltyProgramSku info" + fetchLoyaltyProgramSku.toString());

    }



    @Test
    public void test6IsLoyaltyProgramSkuExisting() {

        // Get the standardSku
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        // Get a new sku
        LoyaltyProgramSku newLoyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        boolean exists = loyaltyProgramSkuService.isLoyaltyProgramSkuExisting(newLoyaltyProgramSku);
        Assert.assertTrue(exists);
        log.info("Loyalty program sku exists");


    }



    @Test
    public void test8SetExtFieldValue() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSkuService.setExtFieldValue(loyaltyProgramSku,attribute,"100.0");
        loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        log.info(loyaltyProgramSku.toString());
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());


        // Get the item information
        LoyaltyProgramSku searchItem = loyaltyProgramSkuService.findByLpuId(loyaltyProgramSku.getLpuId());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

    }


    @Test
    public void test9GetExtFieldValue() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(12L);
        Assert.assertNotNull(attribute);

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSkuService.setExtFieldValue(loyaltyProgramSku,attribute,"100.0");
        loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        log.info(loyaltyProgramSku.toString());
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());


        // Get the item information
        LoyaltyProgramSku searchItem = loyaltyProgramSkuService.findByLpuId(loyaltyProgramSku.getLpuId());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

        // Get the value of the field set
        String value = loyaltyProgramSkuService.getExtFieldValue(searchItem,attribute);
        Assert.assertNotNull(value);
        log.info("Attribute value : " + value );

    }



    @Test
    public void test10ToAttributeExtensionMap() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSkuService.setExtFieldValue(loyaltyProgramSku,attribute,"100.0");
        loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        log.info(loyaltyProgramSku.toString());
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = loyaltyProgramSkuService.toAttributeExtensionMap(loyaltyProgramSku, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());

    }




    @Test
    public void test11FromAttributeExtensionMap() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(12L);
        Assert.assertNotNull(attribute);

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSkuService.setExtFieldValue(loyaltyProgramSku,attribute,"100.0");
        loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);


        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgramSku);

        log.info(loyaltyProgramSku.toString());
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = loyaltyProgramSkuService.toAttributeExtensionMap(loyaltyProgramSku, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());


        // Create a new entity
        LoyaltyProgramSku newItem = new LoyaltyProgramSku();
        newItem = (LoyaltyProgramSku) loyaltyProgramSkuService.fromAttributeExtensionMap(newItem,entityMap,AttributeExtensionMapType.ALL);
        Assert.assertNotNull(newItem);;
        log.info("New object from map : " + newItem.toString());

    }





    @After
    public void tearDown() {

        Set<LoyaltyProgramSku> loyaltyProgramSkus = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();

        for(LoyaltyProgramSku loyaltyProgramSku: loyaltyProgramSkus) {

            LoyaltyProgramSku delLoyaltyProgramSku = loyaltyProgramSkuService.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(loyaltyProgramSku.getLpuProgramId(), loyaltyProgramSku.getLpuItemType(), loyaltyProgramSku.getLpuItemCode());

            if ( delLoyaltyProgramSku != null ) {
                loyaltyProgramSkuService.deleteLoyaltyProgramSku(delLoyaltyProgramSku.getLpuId());
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
