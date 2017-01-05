package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.CodedValueMap;
import com.inspirenetz.api.core.domain.CodedValue;
import com.inspirenetz.api.core.service.CodedValueService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CodedValueFixture;
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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CodedValueServiceTest {


    private static Logger log = LoggerFactory.getLogger(CodedValueServiceTest.class);

    @Autowired
    private CodedValueService codedValueService;


    @Before
    public void setUp() {}




    @Test
    public void test3FindByCdvCodeId() {

        CodedValue codedValue = CodedValueFixture.standardCodedValue();
        codedValue= codedValueService.saveCodedValue(codedValue);
        Assert.assertNotNull(codedValue.getCdvCodeId());

        // Get the coded value
        CodedValue foundValue = codedValueService.findByCdvCodeId(codedValue.getCdvCodeId());
        Assert.assertNotNull(foundValue);
        log.info("CodedValue Information "+foundValue.toString());

    }


    @Test
    public void test4FindByCdvIndexAndCdvCodeValue() {

        CodedValue codedValue = CodedValueFixture.standardCodedValue();
        codedValue= codedValueService.saveCodedValue(codedValue);
        Assert.assertNotNull(codedValue.getCdvCodeId());

        CodedValue searchCodedValue = codedValueService.findByCdvIndexAndCdvCodeValue(codedValue.getCdvIndex(),codedValue.getCdvCodeValue());
        Assert.assertNotNull(searchCodedValue);
        log.info("CodedValue Information" + codedValue.toString());

    }

    @Test
    public void test5FindByCdvIndex() {

        // Get the standard codedValue
        Set<CodedValue> codedValueSet = CodedValueFixture.standardCodedValues();
        List<CodedValue> codedValues = Lists.newArrayList((Iterable<CodedValue>)codedValueSet);
        codedValueService.saveAll(codedValues);

        // Get the standarc codedValue
        CodedValue codedValue = CodedValueFixture.standardCodedValue();

        // check the data
        Page<CodedValue> codedValuePage = codedValueService.findByCdvIndex(codedValue.getCdvIndex(),constructPageSpecification(0));
        Assert.assertTrue(codedValuePage.hasContent());
        List<CodedValue> codedValueList = Lists.newArrayList((Iterable<CodedValue>) codedValuePage);
        log.info("CodedValue Information"+codedValueList.toString());



    }


    @Test
    public void test6IsDuplicateCodeValueExisting() {

        // Get the standard codedValue
        Set<CodedValue> codedValueSet = CodedValueFixture.standardCodedValues();
        List<CodedValue> codedValues = Lists.newArrayList((Iterable<CodedValue>)codedValueSet);
        codedValueService.saveAll(codedValues);


        // Get the standard object
        CodedValue codedValue = CodedValueFixture.standardCodedValue();
        boolean exists = codedValueService.isDuplicateCodedValueExisting(codedValue);
        Assert.assertTrue(exists);
        log.info("Coded Value "+ codedValue.toString());


    }


    @Test
    public void test6GetCodedValueByIndex() {

        // Get the standard codedValue
        Set<CodedValue> codedValueSet = CodedValueFixture.standardCodedValues();
        List<CodedValue> codedValues = Lists.newArrayList((Iterable<CodedValue>) codedValueSet);
        codedValueService.saveAll(codedValues);

        // Get the standarc codedValue
        CodedValue codedValue = CodedValueFixture.standardCodedValue();

        // check the data
        HashMap<Integer,String> codedValueMapForIndex = codedValueService.getCodedValueMapForIndex(codedValue.getCdvIndex());
        Assert.assertTrue(!codedValueMapForIndex.isEmpty());
        log.info("CodedValue Information"+codedValueMapForIndex.toString());



    }


    @Test
    public void test7GetCodedValueMap() {

        HashMap<Integer,List<CodedValueMap>> codedValueMap = codedValueService.getCodedValueMap();
        Assert.assertNotNull(codedValueMap);
        log.info("CodedValueMap : "+ codedValueMap);



    }

    @Test
    public void test8GetCodedValueMapByIndex() {

        // Get the standard codedValue
        Set<CodedValue> codedValueSet = CodedValueFixture.standardCodedValues();
        List<CodedValue> codedValues = Lists.newArrayList((Iterable<CodedValue>) codedValueSet);
        codedValueService.saveAll(codedValues);

        // Get the standarc codedValue
        CodedValue codedValue = CodedValueFixture.standardCodedValue();

        // check the data
        HashMap<Integer, List<CodedValueMap>> codedValueMapByIndex = codedValueService.getCodedValueMapByIndex(codedValue.getCdvIndex());
        Assert.assertTrue(!codedValueMapByIndex.isEmpty());
        log.info("CodedValue Information"+codedValueMapByIndex.toString());



    }


    @After
    public void tearDown() {

        Set<CodedValue> codedValues = CodedValueFixture.standardCodedValues();

        for(CodedValue codedValue: codedValues) {

            CodedValue delCodedValue = codedValueService.findByCdvIndexAndCdvCodeValue(codedValue.getCdvIndex(),codedValue.getCdvCodeValue());

            if ( delCodedValue != null ) {
                codedValueService.deleteCodedValue(delCodedValue.getCdvCodeId());
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
        return new Sort(Sort.Direction.ASC, "cdvCodeId");
    }



}
