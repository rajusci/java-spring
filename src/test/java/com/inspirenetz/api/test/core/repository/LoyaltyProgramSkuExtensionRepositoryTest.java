package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyProgramSkuExtension;
import com.inspirenetz.api.core.repository.LoyaltyProgramSkuExtensionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramSkuExtensionFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class LoyaltyProgramSkuExtensionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramSkuExtensionRepositoryTest.class);

    @Autowired
    private LoyaltyProgramSkuExtensionRepository saleExtensionRepository;

    private LoyaltyProgramSkuExtension saleExtension;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        saleExtension = LoyaltyProgramSkuExtensionFixture.standardLoyaltyProgramSkuInfo();
        saleExtension = saleExtensionRepository.save(saleExtension);
        log.info(saleExtension.toString());
        Assert.assertNotNull(saleExtension.getLueId());

    }




    @After
    public void tearDown() {

        saleExtensionRepository.delete(saleExtension);

    }



}
