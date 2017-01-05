package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.core.service.BrandService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.inspirenetz.api.test.core.fixture.BrandFixture;

import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class BrandServiceTest {


    private static Logger log = LoggerFactory.getLogger(BrandServiceTest.class);

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private GeneralUtils generalUtils;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }



    @Test
    public void test1FindByMerchantNo() {

        // Get the standard brand
        Brand brand = BrandFixture.standardBrand();

        Page<Brand> brands = brandService.findByBrnMerchantNo(brand.getBrnMerchantNo(),constructPageSpecification(1));
        log.info("brands by merchant no " + brands.toString());
        Set<Brand> brandSet = Sets.newHashSet((Iterable<Brand>) brands);
        log.info("brand list "+brandSet.toString());

    }



    @Test
    public void test2FindByBrnMerchantNoAndBrnCode() throws InspireNetzException{

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brandService.saveBrand(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        Brand fetchBrand = brandService.findByBrnMerchantNoAndBrnCode(brand.getBrnMerchantNo(),brand.getBrnCode());
        Assert.assertNotNull(fetchBrand);
        log.info("Fetched brand info" + brand.toString());

    }



    @Test
    public void test3FindByBrnMerchantNoAndBrnNameLike() throws InspireNetzException{

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brandService.saveBrand(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        // Check the brand name
        Page<Brand> brands = brandService.searchBrands("code","BRN",brand.getBrnMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(brands.hasContent());
        Set<Brand> brandSet = Sets.newHashSet((Iterable<Brand>)brands);
        log.info("brand list "+brandSet.toString());


    }


    @Test
    public void test4IsBrandCodeExisting() throws InspireNetzException{

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brand = brandService.saveBrand(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        // Create a new brand
        Brand newBrand = BrandFixture.standardBrand();
        boolean exists = brandService.isBrandCodeDuplicateExisting(newBrand);
        Assert.assertTrue(exists);
        log.info("Brand exists");


    }


    @Test
    public void test5DeleteBrand() throws InspireNetzException {

        // Create the brand
        Brand brand = BrandFixture.standardBrand();
        brand = brandService.saveBrand(brand);
        Assert.assertNotNull(brand.getBrnId());
        log.info("Brand created");

        // call the delete brand
        brandService.deleteBrand(brand.getBrnId());

        // Try searching for the brand
        Brand checkBrand  = brandService.findByBrnMerchantNoAndBrnCode(brand.getBrnMerchantNo(),brand.getBrnCode());

        Assert.assertNull(checkBrand);

        log.info("brand deleted");

    }


    @Test
    public void generateUniqueId() {

        String uniqueId = generalUtils.getUniqueId("9538828853");
        log.info("Unique id is  : " + uniqueId);


    }


    @Test
    public void getFormattedValue() {

        String formatted = generalUtils.getFormattedValue(1243.43);
        log.info("Formatted value si : " + formatted);

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
