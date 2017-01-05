package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.dictionary.SaleResource;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.repository.SaleRepository;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.core.service.LoyaltyEngineService;
import com.inspirenetz.api.core.service.SaleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.SaleSKUResource;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.NotificationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.SaleFixture;
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

import java.sql.Date;
import java.util.*;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, NotificationTestConfig.class})
public class SaleServiceTest {


    private static Logger log = LoggerFactory.getLogger(SaleServiceTest.class);

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;
    

    Set<Sale> tempSet = new HashSet<>(0);



    @Before
    public void setUp() {}




    @Test
    public void test3FindBySalMerchantNoAndSalDate() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        List<Sale> saleList = Lists.newArrayList((Iterable<Sale>)saleSet);
        saleService.saveAll(saleList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleService.findBySalMerchantNoAndSalDate(sale.getSalMerchantNo(),sale.getSalDate(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }


    @Test
    public void test4FindBySalMerchantNoAndSalDateBetween() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        List<Sale> saleList = Lists.newArrayList((Iterable<Sale>) saleSet);
        saleService.saveAll(saleList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleService.findBySalMerchantNoAndSalDateBetween(sale.getSalMerchantNo(), sale.getSalDate(),sale.getSalDate(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }


    @Test
    public void test5FindBySalMerchantNoAndSalLoyaltyId() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        List<Sale> saleList = Lists.newArrayList((Iterable<Sale>) saleSet);
        saleService.saveAll(saleList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleService.findBySalMerchantNoAndSalLoyaltyId(sale.getSalMerchantNo(), sale.getSalLoyaltyId(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }


    @Test
    public void test6FindBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        List<Sale> saleList = Lists.newArrayList((Iterable<Sale>) saleSet);
        saleService.saveAll(saleList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleService.findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(sale.getSalMerchantNo(), sale.getSalLoyaltyId(), sale.getSalDate(), sale.getSalDate(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }

  
    @Test
    public void test7IsDuplicateSale() throws InspireNetzException {

        // Add the sale
        Sale sale1 = SaleFixture.standardSale();
        saleService.saveSale(sale1);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale1);

        Sale sale2 = SaleFixture.standardSale();
        boolean exists = saleService.isDuplicateSale(sale2.getSalMerchantNo(),sale2.getSalLoyaltyId(),sale2.getSalPaymentReference(),sale2.getSalDate(),sale2.getSalAmount());
        Assert.assertTrue(exists);
        log.info("sale already exists");

    }


    @Test
    public void test8SetExtFieldValue() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        Sale sale = SaleFixture.standardSale();
        saleService.setExtFieldValue(sale,attribute,"100.0");
        saleService.saveSale(sale);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale);

        log.info(sale.toString());
        Assert.assertNotNull(sale.getSalId());


        // Get the item information
        Sale searchItem = saleService.findBySalId(sale.getSalId());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

    }


    @Test
    public void test9GetExtFieldValue() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(12L);
        Assert.assertNotNull(attribute);

        Sale sale = SaleFixture.standardSale();
        saleService.setExtFieldValue(sale,attribute,"100.0");
        saleService.saveSale(sale);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale);

        log.info(sale.toString());
        Assert.assertNotNull(sale.getSalId());


        // Get the item information
        Sale searchItem = saleService.findBySalId(sale.getSalId());
        Assert.assertNotNull(searchItem);
        log.info("Retrived item " + searchItem.toString());

        // Get the value of the field set
        String value = saleService.getExtFieldValue(searchItem,attribute);
        Assert.assertNotNull(value);
        log.info("Attribute value : " + value );

    }



    @Test
    public void test10ToAttributeExtensionMap() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(14L);
        Assert.assertNotNull(attribute);

        Sale sale = SaleFixture.standardSale();
        saleService.setExtFieldValue(sale,attribute,"100.0");
        saleService.saveSale(sale);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale);

        log.info(sale.toString());
        Assert.assertNotNull(sale.getSalId());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = saleService.toAttributeExtensionMap(sale, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());

    }




    @Test
    public void test11FromAttributeExtensionMap() throws InspireNetzException {

        // Get the AttributeService
        Attribute attribute = attributeService.findByAtrId(12L);
        Assert.assertNotNull(attribute);

        Sale sale = SaleFixture.standardSale();
        saleService.setExtFieldValue(sale,attribute,"100.0");
        saleService.saveSale(sale);


        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale);

        log.info(sale.toString());
        Assert.assertNotNull(sale.getSalId());


        // Get the attribute extension map
        AttributeExtendedEntityMap entityMap = saleService.toAttributeExtensionMap(sale, AttributeExtensionMapType.ALL);
        Assert.assertNotNull(entityMap);
        Assert.assertTrue(!entityMap.isEmpty());
        log.info("ExtnsionMap :  " + entityMap.toString());


        // Create a new entity
        Sale newItem = new Sale();
        newItem = (Sale) saleService.fromAttributeExtensionMap(newItem,entityMap,AttributeExtensionMapType.ALL);
        Assert.assertNotNull(newItem);;
        log.info("New object from map : " + newItem.toString());

    }

