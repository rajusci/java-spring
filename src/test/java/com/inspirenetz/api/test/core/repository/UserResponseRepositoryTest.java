package com.inspirenetz.api.test.core.repository;


import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.repository.PromotionRepository;
import com.inspirenetz.api.core.repository.UserResponseRepository;
import com.inspirenetz.api.core.service.PromotionService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PromotionFixture;
import com.inspirenetz.api.test.core.fixture.UserResponseFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
import java.util.Set;

/**
 * Created by alameen on 8/11/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class UserResponseRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(UserResponseRepositoryTest.class);

    Set<UserResponse> tempSet = new HashSet<>(0);

    @Autowired
    private UserResponseRepository userResponseRepository;
    
    @Autowired
    private PromotionRepository promotionRepository;

    Set<Promotion> tempSet1 = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Promotion promotion = promotionRepository.save(PromotionFixture.standardPromotion());
        log.info(promotion.toString());
        Assert.assertNotNull(promotion.getPrmId());
       
        tempSet1.add(promotion);
        
        UserResponse userResponse = userResponseRepository.save(UserResponseFixture.standardUserResponse(promotion));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());

        // Add to tempSet
        tempSet.add(userResponse);

    }

    

    @Test
    public void test2findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType() {

        Promotion promotion = promotionRepository.save(PromotionFixture.standardPromotion());
        log.info(promotion.toString());
        Assert.assertNotNull(promotion.getPrmId());

        tempSet1.add(promotion);

        UserResponse userResponse = userResponseRepository.save(UserResponseFixture.standardUserResponse(promotion));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());

        // Add to tempSet
        tempSet.add(userResponse);
        
        UserResponse userResponse1 =userResponseRepository.findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(userResponse.getUrpUserNo(),userResponse.getUrpResponseItemType(),userResponse.getUrpResponseItemId(),userResponse.getUrpResponseType());
        Assert.assertNotNull(userResponse1);
        
        log.info("user response"+userResponse1);
        
    }

    @Test
    public void test3findByUrpResponseItemIdAndUrpResponseType() {


        Promotion promotion = promotionRepository.save(PromotionFixture.standardPromotion());
        log.info(promotion.toString());
        Assert.assertNotNull(promotion.getPrmId());

        UserResponse userResponse = userResponseRepository.save(UserResponseFixture.standardUserResponse(promotion));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());

        // Add to tempSet
        tempSet.add(userResponse);

        Page<UserResponse> userResponse1 =userResponseRepository.findByUrpUserNoAndUrpResponseTypeAndUrpResponseItemId(userResponse.getUrpUserNo(), userResponse.getUrpResponseType(), userResponse.getUrpResponseItemId(), constructPageSpecification(0));
        Assert.assertNotNull(userResponse1);
        log.info("user response"+userResponse1.getContent());

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


    @org.junit.After
    public void tearDown() {

        for ( UserResponse userResponse : tempSet ) {

            userResponseRepository.delete(userResponse);

        }

        for(Promotion promotion :tempSet1){

            promotionRepository.delete(promotion);
        }
    }

}
