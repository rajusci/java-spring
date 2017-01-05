package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.repository.CardTransactionRepository;
import com.inspirenetz.api.core.service.CardTransactionService;
import com.inspirenetz.api.test.config.*;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class, RabbitMQTestConfig.class})
public class CardTransactionServiceTest {


    private static Logger log = LoggerFactory.getLogger(CardTransactionServiceTest.class);

    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private CardTransactionRepository cardTransactionRepository;


    @Before
    public void setUp() {}



    @Test
    public void test1FindByCtxTxnTerminal() {

        // Get the standard cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();

        Page<CardTransaction> cardTransactions = cardTransactionService.findByCtxTxnTerminal(cardTransaction.getCtxTxnTerminal(), constructPageSpecification(1));
        log.info("cardTransactions by merchant no " + cardTransactions.toString());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>) cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());

    }


    @Test
    public void postToAnalyze() {

        // Get the list of transcations
        List<CardTransaction> cardTransactionList = cardTransactionService.findAll();

        // Save them
        for(CardTransaction cardTransaction : cardTransactionList) {

            cardTransactionService.saveCardTransaction(cardTransaction);

        }

    }


    @Test
    public void test2FindByCtxTxnTerminalAndCtxCrmId() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionService.saveCardTransaction(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        Page<CardTransaction> cardTransactions  = cardTransactionService.findByCtxTxnTerminalAndCtxCrmId(cardTransaction.getCtxTxnTerminal(), cardTransaction.getCtxCrmId(), constructPageSpecification(0));
        log.info("cardTransactions by merchant no " + cardTransactions.toString());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>) cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());

    }



    @Test
    public void test3FindByCtxTxnTerminalAndCtxCardNumber() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionService.saveCardTransaction(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // Check the cardTransaction name
        Page<CardTransaction> cardTransactions = cardTransactionService.findByCtxTxnTerminalAndCtxCardNumber(cardTransaction.getCtxTxnTerminal(), cardTransaction.getCtxCardNumber(), constructPageSpecification(0));
        Assert.assertTrue(cardTransactions.hasContent());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>)cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());


    }


    @Test
    public void test4FindByCtxTxnNo() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionService.saveCardTransaction(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // Check the cardTransaction name
        CardTransaction searchTransaction = cardTransactionService.findByCtxTxnNo(cardTransaction.getCtxTxnNo());
        Assert.assertNotNull(searchTransaction);
        log.info("Card Transaction Response : " +cardTransaction.toString());

    }


    @Test
    public void test5SearchTransactions() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // Check the cardTransaction name
        Page<CardTransaction> cardTransactions = cardTransactionService.searchCardTransactions(cardTransaction.getCtxTxnTerminal(), cardTransaction.getCtxCardNumber(), cardTransaction.getCtxTxnType(), DBUtils.convertToSqlTimestamp("2014-07-01 00:00:00"), DBUtils.convertToSqlTimestamp("2014-10-10 00:00:00"), constructPageSpecification(0));
        Assert.assertTrue(cardTransactions.hasContent());
        Set<CardTransaction> cardTransactionSet = Sets.newHashSet((Iterable<CardTransaction>)cardTransactions);
        log.info("cardTransaction list "+cardTransactionSet.toString());


    }


    @Test
    public void test5DeleteCardTransaction() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // call the delete cardTransaction
        cardTransactionService.deleteCardTransaction(cardTransaction.getCtxTxnNo());

        // Try searching for the cardTransaction
        CardTransaction checkCardTransaction  = cardTransactionService.findByCtxTxnNo(cardTransaction.getCtxTxnNo());

        Assert.assertNull(checkCardTransaction);

        log.info("cardTransaction deleted");

    }



    @Test
    public void test6IsDuplicateCardTransactionExisting() {

        // Create the cardTransaction
        CardTransaction cardTransaction = CardTransactionFixture.standardCardTransaction();
        cardTransactionService.saveCardTransaction(cardTransaction);
        Assert.assertNotNull(cardTransaction.getCtxTxnNo());
        log.info("CardTransaction created");

        // Get the transaction list
        boolean isExist = cardTransactionService.isDuplicateCardTransactionExisting(cardTransaction);
        Assert.assertTrue(isExist);
        log.info("Duplidate transactions : "+ cardTransaction.toString());

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
