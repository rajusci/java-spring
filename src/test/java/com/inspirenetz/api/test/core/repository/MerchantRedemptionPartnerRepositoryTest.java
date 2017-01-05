package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.repository.MerchantRedemptionPartnerRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantRedemptionPartnerFixture;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameen on 26/6/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantRedemptionPartnerRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantRedemptionPartnerRepositoryTest.class);

    @Autowired
    private MerchantRedemptionPartnerRepository merchantRedemptionPartnerRepository;

    Set<RedemptionMerchant> redemptionMerchants =new HashSet<>();

    Set<MerchantRedemptionPartner> merchantRedemptionPartners =new HashSet<>();


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        MerchantRedemptionPartner merchantRedemptionPartner = merchantRedemptionPartnerRepository.save(MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner());
        log.info(merchantRedemptionPartner.toString());
        merchantRedemptionPartners.add(merchantRedemptionPartner);

    }

    @Test
    public void test2Update() {

        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartner = merchantRedemptionPartnerRepository.save(merchantRedemptionPartner);
        log.info("Original MerchantRedemptionPartner " + merchantRedemptionPartner.toString());

        MerchantRedemptionPartner updatedMerchantRedemptionPartner = MerchantRedemptionPartnerFixture.updatedStandardMerchantRedemptionPartner(merchantRedemptionPartner);
        updatedMerchantRedemptionPartner = merchantRedemptionPartnerRepository.save(updatedMerchantRedemptionPartner);
        log.info("Updated MerchantRedemptionPartner "+ updatedMerchantRedemptionPartner.toString());

        merchantRedemptionPartners.add(merchantRedemptionPartner);

        merchantRedemptionPartners.add(updatedMerchantRedemptionPartner);
    }

    @Test
    public void test4FindByMemMerchantNoAndRedemptionMerchant() {

        // Create the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartnerRepository.save(merchantRedemptionPartner);
        Assert.assertNotNull(merchantRedemptionPartner);
        log.info("MerchantRedemptionPartner created");

        MerchantRedemptionPartner fetchMerchantRedemptionPartner = merchantRedemptionPartnerRepository.findByMrpMerchantNoAndMrpRedemptionMerchant(merchantRedemptionPartner.getMrpMerchantNo(), merchantRedemptionPartner.getMrpRedemptionMerchant());
        Assert.assertNotNull(fetchMerchantRedemptionPartner);
        log.info("Fetched merchantRedemptionPartner info" + merchantRedemptionPartner.toString());

        merchantRedemptionPartners.add(merchantRedemptionPartner);

    }

    @Test
    public void test5FindByMemMerchantNo() {

        // Create the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartnerRepository.save(merchantRedemptionPartner);
        log.info("MerchantRedemptionPartner created");

        // Check the merchantRedemptionPartner name
        List<MerchantRedemptionPartner> merchantRedemptionPartnerList = merchantRedemptionPartnerRepository.findByMrpMerchantNo(merchantRedemptionPartner.getMrpMerchantNo());
        Assert.assertNotNull(merchantRedemptionPartnerList);
        log.info("merchantRedemptionPartner list "+merchantRedemptionPartnerList.toString());

        merchantRedemptionPartners.add(merchantRedemptionPartner);
    }

    @Test
    public void test5FindByMrpRedemptionMerchant() {

        // Create the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartnerRepository.save(merchantRedemptionPartner);
        log.info("MerchantRedemptionPartner created");

        // Check the merchantRedemptionPartner name
        List<MerchantRedemptionPartner> merchantRedemptionPartnerList = merchantRedemptionPartnerRepository.findByMrpRedemptionMerchant(merchantRedemptionPartner.getMrpRedemptionMerchant());
        Assert.assertNotNull(merchantRedemptionPartnerList);
        log.info("merchantRedemptionPartner list "+merchantRedemptionPartnerList.toString());

        merchantRedemptionPartners.add(merchantRedemptionPartner);
    }


    @After
    public void tearDown() {

        for (MerchantRedemptionPartner merchantRedemptionPartner:merchantRedemptionPartners){

            merchantRedemptionPartnerRepository.delete(merchantRedemptionPartner);
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
