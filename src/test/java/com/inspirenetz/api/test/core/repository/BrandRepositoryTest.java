package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
import com.inspirenetz.api.test.core.fixture.BrandFixture;

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class BrandRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(BrandRepositoryTest.class);

    @Autowired
    private BrandRepository brandRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Brand brand = brandRepository.save(BrandFixture.standardBrand());
        log.info(brand.toString());
        Assert.assertNotNull(brand.getBrnId());

    }

    @Test
    public void test2Update() {

        Brand brand = BrandFixture.standardBrand();
        brand = brandRepository.save(brand);
        log.info("Original Brand " + brand.toString());

        Brand updatedBrand = BrandFixture.updatedStandardBrand(brand);
        updatedBrand = brandRepository.save(updatedBrand);
        log.info("Updated Brand "+ updatedBrand.toString());

    }



    @Test
    public void test3FindByBrnMerchantNo() {

        // Get the standard brand
        Brand brand = BrandFixture.standardBrand();

        Page<Brand> brands = brandRepository.findByBrnMerchantNo(brand.getBrnMerchantNo(),constructPageSpecification(1));
        log.info("brands by merchant no " + brands.toString());
        Set<Brand> brandSet = Sets.newHashSet((Iterable<Brand>)brands);
        log.info("brand list "+brandSet.toString());

    }

    @Test
    public void test4FindByBrnMerchantNoAndBrnCode() {

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brandRepository.save(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        Brand fetchBrand = brandRepository.findByBrnMerchantNoAndBrnCode(brand.getBrnMerchantNo(),brand.getBrnCode());
        Assert.assertNotNull(fetchBrand);
        log.info("Fetched brand info" + brand.toString());

    }


    @Test
    public void test5FindByBrnMerchantNoAndBrnNameLike() {

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brandRepository.save(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        // Check the brand name
        Page<Brand> brands = brandRepository.findByBrnMerchantNoAndBrnNameLike(brand.getBrnMerchantNo(),"%pep%",constructPageSpecification(0));
        Assert.assertTrue(brands.hasContent());
        Set<Brand> brandSet = Sets.newHashSet((Iterable<Brand>)brands);
        log.info("brand list "+brandSet.toString());


    }

    @Test
    public void test6FindByBrnMerchantNoAndBrnCodeLike() {

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brandRepository.save(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        // Check the brand name
        Page<Brand> brands = brandRepository.findByBrnMerchantNoAndBrnCodeLike(brand.getBrnMerchantNo(),"%BRN%",constructPageSpecification(0));
        Assert.assertTrue(brands.hasContent());
        Set<Brand> brandSet = Sets.newHashSet((Iterable<Brand>)brands);
        log.info("brand list "+brandSet.toString());


    }

    @After
    public void tearDown() {

        Set<Brand> brands = BrandFixture.standardBrands();

        for(Brand brand: brands) {

            Brand delBrand = brandRepository.findByBrnMerchantNoAndBrnCode(brand.getBrnMerchantNo(),brand.getBrnCode());

            if ( delBrand != null ) {
                brandRepository.delete(delBrand);
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
        return new Sort(Sort.Direction.ASC, "brnName");
    }


}
