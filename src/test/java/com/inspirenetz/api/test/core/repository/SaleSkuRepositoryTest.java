package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.repository.SaleSKURepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.SaleSKUFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SaleSkuRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(SaleSkuRepositoryTest.class);

    @Autowired
    private SaleSKURepository saleSKURepository;

    Set<SaleSKU> tempSet = new HashSet<>(0);


    @Before
    public void setUp() throws Exception {


    }




    @Test
    public void test1Create(){


        SaleSKU saleSKU = saleSKURepository.save(SaleSKUFixture.standardSaleSku());

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);

        log.info(saleSKU.toString());
        Assert.assertNotNull(saleSKU.getSsuId());

    }

    @Test
    public void test2Update() {

        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSKU = saleSKURepository.save(saleSKU);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(saleSKU);


        log.info("Original SaleSKU " + saleSKU.toString());

        SaleSKU updatedSaleSKU = SaleSKUFixture.updatedStandardSaleSku(saleSKU);
        updatedSaleSKU = saleSKURepository.save(updatedSaleSKU);
        log.info("Updated SaleSKU "+ updatedSaleSKU.toString());

    }


    @Test
    public void testFindBySsuSaleId() {

        Set<SaleSKU> saleSKUSet = SaleSKUFixture.standardSaleSkus();
        saleSKURepository.save(saleSKUSet);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSKUSet);

        // Get the list
        List<SaleSKU> saleSKUList = saleSKURepository.findBySsuSaleId(1L);
        Assert.assertNotNull(saleSKUList);
        Assert.assertTrue(!saleSKUList.isEmpty());
        log.info("Sale SKU List : " + saleSKUList.toString());

    }


    @After
    public void tearDown() throws Exception {

        for(SaleSKU saleSKU : tempSet ) {

            saleSKURepository.delete(saleSKU);

        }

    }
}
