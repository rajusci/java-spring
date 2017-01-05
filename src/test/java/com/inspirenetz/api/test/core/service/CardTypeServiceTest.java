package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.dictionary.CardTypeExpiryOption;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.repository.CardTypeRepository;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CardTypeFixture;
import net.sf.cglib.core.Local;
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
import se.sawano.java.text.AlphanumericComparator;

import java.util.*;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CardTypeServiceTest {


    private static Logger log = LoggerFactory.getLogger(CardTypeServiceTest.class);

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private CardTypeRepository cardTypeRepository;


    @Before
    public void setUp() {}

    Set<CardType> cardTypeHashSet =new HashSet<>(0);

    @Test
    public void test1FindByMerchantNo() {

        // Get the standard cardType
        CardType cardType = CardTypeFixture.standardCardType();

        Page<CardType> cardTypes = cardTypeService.findByCrtMerchantNo(cardType.getCrtMerchantNo(),constructPageSpecification(1));
        log.info("cardTypes by merchant no " + cardTypes.toString());
        Set<CardType> cardTypeSet = Sets.newHashSet((Iterable<CardType>) cardTypes);
        log.info("cardType list "+cardTypeSet.toString());

        cardTypeHashSet.add(cardType);
    }




    @Test
    public void test3FindByCrtMerchantNoAndCrtNameLike() throws InspireNetzException {

        // Create the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardTypeService.saveCardType(cardType);
        Assert.assertNotNull(cardType.getCrtId());
        log.info("CardType created");

        // Check the cardType name
        Page<CardType> cardTypes = cardTypeService.findByCrtMerchantNoAndCrtNameLike(cardType.getCrtMerchantNo(),"%test%",constructPageSpecification(0));
        Assert.assertTrue(cardTypes.hasContent());
        Set<CardType> cardTypeSet = Sets.newHashSet((Iterable<CardType>)cardTypes);
        log.info("cardType list "+cardTypeSet.toString());
        cardTypeHashSet.add(cardType);

    }


    @Test
    public void test4IsCardTypeCodeExisting() throws InspireNetzException {

        // Create the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.saveCardType(cardType);
        Assert.assertNotNull(cardType.getCrtId());
        log.info("CardType created");

        // Create a new cardType
        CardType newCardType = CardTypeFixture.standardCardType();
        boolean exists = cardTypeService.isDuplicateCardTypeExisting(newCardType);
        Assert.assertTrue(exists);
        log.info("CardType exists");
        cardTypeHashSet.add(cardType);

    }


    @Test
    public void test5DeleteCardType() throws InspireNetzException {

        // Create the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.saveCardType(cardType);
        Assert.assertNotNull(cardType.getCrtId());
        log.info("CardType created");

        // call the delete cardType
        cardTypeService.deleteCardType(cardType.getCrtId(),cardType.getCrtMerchantNo());

        // Try searching for the cardType
        CardType checkCardType  = cardTypeService.findByCrtMerchantNoAndCrtName(cardType.getCrtMerchantNo(), cardType.getCrtName());
        Assert.assertNull(checkCardType);
        log.info("cardType deleted");

        cardTypeHashSet.add(cardType);
    }



    @Test
    public void test6GetExpiryDateForCardType() {

        // Get the CardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtExpiryOption(CardTypeExpiryOption.DAYS_AFTER_ISSUANCE);
        cardType.setCrtExpiryDays(20);

        // Get the date
        java.util.Date expiryDate = cardTypeService.getExpiryDateForCardType(cardType);

        // Check if its null
        Assert.assertNotNull(expiryDate);;


        log.info("Expiry date is : " + expiryDate);

        cardTypeHashSet.add(cardType);

    }



    @Test
    public void test7IsCardNumberValid() {

        // Get the CardType
        CardType cardType = CardTypeFixture.standardCardType();

        boolean isValid = cardTypeService.isCardNumberValid("2000",cardType);

        Assert.assertTrue(isValid);

        cardTypeHashSet.add(cardType);

    }


    @Test
    public void test8IsCardValueValid() {

        // Get the CardType
        CardType  cardType = CardTypeFixture.standardCardType();

        // Check if its valid
        boolean isValid = cardTypeService.isCardValueValid(cardType,10.0,0.0, CardTransactionType.TOPUP);

        // Assert
        Assert.assertTrue(isValid);

        // Log the informatio
        log.info("Card value is valid");

        cardTypeHashSet.add(cardType);

    }


    @Test
    public void test9CheckDuplicateRange() throws InspireNetzException {

        // Get the CardType
        CardType  cardType1 = CardTypeFixture.standardCardType();

        cardType1.setCrtCardNoRangeFrom("11231");
        cardType1.setCrtCardNoRangeTo("11321");

        cardTypeService.save(cardType1);

        CardType cardType =CardTypeFixture.standardCardType();

        cardType.setCrtName("test2");

        cardType.setCrtCardNoRangeFrom("1234");
        cardType.setCrtCardNoRangeTo("1235");

        cardTypeService.checkCardNumberRangeValid(cardType);

        log.info("test9CheckDuplicateRange:"+cardType.toString());



        // Check if its valid


        // Log the informatio
        log.info("Card value is valid");

        cardTypeHashSet.add(cardType1);

    }


    @Test
    public void example() {
         List<String> stringsToSort = new ArrayList<>();

        stringsToSort.add("11231");
        stringsToSort.add("123A");

        stringsToSort.sort(new AlphanumericComparator(Locale.ENGLISH));

        log.info("Alphanumeric Sort:"+stringsToSort);
    }



    @After
    public void tearDown() throws InspireNetzException {

        for(CardType cardType:cardTypeHashSet){

            cardTypeService.deleteCardType(cardType.getCrtId(),cardType.getCrtMerchantNo());
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
        return new Sort(Sort.Direction.ASC, "crtName");
    }

}
