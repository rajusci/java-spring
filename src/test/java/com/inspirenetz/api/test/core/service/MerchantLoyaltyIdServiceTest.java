package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import com.inspirenetz.api.core.repository.MerchantLoyaltyIdRepository;
import com.inspirenetz.api.core.service.MerchantLoyaltyIdService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantLoyaltyIdServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantLoyaltyIdServiceTest.class);

    @Autowired
    private MerchantLoyaltyIdService merchantLoyaltyIdService;



    @Before
    public void setUp() {}



    @Test
    public void test1FindByMliMerchantNo() {

        // Get the standard merchantLoyaltyId
        MerchantLoyaltyId merchantLoyaltyId = MerchantLoyaltyIdFixture.standardMerchantLoyaltyId();

        MerchantLoyaltyId merchantLoyaltyIds = merchantLoyaltyIdService.findByMliMerchantNo(merchantLoyaltyId.getMliMerchantNo());
        log.info("merchantLoyaltyIds by merchant no " + merchantLoyaltyIds.toString());

    }



    @Test
    public void test2FindByMliMerchantNo() {

        // Get the standard merchantLoyaltyId
        MerchantLoyaltyId merchantLoyaltyId = MerchantLoyaltyIdFixture.standardMerchantLoyaltyId();
        Long loyaltyId = merchantLoyaltyIdService.getNextLoyaltyId(merchantLoyaltyId.getMliMerchantNo());
        log.info("Loyalty Id : " + loyaltyId);

    }


}
