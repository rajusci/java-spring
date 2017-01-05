package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.RecordStatus;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.repository.TransactionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.TransactionFixture;
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

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class TransactionRespositoryTest {

    private static Logger log = LoggerFactory.getLogger(TransactionRespositoryTest.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Save(){
        Transaction transaction = transactionRepository.save(TransactionFixture.standardTransaction());
        log.info(transaction.toString());
        //Assert.assertNotNull(transaction.getUid());
    }


    @Test
    public void test2FindByTxnMerchantNoAndTxnDateBetweenOrderByTxnIdDesc() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();

        // Get the startDate


        // Get the transaction page
        Page<Transaction> transactionPage = transactionRepository.findByTxnMerchantNoAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(transaction.getTxnMerchantNo(), RecordStatus.RECORD_STATUS_ACTIVE,DBUtils.covertToSqlDate("2014-01-01"), DBUtils.covertToSqlDate("2014-07-01"), constructPageSpecification(0));
        Assert.assertNotNull(transactionPage);
        Assert.assertTrue(transactionPage.hasContent());
        List<Transaction> transactionList = Lists.newArrayList((Iterable<Transaction>)transactionPage);
        log.info("Transaction list :" +transactionList.toString());


    }



    @Test
    public void test3FindByTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();

        // Get the startDate


        // Get the transaction page
        Page<Transaction> transactionPage = transactionRepository.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(transaction.getTxnMerchantNo(),transaction.getTxnLoyaltyId(), RecordStatus.RECORD_STATUS_ACTIVE, DBUtils.covertToSqlDate("2014-01-01"),DBUtils.covertToSqlDate("2014-07-01"),constructPageSpecification(0));
        Assert.assertNotNull(transactionPage);
        Assert.assertTrue(transactionPage.hasContent());
        List<Transaction> transactionList = Lists.newArrayList((Iterable<Transaction>)transactionPage);
        log.info("Transaction list :" +transactionList.toString());


    }



    @Test
    public void test4findByTxnMerchantNoAndTxnId() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionRepository.save(transaction);

        // Get the information
        Transaction searchTransaction = transactionRepository.findByTxnMerchantNoAndTxnId(transaction.getTxnMerchantNo(),transaction.getTxnId());
        Assert.assertNotNull(searchTransaction);
        log.info("Transaction Info "+searchTransaction.toString());

    }




    @Test
    public void test5SearchTransactionByTypeAndDateRange() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionRepository.save(transaction);


        // Get the list
        List<Transaction> transactionList =  transactionRepository.searchTransactionByTypeAndDateRange(transaction.getTxnMerchantNo(),transaction.getTxnLoyaltyId(),transaction.getTxnType(),transaction.getTxnDate(),transaction.getTxnDate(),RecordStatus.RECORD_STATUS_ACTIVE);
        Assert.assertNotNull(transactionList);
        log.info("Transaction List : " + transactionList.toString());


    }

    @Test
    public void test6findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionRepository.save(transaction);


        // Get the transaction
        List<Transaction> fetchedTransaction =  transactionRepository.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(transaction.getTxnMerchantNo(),transaction.getTxnLoyaltyId(),transaction.getTxnInternalRef(),transaction.getTxnLocation(),transaction.getTxnDate());

        Assert.assertNotNull(fetchedTransaction);

        log.info("Transaction  : " + fetchedTransaction.toString());


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
        return new Sort(Sort.Direction.ASC, "txnId");
    }
}
