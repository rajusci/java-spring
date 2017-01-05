package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.repository.PromotionRepository;
import com.inspirenetz.api.core.repository.UserResponseRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PromotionService;
import com.inspirenetz.api.core.service.UserResponseService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.PromotionFixture;
import com.inspirenetz.api.test.core.fixture.UserResponseFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 9/11/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class UserResponseServiceTest {

    private static Logger log = LoggerFactory.getLogger(UserResponseServiceTest.class);

    @Autowired
    private UserResponseService userResponseService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;


    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    Set<UserResponse> tempSet = new HashSet<>(0);

    Set<Promotion> tempSet1 = new HashSet<>(0);

    Set<Customer> tempSet2 = new HashSet<>(0);



    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1saveUserResponse() throws InspireNetzException {

        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        tempSet1.add(promotion);

        UserResponse userResponse = userResponseService.saveUserResponse(UserResponseFixture.standardUserResponse(promotion));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());
        // Add to tempSet
        tempSet.add(userResponse);

    }



    @Test
    public void test2findByUspUserNo() throws InspireNetzException {

        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        tempSet1.add(promotion);

        UserResponse userResponse = userResponseService.saveUserResponse(UserResponseFixture.standardUserResponse(promotion));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());
        // Add to tempSet
        tempSet.add(userResponse);

        UserResponse userResponse1 =userResponseService.findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(userResponse.getUrpUserNo(),userResponse.getUrpResponseItemType(),userResponse.getUrpResponseItemId(),userResponse.getUrpResponseType());
        Assert.assertNotNull(userResponse1);

        log.info("user response"+userResponse1);
    }

    @Test
    public void findByUrpResponseItemIdAndUrpResponseType() throws InspireNetzException {

        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        tempSet1.add(promotion);

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        tempSet2.add(customer);

        UserResponse userResponse = userResponseService.saveUserResponse(UserResponseFixture.standardUserResponse(promotion,customer));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());
        // Add to tempSet
        tempSet.add(userResponse);

        Page<UserResponse> userResponse1 =userResponseService.findByUrpResponseItemIdAndUrpResponseType("0","0",userResponse.getUrpResponseItemId(),userResponse.getUrpResponseType(),constructPageSpecification(0));
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
    public void tearDown() throws InspireNetzException {

        for ( UserResponse userResponse : tempSet ) {

            userResponseService.deleteUserResponse(userResponse);

        }

        for(Promotion promotion :tempSet1){

            promotionRepository.delete(promotion);
        }


//        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: tempSet2) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }
    }
}
