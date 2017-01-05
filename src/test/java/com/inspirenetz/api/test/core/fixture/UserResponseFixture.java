package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.test.core.builder.UserResponseBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 8/11/14.
 */
public class UserResponseFixture {

    public static UserResponse standardUserResponse(Promotion promotion) {

        UserResponse userResponse = UserResponseBuilder.anUserResponse()
                 .withUrpUserNo(1L)
                 .withUrpResponseItemType(1L)
                 .withUrpResponseItemId(promotion.getPrmId())
                 .withUrpResponseType(1)
                 .build();


        return userResponse;


    }


    public static UserResponse standardUserResponse(Promotion promotion,Customer customer) {

        UserResponse userResponse = UserResponseBuilder.anUserResponse()
                .withUrpUserNo(customer.getCusUserNo())
                .withUrpResponseItemType(1L)
                .withUrpResponseItemId(promotion.getPrmId())
                .withUrpResponseType(1)
                .build();


        return userResponse;


    }




    public static Set<UserResponse> standardUserResponses(Promotion promotion) {

        Set<UserResponse> userResponses = new HashSet<UserResponse>(0);

        UserResponse pepsi  = UserResponseBuilder.anUserResponse()
                .withUrpUserNo(1L)
                .withUrpResponseItemType(1L)
                .withUrpResponseItemId(promotion.getPrmId())
                .withUrpResponseType(1)
                .build();


        userResponses.add(pepsi);



        UserResponse coke = UserResponseBuilder.anUserResponse()
                .withUrpUserNo(1L)
                .withUrpResponseItemType(1L)
                .withUrpResponseItemId(promotion.getPrmId())
                .withUrpResponseType(1)
                .build();

        userResponses.add(coke);



        return userResponses;



    }
}
