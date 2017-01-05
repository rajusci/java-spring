package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.repository.MerchantRepository;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantFixture;
import com.inspirenetz.api.test.core.fixture.MerchantLocationFixture;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantServiceTest.class);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantRepository merchantRepository;


    @Before
    public void setUp() {}


    @Test
    public void test1Create()throws InspireNetzException {


        Merchant merchant = merchantService.saveMerchant(MerchantFixture.standardMerchant());
        log.info(merchant.toString());
        Assert.assertNotNull(merchant.getMerMerchantNo());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        Merchant merchant = MerchantFixture.standardMerchant();
        merchant = merchantService.saveMerchant(merchant);
        log.info("Original Merchant " + merchant.toString());

        Merchant updatedMerchant = MerchantFixture.updatedStandardMerchant(merchant);
        updatedMerchant = merchantService.saveMerchant(updatedMerchant);
        log.info("Updated Merchant "+ updatedMerchant.toString());

    }



    @Test
    public void test3FindByMerMerchantNo() throws InspireNetzException {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantService.saveMerchant(merchant);

        merchant = merchantService.findByMerMerchantNo(merchant.getMerMerchantNo());
        log.info("merchant data "+merchant.toString());

    }




    @Test
    public void test4SearchMerchants() throws InspireNetzException {

        // Create the merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantService.saveMerchant(merchant);
        Assert.assertNotNull(merchant.getMerMerchantNo());
        log.info("Merchant created");

        // Check the merchant name
        Page<Merchant> merchants = merchantService.searchMerchants("name", "%standard%", constructPageSpecification(0));
        Assert.assertTrue(merchants.hasContent());
        Set<Merchant> merchantSet = Sets.newHashSet((Iterable<Merchant>)merchants);
        log.info("merchant list "+merchantSet.toString());


    }

 


    @Test
    public void test6FindByMerMerchantName() throws InspireNetzException {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantService.saveMerchant(merchant);

        merchant = merchantService.findByMerMerchantName(merchant.getMerMerchantName());
        log.info("merchant data "+merchant.toString());

    }


    @Test
    public void test7FindByMerUrlName() throws InspireNetzException {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantService.saveMerchant(merchant);

        merchant = merchantService.findByMerUrlName(merchant.getMerUrlName());
        log.info("merchant data "+merchant.toString());

    }


    @Test
    public void test8IsDuplicateMerchantExist() throws InspireNetzException {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantService.saveMerchant(merchant);

        Merchant newMerchant = MerchantFixture.standardMerchant();
        boolean exists = merchantService.isDuplicateMerchantExist(newMerchant);
        Assert.assertTrue(exists);
    }

        @Test
    public void test9SaveMerchant() throws InspireNetzException {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();

        // Create the merchant locations
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelLocation("Location A");
        MerchantLocation merchantLocation2 = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation2.setMelLocation("Location B");

        // The list
        Set<MerchantLocation> locationList = new HashSet<>(0);
        locationList.add(merchantLocation);
        locationList.add(merchantLocation2);

        merchant.setMerchantLocationSet(locationList);
        merchant  = merchantService.saveMerchant(merchant);
        Assert.assertNotNull(merchant);

        locationList = new HashSet<>(0);
        merchantLocation.setMelLocation("Location C");
        locationList.add(merchantLocation);
        merchant.setMerchantLocationSet(locationList);
        merchant  = merchantService.saveMerchant(merchant);

        Assert.assertNotNull(merchant);
    }

    @Test
    public void test7GetActiveMerchants() throws InspireNetzException {

        // Get the standard merchant

        Page<Merchant>merchantPage = merchantService.getActiveMerchants(0L,"%%",constructPageSpecification(0));

        Assert.assertNotNull(merchantPage);

        Assert.assertTrue(merchantPage.hasContent());

        log.info("getActiveMerchants :Response "+merchantPage.toString());

    }




    @After
    public void tearDown() {

        Set<Merchant> merchants = MerchantFixture.standardMerchants();

        for(Merchant merchant: merchants) {

            Merchant delMerchant = merchantRepository.findByMerMerchantName(merchant.getMerMerchantName());

            if ( delMerchant != null ) {
                merchantRepository.delete(delMerchant);
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
        return new Sort(Sort.Direction.ASC, "merMerchantName");
    }



}