    @Test
    public void test12findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference() {

        Set<Sale> saleSet = SaleFixture.standardSales();

        List<Sale> saleList = Lists.newArrayList((Iterable<Sale>)saleSet);

        saleService.saveAll(saleList);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        List<Sale> fetchedSales = saleService.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(sale.getSalMerchantNo(),sale.getSalLoyaltyId(),sale.getSalDate(), sale.getSalPaymentReference());

        Assert.assertNotNull(fetchedSales);

        log.info("sale list "+fetchedSales.toString());

    }

    @Test
    public void test12UpdateSales() throws InspireNetzException{

        try{
        Sale sale = SaleFixture.standardSale();

        saleService.saveSale(sale);

        tempSet.add(sale);

        loyaltyEngineService.processTransaction(sale);

        sale.setSalAmount(200.0);

        List<Sale> saleList=new ArrayList<Sale>();

        saleList.add(sale);

        saleService.UpdateSales(saleList,"audit");



        }catch (InspireNetzException ex){

            log.info("sale list "+ "exception generated"+ex);
        }

        log.info("sale list "+"updated successfully");

    }

    @Test
    public void test13SendSalesEBill()throws InspireNetzException{

        try{

            CustomerResource customerResource=new CustomerResource();
            customerResource.setCusFName("FAYIZ");
            customerResource.setCusLName("KOYISSAN");
            customerResource.setCusMobile("8867987369");
            customerResource.setCusEmail("fayizmuhamed@gmail.com");

            SaleSKUResource saleSKUResource=new SaleSKUResource();

            List<SaleSKUResource> saleSKUResources=new ArrayList<SaleSKUResource>();

            saleSKUResource.setPrdName("PEPPER SALT");
            saleSKUResource.setSsuPrice(300);
            saleSKUResource.setSsuQty(2);
            saleSKUResources.add(saleSKUResource);

            com.inspirenetz.api.rest.resource.SaleResource saleResource=new com.inspirenetz.api.rest.resource.SaleResource();
            saleResource.setSalAmount(1000.00);
            java.util.Date today = new java.util.Date();
            saleResource.setSalDate(new Date(today.getTime()));
            saleResource.setSalMerchantNo(1L);
            saleResource.setSalPaymentReference("BILL0001");

            saleResource.setSaleSKUResourceList(saleSKUResources);

            HashMap<String,Object> params=new HashMap<String,Object>();
            params.put("flightNo","SG0001");
            params.put("flightDate","25/04/2016");
            params.put("seatNo","1C");
            params.put("pnr","123000023");


            saleService.sendSalesEBill(customerResource,saleResource,params);




        }catch (InspireNetzException ex){

            log.info("Send Sales E-BILL :"+"exception generated"+ex);
        }

        log.info("Send Sales E-BILL :"+"E-BILL send successfully" );

    }


    @After
    public void tearDown() {

        for(Sale sale : tempSet ) {

            saleService.deleteSale(sale.getSalId());

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
        return new Sort(Sort.Direction.ASC, "salDate");
    }


}
