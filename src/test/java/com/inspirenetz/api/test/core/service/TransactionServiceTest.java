package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.CatalogueService;
import com.inspirenetz.api.core.service.TransactionService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CatalogueFixture;
import com.inspirenetz.api.test.core.fixture.TransactionFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class,RabbitMQTestConfig.class})
public class TransactionServiceTest {


    private static Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    UsernamePasswordAuthenticationToken principal;

    Set<Sale> tempSet = new HashSet<>(0);

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void testSaveTransaction() {

        // Get the transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction.setTxnDate(new Date(new java.util.Date().getTime()));
        transaction.setTxnRewardCurrencyId(6325l);
        transaction = transactionService.saveTransaction(transaction);
        Assert.assertNotNull(transaction);
        log.info("Transaction" + transaction.toString());

        // Delete the transaction
        transactionService.deleteTransaction(transaction.getTxnId());


    }



    @Test
    public void postToAnalyze() {

        List<Transaction> transactions = transactionService.findAll();
        for( Transaction transaction: transactions) {

            transactionService.saveTransaction(transaction);

        }

    }


    @Test
    public void test2FindByTxnMerchantNoAndTxnDateBetweenOrderByTxnIdDesc() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();

        // Get the startDate


        // Get the transaction page
        Page<Transaction> transactionPage = transactionService.findByTxnMerchantNoAndTxnDateBetweenOrderByTxnIdDesc(transaction.getTxnMerchantNo(), DBUtils.covertToSqlDate("2014-01-01"),DBUtils.covertToSqlDate("2014-07-01"),constructPageSpecification(0));
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
        Page<Transaction> transactionPage = transactionService.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc(transaction.getTxnMerchantNo(),transaction.getTxnLoyaltyId(), DBUtils.covertToSqlDate("2014-01-01"),DBUtils.covertToSqlDate("2014-07-01"),constructPageSpecification(0));
        Assert.assertNotNull(transactionPage);
        Assert.assertTrue(transactionPage.hasContent());
        List<Transaction> transactionList = Lists.newArrayList((Iterable<Transaction>)transactionPage);
        log.info("Transaction list :" +transactionList.toString());


    }




    @Test
    public void test4findByTxnMerchantNoAndTxnId() {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionService.saveTransaction(transaction);

        // Get the information
        Transaction searchTransaction = transactionService.findByTxnMerchantNoAndTxnId(transaction.getTxnMerchantNo(),transaction.getTxnId());
        Assert.assertNotNull(searchTransaction);
        log.info("Transaction Info "+searchTransaction.toString());

    }

    @Test
    public void test4findByCustomerTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc() throws InspireNetzException {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionService.saveTransaction(transaction);

        // Get the information
        Page<Transaction> searchTransaction = transactionService.searchCustomerTransaction(transaction.getTxnMerchantNo(), transaction.getTxnDate(), transaction.getTxnRewardExpDt(), constructPageSpecification(0));
        Assert.assertNotNull(searchTransaction);
        log.info("Transaction Info "+searchTransaction.toString());

    }


    @Test
    public void test5SendTransactionSMS() throws InspireNetzException {

        transactionService.sendTransactionSMS("9538828853");;

    }

    @Test
    public void test6findTop1ByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc() throws InspireNetzException {

        // Get the default transaction
        Transaction transaction = TransactionFixture.standardTransaction();
        transaction = transactionService.saveTransaction(transaction);

        Transaction fetchedTransaction=transactionService.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(transaction.getTxnMerchantNo(),transaction.getTxnLoyaltyId(),transaction.getTxnInternalRef(),transaction.getTxnLocation(),transaction.getTxnDate());

        Assert.assertNotNull(fetchedTransaction);

        log.info("Transaction Info "+fetchedTransaction.toString());

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

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     *
     * @return
     */


    /**
     * Modified by:Al Ameen
     * Date 24/10/2014
     * purpose: using orderd by is using  test4findByCustomerTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc  function with txnId desc
     *         :so at that again we call below method for Asc at time happening pass tow argument in order by condition(so commented below method)
     * @throws Exception
     */


//    private Sort sortByLastNameAsc() {
//        return new Sort(Sort.Direction.ASC, "txnId");
//    }


    @After
    public void tearDown() throws Exception {


    }

}
