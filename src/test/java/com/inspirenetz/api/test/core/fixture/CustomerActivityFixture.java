package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CustomerActivityType;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.test.core.builder.CustomerActivityBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class CustomerActivityFixture {




    public static CustomerActivity standardCustomerActivity() {


        CustomerActivity drawChance = CustomerActivityBuilder.aCustomerActivity()
                .withCuaActivityType(CustomerActivityType.EARNING)
                .withCuaLoyaltyId("9538828853")
                .withCuaMerchantNo(1L)
                .build();


        return drawChance;


    }


    public static CustomerActivity updatedStandardCustomerActivity(CustomerActivity drawChance) {

        drawChance.setCuaLoyaltyId("9742375894");

        return drawChance;

    }


    public static Set<CustomerActivity> standardCustomerActivitys() {

        Set<CustomerActivity> drawChances = new HashSet<CustomerActivity>(0);

        CustomerActivity drawChanceA  = CustomerActivityBuilder.aCustomerActivity()
                .withCuaActivityType(CustomerActivityType.REDEMPTION)
                .withCuaLoyaltyId("9538828853")
                .withCuaMerchantNo(1L)
                .withCuaDate(Date.valueOf("2014-01-01"))
                .withCuaTime(Time.valueOf("12:30"))
                .build();
        drawChances.add(drawChanceA);



        CustomerActivity drawChanceB = CustomerActivityBuilder.aCustomerActivity()
                .withCuaActivityType(CustomerActivityType.REDEMPTION)
                .withCuaLoyaltyId("9538828853")
                .withCuaMerchantNo(1L)
                .withCuaDate(Date.valueOf("2014-01-01"))
                .withCuaTime(Time.valueOf("12:30"))
                .build();

        drawChances.add(drawChanceB);



        return drawChances;



    }
}
