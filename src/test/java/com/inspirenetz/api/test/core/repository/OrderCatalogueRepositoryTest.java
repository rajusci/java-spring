package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.core.repository.OrderCatalogueRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.OrderCatalogueFixture;
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
public class OrderCatalogueRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(OrderCatalogueRepositoryTest.class);

    @Autowired
    private OrderCatalogueRepository orderCatalogueRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        OrderCatalogue orderCatalogue = orderCatalogueRepository.save(OrderCatalogueFixture.standardOrderCatalogue());
        log.info(orderCatalogue.toString());
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());

    }

    @Test
    public void test2Update() {

        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogue = orderCatalogueRepository.save(orderCatalogue);
        log.info("Original OrderCatalogue " + orderCatalogue.toString());

        OrderCatalogue updatedOrderCatalogue = OrderCatalogueFixture.updatedStandardOrderCatalogue(orderCatalogue);
        updatedOrderCatalogue = orderCatalogueRepository.save(updatedOrderCatalogue);
        log.info("Updated OrderCatalogue "+ updatedOrderCatalogue.toString());

    }



    @Test
    public void test3FindByOrcMerchantNo() {

        // Get the standard orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();

        Page<OrderCatalogue> orderCatalogues = orderCatalogueRepository.findByOrcMerchantNo(orderCatalogue.getOrcMerchantNo(),constructPageSpecification(1));
        log.info("orderCatalogues by merchant no " + orderCatalogues.toString());
        Set<OrderCatalogue> orderCatalogueSet = Sets.newHashSet((Iterable<OrderCatalogue>)orderCatalogues);
        log.info("orderCatalogue list "+orderCatalogueSet.toString());

    }

    @Test
    public void test4FindByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation() {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueRepository.save(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        OrderCatalogue fetchOrderCatalogue = orderCatalogueRepository.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());
        Assert.assertNotNull(fetchOrderCatalogue);
        log.info("Fetched orderCatalogue info" + orderCatalogue.toString());

    }


    @Test
    public void test5FindByOrcMerchantNoAndOrcAvailableLocation() {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueRepository.save(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        // Check the orderCatalogue name
        Page<OrderCatalogue> orderCatalogues = orderCatalogueRepository.findByOrcMerchantNoAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcAvailableLocation(), constructPageSpecification(0));
        Assert.assertTrue(orderCatalogues.hasContent());
        Set<OrderCatalogue> orderCatalogueSet = Sets.newHashSet((Iterable<OrderCatalogue>)orderCatalogues);
        log.info("orderCatalogue list "+orderCatalogueSet.toString());


    }


    @Test
    public void test6FindByOrcMerchantNoAndOrcAvailableLocationAndOrcProductCodeLike() {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueRepository.save(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        // Check the orderCatalogue name
        Page<OrderCatalogue> orderCatalogues = orderCatalogueRepository.findByOrcMerchantNoAndOrcAvailableLocationAndOrcProductCodeLike(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcAvailableLocation(), "%test%", constructPageSpecification(0));
        Assert.assertTrue(orderCatalogues.hasContent());
        Set<OrderCatalogue> orderCatalogueSet = Sets.newHashSet((Iterable<OrderCatalogue>)orderCatalogues);
        log.info("orderCatalogue list "+orderCatalogueSet.toString());


    }


    @Test
    public void test7FindByOrcMerchantNoAndOrcAvailableLocationAndOrcDescriptionLike() {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueRepository.save(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        // Check the orderCatalogue name
        Page<OrderCatalogue> orderCatalogues = orderCatalogueRepository.findByOrcMerchantNoAndOrcAvailableLocationAndOrcDescriptionLike(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcAvailableLocation(), "%test%", constructPageSpecification(0));
        Assert.assertTrue(orderCatalogues.hasContent());
        Set<OrderCatalogue> orderCatalogueSet = Sets.newHashSet((Iterable<OrderCatalogue>)orderCatalogues);
        log.info("orderCatalogue list "+orderCatalogueSet.toString());

    }


    @Test
    public void test8FindByOrcProductNo() {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueRepository.save(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        OrderCatalogue fetchOrderCatalogue = orderCatalogueRepository.findByOrcProductNo(orderCatalogue.getOrcProductNo());
        Assert.assertNotNull(fetchOrderCatalogue);
        log.info("Fetched orderCatalogue info" + orderCatalogue.toString());

    }
       
    
    @After
    public void tearDown() {

        Set<OrderCatalogue> orderCatalogues = OrderCatalogueFixture.standardOrderCatalogues();

        for(OrderCatalogue orderCatalogue: orderCatalogues) {

            OrderCatalogue delOrderCatalogue = orderCatalogueRepository.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());

            if ( delOrderCatalogue != null ) {
                orderCatalogueRepository.delete(delOrderCatalogue);
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
        return new Sort(Sort.Direction.ASC, "orcProductNo");
    }


}
