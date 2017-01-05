package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.service.FunctionService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.FunctionFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})
public class FunctionServiceTest {


    private static Logger log = LoggerFactory.getLogger(FunctionServiceTest.class);

    @Autowired
    private FunctionService functionService;

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
    public void testFindByFncFunctionCode() throws Exception {

        // Create the prodcut
        Function function = FunctionFixture.standardFunction();
        function = functionService.saveFunction(function);

        Function searchFunction = functionService.findByFncFunctionCode(function.getFncFunctionCode());
        Assert.assertNotNull(searchFunction);
        log.info("Function Information" + searchFunction.toString());
    }


    @Test
    public void testFindByFncFunctionName() throws Exception {

        // Create the prodcut
        Function function = FunctionFixture.standardFunction();
        function = functionService.saveFunction(function);

        Function searchFunction = functionService.findByFncFunctionName(function.getFncFunctionName());
        Assert.assertNotNull(searchFunction);
        log.info("Function Information" + searchFunction.toString());
    }



    @Test
    public void testFindByFncFunctionNameLike() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        Page<Function> functions = functionService.findByFncFunctionNameLike("%TEST%",constructPageSpecification(0));
        Assert.assertTrue(functions.hasContent());
        Set<Function> functionSet = Sets.newHashSet((Iterable<Function>) functions);
        log.info("function list "+functionSet.toString());


    }



    @Test
    public void testFindAll() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        Page<Function> functions = functionService.listFunctions(constructPageSpecification(0));
        Assert.assertTrue(functions.hasContent());
        Set<Function> functionSet = Sets.newHashSet((Iterable<Function>) functions);
        log.info("function list "+functionSet.toString());


    }



    @Test
    public void testIsDuplicateFunctionExisting() {

        // Save the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);

        // New Function
        Function newFunction = FunctionFixture.standardFunction();
        boolean exists = functionService.isDuplicateFunctionExisting(newFunction);
        Assert.assertTrue(exists);
        log.info("function exists");



    }

    @Test
    public void test8GetFunctionsByUserType() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        List<Function> functions = functionService.getFunctionsForUserType(2);
        Assert.assertTrue(functions.size()>0);
        Set<Function> functionSet = Sets.newHashSet((Iterable<Function>) functions);
        log.info("function list "+functionSet.toString());

    }

    @Test
    public void test8findByUserTypeAndFunctionNameLike() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        List<Function> functions = functionService.findByUserTypeAndFunctionNameLike(4,function.getFncFunctionName());

        log.info("function list "+functions.toString());

    }
    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecifisetion = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecifisetion;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "fncFunctionCode");
    }


    @After
    public void tearDown() throws Exception {

        Set<Function> functions = FunctionFixture.standardFunctions();

        for(Function function: functions) {

            Function delFunction = functionService.findByFncFunctionName(function.getFncFunctionName());

            if ( delFunction != null ) {
                functionService.deleteFunction(delFunction.getFncFunctionCode());
            }

        }
    }

}