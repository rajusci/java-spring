package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramStatus;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.repository.LoyaltyProgramRepository;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.core.service.LoyaltyProgramService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class LoyaltyProgramServiceTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramServiceTest.class);

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    private AttributeService attributeService;

    Set<LoyaltyProgram> tempSet = new HashSet<>(0);

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void testFindByPrgProgramNo() throws Exception {

        // Create the prodcut
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        LoyaltyProgram searchLoyaltyProgram = loyaltyProgramService.findByPrgProgramNo(loyaltyProgram.getPrgProgramNo());
        Assert.assertNotNull(searchLoyaltyProgram);
        log.info("LoyaltyProgram Information" + searchLoyaltyProgram.toString());
    }

    @Test
    public void testFindByPrgMerchantNo() throws Exception {

        Set<LoyaltyProgram> loyaltyPrograms = LoyaltyProgramFixture.standardLoyaltyPrograms();
        List<LoyaltyProgram> loyaltyProgramList = Lists.newArrayList((Iterable<LoyaltyProgram>)loyaltyPrograms);
        loyaltyProgramService.saveAll(loyaltyProgramList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(loyaltyPrograms);

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();

        Page<LoyaltyProgram>  loyaltyProgramPage = loyaltyProgramService.findByPrgMerchantNo(loyaltyProgram.getPrgMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(loyaltyProgramPage.hasContent());
        loyaltyProgramList = Lists.newArrayList((Iterable<LoyaltyProgram>) loyaltyProgramPage);
        log.info("LoyaltyProgram List" + loyaltyProgramList);

    }

    @Test
    public void testFindByPrgMerchantNoAndPrgProgramNameLike() throws Exception {

        // Create the prodcut
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        Page<LoyaltyProgram>  loyaltyProgramPage = loyaltyProgramService.findByPrgMerchantNoAndPrgProgramNameLike(loyaltyProgram.getPrgMerchantNo(),"%standard%",constructPageSpecification(0));
        Assert.assertTrue(loyaltyProgramPage.hasContent());
        List<LoyaltyProgram> loyaltyProgramList = Lists.newArrayList((Iterable<LoyaltyProgram>) loyaltyProgramPage);
        log.info("LoyaltyProgram List" + loyaltyProgramList);


    }



    @Test
    public void testIsDuplicateLoyaltyProgramExisting()throws InspireNetzException {

        // Save the loyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        // New LoyaltyProgram
        LoyaltyProgram newLoyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        boolean exists = loyaltyProgramService.isDuplicateProgramNameExisting(newLoyaltyProgram);
        Assert.assertTrue(exists);
        log.info("loyaltyProgram exists");



    }

    @Test
    public void testFindByPrgMerchantNoAndPrgStatus() throws InspireNetzException {

        // Create the loyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());
        log.info("LoyaltyProgram created");

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);


        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramService.findByPrgMerchantNoAndPrgStatus(loyaltyProgram.getPrgMerchantNo(),loyaltyProgram.getPrgStatus());
        log.info("LoyaltyProgram List" + loyaltyProgramList.toString());

    }





    @Test
    public void test8SetExtFieldValue() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(15L);
        Assert.assertNotNull(attribute);

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.setExtFieldValue(loyaltyProgram,attribute,"100.0");
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        log.info(loyaltyProgram.toString());
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());


        // Get the item information
        LoyaltyProgram searchItem = loyaltyProgramService.findByPrgProgramNo(loyaltyProgram.getPrgProgramNo());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

    }

    @Test
    public void test9GetExtFieldValue() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(15L);
        Assert.assertNotNull(attribute);

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.setExtFieldValue(loyaltyProgram,attribute,"100.0");
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        log.info(loyaltyProgram.toString());
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());


        // Get the item information
        LoyaltyProgram searchItem = loyaltyProgramService.findByPrgProgramNo(loyaltyProgram.getPrgProgramNo());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

        // Get the value of the field set
        String value = loyaltyProgramService.getExtFieldValue(searchItem,attribute);
        Assert.assertNotNull(value);
        log.info("Attribute value : " + value );

    }

    @Test
    public void test10ToAttributeExtensionMap() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(15L);
        Assert.assertNotNull(attribute);

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.setExtFieldValue(loyaltyProgram,attribute,"100.0");
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        log.info(loyaltyProgram.toString());
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = loyaltyProgramService.toAttributeExtensionMap(loyaltyProgram, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());

    }

    @Test
    public void test11FromAttributeExtensionMap() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(15L);
        Assert.assertNotNull(attribute);

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramService.setExtFieldValue(loyaltyProgram,attribute,"100.0");
        loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);


        // Add the created items to the tempset for removal on test completion
        tempSet.add(loyaltyProgram);

        log.info(loyaltyProgram.toString());
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = loyaltyProgramService.toAttributeExtensionMap(loyaltyProgram, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());


        // Create a new entity
        LoyaltyProgram newItem = new LoyaltyProgram();
        newItem = (LoyaltyProgram) loyaltyProgramService.fromAttributeExtensionMap(newItem,entityMap,AttributeExtensionMapType.ALL);
        Assert.assertNotNull(newItem);;
        log.info("New object from map : " + newItem.toString());

    }



    @Test
    public void test12UpdateLoyaltyProgramStatus() throws InspireNetzException {

        // Get the standard loyalty program
        LoyaltyProgram   loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram);

        // Set the loyalty Program status
        boolean updated = loyaltyProgramService.updateLoyaltyProgramStatus(loyaltyProgram.getPrgProgramNo(), LoyaltyProgramStatus.EXPIRED,1L,1L);
        Assert.assertTrue(updated);

        // Read the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.findByPrgProgramNo(loyaltyProgram.getPrgProgramNo());
        Assert.assertTrue(loyaltyProgram.getPrgStatus() == LoyaltyProgramStatus.EXPIRED);
        log.info("Loyalty program status updated successfully");


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
        return new Sort(Sort.Direction.ASC, "prgProgramNo");
    }


    @After
    public void tearDown() throws Exception {

        for(LoyaltyProgram loyaltyProgram : tempSet ) {

            loyaltyProgramService.deleteLoyaltyProgram(loyaltyProgram.getPrgProgramNo());

        }

    }

}
