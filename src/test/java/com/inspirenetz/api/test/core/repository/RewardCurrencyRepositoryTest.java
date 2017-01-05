package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.repository.RewardCurrencyRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.RewardCurrencyFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class RewardCurrencyRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(RewardCurrencyRepositoryTest.class);

    @Autowired
    private RewardCurrencyRepository rewardCurrencyRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        RewardCurrency rewardCurrency = rewardCurrencyRepository.save(RewardCurrencyFixture.standardRewardCurrency());
        log.info(rewardCurrency.toString());
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());

    }

    @Test
    public void test2Update() {

        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyRepository.save(rewardCurrency);
        log.info("Original RewardCurrency " + rewardCurrency.toString());

        RewardCurrency updatedRewardCurrency = RewardCurrencyFixture.updatedStandardRewardCurrency(rewardCurrency);
        updatedRewardCurrency = rewardCurrencyRepository.save(updatedRewardCurrency);
        log.info("Updated RewardCurrency "+ updatedRewardCurrency.toString());

    }



    @Test
    public void test3FindByRwdMerchantNo() {

        // Get the standard rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        Page<RewardCurrency> rewardCurrencys = rewardCurrencyRepository.findByRwdMerchantNo(rewardCurrency.getRwdMerchantNo(),constructPageSpecification(1));
        log.info("rewardCurrencys by merchant no " + rewardCurrencys.toString());
        Set<RewardCurrency> rewardCurrencySet = Sets.newHashSet((Iterable<RewardCurrency>)rewardCurrencys);
        log.info("rewardCurrency list "+rewardCurrencySet.toString());

    }


    @Test
    public void test4FindByRwdMerchantNo() {

        // Get the standard rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        List<RewardCurrency> rewardCurrencys = rewardCurrencyRepository.findByRwdMerchantNo(rewardCurrency.getRwdMerchantNo());
        log.info("rewardCurrencys by merchant no " + rewardCurrencys.toString());
        Set<RewardCurrency> rewardCurrencySet = Sets.newHashSet((Iterable<RewardCurrency>)rewardCurrencys);
        log.info("rewardCurrency list "+rewardCurrencySet.toString());

    }


    @Test
    public void test5FindByRwdMerchantNoAndRwdCurrencyNameLike() {

        // Create the rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrencyRepository.save(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());
        log.info("RewardCurrency created");

        // Check the rewardCurrency name
        Page<RewardCurrency> rewardCurrencys = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyNameLike(rewardCurrency.getRwdMerchantNo(), "%super%", constructPageSpecification(0));
        Assert.assertTrue(rewardCurrencys.hasContent());
        Set<RewardCurrency> rewardCurrencySet = Sets.newHashSet((Iterable<RewardCurrency>)rewardCurrencys);
        log.info("rewardCurrency list "+rewardCurrencySet.toString());

    }


    @Test
    public void test6FindByRwdMerchantNoAndRwdCurrencyName() {

        // Create the rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrencyRepository.save(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());
        log.info("RewardCurrency created");

        // Check the rewardCurrency name
        RewardCurrency rewardCurrency1 = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyName(rewardCurrency.getRwdMerchantNo(),rewardCurrency.getRwdCurrencyName());
        Assert.assertNotNull(rewardCurrency1);
        log.info("rewardCurrency information " + rewardCurrency1.toString());

    }

    @After
    public void tearDown() {

        Set<RewardCurrency> rewardCurrencys = RewardCurrencyFixture.standardRewardCurrencies();

        for(RewardCurrency rewardCurrency: rewardCurrencys) {

            RewardCurrency delRewardCurrency = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyName(rewardCurrency.getRwdMerchantNo(),rewardCurrency.getRwdCurrencyName());

            if ( delRewardCurrency != null ) {
                rewardCurrencyRepository.delete(delRewardCurrency);
            }

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
        return new Sort(Sort.Direction.ASC, "rwdCurrencyName");
    }


}
