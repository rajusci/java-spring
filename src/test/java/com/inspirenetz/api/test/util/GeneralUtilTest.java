package com.inspirenetz.api.test.util;

import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.NotificationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, NotificationTestConfig.class})
public class GeneralUtilTest {


    private static Logger log = LoggerFactory.getLogger(GeneralUtilTest.class);

    @Autowired
    GeneralUtils generalUtils;


    @Before
    public void setUp() {}



    // Set holding the temporary variable
    Set<Tier> tempSet = new HashSet<>(0);




    @Before
    public void setup() {}

    @Test
    public void generateUniqueCodeTest(){

        HashSet<String> hashSet=new HashSet<>();
        for(int i=0;i<10000000;i++){

            String uniqueCode=generalUtils.generateUniqueCode(6);

            hashSet.add(uniqueCode);
            log.info("generateUniqueCode : generated code "+uniqueCode);
        }

        if(hashSet.size()<10000000){

            log.info("generateUniqueCode : duplicate exist ");
        }

    }


    @After
    public void tearDown() throws InspireNetzException {


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
        return new Sort(Sort.Direction.ASC, "tieName");
    }

}
