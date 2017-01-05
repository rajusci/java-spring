package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.repository.CardTransactionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CardTransactionFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CardTransactionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CardTransactionRepositoryTest.class);

    @Autowired
    private CardTransactionRepository cardTransactionRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CardTransaction cardTransaction = cardTransactionRepository.save(CardTransactionFixture.standardCardTransaction());
        log.info(cardTransaction.toString());
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());

    }

    @Test
    public void test2Update() {

        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransaction = cardTransactionRepository.save(cardTransaction);
        log.info("Original CardTransaction " + cardTransaction.toString());

        CardTransaction updatedCardTransaction = CardTransactionFixture.updatedStandardCardTransaction(cardTransaction);
        updatedCardTransaction = cardTransactionRepository.save(updatedCardTransaction);
        log.info("Updated CardTransaction "+ updatedCardTransaction.toString());

    }



    @Test
    public void test3FindByCtxTxnTerminak() {

        // Get the standard cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();

        Page<CardTransaction> cardTransactions = cardTransactionRepository.findByCtxTxnTerminalOrderByCtxTxnNoDesc(cardTransaction.getCtxTxnTerminal(), constructPageSpecification(1));
        log.info("cardTransactions by merchant no " + cardTransactions.toString());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>)cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());

    }

    @Test
    public void test4FindByCtxTxnTerminalAndCtxCrmId() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionRepository.save(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        Page<CardTransaction> cardTransactions = cardTransactionRepository.findByCtxTxnTerminalAndCtxCrmIdOrderByCtxTxnNoDesc(cardTransaction.getCtxTxnTerminal(), cardTransaction.getCtxCrmId(), constructPageSpecification(0));
        log.info("cardTransactions by crm id " + cardTransactions.toString());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>)cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());

    }



    @Test
    public void test5FindByCtxTxnTerminalAndCtxCardNumber() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionRepository.save(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        Page<CardTransaction> cardTransactions = cardTransactionRepository.findByCtxTxnTerminalAndCtxCardNumberOrderByCtxTxnNoDesc(cardTransaction.getCtxTxnTerminal(), cardTransaction.getCtxCardNumber(), constructPageSpecification(0));
        log.info("cardTransactions by card number " + cardTransactions.toString());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>)cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());

    }


    @Test
    public void test6SearchCardTransactions() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionRepository.save(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // Check the cardTransaction name
        Page<CardTransaction> cardTransactions = cardTransactionRepository.searchCardTransactions(cardTransaction.getCtxTxnTerminal(), cardTransaction.getCtxCardNumber(), cardTransaction.getCtxTxnType(), DBUtils.convertToSqlTimestamp("2014-07-01 00:00:00"), DBUtils.convertToSqlTimestamp("2016-10-13 00:00:00"), constructPageSpecification(0));
        Assert.assertTrue(cardTransactions.hasContent());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>)cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());


    }


    @Test
    public void test7SearchDuplicateTransactions() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionRepository.save(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // Get the transaction list
        List<CardTransaction> cardTransactionList = cardTransactionRepository.searchDuplicateTransactions(cardTransaction.getCtxTxnTerminal(),cardTransaction.getCtxCrmId(),cardTransaction.getCtxCardNumber(),cardTransaction.getCtxTxnType(),cardTransaction.getCtxTxnAmount(),cardTransaction.getCtxReference(),cardTransaction.getCtxLocation());
        Assert.assertNotNull(cardTransactionList);
        log.info("Duplidate transactions : "+cardTransactionList.toString());

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
        return new Sort(Sort.Direction.ASC, "ctxTxnNo");
    }


}
