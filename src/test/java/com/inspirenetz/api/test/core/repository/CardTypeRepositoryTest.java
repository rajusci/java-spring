package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.repository.CardTypeRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CardTypeFixture;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CardTypeRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CardTypeRepositoryTest.class);

    @Autowired
    private CardTypeRepository cardTypeRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CardType cardType = cardTypeRepository.save(CardTypeFixture.standardCardType());
        log.info(cardType.toString());
        Assert.assertNotNull(cardType.getCrtId());

    }

    @Test
    public void test2Update() {

        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeRepository.save(cardType);
        log.info("Original CardType " + cardType.toString());

        CardType updatedCardType = CardTypeFixture.standardUpdatedCardType(cardType);
        updatedCardType = cardTypeRepository.save(updatedCardType);
        log.info("Updated CardType "+ updatedCardType.toString());

    }



    @Test
    public void test3FindByCrtMerchantNo() {

        // Get the standard cardType
        CardType cardType = CardTypeFixture.standardCardType();

        Page<CardType> cardTypes = cardTypeRepository.findByCrtMerchantNo(cardType.getCrtMerchantNo(),constructPageSpecification(1));
        log.info("cardTypes by merchant no " + cardTypes.toString());
        Set<CardType> cardTypeSet = Sets.newHashSet((Iterable<CardType>)cardTypes);
        log.info("cardType list "+cardTypeSet.toString());

    }

    @Test
    public void test4FindByCrtMerchantNoAndCrtName() {

        // Create the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardTypeRepository.save(cardType);
        Assert.assertNotNull(cardType.getCrtId());
        log.info("CardType created");

        CardType fetchCardType = cardTypeRepository.findByCrtMerchantNoAndCrtName(cardType.getCrtMerchantNo(),cardType.getCrtName());
        Assert.assertNotNull(fetchCardType);
        log.info("Fetched cardType info" + cardType.toString());

    }


    @Test
    public void test5FindByCrtMerchantNoAndCrtNameLike() {

        // Create the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardTypeRepository.save(cardType);
        Assert.assertNotNull(cardType.getCrtId());
        log.info("CardType created");

        // Check the cardType name
        Page<CardType> cardTypes = cardTypeRepository.findByCrtMerchantNoAndCrtNameLike(cardType.getCrtMerchantNo(),"%test%",constructPageSpecification(0));
        Assert.assertTrue(cardTypes.hasContent());
        Set<CardType> cardTypeSet = Sets.newHashSet((Iterable<CardType>)cardTypes);
        log.info("cardType list "+cardTypeSet.toString());


    }

    @After
    public void tearDown() {

        Set<CardType> cardTypes = CardTypeFixture.standardCardTypes();

        for(CardType cardType: cardTypes) {

            CardType delCardType = cardTypeRepository.findByCrtMerchantNoAndCrtName(cardType.getCrtMerchantNo(),cardType.getCrtName());

            if ( delCardType != null ) {
                cardTypeRepository.delete(delCardType);
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
        return new Sort(Sort.Direction.ASC, "crtName");
    }


}
