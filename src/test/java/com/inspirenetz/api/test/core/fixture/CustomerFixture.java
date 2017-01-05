package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.test.core.builder.CustomerBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class CustomerFixture {

    public static Customer standardCustomer() {

       Customer customer = CustomerBuilder.aCustomer()
               .withCusMerchantNo(1L)
               .withCusIdType(0)
               .withCusIdNo("8983")
               .withCusEmail("test3333333@gmail.com")
               .withCusMobile("886976")
               .withCusFName("Test1")
               .withCusLName("Customer")
               .withCusLoyaltyId("6798739")
               .withCusStatus(CustomerStatus.ACTIVE)
               .withCusMerchantUserRegistered(12L)
               .withCusLocation(1L)
               .build();


        return customer;


    }


    public static Customer standardCustomer(User user) {

        Customer customer = CustomerBuilder.aCustomer()
                .withCusMerchantNo(1L)
                .withCusUserNo(121L)
                .withCusIdType(0)
                .withCusIdNo("39208423906")
                .withCusEmail("test3333333@gmail.com")
                .withCusMobile("0000000000")
                .withCusFName("Test")
                .withCusLName("Customer")
                .withCusLoyaltyId("3333333")
                .withCusStatus(CustomerStatus.ACTIVE)
                .withCusMerchantUserRegistered(12L)
                .withCusLocation(1L)
                .build();


        return customer;


    }


    public static Customer updatedStandardCustomer(Customer customer) {

        customer.setCusFName("James");
        customer.setCusLName("Smith");
        customer.setCusLoyaltyId("9999888877776662");


        return customer;

    }


    public static Set<Customer> standardCustomers() {

        Set<Customer> customers = new HashSet<Customer>(0);

        Customer sandeep = CustomerBuilder.aCustomer()
                .withCusMerchantNo(1L)
                .withCusUserNo(12L)
                .withCusIdType(0)
                .withCusIdNo("39208423906")
                .withCusEmail("customer33333@gmail.com")
                .withCusMobile("0000000000")
                .withCusFName("Test")
                .withCusLName("Customer")
                .withCusLoyaltyId("3333333")
                .withCusStatus(CustomerStatus.ACTIVE)
                .withCusMerchantUserRegistered(12L)
                .withCusLocation(0L)
                .build();

        customers.add(sandeep);



        Customer customer = CustomerBuilder.aCustomer()
                .withCusMerchantNo(1L)
                .withCusUserNo(121L)
                .withCusIdType(0)
                .withCusIdNo("39208423906")
                .withCusEmail("test44444@gmail.com")
                .withCusMobile("44444")
                .withCusFName("Test")
                .withCusLName("Customer2")
                .withCusLoyaltyId("44444")
                .withCusStatus(CustomerStatus.ACTIVE)
                .withCusMerchantUserRegistered(12L)
                .withCusLocation(1L)
                .build();
        customers.add(customer);
        return customers;

    }
}
