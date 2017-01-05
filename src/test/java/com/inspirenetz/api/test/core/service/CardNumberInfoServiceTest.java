package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.CardNumberInfoRequest;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.service.CardNumberInfoService;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CardNumberInfoFixture;
import com.inspirenetz.api.test.core.fixture.CardTypeFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 21/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})
public class CardNumberInfoServiceTest {

    private static Logger log = LoggerFactory.getLogger(CardNumberInfoServiceTest.class);

    @Autowired
    private CardNumberInfoService cardNumberInfoService;

    @Autowired
    private CardTypeService cardTypeService;


    @Before
    public void setup() {}

    Set<CardNumberInfo> cardNumberInfoSet =new HashSet<>();

    Set<CardType> cardTypeSet =new HashSet<>();

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1Create(){


        CardNumberInfo cardNumberInfo = cardNumberInfoService.saveCardNumberInfo(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());
        Assert.assertNotNull(cardNumberInfo.getCniId());

        cardNumberInfoSet.add(cardNumberInfo);

    }



    @Test
    public void findByCniMerchantNoAndCniCardNumber() {

        CardNumberInfo cardNumberInfo = cardNumberInfoService.saveCardNumberInfo(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());

        Assert.assertNotNull(cardNumberInfo.getCniId());

        cardNumberInfo =cardNumberInfoService.findByCniMerchantNoAndCniCardNumber(cardNumberInfo.getCniMerchantNo(), cardNumberInfo.getCniCardNumber());

        cardNumberInfoSet.add(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfo);
    }

    @Test
    public void findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike() {


        CardNumberInfo cardNumberInfo = cardNumberInfoService.saveCardNumberInfo(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());

        Assert.assertNotNull(cardNumberInfo.getCniId());

        Page<CardNumberInfo> cardNumberInfos =cardNumberInfoService.findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike(cardNumberInfo.getCniMerchantNo(), cardNumberInfo.getCniBatchId(), cardNumberInfo.getCniCardNumber(), constructPageSpecification(0));

        cardNumberInfoSet.add(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfos);
    }

    @Test
    public void  processBatchFile(){

        CardNumberInfoRequest cardNumberInfoRequest =new CardNumberInfoRequest();

        cardNumberInfoRequest.setCniMerchantNo(1L);
        cardNumberInfoRequest.setCniCardType(1L);
        cardNumberInfoRequest.setCniBatchName("Batch5");

        //testing file pls put under upload directry
        cardNumberInfoRequest.setFilePath("/vouchersource/test1.csv");

        //process file
        try{

            cardNumberInfoService.processBatchFile(cardNumberInfoRequest);

        }catch (InspireNetzException ex){

            Assert.assertFalse(true);
        }catch (Exception ex){

            Assert.assertFalse(true);
        }



    }

    @Test
    public void searchCardNumberInfo() {


        CardNumberInfo cardNumberInfo = cardNumberInfoService.saveCardNumberInfo(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());

        Assert.assertNotNull(cardNumberInfo.getCniId());

        Page<CardNumberInfo> cardNumberInfos =cardNumberInfoService.searchCardNumberInfo(cardNumberInfo.getCniMerchantNo(), cardNumberInfo.getCniBatchId(),"0","0", constructPageSpecification(0));

        cardNumberInfoSet.add(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfos);
    }

    @Test
    public void GetValidatedCardDetails() {

        CardNumberInfo cardNumberInfo = cardNumberInfoService.saveCardNumberInfo(CardNumberInfoFixture.standardCardNumberInfo());
        log.info(cardNumberInfo.toString());

        Assert.assertNotNull(cardNumberInfo.getCniId());
        cardNumberInfoSet.add(cardNumberInfo);

        try{

            CardNumberInfo fetchedCardNumberInfo =cardNumberInfoService.getValidatedCardDetails(cardNumberInfo.getCniCardNumber(),cardNumberInfo.getCniPin());

            Assert.assertNotNull(fetchedCardNumberInfo);

        }catch (InspireNetzException e){

            e.printStackTrace();

        }



    }

    @Test
    public void GetValidatedCardDetailsForPublic() {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.save(cardType);
        Assert.assertNotNull(cardType);
        cardTypeSet.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        log.info(cardNumberInfo.toString());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfo.getCniId());
        cardNumberInfoSet.add(cardNumberInfo);

        try{

            CardNumberInfo fetchedCardNumberInfo =cardNumberInfoService.getValidatedCardDetailsForPublic(cardNumberInfo.getCniCardNumber(), "8867987369", cardNumberInfo.getCniPin(), 0L);

            Assert.assertNotNull(fetchedCardNumberInfo);

        }catch (InspireNetzException e){

            e.printStackTrace();

        }



    }

    @Test
    public void getAvailableCardNumber(){


        CardNumberInfo cardNumberInfo=cardNumberInfoService.getAvailableCardNumber(1L, 86L);

        Assert.assertNotNull(cardNumberInfo);

        log.info("getAvailableCardNumber:Response - "+cardNumberInfo.toString());


    }



    @After
    public void tearDown() throws InspireNetzException{

        for (CardNumberInfo cardNumberInfo:cardNumberInfoSet){

            cardNumberInfoService.delete(cardNumberInfo);
        }

        for (CardType cardType:cardTypeSet){


            cardTypeService.deleteCardType(cardType.getCrtId(),cardType.getCrtMerchantNo());
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
