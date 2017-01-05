package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CatalogueDisplayPreferenceService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CatalogueDisplayPreferenceFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * Created by saneesh-ci on 9/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CatalogueDisplayPreferenceServiceTest {


    private static Logger log = LoggerFactory.getLogger(CatalogueDisplayPreferenceServiceTest.class);

    @Autowired
    private CatalogueDisplayPreferenceService catalogueDisplayPreferenceService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;


    Set<CatalogueDisplayPreference> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceService.saveDisplayPreference(CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference());
        log.info(catalogueDisplayPreference.toString());

        // Add the items
        tempSet.add(catalogueDisplayPreference);

        Assert.assertNotNull(catalogueDisplayPreference.getCdpId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CatalogueDisplayPreference catalogueDisplayPreference = new CatalogueDisplayPreference();
        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        catalogueDisplayPreference = catalogueDisplayPreferenceService.saveDisplayPreference(CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference());
        catalogueDisplayPreference=catalogueDisplayPreferenceService.findByCdpId(catalogueDisplayPreference.getCdpId());
        log.info("catalogueDisplayPreference select data"+catalogueDisplayPreference.getCdpId());

        CatalogueDisplayPreference updatedCatalogueDisplayPreference = CatalogueDisplayPreferenceFixture.updatedStandardCataloguDisplayPreference(catalogueDisplayPreference);
        log.info("Updated CatalogueDisplayPreference "+ updatedCatalogueDisplayPreference.toString());
        updatedCatalogueDisplayPreference = catalogueDisplayPreferenceService.validateAndSaveCatalogueDisplayPreference(updatedCatalogueDisplayPreference);
//        tempSet.add(catalogueDisplayPreference);
        log.info("Updated CatalogueDisplayPreference "+ updatedCatalogueDisplayPreference.toString());

    }

    @Test
    public void test3FindByCpdId() throws InspireNetzException {

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference();
        catalogueDisplayPreference = catalogueDisplayPreferenceService.saveDisplayPreference(catalogueDisplayPreference);
        log.info("Original CatalogueDisplayPreferences " + catalogueDisplayPreference.toString());

        // Add the items
        tempSet.add(catalogueDisplayPreference);

        CatalogueDisplayPreference searchRequest = catalogueDisplayPreferenceService.findByCdpId(catalogueDisplayPreference.getCdpId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(catalogueDisplayPreference.getCdpId().longValue() == searchRequest.getCdpId().longValue());



    }


    @Test
    public void test4getCatalogueDisplayPreferences() throws InspireNetzException {

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference();
        catalogueDisplayPreference = catalogueDisplayPreferenceService.saveDisplayPreference(catalogueDisplayPreference);
        log.info("Original CatalogueDisplayPreferences " + catalogueDisplayPreference.toString());

        // Add the items
        tempSet.add(catalogueDisplayPreference);

        CatalogueDisplayPreference catalogueDisplayPreference1 = catalogueDisplayPreferenceService.getCatalogueDisplayPreference(catalogueDisplayPreference.getCdpMerchantNo());
        Assert.assertNotNull(catalogueDisplayPreference1);
        log.info("CatalogueDisplayPreference List : "+ catalogueDisplayPreference1);

    }




    @Test
    public void test6SaveAndValidateCatalogueDisplayPreference() throws InspireNetzException {

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceService.validateAndSaveCatalogueDisplayPreference(catalogueDisplayPreferenceFixture.standardCataloguDisplayPreference());
        log.info(catalogueDisplayPreference.toString());

        // Add the items
        tempSet.add(catalogueDisplayPreference);

        Assert.assertNotNull(catalogueDisplayPreference.getCdpId());

    }







    @After
    public void tearDown() throws InspireNetzException {
        for(CatalogueDisplayPreference catalogueDisplayPreference: tempSet) {

            catalogueDisplayPreferenceService.deleteDisplayPreference(catalogueDisplayPreference.getCdpId());

        }
        for(Customer customer: customerSet) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

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
        return new Sort(Sort.Direction.ASC, "rvrVoucherCode");
    }

}
