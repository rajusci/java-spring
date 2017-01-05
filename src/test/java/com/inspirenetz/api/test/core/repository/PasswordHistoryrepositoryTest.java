package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.PasswordHistory;
import com.inspirenetz.api.core.repository.PasswordHistoryRepository;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PasswordHistoryFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 9/10/14.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PasswordHistoryrepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(PasswordHistoryrepositoryTest.class);

    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    Set<PasswordHistory> tempSet = new HashSet<>(0);


    @Before
    public void setUp() throws Exception {


    }


    @Test
    public void test1Create(){

        PasswordHistory passwordHistory = passwordHistoryRepository.save(PasswordHistoryFixture.standardPasswordHistory());
        tempSet.add(passwordHistory);
        log.info(passwordHistory.toString());
        Assert.assertNotNull(passwordHistory.getPasHistoryId());

    }
    @Test
    public void test2findByMaxPasChangedAt() throws InspireNetzException {

        PasswordHistory passwordHistory = passwordHistoryRepository.save(PasswordHistoryFixture.standardPasswordHistory());
        log.info(passwordHistory.toString());
        Assert.assertNotNull(passwordHistory.getPasHistoryId());

        tempSet.add(passwordHistory);

        passwordHistory = PasswordHistoryFixture.standardPasswordHistory();
        passwordHistory.setPasChangedAt(new Date());
        passwordHistory = passwordHistoryRepository.save(passwordHistory);
        log.info(passwordHistory.toString());
        Assert.assertNotNull(passwordHistory.getPasHistoryId());

        tempSet.add(passwordHistory);
        Date date = passwordHistoryRepository.findByMaxPasChangedAt(passwordHistory.getPasHistoryUserNo());
        Assert.assertNotNull(date);
        log.info("maximum changed at date" + date);

    }

    @Test
    public void test3findByLastChangedAtDate() throws InspireNetzException {

        PasswordHistory passwordHistory = passwordHistoryRepository.save(PasswordHistoryFixture.standardPasswordHistory());
        log.info(passwordHistory.toString());
        Assert.assertNotNull(passwordHistory.getPasHistoryId());

        tempSet.add(passwordHistory);

        List<PasswordHistory> passwordHistoryList = passwordHistoryRepository.findByLastChangedAtDate(passwordHistory.getPasHistoryUserNo());
        log.info("list for last logged order from history"+passwordHistoryList);

    }

    @After
    public void tearDown() {

        for(PasswordHistory passwordHistory : tempSet ) {

            passwordHistoryRepository.delete(passwordHistory);

        }

    }


}
