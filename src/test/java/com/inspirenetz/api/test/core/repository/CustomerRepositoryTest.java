package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.repository.CustomerRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Customer customer = customerRepository.save(CustomerFixture.standardCustomer());
        log.info(customer.toString());
        Assert.assertNotNull(customer.getCusCustomerNo());

    }

    @Test
    public void test2Update() {

        Customer customer = CustomerFixture.standardCustomer();
        customer = customerRepository.save(customer);
        log.info("Original Customer " + customer.toString());

        Customer updatedCustomer = CustomerFixture.updatedStandardCustomer(customer);
        updatedCustomer = customerRepository.save(updatedCustomer);
        log.info("Updated Customer "+ updatedCustomer.toString());

    }



    @Test
    public void test3FindByCusMerchantNo() {

        // Get the standard customer
        Customer customer = CustomerFixture.standardCustomer();

        Page<Customer> customers = customerRepository.findByCusMerchantNo(customer.getCusMerchantNo(),constructPageSpecification(1));
        log.info("customers by merchant no " + customers.toString());
        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>)customers);
        log.info("customer list "+customerSet.toString());

    }

    @Test
    public void test4FindByCusLoyaltyIdAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerRepository.save(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        Customer fetchCustomer = customerRepository.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(), customer.getCusMerchantNo());
        Assert.assertNotNull(fetchCustomer);
        log.info("Fetched customer info" + customer.toString());

    }



    @Test
    public void test5findByCusMobileAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerRepository.save(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        Customer fetchCustomer = customerRepository.findByCusMobileAndCusMerchantNo(customer.getCusMobile(), customer.getCusMerchantNo());
        Assert.assertNotNull(fetchCustomer);
        log.info("Fetched customer info" + customer.toString());

    }



    @Test
    public void test6FindByCusEmailAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerRepository.save(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        Customer fetchCustomer = customerRepository.findByCusEmailAndCusMerchantNo(customer.getCusEmail(), customer.getCusMerchantNo());
        Assert.assertNotNull(fetchCustomer);
        log.info("Fetched customer info" + customer.toString());

    }



    @Test
    public void test7FindByCusMerchantNo() {

        // Create the Customers
        Set<Customer> customers = CustomerFixture.standardCustomers();
        customerRepository.save(customers);
        log.info("Customers created");

        // Get the customer
        Customer customer = CustomerFixture.standardCustomer();


        Page<Customer> customerList = customerRepository.findByCusMerchantNo(customer.getCusMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(customerList);
        log.info("Fetched customer info" + customers.toString());

    }


    @Test
    public void test8FindByCusFirstNameLikeAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerRepository.save(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        // Check the customer name
        Page<Customer> customers = customerRepository.findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatus("%sandeep%",customer.getCusMerchantNo(), IndicatorStatus.YES,constructPageSpecification(0));
        Assert.assertTrue(customers.hasContent());
        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>)customers);
        log.info("customer list "+customerSet.toString());


    }



    @Test
    public void test9FindByCusLastNameLikeAndCusMerchantNo() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerRepository.save(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        // Check the customer name
        Page<Customer> customers = customerRepository.findByCusLNameLikeAndCusMerchantNo("%g%",customer.getCusMerchantNo(), constructPageSpecification(0));
        Assert.assertTrue(customers.hasContent());
        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>)customers);
        log.info("customer list "+customerSet.toString());


    }


    @Test
    public void test10findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusMerchantNo(1L);

        customer.setCusLoyaltyId("9946693048");

        customer.setCusRegisterStatus(null);
        //Save customer
        customerRepository.save(customer);

        Assert.assertNotNull(customer.getCusCustomerNo());

        log.info("Customer created");

        // Fetch customer with CusMerchantNo,CusLoyaltyId,cusEmail,cusMobile
        List<Customer> customers = customerRepository.findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(customer.getCusMerchantNo(), customer.getCusLoyaltyId(), customer.getCusEmail(), customer.getCusMobile());

        Assert.assertTrue(!customers.isEmpty());

        Set<Customer> customerSet = Sets.newHashSet((Iterable<Customer>) customers);

        log.info("Customer list "+customerSet.toString());


    }

    @Test
    public void test7FindByCusUserNoAndCusStatus() {

        // Create the Customers
        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer:customers){

            customer.setCusUserNo(11111L);

            customerRepository.save(customer);
        }

        log.info("Customers created");

        List<Customer> customerList = customerRepository.findByCusUserNoAndCusStatus(11111L, CustomerStatus.ACTIVE);

        Assert.assertNotNull(customerList);

        log.info("Fetched customer info" + customerList.toString());

    }

    @Test
    public void searchCustomer() {

        // Create the Customers

       Customer customer2 =new Customer();

       customer2.setCusLoyaltyId("234455555");
       customer2.setCusMobile("9000000");
       customer2.setCusMerchantNo(20L);
       customer2.setCusRegisterStatus(IndicatorStatus.YES);
       customer2.setCusLocation(0L);
       customer2.setCusFName("Test1");

        Customer customer3 =new Customer();

        customer3.setCusLoyaltyId("2344255555");
        customer3.setCusMobile("90000010");
        customer3.setCusMerchantNo(20L);
        customer3.setCusRegisterStatus(IndicatorStatus.YES);
        customer3.setCusLocation(5L);
        customer3.setCusFName("Test1");

       Customer customer1 =new Customer();
       customer1.setCusLoyaltyId("2222222221");
       customer1.setCusMobile("2222222221");
       customer1.setCusLocation(3L);
       customer1.setCusMerchantNo(20L);

       customer1.setCusFName("Test1");
       customer1.setCusRegisterStatus(IndicatorStatus.YES);

       //save customer information


        customer1 =customerRepository.save(customer1);

        customer2=customerRepository.save(customer2);
        customer3 =customerRepository.save(customer3);

        try {

            Page<Customer> customerPages = customerRepository.findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocationOrCusLocation(customer1.getCusFName(), customer1.getCusMerchantNo(), customer1.getCusRegisterStatus().intValue(), 0L, customer1.getCusLocation(), constructPageSpecification(0));

            Assert.assertNotNull(customerPages);

            log.info("Fetched customer info" + customerPages.toString());

            deleteCustomer(customer1);

            deleteCustomer(customer2);

            deleteCustomer(customer3);


        }catch (Exception e){

            deleteCustomer(customer1);

            deleteCustomer(customer2);

            deleteCustomer(customer3);
        }



    }

    private void deleteCustomer(Customer customer1) {

        customerRepository.delete(customer1);
    }


    @After
    public void tearDown() {

        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: customers) {

            Customer delCustomer = customerRepository.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            if ( delCustomer != null ) {
                customerRepository.delete(delCustomer);
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
        return new Sort(Sort.Direction.ASC, "cusLoyaltyId");
    }


}
