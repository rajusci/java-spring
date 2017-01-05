package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.domain.VoucherCode;
import com.inspirenetz.api.core.service.RedemptionVoucherSourceService;
import com.inspirenetz.api.core.service.VoucherCodeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherSourceFixture;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class VoucherCodeServiceTest {

    private static Logger log = LoggerFactory.getLogger(VoucherCodeServiceTest.class);

    @Autowired
    VoucherCodeService voucherCodeService;

    @Autowired
    RedemptionVoucherSourceService redemptionVoucherSourceService;

    Set<VoucherCode> tempSet =new HashSet<>(0);

    Set<RedemptionVoucherSource> tempSet1 =new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        VoucherCode voucherCode = voucherCodeService.save(VoucherCodesFixture.stVoucherCodes());

        log.info(voucherCode.toString());

        Assert.assertNotNull(voucherCode.getVocId());

    }

    @Test
    public void tes2findByVocMerchantNoAndVocVoucherSourceAndVocIndex() throws InspireNetzException {

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(RedemptionVoucherSourceFixture.standardRedemptionVoucherSource());

        VoucherCode voucherCode =new VoucherCode();

        voucherCode.setVocIndex(redemptionVoucherSource.getRvsIndex());

        voucherCode.setVocMerchantNo(redemptionVoucherSource.getRvsMerchantNo());

        voucherCode.setVocVoucherSource(redemptionVoucherSource.getRvsId());

        voucherCode.setVocVoucherCode("Testing");

        VoucherCode voucherCode1 =voucherCodeService.save(voucherCode);

        String voucherCode2 = voucherCodeService.getVoucherCode(redemptionVoucherSource);

        Assert.assertNotNull(voucherCode1);

    }

    @Test
    public void processBatchFile() throws InspireNetzException {

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(RedemptionVoucherSourceFixture.standardRedemptionVoucherSource());

        //set file path
        String filePath="/home/alameen/programs/apache-tomcat-7.0.50/webapps/in-resources/"+"ss.txt";

        voucherCodeService.processBatchFile(filePath,redemptionVoucherSource);

        VoucherCode voucherCode = voucherCodeService.save(VoucherCodesFixture.stVoucherCodes());



    }

    @After
    public void tearDown() {


        for(VoucherCode voucherCode : tempSet) {

            voucherCodeService.delete(voucherCode);

        }

        for(RedemptionVoucherSource redemptionVoucherSource:tempSet1){

            redemptionVoucherSourceService.deleteRedemptionVoucherSource(redemptionVoucherSource.getRvsId());
        }


    }

}
