package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.repository.SegmentMemberRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
import java.util.Set;

/**
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SegmentMemberRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(SegmentMemberRepositoryTest.class);

    @Autowired
    private SegmentMemberRepository segmentMemberRepository;


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        Set<SegmentMember> segmentMembers = SegmentMemberFixture.standardSegmentMembers();

        for(SegmentMember segmentMember: segmentMembers) {

            SegmentMember delSegmentMember = segmentMemberRepository.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(), segmentMember.getSgmCustomerNo());

            if ( delSegmentMember != null ) {
                segmentMemberRepository.delete(delSegmentMember);
            }

        }
    }


    @Test
    public void test1Create(){


        SegmentMember segmentMember = segmentMemberRepository.save(SegmentMemberFixture.standardSegmentMember());
        log.info(segmentMember.toString());
        Assert.assertNotNull(segmentMember.getSgmId());

    }
 
    @Test
    public void test2FindBySgmSegmentIdAndSgmCustomerNo() {

        SegmentMember segmentMember = segmentMemberRepository.save(SegmentMemberFixture.standardSegmentMember());
        Assert.assertNotNull(segmentMember.getSgmId());

        SegmentMember standardSegmentMember = segmentMemberRepository.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(),segmentMember.getSgmCustomerNo());
        Assert.assertNotNull(standardSegmentMember);
        Assert.assertTrue(segmentMember.getSgmId() == standardSegmentMember.getSgmId());
        log.info("SegmentMember Info " + standardSegmentMember.toString());

    }


    @Test
    public void test3FindBySgmSegmentId() {

        Set<SegmentMember> segmentMemberSet = SegmentMemberFixture.standardSegmentMembers();
        segmentMemberRepository.save(segmentMemberSet);

        // Get the standard SegmentMember
        SegmentMember segmentMember = SegmentMemberFixture.standardSegmentMember();

        Page<SegmentMember> segmentMemberPage = segmentMemberRepository.findBySgmSegmentId(segmentMember.getSgmSegmentId(),constructPageSpecification(0));
        Assert.assertNotNull(segmentMemberPage);
        List<SegmentMember> segmentMemberList = Lists.newArrayList((Iterable<SegmentMember>)segmentMemberPage);
        log.info("SegmentMember List" + segmentMemberList.toString());

    }


    @Test
    public void test3FindBySgmCustomerNo() {

        Set<SegmentMember> segmentMemberSet = SegmentMemberFixture.standardSegmentMembers();
        segmentMemberRepository.save(segmentMemberSet);

        // Get the standard SegmentMember
        SegmentMember segmentMember = SegmentMemberFixture.standardSegmentMember();

        List<SegmentMember> segmentMemberList = segmentMemberRepository.findBySgmCustomerNo(segmentMember.getSgmCustomerNo());
        Assert.assertNotNull(segmentMemberList);
        log.info("SegmentMember List" + segmentMemberList.toString());

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
}
