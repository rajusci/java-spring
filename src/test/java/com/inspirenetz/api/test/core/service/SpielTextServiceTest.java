package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.core.service.SpielTextService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.MessageSpielFixture;
import com.inspirenetz.api.test.core.fixture.SpielTextFixture;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alameen on 3/2/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})

public class SpielTextServiceTest {

    private static Logger log = LoggerFactory.getLogger(SettingServiceTest.class);

    Set<SpielText> tempSet =new HashSet<>(0);

    Set<Customer> tempSet1 =new HashSet<>(0);

    Set<MessageSpiel> tempSet2 =new HashSet<>(0);

    @Autowired
    private SpielTextService spielTextService;

    @Autowired
    private MessageSpielService messageSpielService;

    @Autowired
    private CustomerService customerService;

    @Before
    public void setUp() {}
    
    @Test
    public void test1Create()throws InspireNetzException {


        SpielText spielText = spielTextService.saveSpielText(SpielTextFixture.standardSpielText());

        tempSet.add(spielText);

        log.info(spielText.toString());
        Assert.assertNotNull(spielText.getSptId());



    }

    @Test
    public void test2Update() throws InspireNetzException {

        SpielText spielText = SpielTextFixture.standardSpielText();
        spielText = spielTextService.saveSpielText(spielText);
        log.info("Original SpielText " + spielText.toString());

        SpielText updatedSpielText = SpielTextFixture.updatedStandardSpielText(spielText);
        updatedSpielText = spielTextService.saveSpielText(updatedSpielText);
        log.info("Updated SpielText "+ updatedSpielText.toString());

    }



    @Test
    public void findBySptRef() throws InspireNetzException {

        // Get the standard spielText
        SpielText spielText = SpielTextFixture.standardSpielText();
        spielText.setSptRef(1L);

        spielTextService.saveSpielText(spielText);

        List spielTextList = spielTextService.findBySptRef(spielText.getSptRef());
        log.info("spielText data "+spielTextList.toString());

    }




    @Test
    public void getSpielText() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusLocation(1L);
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");
        tempSet1.add(customer);

        // Create the spielText
        MessageSpiel messageSpiel = MessageSpielFixture.standardMessageSpiel();

        Set<SpielText> childSet =new HashSet<>(0);

        SpielText spielText =new SpielText();

        spielText.setSptChannel(1);

        spielText.setSptLocation(customer.getCusLocation());

        spielText.setSptDescription("hi we are testing");

        childSet.add(spielText);

        messageSpiel.setSpielTexts(childSet);

        messageSpielService.saveMessageSpiel(messageSpiel);

        tempSet2.add(messageSpiel);

        SpielText spielText1 = spielTextService.getSpielText(customer, messageSpiel.getMsiName(), 1);

        Assert.assertNotNull(spielText1.getSptDescription());

        log.info("SpielContent Is:"+spielText1.getSptDescription());

        // Check the spielText name


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
        return new Sort(Sort.Direction.ASC, "merSpielTextName");
    }


    @After
    public void tearDown() throws InspireNetzException {


        spielTextService.deleteSpielTextSet(tempSet);

        for ( MessageSpiel messageSpiel : tempSet2 ) {

            messageSpielService.deleteMessageSpiel(messageSpiel.getMsiId());

        }

        for(Customer customer:tempSet1){

            customerService.deleteCustomer(customer.getCusCustomerNo());
        }
    }
}
