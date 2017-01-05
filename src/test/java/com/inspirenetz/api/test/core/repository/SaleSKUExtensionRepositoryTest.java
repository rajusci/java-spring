package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.SaleSKUExtension;
import com.inspirenetz.api.core.repository.SaleSKUExtensionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.SaleSKUExtensionFixture;
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
public class SaleSKUExtensionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SaleSKUExtensionRepositoryTest.class);

    @Autowired
    private SaleSKUExtensionRepository saleSKUExtensionRepository;

    private SaleSKUExtension saleSKUExtension;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        saleSKUExtension = SaleSKUExtensionFixture.standardSaleSKUExtension();
        saleSKUExtension = saleSKUExtensionRepository.save(saleSKUExtension);
        log.info(saleSKUExtension.toString());
        Assert.assertNotNull(saleSKUExtension.getSseId());

    }




    @After
    public void tearDown() {

        saleSKUExtensionRepository.delete(saleSKUExtension);

    }



}
