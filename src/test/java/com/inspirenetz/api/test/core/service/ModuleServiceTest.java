package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.ModuleFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class ModuleServiceTest {


    private static Logger log = LoggerFactory.getLogger(ModuleServiceTest.class);

    @Autowired
    private ModuleService moduleService;

    @Before
    public void setUp() {}



    @Test
    public void testFindByMdlId() throws InspireNetzException {

        // Create the prodcut
        Module module = ModuleFixture.standardModule();
        module = moduleService.saveModule(module);

        Module searchModule = moduleService.findByMdlId(module.getMdlId());
        Assert.assertNotNull(searchModule);
        log.info("Module Information" + searchModule.toString());
    }


    @Test
    public void testFindByMdlName() throws Exception {

        // Create the prodcut
        Module module = ModuleFixture.standardModule();
        module = moduleService.saveModule(module);

        Module searchModule = moduleService.findByMdlName(module.getMdlName());
        Assert.assertNotNull(searchModule);
        log.info("Module Information" + searchModule.toString());
    }



    @Test
    public void testFindByMdlNameLike() throws InspireNetzException {

        // Create the module
        Module module = ModuleFixture.standardModule();
        moduleService.saveModule(module);
        Assert.assertNotNull(module.getMdlId());
        log.info("Module created");

        // Check the module name
        Page<Module> modules = moduleService.findByMdlNameLike("%TEST%",constructPageSpecification(0));
        Assert.assertTrue(modules.hasContent());
        Set<Module> moduleSet = Sets.newHashSet((Iterable<Module>) modules);
        log.info("module list "+moduleSet.toString());


    }



    @Test
    public void testFindAll() throws InspireNetzException {

        // Create the module
        Module module = ModuleFixture.standardModule();
        moduleService.saveModule(module);
        Assert.assertNotNull(module.getMdlId());
        log.info("Module created");

        // Check the module name
        Page<Module> modules = moduleService.listModules(constructPageSpecification(0));
        Assert.assertTrue(modules.hasContent());
        Set<Module> moduleSet = Sets.newHashSet((Iterable<Module>) modules);
        log.info("module list "+moduleSet.toString());


    }



    @Test
    public void testIsDuplicateModuleExisting() throws InspireNetzException {

        // Save the module
        Module module = ModuleFixture.standardModule();
        moduleService.saveModule(module);

        // New Module
        Module newModule = ModuleFixture.standardModule();
        boolean exists = moduleService.isDuplicateModuleExisting(newModule);
        Assert.assertTrue(exists);
        log.info("module exists");



    }


    @Test
    public void testSearchModules() throws InspireNetzException {

        // SAve the standard Module
        Module module = ModuleFixture.standardModule();
        module = moduleService.saveModule(module);

        // Search
        Page<Module> modulePage = moduleService.searchModules("name",module.getMdlName(),constructPageSpecification(0));
        Assert.assertNotNull(modulePage);
        Assert.assertTrue(modulePage.hasContent());
        List<Module> moduleList = Lists.newArrayList((Iterable<Module>) modulePage);
        log.info("Returned Module List "+ moduleList.toString());

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
        return new Sort(Sort.Direction.ASC, "mdlId");
    }


    @After
    public void tearDown() throws Exception {

        Set<Module> modules = ModuleFixture.standardModules();

        for(Module module: modules) {

            Module delModule = moduleService.findByMdlName(module.getMdlName());

            if ( delModule != null ) {
                moduleService.deleteModule(delModule.getMdlId());
            }

        }
    }

}
