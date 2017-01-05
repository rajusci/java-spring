package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import com.inspirenetz.api.core.repository.MerchantPartnerTransactionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by abhi on 14/7/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantPartnerTransactionRepositoryTest {


    private static Logger log = LoggerFactory.getLogger(MerchantPartnerTransactionRepositoryTest.class);

    @Autowired
    MerchantPartnerTransactionRepository merchantPartnerTransactionRepository;
    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();

        merchantPartnerTransaction = merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        log.info(merchantPartnerTransaction.toString());
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());

    }

    @Test
    public void tes2Update(){

        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransaction.setMptPrice(1000.0);

        merchantPartnerTransaction = merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        log.info(merchantPartnerTransaction.toString());
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
    }

    @Test
    public void test3FindByMptId() {

        // Get the standard MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction1 = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();

        MerchantPartnerTransaction merchantPartnerTransaction = merchantPartnerTransactionRepository.findByMptId(merchantPartnerTransaction1.getMptId());
        log.info("merchantPartnerTransaction by mptId" + merchantPartnerTransaction.toString());

        Assert.assertNotNull(merchantPartnerTransaction);

    }

    @Test
    public void test4FindByMptProductNoAndMptPartnerNo(){

        // Create the MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
        log.info("MerchantPartnerTransaction created");

        Page<MerchantPartnerTransaction> merchantPartnerTransactions = merchantPartnerTransactionRepository.findByMptProductNoAndMptPartnerNo(merchantPartnerTransaction.getMptProductNo(),merchantPartnerTransaction.getMptMerchantNo(),constructPageSpecification(1));
        log.info("merchantPartnerTransactions by product no and partner no " + merchantPartnerTransactions.toString());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>) merchantPartnerTransactions);
        log.info("merchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());

    }



    @Test
    public void test5FindByMptProductNoAndMptMerchantNo(){

        // Create the MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
        log.info("MerchantPartnerTransaction created");

        Page<MerchantPartnerTransaction> merchantPartnerTransactions = merchantPartnerTransactionRepository.findByMptProductNoAndMptMerchantNo(merchantPartnerTransaction.getMptProductNo(),merchantPartnerTransaction.getMptMerchantNo(),constructPageSpecification(1));
        log.info("merchantPartnerTransactions by product no and partner no " + merchantPartnerTransactions.toString());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>) merchantPartnerTransactions);
        log.info("merchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());

    }


    @Test
    public void test6findByMptMerchantNo() {

        // Create the MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
        log.info("MerchantPartnerTransaction created");

        // Check the MerchantPartnerTransaction name
        Page<MerchantPartnerTransaction> merchantPartnerTransactions = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptTxnDateBetween(merchantPartnerTransaction.getMptMerchantNo(),DBUtils.covertToSqlDate("2016-07-14"),DBUtils.covertToSqlDate("2019-07-14"), constructPageSpecification(1));
        Assert.assertTrue(merchantPartnerTransactions.hasContent());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>)merchantPartnerTransactions);
        log.info("MerchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());


    }

    @Test
    public void test7findByMptPartnerNo() {

        // Create the MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
        log.info("MerchantPartnerTransaction created");

        // Check the MerchantPartnerTransaction name
        Page<MerchantPartnerTransaction> merchantPartnerTransactions = merchantPartnerTransactionRepository.findByMptPartnerNo(merchantPartnerTransaction.getMptPartnerNo(), constructPageSpecification(1));
        Assert.assertTrue(merchantPartnerTransactions.hasContent());
        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = Sets.newHashSet((Iterable<MerchantPartnerTransaction>)merchantPartnerTransactions);
        log.info("MerchantPartnerTransaction list "+merchantPartnerTransactionSet.toString());


    }

    @Test
    public void test8findByMptProductNoAndMerchantNoAndMptDateBetween() {

        // Create the MerchantPartnerTransaction
        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionFixture.standardMerchantPartnerTransaction();
        merchantPartnerTransaction = merchantPartnerTransactionRepository.save(merchantPartnerTransaction);
        Assert.assertNotNull(merchantPartnerTransaction.getMptId());
        log.info("MerchantPartnerTransaction created");

        // Check the MerchantPartnerTransaction name
        Page<MerchantPartnerTransaction> merchantPartnerTransactions = merchantPartnerTransactionRepository.findByMptProductNoAndMptMerchantNoAndMptTxnDateBetween(merchantPartnerTransaction.getMptProductNo(),merchantPartnerTransaction.getMptMerchantNo(), DBUtils.covertToSqlDate("2014-07-01"), DBUtils.covertToSqlDate("2016-10-14"),constructPageSpecification(0));
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

