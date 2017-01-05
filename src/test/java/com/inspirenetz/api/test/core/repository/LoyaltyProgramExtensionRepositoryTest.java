package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyProgramExtension;
import com.inspirenetz.api.core.repository.LoyaltyProgramExtensionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramExtensionFixture;
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
public class LoyaltyProgramExtensionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramExtensionRepositoryTest.class);

    @Autowired
    private LoyaltyProgramExtensionRepository loyaltyProgramExtensionRepository;

    private LoyaltyProgramExtension loyaltyProgramExtension;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        loyaltyProgramExtension = LoyaltyProgramExtensionFixture.standardLoyaltyProgramInfo();
        loyaltyProgramExtension = loyaltyProgramExtensionRepository.save(loyaltyProgramExtension);
        log.info(loyaltyProgramExtension.toString());
        Assert.assertNotNull(loyaltyProgramExtension.getLpeId());

    }




    @After
    public void tearDown() {

        loyaltyProgramExtensionRepository.delete(loyaltyProgramExtension);

    }



}
