package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.config.NotificationConfig;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.PromotionRepository;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.PromotionService;
import com.inspirenetz.api.core.service.SettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PromotionResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.MerchantFixture;
import com.inspirenetz.api.test.core.fixture.PromotionFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.dozer.Mapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class PromotionServiceTest {


    private static Logger log = LoggerFactory.getLogger(PromotionServiceTest.class);

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    SettingService settingService;

    @Autowired
    Mapper mapper;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1ValidateAndSavePromotion() throws InspireNetzException{



        // Get the standard promotion
        Promotion promotion = PromotionFixture.standardPromotion();

        promotion.setPrmSegmentId(69L);

        PromotionResource promotionResource=mapper.map(promotion,PromotionResource.class);

        promotion=promotionService.validateAndSavePromotion(promotionResource);

        log.info(promotion.toString());


        Assert.assertNotNull(promotion.getPrmId());

    }


    @Test
    public void test1FindByMerchantNo() {

        // Get the standard promotion
        Promotion promotion = PromotionFixture.standardPromotion();

        Page<Promotion> promotions = promotionService.findByPrmMerchantNo(promotion.getPrmMerchantNo(),constructPageSpecification(0));
        log.info("promotions by merchant no " + promotions.toString());
        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>) promotions);
        log.info("promotion list "+promotionSet.toString());

    }

    @Test
    public void test2FindByPrmMerchantNoAndPrmName() throws InspireNetzException {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        Promotion fetchPromotion = promotionService.findByPrmMerchantNoAndPrmName(promotion.getPrmMerchantNo(),promotion.getPrmName());
        Assert.assertNotNull(fetchPromotion);
        log.info("Fetched promotion info" + promotion.toString());

    }

    @Test
    public void test3FindByPrmMerchantNoAndPrmNameLike() throws InspireNetzException {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // Check the promotion name
        Page<Promotion> promotions = promotionService.findByPrmMerchantNoAndPrmNameLike(promotion.getPrmMerchantNo(),"%test%",constructPageSpecification(0));
        Assert.assertTrue(promotions.hasContent());
        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>)promotions);
        log.info("promotion list "+promotionSet.toString());


    }

    @Test
    public void test4IsDuplicatePromotionExisting() throws InspireNetzException {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // Create a new promotion
        Promotion newPromotion = PromotionFixture.standardPromotion();
        boolean exists = promotionService.isDuplicatePromotionExisting(newPromotion);
        Assert.assertTrue(exists);
        log.info("Promotion exists");


    }

    @Test
    public void test5DeletePromotion() throws InspireNetzException {

        // Create the promotion
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // call the delete promotion
        promotionService.deletePromotion(promotion.getPrmId());

        // Try searching for the promotion
        Promotion checkPromotion  = promotionService.findByPrmMerchantNoAndPrmName(promotion.getPrmMerchantNo(),promotion.getPrmName());

        Assert.assertNull(checkPromotion);

        log.info("promotion deleted");

    }

    @Test
    public void test6IsPromotionValidForCustomer() {

        // Create the standard promotion
        Promotion promotion = PromotionFixture.standardPromotion();

        // Create the customer
        Customer cusotmer = CustomerFixture.standardCustomer();


        // Check if the customer is valid
        boolean isValid = promotionService.isPromotionValidForCustomer(promotion,cusotmer);
        Assert.assertTrue(isValid);
        log.info("Promotion is valid for customer");



    }

    @Test
    public void test7GetPromotionsForCustomer() throws InspireNetzException {

        // Save the promoton
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);

        // Get the standar customer
        Customer customer = CustomerFixture.standardCustomer();

        // Get the list of promotions
        List<Promotion> promotionList = promotionService.getPromotionsForCustomer(customer);
        Assert.assertNotNull(promotionList);
        log.info("Promotion List : " + promotionList.toString());


    }

    @Test
    public void test8GetPromotionsForUser() throws InspireNetzException {

        // Save the promoton
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);

        User user = new User();
        user.setUsrUserNo(12L);
        //user.setUsrLoginId("gr.sandeep1@gmail.com");
        //user.setUsrMobile("9538828853");


        // Get the list of the promotions for the user
        List<Promotion> promotionList = promotionService.getPromotionsForUser(user);
        Assert.assertNotNull(promotionList);
        log.info("Promo Count : " + promotionList.size());
        log.info("Promotion List " + promotionList.toString());

    }

    @Test
    public void test8GetPublicPromotions() throws InspireNetzException {

        // Save the promoton
        Promotion promotion = PromotionFixture.standardPromotion();
        promotion.setPrmTargetedOption(PromotionTargetOption.PUBLIC);
        promotion = promotionService.savePromotion(promotion);




        // Get the list of the public promotions for all merchants
        Page<Promotion> promotionPage = promotionService.getPublicPromotions(0L,"%"+promotion.getPrmName()+"%", constructPageSpecification(0));
        Assert.assertNotNull(promotionPage);
        log.info("Promotion page " + promotionPage.toString());

        Set<Promotion> promotionSet = Sets.newHashSet((Iterable<Promotion>) promotionPage);
        log.info("promotion list "+promotionSet.toString());



    }

    @Test
    public void test9GetPromotionsForUser() throws InspireNetzException {


        User user = new User();
        user.setUsrUserNo(12L);
        user.setUsrLoginId("4444444444");
        //user.setUsrLoginId("gr.sandeep1@gmail.com");
        //user.setUsrMobile("9538828853");


        // Get the list of the promotions for the user
        Page<Promotion> promotions = promotionService.getPromotionsForUser(user.getUsrLoginId(),0L,"",constructPageSpecification(0));
        Assert.assertNotNull(promotions);
        log.info("Promotion List " + promotions.toString());

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
