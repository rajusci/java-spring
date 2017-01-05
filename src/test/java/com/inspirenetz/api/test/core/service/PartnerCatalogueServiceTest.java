package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.dictionary.PartnerCatalogueStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PartnerCatalogueService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.PartnerCatalogueFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class PartnerCatalogueServiceTest {


    private static Logger log = LoggerFactory.getLogger(PartnerCatalogueServiceTest.class);

    @Autowired
    private PartnerCatalogueService partnerCatalogueService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<PartnerCatalogue> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        PartnerCatalogue partnerCatalogue = partnerCatalogueService.savePartnerCatalogue(PartnerCatalogueFixture.standardPartnerCatalogue());
        log.info(partnerCatalogue.toString());

        // Add the items
        tempSet.add(partnerCatalogue);

        Assert.assertNotNull(partnerCatalogue.getPacId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        PartnerCatalogue partnerCatalogue = partnerCatalogueService.savePartnerCatalogue(PartnerCatalogueFixture.standardPartnerCatalogue());
        partnerCatalogue=partnerCatalogueService.findByPacId(partnerCatalogue.getPacId());
        tempSet.add(partnerCatalogue);
        log.info("partnerCatalogue select data"+partnerCatalogue.getPacId());

        PartnerCatalogue updatedPartnerCatalogue = PartnerCatalogueFixture.updatedStandardPartnerCatalogue(partnerCatalogue);
        log.info("Updated PartnerCatalogue "+ updatedPartnerCatalogue.toString());
        updatedPartnerCatalogue = partnerCatalogueService.validateAndSavePartnerCatalogue(updatedPartnerCatalogue);
//        tempSet.add(partnerCatalogue);
        log.info("Updated PartnerCatalogue "+ updatedPartnerCatalogue.toString());

    }

    @Test
    public void test3FindByDrcId() throws InspireNetzException {

        PartnerCatalogueFixture partnerCatalogueFixture=new PartnerCatalogueFixture();

        PartnerCatalogue partnerCatalogue = partnerCatalogueFixture.standardPartnerCatalogue();
        partnerCatalogue = partnerCatalogueService.savePartnerCatalogue(partnerCatalogue);
        log.info("Original PartnerCatalogues " + partnerCatalogue.toString());

        // Add the items
        tempSet.add(partnerCatalogue);

        PartnerCatalogue searchRequest = partnerCatalogueService.findByPacId(partnerCatalogue.getPacId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(partnerCatalogue.getPacId().longValue() == searchRequest.getPacId().longValue());



    }

    @Test
    public void test4UpdatePartnerCatalogueStatus() throws InspireNetzException {

        // Get the standard loyalty program
        PartnerCatalogue   partnerCatalogue = PartnerCatalogueFixture.standardPartnerCatalogue();
        partnerCatalogue = partnerCatalogueService.savePartnerCatalogue(partnerCatalogue);
        Assert.assertNotNull(partnerCatalogue);

        // Set the loyalty Program status
        boolean updated = partnerCatalogueService.updatePartnerCatalogueStatus(partnerCatalogue.getPacId(), PartnerCatalogueStatus.DISABLED,1L,1L);
        Assert.assertTrue(updated);

        // Read the partnerCatalogue
        partnerCatalogue = partnerCatalogueService.findByPacId(partnerCatalogue.getPacId());
        Assert.assertTrue(partnerCatalogue.getPacStatus() == PartnerCatalogueStatus.DISABLED);
        log.info("Partner Catalogue status updated successfully");


    }



    @After
    public void tearDown() throws InspireNetzException {
        for(PartnerCatalogue partnerCatalogue: tempSet) {

            partnerCatalogueService.deletePartnerCatalogue(partnerCatalogue.getPacId());

        }

        for(Customer customer:customerSet){


            customerService.deleteCustomer(customer.getCusCustomerNo());
        }


    }


}
