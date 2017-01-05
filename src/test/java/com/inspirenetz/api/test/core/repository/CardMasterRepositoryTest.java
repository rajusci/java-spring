package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.repository.CardMasterRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CardMasterFixture;
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
public class CardMasterRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CardMasterRepositoryTest.class);

    @Autowired
    private CardMasterRepository cardMasterRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        CardMaster cardMaster = cardMasterRepository.save(CardMasterFixture.standardCardMaster());
        log.info(cardMaster.toString());
        Assert.assertNotNull(cardMaster.getCrmId());

    }

    @Test
    public void test2Update() {

        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterRepository.save(cardMaster);
        log.info("Original CardMaster " + cardMaster.toString());

        CardMaster updatedCardMaster = CardMasterFixture.updatedStandardCardMaster(cardMaster);
        updatedCardMaster = cardMasterRepository.save(updatedCardMaster);
        log.info("Updated CardMaster "+ updatedCardMaster.toString());

    }



    @Test
    public void test3FindByCrmMerchantNo() {

        // Get the standard cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();

        Page<CardMaster> cardMasters = cardMasterRepository.findByCrmMerchantNo(cardMaster.getCrmMerchantNo(),constructPageSpecification(1));
        log.info("cardMasters by merchant no " + cardMasters.toString());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());

    }

    @Test
    public void test4FindByCrmMerchantNoAndCrmCardNo() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterRepository.save(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        CardMaster fetchCardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(), cardMaster.getCrmCardNo());
        Assert.assertNotNull(fetchCardMaster);
        log.info("Fetched cardMaster info" + cardMaster.toString());

    }


    @Test
    public void test5FindByCrmMerchantNoAndCrmCardNoLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterRepository.save(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterRepository.findByCrmMerchantNoAndCrmCardNoLike(cardMaster.getCrmMerchantNo(), "%10%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());


    }


    @Test
    public void test6FindByCrmMerchantNoAndCrmMobileLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterRepository.save(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterRepository.findByCrmMerchantNoAndCrmMobileLike(cardMaster.getCrmMerchantNo(), "%9999%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());


    }


    @Test
    public void test7FindByCrmMerchantNoAndCrmLoyaltyIdLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterRepository.save(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterRepository.findByCrmMerchantNoAndCrmLoyaltyIdLike(cardMaster.getCrmMerchantNo(), "%10%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());


    }



    @Test
    public void test8FindByCrmMerchantNoAndCrmCardHolderNameLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterRepository.save(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterRepository.findByCrmMerchantNoAndCrmCardHolderNameLike(cardMaster.getCrmMerchantNo(), "%test%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());


    }



    @Test
    public void test9ListCardsForCustomer() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterRepository.save(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        List<CardMaster> cardMasterList = cardMasterRepository.listCardsForCustomer(cardMaster.getCrmMerchantNo(),cardMaster.getCrmMobile(),cardMaster.getCrmEmailId(),cardMaster.getCrmLoyaltyId());
        Assert.assertTrue(!cardMasterList.isEmpty());
        log.info("cardMaster list "+cardMasterList.toString());


    }



    @Test
    public void test10FindByCrmId() {

        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterRepository.save(cardMaster);
        log.info("Original CardMaster " + cardMaster.toString());

        CardMaster fetchedCardMaster = cardMasterRepository.findByCrmId(cardMaster.getCrmId());
        log.info("Fetched CardMaster "+ fetchedCardMaster.toString());

    }


    @After
    public void tearDown() {

        Set<CardMaster> cardMasters = CardMasterFixture.standardCardMasters();

        for(CardMaster cardMaster: cardMasters) {

            CardMaster delCardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(),cardMaster.getCrmCardNo());

            if ( delCardMaster != null ) {
                cardMasterRepository.delete(delCardMaster);
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
        return new Sort(Sort.Direction.ASC, "crmId");
    }


}
