package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.core.repository.RoleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.RoleFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class RoleRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(RoleRepositoryTest.class);

    @Autowired
    private RoleRepository roleRepository;




    Set<Role> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}



    @Test
    public void test1Create(){

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleRepository.save(roleFixture.standardRole());

        // Add to the tempSet
        tempSet.add(role);

        log.info(role.toString());
        Assert.assertNotNull(role.getRolId());

    }

    @Test
    public void test2Update() {

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleRepository.save(role);
        log.info("Original Role " + role.toString());

        // Add to the tempSet
        tempSet.add(role);

        RoleAccessRight roleAccessRight=new RoleAccessRight();
        roleAccessRight.setRarFunctionCode(1L);


        Set<RoleAccessRight> roleAccessRightList =new HashSet<>();
        roleAccessRightList.add(roleAccessRight);

        RoleAccessRight roleAccessRight1=new RoleAccessRight();
        roleAccessRight1.setRarFunctionCode(5L);

        roleAccessRightList.add(roleAccessRight1);

        Role updatedRole = RoleFixture.updatedStandardRole(role);
        updatedRole.setRoleAccessRightSet(roleAccessRightList);
        updatedRole = roleRepository.save(updatedRole);
        log.info("Updated Role "+ updatedRole.toString());

    }

    @Test
    public void test3FindByRolId() {

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleRepository.save(role);
        log.info("Original Role " + role.toString());


        // Add to the tempSet
        tempSet.add(role);

        // Get the data
        Role searchrole = roleRepository.findByRolId(role.getRolId());
        Assert.assertNotNull(searchrole);
        Assert.assertTrue(role.getRolId().longValue() ==  searchrole.getRolId().longValue());;
        log.info("Searched Role : " + searchrole.toString());


    }

    @Test
    public void test4FindByRolMerchantNoAndRolNameLike(){

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleRepository.save(role);
        log.info("Original Role " + role.toString());


        // Add to the tempSet
        tempSet.add(role);

        // Get the data
        Page<Role> searchRole = roleRepository.findByRolMerchantNoAndRolNameLike(role.getRolMerchantNo(),role.getRolName(),constructPageSpecification(0));
        Assert.assertTrue(searchRole.hasContent());
        log.info("Searched Role : " + searchRole.toString());

    }



    @Test
    public void test5FindByRolUserType(){

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleRepository.save(role);
        log.info("Original Role " + role.toString());


        // Add to the tempSet
        tempSet.add(role);

        // Get the data
        List<Role> roleList = roleRepository.findByRolUserType(role.getRolUserType());
        Assert.assertNotNull(roleList);
        log.info("Searched Role : " + roleList.toString());




    }

    @Test
    public void test6FindByRolMerchantNo(){

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleRepository.save(role);
        log.info("Original Role " + role.toString());


        // Add to the tempSet
        tempSet.add(role);

        // Get the data
        Page<Role> searchRole = roleRepository.findByRolMerchantNo(role.getRolMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(searchRole.hasContent());
        log.info("Searched Role : " + searchRole.toString());

    }




    @After
    public void tearDown() {

        for(Role role : tempSet ) {

            roleRepository.delete(role);

        }

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 100, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "rolName");
    }

}
