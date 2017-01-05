package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.test.core.builder.RoleAccessRightBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public class RoleAccessRightFixture {

    public static RoleAccessRight standardRoleAccessRight(){

        RoleAccessRight roleAccessRight = RoleAccessRightBuilder.aRoleAccessRight()

                .withRarRole(1L)
                .withRarFunctionCode(2L)
                .build();


        return roleAccessRight;


    }


    public static RoleAccessRight updatedStandRoleAccessRight(RoleAccessRight roleAccessRight) {

        roleAccessRight.setRarFunctionCode(3L);


        return roleAccessRight;

    }


    public static Set<RoleAccessRight> standardRoleAccessRights() {

        Set<RoleAccessRight> roleAccessRights = new HashSet<RoleAccessRight>(0);

        RoleAccessRight roleAccessRight  = RoleAccessRightBuilder.aRoleAccessRight()
                    .withRarRole(1L)
                    .withRarFunctionCode(2L)
                    .build();


        roleAccessRights.add(roleAccessRight);



        RoleAccessRight roleAccessRight1 = RoleAccessRightBuilder.aRoleAccessRight()

                .withRarRole(1L)
                .withRarFunctionCode(2L)
                .build();
        roleAccessRights.add(roleAccessRight1);



        return roleAccessRights;



    }
}
