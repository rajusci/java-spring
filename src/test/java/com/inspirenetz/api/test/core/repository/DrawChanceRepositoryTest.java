package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.repository.DrawChanceRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.DrawChanceFixture;
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
public class DrawChanceRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(DrawChanceRepositoryTest.class);

    @Autowired
    private DrawChanceRepository drawChanceRepository;

    Set<DrawChance> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceRepository.save(drawChanceFixture.standardDrawChance());

        // Add to the tempSet
        tempSet.add(drawChance);

        log.info(drawChance.toString());
        Assert.assertNotNull(drawChance.getDrcId());

    }

    @Test
    public void test2Update() {

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceRepository.save(drawChance);
        log.info("Original DrawChance " + drawChance.toString());

        // Add to the tempSet
        tempSet.add(drawChance);


        DrawChance updatedDrawChance = DrawChanceFixture.updatedStandardDrawChance(drawChance);
        updatedDrawChance = drawChanceRepository.save(updatedDrawChance);
        log.info("Updated DrawChance "+ updatedDrawChance.toString());

    }

    @Test
    public void test3FindByDrcId() {

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceRepository.save(drawChance);
        log.info("Original DrawChance " + drawChance.toString());


        // Add to the tempSet
        tempSet.add(drawChance);

        // Get the data
        DrawChance searchdrawChance = drawChanceRepository.findByDrcId(drawChance.getDrcId());
        Assert.assertNotNull(searchdrawChance);
        Assert.assertTrue(drawChance.getDrcId().longValue() ==  searchdrawChance.getDrcId().longValue());;
        log.info("Searched DrawChance : " + searchdrawChance.toString());


    }

    @Test
    public void test4FindByDrcCustomerNoAndDrcType(){

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceRepository.save(drawChance);
        log.info("Original DrawChance " + drawChance.toString());


        // Add to the tempSet
        tempSet.add(drawChance);

        // Get the data
        DrawChance searchDrawChance = drawChanceRepository.findByDrcCustomerNoAndDrcType(drawChance.getDrcCustomerNo(), DrawType.RAFFLE_TICKET);
        Assert.assertNotNull(searchDrawChance);
        log.info("Searched DrawChance : " + searchDrawChance.toString());

    }

    @Test
    public void testFindByDrcType(){

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        DrawChance drawChance = drawChanceFixture.standardDrawChance();
        drawChance = drawChanceRepository.save(drawChance);
        tempSet.add(drawChance);

        drawChance.setDrcId(null);
        drawChance.setDrcChances(10L);
        drawChance = drawChanceRepository.save(drawChance);

        // Add to the tempSet
        tempSet.add(drawChance);

        // Get the data
        List<DrawChance> searchDrawChances = drawChanceRepository.findByDrcTypeAndDrcStatus( DrawType.RAFFLE_TICKET,1);
        Assert.assertTrue(searchDrawChances.size()>0);
        log.info("Searched DrawChance : " + searchDrawChances);

    }



    @After
    public void tearDown() {

        for(DrawChance drawChance : tempSet ) {

            drawChanceRepository.delete(drawChance);

        }

    }



}
