package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.CustomerReferralStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.repository.CustomerReferralRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerReferralFixture;
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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fayiz on 27/4/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerReferralRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerReferralRepositoryTest.class);

    @Autowired
    private CustomerReferralRepository customerReferralRepository;

    Set<CustomerReferral> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        customerReferralRepository.save(customerReferral);

        log.info(customerReferral.toString());

        Assert.assertNotNull(customerReferral.getCsrId());

        // Add to the tempSet
        tempSet.add(customerReferral);

    }

    @Test
    public void test2Update() {

        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();
        customerReferral = customerReferralRepository.save(customerReferral);
        log.info("Original customer referral" + customerReferral.toString());

        // Add to the tempSet
        tempSet.add(customerReferral);

        CustomerReferral updatedCustomerReferral = CustomerReferralFixture.updatedStandardCustomerReferral(customerReferral);
        updatedCustomerReferral = customerReferralRepository.save(updatedCustomerReferral);
        log.info("Updated customer referral"+ updatedCustomerReferral.toString());

    }



    @Test
    public void test3FindByCsrId() {

        // Get the standard customer
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();
        customerReferral = customerReferralRepository.save(customerReferral);

        // Add to the tempSet
        tempSet.add(customerReferral);

        CustomerReferral fetchedCustomerReferral = customerReferralRepository.findByCsrId(customerReferral.getCsrId());
        log.info("Customer referral by csrId " + fetchedCustomerReferral.toString());

    }

    @Test
    public void test4FindByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc() {

        // Create the customer
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        customerReferralRepository.save(customerReferral);

        Assert.assertNotNull(customerReferral.getCsrId());

        log.info("Customer referral created");

        // Add to the tempSet
        tempSet.add(customerReferral);

        Page<CustomerReferral> fetchedCustomerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrLoyaltyId(),constructPageSpecification(1));

        Assert.assertNotNull(fetchedCustomerReferrals);

        log.info("Fetched customer referrals" + fetchedCustomerReferrals.toString());

    }

    @Test
    public void test4FindByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc() {

        // Create the customer
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        customerReferralRepository.save(customerReferral);

        Assert.assertNotNull(customerReferral.getCsrId());

        log.info("Customer referral created");

        // Add to the tempSet
        tempSet.add(customerReferral);

        List<CustomerReferral> fetchedCustomerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrRefMobile(), IndicatorStatus.NO);

        Assert.assertNotNull(fetchedCustomerReferrals);

        log.info("Fetched customer referrals" + fetchedCustomerReferrals.toString());

    }

    @Test
    public void test4FindByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileAndNotCsrRefStatus() {

        // Create the customer
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        customerReferral.setCsrRefStatus(CustomerReferralStatus.NEW);

        customerReferralRepository.save(customerReferral);

        Assert.assertNotNull(customerReferral.getCsrId());

        log.info("Customer referral created");

        // Add to the tempSet
        tempSet.add(customerReferral);

        CustomerReferral fetchedCustomerReferral = customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileAndCsrRefStatusNot(customerReferral.getCsrMerchantNo(), customerReferral.getCsrLoyaltyId(),customerReferral.getCsrRefMobile(), CustomerReferralStatus.OVERRIDDEN);

        Assert.assertNotNull(fetchedCustomerReferral);

        log.info("Fetched customer referrals" + fetchedCustomerReferral.toString());

    }

    @Test
    public void findByCsrMerchantNoAndCsrLoyaltyIdAndCsrJoinStatusAndCsrRefTimeStampBetween() {

        // Create the customer
        Set<CustomerReferral> customerReferral = CustomerReferralFixture.standardCustomerReferrals();

        customerReferralRepository.save(customerReferral);


        log.info("Customer referral created");

        for (CustomerReferral customerReferral1 :customerReferral){

            // Add to the tempSet
            tempSet.add(customerReferral1);
        }

        //convert time stmp
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(DBUtils.covertToSqlDate("2015-10-02"));

        Calendar calendar1 =Calendar.getInstance();
        calendar1.setTime(DBUtils.covertToSqlDate("2015-10-24"));

        Timestamp timestamp =new Timestamp(calendar.getTimeInMillis());
        timestamp.setHours(0);
        timestamp.setMinutes(0);
        timestamp.setSeconds(0);

        Timestamp timestamp1 =new Timestamp(calendar1.getTimeInMillis());

        timestamp1.setHours(23);
        timestamp1.setMinutes(59);
        timestamp1.setSeconds(59);

        List<CustomerReferral> customerReferralList =customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(1L, "9400651677", timestamp, timestamp1);

        Assert.assertNotNull(customerReferralList);

    }




    @After
    public void tearDown() {


        for(CustomerReferral customerReferral: tempSet) {

            CustomerReferral delCustomerReferral = customerReferralRepository.findByCsrId(customerReferral.getCsrId());

            if ( delCustomerReferral != null ) {
                customerReferralRepository.delete(delCustomerReferral);
            }

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


}
