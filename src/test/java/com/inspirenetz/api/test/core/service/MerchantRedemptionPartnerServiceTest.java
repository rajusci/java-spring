package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.MerchantRedemptionPartnerService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantRedemptionPartnerFixture;
import com.inspirenetz.api.test.core.fixture.ModuleFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionMerchantFixture;
import org.drools.lang.dsl.DSLMapParser;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameen on 26/6/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantRedemptionPartnerServiceTest {
    private static Logger log = LoggerFactory.getLogger(MerchantRedemptionPartnerServiceTest.class);

    @Autowired
    private MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    Set<MerchantRedemptionPartner> merchantRedemptionPartnerSet =new HashSet<>();

    Set<RedemptionMerchant> redemptionMerchants =new HashSet<>();

    @Before
    public void setUp() {}

    @Test
    public void test1Create()throws InspireNetzException {


        MerchantRedemptionPartner merchantRedemptionPartner = merchantRedemptionPartnerService.saveMerchantRedemptionPartner(MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner());
        log.info(merchantRedemptionPartner.toString());
        merchantRedemptionPartnerSet.add(merchantRedemptionPartner);

    }

    @Test
    public void test2Update() throws InspireNetzException {

        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartner = merchantRedemptionPartnerService.saveMerchantRedemptionPartner(merchantRedemptionPartner);
        log.info("Original MerchantRedemptionPartner " + merchantRedemptionPartner.toString());

        MerchantRedemptionPartner updatedMerchantRedemptionPartner = MerchantRedemptionPartnerFixture.updatedStandardMerchantRedemptionPartner(merchantRedemptionPartner);
        updatedMerchantRedemptionPartner = merchantRedemptionPartnerService.saveMerchantRedemptionPartner(updatedMerchantRedemptionPartner);
        log.info("Updated MerchantRedemptionPartner "+ updatedMerchantRedemptionPartner.toString());

        merchantRedemptionPartnerSet.add(merchantRedemptionPartner);
        merchantRedemptionPartnerSet.add(updatedMerchantRedemptionPartner);

    }

    @Test
    public void test4FindByMemMerchantNoAndRedemptionMerchant() throws InspireNetzException {

        // Create the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartnerService.saveMerchantRedemptionPartner(merchantRedemptionPartner);
        Assert.assertNotNull(merchantRedemptionPartner);
        log.info("MerchantRedemptionPartner created");

        MerchantRedemptionPartner fetchMerchantRedemptionPartner = merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(merchantRedemptionPartner.getMrpMerchantNo(), merchantRedemptionPartner.getMrpRedemptionMerchant());
        Assert.assertNotNull(fetchMerchantRedemptionPartner);
        log.info("Fetched merchantRedemptionPartner info" + merchantRedemptionPartner.toString());

        merchantRedemptionPartnerSet.add(merchantRedemptionPartner);

    }

    @Test
    public void test5FindByMemMerchantNo() throws InspireNetzException {

        // Create the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartnerService.saveMerchantRedemptionPartner(merchantRedemptionPartner);
        log.info("MerchantRedemptionPartner created");

        // Check the merchantRedemptionPartner name
        List<MerchantRedemptionPartner> merchantRedemptionPartnerList = merchantRedemptionPartnerService.findByMrpMerchantNo(merchantRedemptionPartner.getMrpMerchantNo());
        Assert.assertNotNull(merchantRedemptionPartnerList);
        log.info("merchantRedemptionPartner list "+merchantRedemptionPartnerList.toString());

        merchantRedemptionPartnerSet.add(merchantRedemptionPartner);

    }

    @Test
    public void test5FindByMrpRedemptionMerchant() throws InspireNetzException {

        // Create the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartnerService.saveMerchantRedemptionPartner(merchantRedemptionPartner);
        log.info("MerchantRedemptionPartner created");

        // Check the merchantRedemptionPartner name
        List<MerchantRedemptionPartner> merchantRedemptionPartnerList = merchantRedemptionPartnerService.findByMrpRedemptionMerchant(merchantRedemptionPartner.getMrpRedemptionMerchant());
        Assert.assertNotNull(merchantRedemptionPartnerList);
        log.info("merchantRedemptionPartner list "+merchantRedemptionPartnerList.toString());

        merchantRedemptionPartnerSet.add(merchantRedemptionPartner);

    }


    @Test
    public void test6getRedemptionPartnerFormAdminFilter() throws InspireNetzException {

        //create redemption merchant
        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();

        //save redemption merchant
        redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);

        redemptionMerchants.add(redemptionMerchant);

        MerchantRedemptionPartner merchantRedemptionPartner =MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();

        merchantRedemptionPartner.setMrpEnabled(IndicatorStatus.NO);

        merchantRedemptionPartner.setMrpRedemptionMerchant(redemptionMerchant.getRemNo());

        merchantRedemptionPartner =merchantRedemptionPartnerService.saveMerchantRedemptionPartner(merchantRedemptionPartner);

        merchantRedemptionPartnerSet.add(merchantRedemptionPartner);

        //get pageable object
        Page<MerchantRedemptionPartner> merchantRedemptionPartners =merchantRedemptionPartnerService.getRedemptionPartnerFormAdminFilter(merchantRedemptionPartner.getMrpMerchantNo(),"0","0");

        //assertion error
        Assert.assertNotNull(merchantRedemptionPartner);
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

      for (MerchantRedemptionPartner merchantRedemptionPartner:merchantRedemptionPartnerSet){

        merchantRedemptionPartnerService.deleteMerchantRedemptionPartner(merchantRedemptionPartner);
      }

      for (RedemptionMerchant redemptionMerchant:redemptionMerchants){

          redemptionMerchantService.deleteRedemptionMerchant(redemptionMerchant.getRemNo());
      }

    }
}
