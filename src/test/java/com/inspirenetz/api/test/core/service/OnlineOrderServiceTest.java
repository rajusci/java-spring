package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.core.repository.OnlineOrderRepository;
import com.inspirenetz.api.core.service.OnlineOrderService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class OnlineOrderServiceTest {


    private static Logger log = LoggerFactory.getLogger(OnlineOrderServiceTest.class);

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;


    @Before
    public void setUp() {}



    @Test
    public void test1Create() throws InspireNetzException {

        OnlineOrder onlineOrder = onlineOrderService.saveOnlineOrder(OnlineOrderFixture.standardOnlineOrder());
        log.info(onlineOrder.toString());
        Assert.assertNotNull(onlineOrder.getOrdId());

    }



    @Test
    public void test2Update() throws InspireNetzException {

        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);
        log.info("Original OnlineOrder " + onlineOrder.toString());

        OnlineOrder updatedOnlineOrder = OnlineOrderFixture.updatedStandardOnlineOrder(onlineOrder);
        updatedOnlineOrder = onlineOrderService.saveOnlineOrder(updatedOnlineOrder);
        log.info("Updated OnlineOrder "+ updatedOnlineOrder.toString());

    }



    @Test
    public void test3FindByOrdId() throws InspireNetzException {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderService.saveOnlineOrder(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        OnlineOrder fetchOnlineOrder = onlineOrderService.findByOrdId(onlineOrder.getOrdId());
        Assert.assertNotNull(fetchOnlineOrder);
        log.info("Fetched onlineOrder info" + onlineOrder.toString());

    }


    @Test
    public void test4FindByOrdMerchantNoAndOrdUniqueBatchTrackingId() throws InspireNetzException {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderService.saveOnlineOrder(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        List<OnlineOrder> onlineOrderList = onlineOrderService.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(onlineOrder.getOrdMerchantNo(),onlineOrder.getOrdUniqueBatchTrackingId());
        Assert.assertNotNull(onlineOrderList);;
        Assert.assertTrue(!onlineOrderList.isEmpty());
        log.info("OnlineOrder List : " + onlineOrderList.toString());
    }


    @Test
    public void test5SearchOnlineOrders() throws InspireNetzException {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrderService.saveOnlineOrder(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // Get the list of orders for  given tracking id
        Page<OnlineOrder> onlineOrderPage = onlineOrderService.searchOnlineOrders(onlineOrder.getOrdMerchantNo(),onlineOrder.getOrdOrderLocation(),onlineOrder.getOrdOrderSlot(),onlineOrder.getOrdStatus(),"0","0",constructPageSpecification(0));
        Assert.assertNotNull(onlineOrderPage);;
        Assert.assertTrue(onlineOrderPage.hasContent());
        List<OnlineOrder> onlineOrderList = Lists.newArrayList((Iterable<OnlineOrder>) onlineOrderPage);

        // Get the online order list
        log.info("OnlineOrder List : " + onlineOrderList.toString());

    }


    @Test
    public void test5DeleteOnlineOrder() throws InspireNetzException {

        // Create the onlineOrder
        OnlineOrder onlineOrder = OnlineOrderFixture.standardOnlineOrder();
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");

        // call the delete onlineOrder
        onlineOrderService.deleteOnlineOrder(onlineOrder.getOrdId());

        // Try searching for the onlineOrder
        OnlineOrder checkOnlineOrder  = onlineOrderService.findByOrdId(onlineOrder.getOrdId());
        Assert.assertNull(checkOnlineOrder);
        log.info("onlineOrder deleted");

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
