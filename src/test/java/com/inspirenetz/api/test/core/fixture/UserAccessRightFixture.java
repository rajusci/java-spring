package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.test.core.builder.UserAccessRightBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class UserAccessRightFixture {

    public static UserAccessRight standardUserAccessRight() {

        UserAccessRight userAccessRight = UserAccessRightBuilder.anUserAccessRight()
                .withUarUserNo(2L)
                .withUarFunctionCode(888L)
                .withUarAccessEnableFlag(UserAccessRightAccessEnableFlag.ENABLED)
                .build();


        return userAccessRight;
    }


    public static UserAccessRight updatedStandardUserAccessRight(UserAccessRight userAccessRight ) {

        userAccessRight.setUarAccessEnableFlag(UserAccessRightAccessEnableFlag.DISABLED);
        return userAccessRight;
    }


    public static Set<UserAccessRight> standardUserAccessRights() {

        Set<UserAccessRight> userAccessRightSet = new HashSet<>(0);

        UserAccessRight userAccessRight1 = UserAccessRightBuilder.anUserAccessRight()
                .withUarUserNo(2L)
                .withUarFunctionCode(888L)
                .withUarAccessEnableFlag(UserAccessRightAccessEnableFlag.ENABLED)
                .build();

        userAccessRightSet.add(userAccessRight1);


        UserAccessRight userAccessRight2 = UserAccessRightBuilder.anUserAccessRight()
                .withUarUserNo(2L)
                .withUarFunctionCode(777L)
                .withUarAccessEnableFlag(UserAccessRightAccessEnableFlag.ENABLED)
                .build();

        userAccessRightSet.add(userAccessRight2);


        return userAccessRightSet;


    }
}
