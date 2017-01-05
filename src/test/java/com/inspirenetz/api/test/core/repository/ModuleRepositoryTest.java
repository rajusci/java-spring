package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.repository.ModuleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.Set;

/**
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class ModuleRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(ModuleRepositoryTest.class);

    @Autowired
    private ModuleRepository moduleRepository;


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        Set<Module> modules = ModuleFixture.standardModules();

        for(Module module: modules) {

            Module delModule = moduleRepository.findByMdlName(module.getMdlName());

            if ( delModule != null ) {
                moduleRepository.delete(delModule);
            }

        }
    }


    @Test
    public void test1Create(){


        Module module = moduleRepository.save(ModuleFixture.standardModule());
        log.info(module.toString());
        Assert.assertNotNull(module.getMdlId());

    }

    @Test
    public void test2Update() {

        Module module = ModuleFixture.standardModule();
        module = moduleRepository.save(module);
        log.info("Original Module " + module.toString());

        Module updatedModule = ModuleFixture.updatedStandardModule(module);
        updatedModule = moduleRepository.save(updatedModule);
        log.info("Updated Module "+ updatedModule.toString());

    }


    @Test
    public void testFindByMdlId() throws Exception {

        // Create the prodcut
        Module module = ModuleFixture.standardModule();
        module = moduleRepository.save(module);

        Module searchModule = moduleRepository.findByMdlId(module.getMdlId());
        Assert.assertNotNull(searchModule);
        log.info("Module Information" + searchModule.toString());
    }


    @Test
    public void testFindByMdlName() throws Exception {

        // Create the prodcut
        Module module = ModuleFixture.standardModule();
        module = moduleRepository.save(module);

        Module searchModule = moduleRepository.findByMdlName(module.getMdlName());
        Assert.assertNotNull(searchModule);
        log.info("Module Information" + searchModule.toString());
    }



    @Test
    public void testFindByMdlNameLike() {

        // Create the module
        Module module = ModuleFixture.standardModule();
        moduleRepository.save(module);
        Assert.assertNotNull(module.getMdlId());
        log.info("Module created");

        // Check the module name
        Page<Module> modules = moduleRepository.findByMdlNameLike("%TEST%", constructPageSpecification(0));
        Assert.assertTrue(modules.hasContent());
        Set<Module> moduleSet = Sets.newHashSet((Iterable<Module>) modules);
        log.info("module list "+moduleSet.toString());


    }



    @Test
    public void testFindAll() {

        // Create the module
        Module module = ModuleFixture.standardModule();
        moduleRepository.save(module);
        Assert.assertNotNull(module.getMdlId());
        log.info("Module created");

        // Check the module name
        Page<Module> modules = moduleRepository.findAll(constructPageSpecification(0));
        Assert.assertTrue(modules.hasContent());
        Set<Module> moduleSet = Sets.newHashSet((Iterable<Module>) modules);
        log.info("module list "+moduleSet.toString());


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
        return new Sort(Sort.Direction.ASC, "mdlId");
    }
}
