package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.RequestChannel;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.UserAccessLocationRepository;
import com.inspirenetz.api.core.repository.UserAccessRightRepository;
import com.inspirenetz.api.core.repository.UserRepository;
import com.inspirenetz.api.core.repository.UserRoleRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
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
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, NotificationTestConfig.class,SecurityTestConfig.class})
public class UserServiceTest {


    private static Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserRepository userRepository;

    Set<UserRole> roleSet = new HashSet<>(0);

    Set<User> tempSet = new HashSet<>(0);

    Set<UserAccessLocation> locationSet = new HashSet<>(0);

    Set<Function> functionSet = new HashSet<>(0);

    Set<Role> rolSet = new HashSet<>(0);

    Set<RoleAccessRight> rarSet = new HashSet<>(0);

    Set<UserAccessRight> accessRightsSet = new HashSet<>(0);

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserAccessLocationService userAccessLocationService;

    @Autowired
    private UserAccessRightService userAccessRightService;

    @Autowired
    private RoleAccessRightService roleAccessRightService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

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
    public void test1GetDataByUsrUserNo() throws InspireNetzException {

        //Saving first function
        Function function = FunctionFixture.standardFunction();
        function.setFncFunctionName("Function1");
        function = functionService.saveFunction(function);
        functionSet.add(function);

        //Saving Second  function
        Function secFunction = FunctionFixture.standardFunction();
        secFunction.setFncFunctionName("Function2");
        secFunction = functionService.saveFunction(secFunction);
        functionSet.add(secFunction);

        //Saving Third  function
        Function thirdFunction = FunctionFixture.standardFunction();
        thirdFunction.setFncFunctionName("Function3");
        thirdFunction = functionService.saveFunction(thirdFunction);
        functionSet.add(thirdFunction);

        //Saving Third  function
        Function fourthFunction = FunctionFixture.standardFunction();
        fourthFunction.setFncFunctionName("Function4");
        fourthFunction = functionService.saveFunction(fourthFunction);
        functionSet.add(fourthFunction);

        Role role = RoleFixture.standardRole();
        role.setRolName("Test Role A");
        role=roleService.saveRole(role);
        rolSet.add(role);

        Role anotherRole = RoleFixture.standardRole();
        anotherRole.setRolName("Test Role B");
        anotherRole = roleService.saveRole(anotherRole);
        rolSet.add(anotherRole);

        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight.setRarRole(role.getRolId());
        roleAccessRight.setRarFunctionCode(function.getFncFunctionCode());
        roleAccessRight = roleAccessRightService.saveRoleAccessRight(roleAccessRight);
        rarSet.add(roleAccessRight);

        RoleAccessRight roleAccessRight1 = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight1.setRarRole(role.getRolId());
        roleAccessRight1.setRarFunctionCode(secFunction.getFncFunctionCode());
        roleAccessRight1 = roleAccessRightService.saveRoleAccessRight(roleAccessRight1);
        rarSet.add(roleAccessRight1);

        RoleAccessRight roleAccessRight2 = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight2.setRarRole(anotherRole.getRolId());
        roleAccessRight2.setRarFunctionCode(thirdFunction.getFncFunctionCode());
        roleAccessRight2 = roleAccessRightService.saveRoleAccessRight(roleAccessRight2);
        rarSet.add(roleAccessRight2);

        // For setting user role data
        Set<UserRole> userRoles=new HashSet<>();

        UserRole userRole=new UserRole();
        userRole.setUerRole(role.getRolId());
        userRoles.add(userRole);
        roleSet.add(userRole);

        UserRole userRole1=new UserRole();
        userRole1.setUerRole(anotherRole.getRolId());
        userRoles.add(userRole1);
        roleSet.add(userRole1);
        // For setting user access location data
        Set<UserAccessLocation> userAccessLocations=new HashSet<>();

        UserAccessLocation userAccessLocation=new UserAccessLocation();
        userAccessLocation.setUalLocation(20L);
        userAccessLocations.add(userAccessLocation);
        locationSet.add(userAccessLocation);

        UserAccessLocation userAccessLocation1=new UserAccessLocation();
        userAccessLocation1.setUalLocation(21L);
        userAccessLocations.add(userAccessLocation1);
        locationSet.add(userAccessLocation1);

        // For setting user access location data


        User user = UserFixture.standardUser();
        user.setUserAccessLocationSet(userAccessLocations);
     //   user.setUserAccessRightsSet(userAccessRights);
        user.setUserRoleSet(userRoles);



        log.info("Original role access right " + roleAccessRight.toString());

        user = userService.saveUser(user);

        user = userService.getUserDataByUsrUserNo(user.getUsrUserNo());

        Set<UserAccessRight> userAccessRights=new HashSet<>();

        UserAccessRight userAccessRight=new UserAccessRight();
        userAccessRight.setUarFunctionCode(secFunction.getFncFunctionCode());
        userAccessRight.setUarAccessEnableFlag("N");
        userAccessRight.setUarUserNo(user.getUsrUserNo());
        userAccessRights.add(userAccessRight);
        accessRightsSet.add(userAccessRight);

        UserAccessRight userAccessRight1=new UserAccessRight();
        userAccessRight1.setUarFunctionCode(fourthFunction.getFncFunctionCode());
        userAccessRight1.setUarAccessEnableFlag("Y");
        userAccessRight1.setUarUserNo(user.getUsrUserNo());
        userAccessRights.add(userAccessRight1);
        accessRightsSet.add(userAccessRight);

        user.setUserAccessRightsSet(userAccessRights);
        userService.saveUser(user);

        log.info("retrieved data :"+user.getUserRoleSet().toString()+"-"+user.getUserAccessRightsSet()+"-"+user.getUserAccessLocationSet());

       /* Assert.assertTrue(user.getUserRoleSet().size() == 2);

        Assert.assertTrue(user.getUserAccessLocationSet().size() == 2);

        Assert.assertTrue(user.getUserAccessRightsSet().size() == 5);*/
    }



