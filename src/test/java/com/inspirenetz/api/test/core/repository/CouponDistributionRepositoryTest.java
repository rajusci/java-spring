package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.core.repository.CouponDistributionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CouponDistributionFixture;
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
public class CouponDistributionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CouponDistributionRepositoryTest.class);

    @Autowired
    private CouponDistributionRepository couponDistributionRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CouponDistribution couponDistribution = couponDistributionRepository.save(CouponDistributionFixture.standardCouponDistribution());
        log.info(couponDistribution.toString());
        Assert.assertNotNull(couponDistribution.getCodId());

    }

    @Test
    public void test2Update() {

        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistribution = couponDistributionRepository.save(couponDistribution);
        log.info("Original CouponDistribution " + couponDistribution.toString());

        CouponDistribution updatedCouponDistribution = CouponDistributionFixture.updatedStandardCouponDistribution(couponDistribution);
        updatedCouponDistribution = couponDistributionRepository.save(updatedCouponDistribution);
        log.info("Updated CouponDistribution "+ updatedCouponDistribution.toString());

    }



    @Test
    public void test3FindByCodMerchantNo() {

        // Get the standard couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();

        Page<CouponDistribution> couponDistributions = couponDistributionRepository.findByCodMerchantNo(couponDistribution.getCodMerchantNo(),constructPageSpecification(0));
        log.info("couponDistributions by merchant no " + couponDistributions.toString());
        Set<CouponDistribution> couponDistributionSet = Sets.newHashSet((Iterable<CouponDistribution>)couponDistributions);
        log.info("couponDistribution list "+couponDistributionSet.toString());

    }

    @Test
    public void test4FindByCodMerchantNoAndCodCouponCode() {

        // Create the couponDistribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistributionRepository.save(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");

        CouponDistribution fetchCouponDistribution = couponDistributionRepository.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(),couponDistribution.getCodCouponCode());
        Assert.assertNotNull(fetchCouponDistribution);
        log.info("Fetched couponDistribution info" + couponDistribution.toString());

    }


    @After
    public void tearDown() {

        Set<CouponDistribution> couponDistributions = CouponDistributionFixture.standardCouponDistributions();

        for(CouponDistribution couponDistribution: couponDistributions) {

            CouponDistribution delCouponDistribution = couponDistributionRepository.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(),couponDistribution.getCodCouponCode());

            if ( delCouponDistribution != null ) {
                couponDistributionRepository.delete(delCouponDistribution);
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
        return new Sort(Sort.Direction.ASC, "codId");
    }


}
