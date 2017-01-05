package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.core.repository.NotificationCampaignRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.NotificationCampaignFixture;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class NotificationCampaignRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(NotificationCampaignRepositoryTest.class);

    @Autowired
    private NotificationCampaignRepository notificationCampaignRepository;

    Set<NotificationCampaign> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign notificationCampaign = notificationCampaignRepository.save(notificationCampaignFixture.standardNotificationCampaign());

        // Add to the tempSet
        tempSet.add(notificationCampaign);

        log.info(notificationCampaign.toString());
        Assert.assertNotNull(notificationCampaign.getNtcId());

    }

    @Test
    public void test2Update() {

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign notificationCampaign = notificationCampaignFixture.standardNotificationCampaign();
        notificationCampaign = notificationCampaignRepository.save(notificationCampaign);
        log.info("Original NotificationCampaign " + notificationCampaign.toString());

        // Add to the tempSet
        tempSet.add(notificationCampaign);


        NotificationCampaign updatedNotificationCampaign = NotificationCampaignFixture.updatedStandardNotificationCampaign(notificationCampaign);
        updatedNotificationCampaign = notificationCampaignRepository.save(updatedNotificationCampaign);
        log.info("Updated NotificationCampaign " + updatedNotificationCampaign.toString());

    }

    @Test
    public void test3FindByNtcId() {

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign notificationCampaign = notificationCampaignFixture.standardNotificationCampaign();
        notificationCampaign = notificationCampaignRepository.save(notificationCampaign);
        log.info("Original NotificationCampaign " + notificationCampaign.toString());


        // Add to the tempSet
        tempSet.add(notificationCampaign);

        // Get the data
        NotificationCampaign searchnotificationCampaign = notificationCampaignRepository.findByNtcId(notificationCampaign.getNtcId());
        Assert.assertNotNull(searchnotificationCampaign);
        Assert.assertTrue(notificationCampaign.getNtcId().longValue() == searchnotificationCampaign.getNtcId().longValue());;
        log.info("Searched NotificationCampaign : " + searchnotificationCampaign.toString());


    }

    @Test
    public void testFindByNtcNameLike(){

        NotificationCampaignFixture notificationCampaignFixture=new NotificationCampaignFixture();

        NotificationCampaign notificationCampaign = notificationCampaignFixture.standardNotificationCampaign();
        notificationCampaign = notificationCampaignRepository.save(notificationCampaign);
        tempSet.add(notificationCampaign);


        // Get the data
        Page<NotificationCampaign> searchNotificationCampaigns = notificationCampaignRepository.findByNtcNameLikeAndNtcMerchantNo(notificationCampaign.getNtcName(), notificationCampaign.getNtcMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(searchNotificationCampaigns.hasContent());
        Set<NotificationCampaign> notificationCampaignSet = Sets.newHashSet((Iterable<NotificationCampaign>) searchNotificationCampaigns);
        log.info("Searched NotificationCampaign " + notificationCampaignSet.toString());

    }



    @After
    public void tearDown() {

        for(NotificationCampaign notificationCampaign : tempSet ) {

            notificationCampaignRepository.delete(notificationCampaign);

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
        return new Sort(Sort.Direction.ASC, "ntcId");
    }

}
