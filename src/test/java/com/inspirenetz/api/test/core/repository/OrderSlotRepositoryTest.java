package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.core.repository.OrderSlotRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class OrderSlotRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(OrderSlotRepositoryTest.class);

    @Autowired
    private OrderSlotRepository orderSlotRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        OrderSlot orderSlot = orderSlotRepository.save(OrderSlotFixture.standardOrderSlot());
        log.info(orderSlot.toString());
        Assert.assertNotNull(orderSlot.getOrtId());

    }

    @Test
    public void test2Update() {

        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlot = orderSlotRepository.save(orderSlot);
        log.info("Original OrderSlot " + orderSlot.toString());

        OrderSlot updatedOrderSlot = OrderSlotFixture.standardUpdatedOrderSlot(orderSlot);
        updatedOrderSlot = orderSlotRepository.save(updatedOrderSlot);
        log.info("Updated OrderSlot "+ updatedOrderSlot.toString());

    }



    @Test
    public void test3FindByOrtMerchantNo() {

        // Get the standard orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();

        Page<OrderSlot> orderSlots = orderSlotRepository.findByOrtMerchantNo(orderSlot.getOrtMerchantNo(),constructPageSpecification(1));
        log.info("orderSlots by merchant no " + orderSlots.toString());
        Set<OrderSlot> orderSlotSet = Sets.newHashSet((Iterable<OrderSlot>)orderSlots);
        log.info("orderSlot list "+orderSlotSet.toString());

    }

    @Test
    public void test4FindByOrtId() {

        // Create the orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlotRepository.save(orderSlot);
        Assert.assertNotNull(orderSlot.getOrtId());
        log.info("OrderSlot created");

        OrderSlot fetchOrderSlot = orderSlotRepository.findByOrtId(orderSlot.getOrtId());
        Assert.assertNotNull(fetchOrderSlot);
        log.info("Fetched orderSlot info" + orderSlot.toString());

    }


    @Test
    public void test5FindByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime() {

        // Create the orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();
        orderSlotRepository.save(orderSlot);
        Assert.assertNotNull(orderSlot.getOrtId());
        log.info("OrderSlot created");

        OrderSlot fetchOrderSlot = orderSlotRepository.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), orderSlot.getOrtStartingTime());
        Assert.assertNotNull(fetchOrderSlot);
        log.info("Fetched orderSlot info" + orderSlot.toString());


    }


    @Test
    public void test6FindByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionOrderByOrtStartingTime() {

        // Get the standard orderSlot
        OrderSlot orderSlot = OrderSlotFixture.standardOrderSlot();

        Page<OrderSlot> orderSlots = orderSlotRepository.searchOrderSlots(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), constructPageSpecification(1));
        log.info("orderSlots by merchant no " + orderSlots.toString());
        Set<OrderSlot> orderSlotSet = Sets.newHashSet((Iterable<OrderSlot>)orderSlots);
        log.info("orderSlot list "+orderSlotSet.toString());

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
