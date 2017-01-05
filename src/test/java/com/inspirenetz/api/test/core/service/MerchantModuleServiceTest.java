package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.service.MerchantModuleService;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantModuleFixture;
import com.inspirenetz.api.test.core.fixture.ModuleFixture;
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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantModuleServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantModuleServiceTest.class);

    @Autowired
    private MerchantModuleService merchantModuleService;

    @Autowired
    private ModuleService moduleService;

    @Before
    public void setUp() {}

    @Test
    public void test1Create()throws InspireNetzException {


        MerchantModule merchantModule = merchantModuleService.saveMerchantModule(MerchantModuleFixture.standardMerchantModule());
        log.info(merchantModule.toString());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModule = merchantModuleService.saveMerchantModule(merchantModule);
        log.info("Original MerchantModule " + merchantModule.toString());

        MerchantModule updatedMerchantModule = MerchantModuleFixture.updatedStandardMerchantModule(merchantModule);
        updatedMerchantModule = merchantModuleService.saveMerchantModule(updatedMerchantModule);
        log.info("Updated MerchantModule "+ updatedMerchantModule.toString());

    }

    @Test
    public void test4FindByMemMerchantNoAndMemCode() throws InspireNetzException {

        // Create the merchantModule
        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModuleService.saveMerchantModule(merchantModule);
        Assert.assertNotNull(merchantModule);
        log.info("MerchantModule created");

        MerchantModule fetchMerchantModule = merchantModuleService.findByMemMerchantNoAndMemModuleId(merchantModule.getMemMerchantNo(), merchantModule.getMemModuleId());
        Assert.assertNotNull(fetchMerchantModule);
        log.info("Fetched merchantModule info" + merchantModule.toString());

    }

    @Test
    public void test5FindByMemMerchantNo() throws InspireNetzException {

        // Create the merchantModule
        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModuleService.saveMerchantModule(merchantModule);
        log.info("MerchantModule created");

        // Check the merchantModule name
        List<MerchantModule> merchantModuleList = merchantModuleService.findByMemMerchantNo(merchantModule.getMemMerchantNo());
        Assert.assertNotNull(merchantModuleList);
        log.info("merchantModule list "+merchantModuleList.toString());


    }


    @Test
    public void test6GetModulesAsMap() throws InspireNetzException {

        //Save the moduel
        Module module = ModuleFixture.standardModule();
        module = moduleService.saveModule(module);

        // Create the merchantModule
        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModule.setMemModuleId(module.getMdlId());
        merchantModuleService.saveMerchantModule(merchantModule);
        log.info("MerchantModule created");


        // Get the HashMap
        HashMap<Long,String> uarMap =  merchantModuleService.getModulesAsMap(merchantModule.getMemMerchantNo());
        Assert.assertTrue(!uarMap.isEmpty());
        log.info("MerchantSEtting Map " + uarMap.toString());
    }


    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecifimesion(int pageIndex) {
        Pageable pageSpecifimesion = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecifimesion;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "mesProductNo");
    }


    @After
    public void tearDown() throws Exception {

        Set<MerchantModule> merchantModules = MerchantModuleFixture.standardMerchantModules();

        for(MerchantModule merchantModule: merchantModules) {

            MerchantModule delMerchantModule = merchantModuleService.findByMemMerchantNoAndMemModuleId(merchantModule.getMemMerchantNo(), merchantModule.getMemModuleId());

            if ( delMerchantModule != null ) {
                merchantModuleService.deleteMerchantModule(delMerchantModule);
            }

        }
    }

}
