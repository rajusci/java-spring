package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.core.repository.OrderCatalogueRepository;
import com.inspirenetz.api.core.service.OrderCatalogueService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class OrderCatalogueServiceTest {


    private static Logger log = LoggerFactory.getLogger(OrderCatalogueServiceTest.class);

    @Autowired
    private OrderCatalogueService orderCatalogueService;

    @Autowired
    private OrderCatalogueRepository orderCatalogueRepository;


    @Before
    public void setUp() {}



    @Test
    public void test1Create() throws InspireNetzException {


        OrderCatalogue orderCatalogue = orderCatalogueService.saveOrderCatalogue(OrderCatalogueFixture.standardOrderCatalogue());
        log.info(orderCatalogue.toString());
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogue = orderCatalogueService.saveOrderCatalogue(orderCatalogue);
        log.info("Original OrderCatalogue " + orderCatalogue.toString());

        OrderCatalogue updatedOrderCatalogue = OrderCatalogueFixture.updatedStandardOrderCatalogue(orderCatalogue);
        updatedOrderCatalogue = orderCatalogueService.saveOrderCatalogue(updatedOrderCatalogue);
        log.info("Updated OrderCatalogue "+ updatedOrderCatalogue.toString());

    }



    @Test
    public void test3FindByMerchantNo() {

        // Get the standard orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();

        Page<OrderCatalogue> orderCatalogues = orderCatalogueService.findByOrcMerchantNo(orderCatalogue.getOrcMerchantNo(),constructPageSpecification(1));
        log.info("orderCatalogues by merchant no " + orderCatalogues.toString());
        Set<OrderCatalogue> orderCatalogueSet = Sets.newHashSet((Iterable<OrderCatalogue>) orderCatalogues);
        log.info("orderCatalogue list "+orderCatalogueSet.toString());

    }



    @Test
    public void test4FindByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation()  throws InspireNetzException {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueService.saveOrderCatalogue(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        OrderCatalogue fetchOrderCatalogue = orderCatalogueService.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());
        Assert.assertNotNull(fetchOrderCatalogue);
        log.info("Fetched orderCatalogue info" + orderCatalogue.toString());

    }


    @Test
    public void test5SearchOrderCatalogues() throws InspireNetzException {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogueService.saveOrderCatalogue(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        // Check the orderCatalogue name
        Page<OrderCatalogue> orderCatalogues = orderCatalogueService.searchOrderCatalogues(orderCatalogue.getOrcMerchantNo(),orderCatalogue.getOrcAvailableLocation(),"code",orderCatalogue.getOrcProductCode(),constructPageSpecification(0));
        Assert.assertTrue(orderCatalogues.hasContent());
        Set<OrderCatalogue> orderCatalogueSet = Sets.newHashSet((Iterable<OrderCatalogue>)orderCatalogues);
        log.info("orderCatalogue list "+orderCatalogueSet.toString());


    }


    @Test
    public void test4IsOrderCatalogueCodeExisting() throws InspireNetzException {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogue = orderCatalogueService.saveOrderCatalogue(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        // Create a new orderCatalogue
        OrderCatalogue newOrderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        boolean exists = orderCatalogueService.isDuplicateOrderCatalogueExisting(newOrderCatalogue);
        Assert.assertTrue(exists);
        log.info("OrderCatalogue exists");


    }


    @Test
    public void test5DeleteOrderCatalogue() throws InspireNetzException {

        // Create the orderCatalogue
        OrderCatalogue orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();
        orderCatalogue = orderCatalogueService.saveOrderCatalogue(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");

        // call the delete orderCatalogue
        orderCatalogueService.deleteOrderCatalogue(orderCatalogue.getOrcProductNo());

        // Try searching for the orderCatalogue
        OrderCatalogue checkOrderCatalogue  = orderCatalogueService.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());

        Assert.assertNull(checkOrderCatalogue);

        log.info("orderCatalogue deleted");

    }





    @After
    public void tearDown() {

        Set<OrderCatalogue> orderCatalogues = OrderCatalogueFixture.standardOrderCatalogues();

        for(OrderCatalogue orderCatalogue: orderCatalogues) {

            OrderCatalogue delOrderCatalogue = orderCatalogueService.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());

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
