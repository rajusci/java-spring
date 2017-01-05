package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.test.core.builder.UserRoleBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public class UserRoleFixture {

    public static UserRole standardUserRole() {

        UserRole userRole = UserRoleBuilder.anUserRole()
                .withUerRole(1L)
                .withUerUserId(2L)
                .build();


        return userRole;
    }


    public static UserRole updatedStandardUserRole(UserRole userRole ) {

        userRole.setUerRole(3L);
        return userRole;
    }


    public static Set<UserRole> standarUserRoles() {

        Set<UserRole> userRoleSet = new HashSet<>(0);

        UserRole userRole = UserRoleBuilder.anUserRole()
                .withUerRole(3L)
                .withUerUserId(4L)
                .build();

        userRoleSet.add(userRole);

        UserRole userRole1 = UserRoleBuilder.anUserRole()
                .withUerRole(4L)
                .withUerUserId(4L)
                .build();



        userRoleSet.add(userRole1);


        return userRoleSet;


    }
}
