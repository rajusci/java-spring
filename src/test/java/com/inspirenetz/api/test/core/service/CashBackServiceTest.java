package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.core.service.impl.AccountNoValidation;
import com.inspirenetz.api.core.service.impl.OTPValidation;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class,IntegrationTestConfig.class,NotificationTestConfig.class})
public class CashBackServiceTest {


    private static Logger log = LoggerFactory.getLogger(CashBackServiceTest.class);

    @Autowired
    private CashBackService cashBackService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    @Autowired
    OTPValidation otpValidation;

    @Autowired
     MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    Set<RedemptionMerchant> merchantSet = new HashSet<>(0);
    Set<User> userSet = new HashSet<>(0);
    Set<Customer> customerSet = new HashSet<>(0);
    Set<CustomerRewardBalance> balanceSet = new HashSet<>(0);
    Set<CustomerRewardExpiry> expirySet = new HashSet<>(0);
    Set<OneTimePassword> oneTimePasswordSet = new HashSet<>(0);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create() throws InspireNetzException, ParserConfigurationException, SAXException, IOException {

    }

    @Test
    public void getCashBackRequestObject() throws InspireNetzException {

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant.setRemCode("TEST");
        redemptionMerchant.setRemContactMobile("9192919394");
        redemptionMerchant.setRemType(RedemptionMerchantType.SMS);
        redemptionMerchant.setRemSettlementType(MerchantSettlementType.BANK_PAYMENT);
        redemptionMerchant.setRemSettlementLevel(MerchantSettlementLevel.MERCHANT_USER_LEVEL);
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);

        merchantSet.add(redemptionMerchant);

        User user = UserFixture.standardUser();
        user.setUsrLoginId("TESTREDEMPTION");
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user.setUsrThirdPartyVendorNo(redemptionMerchant.getRemNo());
        user.setUsrStatus(UserStatus.ACTIVE);
        user = userService.saveUser(user);
        userSet.add(user);

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9538052636");
        customer.setCusStatus(CustomerStatus.ACTIVE);
        customer = customerService.saveCustomer(customer);

        customerSet.add(customer);


        User customerUser = UserFixture.standardUser();
        customerUser.setUsrLoginId(customer.getCusLoyaltyId());
        customerUser.setUsrStatus(UserStatus.ACTIVE);
        customerUser = userService.saveUser(customerUser);
        userSet.add(customerUser);

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(1L);
        customerRewardBalance.setCrbMerchantNo(1L);
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        balanceSet.add(customerRewardBalance);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardCurrencyId(1L);
        customerRewardExpiry.setCreExpiryDt(Date.valueOf("2015-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        expirySet.add(customerRewardExpiry);

        CashBackRequest cashBackRequest = new CashBackRequest();
        cashBackRequest.setMerchantNo(1l);
        cashBackRequest.setLoyaltyId(customer.getCusLoyaltyId());
        cashBackRequest.setAmount(100.0);
        cashBackRequest.setMerchantIdentifier(redemptionMerchant.getRemCode());
        cashBackRequest.setRef(user.getUsrLoginId());

        cashBackService.doCashBack(cashBackRequest);


    }


    @Test
    public void doCashBackFromPartner() throws InspireNetzException {

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant.setRemCode("TEST");
        redemptionMerchant.setRemContactMobile("9192919394");
        redemptionMerchant.setRemType(RedemptionMerchantType.SMS);
        redemptionMerchant.setRemSettlementType(MerchantSettlementType.BANK_PAYMENT);
        redemptionMerchant.setRemSettlementLevel(MerchantSettlementLevel.MERCHANT_USER_LEVEL);
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);

        merchantSet.add(redemptionMerchant);

        User user = UserFixture.standardUser();
        user.setUsrLoginId("TESTREDEMPTION");
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user.setUsrThirdPartyVendorNo(redemptionMerchant.getRemNo());
        user.setUsrStatus(UserStatus.ACTIVE);
        user = userService.saveUser(user);
        userSet.add(user);

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9538052636");
        customer.setCusStatus(CustomerStatus.ACTIVE);
        customer = customerService.saveCustomer(customer);

        customerSet.add(customer);


        User customerUser = UserFixture.standardUser();
        customerUser.setUsrLoginId(customer.getCusLoyaltyId());
        customerUser.setUsrStatus(UserStatus.ACTIVE);
        customerUser = userService.saveUser(customerUser);
        userSet.add(customerUser);

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(1L);
        customerRewardBalance.setCrbMerchantNo(1L);
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        balanceSet.add(customerRewardBalance);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardCurrencyId(1L);
        customerRewardExpiry.setCreExpiryDt(Date.valueOf("2015-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        expirySet.add(customerRewardExpiry);

        CashBackRequest cashBackRequest = new CashBackRequest();
        cashBackRequest.setMerchantNo(1l);
        cashBackRequest.setLoyaltyId(customer.getCusLoyaltyId());
        cashBackRequest.setAmount(100.0);
        cashBackRequest.setMerchantIdentifier(redemptionMerchant.getRemCode());
        cashBackRequest.setRef(user.getUsrLoginId());

        cashBackService.doCashBack(cashBackRequest);


    }

    @Test
    public void testAccountNoValidation() throws InspireNetzException {

        ValidationService validationService = new AccountNoValidation();

        ValidationRequest validationRequest = new ValidationRequest();

        validationRequest.setRef("0201889544");;
        validationService.isValid(validationRequest);

        validationRequest.setRef("0134927542");;
        validationService.isValid(validationRequest);

        validationRequest.setRef("0116641070");
        validationService.isValid(validationRequest);


    }


    @Test
    public void testOTPValidation() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer.setCusMerchantNo(3L);
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);


        boolean isGenerated=oneTimePasswordService.generateOTPCompatible(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),OTPType.PAY_POINTS);

