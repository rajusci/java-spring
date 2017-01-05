package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.RecordStatus;
import com.inspirenetz.api.core.dictionary.UniqueIdType;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.repository.RedemptionRepository;
import com.inspirenetz.api.test.core.fixture.RedemptionFixture;
import com.inspirenetz.api.util.DBUniqueIdGenerator;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.util.DBUtils;
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

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class RedemptionRepositoryTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionRepositoryTest.class);

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void test1Save(){

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemption = redemptionRepository.save(redemption);
        Assert.assertNotNull(redemption.getRdmId());

    }


    @Test
    public void test2Update(){

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemption = redemptionRepository.save(redemption);

        Redemption updatedRedemption = RedemptionFixture.updatedStandardCatalogueRedemption(redemption);
        redemptionRepository.save(redemption);

        Redemption redemption1 = redemptionRepository.findByRdmId(redemption.getRdmId());

        // Check the redemptionData
        Assert.assertNotNull(redemption1);
        log.info("Updated Redemption :" +redemption1.toString());

    }




    @Test
    public void test3FindByRdmMerchantNoAndRdmUniqueBatchTrackingId() {

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemption = redemptionRepository.save(redemption);

        List<Redemption> redemptionList = redemptionRepository.findByRdmMerchantNoAndRdmUniqueBatchTrackingId(redemption.getRdmMerchantNo(),redemption.getRdmUniqueBatchTrackingId());
        Assert.assertTrue(!redemptionList.isEmpty());
        log.info("Redemption List : " + redemptionList.toString());


    }


    @Test
    public void test4FindByRdmId() {

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemption = redemptionRepository.save(redemption);
        Assert.assertNotNull(redemption.getRdmId());

        Redemption searchRedemption = redemptionRepository.findByRdmId(redemption.getRdmId());
        Assert.assertNotNull(searchRedemption);
        log.info("Found Redemption : " + searchRedemption.toString());

    }


    @Test
    public void test5FindByRdmMerchantNoAndRdmLoyaltyIdAndRdmDateBetween() {

        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmRecordStatusAndRdmDateBetween(redemption.getRdmMerchantNo(),redemption.getRdmLoyaltyId(), RecordStatus.RECORD_STATUS_ACTIVE,DBUtils.covertToSqlDate("2014-05-01"),DBUtils.covertToSqlDate("9999-12-31"),constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }



    @Test
    public void test5FindByRdmMerchantNoAndRdmTypeAndRdmDateBetween() {

        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmTypeAndRdmRecordStatusAndRdmDateBetween(redemption.getRdmMerchantNo(),redemption.getRdmType(),RecordStatus.RECORD_STATUS_ACTIVE, DBUtils.covertToSqlDate("2014-05-01"),DBUtils.covertToSqlDate("9999-12-31"),constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test5findByRdmMerchantNoAndRdmLoyaltyIdAndRdmStatusAndRdmRecordStatus() {

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemptionRepository.save(redemption);

        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmStatusAndRdmRecordStatus(redemption.getRdmMerchantNo(),redemption.getRdmLoyaltyId(),redemption.getRdmStatus(),RecordStatus.RECORD_STATUS_ACTIVE,constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test5findByRdmMerchantNoAndRdmLoyaltyIdAndRdmRecordStatus() {

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemptionRepository.save(redemption);

        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmRecordStatus(redemption.getRdmMerchantNo(),redemption.getRdmLoyaltyId(),RecordStatus.RECORD_STATUS_ACTIVE,constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }
    /*
    @Test
    public void test6ListRedemptionRequestsByMerchantNoAndLoyaltyIdAndStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        List<Redemption> redemptionPage = redemptionRepository.listRedemptionRequestsByMerchantNoAndLoyaltyIdAndStatus(redemption.getRdmMerchantNo(), redemption.getRdmLoyaltyId(), 2, constructPageSpecification(0));
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }


    @Test
    public void test7ListRedemptionRequestsByMerchantNoAndTrackingIdAndStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        List<Redemption> redemptionPage = redemptionRepository.listRedemptionRequestsByMerchantNoAndTrackingIdAndStatus(redemption.getRdmMerchantNo(), redemption.getRdmUniqueBatchTrackingId(), 0, constructPageSpecification(0));
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }


    @Test
    public void test8ListRedemptionRequestsByMerchantNoAndProductCodeAndStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        List<Redemption> redemptionPage = redemptionRepository.listRedemptionRequestsByMerchantNoAndProductCodeAndStatus(redemption.getRdmMerchantNo(), redemption.getRdmProductCode(), 0, constructPageSpecification(0));
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }


    @Test
    public void test9ListRedemptionRequestsByMerchantNoAndStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        List<Redemption> redemptionPage = redemptionRepository.listRedemptionRequestsByMerchantNoAndStatus(redemption.getRdmMerchantNo(), 0, constructPageSpecification(0));
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());
    }
*/
    @Test
    public void test9ListRedemptionRequestsByMerchantNoAndStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        Page<Redemption> redemptionPage = redemptionRepository.listRedemptionRequestsByMerchantNoAndStatusAndRdmRecordStatus(redemption.getRdmMerchantNo(), 0, RecordStatus.RECORD_STATUS_ACTIVE,constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        log.info("Redemption List "+redemptionPage.toString());



    }

    @Test
    public void test9findByRdmMerchantNoAndRdmUniqueBatchTrackingIdAndRdmStatusAndRdmRecordStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmUniqueBatchTrackingIdAndRdmStatusAndRdmRecordStatus(redemption.getRdmMerchantNo(), redemption.getRdmUniqueBatchTrackingId(),redemption.getRdmStatus(), RecordStatus.RECORD_STATUS_ACTIVE,constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        log.info("Redemption List "+redemptionPage.toString());



    }

    @Test
    public void test9findByRdmMerchantNoAndRdmUniqueBatchTrackingIdAndRdmRecordStatus() {


        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmUniqueBatchTrackingIdAndRdmRecordStatus(redemption.getRdmMerchantNo(), redemption.getRdmUniqueBatchTrackingId(), RecordStatus.RECORD_STATUS_ACTIVE,constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        log.info("Redemption List "+redemptionPage.toString());



    }
    @Test
    public void test10findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmDateBetween() {


        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmRecordStatusAndRdmDateBetween(redemption.getRdmMerchantNo(),redemption.getRdmLoyaltyId(), redemption.getRdmType(),RecordStatus.RECORD_STATUS_ACTIVE, DBUtils.covertToSqlDate("2014-05-01"), DBUtils.covertToSqlDate("9999-12-31"), constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test10findByRdmMerchantNoAndRdmProductCodeAndRdmStatusAndRdmRecordStatus() {


        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmProductCodeAndRdmStatusAndRdmRecordStatus(redemption.getRdmMerchantNo(),redemption.getRdmProductCode(), redemption.getRdmStatus(),RecordStatus.RECORD_STATUS_ACTIVE, constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test10findByRdmMerchantNoAndRdmProductCodeAndRdmRecordStatus() {


        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmProductCodeAndRdmRecordStatus(redemption.getRdmMerchantNo(),redemption.getRdmProductCode(),RecordStatus.RECORD_STATUS_ACTIVE, constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test10findByRdmMerchantNoAndRdmStatusAndRdmRecordStatus() {


        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmStatusAndRdmRecordStatus(redemption.getRdmMerchantNo(),redemption.getRdmStatus(),RecordStatus.RECORD_STATUS_ACTIVE, constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test10findByRdmMerchantNoAndRdmRecordStatus() {


        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionRepository.findByRdmMerchantNoAndRdmRecordStatus(redemption.getRdmMerchantNo(),RecordStatus.RECORD_STATUS_ACTIVE, constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test10ListRedemptions() {


        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        List<Redemption> redemptionPage = redemptionRepository.findByRdmLoyaltyIdAndRdmMerchantNoAndRdmRecordStatus(redemption.getRdmLoyaltyId(),redemption.getRdmMerchantNo(),RecordStatus.RECORD_STATUS_ACTIVE);
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }


    @After
    public void tearDown(){

        Set<Redemption> redemptions = RedemptionFixture.getStandardRedemptions();

        for(Redemption redemption: redemptions) {

            redemptionRepository.delete(redemption);

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
        return new Sort(Sort.Direction.DESC, "rdmId");
    }

}
