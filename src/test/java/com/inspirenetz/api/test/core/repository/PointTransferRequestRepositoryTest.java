package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.core.repository.PointTransferRequestRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PointTransferRequestFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class PointTransferRequestRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PointTransferRequestRepositoryTest.class);

    @Autowired
    private PointTransferRequestRepository pointTransferRequestRepository;

    Set<PointTransferRequest> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequestFixture.standardPointTransferRequest());

        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        log.info(pointTransferRequest.toString());
        Assert.assertNotNull(pointTransferRequest.getPtrId());

    }

    @Test
    public void test2Update() {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);
        log.info("Original PointTransferRequest " + pointTransferRequest.toString());

        // Add to the tempSet
        tempSet.add(pointTransferRequest);


        PointTransferRequest updatedPointTransferRequest = PointTransferRequestFixture.updatedStandardPointTransferRequest(pointTransferRequest);
        updatedPointTransferRequest = pointTransferRequestRepository.save(updatedPointTransferRequest);
        log.info("Updated PointTransferRequest "+ updatedPointTransferRequest.toString());

    }

    @Test
    public void test3FindByPtrId() {

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);
        log.info("Original PointTransferRequest " + pointTransferRequest.toString());


        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        // Get the data
        PointTransferRequest searchpointTransferRequest = pointTransferRequestRepository.findByPtrId(pointTransferRequest.getPtrId());
        Assert.assertNotNull(searchpointTransferRequest);
        Assert.assertTrue(pointTransferRequest.getPtrId().longValue() ==  searchpointTransferRequest.getPtrId().longValue());;
        log.info("Searched PointTransferRequest : " + searchpointTransferRequest.toString());


    }

    @Test
    public void test4FindByPtrMerchantNo(){

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);
        log.info("Original PointTransferRequest " + pointTransferRequest.toString());


        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        // Get the data
        Page<PointTransferRequest> searchPointTransferRequest = pointTransferRequestRepository.findByPtrMerchantNo(pointTransferRequest.getPtrMerchantNo(),constructPageSpecification(0) );
        Assert.assertTrue(searchPointTransferRequest.hasContent());
        log.info("Searched PointTransferRequest : " + searchPointTransferRequest.toString());

    }

    @Test
    public void testFindByPtrMerchantNoAndPtrStatus(){

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);
        log.info("Original PointTransferRequest " + pointTransferRequest.toString());


        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        // Get the data
        Page<PointTransferRequest> searchPointTransferRequest = pointTransferRequestRepository.findByPtrMerchantNoAndPtrStatus(pointTransferRequest.getPtrMerchantNo(),pointTransferRequest.getPtrStatus(),constructPageSpecification(0) );
        Assert.assertTrue(searchPointTransferRequest.hasContent());
        log.info("Searched PointTransferRequest : " + searchPointTransferRequest.toString());

    }

    @Test
    public void testFindByPtrMerchantNoAndPtrSourceAndPtrStatus(){

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);
        log.info("Original PointTransferRequest " + pointTransferRequest.toString());


        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        // Get the data
        Page<PointTransferRequest> searchPointTransferRequest = pointTransferRequestRepository.findByPtrMerchantNoAndPtrSourceAndPtrStatus(pointTransferRequest.getPtrMerchantNo(),pointTransferRequest.getPtrSource(),pointTransferRequest.getPtrStatus(),constructPageSpecification(0) );
        Assert.assertTrue(searchPointTransferRequest.hasContent());
        log.info("Searched PointTransferRequest : " + searchPointTransferRequest.toString());

    }


    @Test
    public void testFindByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(){

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();
        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);
        log.info("Original PointTransferRequest " + pointTransferRequest.toString());


        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        // Get the data
        List<PointTransferRequest> searchPointTransferRequest = pointTransferRequestRepository.findByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(pointTransferRequest.getPtrMerchantNo(),pointTransferRequest.getPtrSource(),pointTransferRequest.getPtrDestination(),pointTransferRequest.getPtrStatus());
        Assert.assertNotNull(searchPointTransferRequest);
        log.info("Searched PointTransferRequest : " + searchPointTransferRequest.toString());

    }

    @Test
    public void test6FindByPtrMerchantNoAndPtrSource(){

        PointTransferRequestFixture pointTransferRequestFixture=new PointTransferRequestFixture();

        PointTransferRequest pointTransferRequest = pointTransferRequestFixture.standardPointTransferRequest();

        pointTransferRequest = pointTransferRequestRepository.save(pointTransferRequest);

        log.info("Original PointTransferRequest " + pointTransferRequest.toString());

        // Add to the tempSet
        tempSet.add(pointTransferRequest);

        // Get the data
        Page<PointTransferRequest> searchPointTransferRequest = pointTransferRequestRepository.findByPtrMerchantNoAndPtrSource(pointTransferRequest.getPtrMerchantNo(), pointTransferRequest.getPtrSource(), constructPageSpecification(0));

        Assert.assertTrue(searchPointTransferRequest.hasContent());

        log.info("Searched PointTransferRequest : " + searchPointTransferRequest.toString());

    }

    @After
    public void tearDown() {

        for(PointTransferRequest pointTransferRequest : tempSet ) {

            pointTransferRequestRepository.delete(pointTransferRequest);

        }

    }


    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

}