    @Test
    public void test2Update() throws InspireNetzException {

        // For setting user role data
        Set<UserRole> userRoles=new HashSet<>();

        UserRole userRole=new UserRole();
        userRole.setUerRole(10L);
        userRoles.add(userRole);
        roleSet.add(userRole);

        UserRole userRole1=new UserRole();
        userRole1.setUerRole(11L);
        userRoles.add(userRole1);
        roleSet.add(userRole1);
        // For setting user access location data
        Set<UserAccessLocation> userAccessLocations=new HashSet<>();

        UserAccessLocation userAccessLocation=new UserAccessLocation();
        userAccessLocation.setUalLocation(20L);
        userAccessLocations.add(userAccessLocation);
        locationSet.add(userAccessLocation);

        UserAccessLocation userAccessLocation1=new UserAccessLocation();
        userAccessLocation1.setUalLocation(21L);
        userAccessLocations.add(userAccessLocation1);
        locationSet.add(userAccessLocation1);

        // For setting user access location data
        Set<UserAccessRight> userAccessRights=new HashSet<>();

        UserAccessRight userAccessRight=new UserAccessRight();
        userAccessRight.setUarFunctionCode(30L);
        userAccessRights.add(userAccessRight);
        accessRightsSet.add(userAccessRight);

        UserAccessRight userAccessRight1=new UserAccessRight();
        userAccessRight1.setUarFunctionCode(31L);
        userAccessRights.add(userAccessRight1);
        accessRightsSet.add(userAccessRight1);

        User user = UserFixture.standardUser();
        user.setUserAccessLocationSet(userAccessLocations);
        user.setUserAccessRightsSet(userAccessRights);
        user.setUserRoleSet(userRoles);

        user = userService.saveUser(user);

        user = userService.getUserDataByUsrUserNo(user.getUsrUserNo());
        userRoles = user.getUserRoleSet();
        userAccessLocations = user.getUserAccessLocationSet();
        userAccessRights = user.getUserAccessRightsSet();

        userAccessLocations.removeAll(userAccessLocations);

        for(UserRole userRole2 : userRoles){
            if(userRole2.getUerRole() == userRole.getUerRole()){

                userRoles.remove(userRole2);

            }
        }

        UserRole userRole3 = new UserRole();
        userRole3.setUerRole(111L);
        userRoles.add(userRole3);

        user.setUserRoleSet(userRoles);
        userService.saveUser(user);

    }

