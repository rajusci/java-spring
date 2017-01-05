package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.RewardCurrencyExpiryOption;
import com.inspirenetz.api.core.dictionary.RewardCurrencyExpiryPeriod;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.repository.RewardCurrencyRepository;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class RewardCurrencyServiceTest {


    private static Logger log = LoggerFactory.getLogger(RewardCurrencyServiceTest.class);

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private RewardCurrencyRepository rewardCurrencyRepository;


    @Before
    public void setUp() {}



    @Test
    public void test3FindByRwdMerchantNo() {

        // Get the standard rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        Page<RewardCurrency> rewardCurrencys = rewardCurrencyService.findByRwdMerchantNo(rewardCurrency.getRwdMerchantNo(),constructPageSpecification(1));
        log.info("rewardCurrencys by merchant no " + rewardCurrencys.toString());
        Set<RewardCurrency> rewardCurrencySet = Sets.newHashSet((Iterable<RewardCurrency>)rewardCurrencys);
        log.info("rewardCurrency list "+rewardCurrencySet.toString());

    }


    @Test
    public void test5FindByRwdMerchantNoAndRwdCurrencyNameLike() throws InspireNetzException {

        // Create the rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());
        log.info("RewardCurrency created");

        // Check the rewardCurrency name
        Page<RewardCurrency> rewardCurrencys = rewardCurrencyService.findByRwdMerchantNoAndRwdCurrencyNameLike(rewardCurrency.getRwdMerchantNo(), "%super%", constructPageSpecification(0));
        Assert.assertTrue(rewardCurrencys.hasContent());
        Set<RewardCurrency> rewardCurrencySet = Sets.newHashSet((Iterable<RewardCurrency>)rewardCurrencys);
        log.info("rewardCurrency list "+rewardCurrencySet.toString());

    }


    @Test
    public void test6isRewardCurrencyNameExisting() throws InspireNetzException {

        // Create the rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());
        log.info("RewardCurrency created");

        RewardCurrency rewardCurrency1 = RewardCurrencyFixture.standardRewardCurrency();

        // Check the rewardCurrency name
        boolean exists = rewardCurrencyService.isDuplicateRewardCurrencyNameExisting(rewardCurrency1);
        Assert.assertTrue(exists);
        log.info("reward currency exists");

    }


    @Test
    public void test7GetRewardCurrencyKeyMap() {

        Set<RewardCurrency> rewardCurrencies = RewardCurrencyFixture.standardRewardCurrencies();
        rewardCurrencyRepository.save(rewardCurrencies);

        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        HashMap<Long,RewardCurrency> rewardCurrencyHashMap = rewardCurrencyService.getRewardCurrencyKeyMap(rewardCurrency.getRwdMerchantNo());
        log.info("Reward currency map" + rewardCurrencyHashMap.toString());

    }



    @Test
    public void test8GetCashbackValue() {

        Set<RewardCurrency> rewardCurrencies = RewardCurrencyFixture.standardRewardCurrencies();
        rewardCurrencyRepository.save(rewardCurrencies);

        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        double value = rewardCurrencyService.getCashbackValue(rewardCurrency,100.0);
        Assert.assertTrue(value > 0);
        log.info("Cashback value " + Double.toString(value));

    }


    @Test
    public void test8GetCashbackQtyForAmount() {

        Set<RewardCurrency> rewardCurrencies = RewardCurrencyFixture.standardRewardCurrencies();
        rewardCurrencyRepository.save(rewardCurrencies);

        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        double rewardQty = rewardCurrencyService.getCashbackQtyForAmount(rewardCurrency, 100.0);
        Assert.assertTrue(rewardQty > 0);
        log.info("Reward Qty " + Double.toString(rewardQty));

    }



    @Test
    public void test9GetRewardExpiryDate() {

        // Create the rewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency.setRwdExpiryOption(RewardCurrencyExpiryOption.EXPIRY_DAYS);
        rewardCurrency.setRwdExpiryDays(123);
        rewardCurrency.setRwdExpiryPeriod(RewardCurrencyExpiryPeriod.END_OF_QUARTER);

        Date expiryDate = rewardCurrencyService.getRewardExpiryDate(rewardCurrency,new Date());
        Assert.assertNotNull(expiryDate);
        log.info("Expiry Date " + expiryDate);

    }




    @After
    public void tearDown() throws InspireNetzException {

        Set<RewardCurrency> rewardCurrencys = RewardCurrencyFixture.standardRewardCurrencies();

        for(RewardCurrency rewardCurrency: rewardCurrencys) {

            RewardCurrency delRewardCurrency = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyName(rewardCurrency.getRwdMerchantNo(), rewardCurrency.getRwdCurrencyName());

            if ( delRewardCurrency != null ) {
                rewardCurrencyService.deleteRewardCurrency(delRewardCurrency.getRwdCurrencyId());
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
