package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.config.NotificationConfig;
import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.drools.core.util.PrimitiveLongStack;
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

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})
public class CatalogueServiceTest {


    private static Logger log = LoggerFactory.getLogger(CatalogueServiceTest.class);

    @Autowired
    private CatalogueService catalogueService;


    @Autowired
    private CodedValueService codedValueService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    RedemptionVoucherService redemptionVoucherService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    CatalogueFavoriteService catalogueFavoriteService;

    @Autowired
    private GeneralUtils generalUtils;

    //Set for user temp
    Set<User> usrSet = new HashSet<>(0);

    //set for customer temp
    Set<Customer> cusSet = new HashSet<>(0);

    //set for catalogue temp
    Set<Catalogue> catSet = new HashSet<>(0);

    //set for customer reward balance
    Set<CustomerRewardBalance> crbSet = new HashSet<>(0);

    //set for catalogue favorites
    Set<CatalogueFavorite> cafSet = new HashSet<>(0);

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void testFindByCatProductNo() throws Exception {

        // Create the prodcut
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue = catalogueService.saveCatalogue(catalogue);

        Catalogue searchCatalogue = catalogueService.findByCatProductNo(catalogue.getCatProductNo());
        Assert.assertNotNull(searchCatalogue);
        log.info("Catalogue Information" + searchCatalogue.toString());
    }

    @Test
    public void testFindByCatMerchantNo() throws Exception {

        Set<Catalogue> catalogues = CatalogueFixture.standardCatalogues();
        List<Catalogue> catalogueList = Lists.newArrayList((Iterable<Catalogue>)catalogues);
        catalogueService.saveAll(catalogueList);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();

        Page<Catalogue>  cataloguePage = catalogueService.findByCatMerchantNo(catalogue.getCatMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(cataloguePage.hasContent());
        catalogueList = Lists.newArrayList((Iterable<Catalogue>) cataloguePage);
        log.info("Catalogue List" + catalogueList);

    }

    @Test
    public void testFindByCatProductCodeAndCatMerchantNo() throws Exception {

        // Create the prodcut
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueService.saveCatalogue(catalogue);

        Catalogue searchCatalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(catalogue.getCatProductCode(),catalogue.getCatMerchantNo());
        Assert.assertNotNull(searchCatalogue);
        log.info("Catalogue information " + searchCatalogue.toString());

    }



    @Test
    public void testFindByCatMerchantNoAndCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueService.saveCatalogue(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueService.findByCatMerchantNoAndCatDescriptionLike(catalogue.getCatMerchantNo(),"%item%",constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>)catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }


    @Test
    public void testgetCatalogueExpiryDate() throws InspireNetzException {

        // Save the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatRedemptionType(CatalogueRedemptionType.VOUCHER_BASED);
        catalogue.setCatRedemptionVoucherExpiry(RedemptionVoucherExpiryOption.EXPIRYDATE);
        catalogue.setCatRedemptionVoucherExpiryDate(DBUtils.covertToSqlDate("2015-02-07"));
        catalogueService.saveCatalogue(catalogue);

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrCreateDate(DBUtils.covertToSqlDate("07-02-2015"));

        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        log.info("Original RedemptionVouchers " + redemptionVoucher.toString());


        // New Catalogue
        Date date=catalogueService.getExpiryDateForVoucher(catalogue,redemptionVoucher.getRvrCreateDate());

        Assert.assertNotNull(date);

        log.info("Expiry date"+date);



    }




    @Test
    public void searchCatalogueByCurrencyAndCategory() throws InspireNetzException {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueService.saveCatalogue(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        String loyaltyId = "=96538828853";

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(catalogue.getCatMerchantNo(),1L,0, loyaltyId, RequestChannel.RDM_CHANNEL_SMS,constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());

    }




    @Test
    public void searchCatalogueByCurrencyAndCategoryNew() throws InspireNetzException {

        Catalogue catalogue  = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerTier("");
        catalogue.setCatCustomerType(0);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-09-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2014-11-30")));
        catalogue.setCatLocationValues("46");

        catalogue.setCatProductValues("BUDDY");

        //saving catalogue object
        catalogue=catalogue= catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);


        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(catalogue.getCatMerchantNo(),1L,0, "9538828853", RequestChannel.RDM_CHANNEL_SMS, constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());

    }


    @Test
    public void getCatalogueListCompatible() throws InspireNetzException {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();

        catalogue=catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        //for creating customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("987458");
        customer=customerService.saveCustomer(customer);

        Assert.assertNotNull(customer.getCusCustomerNo());
        cusSet.add(customer);
        log.info("Customer created");

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardBalance(100.0);
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance=customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Add to the set
        crbSet.add(customerRewardBalance);


        CodedValue codedValue = codedValueService.findByCdvIndexAndCdvCodeValue(CodedValueIndex.CATALOGUE_PRODUCT_CATEGORY,catalogue.getCatCategory());

        // Check the catalogue name
        List<Catalogue> catalogues = catalogueService.getCatalogueListCompatible(codedValue.getCdvCodeLabel(),customer.getCusLoyaltyId(),catalogue.getCatMerchantNo(),"0") ;

        log.info("catalogue list "+catalogues);

    }

    @Test
    public void getPublicCatalogues() throws InspireNetzException {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerType(4);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2015-05-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2015-05-20")));
        catalogue.setCatChannelValues("2");
        catalogue.setCatAvailableStock(10L);
        catalogue=catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // get the catalogues for public
        Page<Catalogue> cataloguePage = catalogueService.getPublicCatalogues(0L,catalogue.getCatCategory(), RequestChannel.RDM_WEB,"%%", constructPageSpecification(0)) ;

        Assert.assertNotNull(cataloguePage);

        Assert.assertTrue(cataloguePage.hasContent())  ;

        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) cataloguePage);

