package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.core.repository.RedemptionVoucherRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherFixture;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class RedemptionVoucherRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherRepositoryTest.class);

    @Autowired
    private RedemptionVoucherRepository redemptionVoucherRepository;

    Set<RedemptionVoucher> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucherFixture.standardRedemptionVoucher());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        log.info(redemptionVoucher.toString());
        Assert.assertNotNull(redemptionVoucher.getRvrId());

    }

    @Test
    public void test2Update() {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);


        RedemptionVoucher updatedRedemptionVoucher = RedemptionVoucherFixture.updatedStandardRedemptionVoucher(redemptionVoucher);
        updatedRedemptionVoucher = redemptionVoucherRepository.save(updatedRedemptionVoucher);
        log.info("Updated RedemptionVoucher "+ updatedRedemptionVoucher.toString());

    }

    @Test
    public void findByRvrMerchantNoAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc() {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());
        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        List<RedemptionVoucher> redemptionVoucherList = redemptionVoucherRepository.findByRvrMerchantNoAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(redemptionVoucher.getRvrMerchantNo(),redemptionVoucher.getRvrLoyaltyId(),DBUtils.covertToSqlDate("2010-08-10"),DBUtils.covertToSqlDate("2016-08-10"));

        Assert.assertNotNull(redemptionVoucherList);
    }

    @Test
    public void findByRvrMerchantNoAndRvrVoucherCodeLikeAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc() {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);


        RedemptionVoucher updatedRedemptionVoucher = RedemptionVoucherFixture.updatedStandardRedemptionVoucher(redemptionVoucher);
        updatedRedemptionVoucher = redemptionVoucherRepository.save(updatedRedemptionVoucher);
        log.info("Updated RedemptionVoucher "+ updatedRedemptionVoucher.toString());

    }

    @Test
    public void findByRvrMerchantNoAndRvrProductCodeLikeAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc() {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);


        RedemptionVoucher updatedRedemptionVoucher = RedemptionVoucherFixture.updatedStandardRedemptionVoucher(redemptionVoucher);
        updatedRedemptionVoucher = redemptionVoucherRepository.save(updatedRedemptionVoucher);
        log.info("Updated RedemptionVoucher "+ updatedRedemptionVoucher.toString());

    }

    @Test
    public void findByRvrMerchantAndRvrLoyaltyId() {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);


        RedemptionVoucher updatedRedemptionVoucher = RedemptionVoucherFixture.updatedStandardRedemptionVoucher(redemptionVoucher);
        updatedRedemptionVoucher = redemptionVoucherRepository.save(updatedRedemptionVoucher);
        log.info("Updated RedemptionVoucher "+ updatedRedemptionVoucher.toString());

    }

    @Test
    public void test3FindByRvrId() {

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        RedemptionVoucher searchredemptionVoucher = redemptionVoucherRepository.findByRvrId(redemptionVoucher.getRvrId());
        Assert.assertNotNull(searchredemptionVoucher);
        Assert.assertTrue(redemptionVoucher.getRvrId().longValue() ==  searchredemptionVoucher.getRvrId().longValue());;
        log.info("Searched RedemptionVoucher : " + searchredemptionVoucher.toString());


    }

    @Test
    public void test4FindByRvrCustomerNoAndRvrStatus(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        Page<RedemptionVoucher> searchRedemptionVoucher = redemptionVoucherRepository.findByRvrMerchantNoAndRvrCustomerNoAndRvrStatus(1L,redemptionVoucher.getRvrCustomerNo(),redemptionVoucher.getRvrStatus(),constructPageSpecification(0));
        Assert.assertTrue(searchRedemptionVoucher.hasContent());
        log.info("Searched RedemptionVoucher : " + searchRedemptionVoucher.toString());

    }

    @Test
    public void test6FindByRvrCustomerNoAndRvrMerchantAndRvrVoucherCode(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        RedemptionVoucher searchRedemptionVoucher = redemptionVoucherRepository.findByRvrMerchantNoAndRvrCustomerNoAndRvrMerchantAndRvrVoucherCodeAndRvrStatus(1L,redemptionVoucher.getRvrCustomerNo(),redemptionVoucher.getRvrMerchant(),redemptionVoucher.getRvrVoucherCode(),redemptionVoucher.getRvrStatus());
        Assert.assertTrue(searchRedemptionVoucher != null);
        log.info("Searched RedemptionVoucher : " + searchRedemptionVoucher.toString());

    }

    @Test
    public void test6FindByRvrVoucherCode(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        Page<RedemptionVoucher> searchRedemptionVoucher = redemptionVoucherRepository.findByRvrMerchantNoAndRvrVoucherCodeLikeOrderByRvrIdDesc(1L,redemptionVoucher.getRvrVoucherCode(),constructPageSpecification(0));
        Assert.assertTrue(searchRedemptionVoucher != null);
        log.info("Searched RedemptionVoucher : " + searchRedemptionVoucher.toString());

    }
    @Test
    public void test6FindByProductCode(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        Page<RedemptionVoucher> searchProductCode = redemptionVoucherRepository.findByRvrMerchantNoAndRvrProductCodeLikeOrderByRvrIdDesc(1L,redemptionVoucher.getRvrProductCode(),constructPageSpecification(0));
        Assert.assertTrue(searchProductCode != null);
        log.info("Searched RedemptionVoucher : " + searchProductCode.toString());

    }

    @Test
    public void test6FindByRvrLoyaltyId(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        Page<RedemptionVoucher> searchRedemptionByLoyaltyId = redemptionVoucherRepository.findByRvrMerchantNoAndRvrVoucherCodeLikeOrderByRvrIdDesc(1L,redemptionVoucher.getRvrLoyaltyId(),constructPageSpecification(0));
        Assert.assertTrue(searchRedemptionByLoyaltyId != null);
        log.info("Searched RedemptionVoucher : " + searchRedemptionByLoyaltyId.toString());

    }

    @Test
    public void test7SearchRedemptionVoucher(){

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);
        log.info("Original RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        // Get the data
        Page<RedemptionVoucher> searchRedemptionByLoyaltyId = redemptionVoucherRepository.searchForRedemptionVoucher(redemptionVoucher.getRvrMerchant(),1L, "0", redemptionVoucher.getRvrLoyaltyId(),DBUtils.covertToSqlDate("1990-01-01"),DBUtils.covertToSqlDate("9999-01-01"),constructPageSpecification(0));
        Assert.assertTrue(searchRedemptionByLoyaltyId != null);
        log.info("Searched RedemptionVoucher : " + searchRedemptionByLoyaltyId.getContent());

    }

    @Test
    public void test8redemptionVoucherIsValid(){


        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();

        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);

        log.info("RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        //check voucher is valid or not
        RedemptionVoucher redemptionVoucher1 = redemptionVoucherRepository.findByRvrVoucherCode(redemptionVoucher.getRvrVoucherCode());

        log.info("RedemptionVoucher :"+redemptionVoucher1);

        Assert.assertNotNull(redemptionVoucher1);



    }


    @Test
    public void test8RvrIn(){


        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();

        redemptionVoucher.setRvrUniqueBatchId("9088888");

        redemptionVoucher = redemptionVoucherRepository.save(redemptionVoucher);

        RedemptionVoucher redemptionVoucher1 = RedemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher1.setRvrUniqueBatchId("9088888000");


        redemptionVoucher1 = redemptionVoucherRepository.save(redemptionVoucher1);
        List<Long> listLong = new ArrayList<>();

        listLong.add(redemptionVoucher.getRvrId());
        listLong.add(redemptionVoucher1.getRvrId());

        log.info("RedemptionVoucher " + redemptionVoucher.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucher);

        //check voucher is valid or not
        List<RedemptionVoucher> redemptionVoucher2 = redemptionVoucherRepository.findByRvrIdIn(listLong);

        log.info("RedemptionVoucher :"+redemptionVoucher2);

        Assert.assertNotNull(redemptionVoucher2);



    }




    @After
    public void tearDown() {

        for(RedemptionVoucher redemptionVoucher : tempSet ) {

            redemptionVoucherRepository.delete(redemptionVoucher);

        }

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

//    /**
//     * Returns a Sort object which sorts persons in ascending order by using the last name.
//     * @return
//     */
//    private Sort sortByLastNameAsc() {
//        return new Sort(Sort.Direction.ASC, "rvrVoucherCode");
//    }

}
