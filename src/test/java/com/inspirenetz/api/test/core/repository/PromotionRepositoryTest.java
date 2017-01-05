package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.repository.PromotionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PromotionFixture;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PromotionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PromotionRepositoryTest.class);

    @Autowired
    private PromotionRepository promotionRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Promotion promotion = promotionRepository.save(PromotionFixture.standardPromotion());
        log.info(promotion.toString());
        Assert.assertNotNull(promotion.getPrmId());

    }

    @Test
    public void test2Update() {

        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionRepository.save(promotion);
        log.info("Original Promotion " + promotion.toString());

        Promotion updatedPromotion = PromotionFixture.updatedStandardPromotion(promotion);
        updatedPromotion = promotionRepository.save(updatedPromotion);
        log.info("Updated Promotion "+ updatedPromotion.toString());

    }



    @Test
    public void test3FindByPrmMerchantNo() {

        // Get the standard promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionRepository.save(promotion);

        Page<Promotion> promotions = promotionRepository.findByPrmMerchantNo(promotion.getPrmMerchantNo(),constructPageSpecification(0));
        log.info("promotions by merchant no " + promotions.toString());
        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>)promotions);
        log.info("promotion list "+promotionSet.toString());

    }

    @Test
    public void test4FindByPrmMerchantNoAndPrmName() {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotionRepository.save(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        Promotion fetchPromotion = promotionRepository.findByPrmMerchantNoAndPrmName(promotion.getPrmMerchantNo(),promotion.getPrmName());
        Assert.assertNotNull(fetchPromotion);
        log.info("Fetched promotion info" + promotion.toString());

    }


    @Test
    public void test5FindByPrmMerchantNoAndPrmNameLike() {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotionRepository.save(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // Check the promotion name
        Page<Promotion> promotions = promotionRepository.findByPrmMerchantNoAndPrmNameLike(promotion.getPrmMerchantNo(),"%test%",constructPageSpecification(0));
        Assert.assertTrue(promotions.hasContent());
        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>)promotions);
        log.info("promotion list "+promotionSet.toString());


    }

    @Test
    public void test5FindByPrmTargetedOptionAndPrmNameLike() {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion.setPrmTargetedOption(PromotionTargetOption.PUBLIC);
        promotionRepository.save(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // Check the promotion name
        Page<Promotion> promotions = promotionRepository.findByPrmTargetedOptionAndPrmNameLike(PromotionTargetOption.PUBLIC,"%"+promotion.getPrmName()+"%",constructPageSpecification(0));
        Assert.assertTrue(promotions.hasContent());
        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>)promotions);
        log.info("promotion list "+promotionSet.toString());


    }

    @Test
    public void test5FindByPrmMerchantNoAndPrmTargetedOptionAndPrmNameLike() {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion.setPrmTargetedOption(PromotionTargetOption.PUBLIC);
        promotionRepository.save(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // Check the promotion name
        Page<Promotion> promotions = promotionRepository.findByPrmMerchantNoAndPrmTargetedOptionAndPrmNameLike(promotion.getPrmMerchantNo(), PromotionTargetOption.PUBLIC,"%"+promotion.getPrmName()+"%",constructPageSpecification(0));
        Assert.assertTrue(promotions.hasContent());
        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>)promotions);
        log.info("promotion list "+promotionSet.toString());


    }

    @After
    public void tearDown() {

        Set<Promotion> promotions = PromotionFixture.standardPromotions();

        for(Promotion promotion: promotions) {

            Promotion delPromotion = promotionRepository.findByPrmMerchantNoAndPrmName(promotion.getPrmMerchantNo(),promotion.getPrmName());

            if ( delPromotion != null ) {
                promotionRepository.delete(delPromotion);
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
        return new Sort(Sort.Direction.ASC, "prmName");
    }


}