        Assert.assertTrue(isGenerated);

        log.info("generateOTPCompatible Response : " +isGenerated);

        //get the generated one time password

        OneTimePassword oneTimePassword = oneTimePasswordService.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(customer.getCusMerchantNo(),OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),OTPType.PAY_POINTS);

        ValidationService validationService = new OTPValidation();

        ValidationRequest validationRequest = new ValidationRequest();

        validationRequest.setOtpCode(oneTimePassword.getOtpCode());

        validationRequest.setMerchantNo(oneTimePassword.getOtpMerchantNo());

        validationRequest.setRef(oneTimePassword.getOtpReference());

        validationRequest.setCustomer(customer);

        ValidationResponse validationResponse = otpValidation.isValid(validationRequest);

        Assert.assertNotNull(validationResponse);

        log.info("Validation response -->" + validationResponse);
    }


    @Test
    public void testOTPValidationForCashback() throws InspireNetzException{

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant.setRemCode("TEST01");
        redemptionMerchant.setRemContactMobile("9192919384");
        redemptionMerchant.setRemType(RedemptionMerchantType.SMS);
        redemptionMerchant.setRemSettlementType(MerchantSettlementType.BANK_PAYMENT);
        redemptionMerchant.setRemSettlementLevel(MerchantSettlementLevel.MERCHANT_USER_LEVEL);
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);

        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();
        merchantRedemptionPartner.setMrpMerchantNo(3L);
        merchantRedemptionPartner.setMrpRewardCurrency(3L);
        merchantRedemptionPartner.setMrpRedemptionMerchant(redemptionMerchant.getRemNo());
        merchantRedemptionPartner.setMrpEnabled(IndicatorStatus.YES);

        merchantRedemptionPartnerService.saveMerchantRedemptionPartner(merchantRedemptionPartner);
        merchantSet.add(redemptionMerchant);

        User user = UserFixture.standardUser();
        user.setUsrLoginId("OTTES1");
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user.setUsrThirdPartyVendorNo(redemptionMerchant.getRemNo());
        user.setUsrStatus(UserStatus.ACTIVE);
        user = userService.saveUser(user);
        userSet.add(user);

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9538778054");
        customer.setCusMerchantNo(3L);
        customer.setCusStatus(CustomerStatus.ACTIVE);
        customer = customerService.saveCustomer(customer);

        customerSet.add(customer);


        User customerUser = UserFixture.standardUser();
        customerUser.setUsrLoginId(customer.getCusLoyaltyId());
        customerUser.setUsrStatus(UserStatus.ACTIVE);
        customerUser = userService.saveUser(customerUser);
        userSet.add(customerUser);

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(3L);
        customerRewardBalance.setCrbMerchantNo(3L);
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        balanceSet.add(customerRewardBalance);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardCurrencyId(3L);
        customerRewardExpiry.setCreExpiryDt(Date.valueOf("2016-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        expirySet.add(customerRewardExpiry);

        boolean isGenerated=oneTimePasswordService.generateOTPCompatible(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),OTPType.PAY_POINTS);

        Assert.assertTrue(isGenerated);

        log.info("generateOTPCompatible Response : " +isGenerated);

        //get the generated one time password

        OneTimePassword oneTimePassword = oneTimePasswordService.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(customer.getCusMerchantNo(),OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),OTPType.PAY_POINTS);

        CashBackRequest cashBackRequest = new CashBackRequest();
        cashBackRequest.setMerchantNo(3l);
        cashBackRequest.setLoyaltyId(customer.getCusLoyaltyId());
        cashBackRequest.setAmount(100.0);
        cashBackRequest.setMerchantIdentifier(redemptionMerchant.getRemCode());
        cashBackRequest.setRef(user.getUsrLoginId());
        cashBackRequest.setOtpCode(oneTimePassword.getOtpCode());
        cashBackRequest.setChannel(RequestChannel.RDM_CHANNEL_SMS);


        cashBackService.doCashBack(cashBackRequest);
    }

    @After
    public void tearDown() throws InspireNetzException {

        for(RedemptionMerchant merchant : merchantSet){

            redemptionMerchantService.deleteRedemptionMerchant(merchant.getRemNo());
        }

        for(User user : userSet){

            userService.deleteUser(user);

        }

        for(Customer customer :customerSet){

            customerService.deleteCustomer(customer.getCusCustomerNo());
        }

        for(CustomerRewardBalance customerRewardBalance :balanceSet){

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);
        }

        for(CustomerRewardExpiry customerRewardExpiry :expirySet){

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);
        }
    }

}
