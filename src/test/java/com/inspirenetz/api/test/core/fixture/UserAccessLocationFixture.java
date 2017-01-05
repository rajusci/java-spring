package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.test.core.builder.UserAccessLocationBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public class UserAccessLocationFixture {

    public static UserAccessLocation standardUserAccessLocation(){

        UserAccessLocation userAccessLocation = UserAccessLocationBuilder.anUserAccessLocation()
                .withUalUserId(1L)
                .withUalLocation(2L)
                .build();


        return userAccessLocation;


    }


    public static UserAccessLocation updatedStandUserAccessLocation(UserAccessLocation userAccessLocation) {

        userAccessLocation.setUalUserId(3L);


        return userAccessLocation;

    }


    public static Set<UserAccessLocation> standardUserAccessLocations() {

        Set<UserAccessLocation> userAccessLocationSet = new HashSet<UserAccessLocation>(0);

        UserAccessLocation userAccessLocation  = UserAccessLocationBuilder.anUserAccessLocation()

                                    .withUalUserId(1L)
                                    .withUalLocation(2L)
                                    .build();


        userAccessLocationSet.add(userAccessLocation);

        UserAccessLocation userAccessLocation1  = UserAccessLocationBuilder.anUserAccessLocation()

                                .withUalUserId(2L)
                                .withUalLocation(5L)
                                .build();


        userAccessLocationSet.add(userAccessLocation1);





        return userAccessLocationSet;



    }
}
