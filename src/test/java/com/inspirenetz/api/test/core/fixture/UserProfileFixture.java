package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.test.core.builder.UserProfileBuilder;
import com.inspirenetz.api.util.DBUtils;


import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 24/10/14.
 */
public class UserProfileFixture {

    public static UserProfile standardUserProfile(){

        UserProfile userProfile = UserProfileBuilder.anUserProfile()
                .withUspAddress1("abs")
                .withUspAddress2("testadd2")
                .withUspAddress3("testadd3")
                .withUspCity("kkkk")
                .withUspUserNo(4236L)
                .withUspAnniversary(DBUtils.covertToSqlDate("2010-02-01"))
                .withUspBirthday(DBUtils.covertToSqlDate("2010-02-01"))
                .withUsrFName("PBTEST")
                .withUsrLName("PBTESTDEMO")
                .withUsrProfilePic(216L)
                .build();


        return userProfile;


    }




    public static UserProfile standardUserProfile(User user){

        UserProfile userProfile = UserProfileBuilder.anUserProfile()
                .withUspAddress1("abs")
                .withUspAddress2("testadd2")
                .withUspAddress3("testadd3")
                .withUspCity("kkkk")
                .withUspUserNo(user.getUsrUserNo())
                .withUspAnniversary(DBUtils.covertToSqlDate("2010-02-01"))
                .withUspBirthday(DBUtils.covertToSqlDate("2010-02-01"))
                .withUsrFName("PBTEST")
                .withUsrLName("PBTESTDEMO")
                .build();


        return userProfile;


    }


    public static UserProfile updatedStandUserProfile(UserProfile userProfile) {

        userProfile.setUspAddress1("test23");

        return userProfile;

    }


    public static Set<UserProfile> standardUserProfiles() {

        Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

        UserProfile message  = UserProfileBuilder.anUserProfile()
                .withUspAddress1("abs")
                .withUspAddress2("testadd2")
                .withUspAddress3("testadd3")
                .withUspCity("kkkk")
                .withUspAnniversary(new Date(2014, 10, 02))
                .build();
        userProfiles.add(message);



        UserProfile message1 = UserProfileBuilder.anUserProfile()
                .withUspAddress1("abs1")
                .withUspAddress2("testadd12")
                .withUspAddress3("testadd13")
                .withUspCity("kkkk1")
                .withUspAnniversary(new Date(2014,10,02))
                .build();

        userProfiles.add(message1);



        return userProfiles;



    }
}
