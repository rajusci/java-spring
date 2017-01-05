package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.test.core.builder.RoleBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public class RoleFixture {




    public static Role standardRole() {

        // For setting dummy data to collection
        Set<RoleAccessRight> roleAccessRightSet=new HashSet<>(0);

        RoleAccessRight roleAccessRight=new RoleAccessRight();
        roleAccessRight.setRarFunctionCode(1L);


        roleAccessRightSet.add(roleAccessRight);

        RoleAccessRight roleAccessRight1=new RoleAccessRight();
        roleAccessRight1.setRarFunctionCode(2L);

        roleAccessRightSet.add(roleAccessRight1);

        Role role = RoleBuilder.aRole()
                .withRolMerchantNo(1L)
                .withRolName("Cashier")
                .withRolUserType(UserType.MERCHANT_USER)
                .withRolIsDefaultRole(0)
                .withRoleAccessRightList(roleAccessRightSet)
                .build();


        return role;


    }


    public static Role updatedStandardRole(Role role) {

       Set<RoleAccessRight> roleAccessRightList=new HashSet<>();





        RoleAccessRight roleAccessRight1=new RoleAccessRight();

        roleAccessRight1.setRarId(35L);
        roleAccessRight1.setRarRole(42L);
        roleAccessRight1.setRarFunctionCode(5000L);



        roleAccessRightList.add(roleAccessRight1);

//        RoleAccessRight roleAccessRight2=new RoleAccessRight();
//
//        roleAccessRight2.setRarFunctionCode(4000L);
//
//        roleAccessRightList.add(roleAccessRight2);

//        RoleAccessRight roleAccessRight1=new RoleAccessRight();
//        roleAccessRight1.setRarFunctionCode(100L);
//        roleAccessRightList.add(roleAccessRight1);
//        role.setRolName("Cashier A");

        role.setRoleAccessRightSet(roleAccessRightList);

       /* Set<RoleAccessRight> roleAccessRightSet = role.getRoleAccessRightSet();

        for (RoleAccessRight roleAccessRight : roleAccessRightSet ) {

            roleAccessRight.setRarFunctionCode(6000L);
            break;

        }*/


        return role;

    }


    public static Set<Role> standardRoles() {

        Set<Role> roles = new HashSet<Role>(0);

        Role pepsi  = RoleBuilder.aRole()
                .withRolMerchantNo(1L)
                .withRolName("Cashier")
                .withRolUserType(UserType.MERCHANT_USER)
                .withRolIsDefaultRole(0)
                .build();

        roles.add(pepsi);



        Role coke = RoleBuilder.aRole()
                .withRolMerchantNo(1L)
                .withRolName("Supervisor")
                .withRolUserType(UserType.MERCHANT_USER)
                .withRolIsDefaultRole(0)
                .build();

        roles.add(coke);



        return roles;



    }
}
