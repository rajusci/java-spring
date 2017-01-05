package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.CardMasterRepository;
import com.inspirenetz.api.core.repository.CardTransactionRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardMasterResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import org.dozer.Mapper;
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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})

public class CardMasterServiceTest {


    private static Logger log = LoggerFactory.getLogger(CardMasterServiceTest.class);

    @Autowired
    private CardMasterService cardMasterService;

    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private TierService tierService;

    @Autowired
    private CardNumberInfoService cardNumberInfoService;

    @Autowired
    private OneTimePasswordService oneTimePasswordService;


    UsernamePasswordAuthenticationToken principal;

    String otp;
    Long merchantNo;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;



    @Autowired
    Mapper mapper;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

     Set<CardMaster> tempCardMaster =new HashSet<>();

     Set<CardType> tempCardType =new HashSet<>();

    Set<CardNumberInfo> cardNumberInfoSet =new HashSet<>();

    Set<Tier> tierSet =new HashSet<>();

    Set<CardTransaction> cardTransactionSet = new HashSet<>();


    Set<CardMaster> cardMasterSet =new HashSet<>(0);

    Set<CardType> cardTypeSet =new HashSet<>(0);

    Set<CardTransaction> cardTransactionSet1 =new HashSet<>(0);


    @Test
    public void test1Create(){

        CardMaster cardMaster = cardMasterService.saveCardMaster(CardMasterFixture.standardCardMaster());
        log.info(cardMaster.toString());
        Assert.assertNotNull(cardMaster.getCrmId());

        tempCardMaster.add(cardMaster);

    }

    @Test
    public void test2Update() {

        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        log.info("Original CardMaster " + cardMaster.toString());

        CardMaster updatedCardMaster = CardMasterFixture.updatedStandardCardMaster(cardMaster);
        updatedCardMaster = cardMasterService.saveCardMaster(updatedCardMaster);
        log.info("Updated CardMaster "+ updatedCardMaster.toString());

        tempCardMaster.add(cardMaster);

    }

