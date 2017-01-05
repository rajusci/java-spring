package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.core.repository.VendorRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class VendorRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(VendorRepositoryTest.class);

    @Autowired
    private VendorRepository vendorRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Vendor vendor = vendorRepository.save(VendorFixture.standardVendor());
        log.info(vendor.toString());
        Assert.assertNotNull(vendor.getVenId());

    }

    @Test
    public void test2Update() {

        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorRepository.save(vendor);
        log.info("Original Vendor " + vendor.toString());

        Vendor updatedVendor = VendorFixture.updatedStandardVendor(vendor);
        updatedVendor = vendorRepository.save(updatedVendor);
        log.info("Updated Vendor "+ updatedVendor.toString());

    }



    @Test
    public void test3FindByVenMerchantNo() {

        // Get the standard vendor
        Vendor vendor = VendorFixture.standardVendor();

        Page<Vendor> vendors = vendorRepository.findByVenMerchantNo(vendor.getVenMerchantNo(),constructPageSpecification(1));
        log.info("vendors by merchant no " + vendors.toString());
        Set<Vendor> vendorSet = Sets.newHashSet((Iterable<Vendor>)vendors);
        log.info("vendor list "+vendorSet.toString());

    }


    @Test
    public void test4FindByVenMerchantNoAndVenName() {

        // Get the standard vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendor = vendorRepository.save(vendor);


        // Get the vendor information
        Vendor searchVendor = vendorRepository.findByVenMerchantNoAndVenName(vendor.getVenMerchantNo(),vendor.getVenName());
        Assert.assertNotNull(searchVendor);
        log.info("Search Vendor : " + searchVendor.toString());

    }




    @Test
    public void test5FindByVenMerchantNoAndVenNameLike() {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendorRepository.save(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");

        // Check the vendor name
        Page<Vendor> vendors = vendorRepository.findByVenMerchantNoAndVenNameLike(vendor.getVenMerchantNo(),"%test%",constructPageSpecification(0));
        Assert.assertTrue(vendors.hasContent());
        Set<Vendor> vendorSet = Sets.newHashSet((Iterable<Vendor>)vendors);
        log.info("vendor list "+vendorSet.toString());


    }


    @Test
    public void test6FindByVenId() {

        // Create the vendor
        Vendor vendor = VendorFixture.standardVendor();
        vendorRepository.save(vendor);
        Assert.assertNotNull(vendor.getVenId());
        log.info("Vendor created");


        //  Get the vendor information for the id
        Vendor searchVendor = vendorRepository.findByVenId(vendor.getVenId());
        Assert.assertNotNull(searchVendor);
        log.info("Search Vendor : " + searchVendor.toString());

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
        return new Sort(Sort.Direction.ASC, "venName");
    }


}
