package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.core.repository.LoyaltyExtensionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LoyaltyExtensionFixture;
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
 * Created by saneeshci on 10/09/2014
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class LoyaltyExtensionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LoyaltyExtensionRepositoryTest.class);

    @Autowired
    private LoyaltyExtensionRepository loyaltyExtensionRepository;

    Set<LoyaltyExtension> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        LoyaltyExtension loyaltyExtension = loyaltyExtensionRepository.save(LoyaltyExtensionFixture.standardLoyaltyExtension());
        log.info(loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        Assert.assertNotNull(loyaltyExtension.getLexId());

    }

    @Test
    public void test2Update() {

        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionRepository.save(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        LoyaltyExtension updatedLoyaltyExtensions = LoyaltyExtensionFixture.updatedStandardLoyaltyExtensions(loyaltyExtension);
        updatedLoyaltyExtensions = loyaltyExtensionRepository.save(updatedLoyaltyExtensions);
        log.info("Updated LoyaltyExtensions "+ updatedLoyaltyExtensions.toString());

    }

    @Test
    public void test3FindByLexId() {


        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionRepository.save(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        LoyaltyExtension searchRequest = loyaltyExtensionRepository.findByLexId(loyaltyExtension.getLexId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(loyaltyExtension.getLexId().longValue() == searchRequest.getLexId().longValue());



    }


    @Test
    public void test4FindByLexMerchantNo() {

        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionRepository.save(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        Page<LoyaltyExtension> loyaltyExtensionPage = loyaltyExtensionRepository.findByLexMerchantNo(loyaltyExtension.getLexMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(loyaltyExtensionPage);
        List<LoyaltyExtension> loyaltyExtensionList = Lists.newArrayList((Iterable<LoyaltyExtension>) loyaltyExtensionPage);
        log.info("Loyalty Extension List : "+ loyaltyExtensionList.toString());

    }

    @Test
    public void test4FindByLexMerchantNoAndLexNameLike() {

        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension = loyaltyExtensionRepository.save(loyaltyExtension);
        log.info("Original LoyaltyExtensions " + loyaltyExtension.toString());

        // Add the items
        tempSet.add(loyaltyExtension);

        Page<LoyaltyExtension> loyaltyExtensionPage = loyaltyExtensionRepository.findByLexMerchantNoAndLexNameLike(loyaltyExtension.getLexMerchantNo(),"%"+loyaltyExtension.getLexName()+"%",constructPageSpecification(0));
        Assert.assertNotNull(loyaltyExtensionPage);
        List<LoyaltyExtension> loyaltyExtensionList = Lists.newArrayList((Iterable<LoyaltyExtension>) loyaltyExtensionPage);
        log.info("Loyalty Extension List : "+ loyaltyExtensionList.toString());

    }


    @After
    public void tearDown() {

        for(LoyaltyExtension loyaltyExtension: tempSet) {

           loyaltyExtensionRepository.delete(loyaltyExtension);

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
        return new Sort(Sort.Direction.ASC, "lexName");
    }


}
