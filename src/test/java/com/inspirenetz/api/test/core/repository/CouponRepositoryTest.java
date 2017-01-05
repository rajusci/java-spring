package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Coupon;
import com.inspirenetz.api.core.domain.CouponTransaction;
import com.inspirenetz.api.core.repository.CouponRepository;
import com.inspirenetz.api.core.repository.CouponTransactionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CouponFixture;
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
public class CouponRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CouponRepositoryTest.class);

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTransactionRepository couponTransactionRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        Coupon coupon = couponRepository.save(CouponFixture.standardCoupon());
        log.info(coupon.toString());
        Assert.assertNotNull(coupon.getCpnCouponId());

    }

    @Test
    public void test2Update() {

        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponRepository.save(coupon);
        log.info("Original Coupon " + coupon.toString());

        Coupon updatedCoupon = CouponFixture.updatedStandardCoupon(coupon);
        updatedCoupon = couponRepository.save(updatedCoupon);
        log.info("Updated Coupon "+ updatedCoupon.toString());

    }

    @Test
    public void test3FindByCpnMerchantNo() {

        // Get the standard coupon
        Coupon coupon = CouponFixture.standardCoupon();

        Page<Coupon> coupons = couponRepository.findByCpnMerchantNo(coupon.getCpnMerchantNo(),constructPageSpecification(0));
        log.info("coupons by merchant no " + coupons.toString());
        Set<Coupon> couponSet = Sets.newHashSet((Iterable<Coupon>)coupons);
        log.info("coupon list "+couponSet.toString());

    }

    @Test
    public void test4FindByCpnMerchantNoAndCpnCode() {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        couponRepository.save(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");

        Coupon fetchCoupon = couponRepository.findByCpnMerchantNoAndCpnCouponCode(coupon.getCpnMerchantNo(),coupon.getCpnCouponCode());
        Assert.assertNotNull(fetchCoupon);
        log.info("Fetched coupon info" + coupon.toString());

    }

    @Test
    public void test5FindByCpnMerchantNoAndCpnNameLike() {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        couponRepository.save(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");

        // Check the coupon name
        Page<Coupon> coupons = couponRepository.findByCpnMerchantNoAndCpnCouponNameLike(coupon.getCpnMerchantNo(), "%test%", constructPageSpecification(0));
        Assert.assertTrue(coupons.hasContent());
        Set<Coupon> couponSet = Sets.newHashSet((Iterable<Coupon>)coupons);
        log.info("coupon list "+couponSet.toString());


    }

    @Test
    public void test6FindSameRangeCoupons() {

        // Create the coupon
        Set<Coupon> couponSet = CouponFixture.standardCoupons();
        couponRepository.save(couponSet);
        log.info("Coupon created");

        // Get the standard coupon
        Coupon coupon = CouponFixture.standardCoupon();

        // Check the same range
        List<Coupon> couponList = couponRepository.findSameRangeCoupons(coupon.getCpnMerchantNo(),"CPN10003","CPN10003");
        Assert.assertNotNull(couponList);
        log.info("Coupons list :" + couponList.toString());



    }


    @After
    public void tearDown() {

        Set<Coupon> coupons = CouponFixture.standardCoupons();

        for(Coupon coupon: coupons) {

            Coupon delCoupon = couponRepository.findByCpnMerchantNoAndCpnCouponName(coupon.getCpnMerchantNo(), coupon.getCpnCouponName());

            if ( delCoupon != null ) {
                couponRepository.delete(delCoupon);
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
        return new Sort(Sort.Direction.ASC, "cpnCouponName");
    }


}
