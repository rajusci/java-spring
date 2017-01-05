package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantSettlement;
import com.inspirenetz.api.core.repository.MerchantSettlementRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantSettlementFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantSettlementRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantSettlementRepositoryTest.class);

    @Autowired
    private MerchantSettlementRepository merchantSettlementRepository;

    Set<MerchantSettlement> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementRepository.save(merchantSettlementFixture.standardMerchantSettlement());

        // Add to the tempSet
        tempSet.add(merchantSettlement);

        log.info(merchantSettlement.toString());
        Assert.assertNotNull(merchantSettlement.getMesId());

    }

    @Test
    public void test2Update() {

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementFixture.standardMerchantSettlement();
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);
        log.info("Original MerchantSettlement " + merchantSettlement.toString());

        // Add to the tempSet
        tempSet.add(merchantSettlement);


        MerchantSettlement updatedMerchantSettlement = MerchantSettlementFixture.updatedStandardMerchantSettlement(merchantSettlement);
        updatedMerchantSettlement = merchantSettlementRepository.save(updatedMerchantSettlement);
        log.info("Updated MerchantSettlement "+ updatedMerchantSettlement.toString());

    }

    @Test
    public void test3FindByMesId() {

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementFixture.standardMerchantSettlement();
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);

        log.info("Original MerchantSettlement " + merchantSettlement.toString());


        // Add to the tempSet
        tempSet.add(merchantSettlement);

        // Get the data
        MerchantSettlement searchmerchantSettlement = merchantSettlementRepository.findByMesId(merchantSettlement.getMesId());
        Assert.assertNotNull(searchmerchantSettlement);
        Assert.assertTrue(merchantSettlement.getMesId().longValue() ==  searchmerchantSettlement.getMesId().longValue());;
        log.info("Searched MerchantSettlement : " + searchmerchantSettlement.toString());


    }

    @Test
    public void test4FindByMesVendorNo(){

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementFixture.standardMerchantSettlement();
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);
        log.info("Original MerchantSettlement " + merchantSettlement.toString());


        // Add to the tempSet
        tempSet.add(merchantSettlement);

        // Get the data
        List<MerchantSettlement> searchMerchantSettlement = merchantSettlementRepository.findByMesVendorNo(merchantSettlement.getMesVendorNo());
        Assert.assertNotNull(searchMerchantSettlement);
        log.info("Searched MerchantSettlement : " + searchMerchantSettlement.toString());

    }

    @Test
    public void testFindByMesVendorNoAndMesIsSettled(){

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementFixture.standardMerchantSettlement();
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);
        tempSet.add(merchantSettlement);

        merchantSettlement.setMesId(null);
        merchantSettlement.setMesIsSettled(0);
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);

        // Add to the tempSet
        tempSet.add(merchantSettlement);

        // Get the data
        List<MerchantSettlement> searchMerchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesIsSettled(merchantSettlement.getMesVendorNo(), IndicatorStatus.YES);
        Assert.assertTrue(searchMerchantSettlements.size() == 1);
        log.info("Searched MerchantSettlement : " + searchMerchantSettlements);

    }


    @Test
    public void testFindByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(){

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementFixture.standardMerchantSettlement();
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);
        tempSet.add(merchantSettlement);

        merchantSettlement.setMesId(null);
        merchantSettlement.setMesIsSettled(0);
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);

        // Add to the tempSet
        tempSet.add(merchantSettlement);

        // Get the data
        List<MerchantSettlement> searchMerchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(1012L,0L,1, 0, Date.valueOf("2015-10-01"));
        log.info("Searched MerchantSettlement : " + searchMerchantSettlements);

    }

    @Test
    public void testFindByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(){

        MerchantSettlementFixture merchantSettlementFixture=new MerchantSettlementFixture();

        MerchantSettlement merchantSettlement = merchantSettlementFixture.standardMerchantSettlement();
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);
        tempSet.add(merchantSettlement);

        merchantSettlement.setMesId(null);
        merchantSettlement.setMesIsSettled(0);
        merchantSettlement = merchantSettlementRepository.save(merchantSettlement);

        // Add to the tempSet
        tempSet.add(merchantSettlement);

        // Get the data
        List<MerchantSettlement> searchMerchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(1012L,0L,1, 0, Date.valueOf("2015-10-01"), Date.valueOf("2015-10-16"));
        log.info("Searched MerchantSettlement : " + searchMerchantSettlements);

    }



    @After
    public void tearDown() {

        for(MerchantSettlement merchantSettlement : tempSet ) {

            merchantSettlementRepository.delete(merchantSettlement);

        }

    }



}
