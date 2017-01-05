package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import com.inspirenetz.api.core.repository.MerchantLoyaltyIdRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantLoyaltyIdFixture;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantLoyaltyIdRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantLoyaltyIdRepositoryTest.class);

    @Autowired
    private MerchantLoyaltyIdRepository merchantLoyaltyIdRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        MerchantLoyaltyId merchantLoyaltyId = merchantLoyaltyIdRepository.save(MerchantLoyaltyIdFixture.standardMerchantLoyaltyId());
        log.info(merchantLoyaltyId.toString());
        Assert.assertNotNull(merchantLoyaltyId.getMliMerchantNo());

    }

    @Test
    public void test2Update() {

        MerchantLoyaltyId merchantLoyaltyId = MerchantLoyaltyIdFixture.standardMerchantLoyaltyId();
        merchantLoyaltyId = merchantLoyaltyIdRepository.save(merchantLoyaltyId);
        log.info("Original MerchantLoyaltyId " + merchantLoyaltyId.toString());

        MerchantLoyaltyId updatedMerchantLoyaltyId = MerchantLoyaltyIdFixture.updatedStandardMerchantLoyaltyId(merchantLoyaltyId);
        updatedMerchantLoyaltyId = merchantLoyaltyIdRepository.save(updatedMerchantLoyaltyId);
        log.info("Updated MerchantLoyaltyId "+ updatedMerchantLoyaltyId.toString());

    }



    @Test
    public void test3FindByMliMerchantNo() {

        // Get the standard merchantLoyaltyId
        MerchantLoyaltyId merchantLoyaltyId = MerchantLoyaltyIdFixture.standardMerchantLoyaltyId();

        MerchantLoyaltyId merchantLoyaltyIds = merchantLoyaltyIdRepository.findByMliMerchantNo(merchantLoyaltyId.getMliMerchantNo());
        log.info("merchantLoyaltyIds by merchant no " + merchantLoyaltyIds.toString());

    }


}
