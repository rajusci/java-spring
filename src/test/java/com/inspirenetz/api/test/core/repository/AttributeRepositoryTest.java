package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.repository.AttributeRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class AttributeRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(AttributeRepositoryTest.class);

    @Autowired
    private AttributeRepository attributeRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Attribute attribute = attributeRepository.save(AttributeFixture.standardAttribute());
        log.info(attribute.toString());
        Assert.assertNotNull(attribute.getAtrId());

    }

    @Test
    public void test2Update() {

        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeRepository.save(attribute);
        log.info("Original Attribute " + attribute.toString());

        Attribute updatedAttribute = AttributeFixture.updatedStandardAttribute(attribute);
        updatedAttribute = attributeRepository.save(updatedAttribute);
        log.info("Updated Attribute "+ updatedAttribute.toString());

    }



    @Test
    public void test3FindByAtrId() {

        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeRepository.save(attribute);
        log.info("Original Attribute " + attribute.toString());

        Attribute searchAttribute = attributeRepository.findByAtrId(attribute.getAtrId());
        Assert.assertNotNull(searchAttribute);
        Assert.assertTrue(searchAttribute.getAtrId() == attribute.getAtrId());
        log.info("Seaerch Attribute "+ searchAttribute.toString());

    }


    @Test
    public void test4FindByAtrName() {

        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeRepository.save(attribute);
        log.info("Original Attribute " + attribute.toString());

        Attribute searchAttribute = attributeRepository.findByAtrName(attribute.getAtrName());
        Assert.assertNotNull(searchAttribute);
        log.info("Seaerch Attribute "+ searchAttribute.toString());

    }


    @Test
    public void test5FindByAtrEntity() {

        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeRepository.save(attribute);
        log.info("Original Attribute " + attribute.toString());

        List<Attribute> attributeList = attributeRepository.findByAtrEntity(1);
        Assert.assertNotNull(attributeList);
        Assert.assertTrue(!attributeList.isEmpty());
        log.info("Attributes for entity" + attributeList.toString());


    }


    @Test
    public void test6FindAll() {


        Attribute attribute = AttributeFixture.standardAttribute();
        attribute = attributeRepository.save(attribute);
        log.info("Original Attribute " + attribute.toString());

        List<Attribute> attributeList = attributeRepository.findAll();
        Assert.assertNotNull(attributeList);
        Assert.assertTrue(!attributeList.isEmpty());
        log.info("Attributes for entity" + attributeList.toString());


    }

    @Test
    public void test7findByAtrNameLike(){

        // Get the standard brand
        Set<Attribute> attributes = AttributeFixture.standardAttributes();
        attributeRepository.save(attributes);
        log.info("Original attribute " + attributes.toString());


        Page<Attribute> attributefindByName = attributeRepository.findByAtrNameLike("%TEST_ATTRIBUTE1%",constructPageSpecification(0));
        Assert.assertNotNull(attributefindByName);
        log.info("Fetched message findByAtrNameLik " + attributefindByName.getContent().toString());
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


}
