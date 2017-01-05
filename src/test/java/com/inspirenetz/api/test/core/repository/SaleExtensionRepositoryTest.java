package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.SaleExtension;
import com.inspirenetz.api.core.repository.SaleExtensionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.SaleExtensionFixture;
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
public class SaleExtensionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SaleExtensionRepositoryTest.class);

    @Autowired
    private SaleExtensionRepository saleExtensionRepository;

    private SaleExtension saleExtension;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        saleExtension = SaleExtensionFixture.standardSaleInfo();
        saleExtension = saleExtensionRepository.save(saleExtension);
        log.info(saleExtension.toString());
        Assert.assertNotNull(saleExtension.getSaeId());

    }




    @After
    public void tearDown() {

        saleExtensionRepository.delete(saleExtension);

    }



}
