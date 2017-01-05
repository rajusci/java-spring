package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.UserRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class UserControllerTest {


    private static Logger log = LoggerFactory.getLogger(UserControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    UserRepository userRepository;

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

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CustomerService customerService;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // User object
    private User user;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<UserRole> roleSet = new HashSet<>(0);

    Set<User> tempSet = new HashSet<>(0);

    Set<UserAccessLocation> locationSet = new HashSet<>(0);

    Set<Function> functionSet = new HashSet<>(0);

    Set<Role> rolSet = new HashSet<>(0);

    Set<RoleAccessRight> rarSet = new HashSet<>(0);

    Set<UserAccessRight> accessRightsSet = new HashSet<>(0);




    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void useCustomerSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the user
        user = UserFixture.standardUser();


    }




    @Test
    public void authenticateUser() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/authenticate")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void saveMerchantUser() throws Exception  {

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


        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userData = objectMapper.writeValueAsString(user);
        log.info("JSON string : " + userData);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(userData)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void updateMerchantUser() throws Exception  {

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


        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userData = objectMapper.writeValueAsString(user);
        log.info("JSON string : " + userData);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(userData)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Long usrUserNo = Long.parseLong(map.get("data"));

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));
        user.setUsrUserNo(usrUserNo);

        Set<UserAccessRight> userAccessRights=new HashSet<>();

        UserAccessRight userAccessRight=new UserAccessRight();
        userAccessRight.setUarFunctionCode(secFunction.getFncFunctionCode());
        userAccessRight.setUarAccessEnableFlag("N");
        userAccessRight.setUarUserNo(usrUserNo);
        userAccessRights.add(userAccessRight);
        accessRightsSet.add(userAccessRight);

        UserAccessRight userAccessRight1=new UserAccessRight();
        userAccessRight1.setUarFunctionCode(fourthFunction.getFncFunctionCode());
        userAccessRight1.setUarAccessEnableFlag("Y");
        userAccessRight1.setUarUserNo(usrUserNo);
        userAccessRights.add(userAccessRight1);
        accessRightsSet.add(userAccessRight);

        user.setUserAccessRightsSet(userAccessRights);

        user.setUserAccessLocationSet(null);

        // Convert to JSON
        objectMapper = new ObjectMapper();
        userData = objectMapper.writeValueAsString(user);
        log.info("JSON string : " + userData);

        // Place the delete request
        mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(userData)
        )
                .andReturn();

        response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));



    }



    @Test
    public void deleteMerchantUser() throws Exception  {

        // Save the user
        user  = userService.saveUser(user);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user/delete/" + user.getUsrUserNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void listMerchantUsers() throws Exception  {

        // Save the user
        user  = userService.saveUser(user);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/users/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getMerchantUserInfo() throws Exception  {

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

        user.setUserRoleSet(userRoles);



        log.info("Original role access right " + roleAccessRight.toString());


        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userData = objectMapper.writeValueAsString(user);
        log.info("JSON string : " + userData);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(userData)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Long usrUserNo = Long.parseLong(map.get("data"));

       /* Set<UserAccessRight> userAccessRights=new HashSet<>();

        UserAccessRight userAccessRight=new UserAccessRight();
        userAccessRight.setUarFunctionCode(secFunction.getFncFunctionCode());
        userAccessRight.setUarAccessEnableFlag("N");
        userAccessRight.setUarUserNo(usrUserNo);
        userAccessRights.add(userAccessRight);

        accessRightsSet.add(userAccessRight);

        user.setUserAccessRightsSet(userAccessRights);*/
        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

        // Place the delete request
        mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/user/details/"+usrUserNo)
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void getRedemptionMerchantUsers() throws Exception  {

        // Save the user
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user  = userService.saveUser(user);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptionusers/username/"+user.getUsrLoginId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getRegistrationStatus() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/registrationstatus")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyaltyId","9400612345")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void saveTestForRedemptionUser() throws Exception  {

        // Save the user
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user  = userService.saveUser(user);

        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userData = objectMapper.writeValueAsString(user);
        log.info("JSON string : " + userData);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(userData)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void generateOtp() throws Exception  {



        user  = userService.saveUser(user);

        Customer customer = CustomerFixture.standardCustomer(user);

        customerService.saveCustomer(customer);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/forgetpassword/generateotp")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyaltyid",customer.getCusLoyaltyId())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerResponse: " + response);


    }


    @Test
    public void authenticateUserCompatible() throws Exception  {

        // Place the authentication request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/authentication")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("UserResponse: " + response);

        // check authentication status is success
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void registerUser() throws Exception  {



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/user/register")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", user.getUsrFName())
                .param("lastName", user.getUsrLName())
                .param("mobile", user.getUsrMobile())
                .param("password",user.getUsrPassword())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);


    }

    @Test
    public void getUsrUserCode() throws Exception  {

        useCustomerSession();

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/user/getusercode")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);


    }


    @Test
    public void confirmUserRegister() throws Exception  {



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/user/register/validate")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userLoginId", "177771")
                .param("otpCode","529053")

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);


    }

    @Test
    public void generateForgetPasswordOtp() throws Exception  {



        user  = userService.saveUser(user);



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/user/forgetpassword/generateotp")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userLoginId",user.getUsrLoginId())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);


    }

    @Test
    public void forgetUserPasswordOtpValidation() throws Exception  {

        user  = userService.saveUser(user);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/user/forgetpassword/validate")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userLoginId",user.getUsrLoginId())
                .param("password",user.getUsrPassword()+"1")
                .param("otpcode","547404")

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);


    }

    @Test
    public void getUserMembership() throws Exception {

        useCustomerSession();



        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/memberships/0/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

    }

    @Test
    public void generateOTPForCustomer() throws Exception  {

        useCustomerSession();

        user  = userService.saveUser(user);



        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/otp")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userLoginId",user.getUsrLoginId())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);


    }


    @After
    public void tearDown() throws Exception {

        Set<User> users = UserFixture.standardUsers();

        for(User user: users) {

            User delUser = userRepository.findByUsrLoginId(user.getUsrLoginId());

            if ( delUser != null ) {
                userRepository.delete(delUser);
            }

        }
        //userAccessLocationService.deleteUserAccessLocationSet(locationSet);

        //userAccessRightService.deleteUserAccessRightSet(accessRightsSet);

        //userRoleService.deleteUserRoleSet(roleSet);

        for(Role role : rolSet){

            roleService.deleteRole(role.getRolId());

        }
        for(Function function : functionSet){

            functionService.deleteFunction(function.getFncFunctionCode());

        }
    }

}