        log.info("catalogue list "+catalogueSet.toString());

    }

    @Test
    public void listCataloguesUser() throws InspireNetzException {


        /*//create user
        User user= UserFixture.standardUser();
        user.setUsrUserType(UserType.CUSTOMER);
        user=userService.saveUser(user);
        Assert.assertNotNull(user.getUsrUserNo());
        usrSet.add(user);

        log.info("User created");

        //for creating customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusUserNo(user.getUsrUserNo());
        customer=customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        cusSet.add(customer);
        log.info("Customer created");

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardBalance(100.0);
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance=customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatMerchantNo(customer.getCusMerchantNo());
        catalogue.setCatStartDate(new java.sql.Date( generalUtils.addDaysToToday(-10).getTime()));
        catalogue.setCatEndDate(new java.sql.Date( generalUtils.addDaysToToday(10).getTime()));
        catalogue.setCatAvailableStock(100L);
        catalogue.setCatLocationValues(customer.getCusLocation()+"");
        catalogue=catalogueService.saveCatalogue(catalogue);

        Assert.assertNotNull(catalogue.getCatProductNo());
        catSet.add(catalogue);
        log.info("Catalogue created");user.getUsrLoginId()*/

        // get the catalogues for public

        Page<Catalogue> cataloguePage = catalogueService.listCataloguesUser(0L,"4444444444","name","%%","","0",RequestChannel.RDM_WEB, constructPageSpecification(0)) ;

        Assert.assertNotNull(cataloguePage);

        Assert.assertTrue(cataloguePage.hasContent());

        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) cataloguePage);

        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void searchCatalogueByCatMerchantNoAndCatCategoryAndSortOption() throws InspireNetzException {


        /*//create user
        User user= UserFixture.standardUser();
        user.setUsrUserType(UserType.CUSTOMER);
        user=userService.saveUser(user);
        Assert.assertNotNull(user.getUsrUserNo());
        usrSet.add(user);

        log.info("User created");

        //for creating customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusUserNo(user.getUsrUserNo());
        customer=customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        cusSet.add(customer);
        log.info("Customer created");

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardBalance(100.0);
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance=customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatMerchantNo(customer.getCusMerchantNo());
        catalogue.setCatStartDate(new java.sql.Date( generalUtils.addDaysToToday(-10).getTime()));
        catalogue.setCatEndDate(new java.sql.Date( generalUtils.addDaysToToday(10).getTime()));
        catalogue.setCatAvailableStock(100L);
        catalogue.setCatLocationValues(customer.getCusLocation()+"");
        catalogue=catalogueService.saveCatalogue(catalogue);

        Assert.assertNotNull(catalogue.getCatProductNo());
        catSet.add(catalogue);
        log.info("Catalogue created");user.getUsrLoginId()*/

        String usrLoginId=authSessionUtils.getUserLoginId();

        // get the catalogues for pu      
        Page<Catalogue> cataloguePage = catalogueService.searchCatalogueByCatMerchantNoAndCatCategoryAndSortOption(usrLoginId,0L, 0, "name", "%%", CatalogueSortOptionCodedValues.POINT_LOW_TO_HIGH, RequestChannel.RDM_WEB, constructPageSpecification(0)) ;

        Assert.assertNotNull(cataloguePage);

        Assert.assertTrue(cataloguePage.hasContent());

        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) cataloguePage);

        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void getCatalogueFavouritesForUser() throws InspireNetzException {


        //create user
        User user= UserFixture.standardUser();
        user.setUsrUserType(UserType.CUSTOMER);
        user=userService.saveUser(user);
        Assert.assertNotNull(user.getUsrUserNo());
        usrSet.add(user);

        log.info("User created");

        //for creating customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusUserNo(user.getUsrUserNo());
        customer=customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        cusSet.add(customer);
        log.info("Customer created");

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardBalance(100.0);
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance=customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatMerchantNo(customer.getCusMerchantNo());
        catalogue.setCatStartDate(new java.sql.Date( generalUtils.addDaysToToday(-10).getTime()));
        catalogue.setCatEndDate(new java.sql.Date( generalUtils.addDaysToToday(10).getTime()));
        catalogue.setCatAvailableStock(100L);
        catalogue.setCatLocationValues(customer.getCusLocation()+"");
        catalogue=catalogueService.saveCatalogue(catalogue);

        Assert.assertNotNull(catalogue.getCatProductNo());
        catSet.add(catalogue);
        log.info("Catalogue created");

        CatalogueFavorite catalogueFavorite = CatalogueFavoriteFixture.standardCatalogueFavorite();
        catalogueFavorite.setCafLoyaltyId(customer.getCusLoyaltyId());
        catalogueFavorite.setCafProductNo(catalogue.getCatProductNo());
        catalogueFavorite = catalogueFavoriteService.saveCatalogueFavorite(catalogueFavorite);
        log.info(catalogueFavorite.toString());
        Assert.assertNotNull(catalogueFavorite.getCafId());

        cafSet.add(catalogueFavorite);

        // get the catalogues for catalogueFavorite
        List<Catalogue> catalogues = catalogueService.getCatalogueFavouritesForUser(user.getUsrLoginId(),0L,"") ;

        Assert.assertNotNull(catalogues);

        Assert.assertFalse(catalogues.isEmpty());

        log.info("catalogue list "+catalogues.toString());
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
        return new Sort(Sort.Direction.ASC, "catProductNo");
    }


    @After
    public void tearDown() throws Exception {

        Set<Catalogue> catalogues = CatalogueFixture.standardCatalogues();

        for(Catalogue catalogue: catalogues) {

            Catalogue delCatalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(catalogue.getCatProductCode(),catalogue.getCatMerchantNo());

            if ( delCatalogue != null ) {
                catalogueService.deleteCatalogue(delCatalogue.getCatProductNo());
            }

        }
        for(CustomerRewardBalance customerRewardBalance: crbSet) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

        }

        for(CatalogueFavorite catalogueFavorite: cafSet) {

            catalogueFavoriteService.delete(catalogueFavorite);

        }

        for(Catalogue catalogue: catSet) {

            catalogueService.deleteCatalogue(catalogue.getCatProductNo());

        }

        for(Customer customer: cusSet) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(User user: usrSet) {

            userService.deleteUser(user);
        }
    }

}
