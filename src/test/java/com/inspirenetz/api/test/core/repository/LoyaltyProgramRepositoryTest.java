package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.repository.LoyaltyProgramRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramFixture;
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
public class LoyaltyProgramRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramRepositoryTest.class);

    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        Set<LoyaltyProgram> loyaltyPrograms = LoyaltyProgramFixture.standardLoyaltyPrograms();

        for(LoyaltyProgram loyaltyProgram: loyaltyPrograms) {

            LoyaltyProgram delLoyaltyProgram = loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramName(loyaltyProgram.getPrgMerchantNo(), loyaltyProgram.getPrgProgramName());

            if ( delLoyaltyProgram != null ) {
                loyaltyProgramRepository.delete(delLoyaltyProgram);
            }

        }
    }


    @Test
    public void test1Create(){


        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.save(LoyaltyProgramFixture.standardLoyaltyProgram());
        log.info(loyaltyProgram.toString());
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());

    }

    @Test
    public void test2Update() {

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram = loyaltyProgramRepository.save(loyaltyProgram);
        log.info("Original LoyaltyProgram " + loyaltyProgram.toString());

        LoyaltyProgram updatedLoyaltyProgram = LoyaltyProgramFixture.updatedStandardLoyaltyProgram(loyaltyProgram);
        updatedLoyaltyProgram = loyaltyProgramRepository.save(updatedLoyaltyProgram);
        log.info("Updated LoyaltyProgram "+ updatedLoyaltyProgram.toString());

    }

    @Test
    public void testFindByPrgProgramNo() throws Exception {

        // Create the prodcut
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram = loyaltyProgramRepository.save(loyaltyProgram);

        LoyaltyProgram searchLoyaltyProgram = loyaltyProgramRepository.findByPrgProgramNo(loyaltyProgram.getPrgProgramNo());
        Assert.assertNotNull(searchLoyaltyProgram);
        log.info("LoyaltyProgram Information" + searchLoyaltyProgram.toString());
    }

    @Test
    public void testFindByPrgMerchantNo() throws Exception {

        Set<LoyaltyProgram> loyaltyPrograms = LoyaltyProgramFixture.standardLoyaltyPrograms();
        loyaltyProgramRepository.save(loyaltyPrograms);

        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();

        Page<LoyaltyProgram>  loyaltyProgramPage = loyaltyProgramRepository.findByPrgMerchantNo(loyaltyProgram.getPrgMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(loyaltyProgramPage.hasContent());
        List<LoyaltyProgram> loyaltyProgramList = Lists.newArrayList((Iterable<LoyaltyProgram>)loyaltyProgramPage);
        log.info("LoyaltyProgram List" + loyaltyProgramList);

    }

    @Test
    public void testFindByPrgMerchantNoAndPrgProgramName() throws Exception {

        // Create the prodcut
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramRepository.save(loyaltyProgram);

        LoyaltyProgram searchLoyaltyProgram = loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramName(loyaltyProgram.getPrgMerchantNo(), loyaltyProgram.getPrgProgramName());
        Assert.assertNotNull(searchLoyaltyProgram);
        log.info("LoyaltyProgram information " + searchLoyaltyProgram.toString());

    }



    @Test
    public void testFindByPrgMerchantNoAndPrgProgramNameLike() {

        // Create the loyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgramRepository.save(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());
        log.info("LoyaltyProgram created");

        // Check the loyaltyProgram name
        Page<LoyaltyProgram> loyaltyPrograms = loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramNameLike(loyaltyProgram.getPrgMerchantNo(),"%standard%",constructPageSpecification(0));
        Assert.assertTrue(loyaltyPrograms.hasContent());
        Set<LoyaltyProgram> loyaltyProgramSet = Sets.newHashSet((Iterable<LoyaltyProgram>) loyaltyPrograms);
        log.info("loyaltyProgram list "+loyaltyProgramSet.toString());


    }


    @Test
    public void testFindByPrgMerchantNoAndPrgStatus() {

        // Create the loyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram=loyaltyProgramRepository.save(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());
        log.info("LoyaltyProgram created");


        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramRepository.findByPrgMerchantNoAndPrgStatus(loyaltyProgram.getPrgMerchantNo(),loyaltyProgram.getPrgStatus());
        log.info("LoyaltyProgram List" + loyaltyProgramList.toString());

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
}
