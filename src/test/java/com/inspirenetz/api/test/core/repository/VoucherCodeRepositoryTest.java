package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.VoucherCode;
import com.inspirenetz.api.core.repository.VoucherCodeRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.VoucherCodesFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 10/2/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class VoucherCodeRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(VoucherCodeRepositoryTest.class);

    @Autowired
    VoucherCodeRepository voucherCodeRepository;

    Set<VoucherCode> tempSet =new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        VoucherCode voucherCode = voucherCodeRepository.save(VoucherCodesFixture.stVoucherCodes());

        log.info(voucherCode.toString());

        Assert.assertNotNull(voucherCode.getVocId());

    }

    @Test
    public void test2FindByVocId() {

        VoucherCode voucherCode = voucherCodeRepository.save(VoucherCodesFixture.stVoucherCodes());

        tempSet.add(voucherCode);

        voucherCode = voucherCodeRepository.findByVocId(voucherCode.getVocId());

        Assert.assertNotNull(voucherCode);

    }

    @Test
    public void findByVocMerchantNoAndVocVoucherSourceAndVocIndex() {

        VoucherCode voucherCode = voucherCodeRepository.save(VoucherCodesFixture.stVoucherCodes());

        tempSet.add(voucherCode);

        VoucherCode voucherCode1 = voucherCodeRepository.findByVocMerchantNoAndVocVoucherSourceAndVocIndex(voucherCode.getVocMerchantNo(), voucherCode.getVocVoucherSource(), voucherCode.getVocIndex());

        Assert.assertNotNull(voucherCode);

    }

    @After
    public void tearDown() {


        for(VoucherCode voucherCode : tempSet) {

            voucherCodeRepository.delete(voucherCode);

        }
    }



}
