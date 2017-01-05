package com.inspirenetz.api.test.core.repository;


import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.core.repository.MessageSpielRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.BrandFixture;
import com.inspirenetz.api.test.core.fixture.MessageSpielFixture;
import org.aspectj.lang.annotation.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 8/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MessageSpielRepositoryTest {


    private static Logger log = LoggerFactory.getLogger(MessageSpielRepositoryTest.class);

    Set<MessageSpiel> tempSet = new HashSet<>(0);

    @Autowired
    private MessageSpielRepository messageSpielRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        MessageSpiel messageSpiel =MessageSpielFixture.standardMessageSpiel();

        MessageSpiel messageSpiel1 = messageSpielRepository.save(messageSpiel);
        log.info(messageSpiel.toString());
        Assert.assertNotNull(messageSpiel.getMsiId());

        // Add to tempSet
        tempSet.add(messageSpiel);

    }

    @Test
    public void test2Update() {

        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel = messageSpielRepository.save(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        // Add to tempSet
        tempSet.add(messageSpiel);

        MessageSpiel updateMessagespiel = MessageSpielFixture.updatedStandMessageSpiel(messageSpiel);
        updateMessagespiel = messageSpielRepository.save(updateMessagespiel);
        log.info("Updated messageSpiel "+ updateMessagespiel.toString());

    }

    @Test
    public void test3findByMsiId() {

        // Get the standard brand
        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel = messageSpielRepository.save(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        // Add to tempSet
        tempSet.add(messageSpiel);

        Assert.assertNotNull(messageSpiel.getMsiId());

        MessageSpiel messageSpielById = messageSpielRepository.findByMsiId(messageSpiel.getMsiId());
        Assert.assertNotNull(messageSpielById);
        log.info("Fetched messageSpielById info" + messageSpielById.toString());


    }

    @Test
    public void test4findByMsiFunctionCode() {

        // Get the standard brand
//        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
//        messageSpiel = messageSpielRepository.save(messageSpiel);
//        log.info("Original messgae spiel " + messageSpiel.toString());
//
//        Assert.assertNotNull(messageSpiel.getMsiId());
//
//        // Add to tempSet
//        tempSet.add(messageSpiel);
//
//        List<MessageSpiel> messageSpielByFunctioncode = messageSpielRepository.findByMsiFunctionCode(messageSpiel.getMsiFunctionCode());
//        Assert.assertNotNull(messageSpielByFunctioncode);
//        log.info("Fetched message function code  info" + messageSpielByFunctioncode.toString());


    }


    @Test
    public void test5findByMsiName() {

        // Get the standard brand
        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();
        messageSpiel.setMsiMerchantNo(9L);
        messageSpiel = messageSpielRepository.save(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        Assert.assertNotNull(messageSpiel.getMsiId());

        // Add to tempSet
        tempSet.add(messageSpiel);

        MessageSpiel messageSpielByMsiName = messageSpielRepository.findByMsiNameAndMsiMerchantNo(messageSpiel.getMsiName(),messageSpiel.getMsiMerchantNo());
        Assert.assertNotNull(messageSpielByMsiName);
        log.info("Fetched messageSpielByMsiName info" + messageSpielByMsiName.toString());


    }

    @Test
    public void test6findByMsiNameLike() {

        // Get the standard brand
        Set<MessageSpiel> messageSpiel = MessageSpielFixture.standardMessageSpiels();
        messageSpielRepository.save(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        // Add to tempSet
        tempSet.addAll(messageSpiel);

        Page<MessageSpiel> messageSpielfindByMsiNameLike = messageSpielRepository.findByMsiMerchantNoAndMsiNameLike(1L,"%test%",constructPageSpecification(0));
        Assert.assertNotNull(messageSpielfindByMsiNameLike);
        log.info("Fetched message findByMsiNameLike" + messageSpielfindByMsiNameLike.toString());


    }


    @Test
    public void test7findByAll() {

        // Get the standard brand
        Set<MessageSpiel> messageSpiel = MessageSpielFixture.standardMessageSpiels();
        messageSpielRepository.save(messageSpiel);
        log.info("Original messgae spiel " + messageSpiel.toString());

        // Add to tempSet
        tempSet.addAll(messageSpiel);

        Page<MessageSpiel> messageSpielfindByMsiNameLike = messageSpielRepository.findAll(constructPageSpecification(0));
        Assert.assertNotNull(messageSpielfindByMsiNameLike);
        log.info("Fetched message find all method" + messageSpielfindByMsiNameLike.getContent().toString());


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
        return new Sort(Sort.Direction.ASC, "msiName");
    }


//    @org.junit.After
//    public void tearDown() {
//
//        for ( MessageSpiel messageSpiel : tempSet ) {
//
//            messageSpielRepository.delete(messageSpiel);
//
//        }
//    }

}
