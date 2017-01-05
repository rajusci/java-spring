package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.MessageSpielValue;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.core.repository.MessageSpielRepository;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MessageSpielFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 8/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})
public class MessageSpielServiceTest {

    private static Logger log = LoggerFactory.getLogger(MessageSpielServiceTest.class);

    Set<MessageSpiel> tempSet = new HashSet<>(0);

    @Autowired
    private MessageSpielService messageSpielService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

    @Test
    public void test1Create(){


        MessageSpiel messageSpiel = messageSpielService.saveMessageSpiel(MessageSpielFixture.standardMessageSpiel());

        log.info(messageSpiel.toString());

        Assert.assertNotNull(messageSpiel.getMsiId());

        // Add to tempSet
        tempSet.add(messageSpiel);

    }

    @Test
    public void test2Update() {

        Set<SpielText> childSet =new HashSet<>(0);

        SpielText spielText =new SpielText();

        spielText.setSptChannel(2);

        spielText.setSptLocation(2L);

        childSet.add(spielText);

        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();

        messageSpiel.setSpielTexts(childSet);

        messageSpiel = messageSpielService.saveMessageSpiel(messageSpiel);
        log.info(" original data for save  " + messageSpiel.toString());

        // Add to tempSet
        tempSet.add(messageSpiel);

        MessageSpiel updateMessagespiel = MessageSpielFixture.updatedStandMessageSpiel(messageSpiel);
        updateMessagespiel.setMsiName("checking");


        updateMessagespiel = messageSpielService.saveMessageSpiel(updateMessagespiel);
        log.info("Updated messageSpiel "+ updateMessagespiel.toString());

    }

    @Test
    public void test3findByMsiId() {

        // Get the standard brand
        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel = messageSpielService.saveMessageSpiel(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        // Add to tempSet
        tempSet.add(messageSpiel);

        Assert.assertNotNull(messageSpiel.getMsiId());

        MessageSpiel messageSpielById = messageSpielService.findByMsiId(messageSpiel.getMsiId());
        Assert.assertNotNull(messageSpielById);
        log.info("Fetched messageSpielById info" + messageSpielById.toString());


    }




    @Test
    public void test5findByMsiName() {

        // Get the standard brand
        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel = messageSpielService.saveMessageSpiel(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        Assert.assertNotNull(messageSpiel.getMsiId());

        // Add to tempSet
        tempSet.add(messageSpiel);

        MessageSpiel messageSpielByMsiName = messageSpielService.findByMsiName(MessageSpielValue.LOYALTY_MIN_ALREADY_REGISTERED);
        Assert.assertNotNull(messageSpielByMsiName);
        log.info("Fetched messageSpielByMsiName info" + messageSpielByMsiName.toString());


    }

    @Test
    public void test6findByMsiNameLike() {

        // Get the standard brand
        Set<MessageSpiel> messageSpiel = MessageSpielFixture.standardMessageSpiels();
        List<MessageSpiel> messageSpielList = Lists.newArrayList((Iterable<MessageSpiel>) messageSpiel);
        messageSpielService.saveAll(messageSpielList);
        log.info("Original messgae spiel " + messageSpiel.toString());

        // Add to tempSet
        tempSet.addAll(messageSpiel);

        Page<MessageSpiel> messageSpielfindByMsiNameLike = messageSpielService.searchMessageSpiel("name","%test%",constructPageSpecification(0));
        Assert.assertNotNull(messageSpielfindByMsiNameLike);
        log.info("Fetched message findByMsiNameLike" + messageSpielfindByMsiNameLike.toString());


    }


    @Test
    public void test7deleteMessageSpiel() {

        // Get the standard brand
        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel = messageSpielService.saveMessageSpiel(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());


        // Add to tempSet
        tempSet.add(messageSpiel);
        boolean isdeleted=messageSpielService.deleteMessageSpiel(messageSpiel.getMsiId());
        Assert.assertTrue(isdeleted);
        log.info("Fetched message findByMsiNameLike" + isdeleted);


        MessageSpiel searchMessageSpiel = messageSpielService.findByMsiId(messageSpiel.getMsiId());
        Assert.assertNull(searchMessageSpiel);
        log.info("MessageSpiel Deleted");

        // Remove from tempSet
        tempSet.remove(messageSpiel);

    }

    @Test
    public void test7isDuplicateMessageSpielExisting() throws InspireNetzException {

        // Get the standard brand
        Set<SpielText> childSet =new HashSet<>(0);

        SpielText spielText =new SpielText();

        spielText.setSptChannel(2);

        spielText.setSptLocation(2L);

        childSet.add(spielText);

        SpielText spielText1=new SpielText();

        spielText1.setSptChannel(2);

        spielText1.setSptLocation(2L);

        childSet.add(spielText1);

        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();

        messageSpiel.setSpielTexts(childSet);

        messageSpiel = messageSpielService.validateAndSaveMessageSpiel(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());



        // Add to tempSet
        tempSet.add(messageSpiel);

        MessageSpiel messageSpiel1 = MessageSpielFixture.standardMessageSpiel();

        messageSpiel.setMsiName("testing");

        messageSpiel.setSpielTexts(childSet);

        boolean isexisting=messageSpielService.isDuplicateMessageSpielExisting(messageSpiel1);
        Assert.assertTrue(isexisting);
        log.info("Fetched message messageSpielService" + isexisting);




    }


    @Test
    public void getSpiel(){

        MessageSpiel mesageMessageSpiel = messageSpielService.findByMsiNameAndMsiMerchantNo("CUSTOMER_REGISTRATION_OTP", 1L);

        log.info("Message"+mesageMessageSpiel);

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
        return new Sort(Sort.Direction.ASC, "msiName");
    }


    @org.junit.After
    public void tearDown() {

        for ( MessageSpiel messageSpiel : tempSet ) {

            messageSpielService.deleteMessageSpiel(messageSpiel.getMsiId());

        }
    }

}
