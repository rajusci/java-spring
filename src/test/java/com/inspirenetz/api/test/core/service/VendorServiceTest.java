package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.core.repository.VendorRepository;
import com.inspirenetz.api.core.service.VendorService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.VendorFixture;
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
public class VendorServiceTest {


    private static Logger log = LoggerFactory.getLogger(VendorServiceTest.class);

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorRepository vendorRepository;


    @Before
    public void setUp() {}


    @Test
    public void test1Create() throws InspireNetzException {


        Vendor vendor = vendorService.saveVendor(VendorFixture.standardVendor());
        log.info(vendor.toString());
        Assert.assertNotNull(vendor.getVenId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorService.saveVendor(vendor);
        log.info("Original Vendor " + vendor.toString());

        Vendor updatedVendor = VendorFixture.updatedStandardVendor(vendor);
        updatedVendor = vendorService.saveVendor(updatedVendor);
        log.info("Updated Vendor "+ updatedVendor.toString());

    }
      


    @Test
    public void test3SearchVendors() throws InspireNetzException {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorService.saveVendor(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");

        // Get the venodr list
        Page<Vendor> vendorPage = vendorService.searchVendors(vendor.getVenMerchantNo(),"0","0",constructPageSpecification(0));
        Assert.assertNotNull(vendorPage);
        Assert.assertTrue(vendorPage.hasContent());
        List<Vendor> vendorList = Lists.newArrayList((Iterable<Vendor>)vendorPage);
        log.info("Vendor list " + vendorList.toString());

    }



    @Test
    public void test4IsVendorCodeExisting() throws InspireNetzException {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorService.saveVendor(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");

        // Create a new vendor
        Vendor newVendor = VendorFixture.standardVendor();
        boolean exists = vendorService.isDuplicateVendorExisting(newVendor);
        Assert.assertTrue(exists);
        log.info("Vendor exists");


    }


    @Test
    public void test5FindByVenMerchantNoAndVenName() throws InspireNetzException {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorService.saveVendor(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");


        Vendor searchVendor = vendorService.findByVenMerchantNoAndVenName(vendor.getVenMerchantNo(),vendor.getVenName());
        Assert.assertNotNull(searchVendor);
        log.info("Vendor INformation : " + searchVendor.toString());

    }


    @Test
    public void test6FindByVenId() throws InspireNetzException {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorService.saveVendor(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");


        Vendor searchVendor = vendorService.findByVenId(vendor.getVenId());
        Assert.assertNotNull(searchVendor);
        log.info("Vendor INformation : " + searchVendor.toString());

    }


    @Test
    public void test7DeleteVendor() throws InspireNetzException {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorService.saveVendor(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");

        // call the delete vendor
        vendorService.deleteVendor(vendor.getVenId());

        // Try searching for the vendor
        Vendor checkVendor  = vendorService.findByVenMerchantNoAndVenName(vendor.getVenMerchantNo(), vendor.getVenName());
        Assert.assertNull(checkVendor);
        log.info("vendor deleted");

    }





    @After
    public void tearDown() {

        Set<Vendor> vendors = VendorFixture.standardVendors();

        for(Vendor vendor: vendors) {

            Vendor delVendor = vendorRepository.findByVenMerchantNoAndVenName(vendor.getVenMerchantNo(), vendor.getVenName());

            if ( delVendor != null ) {
                vendorRepository.delete(delVendor);
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
        return new Sort(Sort.Direction.ASC, "venId");
    }

}