    @Test
    public void test3FindByCrmMerchantNo() {

        // Get the standard cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();

        Page<CardMaster> cardMasters = cardMasterService.findByCrmMerchantNo(cardMaster.getCrmMerchantNo(),constructPageSpecification(1));
        log.info("cardMasters by merchant no " + cardMasters.toString());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());

    }

    @Test
    public void test4FindByCrmMerchantNoAndCrmCardNo() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        CardMaster fetchCardMaster = cardMasterService.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(), cardMaster.getCrmCardNo());
        Assert.assertNotNull(fetchCardMaster);
        log.info("Fetched cardMaster info" + cardMaster.toString());

        tempCardMaster.add(cardMaster);
    }

    @Test
    public void test5FindByCrmMerchantNoAndCrmCardNoLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterService.findByCrmMerchantNoAndCrmCardNoLike(cardMaster.getCrmMerchantNo(), "%10%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test6FindByCrmMerchantNoAndCrmMobileLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterService.findByCrmMerchantNoAndCrmMobileLike(cardMaster.getCrmMerchantNo(), "%9999%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test7FindByCrmMerchantNoAndCrmLoyaltyIdLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterService.findByCrmMerchantNoAndCrmLoyaltyIdLike(cardMaster.getCrmMerchantNo(), "%10%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test8FindByCrmMerchantNoAndCrmCardHolderNameLike() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        Page<CardMaster> cardMasters = cardMasterService.findByCrmMerchantNoAndCrmCardHolderNameLike(cardMaster.getCrmMerchantNo(), "%test%", constructPageSpecification(0));
        Assert.assertTrue(cardMasters.hasContent());
        Set<CardMaster> cardMasterSet = Sets.newHashSet((Iterable<CardMaster>)cardMasters);
        log.info("cardMaster list "+cardMasterSet.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test9ListCardsForCustomer() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Check the cardMaster name
        List<CardMaster> cardMasterList = cardMasterService.listCardsForCustomer(cardMaster.getCrmMerchantNo(),cardMaster.getCrmMobile(),cardMaster.getCrmEmailId(),cardMaster.getCrmLoyaltyId());
        Assert.assertTrue(!cardMasterList.isEmpty());
        log.info("cardMaster list "+cardMasterList.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test10FindByCrmId() throws InspireNetzException{

        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.saveCardType(cardType);

        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        log.info("Original CardMaster " + cardMaster.toString());

        CardMaster fetchedCardMaster = cardMasterService.findByCrmId(cardMaster.getCrmId());
        log.info("Fetched CardMaster "+ fetchedCardMaster.toString());
        log.info("CardMaster " + fetchedCardMaster.getCardType().toString());


        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void testIsBalanceExpire() throws InspireNetzException{

        //settings initial configuration so that is mandatory
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtExpiryOption(CardTypeExpiryOption.EXPIRY_DATE);
        cardType.setCrtExpiryDate(DBUtils.covertToSqlDate("2019-08-13"));
        cardType.setCrtMerchantNo(3L);
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtCardNoRangeFrom("1000");
        cardType.setCrtCardNoRangeTo("2000");
        cardType.setCrtBalanceExpiryOption(CardTypeExpiryOption.EXPIRY_DATE);
        cardType.setCrtBalExpiryDate(DBUtils.covertToSqlDate("2020-11-12"));
        cardType.setCrtBalTopExpiryTime("01:00:02");
        cardType.setCrtBalExpiryDays(0);
        cardType = cardTypeService.save(cardType);

        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1501");
        cardMaster.setCrmCardStatus(IndicatorStatus.YES);
        //put issue date is past date
        cardMaster.setCrmIssuedDate(DBUtils.covertToSqlDate("2015-05-02"));
        cardMaster.setCrmMerchantNo(3L);
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCardType(cardType);
        cardMaster.setCrmCardBalance(12.00);
        Date date =new Date();
        cardMaster.setCrmTopupDate(new Timestamp(date.getTime()));
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        log.info("Original CardMaster " + cardMaster.toString());

        boolean isCardBalanceExpired =cardMasterService.isCardBalanceExpired(cardType,cardMaster);

        //Assert.assertTrue(isCardBalanceExpired);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);
    }


    @Test
    public void testTransferAmount() throws InspireNetzException{

        //settings initial configuration so that is mandatory
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtExpiryOption(CardTypeExpiryOption.EXPIRY_DATE);
        cardType.setCrtExpiryDate(DBUtils.covertToSqlDate("2019-08-13"));
        cardType.setCrtMerchantNo(3L);
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtCardNoRangeFrom("1000");
        cardType.setCrtCardNoRangeTo("2000");
        cardType.setCrtBalanceExpiryOption(CardTypeExpiryOption.EXPIRY_DATE);
        cardType.setCrtBalExpiryDate(DBUtils.covertToSqlDate("2020-11-12"));
        cardType.setCrtBalTopExpiryTime("01:00:02");
        cardType.setCrtBalExpiryDays(0);
        cardType.setCrtMinTopupValue(3);
        cardType.setCrtMaxValue(100);
        cardType = cardTypeService.save(cardType);

        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1501");
        cardMaster.setCrmCardStatus(IndicatorStatus.YES);
        //put issue date is past date
        cardMaster.setCrmIssuedDate(DBUtils.covertToSqlDate("2015-10-02"));
        cardMaster.setCrmMerchantNo(3L);
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCardType(cardType);
        cardMaster.setCrmCardBalance(12.00);
        Date date =new Date();
        cardMaster.setCrmTopupDate(new Timestamp(date.getTime()));
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        log.info("Original CardMaster " + cardMaster.toString());

        CardMaster cardMaster2 = CardMasterFixture.standardCardMaster();
        cardMaster2.setCrmCardNo("12345");
        cardMaster2.setCrmCardStatus(IndicatorStatus.YES);

        //put issue date is past date
        cardMaster2.setCrmIssuedDate(DBUtils.covertToSqlDate("2015-10-02"));
        cardMaster2.setCrmMerchantNo(3L);
        cardMaster2.setCrmType(cardType.getCrtId());
        cardMaster2.setCardType(cardType);
        cardMaster2.setCrmCardBalance(12.00);
        cardMaster2.setCrmTopupDate(new Timestamp(date.getTime()));
        cardMaster2 = cardMasterService.saveCardMaster(cardMaster2);
        log.info("Original CardMaster " + cardMaster.toString());

        //transfer Amount
        boolean cardMasterOperationResponse =cardMasterService.transferAmount(cardMaster.getCrmCardNo(), cardMaster2.getCrmCardNo(), 3L, 1.00, "", "reference",1,"","");



        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);
    }

    @Test
    public void test11IsDuplicateCardExisting() {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // Create a new cardMaster
        CardMaster newCardMaster = CardMasterFixture.standardCardMaster();
        boolean exists = cardMasterService.isDuplicateCardExisting(newCardMaster);
        Assert.assertTrue(exists);
        log.info("CardMaster exists");

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test12DeleteCardMaster() throws InspireNetzException {

        // Create the cardMaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");

        // call the delete cardMaster
        cardMasterService.deleteCardMaster(cardMaster.getCrmId(),cardMaster.getCrmMerchantNo());

        // Try searching for the cardMaster
        CardMaster checkCardMaster  = cardMasterService.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(), cardMaster.getCrmCardNo());

        Assert.assertNull(checkCardMaster);

        log.info("cardMaster deleted");

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test13SetCardLockStatus() throws InspireNetzException {

        // Get the CardMaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Create CardMasterLockStatusRequest
        CardMasterLockStatusRequest cardMasterLockStatusRequest = new CardMasterLockStatusRequest();
        cardMasterLockStatusRequest.setCtxLocation(0L);
        cardMasterLockStatusRequest.setUserNo(0L);
        cardMasterLockStatusRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterLockStatusRequest.setLockStatus(CardMasterStatus.LOCKED);
        cardMasterLockStatusRequest.setMerchantNo(cardMaster.getCrmMerchantNo());

        // Set the card as locked
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.setCardLockStatus(cardMasterLockStatusRequest);
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test15ChangeCardPin() throws InspireNetzException {

        // Get the CardMaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Create the CardMasterChangePinRequest
        CardMasterChangePinRequest cardMasterChangePinRequest = new CardMasterChangePinRequest();
        cardMasterChangePinRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterChangePinRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterChangePinRequest.setUserNo(0L);
        cardMasterChangePinRequest.setCardPin("3323");
        cardMasterChangePinRequest.setCtxLocation(0L);

        // Set the card as changed
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.changeCardPin(cardMasterChangePinRequest.getCardNo(),cardMasterChangePinRequest.getCardPin(),cardMaster.getCrmMerchantNo());
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);



    }

    @Test
    public void test16IssueCard() throws InspireNetzException {

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("2222333344445555");

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.save(cardType);
        cardMaster.setCrmType(cardType.getCrtId());
        tempCardType.add(cardType);

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.issueCard(cardMaster,0L,"",false,1);
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void test17IsCardExpired() {

        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();

        // Set the cardMasterExpiry
        cardMaster.setCrmExpiryDate(DBUtils.covertToSqlDate("2014-07-26"));



        boolean isExpired = cardMasterService.isCardExpired(cardType,cardMaster);
        Assert.assertTrue(isExpired);
        log.info("CardExpired");

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void test18TopupCard() throws InspireNetzException {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmActivationType(CardActivationType.CARD_INSTANTLY);
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);


        // Create the CardMasterTopupRequest
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCtxLocation(0L);
        cardMasterTopupRequest.setTopupAmount(20.0);
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterTopupRequest.setPaymentMode(PaymentMode.CASH);


        // Variable holding incentiveAmount
        Double incentiveAmount = 0.0;

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.topupCard(cardMasterTopupRequest.getCardNo(),cardMasterTopupRequest.getTopupAmount(),cardMaster.getCrmMerchantNo(),"",cardMasterTopupRequest.getPaymentMode(),"",true,false,incentiveAmount);
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void test19RefundCard() throws InspireNetzException {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);


        // Create the CardMasterRefundRequest
        CardMasterRefundRequest cardMasterRefundRequest = new CardMasterRefundRequest();
        cardMasterRefundRequest.setCtxLocation(0l);;
        cardMasterRefundRequest.setUserNo(0L);
        cardMasterRefundRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterRefundRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterRefundRequest.setReference("c/234234");
        cardMasterRefundRequest.setRefundAmount(100.0);
        cardMasterRefundRequest.setPromoRefund(10.0);
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.refundCard(cardMasterRefundRequest.getCardNo(),cardMasterRefundRequest.getRefundAmount(), cardMasterRefundRequest.getReference(),cardMasterRefundRequest.getMerchantNo(),1,"",cardMasterRefundRequest.getPromoRefund());
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void test20TransferCard() throws InspireNetzException {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the  source Cardmaster
        CardMaster srcCardMaster = CardMasterFixture.standardCardMaster();
        srcCardMaster.setCrmCardNo("1001");
        srcCardMaster.setCrmType(cardType.getCrtId());
        srcCardMaster = cardMasterService.saveCardMaster(srcCardMaster);


        // Get the  source Cardmaster
        CardMaster destCardMaster = CardMasterFixture.standardCardMaster();
        destCardMaster.setCrmType(cardType.getCrtId());
        destCardMaster.setCrmCardNo("1002");
        destCardMaster = cardMasterService.saveCardMaster(destCardMaster);


        // Create the CardMasterTransferRequest
        CardMasterTransferRequest cardMasterTransferRequest = new CardMasterTransferRequest();
        cardMasterTransferRequest.setCtxLocation(0L);
        cardMasterTransferRequest.setReference("00");
        cardMasterTransferRequest.setDestCardNo(destCardMaster.getCrmCardNo());
        cardMasterTransferRequest.setSourceCardNo(srcCardMaster.getCrmCardNo());
        cardMasterTransferRequest.setMerchantNo(srcCardMaster.getCrmMerchantNo());
        cardMasterTransferRequest.setUserNo(0L);

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.transferCard(cardMasterTransferRequest.getSourceCardNo(),cardMasterTransferRequest.getDestCardNo(),cardMasterTransferRequest.getMerchantNo(),"");
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());


        tempCardType.add(cardType);

    }

    @Test
    public void test21GetCardExpiry() {

        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();

        // Set the cardMasterExpiry
        cardMaster.setCrmExpiryDate(DBUtils.covertToSqlDate("2014-07-26"));



        String expiryText = cardMasterService.getCardExpiry(cardType,cardMaster);
        Assert.assertNotNull(expiryText);
        log.info("Expiry Text: "+expiryText);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }


    @Test
    public void test22DebitCard() throws InspireNetzException {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);


        // Create the CardMasterDebitrequest
        CardMasterDebitRequest cardMasterDebitRequest = new CardMasterDebitRequest();
        cardMasterDebitRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterDebitRequest.setDebitAmount(200.0);
        cardMasterDebitRequest.setTxnReference("232323");
        cardMasterDebitRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterDebitRequest.setTxnLocation(0L);

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.debitCard(cardMasterDebitRequest.getCardNo(),cardMasterDebitRequest.getTxnReference(),cardMaster.getCrmPin(),cardMasterDebitRequest.getDebitAmount(),cardMaster.getCrmMerchantNo(),new CardTransferAuthenticationRequest(),1,"");
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void validateAndIssueCardFromMerchant() throws InspireNetzException {

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("3333444455556666");

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.save(cardType);
        cardMaster.setCrmType(cardType.getCrtId());
        tempCardType.add(cardType);

        CardMasterResource cardMasterResource=mapper.map(cardMaster,CardMasterResource.class);
        cardMasterResource.setActivationPin("1234");

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.validateAndIssueCardFromMerchant(cardMasterResource,1L,"", false,1,"");
        Assert.assertNotNull(cardMasterOperationResponse);
        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void IssueCardFromPortal() throws InspireNetzException {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("ISSUE_CARD_TEST_TYPE");
        cardType.setCrtType(CardTypeType.FIXED_VALUE);
        cardType.setCrtFixedValue(1000.0);
        cardType = cardTypeService.save(cardType);

        Assert.assertNotNull(cardType);
        tempCardType.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfo);
        cardNumberInfoSet.add(cardNumberInfo);

        otp=oneTimePasswordService.generateOTP(cardNumberInfo.getCniMerchantNo(),"8867987369", OTPRefType.PUBLIC, OTPType.PUBLIC_CARD_REGISTER);

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1111111111111111");
        cardMaster.setCrmMerchantNo(0L);
        cardMaster.setCrmMobile("8867987369");
        cardMaster.setCrmType(cardType.getCrtId());

        merchantNo =cardNumberInfo.getCniMerchantNo();


        CardMasterPublicResponse cardMasterPublicResponse = cardMasterService.issueCardFromPublic(cardMaster.getCrmCardNo(),cardNumberInfo.getCniPin(),cardMaster.getCrmMobile(),cardMaster.getCrmCardHolderName(),cardMaster.getCrmEmailId(),cardMaster.getCrmDob()==null?"":cardMaster.getCrmDob().toString(),0L,otp,1);
        Assert.assertNotNull(cardMasterPublicResponse);
        log.info("CardMasterOperationResponse : " + cardMasterPublicResponse.toString());

        cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

        tempCardMaster.add(cardMaster);


    }

    @Test
    public void getCardBalanceOTP()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("ISSUE_CARD_TEST_TYPE");
        cardType.setCrtType(CardTypeType.FIXED_VALUE);
        cardType.setCrtFixedValue(1000.0);
        cardType = cardTypeService.save(cardType);

        Assert.assertNotNull(cardType);
        tempCardType.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);

        cardNumberInfoSet.add(cardNumberInfo);

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1111111111111111");
        cardMaster.setCrmMobile("8867987369");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

        cardMaster=cardMasterService.saveCardMaster(cardMaster);

        tempCardMaster.add(cardMaster);


        boolean isOTPGenerated=cardMasterService.getCardBalanceOTP(cardMaster.getCrmCardNo(),0L);

        // Convert json response to HashMap
        Assert.assertTrue(isOTPGenerated);

        log.info("CardMasterResponse: " + isOTPGenerated);






    }

    @Test
    public void getCardBalancePublic()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("ISSUE_CARD_TEST_TYPE");
        cardType.setCrtType(CardTypeType.FIXED_VALUE);
        cardType.setCrtFixedValue(1000.0);
        cardType = cardTypeService.save(cardType);

        Assert.assertNotNull(cardType);
        tempCardType.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);
        cardNumberInfoSet.add(cardNumberInfo);

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1111111111111111");
        cardMaster.setCrmMobile("8867987369");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

        cardMaster=cardMasterService.saveCardMaster(cardMaster);

        tempCardMaster.add(cardMaster);

        otp=oneTimePasswordService.generateOTP(1L,cardMaster.getCrmId().toString(), OTPRefType.CARDMASTER, OTPType.CHARGE_CARD_BALANCE_OTP);

        merchantNo=1L;

        CardMasterPublicResponse cardMasterPublicResponse=cardMasterService.getCardBalancePublic(cardMaster.getCrmCardNo(),0L,otp);

        Assert.assertNotNull(cardMasterPublicResponse);
        log.info("CardMasterResponse: " + cardMasterPublicResponse.toString());



    }

    @Test
    public void testAccountActivation () throws InspireNetzException {



        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);

        // Get the CardMaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1222222");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmMerchantNo(3L);
        cardMaster.setCrmActivationType(CardActivationType.CARD_INSTANTLY);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Create the CardMasterTopupRequest
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCtxLocation(0L);
        cardMasterTopupRequest.setTopupAmount(20.0);
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterTopupRequest.setPaymentMode(PaymentMode.CASH);

        // Variable holding incentiveAmount
        Double incentiveAmount = 0.0;

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.topupCard(cardMasterTopupRequest.getCardNo(),cardMasterTopupRequest.getTopupAmount(),cardMaster.getCrmMerchantNo(),"",cardMasterTopupRequest.getPaymentMode(),"",false,false,incentiveAmount);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);


    }

    @Test
    public void updateTier () throws InspireNetzException {



        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("opop123");
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType.setCrtMerchantNo(3L);
        cardType = cardTypeService.saveCardType(cardType);

        // Get the CardMaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("12222225555789");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmMerchantNo(3L);
        cardMaster.setCrmActivationType(CardActivationType.CARD_INSTANTLY);
        cardMaster.setCrmTier(1L);
        cardMaster.setCrmExpiryDate(DBUtils.covertToSqlDate("2016-11-18"));

        cardMaster = cardMasterService.saveCardMaster(cardMaster);



        //update tier
        CardMaster cardMaster1 =cardMasterService.updateTier(cardMaster.getCrmCardNo(),11L,cardMaster.getCrmMerchantNo(),true,cardMaster.getCrmExpiryDate());

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);


        if (cardMaster1.getCrmTier().longValue() !=11L){

            Assert.assertFalse(true);
        }


    }



    @Test
    public void testTopUp() throws InspireNetzException{



        //get the cardType details
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtName("TEST_PROMO_TOPUP_RECHARGEble");
        cardType.setCrtMaxValue(10000.0);
        cardType.setCrtMinTopupValue(100);
        cardType.setCrtPromoIncentive(1);
        cardType.setCrtPromoIncentiveType(3);
        cardType.setCrtTier1Upto(1);
        cardType.setCrtTier1LimitTo(1000.0);
        cardType.setCrtTier1Num(5.0);
        cardType.setCrtTier1Deno(100.0);
        cardType.setCrtTier5Upto(1);
        cardType.setCrtTier5Num(10.0);
        cardType.setCrtTier5Deno(100.0);
        cardType.setCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS);
        cardType.setCrtActivateDays(0);
        cardType = cardTypeService.saveCardType(cardType);

        //get the cardMaster details
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("1122334455667788");
        cardMaster.setCrmMerchantNo(3L);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //get the cardMasterTopUp request details
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setTopupAmount(100.0);
        cardMasterTopupRequest.setCtxLocation(0L);
        cardMasterTopupRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterTopupRequest.setPaymentMode(PaymentMode.CASH);
        cardMasterTopupRequest.setReference("abhi");

        // Variable holding incentiveAmount
        Double incentiveAmount = 0.0;

        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.topupCard(cardMasterTopupRequest.getCardNo(),cardMasterTopupRequest.getTopupAmount(),cardMaster.getCrmMerchantNo(),cardMasterTopupRequest.getReference(),cardMasterTopupRequest.getPaymentMode(),"",true,false,incentiveAmount);

        Page<CardTransaction> cardTransactions = cardTransactionService.searchCardTransactions(cardMaster.getCrmMerchantNo(), cardMaster.getCrmCardNo(), CardTransactionType.PROMO_TOPUP, null, null, constructPageSpecification(0));

        double topupAmout = 0.0;

        //calculating promo amount
        for (CardTransaction cardTransaction : cardTransactions){

            //getting promo topup amount
            topupAmout = topupAmout + cardTransaction.getCtxTxnAmount();
            cardTransactionSet.add(cardTransaction);

        }

        Assert.assertTrue(topupAmout == 5.0);

        log.info("CardMasterOperationResponse : " + cardMasterOperationResponse.toString());

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void testCalculateTieredRatioIncentiveAmount() throws InspireNetzException{

        //get the cardType details
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtName("TEST_PROMO_TOPUP_RECHARGEABLE");
        cardType.setCrtMaxValue(10000.0);
        cardType.setCrtMinTopupValue(100);
        cardType.setCrtPromoIncentive(1);
        cardType.setCrtPromoIncentiveType(3);
        cardType.setCrtTier1Upto(1);
        cardType.setCrtTier1LimitTo(1000.0);
        cardType.setCrtTier1Num(5.0);
        cardType.setCrtTier1Deno(100.0);
        cardType.setCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS);
        cardType.setCrtActivateDays(0);
        cardType = cardTypeService.saveCardType(cardType);

        //get the cardMaster details
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("112233445566788");
        cardMaster.setCrmMerchantNo(3L);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //get the cardMasterTopUp request details
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setTopupAmount(100.0);

        Double incentiveAmount = cardMasterService.calculateTieredRatioIncentiveAmount(cardType, cardMasterTopupRequest);

        Assert.assertTrue(incentiveAmount == 5.0);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);
    }


    @Test
    public void testCalculatePromoAmount1() throws InspireNetzException{

        //get the cardType details
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtName("TEST_PROMO_TOPUP_RECHARGEABLE");
        cardType.setCrtMaxValue(10000.0);
        cardType.setCrtMinTopupValue(100);
        cardType.setCrtPromoIncentive(1);
        cardType.setCrtPromoIncentiveType(1);
        cardType.setCrtIncentiveAmount(10.0);
        cardType.setCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS);
        cardType.setCrtActivateDays(0);
        cardType = cardTypeService.saveCardType(cardType);

        //get the cardMaster details
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("112233445566788");
        cardMaster.setCrmMerchantNo(3L);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //get the cardMasterTopUp request details
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setTopupAmount(100.0);

        Double promoAmount = cardMasterService.calculatePromoAmount(cardType, cardMasterTopupRequest);

        Assert.assertTrue(promoAmount == 10.0);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }

    @Test
    public void testCalculatePromoAmount2() throws InspireNetzException{

        //get the cardType details
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtName("TEST_PROMO_TOPUP_RECHARGEABLE");
        cardType.setCrtMaxValue(10000.0);
        cardType.setCrtMinTopupValue(100);
        cardType.setCrtPromoIncentive(1);
        cardType.setCrtPromoIncentiveType(2);
        cardType.setCrtIncentiveDiscount(2.0);
        cardType.setCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS);
        cardType.setCrtActivateDays(0);
        cardType = cardTypeService.saveCardType(cardType);

        //get the cardMaster details
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("112233445566788");
        cardMaster.setCrmMerchantNo(3L);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //get the cardMasterTopUp request details
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setTopupAmount(50.0);

        Double promoAmount = cardMasterService.calculatePromoAmount(cardType, cardMasterTopupRequest);

        Assert.assertTrue(promoAmount == 1.0);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }


    @Test
    public void testCalculatePromoAmount3() throws InspireNetzException{

        //get the cardType details
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtName("TEST_PROMO_TOPUP_RECHARGEABLE");
        cardType.setCrtMaxValue(10000.0);
        cardType.setCrtMinTopupValue(100);
        cardType.setCrtPromoIncentive(1);
        cardType.setCrtPromoIncentiveType(3);
        cardType.setCrtTier1Upto(1);
        cardType.setCrtTier1LimitTo(1000.0);
        cardType.setCrtTier1Num(3.0);
        cardType.setCrtTier1Deno(100.0);
        cardType.setCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS);
        cardType.setCrtActivateDays(0);
        cardType = cardTypeService.saveCardType(cardType);

        //get the cardMaster details
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("112233445566788");
        cardMaster.setCrmMerchantNo(3L);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //get the cardMasterTopUp request details
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setTopupAmount(50.0);

        Double promoAmount = cardMasterService.calculatePromoAmount(cardType, cardMasterTopupRequest);

        Assert.assertTrue(promoAmount == 1.5);

        tempCardMaster.add(cardMaster);
        tempCardType.add(cardType);

    }


    @Test
    public void getDateFromTimeStamp(){

        //get the date
        CardTransaction cardTransaction = cardTransactionService.findByCtxTxnNo(1L);

        //get the time stamp of transaction object
        String date = DBUtils.getDateFromTimeStamp(cardTransaction.getCtxTxnTimestamp());

        java.sql.Date saleFormatedDate = DBUtils.covertToSqlDate(date);

        String time = DBUtils.getTimeFromTimeStamp(cardTransaction.getCtxTxnTimestamp());

        Time time1 = DBUtils.convertToSqlTime(time);

        log.info(saleFormatedDate +" date");

        log.info("time   "+time1);

    }



    @Test
    public void prepaidCardSaleObject(){

        //get the date
        //get the cardType details
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtName("TEST_PROMO_TOPUP_RECHARGEble");
        cardType.setCrtMaxValue(10000.0);
        cardType.setCrtMinTopupValue(100);
        cardType.setCrtPromoIncentive(1);
        cardType.setCrtPromoIncentiveType(3);
        cardType.setCrtTier1Upto(1);
        cardType.setCrtTier1LimitTo(1000.0);
        cardType.setCrtTier1Num(5.0);
        cardType.setCrtTier1Deno(100.0);
        cardType.setCrtTier5Upto(1);
        cardType.setCrtTier5Num(10.0);
        cardType.setCrtTier5Deno(100.0);
        cardType.setCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS);
        cardType.setCrtActivateDays(0);

        cardType = cardTypeService.save(cardType);

        //get the cardMaster details
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("1122334455667788");
        cardMaster.setCrmMobile("9620127820");
        cardMaster.setCrmMerchantNo(3L);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //get the cardMasterTopUp request details
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();
        cardMasterTopupRequest.setCardNo(cardMaster.getCrmCardNo());
        cardMasterTopupRequest.setTopupAmount(100.0);
        cardMasterTopupRequest.setCtxLocation(0L);
        cardMasterTopupRequest.setMerchantNo(cardMaster.getCrmMerchantNo());
        cardMasterTopupRequest.setPaymentMode(PaymentMode.CASH);
        cardMasterTopupRequest.setReference("ameen");

        // variable holding incentiveAmount
        Double incentiveAmount = 0.0;



        try {

            CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.topupCard(cardMasterTopupRequest.getCardNo(),cardMasterTopupRequest.getTopupAmount(),cardMaster.getCrmMerchantNo(),cardMasterTopupRequest.getReference(),cardMasterTopupRequest.getPaymentMode(),"",true,false,incentiveAmount);

            //get Card Transaction
            CardTransaction cardTransaction = cardTransactionService.findByCtxTxnNo(Long.parseLong(cardMasterOperationResponse.getTxnref()));

            //get Sale object
            Sale sale = cardMasterService.prepaidCardSaleObject(cardMaster,cardTransaction);

            tempCardType.add(cardType);
            tempCardMaster.add(cardMaster);
            cardTransactionSet.add(cardTransaction);

            Assert.assertNotNull(sale);

            log.info("get the sale object --------------->"+sale);

        } catch (InspireNetzException e) {
            e.printStackTrace();
        }


    }


    @After
    public void tearDown() throws InspireNetzException{        for (CardMaster cardMaster:tempCardMaster){

            cardMasterService.deleteCardMaster(cardMaster.getCrmId(),cardMaster.getCrmMerchantNo());
        }

        for (CardNumberInfo cardNumberInfo:cardNumberInfoSet){

            cardNumberInfoService.delete(cardNumberInfo);
        }

        for (CardType cardType:tempCardType){


            cardTypeService.deleteCardType(cardType.getCrtId(),cardType.getCrtMerchantNo());
        }

        for(CardTransaction cardTransaction:cardTransactionSet){
            cardTransactionService.deleteCardTransaction(cardTransaction.getCtxTxnNo());
        }

        for (Tier tier:tierSet){

            tierService.deleteTier(tier.getTieId());
        }

        //delete otp
        OneTimePassword oneTimePassword=oneTimePasswordService.findByOtpCode(merchantNo,otp);

        if(oneTimePassword!=null){
            oneTimePasswordService.deleteOneTimePassword(oneTimePassword.getOtpId());
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

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "crmId");
    }

}
