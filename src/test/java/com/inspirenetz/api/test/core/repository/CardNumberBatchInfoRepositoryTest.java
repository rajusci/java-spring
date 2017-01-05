package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.core.repository.CardNumberBatchInfoRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CardNumberBatchInfoFixture;
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
import java.util.Set;

/**
 * Created by ameen on 21/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CardNumberBatchInfoRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CardNumberBatchInfoRepositoryTest.class);

    @Autowired
    private CardNumberBatchInfoRepository cardNumberBatchInfoRepository;


    @Before
    public void setup() {}

    Set<CardNumberBatchInfo> cardNumberBatchInfoSet =new HashSet<>();

    @Test
    public void test1Create(){


        CardNumberBatchInfo cardNumberBatchInfo = cardNumberBatchInfoRepository.save(CardNumberBatchInfoFixture.standardCardNumberBatchInfo());
        log.info(cardNumberBatchInfo.toString());
        Assert.assertNotNull(cardNumberBatchInfo.getCnbId());

        cardNumberBatchInfoSet.add(cardNumberBatchInfo);

    }



    @Test
    public void findByCnbMerchantNoAndCnbName() {

        CardNumberBatchInfo cardNumberBatchInfo = cardNumberBatchInfoRepository.save(CardNumberBatchInfoFixture.standardCardNumberBatchInfo());
        log.info(cardNumberBatchInfo.toString());

        Assert.assertNotNull(cardNumberBatchInfo.getCnbId());

        cardNumberBatchInfo =cardNumberBatchInfoRepository.findByCnbMerchantNoAndCnbName(cardNumberBatchInfo.getCnbMerchantNo(),cardNumberBatchInfo.getCnbName());

        cardNumberBatchInfoSet.add(cardNumberBatchInfo);

        Assert.assertNotNull(cardNumberBatchInfo);
    }

    @Test
    public void findByCnbMerchantNoAndCnbDateBetween() {


        CardNumberBatchInfo cardNumberBatchInfo = cardNumberBatchInfoRepository.save(CardNumberBatchInfoFixture.standardCardNumberBatchInfo());
        log.info(cardNumberBatchInfo.toString());

        Assert.assertNotNull(cardNumberBatchInfo.getCnbId());

        Page<CardNumberBatchInfo> cardNumberBatchInfos =cardNumberBatchInfoRepository.findByCnbMerchantNoAndCnbDateBetween(cardNumberBatchInfo.getCnbMerchantNo(),cardNumberBatchInfo.getCnbDate(),cardNumberBatchInfo.getCnbDate(),constructPageSpecification(0));

        cardNumberBatchInfoSet.add(cardNumberBatchInfo);

        Assert.assertNotNull(cardNumberBatchInfos);
    }

    @Test
    public void findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike() {


        CardNumberBatchInfo cardNumberBatchInfo = cardNumberBatchInfoRepository.save(CardNumberBatchInfoFixture.standardCardNumberBatchInfo());
        log.info(cardNumberBatchInfo.toString());

        Assert.assertNotNull(cardNumberBatchInfo.getCnbId());

        Page<CardNumberBatchInfo> cardNumberBatchInfos =cardNumberBatchInfoRepository.findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike(cardNumberBatchInfo.getCnbMerchantNo(),cardNumberBatchInfo.getCnbDate(),cardNumberBatchInfo.getCnbDate(),cardNumberBatchInfo.getCnbName(),constructPageSpecification(0));

        cardNumberBatchInfoSet.add(cardNumberBatchInfo);

        Assert.assertNotNull(cardNumberBatchInfos);
    }




    @After
    public void tearDown() {

        for (CardNumberBatchInfo cardNumberBatchInfo:cardNumberBatchInfoSet){

            cardNumberBatchInfoRepository.delete(cardNumberBatchInfo);
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
