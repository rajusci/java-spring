package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.CouponCodeType;
import com.inspirenetz.api.core.dictionary.CouponValueType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.CouponRepository;
import com.inspirenetz.api.core.service.CouponDistributionService;
import com.inspirenetz.api.core.service.CouponService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CouponDistributionFixture;
import com.inspirenetz.api.test.core.fixture.CouponFixture;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.PurchaseFixture;
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
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CouponServiceTest {


    private static Logger log = LoggerFactory.getLogger(CouponServiceTest.class);

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponDistributionService couponDistributionService;

    @Autowired
    private CouponRepository couponRepository;


    @Before
    public void setUp() {}



    @Test
    public void test1FindByMerchantNo() {

        // Get the standard coupon
        Coupon coupon = CouponFixture.standardCoupon();

        Page<Coupon> coupons = couponService.findByCpnMerchantNo(coupon.getCpnMerchantNo(),constructPageSpecification(1));
        log.info("coupons by merchant no " + coupons.toString());
        Set<Coupon> couponSet = Sets.newHashSet((Iterable<Coupon>) coupons);
        log.info("coupon list "+couponSet.toString());

    }

    @Test
    public void test2FindByCpnMerchantNoAndCpnCode() throws InspireNetzException {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        couponService.saveCoupon(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");

        Coupon fetchCoupon = couponService.findByCpnMerchantNoAndCpnCouponCode(coupon.getCpnMerchantNo(), coupon.getCpnCouponCode());
        Assert.assertNotNull(fetchCoupon);
        log.info("Fetched coupon info" + coupon.toString());

    }

    @Test
    public void test3FindByCpnMerchantNoAndCpnNameLike() throws InspireNetzException {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        couponService.saveCoupon(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");

        // Check the coupon name
        Page<Coupon> coupons = couponService.findByCpnMerchantNoAndCpnCouponNameLike(coupon.getCpnMerchantNo(), "%test%", constructPageSpecification(0));
        Assert.assertTrue(coupons.hasContent());
        Set<Coupon> couponSet = Sets.newHashSet((Iterable<Coupon>)coupons);
        log.info("coupon list "+couponSet.toString());


    }

    @Test
    public void test4IsDuplicateCouponExisting()throws InspireNetzException {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");

        // Create a new coupon
        Coupon newCoupon = CouponFixture.standardCoupon();
        boolean exists = couponService.isDuplicateCouponExisting(newCoupon);
        Assert.assertTrue(exists);
        log.info("Coupon exists");


    }

    @Test
    public void test5DeleteCoupon() throws InspireNetzException {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");

        // call the delete coupon
        couponService.deleteCoupon(coupon.getCpnCouponId());

        // Try searching for the coupon
        Coupon checkCoupon  = couponService.findByCpnMerchantNoAndCpnCouponName(coupon.getCpnMerchantNo(), coupon.getCpnCouponName());
        Assert.assertNull(checkCoupon);
        log.info("coupon deleted");

    }
    
    @Test
    public void test6IsCouponCodeValidFixedType() throws InspireNetzException {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);
        Assert.assertNotNull(coupon.getCpnCouponId());
        log.info("Coupon created");


        // Get the standard coupon
        Coupon standardCoupon = CouponFixture.standardCoupon();
        boolean isValid = couponService.isCouponCodeValid(standardCoupon);
        Assert.assertTrue(!isValid);
        log.info("fixed coupon code is not valid");

    }

    @Test
    public void test7IsCouponCodeValidRangeType() throws InspireNetzException {

        Set<Coupon> couponSet = CouponFixture.standardCoupons();
        List<Coupon> couponList = Lists.newArrayList((Iterable<Coupon>)couponSet);
        couponService.saveAll(couponList);
        log.info("Coupons created");


        // Get the standard coupon
        Coupon checkCoupon = CouponFixture.standardCoupon();
        checkCoupon.setCpnCouponCodeType(CouponCodeType.RANGE);
        checkCoupon.setCpnCouponCodeFrom("CPN10003");
        checkCoupon.setCpnCouponCodeTo("CPN10008");
        boolean isValid = couponService.isCouponCodeValid(checkCoupon);
        Assert.assertTrue(!isValid);
        log.info("range coupon code is not valid");

    }

    @Test
    public void test8CalculateCouponValue() throws InspireNetzException {

        // Create the coupon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon.setCpnValueType(CouponValueType.PERCENTAGE);
        coupon.setCpnValue(10.0);

        log.info("Coupon Details "+coupon.toString());

        // Get the purchase object
        Purchase purchase = PurchaseFixture.standardPurchase();

        // Get the coupon value
        double couponValue = couponService.calculateCouponValue(coupon,purchase);
        Assert.assertTrue(couponValue > 0);
        log.info("Coupon value : "+Double.toString(couponValue));

    }

    @Test
    public void test9IsCouponGeneralRulesValid() throws InspireNetzException {

        // Create the Copuon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);

        // Get the purchase information
        Purchase purchase = PurchaseFixture.standardPurchase();

        // Check the coupon
        boolean isValid = couponService.isCouponGeneralRulesValid(coupon, purchase);
        Assert.assertTrue(isValid);
        log.info("The coupon is valid for the customer");


    }


    @Test
    public void test10GetCustomerCoupons() throws InspireNetzException {

        // Create the Copuon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);

        // Create the distribution
        CouponDistribution couponDistribution = CouponDistributionFixture.standardCouponDistribution();
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);

        // Get the customer information
        Customer customer = CustomerFixture.standardCustomer();

        // Get the customer coupons
        List<Coupon> couponList = couponService.getCustomerCoupons(customer);
        Assert.assertTrue(!couponList.isEmpty());
        log.info("CouponList : " + couponList.toString());

    }

    @Test
    public void test11ValidateCoupon() throws InspireNetzException {

        // Create the Copuon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon = couponService.saveCoupon(coupon);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();


        boolean isValid = couponService.validateCoupon(coupon,purchase,new ArrayList<PurchaseSKU>());
        Assert.assertTrue(isValid);
        log.info("Coupon is valid");


    }

    @Test
    public void test12EvaluateCoupon() throws InspireNetzException {

        // Create the Copuon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon.setCpnValue(5.0);
        coupon = couponService.saveCoupon(coupon);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        // Get the value for the coupon
        double couponValue = couponService.evaluateCoupon(coupon,purchase,new ArrayList<PurchaseSKU>());
        Assert.assertTrue(couponValue > 0);
        log.info("CouponValue : " + Double.toString(couponValue));


    }

    @Test
    public void test13getCouponsForUser() throws InspireNetzException {

        // Create the Copuon
        Coupon coupon = CouponFixture.standardCoupon();
        coupon.setCpnValue(5.0);
        coupon = couponService.saveCoupon(coupon);

        // Get the standard purchase
        Purchase purchase = PurchaseFixture.standardPurchase();

        // Get the value for the coupon
        Page<Coupon> coupons = couponService.getCouponsForUser("4444444444",0L);
        Assert.assertNotNull(coupons);
        log.info("Fetched Coupons : " + coupons.toString());


    }


    @After
    public void tearDown() throws InspireNetzException {

        Set<Coupon> coupons = CouponFixture.standardCoupons();

        for(Coupon coupon: coupons) {

            Coupon delCoupon = couponService.findByCpnMerchantNoAndCpnCouponName(coupon.getCpnMerchantNo(), coupon.getCpnCouponName());

            if ( delCoupon != null ) {
                couponService.deleteCoupon(delCoupon.getCpnCouponId());
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
