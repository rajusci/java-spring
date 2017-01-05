package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.repository.LinkedLoyaltyRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import com.inspirenetz.api.core.repository.LinkedLoyaltyRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.core.service.PrimaryLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
import com.inspirenetz.api.test.core.fixture.PrimaryLoyaltyFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class LinkedLoyaltyServiceTest {


    private static Logger log = LoggerFactory.getLogger(LinkedLoyaltyServiceTest.class);

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;



    Set<LinkedLoyalty> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<PrimaryLoyalty> pLoyaltySet = new HashSet<>(0);


    @Before
    public void setup() {}



    @Test
    public void test1Create(){


        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(LinkedLoyaltyFixture.standardLinkedLoyalty());

        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        log.info(linkedLoyalty.toString());
        Assert.assertNotNull(linkedLoyalty.getLilId());

    }

    @Test
    public void test2Update() {

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());

        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        LinkedLoyalty updatedLinkedLoyalty = LinkedLoyaltyFixture.updatedStandardLinkedLoyalty(linkedLoyalty);
        updatedLinkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(updatedLinkedLoyalty);
        log.info("Updated LinkedLoyalty "+ updatedLinkedLoyalty.toString());

    }


    public void test3FindByLilId() {

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());


        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        // Get the data
        LinkedLoyalty searchLoyalty = linkedLoyaltyService.findByLilId(linkedLoyalty.getLilId());
        Assert.assertNotNull(searchLoyalty);
        Assert.assertTrue(linkedLoyalty.getLilId().longValue() ==  searchLoyalty.getLilId().longValue());;
        log.info("Searched LinkedLoyalty : " + searchLoyalty.toString());


    }

    @Test
    public void test4FindByLilChildCustomerNo(){

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());


        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        // Get the data
        LinkedLoyalty searchLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(linkedLoyalty.getLilChildCustomerNo());
        Assert.assertNotNull(searchLoyalty);
        Assert.assertTrue(linkedLoyalty.getLilId().longValue() ==  searchLoyalty.getLilId().longValue());;
        log.info("Searched LinkedLoyalty : " + searchLoyalty.toString());

    }



    @Test
    public void test5FindByLilParentCustomerNo(){


        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());


        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        // Get the data
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(linkedLoyalty.getLilParentCustomerNo());
        Assert.assertNotNull(linkedLoyaltyList);
        log.info("Searched LinkedLoyalty : " + linkedLoyaltyList.toString());




    }


    @Test
    public void test5LinkCustomers() {

        // Get the customerset
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList =Lists.newArrayList((Iterable) customers);
        customerService.saveAll(customerList);

        // Add the set to customerSet
        customerSet.addAll(customerList);


        // call the link customers
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.linkCustomers(customerList.get(0),customerList.get(1));
        Assert.assertNotNull(linkedLoyalty);;

        tempSet.add(linkedLoyalty);



    }

    @Test
    public void test6getLinkedAccountsofPrimary() {

        //saving customer data
        Set<Customer> customers  = CustomerFixture.standardCustomers();
        List<Customer> customerList =  Lists.newArrayList((Iterable<Customer>)customers);
        customerList=customerService.saveAll(customerList);
        customerSet.addAll(customerList);
        log.info("customers saved");


        // saving linked loyalty for test
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilParentCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);
        tempSet.add(linkedLoyalty);


        //saving primary loyalty data
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllCustomerNo(customerList.get(0).getCusCustomerNo());
        primaryLoyalty.setPllLoyaltyId(customerList.get(0).getCusLoyaltyId());
        primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);
        pLoyaltySet.add(primaryLoyalty);

        log.info("linked loyalties saved with parent customer no"+customerList.get(0).getCusCustomerNo());
        log.info("primary loyalty cusNo"+primaryLoyalty.getPllCustomerNo());


        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findLinkedAccounts(customerList.get(0).getCusLoyaltyId(),1L);
        Assert.assertTrue(linkedLoyaltyList.size()>0);
        log.info("LinkedLoyaltyList : " + linkedLoyaltyList);


    }

    @Test
    public void test6getMyLinkedAccount() {

        //saving customer data
        Set<Customer> customers  = CustomerFixture.standardCustomers();
        List<Customer> customerList =  Lists.newArrayList((Iterable<Customer>)customers);
        customerList=customerService.saveAll(customerList);
        customerSet.addAll(customerList);
        log.info("customers saved");

        // saving linked loyalty for test
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilParentCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);
        tempSet.add(linkedLoyalty);


        //saving primary loyalty data
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllCustomerNo(customerList.get(1).getCusCustomerNo());
        primaryLoyalty.setPllLoyaltyId(customerList.get(1).getCusLoyaltyId());
        primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);
        pLoyaltySet.add(primaryLoyalty);

        log.info("linked loyalties saved with parent customer no"+customerList.get(1).getCusCustomerNo());
        log.info("primary loyalty cusNo"+primaryLoyalty.getPllCustomerNo());


        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findLinkedAccounts(customerList.get(1).getCusLoyaltyId(),1L);
        Assert.assertTrue(linkedLoyaltyList.size()>0);
        log.info("LinkedLoyaltyList : " + linkedLoyaltyList.toString());

        // Add the set to customerSet
        customerSet.addAll(customerList);

    }



    @After
    public void tearDown() throws InspireNetzException {

        for(LinkedLoyalty linkedLoyalty : tempSet ) {

            linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

        }


        for(Customer customer : customerSet ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(PrimaryLoyalty primaryLoyalty : pLoyaltySet ) {

            primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());

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
        return new Sort(Sort.Direction.ASC, "brnName");
    }

}
