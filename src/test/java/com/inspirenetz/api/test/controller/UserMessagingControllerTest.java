package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by fayizkci on 10/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class,NotificationTestConfig.class})
public class UserMessagingControllerTest {


    private static Logger log = LoggerFactory.getLogger(UserMessagingControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;


    @Autowired
    MerchantService merchantService;

    @Autowired
    SettingService settingService;

    @Autowired
    MerchantSettingService merchantSettingService;

    Set<MerchantSetting> settingSet = new HashSet<MerchantSetting>(0);

    Set<Merchant> merchantSet = new HashSet<Merchant>(0);

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    MessageSpielService messageSpielService;

    @Autowired
    SpielTextService spielTextService;

    Set<MessageSpiel> messageSpielSet = new HashSet<MessageSpiel>(0);

    Set<SpielText> spielTexts = new HashSet<SpielText>(0);

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<Notification> notificationSet = new HashSet<>(0);

    @Value("${email.hostname}")
    private String emailHost;


    @Value("${email.port}")
    private String emailPort;

    @Value("${email.username}")
    private String emailUserName;

    @Value("${email.password}")
    private String emailPassword;

    @Value("${email.from}")
    private String emailFromEmail;

    @Value("${email.prop.mail.transport.protocol}")
    private String emailProtocol;

    @Value("${email.prop.smtp.starttls.enable}")
    private String emailEnableSSL;

    @Value("${email.prop.smtp.auth}")
    private String emailSMTPAuth;

    @Value("${email.prop.smtp.socketFactory.class}")
    private String emailSMTPSocketFactoryClass;

    @Value("${email.mail.debug}")
    private String emailEnableDebug;

    @Value("${email.ReplyTo}")
    private String emailReplyTo;

    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();


    }

    @Test
    public void transmitBulkNotification() throws Exception  {

        Merchant merchant = MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant = merchantService.saveMerchant(merchant);
        merchantSet.add(merchant);

        Setting smsLinkStringSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_SMS_LINK_STRING);

