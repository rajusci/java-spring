package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.core.repository.OrderSlotRepository;
import com.inspirenetz.api.core.service.OrderSlotService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.OrderSlotFixture;
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
public class OrderSlotServiceTest {


    private static Logger log = LoggerFactory.getLogger(OrderSlotServiceTest.class);

    @Autowired
    private OrderSlotService orderSlotService;

    @Autowired
    private OrderSlotRepository orderSlotRepository;


    @Before
    public void setUp() {}

    @Test
    public void test1Create() throws InspireNetzException {


        OrderSlot orderSlot = orderSlotService.saveOrderSlot(OrderSlotFixture.standardOrderSlot());
        log.info(orderSlot.toString());
        Assert.assertNotNull(orderSlot.getOrtId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlot = orderSlotService.saveOrderSlot(orderSlot);
        log.info("Original OrderSlot " + orderSlot.toString());

        OrderSlot updatedOrderSlot = OrderSlotFixture.standardUpdatedOrderSlot(orderSlot);
        updatedOrderSlot = orderSlotService.saveOrderSlot(updatedOrderSlot);
        log.info("Updated OrderSlot "+ updatedOrderSlot.toString());

    }


    @Test
    public void test3FindByMerchantNo() {

        // Get the standard orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();

        Page<OrderSlot> orderSlots = orderSlotService.findByOrtMerchantNo(orderSlot.getOrtMerchantNo(),constructPageSpecification(1));
        log.info("orderSlots by merchant no " + orderSlots.toString());
        Set<OrderSlot> orderSlotSet = Sets.newHashSet((Iterable<OrderSlot>) orderSlots);
        log.info("orderSlot list "+orderSlotSet.toString());

    }



    @Test
    public void test4FindByOrtId() throws InspireNetzException {

        // Create the orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlotService.saveOrderSlot(orderSlot);
        Assert.assertNotNull(orderSlot.getOrtId());
        log.info("OrderSlot created");

        OrderSlot fetchOrderSlot = orderSlotService.findByOrtId(orderSlot.getOrtId());
        Assert.assertNotNull(fetchOrderSlot);
        log.info("Fetched orderSlot info" + orderSlot.toString());

    }


    @Test
    public void test5FindByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime() throws InspireNetzException {

        // Create the orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlotService.saveOrderSlot(orderSlot);
        Assert.assertNotNull(orderSlot.getOrtId());
        log.info("OrderSlot created");

        OrderSlot fetchOrderSlot = orderSlotService.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), orderSlot.getOrtStartingTime());
        Assert.assertNotNull(fetchOrderSlot);
        log.info("Fetched orderSlot info" + orderSlot.toString());


    }


    @Test
    public void test6FindByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionOrderByOrtStartingTime() {

        // Get the standard orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();

        Page<OrderSlot> orderSlots = orderSlotService.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionOrderByOrtStartingTime(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), constructPageSpecification(1));
        log.info("orderSlots by merchant no " + orderSlots.toString());
        Set<OrderSlot> orderSlotSet = Sets.newHashSet((Iterable<OrderSlot>)orderSlots);
        log.info("orderSlot list "+orderSlotSet.toString());

    }


    @Test
    public void test7IsOrderSlotCodeExisting() throws InspireNetzException {

        // Create the orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlot = orderSlotService.saveOrderSlot(orderSlot);
        Assert.assertNotNull(orderSlot.getOrtId());
        log.info("OrderSlot created");

        // Create a new orderSlot
        OrderSlot newOrderSlot = OrderSlotFixture.standardOrderSlot();
        boolean exists = orderSlotService.isDuplicateOrderSlotExisting(newOrderSlot);
        Assert.assertTrue(exists);
        log.info("OrderSlot exists");


    }


    @Test
    public void test8DeleteOrderSlot() throws InspireNetzException {

        // Create the orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlotService.saveOrderSlot(orderSlot);
        Assert.assertNotNull(orderSlot.getOrtId());
        log.info("OrderSlot created");

        // call the delete orderSlot
        orderSlotService.deleteOrderSlot(orderSlot.getOrtId());

        // Try searching for the orderSlot
        OrderSlot checkOrderSlot  = orderSlotService.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), orderSlot.getOrtStartingTime());
        Assert.assertNull(checkOrderSlot);
        log.info("orderSlot deleted");

    }





    @After
    public void tearDown() {

        Set<OrderSlot> orderSlots = OrderSlotFixture.standardOrderSlots();

        for(OrderSlot orderSlot: orderSlots) {

            OrderSlot delOrderSlot = orderSlotRepository.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), orderSlot.getOrtStartingTime());

            if ( delOrderSlot != null ) {
                orderSlotRepository.delete(delOrderSlot);
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
        return new Sort(Sort.Direction.ASC, "ortId");
    }

}
