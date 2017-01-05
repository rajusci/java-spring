package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.repository.AttributeRepository;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.AttributeFixture;
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
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class AttributeServiceTest {


    private static Logger log = LoggerFactory.getLogger(AttributeServiceTest.class);

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeRepository attributeRepository;


    @Before
    public void setUp() {}



    @Test
    public void test1FindByAtrId() throws InspireNetzException{

        // Create the attribute
        Attribute attribute = AttributeFixture.standardAttribute();
        attributeService.saveAttribute(attribute);
        Assert.assertNotNull(attribute.getAtrId());
        log.info("Attribute created");


        Attribute fetchAttribute = attributeService.findByAtrId(attribute.getAtrId());
        Assert.assertNotNull(fetchAttribute);
        log.info("Fetched attribute info" + attribute.toString());

    }

    @Test
    public void test2FindByAtrName() throws InspireNetzException{

        // Create the attribute
        Attribute attribute = AttributeFixture.standardAttribute();
        attributeService.saveAttribute(attribute);
        Assert.assertNotNull(attribute.getAtrId());
        log.info("Attribute created");

        Attribute fetchAttribute = attributeService.findByAtrName(attribute.getAtrName());
        Assert.assertNotNull(fetchAttribute);
        log.info("Fetched attribute info" + attribute.toString());

    }

    @Test
    public void test3FindbyAtrName() throws InspireNetzException{

        // Create the attribute
        Attribute attribute = AttributeFixture.standardAttribute();
        attributeService.saveAttribute(attribute);
        Assert.assertNotNull(attribute.getAtrId());
        log.info("Attribute created");

        // Check the attribute name
        Attribute attribute1 = attributeService.findByAtrName(attribute.getAtrName());
        Assert.assertNotNull(attribute1);
        log.info("attribute list "+attribute1.toString());

    }

    @Test
    public void test4IsDuplidateAttributeExisting() throws InspireNetzException{

        // Create the attribute
        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeService.saveAttribute(attribute);
        Assert.assertNotNull(attribute.getAtrId());
        log.info("Attribute created");

        // Create a new attribute
        Attribute newAttribute = AttributeFixture.standardAttribute();
        boolean exists = attributeService.isDuplicateAttributeExisting(newAttribute);
        Assert.assertTrue(exists);
        log.info("Attribute exists");


    }

    @Test
    public void test5DeleteAttribute() throws InspireNetzException {

        // Create the attribute
        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeService.saveAttribute(attribute);
        Assert.assertNotNull(attribute.getAtrId());
        log.info("Attribute created");

        // call the delete attribute
        attributeService.deleteAttribute(attribute.getAtrId());

        // Try searching for the attribute
        Attribute checkAttribute  = attributeService.findByAtrId(attribute.getAtrId());
        Assert.assertNull(checkAttribute);
        log.info("attribute deleted");

    }

    @Test
    public void test6GetAttributesMapByName() throws InspireNetzException {

        // Create the attribute
        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeService.saveAttribute(attribute);
        Assert.assertNotNull(attribute.getAtrId());
        log.info("Attribute created");

        // List attributes
        HashMap<String,Attribute> attributeHashMap = attributeService.getAttributesMapByName(0);
        Assert.assertNotNull(attributeHashMap);
        log.info("attribute hashmap" + attributeHashMap.toString());

    }

    @Test
    public void test7findByAtrNameLike(){

        // Get the attribute
        Set<Attribute> attributes = AttributeFixture.standardAttributes();
        List<Attribute> attributeList = Lists.newArrayList((Iterable<Attribute>) attributes);
        attributeService.saveAll(attributeList);
        log.info("Original attribute spiel " + attributeList.toString());


        Page<Attribute> attributeNameLike = attributeService.findAttributeNameLike("name","%test%",constructPageSpecification(0));
        Assert.assertNotNull(attributeNameLike);
        log.info("Fetched attribute test7findByAtrNameLike" + attributeNameLike.getContent().toString());
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
        return new Sort(Sort.Direction.ASC, "atrName");
    }
    @After
    public void tearDown() {

        Set<Attribute> attributes = AttributeFixture.standardAttributes();

        for(Attribute attribute: attributes) {

            Attribute delAttribute = attributeRepository.findByAtrName(attribute.getAtrName());

            if ( delAttribute != null ) {
                attributeRepository.delete(delAttribute);
            }

        }
    }

}
