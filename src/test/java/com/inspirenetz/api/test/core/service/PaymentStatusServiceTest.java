package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.PaymentStatus;
import com.inspirenetz.api.core.repository.PaymentStatusRepository;
import com.inspirenetz.api.core.service.PaymentStatusService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.PaymentStatusFixture;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class PaymentStatusServiceTest {


    private static Logger log = LoggerFactory.getLogger(PaymentStatusServiceTest.class);

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    Set<PaymentStatus> tempSet = new HashSet<>(0);

    @Before
    public void setUp() {}



    @Test
    public void test1Create(){


        PaymentStatus paymentStatus = paymentStatusService.savePaymentStatus(PaymentStatusFixture.standardPaymentStatus());
        log.info(paymentStatus.toString());
        Assert.assertNotNull(paymentStatus.getPysId());

        // Add the item to the tempSet
        tempSet.add(paymentStatus);
    }

    @Test
    public void test2Update() {

        PaymentStatus paymentStatus = PaymentStatusFixture.standardPaymentStatus();
        paymentStatus = paymentStatusService.savePaymentStatus(paymentStatus);
        log.info("Original PaymentStatus " + paymentStatus.toString());

        // Add the item to the tempSet
        tempSet.add(paymentStatus);

        PaymentStatus updatedPaymentStatus = PaymentStatusFixture.updatedStandardPaymentStatus(paymentStatus);
        updatedPaymentStatus = paymentStatusService.savePaymentStatus(updatedPaymentStatus);
        log.info("Updated PaymentStatus "+ updatedPaymentStatus.toString());

    }


    @Test
    public void test3FindByPysId() {

        PaymentStatus paymentStatus = paymentStatusService.savePaymentStatus(PaymentStatusFixture.standardPaymentStatus());
        log.info(paymentStatus.toString());
        Assert.assertNotNull(paymentStatus.getPysId());
        log.info("PaymentSTatus added");

        // Add the item to the tempSet
        tempSet.add(paymentStatus);

        // Read the payment status
        PaymentStatus searchPaymentStatus = paymentStatusService.findByPysId(paymentStatus.getPysId());
        Assert.assertNotNull(searchPaymentStatus);
        log.info("Payment Status : " + searchPaymentStatus.toString());


    }

    @Test
    public void test4FindByPysMerchantNo() {

        // Get the standard paymentStatus
        PaymentStatus paymentStatus = PaymentStatusFixture.standardPaymentStatus();
        paymentStatus = paymentStatusService.savePaymentStatus(paymentStatus);

        // Add the item to the tempSet
        tempSet.add(paymentStatus);

        Page<PaymentStatus> paymentStatuss = paymentStatusService.findByPysMerchantNo(paymentStatus.getPysMerchantNo(),constructPageSpecification(1));
        log.info("paymentStatuss by merchant no " + paymentStatuss.toString());
        Set<PaymentStatus> paymentStatusSet = Sets.newHashSet((Iterable<PaymentStatus>)paymentStatuss);
        log.info("paymentStatus list "+paymentStatusSet.toString());

    }

    @Test
    public void test5SearchPaymentStatus() {

        // Create the paymentStatus
        PaymentStatus paymentStatus = PaymentStatusFixture.standardPaymentStatus();
        paymentStatusService.savePaymentStatus(paymentStatus);
        Assert.assertNotNull(paymentStatus.getPysId());
        log.info("PaymentStatus created");

        // Add the item to the tempSet
        tempSet.add(paymentStatus);

        Page<PaymentStatus> paymentStatusPage= paymentStatusService.searchPaymentStatus(paymentStatus.getPysMerchantNo(), paymentStatus.getPysDate(), paymentStatus.getPysModule(), "interalref", paymentStatus.getPysInternalRef(), constructPageSpecification(0));
        Assert.assertNotNull(paymentStatusPage);
        Assert.assertTrue(paymentStatusPage.hasContent());
        List<PaymentStatus> paymentStatusList = Lists.newArrayList((Iterable<PaymentStatus>) paymentStatusPage);
        log.info("Fetched paymentStatus info" + paymentStatusList.toString());

    }



    @Test
    public void test6DeletePaymentStatus() {

        // Create the paymentStatus
        PaymentStatus paymentStatus = PaymentStatusFixture.standardPaymentStatus();
        paymentStatusService.savePaymentStatus(paymentStatus);
        Assert.assertNotNull(paymentStatus.getPysId());
        log.info("PaymentStatus created");

        // Add the item to the tempSet
        tempSet.add(paymentStatus);
        
        // Delete the PaymentSTatus
        paymentStatusService.deletePaymentStatus(paymentStatus.getPysId());

        // Get the PaymentStatus
        PaymentStatus searchPaymentStatus = paymentStatusService.findByPysId(paymentStatus.getPysId());
        Assert.assertNull(searchPaymentStatus);
        log.info("Paymentstatus has been deleted");

    }


    @After
    public void tearDown() {

        for(PaymentStatus paymentStatus : tempSet) {
            paymentStatusRepository.delete(paymentStatus);;

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
        return new Sort(Sort.Direction.ASC, "pysId");
    }

}