    @Test
    public void test3FindByUsrUserNo() throws InspireNetzException  {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userService.saveUser(user);


        User searchUser = userService.findByUsrUserNo(user.getUsrUserNo());
        Assert.assertNotNull(searchUser);
        log.info("User information " + user.toString());

    }


    @Test
    public void test3FindByUsrLoginId() throws InspireNetzException {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userService.saveUser(user);


        User searchUser = userService.findByUsrLoginId(user.getUsrLoginId());
        Assert.assertNotNull(searchUser);
        log.info("User information " + user.toString());

    }


    @Test
    public void test4FindByUsrMerchantNo() throws InspireNetzException {

        Set<User> userSet = UserFixture.standardUsers();
        List<User> userList = Lists.newArrayList((Iterable<User>) userSet) ;
        userService.saveAll(userList);

        // Get the standard user
        User user = UserFixture.standardUser();

        // Get the list of users
        List<User> userList1 = userService.findByUsrMerchantNo(user.getUsrMerchantNo());
        Assert.assertTrue(!userList1.isEmpty());
        log.info("User information : "+ userList1.toString());

    }



    @Test
    public void test5AuthenticateUser() throws InspireNetzException {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userService.saveUser(user);

        // Create the search user
        User authenticateUser = userService.authenticateUser(user.getUsrLoginId(),user.getUsrPassword());
        Assert.assertNotNull(authenticateUser);
        log.info("authenticated user" + authenticateUser.toString());


    }


    @Test
    public void test6IsDuplicateUserExisting() throws InspireNetzException {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userService.saveUser(user);


        // Get a new user
        User checkUser = UserFixture.standardUser();


        // Check if the user exists
        boolean isExists = userService.isDuplicateUserExisting(checkUser);
        Assert.assertTrue(isExists);
        log.info("duplicate user exists");


    }