        if(smsLinkStringSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_SMS_LINK_STRING);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                smsLinkStringSettingsId=setting;
            }
        }

        Setting smsEnableSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_ENABLE_SMS);

        if(smsEnableSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_ENABLE_SMS);
            setting.setSetDataType(SettingDataType.YES_NO);
            setting.setSetDefaultValue(IndicatorStatus.NO+"");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                smsEnableSettingsId=setting;
            }
        }

        Setting smsCountryCodeSettingsId = settingService.findBySetName(AdminSettingsConfiguration.SMS_COUNTRY_CODE);

        if(smsCountryCodeSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.SMS_COUNTRY_CODE);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                smsCountryCodeSettingsId=setting;
            }
        }

        Setting smsProviderSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_SMS_PROVIDER);

        if(smsProviderSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_SMS_PROVIDER);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                smsProviderSettingsId=setting;
            }
        }

        Setting emailEnableSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_ENABLE_EMAIL);

        if(emailEnableSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_ENABLE_EMAIL);
            setting.setSetDataType(SettingDataType.YES_NO);
            setting.setSetDefaultValue(IndicatorStatus.NO+"");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailEnableSettingsId=setting;
            }
        }

        Setting emailHostSettingId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_HOST);

        if(emailHostSettingId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_HOST);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailHostSettingId=setting;
            }
        }

        Setting emailPortSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PORT);

        if(emailPortSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PORT);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailPortSettingsId=setting;
            }
        }

        Setting emailUserNameSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_USER_NAME);

        if(emailUserNameSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_USER_NAME);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailUserNameSettingsId=setting;
            }
        }

        Setting emailPasswordSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PASSWORD);

        if(emailPasswordSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PASSWORD);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailPasswordSettingsId=setting;
            }
        }

        Setting emailProtocolSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PROP_PROTOCOL);

        if(emailProtocolSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PROP_PROTOCOL);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailProtocolSettingsId=setting;
            }
        }

        Setting emailSocketFactorySettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_SOCKETFACTORY_CLASS);

        if(emailSocketFactorySettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_SOCKETFACTORY_CLASS);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailSocketFactorySettingsId=setting;
            }
        }

        Setting emailEnableSSLSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PROP_ENABLE_SSL);

        if(emailEnableSSLSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PROP_ENABLE_SSL);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailEnableSSLSettingsId=setting;
            }
        }

        Setting emailDebugEnabledSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PROP_DEBUG_ENABLE);

        if(emailDebugEnabledSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PROP_DEBUG_ENABLE);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailDebugEnabledSettingsId=setting;
            }
        }

        Setting emailSmtpAuthSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_ENABLE_AUTH);

        if(emailSmtpAuthSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_ENABLE_AUTH);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailSmtpAuthSettingsId=setting;
            }
        }

        Setting emailFromSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_FROM);

        if(emailFromSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_FROM);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailFromSettingsId=setting;
            }
        }

        Setting emailReplyToSettingsId = settingService.findBySetName(AdminSettingsConfiguration.MER_EMAIL_REPLY_TO);

        if(emailReplyToSettingsId==null){

            Setting setting=new Setting();
            setting.setSetName(AdminSettingsConfiguration.MER_EMAIL_REPLY_TO);
            setting.setSetDataType(SettingDataType.STRING);
            setting.setSetDefaultValue("");

            setting=settingService.saveSetting(setting);

            if(setting!=null){

                emailReplyToSettingsId=setting;
            }
        }

        MerchantSetting smsEnableSettings = MerchantSettingFixture.standardMerchantSetting();
        smsEnableSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        smsEnableSettings.setMesSettingId(smsEnableSettingsId.getSetId());
        smsEnableSettings.setSetDataType(SettingDataType.YES_NO);
        smsEnableSettings.setMesValue(IndicatorStatus.YES + "");
        smsEnableSettings = merchantSettingService.saveMerchantSetting(smsEnableSettings);
        settingSet.add(smsEnableSettings);

        MerchantSetting smsProviderSettings = MerchantSettingFixture.standardMerchantSetting();
        smsProviderSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        smsProviderSettings.setMesSettingId(smsProviderSettingsId.getSetId());
        smsProviderSettings.setSetDataType(SettingDataType.STRING);
        smsProviderSettings.setMesValue("Business SMS");
        smsProviderSettings = merchantSettingService.saveMerchantSetting(smsProviderSettings);
        settingSet.add(smsProviderSettings);

        MerchantSetting smsLinkStringSettings = MerchantSettingFixture.standardMerchantSetting();
        smsLinkStringSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        smsLinkStringSettings.setMesSettingId(smsLinkStringSettingsId.getSetId());
        smsLinkStringSettings.setSetDataType(SettingDataType.STRING);
        smsLinkStringSettings.setMesValue("http://www.businesssms.co.in/sms.aspx?ID=privilege@sobha.com&Pwd=sms@123&PhNo=#to&Text=#message");
        smsLinkStringSettings = merchantSettingService.saveMerchantSetting(smsLinkStringSettings);
        settingSet.add(smsLinkStringSettings);

        MerchantSetting smsCountryCodeSettings = MerchantSettingFixture.standardMerchantSetting();
        smsCountryCodeSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        smsCountryCodeSettings.setMesSettingId(smsCountryCodeSettingsId.getSetId());
        smsCountryCodeSettings.setSetDataType(SettingDataType.STRING);
        smsCountryCodeSettings.setMesValue("91");
        smsCountryCodeSettings = merchantSettingService.saveMerchantSetting(smsCountryCodeSettings);
        settingSet.add(smsCountryCodeSettings);

        MerchantSetting emailEnableSettings = MerchantSettingFixture.standardMerchantSetting();
        emailEnableSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailEnableSettings.setMesSettingId(emailEnableSettingsId.getSetId());
        emailEnableSettings.setSetDataType(SettingDataType.YES_NO);
        emailEnableSettings.setMesValue(IndicatorStatus.YES + "");
        emailEnableSettings = merchantSettingService.saveMerchantSetting(emailEnableSettings);
        settingSet.add(emailEnableSettings);

        MerchantSetting emailHostSetting = MerchantSettingFixture.standardMerchantSetting();
        emailHostSetting.setMesMerchantNo(merchant.getMerMerchantNo());
        emailHostSetting.setMesSettingId(emailHostSettingId.getSetId());
        emailHostSetting.setSetDataType(SettingDataType.STRING);
        emailHostSetting.setMesValue(emailHost);
        emailHostSetting = merchantSettingService.saveMerchantSetting(emailHostSetting);
        settingSet.add(emailHostSetting);

        MerchantSetting emailPortSettings = MerchantSettingFixture.standardMerchantSetting();
        emailPortSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailPortSettings.setMesSettingId(emailPortSettingsId.getSetId());
        emailPortSettings.setSetDataType(SettingDataType.STRING);
        emailPortSettings.setMesValue(emailPort);
        emailPortSettings = merchantSettingService.saveMerchantSetting(emailPortSettings);
        settingSet.add(emailPortSettings);

        MerchantSetting emailUserNameSettings = MerchantSettingFixture.standardMerchantSetting();
        emailUserNameSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailUserNameSettings.setMesSettingId(emailUserNameSettingsId.getSetId());
        emailUserNameSettings.setSetDataType(SettingDataType.STRING);
        emailUserNameSettings.setMesValue(emailUserName);
        emailUserNameSettings = merchantSettingService.saveMerchantSetting(emailUserNameSettings);
        settingSet.add(emailUserNameSettings);

        MerchantSetting emailPasswordSettings = MerchantSettingFixture.standardMerchantSetting();
        emailPasswordSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailPasswordSettings.setMesSettingId(emailPasswordSettingsId.getSetId());
        emailPasswordSettings.setSetDataType(SettingDataType.STRING);
        emailPasswordSettings.setMesValue(emailPassword);
        emailPasswordSettings = merchantSettingService.saveMerchantSetting(emailPasswordSettings);
        settingSet.add(emailPasswordSettings);

        MerchantSetting emailProtocolSettings = MerchantSettingFixture.standardMerchantSetting();
        emailProtocolSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailProtocolSettings.setMesSettingId(emailProtocolSettingsId.getSetId());
        emailProtocolSettings.setSetDataType(SettingDataType.STRING);
        emailProtocolSettings.setMesValue(emailProtocol);
        emailProtocolSettings = merchantSettingService.saveMerchantSetting(emailProtocolSettings);
        settingSet.add(emailProtocolSettings);

        MerchantSetting emailSocketFactorySettings = MerchantSettingFixture.standardMerchantSetting();
        emailSocketFactorySettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailSocketFactorySettings.setMesSettingId(emailSocketFactorySettingsId.getSetId());
        emailSocketFactorySettings.setSetDataType(SettingDataType.STRING);
        emailSocketFactorySettings.setMesValue(emailSMTPSocketFactoryClass);
        emailSocketFactorySettings = merchantSettingService.saveMerchantSetting(emailSocketFactorySettings);
        settingSet.add(emailSocketFactorySettings);

        MerchantSetting emailEnableSSLSettings = MerchantSettingFixture.standardMerchantSetting();
        emailEnableSSLSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailEnableSSLSettings.setMesSettingId(emailEnableSSLSettingsId.getSetId());
        emailEnableSSLSettings.setSetDataType(SettingDataType.STRING);
        emailEnableSSLSettings.setMesValue(emailEnableSSL);
        emailEnableSSLSettings = merchantSettingService.saveMerchantSetting(emailEnableSSLSettings);
        settingSet.add(emailEnableSSLSettings);

        MerchantSetting  emailDebugEnabledSettings= MerchantSettingFixture.standardMerchantSetting();
        emailDebugEnabledSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailDebugEnabledSettings.setMesSettingId(emailDebugEnabledSettingsId.getSetId());
        emailDebugEnabledSettings.setSetDataType(SettingDataType.STRING);
        emailDebugEnabledSettings.setMesValue(emailEnableDebug);
        emailDebugEnabledSettings = merchantSettingService.saveMerchantSetting(emailDebugEnabledSettings);
        settingSet.add(emailDebugEnabledSettings);

        MerchantSetting emailSmtpAuthSettings = MerchantSettingFixture.standardMerchantSetting();
        emailSmtpAuthSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailSmtpAuthSettings.setMesSettingId(emailSmtpAuthSettingsId.getSetId());
        emailSmtpAuthSettings.setSetDataType(SettingDataType.STRING);
        emailSmtpAuthSettings.setMesValue(emailSMTPAuth);
        emailSmtpAuthSettings = merchantSettingService.saveMerchantSetting(emailSmtpAuthSettings);
        settingSet.add(emailSmtpAuthSettings);

        MerchantSetting emailFromSettings = MerchantSettingFixture.standardMerchantSetting();
        emailFromSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailFromSettings.setMesSettingId(emailFromSettingsId.getSetId());
        emailFromSettings.setSetDataType(SettingDataType.STRING);
        emailFromSettings.setMesValue(emailFromEmail);
        emailFromSettings = merchantSettingService.saveMerchantSetting(emailFromSettings);
        settingSet.add(emailFromSettings);

        MerchantSetting emailReplyToSettings = MerchantSettingFixture.standardMerchantSetting();
        emailReplyToSettings.setMesMerchantNo(merchant.getMerMerchantNo());
        emailReplyToSettings.setMesSettingId(emailReplyToSettingsId.getSetId());
        emailReplyToSettings.setSetDataType(SettingDataType.STRING);
        emailReplyToSettings.setMesValue(emailReplyTo);
        emailReplyToSettings = merchantSettingService.saveMerchantSetting(emailReplyToSettings);
        settingSet.add(emailReplyToSettings);



        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel.setMsiMerchantNo(merchant.getMerMerchantNo());
        messageSpiel.setMsiName("TEST_SPIEL");
        messageSpiel = messageSpielService.saveMessageSpiel(messageSpiel);
        messageSpielSet.add(messageSpiel);

        SpielText emailspielText = SpielTextFixture.standardSpielText();
        emailspielText.setSptChannel(MessageSpielChannel.EMAIL);
        emailspielText.setSptDescription("<html><body><img src='cid:identifier1234'></br><b>THANKS For Registering #name</b></body></html>");
        emailspielText.setSptLocation(0L);
        emailspielText.setSptRef(messageSpiel.getMsiId());

        emailspielText = spielTextService.saveSpielText(emailspielText);

        spielTexts.add(emailspielText);

        SpielText smsspielText = SpielTextFixture.standardSpielText();
        smsspielText.setSptChannel(MessageSpielChannel.SMS);
        smsspielText.setSptDescription("testing #name");
        smsspielText.setSptLocation(0L);
        smsspielText.setSptRef(messageSpiel.getMsiId());

        smsspielText = spielTextService.saveSpielText(smsspielText);

        spielTexts.add(smsspielText);

        HashMap<String,String> emailContentParams = new HashMap<>(0);

        // Set the mobile
        emailContentParams.put("#name","FAYIZ");



        // Place the get notifications request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/userMessaging/sendNotification")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("channel", MessageSpielChannel.EMAIL + "")
                .param("to", "fayizkoyissan@customerinspire.com")
                .param("subject", "TESTING")
                .param("message", "Dear Customer,Welcome to Loyalty Program")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("transmitBulkNotification Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() throws InspireNetzException {



        for(MerchantSetting merchantSetting: settingSet) {

            merchantSettingService.deleteMerchantSetting(merchantSetting);
        }


        for(Merchant merchant: merchantSet) {

            merchantService.deleteMerchant(merchant.getMerMerchantNo());
        }

        spielTextService.deleteSpielTextSet(spielTexts);

        for(MessageSpiel messageSpiel : messageSpielSet){

            messageSpielService.deleteMessageSpiel(messageSpiel.getMsiId());

        }

    }

}