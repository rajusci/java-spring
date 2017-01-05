package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.core.service.SaleSKUService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.SaleSKUFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SaleSkuServiceTest {


    private static Logger log = LoggerFactory.getLogger(SaleSkuServiceTest.class);

    @Autowired
    private SaleSKUService saleSkuService;

    @Autowired
    private AttributeService attributeService;

    Set<SaleSKU> tempSet = new HashSet<>(0);


    @Before
    public void setUp() {}



    @Test
    public void test1Create(){

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSkuService.saveSaleSku(saleSKU);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        log.info(saleSKU.toString());
        Assert.assertNotNull(saleSKU.getSsuId());

    }

    @Test
    public void test2Update() {

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSKU = saleSkuService.saveSaleSku(saleSKU);
        log.info("Original SaleSKU " + saleSKU.toString());

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        SaleSKU updatedSaleSKU = SaleSKUFixture.updatedStandardSaleSku(saleSKU);
        updatedSaleSKU = saleSkuService.saveSaleSku(updatedSaleSKU);
        log.info("Updated SaleSKU "+ updatedSaleSKU.toString());

    }


    @Test
    public void testFindBySsuSaleId() {

        Set<SaleSKU> saleSKUSet = SaleSKUFixture.standardSaleSkus();
        List<SaleSKU> saleSKUs = Lists.newArrayList((Iterable<SaleSKU>) saleSKUSet);
        saleSkuService.saveAll(saleSKUs);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSKUSet);

        // Get the list
        List<SaleSKU> saleSKUList = saleSkuService.findBySsuSaleId(1L);
        Assert.assertNotNull(saleSKUList);
        Assert.assertTrue(!saleSKUList.isEmpty());
        log.info("Sale SKU List : " + saleSKUList.toString());

    }


    @Test
    public void testSetExtFieldValue() {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSkuService.setExtFieldValue(saleSKU,attribute,"100.0");
        saleSkuService.saveSaleSku(saleSKU);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        log.info(saleSKU.toString());
        Assert.assertNotNull(saleSKU.getSsuId());


        // Get the item information
        SaleSKU searchItem = saleSkuService.findBySsuId(saleSKU.getSsuId());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

    }


    @Test
    public void testGetExtFieldValue() {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSkuService.setExtFieldValue(saleSKU,attribute,"100.0");
        saleSkuService.saveSaleSku(saleSKU);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        log.info(saleSKU.toString());
        Assert.assertNotNull(saleSKU.getSsuId());


        // Get the item information
        SaleSKU searchItem = saleSkuService.findBySsuId(saleSKU.getSsuId());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());


        // Get the value
        String value = saleSkuService.getExtFieldValue(searchItem,attribute);
        Assert.assertNotNull(value);
        log.info("Attribute value : " + value);


    }


    @Test
    public void toAttributeExtensionMap() {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSkuService.setExtFieldValue(saleSKU,attribute,"100.0");
        saleSkuService.saveSaleSku(saleSKU);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        log.info(saleSKU.toString());
        Assert.assertNotNull(saleSKU.getSsuId());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = saleSkuService.toAttributeExtensionMap(saleSKU, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());

    }




    @Test
    public void fromAttributeExtensionMap() {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSkuService.setExtFieldValue(saleSKU,attribute,"100.0");
        saleSkuService.saveSaleSku(saleSKU);


        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        log.info(saleSKU.toString());
        Assert.assertNotNull(saleSKU.getSsuId());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = saleSkuService.toAttributeExtensionMap(saleSKU, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());


        // Create a new entity
        SaleSKU newItem = new SaleSKU();
        newItem = (SaleSKU) saleSkuService.fromAttributeExtensionMap(newItem,entityMap,AttributeExtensionMapType.ALL);
        Assert.assertNotNull(newItem);;
        log.info("New object from map : " + newItem.toString());

    }




    @After
    public void tearDown() {

        for(SaleSKU saleSKU : tempSet ) {

            saleSkuService.deleteSaleSku(saleSKU.getSsuId());

        }

    }

}
