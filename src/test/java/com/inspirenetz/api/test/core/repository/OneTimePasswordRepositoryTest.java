package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.OneTimePassword;
import com.inspirenetz.api.core.repository.CustomerRewardBalanceRepository;
import com.inspirenetz.api.core.repository.OneTimePasswordRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerRewardBalanceFixture;
import com.inspirenetz.api.test.core.fixture.OneTimePasswordFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OneTimePasswordRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(OneTimePasswordRepositoryTest.class);

    @Autowired
    private OneTimePasswordRepository oneTimePasswordRepository;

    Set<OneTimePassword> tempSet = new HashSet<>(0);

    @Before
    public void setUp(){

    }

    @Test
    public void test1Save(){

        OneTimePassword oneTimePassword = oneTimePasswordRepository.save(OneTimePasswordFixture.standardOneTimePassword());
        log.info(oneTimePassword.toString());

        // Add to the set
        tempSet.add(oneTimePassword);

        Assert.assertNotNull(oneTimePassword.getOtpCustomerNo());

    }

    @Test
    public void test2Update() {

        OneTimePassword oneTimePassword = oneTimePasswordRepository.save(OneTimePasswordFixture.standardOneTimePassword());

        log.info("Original data" +oneTimePassword.toString());

        // Add to the set
        tempSet.add(oneTimePassword);


        OneTimePassword updatedOneTimePassword = OneTimePasswordFixture.updatedStandardOneTimePassword(oneTimePassword);
        oneTimePasswordRepository.save(updatedOneTimePassword);
        log.info("Updated data" + updatedOneTimePassword.toString());


    }


    @Test
    public void test3getOTPList() {

        OneTimePassword oneTimePassword = oneTimePasswordRepository.save(OneTimePasswordFixture.standardOneTimePassword());

        log.info("Original data" +oneTimePassword.toString());

        // Add to the set
        tempSet.add(oneTimePassword);


        // Search the data
        List<OneTimePassword> fetchedTimePassword = oneTimePasswordRepository.getOTPList(oneTimePassword.getOtpMerchantNo(), oneTimePassword.getOtpCustomerNo(), oneTimePassword.getOtpType());

        Assert.assertNotNull(fetchedTimePassword);

        log.info("Fetched Data "+fetchedTimePassword);

    }

    @Test
    public void test3FindByOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc() {

        OneTimePassword oneTimePassword = oneTimePasswordRepository.save(OneTimePasswordFixture.standardOneTimePassword());

        log.info("Original data" +oneTimePassword.toString());

        // Add to the set
        tempSet.add(oneTimePassword);


        // Search the data
        OneTimePassword fetchedOneTimePassword = oneTimePasswordRepository.findByOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(oneTimePassword.getOtpRefType(), oneTimePassword.getOtpReference(), oneTimePassword.getOtpType());

        Assert.assertNotNull(fetchedOneTimePassword);

        log.info("Fetched Data "+fetchedOneTimePassword);

    }

    @Test
    public void test3FindByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc() {

        OneTimePassword oneTimePassword = oneTimePasswordRepository.save(OneTimePasswordFixture.standardOneTimePassword());

        log.info("Original data" +oneTimePassword.toString());

        // Add to the set
        tempSet.add(oneTimePassword);


        // Search the data
        //OneTimePassword fetchedOneTimePassword = oneTimePasswordRepository.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(oneTimePassword.getOtpMerchantNo(),oneTimePassword.getOtpRefType(), oneTimePassword.getOtpReference(), oneTimePassword.getOtpType());

        List<OneTimePassword> fetchedOneTimePasswords = oneTimePasswordRepository.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(1L,1, "11549", 4);


        Assert.assertNotNull(fetchedOneTimePasswords);

        log.info("Fetched Data "+fetchedOneTimePasswords);

    }


    @After
    public void tearDown() {

        for (OneTimePassword oneTimePassword : tempSet ) {

            oneTimePasswordRepository.delete(oneTimePassword);

        }

    }



}
