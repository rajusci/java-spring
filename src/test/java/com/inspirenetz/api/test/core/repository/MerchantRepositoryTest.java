package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.MerchantStatus;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.repository.MerchantRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantFixture;
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
public class MerchantRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantRepositoryTest.class);

    @Autowired
    private MerchantRepository merchantRepository;


    @Before
    public void setup() {}


    @Test
    public void test1Create(){


        Merchant merchant = merchantRepository.save(MerchantFixture.standardMerchant());
        log.info(merchant.toString());
        Assert.assertNotNull(merchant.getMerMerchantNo());

    }

    @Test
    public void test2Update() {

        Merchant merchant = MerchantFixture.standardMerchant();
        merchant = merchantRepository.save(merchant);
        log.info("Original Merchant " + merchant.toString());

        Merchant updatedMerchant = MerchantFixture.updatedStandardMerchant(merchant);
        updatedMerchant = merchantRepository.save(updatedMerchant);
        log.info("Updated Merchant "+ updatedMerchant.toString());

    }



    @Test
    public void test3FindByMerMerchantNo() {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantRepository.save(merchant);

        merchant = merchantRepository.findByMerMerchantNo(merchant.getMerMerchantNo());
        log.info("merchant data "+merchant.toString());

    }




    @Test
    public void test4FindByMerMerchantNameLike() {

        // Create the merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantRepository.save(merchant);
        Assert.assertNotNull(merchant.getMerMerchantNo());
        log.info("Merchant created");

        // Check the merchant name
        Page<Merchant> merchants = merchantRepository.findByMerMerchantNameLike("%standard%", constructPageSpecification(0));
        Assert.assertTrue(merchants.hasContent());
        Set<Merchant> merchantSet = Sets.newHashSet((Iterable<Merchant>)merchants);
        log.info("merchant list "+merchantSet.toString());


    }


    @Test
    public void test5FindByMerCityLike() {

        // Create the merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantRepository.save(merchant);
        Assert.assertNotNull(merchant.getMerMerchantNo());
        log.info("Merchant created");

        // Check the merchant name
        Page<Merchant> merchants = merchantRepository.findByMerCityLike("%bang%", constructPageSpecification(0));
        Assert.assertTrue(merchants.hasContent());
        Set<Merchant> merchantSet = Sets.newHashSet((Iterable<Merchant>)merchants);
        log.info("merchant list "+merchantSet.toString());


    }


    @Test
    public void test6FindByMerMerchantName() {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantRepository.save(merchant);

        merchant = merchantRepository.findByMerMerchantName(merchant.getMerMerchantName());
        log.info("merchant data "+merchant.toString());

    }


    @Test
    public void test7FindByMerUrlName() {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantRepository.save(merchant);

        merchant = merchantRepository.findByMerUrlName(merchant.getMerUrlName());
        log.info("merchant data "+merchant.toString());

    }


    @Test
    public void test8FindByMerMerchantNameOrMerUrlName() {

        // Get the standard merchant
        Merchant merchant = MerchantFixture.standardMerchant();
        merchantRepository.save(merchant);

        List<Merchant> merchantList = merchantRepository.findByMerMerchantNameOrMerUrlName(merchant.getMerMerchantName(),merchant.getMerUrlName());
        Assert.assertTrue(!merchantList.isEmpty());
        log.info("merchant data "+merchantList.toString());

    }


    @Test
    public void test9FindAll() {

        Page<Merchant> merchantPage =merchantRepository.findAll(constructPageSpecification(0));
        Assert.assertTrue(merchantPage.hasContent());
        Set<Merchant> merchantSet = Sets.newHashSet((Iterable<Merchant>)merchantPage);
        log.info("merchant list "+merchantSet.toString());
    }

    @Test
    public void test9FindByMerStatusAndMerMerchantNameLike() {

        Page<Merchant> merchantPage =merchantRepository.findByMerStatusAndMerMerchantNameLike(MerchantStatus.ACCOUNT_ACTIVE,"%%",constructPageSpecification(0));
        Assert.assertTrue(merchantPage.hasContent());
        Set<Merchant> merchantSet = Sets.newHashSet((Iterable<Merchant>)merchantPage);
        log.info("merchant list "+merchantSet.toString());
    }

    @Test
    public void test9FindByMerMerchantNoAndMerStatusNot() {

        Merchant merchant =merchantRepository.findByMerMerchantNoAndMerStatusNot(1L,MerchantStatus.DEACTIVATED);
        Assert.assertNotNull(merchant);
        log.info("merchant list "+merchant.toString());
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