    @Test
    public void test7SearchMerchantUsers() throws InspireNetzException {

        Set<User> userSet = UserFixture.standardUsers();
        List<User> userList = Lists.newArrayList((Iterable<User>) userSet) ;
        userService.saveAll(userList);

        // Get the standard user
        User user = UserFixture.standardUser();

        // Get the list of users
        Page<User> userPage = userService.searchMerchantUsers(user.getUsrMerchantNo(), 0, "username", "test", constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        //Convert to list
        List<User> userList1 = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information : "+ userList1.toString());


    }


    @Test
    public void test8GetEncodedPassword() {

        String password = "virtual1";
        String username = "sandeepmenon";

        String digest = userService.getEncodedPassword(username,password);
        Assert.assertNotNull(digest);
        log.info("encoded password : " + digest);

    }

    @Test
    public void test9GetRedemptionMerchantUsers() throws InspireNetzException {

        User user = UserFixture.standardUser();
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        userService.saveUser(user);


        // Get the list of users
        Page<User> userPage = userService.getRedemptionMerchantUsers(constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        //Convert to list
        List<User> userList1 = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information : "+ userList1.toString());


    }

    @Test
    public void getRedemptioUser() throws InspireNetzException {

        User user = UserFixture.standardUser();
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user = userService.saveUser(user);

        // Get the list of users
        List<User> userList = userService.findByUsrThirdPartyVendorNo(user.getUsrThirdPartyVendorNo());
        Assert.assertNotNull(userList);

        log.info("User information : "+ userList.toString());


    }



    @Test
    public void test10searchRedemptionMerchantUsers() throws InspireNetzException {

        User user = UserFixture.standardUser();
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user.setUsrThirdPartyVendorNo(696L);
        userService.saveUser(user);


        // Get the list of users
        Page<User> userPage = userService.searchRedemptionMerchantUsers(user.getUsrMerchantNo(),user.getUsrThirdPartyVendorNo(),"username","test",constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        //Convert to list
        List<User> userList1 = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information : "+ userList1.toString());


    }

    @Test
    public void test11testUserSaveWithSecuritySettings() throws InspireNetzException{

        User user = UserFixture.standardUser();

        user = userService.saveUser(user);

        user.setUsrPassword("");

        user = userService.saveUser(user);

    }

    @Test
    public void test12GetUserAccessRights() throws InspireNetzException{

      userService.getUserAccessRightsByUserNo(4264L);

    }

    @Test
    public void registerUser() throws InspireNetzException{

        User user = UserFixture.standardUser();

        // Call the register customer
        boolean isValid = userService.registerUser(user.getUsrFName(), user.getUsrLName(), user.getUsrLoginId(), user.getUsrEmail(),user.getUsrPassword(), RequestChannel.RDM_WEB,1L);
        Assert.assertTrue(isValid);

    }

    @Test
    public void confirmUserRegistration() throws InspireNetzException{

        User user = UserFixture.standardUser();
        user.setUsrLoginId("177771");

        // Call the register customer
       // boolean isValid = userService.registerUser(user.getUsrFName(), user.getUsrLName(), user.getUsrLoginId(), user.getUsrPassword());

        boolean isConfirmed=userService.confirmUserRegistration(user.getUsrLoginId(),"529053", RequestChannel.RDM_WEB,1L);

        Assert.assertTrue(isConfirmed);

    }

    @Test
    public void generateForgetPasswordOtp() throws InspireNetzException{

        User user = UserFixture.standardUser();

        user=userService.saveUser(user);

        boolean isGenerated=userService.generateForgetPasswordOtp(user.getUsrLoginId(),0L);

        Assert.assertTrue(isGenerated);

    }

    @Test
    public void forgetUserPasswordOtpValidation() throws InspireNetzException{

        User user = UserFixture.standardUser();

        user.setUsrLoginId("177772");

        //user=userService.saveUser(user);

        //boolean isGenerated=userService.generateForgetPasswordOtp(user.getUsrLoginId());

        user =userService.forgetUserPasswordOtpValidation(user.getUsrLoginId(),"welcome12","382454",0L);

    }

    @Test
    public void getUserMemberships() throws InspireNetzException{

        User user = UserFixture.standardUser();



        user=userService.saveUser(user);

        Customer customer = CustomerFixture.standardCustomer();


        customer.setCusUserNo(user.getUsrUserNo());

        customerService.saveCustomer(customer);

        List<Map<String, Object>> membershipMap =userService.getUserMemberships(0L,user.getUsrLoginId(),"0","0");

        Assert.assertNotNull(membershipMap);

        log.info("get user membership "+membershipMap.toString());

    }

    @Test
    public void generateUsrUserCode() throws InspireNetzException{

        String usrUserCode=userService.generateUsrUserCode();


        Assert.assertNotNull(usrUserCode);

        log.info("generateUsrUserCode "+usrUserCode);

    }

    @Test
    public void getUsrUserCode() throws InspireNetzException{

        User user=UserFixture.standardUser();
        user.setUsrUserType(UserType.CUSTOMER);

        user=userService.saveUser(user);

        String usrUserCode=userService.getUsrUserCode(user.getUsrUserNo());


        Assert.assertNotNull(usrUserCode);

        log.info("getUsrUserCode "+usrUserCode);

    }

    @Test
    public void findByUsrUserCode() throws InspireNetzException{

        User user=UserFixture.standardUser();
        user.setUsrUserType(UserType.CUSTOMER);
        user.setUsrUserCode(userService.generateUsrUserCode());

        user=userService.saveUser(user);

        User fetchedUser=userService.findByUsrUserCode(user.getUsrUserCode());


        Assert.assertNotNull(fetchedUser);

        log.info("findByUsrUserCode : response - "+fetchedUser.toString());

    }

    @After
    public void tearDown()  throws InspireNetzException{

        Set<User> users = UserFixture.standardUsers();

        for(User user: users) {

            User delUser = userRepository.findByUsrLoginId(user.getUsrLoginId());

            if ( delUser != null ) {
                userRepository.delete(delUser);
            }

        }

        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: customers) {

            Customer delCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(), customer.getCusMerchantNo());

            if ( delCustomer != null ) {

                customerService.deleteCustomer(delCustomer.getCusCustomerNo());
            }

        }

        userRoleService.deleteUserRoleSet(roleSet);

        for(Role role : rolSet){

            roleService.deleteRole(role.getRolId());

        }
        for(Function function : functionSet){

            functionService.deleteFunction(function.getFncFunctionCode());

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
        return new Sort(Sort.Direction.ASC, "usrLoginId");
    }
}
