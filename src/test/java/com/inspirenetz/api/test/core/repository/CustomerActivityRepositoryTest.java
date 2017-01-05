package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.dictionary.RecordStatus;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.repository.CustomerActivityRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerActivityFixture;
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
public class CustomerActivityRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerActivityRepositoryTest.class);

    @Autowired
    private CustomerActivityRepository customerActivityRepository;

    Set<CustomerActivity> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityRepository.save(customerActivityFixture.standardCustomerActivity());

        // Add to the tempSet
        tempSet.add(customerActivity);

        log.info(customerActivity.toString());
        Assert.assertNotNull(customerActivity.getCuaId());

    }

    @Test
    public void test2Update() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());

        // Add to the tempSet
        tempSet.add(customerActivity);


        CustomerActivity updatedCustomerActivity = CustomerActivityFixture.updatedStandardCustomerActivity(customerActivity);
        updatedCustomerActivity = customerActivityRepository.save(updatedCustomerActivity);
        log.info("Updated CustomerActivity "+ updatedCustomerActivity.toString());

    }

    @Test
    public void test3FindByCuaId() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        CustomerActivity searchcustomerActivity = customerActivityRepository.findByCuaId(customerActivity.getCuaId());
        Assert.assertNotNull(searchcustomerActivity);
        Assert.assertTrue(customerActivity.getCuaId().longValue() ==  searchcustomerActivity.getCuaId().longValue());;
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }


    @Test
    public void test3FindByCuaMerchantNo() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaMerchantNoAndCuaRecordStatus(customerActivity.getCuaMerchantNo(), RecordStatus.RECORD_STATUS_ACTIVE, constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByCuaMerchantNoAndLoyaltyId() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        List<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatus(customerActivity.getCuaMerchantNo(),customerActivity.getCuaLoyaltyId(), RecordStatus.RECORD_STATUS_ACTIVE);
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test3SearchActivities() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.searchActivities("0", 0, customerActivity.getCuaDate(), customerActivity.getCuaDate(), 1L, RecordStatus.RECORD_STATUS_ACTIVE,constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByCuaMerchantNoRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaMerchantNoAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaMerchantNo(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByCuaMerchantNoLoyaltyIdRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaMerchantNoAndCuaLoyaltyIdAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaMerchantNo(),customerActivity.getCuaLoyaltyId(),customerActivity.getCuaActivityType(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaMerchantNo(),customerActivity.getCuaLoyaltyId(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6findByCuaMerchantNoAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaMerchantNoAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaMerchantNo(),customerActivity.getCuaActivityType(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaRecordStatusAndCuaDateBetween(RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByLoyaltyIdRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaLoyaltyIdAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaLoyaltyId(),customerActivity.getCuaActivityType(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6FindByCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaLoyaltyId(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @Test
    public void test6findByCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween() {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityRepository.save(customerActivity);
        log.info("Original CustomerActivity " + customerActivity.toString());


        // Add to the tempSet
        tempSet.add(customerActivity);

        // Get the data
        Page<CustomerActivity> searchcustomerActivity = customerActivityRepository.findByCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(customerActivity.getCuaActivityType(),RecordStatus.RECORD_STATUS_ACTIVE,customerActivity.getCuaDate(),customerActivity.getCuaDate(),constructPageSpecification(0));
        Assert.assertNotNull(searchcustomerActivity);
        log.info("Searched CustomerActivity : " + searchcustomerActivity.toString());


    }

    @After
    public void tearDown() {

        for(CustomerActivity customerActivity : tempSet ) {

            customerActivityRepository.delete(customerActivity);

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
