package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.repository.FunctionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.FunctionFixture;
import com.inspirenetz.api.util.DBUtils;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class FunctionRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(FunctionRepositoryTest.class);

    @Autowired
    private FunctionRepository functionRepository;


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        Set<Function> functions = FunctionFixture.standardFunctions();

        for(Function function: functions) {

            Function delFunction = functionRepository.findByFncFunctionName(function.getFncFunctionName());

            if ( delFunction != null ) {
                functionRepository.delete(delFunction);
            }

        }
    }


    @Test
    public void test1Create(){


        Function function = functionRepository.save(FunctionFixture.standardFunction());
        log.info(function.toString());
        Assert.assertNotNull(function.getFncFunctionCode());

    }

    @Test
    public void test2Update() {

        Function function = FunctionFixture.standardFunction();
        function = functionRepository.save(function);
        log.info("Original Function " + function.toString());

        Function updatedFunction = FunctionFixture.updatedStandardFunction(function);
        updatedFunction = functionRepository.save(updatedFunction);
        log.info("Updated Function "+ updatedFunction.toString());

    }


    @Test
    public void testFindByFncFunctionCode() throws Exception {

        // Create the prodcut
        Function function = FunctionFixture.standardFunction();
        function = functionRepository.save(function);

        Function searchFunction = functionRepository.findByFncFunctionCode(function.getFncFunctionCode());
        Assert.assertNotNull(searchFunction);
        log.info("Function Information" + searchFunction.toString());
    }


    @Test
    public void testFindByFncFunctionName() throws Exception {

        // Create the prodcut
        Function function = FunctionFixture.standardFunction();
        function = functionRepository.save(function);

        Function searchFunction = functionRepository.findByFncFunctionName(function.getFncFunctionName());
        Assert.assertNotNull(searchFunction);
        log.info("Function Information" + searchFunction.toString());
    }



    @Test
    public void testFindByFncFunctionNameLike() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionRepository.save(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        Page<Function> functions = functionRepository.findByFncFunctionNameLike("%TEST%", constructPageSpecification(0));
        Assert.assertTrue(functions.hasContent());
        Set<Function> functionSet = Sets.newHashSet((Iterable<Function>) functions);
        log.info("function list "+functionSet.toString());


    }



    @Test
    public void testFindAll() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionRepository.save(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        Page<Function> functions = functionRepository.findAll(constructPageSpecification(0));
        Assert.assertTrue(functions.hasContent());
        Set<Function> functionSet = Sets.newHashSet((Iterable<Function>) functions);
        log.info("function list "+functionSet.toString());


    }


    @Test
    public void testGetFunctionsForUserType() {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionRepository.save(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Check the function name
        List<Function> functions = functionRepository.getFunctionsForUserType("Y", DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT,DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT,DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT,DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT);
        Assert.assertTrue(functions.size()>0);
        Set<Function> functionSet = Sets.newHashSet((Iterable<Function>) functions);
        log.info("function list "+functionSet.toString());


    }

    @Test
    public void testsearchFunctionsForUserType() {

        // Create the function
        Set<Function> function = FunctionFixture.standardFunctions();
        functionRepository.save(function);

        log.info("Function created");

        // Check the function name
        List<Function> functions = functionRepository.findByUserTypeAndFunctionNameLike("Y", DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT, DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT, DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT, "TEST%");
        Assert.assertNotNull(functions);
        log.info("function list "+functions.toString());


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
        return new Sort(Sort.Direction.ASC, "fncFunctionCode");
    }
}
