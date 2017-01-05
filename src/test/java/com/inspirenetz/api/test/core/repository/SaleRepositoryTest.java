package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.repository.SaleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SaleRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SaleRepositoryTest.class);

    @Autowired
    private SaleRepository saleRepository;

    Set<Sale> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Sale sale = saleRepository.save(SaleFixture.standardSale());

        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale);

        log.info(sale.toString());
        Assert.assertNotNull(sale.getSalId());

    }

    @Test
    public void test2Update() {

        Sale sale = SaleFixture.standardSale();
        sale = saleRepository.save(sale);

        // Add the created items to the tempset for removal on test completion
        tempSet.add(sale);

        log.info("Original Sale " + sale.toString());

        Sale updatedSale = SaleFixture.updatedStandardSale(sale);
        updatedSale = saleRepository.save(updatedSale);
        log.info("Updated Sale "+ updatedSale.toString());

    }



    @Test
    public void test3FindBySalMerchantNoAndSalDate() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        saleRepository.save(saleSet);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleRepository.findBySalMerchantNoAndSalDate(sale.getSalMerchantNo(),sale.getSalDate(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }


    @Test
    public void test4FindBySalMerchantNoAndSalDateBetween() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        saleRepository.save(saleSet);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleRepository.findBySalMerchantNoAndSalDateBetween(sale.getSalMerchantNo(), sale.getSalDate(),sale.getSalDate(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }


    @Test
    public void test5FindBySalMerchantNoAndSalLoyaltyId() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        saleRepository.save(saleSet);


        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleRepository.findBySalMerchantNoAndSalLoyaltyId(sale.getSalMerchantNo(), sale.getSalLoyaltyId(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }


    @Test
    public void test6FindBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        saleRepository.save(saleSet);


        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);


        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Page<Sale> sales = saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(sale.getSalMerchantNo(), sale.getSalLoyaltyId(), sale.getSalDate(), sale.getSalDate(), constructPageSpecification(0));
        Assert.assertTrue(sales.hasContent());
        log.info("sales by merchant no " + sales.toString());
        Set<Sale> saleSet1 = Sets.newHashSet((Iterable<Sale>)sales);
        log.info("sale list "+saleSet1.toString());

    }
 
    @Test
    public void test7FindBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalAmountAndSalPaymentReference() {

        Set<Sale> saleSet = SaleFixture.standardSales();
        saleRepository.save(saleSet);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        Sale sale1 = saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalAmountAndSalPaymentReference(sale.getSalMerchantNo(), sale.getSalLoyaltyId(),sale.getSalDate(),sale.getSalAmount(),sale.getSalPaymentReference());
        Assert.assertNotNull(sale1);
        log.info("sale entry " + sale1.toString()) ;

    }

    @Test
    public void test8findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference() {

        Set<Sale> saleSet = SaleFixture.standardSales();

        saleRepository.save(saleSet);

        // Add the created items to the tempset for removal on test completion
        tempSet.addAll(saleSet);

        // Get the standard sale
        Sale sale = SaleFixture.standardSale();

        List<Sale> fetchSale = saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(sale.getSalMerchantNo(), sale.getSalLoyaltyId(),sale.getSalDate(),sale.getSalPaymentReference());

        Assert.assertNotNull(fetchSale);

        log.info("sale entry " + fetchSale.toString()) ;

    }




    @After
    public void tearDown() {

        for(Sale sale : tempSet ) {

            saleRepository.delete(sale);

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
