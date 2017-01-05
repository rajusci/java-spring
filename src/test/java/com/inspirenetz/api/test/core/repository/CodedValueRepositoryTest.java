package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CodedValue;
import com.inspirenetz.api.core.repository.CodedValueRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CodedValueRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CodedValueRepositoryTest.class);

    @Autowired
    private CodedValueRepository codedValueRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CodedValue codedValue = codedValueRepository.save(CodedValueFixture.standardCodedValue());
        log.info(codedValue.toString());
        Assert.assertNotNull(codedValue.getCdvCodeId());

    }

    @Test
    public void test2Update() {

        CodedValue codedValue = CodedValueFixture.standardCodedValue();
        codedValue = codedValueRepository.save(codedValue);
        log.info("Original CodedValue " + codedValue.toString());

        CodedValue updatedCodedValue = CodedValueFixture.updatedStandardCodedValue(codedValue);
        updatedCodedValue = codedValueRepository.save(updatedCodedValue);
        log.info("Updated CodedValue "+ updatedCodedValue.toString());

    }



    @Test
    public void test3FindByCdvCodeId() {

        CodedValue codedValue = CodedValueFixture.standardCodedValue();
        codedValue= codedValueRepository.save(codedValue);
        Assert.assertNotNull(codedValue.getCdvCodeId());

        // Get the coded value
        CodedValue foundValue = codedValueRepository.findByCdvCodeId(codedValue.getCdvCodeId());
        Assert.assertNotNull(foundValue);
        log.info("CodedValue Information "+foundValue.toString());

    }


    @Test
    public void test4FindByCdvIndexAndCdvCodeValue() {

        CodedValue codedValue = CodedValueFixture.standardCodedValue();
        codedValue= codedValueRepository.save(codedValue);
        Assert.assertNotNull(codedValue.getCdvCodeId());

        CodedValue searchCodedValue = codedValueRepository.findByCdvIndexAndCdvCodeValue(codedValue.getCdvIndex(),codedValue.getCdvCodeValue());
        Assert.assertNotNull(searchCodedValue);
        log.info("CodedValue Information" + codedValue.toString());

    }

    @Test
    public void test5FindByCdvIndex() {

        // Get the standard codedValue
        Set<CodedValue> codedValueSet = CodedValueFixture.standardCodedValues();
        codedValueRepository.save(codedValueSet);

        // Get the standarc codedValue
        CodedValue codedValue = CodedValueFixture.standardCodedValue();

        // check the data
        Page<CodedValue> codedValuePage = codedValueRepository.findByCdvIndex(codedValue.getCdvIndex(),constructPageSpecification(0));
        Assert.assertTrue(codedValuePage.hasContent());
        List<CodedValue> codedValueList = Lists.newArrayList((Iterable<CodedValue>)codedValuePage);
        log.info("CodedValue Information"+codedValueList.toString());



    }



    @Test
    public void test6GetCodedValueByIndex() {

        // Get the standard codedValue
        Set<CodedValue> codedValueSet = CodedValueFixture.standardCodedValues();
        codedValueRepository.save(codedValueSet);

        // Get the standarc codedValue
        CodedValue codedValue = CodedValueFixture.standardCodedValue();

        // check the data
        List<CodedValue> codedValueList = codedValueRepository.findByCdvIndex(codedValue.getCdvIndex());
        Assert.assertTrue(!codedValueList.isEmpty());
        log.info("CodedValue Information"+codedValueList.toString());



    }


    @Test
    public void test7GetOrderedCodedValues() {

        List<CodedValue> codedValueList = codedValueRepository.getOrderedCodedValues();
        Assert.assertNotNull(codedValueList);;
        log.info("CodedVAlueList :" + codedValueList.toString());

    }

    @After
    public void tearDown() {

        Set<CodedValue> codedValues = CodedValueFixture.standardCodedValues();

        for(CodedValue codedValue: codedValues) {

            CodedValue delCodedValue = codedValueRepository.findByCdvIndexAndCdvCodeValue(codedValue.getCdvIndex(),codedValue.getCdvCodeValue());

            if ( delCodedValue != null ) {
                codedValueRepository.delete(delCodedValue);
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
