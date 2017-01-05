package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import com.inspirenetz.api.core.repository.MerchantPartnerTransactionRepository;
import com.inspirenetz.api.core.service.MerchantPartnerTransactionService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantPartnerTransactionFixture;
import com.inspirenetz.api.util.DBUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
 * Created by abhi  on 14/7/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantPartnerTransactionServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantPartnerTransactionServiceTest.class);

    @Autowired
    private MerchantPartnerTransactionService merchantPartnerTransactionService;



    @Before
    public void setUp() {}



    @Test
    public void test1FindByMptId() {

        // Get the standard merchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();

        MerchantPartnerTransaction merchantPartnerTransactions = merchantPartnerTransactionService.findByMptId(merchantPartnerTransaction.getMptId());
        log.info("merchantPartnerTransactions by mpt id" + merchantPartnerTransactions.toString());

    }



    @Test
    public void test2FindByMptProductNoAndMptPartnerNo() {

        // Create the merchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransaction = merchantPartnerTransactionService.saveMerchantPartnerTransaction(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction);
        log.info("MerchantPartnerTransaction created");

        Page<MerchantPartnerTransaction> merchantPartnerTransactions  = merchantPartnerTransactionService.findByMptProductNoAndMptPartnerNo(merchantPartnerTransaction.getMptProductNo(), merchantPartnerTransaction.getMptPartnerNo(), constructPageSpecification(0));
        log.info("merchantPartnerTransactions by merchant no " + merchantPartnerTransactions.toString());
        Assert.assertTrue(merchantPartnerTransactions.hasContent());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>) merchantPartnerTransactions);
        log.info("merchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());


    }



    @Test
    public void test2FindByMptProductNoAndMptMerchantNo() {

        // Create the merchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransaction = merchantPartnerTransactionService.saveMerchantPartnerTransaction(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction);
        log.info("MerchantPartnerTransaction created");

        Page<MerchantPartnerTransaction> merchantPartnerTransactions  = merchantPartnerTransactionService.findByMptProductNoAndMptMerchantNo(merchantPartnerTransaction.getMptProductNo(), merchantPartnerTransaction.getMptMerchantNo(), constructPageSpecification(0));
        log.info("merchantPartnerTransactions by merchant no " + merchantPartnerTransactions.toString());
        Assert.assertTrue(merchantPartnerTransactions.hasContent());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>) merchantPartnerTransactions);
        log.info("merchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());


    }


    @Test
    public void test3findByMptProductNoAndMerchantNoAndMptDateBetween() {

        // Create the MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransaction = merchantPartnerTransactionService.saveMerchantPartnerTransaction(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
        log.info("MerchantPartnerTransaction created");

        // Check the MerchantPartnerTransaction name
        Page<MerchantPartnerTransaction> merchantPartnerTransactions = merchantPartnerTransactionService.findByMptProductNoAndMptMerchantNoAndMptTxnDateBetween(merchantPartnerTransaction.getMptProductNo(),merchantPartnerTransaction.getMptMerchantNo(), DBUtils.covertToSqlDate("2014-07-01"), DBUtils.covertToSqlDate("2016-10-14"),constructPageSpecification(0));
        Assert.assertTrue(merchantPartnerTransactions.hasContent());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>)merchantPartnerTransactions);
        log.info("MerchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());

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
        return new Sort(Sort.Direction.ASC, "mptId");
    }

}
