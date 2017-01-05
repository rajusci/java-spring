package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.core.repository.CardNumberInfoRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CardNumberInfoFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameen on 20/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CardNumberInfoRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CardNumberInfoRepositoryTest.class);

    @Autowired
    private CardNumberInfoRepository cardNumberInfoRepository;


    @Before
    public void setup() {}

    Set<CardNumberInfo> cardNumberInfoSet =new HashSet<>();

    @Test
    public void test1Create(){


        CardNumberInfo cardNumberInfo = cardNumberInfoRepository.save(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());
        Assert.assertNotNull(cardNumberInfo.getCniId());

        cardNumberInfoSet.add(cardNumberInfo);

    }



    @Test
    public void findByCniCardNumber() {

        CardNumberInfo cardNumberInfo = cardNumberInfoRepository.save(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());

        Assert.assertNotNull(cardNumberInfo.getCniId());

        cardNumberInfo =cardNumberInfoRepository.findByCniCardNumber(cardNumberInfo.getCniCardNumber());

        cardNumberInfoSet.add(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfo);
    }

    @Test
    public void findByCniMerchantNoAndCniCardNumberLike() {


        CardNumberInfo cardNumberInfo = cardNumberInfoRepository.save(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());

        Assert.assertNotNull(cardNumberInfo.getCniId());

        Page<CardNumberInfo> cardNumberInfos =cardNumberInfoRepository.findByCniMerchantNoAndCniCardNumberLike(cardNumberInfo.getCniMerchantNo(),cardNumberInfo.getCniCardNumber(),constructPageSpecification(0));

        cardNumberInfoSet.add(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfos);


    }

    @Test
    public void findByCniMerchantNoAndCniCardTypeAndCniCardStatus() {



        Page<CardNumberInfo> cardNumberInfos =cardNumberInfoRepository.findByCniMerchantNoAndCniCardTypeAndCniCardStatus(1L, 86L, IndicatorStatus.NO,constructPageSpecification(0));


        Assert.assertNotNull(cardNumberInfos);

        log.info("Card Number Info Resource "+cardNumberInfos.toString());
    }




    @After
    public void tearDown() {

       for (CardNumberInfo cardNumberInfo:cardNumberInfoSet){

           cardNumberInfoRepository.delete(cardNumberInfo);
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
