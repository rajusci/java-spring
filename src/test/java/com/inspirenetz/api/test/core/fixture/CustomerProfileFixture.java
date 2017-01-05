package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.MaritalStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.test.core.builder.CustomerProfileBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class CustomerProfileFixture {

    public static CustomerProfile standardCustomerProfile() {

        // Get the standarndCustomer
        Customer customer = CustomerFixture.standardCustomer();

        // Create the date


        CustomerProfile customerProfile = CustomerProfileBuilder.aCustomerProfile()

                .withCspCustomerNo(1L)
                .withCspCustomerBirthday(DBUtils.covertToSqlDate("1989-12-08"))
                .withCspProfession(1)
                .withCspIncomeRange(1)
                .withCspAgeGroup(1)
                .withCspGender(Gender.MALE)
                .withCspFamilyStatus(MaritalStatus.MARRIED)
                .withCspAddress("#30, 4th Street")
                .withCspCity("Bangalore")
                .withCspPincode("560001")
                .withCspState(10)
                .withCspCountry(356)
                .withCspCustomerAnniversary(DBUtils.covertToSqlDate("2010-02-01"))
                .withCspFamilyChild1Name("John")
                .withCspFamilyChild1Bday(DBUtils.covertToSqlDate("2012-01-01"))
                .withCspFamilySpouseName("Mary")
                .withCspFamilySpouseBday(DBUtils.covertToSqlDate("1990-12-20"))
                .withCspBirthDayLastAwarded(DBUtils.covertToSqlDate("2010-12-14"))
                .build();

        return customerProfile;

    }

    public static CustomerProfile standardCustomerProfile(Customer customer) {


        // Create the date

        CustomerProfile customerProfile = CustomerProfileBuilder.aCustomerProfile()

                .withCspCustomerNo(customer.getCusCustomerNo())
                .withCspCustomerBirthday(DBUtils.covertToSqlDate("1989-12-08"))
                .withCspProfession(1)
                .withCspIncomeRange(1)
                .withCspAgeGroup(1)
                .withCspGender(Gender.MALE)
                .withCspFamilyStatus(MaritalStatus.MARRIED)
                .withCspAddress("#30, 4th Street")
                .withCspCity("Bangalore")
                .withCspPincode("560001")
                .withCspState(10)
                .withCspCountry(356)
                .withCspCustomerAnniversary(DBUtils.covertToSqlDate("2010-02-01"))
                .withCspFamilyChild1Name("John")
                .withCspFamilyChild1Bday(DBUtils.covertToSqlDate("2012-01-01"))
                .withCspFamilySpouseName("Mary")
                .withCspFamilySpouseBday(DBUtils.covertToSqlDate("1990-12-20"))
                .withCspBirthDayLastAwarded(DBUtils.covertToSqlDate("2014-12-10"))
                .build();

        return customerProfile;

    }



    public static CustomerProfile updatedStandardCustomerProfile(CustomerProfile customerProfile) {

        customerProfile.setCspAddress("#28, Vigyan Nagar");
        customerProfile.setCspCity("Chennai");
        customerProfile.setCspCustomerBirthday(DBUtils.covertToSqlDate("1988-01-23"));

        return customerProfile;
    }



    public static Set<CustomerProfile> standardCustomerProfiles() {

        Set<CustomerProfile> customerProfiles = new HashSet<CustomerProfile>();

        CustomerProfile customerProfile1 = CustomerProfileBuilder.aCustomerProfile()
                .withCspCustomerNo(1L)
                .withCspCustomerBirthday(DBUtils.covertToSqlDate("1989-12-08"))
                .withCspProfession(1)
                .withCspIncomeRange(1)
                .withCspAgeGroup(1)
                .withCspGender(Gender.MALE)
                .withCspFamilyStatus(MaritalStatus.MARRIED)
                .withCspAddress("#30, 4th Street")
                .withCspCity("Bangalore")
                .withCspPincode("560001")
                .withCspState(10)
                .withCspCountry(356)
                .withCspCustomerAnniversary(DBUtils.covertToSqlDate("2010-02-01"))
                .withCspFamilyChild1Name("John")
                .withCspFamilyChild1Bday(DBUtils.covertToSqlDate("2012-01-01"))
                .withCspFamilySpouseName("Mary")
                .withCspFamilySpouseBday(DBUtils.covertToSqlDate("1990-12-20"))
                .build();

        // Add the customerProfile to the list
        customerProfiles.add(customerProfile1);


        CustomerProfile customerProfile2 =  CustomerProfileBuilder.aCustomerProfile()
                .withCspCustomerNo(2L)
                .withCspCustomerBirthday(DBUtils.covertToSqlDate("1988-12-08"))
                .withCspProfession(1)
                .withCspIncomeRange(1)
                .withCspAgeGroup(1)
                .withCspGender(Gender.MALE)
                .withCspFamilyStatus(MaritalStatus.MARRIED)
                .withCspAddress("#30, 9th Street")
                .withCspCity("Hyderabad")
                .withCspPincode("560001")
                .withCspState(10)
                .withCspCountry(356)
                .withCspCustomerAnniversary(DBUtils.covertToSqlDate("2010-02-01"))
                .withCspFamilyChild1Name("sree")
                .withCspFamilyChild1Bday(DBUtils.covertToSqlDate("2012-01-01"))
                .withCspFamilySpouseName("angali")
                .withCspFamilySpouseBday(DBUtils.covertToSqlDate("1990-12-20"))
                .build();

        customerProfiles.add(customerProfile2);

        // Return the customerProfile
        return customerProfiles;

    }
}
