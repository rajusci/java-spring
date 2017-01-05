package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.DrawChance;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by saneesh-ci on 7/11/14.
 */
public class CustomerActivityBuilder {
    private Long cuaId;
    private Long cuaMerchantNo;
    private String cuaLoyaltyId;
    private Integer cuaActivityType;
    private Date cuaDate;
    private Time cuaTime;

    private CustomerActivityBuilder() {
    }

    public static CustomerActivityBuilder aCustomerActivity() {
        return new CustomerActivityBuilder();
    }

    public CustomerActivityBuilder withCuaId(Long cuaId) {
        this.cuaId = cuaId;
        return this;
    }

    public CustomerActivityBuilder withCuaLoyaltyId(String cuaLoyaltyId) {
        this.cuaLoyaltyId = cuaLoyaltyId;
        return this;
    }

    public CustomerActivityBuilder withCuaMerchantNo(Long cuaMerchantNo) {
        this.cuaMerchantNo = cuaMerchantNo;
        return this;
    }

    public CustomerActivityBuilder withCuaActivityType(Integer cuaActivityType) {
        this.cuaActivityType = cuaActivityType;
        return this;
    }

    public CustomerActivityBuilder withCuaDate(Date cuaDate) {
        this.cuaDate = cuaDate;
        return this;
    }
    public CustomerActivityBuilder withCuaTime(Time cuaTime) {
        this.cuaTime = cuaTime;
        return this;
    }

    public CustomerActivityBuilder but() {
        return aCustomerActivity().withCuaId(cuaId).withCuaLoyaltyId(cuaLoyaltyId).withCuaMerchantNo(cuaMerchantNo).withCuaActivityType(cuaActivityType).withCuaDate(cuaDate).withCuaTime(cuaTime);
    }

    public CustomerActivity build() {
        CustomerActivity customerActivity = new CustomerActivity();
        customerActivity.setCuaId(cuaId);
        customerActivity.setCuaActivityType(cuaActivityType);
        customerActivity.setCuaLoyaltyId(cuaLoyaltyId);
        customerActivity.setCuaMerchantNo(cuaMerchantNo);
        customerActivity.setCuaDate(cuaDate);
        customerActivity.setCuaTime(cuaTime);
        return customerActivity;
    }
}
