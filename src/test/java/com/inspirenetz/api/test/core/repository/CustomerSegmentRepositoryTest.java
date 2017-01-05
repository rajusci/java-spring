package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.repository.CustomerSegmentRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerSegmentFixture;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerSegmentRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerSegmentRepositoryTest.class);

    @Autowired
    private CustomerSegmentRepository customerSegmentRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CustomerSegment customerSegment = customerSegmentRepository.save(CustomerSegmentFixture.standardCustomerSegment());
        log.info(customerSegment.toString());
        Assert.assertNotNull(customerSegment.getCsgSegmentId());

    }

    @Test
    public void test2Update() {

        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegment = customerSegmentRepository.save(customerSegment);
        log.info("Original CustomerSegment " + customerSegment.toString());

        CustomerSegment updatedCustomerSegment = CustomerSegmentFixture.updatedStandardCustomerSegment(customerSegment);
        updatedCustomerSegment = customerSegmentRepository.save(updatedCustomerSegment);
        log.info("Updated CustomerSegment "+ updatedCustomerSegment.toString());

    }



    @Test
    public void test3FindByCsgMerchantNo() {

        // Get the standard customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();

        Page<CustomerSegment> customerSegments = customerSegmentRepository.findByCsgMerchantNo(customerSegment.getCsgMerchantNo(),constructPageSpecification(0));
        log.info("customerSegments by merchant no " + customerSegments.toString());
        Set<CustomerSegment> customerSegmentSet = Sets.newHashSet((Iterable<CustomerSegment>)customerSegments);
        log.info("customerSegment list "+customerSegmentSet.toString());

    }

    @Test
    public void test4FindByCsgMerchantNoAndCsgSegmentName() {

        // Create the customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegmentRepository.save(customerSegment);
        Assert.assertNotNull(customerSegment.getCsgSegmentId());
        log.info("CustomerSegment created");

        CustomerSegment fetchCustomerSegment = customerSegmentRepository.findByCsgMerchantNoAndCsgSegmentName(customerSegment.getCsgMerchantNo(),customerSegment.getCsgSegmentName());
        Assert.assertNotNull(fetchCustomerSegment);
        log.info("Fetched customerSegment info" + customerSegment.toString());

    }


    @Test
    public void test5FindByCsgMerchantNoAndCsgSegmentNameLike() {

        // Create the customerSegment
        CustomerSegment customerSegment = CustomerSegmentFixture.standardCustomerSegment();
        customerSegmentRepository.save(customerSegment);
        Assert.assertNotNull(customerSegment.getCsgSegmentId());
        log.info("CustomerSegment created");

        // Check the customerSegment name
        Page<CustomerSegment> customerSegments = customerSegmentRepository.findByCsgMerchantNoAndCsgSegmentNameLike(customerSegment.getCsgMerchantNo(),"%dynamic%",constructPageSpecification(0));
        Assert.assertTrue(customerSegments.hasContent());
        Set<CustomerSegment> customerSegmentSet = Sets.newHashSet((Iterable<CustomerSegment>)customerSegments);
        log.info("customerSegment list "+customerSegmentSet.toString());


    }

    @After
    public void tearDown() {

        Set<CustomerSegment> customerSegments = CustomerSegmentFixture.standardCustomerSegments();

        for(CustomerSegment customerSegment: customerSegments) {

            CustomerSegment delCustomerSegment = customerSegmentRepository.findByCsgMerchantNoAndCsgSegmentName(customerSegment.getCsgMerchantNo(),customerSegment.getCsgSegmentName());

            if ( delCustomerSegment != null ) {
                customerSegmentRepository.delete(delCustomerSegment);
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
        return new Sort(Sort.Direction.ASC, "csgSegmentName");
    }


}
