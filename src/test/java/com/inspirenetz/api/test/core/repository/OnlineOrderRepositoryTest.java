package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.core.repository.OnlineOrderRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.OnlineOrderFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class OnlineOrderRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(OnlineOrderRepositoryTest.class);

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        OnlineOrder onlineOrder = onlineOrderRepository.save(OnlineOrderFixture.standardOnlineOrder());
        log.info(onlineOrder.toString());
        Assert.assertNotNull(onlineOrder.getOrdId());

    }

    @Test
    public void test2Update() {

        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrder = onlineOrderRepository.save(onlineOrder);
        log.info("Original OnlineOrder " + onlineOrder.toString());

        OnlineOrder updatedOnlineOrder = OnlineOrderFixture.updatedStandardOnlineOrder(onlineOrder);
        updatedOnlineOrder = onlineOrderRepository.save(updatedOnlineOrder);
        log.info("Updated OnlineOrder "+ updatedOnlineOrder.toString());

    }


    @Test
    public void test3FindByOrdId() {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderRepository.save(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        OnlineOrder fetchOnlineOrder = onlineOrderRepository.findByOrdId(onlineOrder.getOrdId());
        Assert.assertNotNull(fetchOnlineOrder);
        log.info("Fetched onlineOrder info" + onlineOrder.toString());

    }


    @Test
    public void test4FindByOrdMerchantNoAndOrdUniqueBatchTrackingId() {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderRepository.save(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        List<OnlineOrder> onlineOrderList = onlineOrderRepository.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(onlineOrder.getOrdMerchantNo(),onlineOrder.getOrdUniqueBatchTrackingId());
        Assert.assertNotNull(onlineOrderList);;
        Assert.assertTrue(!onlineOrderList.isEmpty());
        log.info("OnlineOrder List : " + onlineOrderList.toString());
    }


    @Test
    public void test5SearchOnlineOrders() {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderRepository.save(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        Page<OnlineOrder> onlineOrderPage = onlineOrderRepository.searchOnlineOrders(onlineOrder.getOrdMerchantNo(),onlineOrder.getOrdOrderLocation(),constructPageSpecification(0));
        Assert.assertNotNull(onlineOrderPage);;
        Assert.assertTrue(onlineOrderPage.hasContent());
        List<OnlineOrder> onlineOrderList = Lists.newArrayList((Iterable<OnlineOrder>)onlineOrderPage);

        // Get the online order list
        log.info("OnlineOrder List : " + onlineOrderList.toString());

    }



    @Test
    public void test6SearchOnlineOrdersByTrackingId() {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderRepository.save(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        Page<OnlineOrder> onlineOrderPage = onlineOrderRepository.searchOnlineOrdersByTrackingId(onlineOrder.getOrdMerchantNo(), onlineOrder.getOrdOrderLocation(), onlineOrder.getOrdOrderSlot(), onlineOrder.getOrdStatus(), onlineOrder.getOrdUniqueBatchTrackingId(), constructPageSpecification(0));
        Assert.assertNotNull(onlineOrderPage);;
        Assert.assertTrue(onlineOrderPage.hasContent());
        List<OnlineOrder> onlineOrderList = Lists.newArrayList((Iterable<OnlineOrder>)onlineOrderPage);

        // Get the online order list
        log.info("OnlineOrder List : " + onlineOrderList.toString());

    }


    @Test
    public void test8SearchOnlineOrdersByLoyaltyId() {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderRepository.save(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        Page<OnlineOrder> onlineOrderPage = onlineOrderRepository.searchOnlineOrdersByLoyaltyId(onlineOrder.getOrdMerchantNo(), onlineOrder.getOrdOrderLocation(), onlineOrder.getOrdOrderSlot(), onlineOrder.getOrdStatus(), onlineOrder.getOrdLoyaltyId(), constructPageSpecification(0));
        Assert.assertNotNull(onlineOrderPage);;
        Assert.assertTrue(onlineOrderPage.hasContent());
        List<OnlineOrder> onlineOrderList = Lists.newArrayList((Iterable<OnlineOrder>)onlineOrderPage);

        // Get the online order list
        log.info("OnlineOrder List : " + onlineOrderList.toString());

    }


    @Test
    public void test9SearchOnlineOrdersByProductCode() {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderRepository.save(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        Page<OnlineOrder> onlineOrderPage = onlineOrderRepository.searchOnlineOrdersByProductCode(onlineOrder.getOrdMerchantNo(), onlineOrder.getOrdOrderLocation(), onlineOrder.getOrdOrderSlot(), onlineOrder.getOrdStatus(), onlineOrder.getOrdProductCode(), constructPageSpecification(0));
        Assert.assertNotNull(onlineOrderPage);;
        Assert.assertTrue(onlineOrderPage.hasContent());
        List<OnlineOrder> onlineOrderList = Lists.newArrayList((Iterable<OnlineOrder>)onlineOrderPage);

        // Get the online order list
        log.info("OnlineOrder List : " + onlineOrderList.toString());
    
    }
    

    @After
    public void tearDown() {

        Set<OnlineOrder> onlineOrders = OnlineOrderFixture.standardOnlineOrders();

        for(OnlineOrder onlineOrder: onlineOrders) {

            List<OnlineOrder> onlineOrderList = onlineOrderRepository.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(onlineOrder.getOrdMerchantNo(),onlineOrder.getOrdUniqueBatchTrackingId());

            for(OnlineOrder onlineOrder1 : onlineOrderList ) {

                onlineOrderRepository.delete(onlineOrder1);
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
        return new Sort(Sort.Direction.ASC, "ordId");
    }


}
