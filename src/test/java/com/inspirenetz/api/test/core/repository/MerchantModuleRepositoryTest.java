package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.core.repository.MerchantModuleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantModuleFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MerchantModuleRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantModuleRepositoryTest.class);

    @Autowired
    private MerchantModuleRepository merchantModuleRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        MerchantModule merchantModule = merchantModuleRepository.save(MerchantModuleFixture.standardMerchantModule());
        log.info(merchantModule.toString());

    }

    @Test
    public void test2Update() {

        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModule = merchantModuleRepository.save(merchantModule);
        log.info("Original MerchantModule " + merchantModule.toString());

        MerchantModule updatedMerchantModule = MerchantModuleFixture.updatedStandardMerchantModule(merchantModule);
        updatedMerchantModule = merchantModuleRepository.save(updatedMerchantModule);
        log.info("Updated MerchantModule "+ updatedMerchantModule.toString());

    }

    @Test
    public void test4FindByMemMerchantNoAndMemCode() {

        // Create the merchantModule
        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModuleRepository.save(merchantModule);
        Assert.assertNotNull(merchantModule);
        log.info("MerchantModule created");

        MerchantModule fetchMerchantModule = merchantModuleRepository.findByMemMerchantNoAndMemModuleId(merchantModule.getMemMerchantNo(), merchantModule.getMemModuleId());
        Assert.assertNotNull(fetchMerchantModule);
        log.info("Fetched merchantModule info" + merchantModule.toString());

    }

    @Test
    public void test5FindByMemMerchantNo() {

        // Create the merchantModule
        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModuleRepository.save(merchantModule);
        log.info("MerchantModule created");

        // Check the merchantModule name
        List<MerchantModule> merchantModuleList = merchantModuleRepository.findByMemMerchantNo(merchantModule.getMemMerchantNo());
        Assert.assertNotNull(merchantModuleList);
        log.info("merchantModule list "+merchantModuleList.toString());

    }


    @After
    public void tearDown() {

        Set<MerchantModule> merchantModules = MerchantModuleFixture.standardMerchantModules();

        for(MerchantModule merchantModule: merchantModules) {

            MerchantModule delMerchantModule = merchantModuleRepository.findByMemMerchantNoAndMemModuleId(merchantModule.getMemMerchantNo(), merchantModule.getMemModuleId());

            if ( delMerchantModule != null ) {
                merchantModuleRepository.delete(delMerchantModule);
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
        return new Sort(Sort.Direction.ASC, "brnName");
    }


}
