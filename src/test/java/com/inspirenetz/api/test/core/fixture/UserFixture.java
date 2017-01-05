package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.test.core.builder.UserBuilder;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 25/6/14.
 */
public class UserFixture {

    public static User standardUser() {

        Calendar calendar = Calendar.getInstance();

        Date now  = calendar.getTime();

        Timestamp timestamp = new Timestamp(now.getTime());

        User user = UserBuilder.anUser()
                .withUsrUserType(UserType.MERCHANT_USER)
                .withUsrFName("Test")
                .withUsrLName("User")
                .withUsrLoginId("0000000000")
                .withUsrMobile("0000000000")
                .withUsrPassword("Sldjf12#")
                .withUsrMerchantNo(1L)
                .withUsrThirdPartyVendorNo(300L)
                .withUsrCreateTimestamp(timestamp)
                .build();


        return user;

    }



    public static User updatedStandardUser(User user) {

        user.setUsrFName("Demo 2");
        return user;

    }


    public static Set<User> standardUsers() {

        Set<User> userSet = new HashSet<>(0);

        Calendar calendar = Calendar.getInstance();

        Date now  = calendar.getTime();

        Timestamp timestamp = new Timestamp(now.getTime());

        User user1 = UserBuilder.anUser()
                .withUsrUserType(UserType.MERCHANT_USER)
                .withUsrFName("Test")
                .withUsrLName("User")
                .withUsrLoginId("0000000000")
                .withUsrMobile("0000000000")
                .withUsrPassword("sldjf")
                .withUsrMerchantNo(1L)
                .withUsrCreateTimestamp(timestamp)
                .build();

        userSet.add(user1);



        User user2 = UserBuilder.anUser()
                .withUsrUserType(UserType.MERCHANT_USER)
                .withUsrFName("Test")
                .withUsrLName("User2")
                .withUsrLoginId("9999886612")
                .withUsrMobile("9999886612")
                .withUsrPassword("sldjf")
                .withUsrMerchantNo(1L)
                .withUsrCreateTimestamp(timestamp)
                .build();

        userSet.add(user2);


        return userSet;

    }

}
