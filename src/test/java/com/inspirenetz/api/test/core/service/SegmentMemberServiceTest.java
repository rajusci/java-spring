package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.SegmentMemberFixture;
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
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class SegmentMemberServiceTest {


    private static Logger log = LoggerFactory.getLogger(SegmentMemberServiceTest.class);

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Before
    public void setUp() {}

    @Test
    public void test1Create()throws InspireNetzException {


        SegmentMember segmentMember = segmentMemberService.saveSegmentMember(SegmentMemberFixture.standardSegmentMember());
        log.info(segmentMember.toString());
        Assert.assertNotNull(segmentMember.getSgmId());

    }

    @Test
    public void test2FindBySgmSegmentIdAndSgmCustomerNo() throws InspireNetzException {

        SegmentMember segmentMember = segmentMemberService.saveSegmentMember(SegmentMemberFixture.standardSegmentMember());
        Assert.assertNotNull(segmentMember.getSgmId());

        SegmentMember standardSegmentMember = segmentMemberService.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(),segmentMember.getSgmCustomerNo());
        Assert.assertNotNull(standardSegmentMember);
        Assert.assertTrue(segmentMember.getSgmId() == standardSegmentMember.getSgmId());
        log.info("SegmentMember Info " + standardSegmentMember.toString());

    }

    @Test
    public void test3FindBySgmSegmentId() {

        Set<SegmentMember> segmentMemberSet = SegmentMemberFixture.standardSegmentMembers();
        List<SegmentMember> segmentMemberList1 = Lists.newArrayList((Iterable<SegmentMember>)segmentMemberSet);
        segmentMemberService.saveAll(segmentMemberList1);

        // Get the standard SegmentMember
        SegmentMember segmentMember = SegmentMemberFixture.standardSegmentMember();

        Page<SegmentMember> segmentMemberPage = segmentMemberService.findBySgmSegmentId(segmentMember.getSgmSegmentId(),constructPageSpecification(0));
        Assert.assertNotNull(segmentMemberPage);
        List<SegmentMember> segmentMemberList = Lists.newArrayList((Iterable<SegmentMember>)segmentMemberPage);
        log.info("SegmentMember List" + segmentMemberList.toString());

    }

    @Test
    public void test3FindBySgmCustomerNo() {

        Set<SegmentMember> segmentMemberSet = SegmentMemberFixture.standardSegmentMembers();
        List<SegmentMember> segmentMemberList1 = Lists.newArrayList((Iterable<SegmentMember>)segmentMemberSet);
        segmentMemberService.saveAll(segmentMemberList1);

        // Get the standard SegmentMember
        SegmentMember segmentMember = SegmentMemberFixture.standardSegmentMember();

        List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(segmentMember.getSgmCustomerNo());
        Assert.assertNotNull(segmentMemberList);
        log.info("SegmentMember List" + segmentMemberList.toString());

    }

    @Test
    public void testIsDuplicateSegmentMemberExisting()throws InspireNetzException {

        // Save the segmentMember
        SegmentMember segmentMember = SegmentMemberFixture.standardSegmentMember();
        segmentMemberService.saveSegmentMember(segmentMember);

        // New SegmentMember
        SegmentMember newSegmentMember = SegmentMemberFixture.standardSegmentMember();
        boolean exists = segmentMemberService.isDuplicateSegmentMemberExisting(newSegmentMember);
        Assert.assertTrue(exists);
        log.info("segmentMember exists");



    }

    @Test
    public void testGetSegmentMemberMapByProgramId() throws InspireNetzException {

        // Save the segmentMember
        SegmentMember segmentMember = SegmentMemberFixture.standardSegmentMember();
        segmentMemberService.saveSegmentMember(segmentMember);

        // Get the SegmentMemberList
        List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(segmentMember.getSgmCustomerNo());
        Map<Long,SegmentMember> segmentMemberMap = segmentMemberService.getSegmentMemberMapBySegmentId(segmentMemberList);
        Assert.assertNotNull(segmentMemberMap);;
        Assert.assertTrue(!segmentMemberMap.isEmpty());
        log.info("SegmentMember Map" + segmentMemberMap.toString());

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
        return new Sort(Sort.Direction.ASC, "sgmId");
    }

    @After
    public void tearDown() throws Exception {

        Set<SegmentMember> segmentMembers = SegmentMemberFixture.standardSegmentMembers();

        for(SegmentMember segmentMember: segmentMembers) {

            SegmentMember delSegmentMember = segmentMemberService.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(), segmentMember.getSgmCustomerNo());

            if ( delSegmentMember != null ) {
                segmentMemberService.deleteSegmentMember(delSegmentMember.getSgmId());
            }

        }
    }

}
