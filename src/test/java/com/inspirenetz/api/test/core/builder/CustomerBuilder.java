package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.CustomerType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.Tier;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 4/11/14.
 */
public class CustomerBuilder {
    private Long cusCustomerNo;
    private Long cusMerchantNo;
    private Long cusUserNo = 0L;
    private Integer cusIdType = 0;
    private String cusIdNo = "";
    private String cusEmail = "";
    private String cusMobile  = "";
    private String cusFName = "";
    private String cusLName = "";
    private String cusLoyaltyId = "";
    private int cusStatus = CustomerStatus.INACTIVE;
    private String cusUniqueLoyaltyIdentifier;
    private Timestamp cusRegisterTimestamp;
    private Long cusTier;
    private Date cusTierLastEvaluated ;
    private Integer cusType = CustomerType.SUBSCRIBER;
    private Long cusMerchantUserRegistered = 0L;
    private Long cusLocation = 0L ;
    private Integer cusReceiveNotifications = IndicatorStatus.YES ;
    private Integer cusIsWhiteListed = IndicatorStatus.NO;
    private Integer cusRegisterStatus = IndicatorStatus.YES;
    private Tier tier;
    private CustomerProfile customerProfile;
    private Merchant merchant;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private CustomerBuilder() {
    }

    public static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    public CustomerBuilder withCusCustomerNo(Long cusCustomerNo) {
        this.cusCustomerNo = cusCustomerNo;
        return this;
    }

    public CustomerBuilder withCusMerchantNo(Long cusMerchantNo) {
        this.cusMerchantNo = cusMerchantNo;
        return this;
    }

    public CustomerBuilder withCusUserNo(Long cusUserNo) {
        this.cusUserNo = cusUserNo;
        return this;
    }

    public CustomerBuilder withCusIdType(Integer cusIdType) {
        this.cusIdType = cusIdType;
        return this;
    }

    public CustomerBuilder withCusIdNo(String cusIdNo) {
        this.cusIdNo = cusIdNo;
        return this;
    }

    public CustomerBuilder withCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
        return this;
    }

    public CustomerBuilder withCusMobile(String cusMobile) {
        this.cusMobile = cusMobile;
        return this;
    }

    public CustomerBuilder withCusFName(String cusFName) {
        this.cusFName = cusFName;
        return this;
    }

    public CustomerBuilder withCusLName(String cusLName) {
        this.cusLName = cusLName;
        return this;
    }

    public CustomerBuilder withCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
        return this;
    }

    public CustomerBuilder withCusStatus(int cusStatus) {
        this.cusStatus = cusStatus;
        return this;
    }

    public CustomerBuilder withCusUniqueLoyaltyIdentifier(String cusUniqueLoyaltyIdentifier) {
        this.cusUniqueLoyaltyIdentifier = cusUniqueLoyaltyIdentifier;
        return this;
    }

    public CustomerBuilder withCusRegisterTimestamp(Timestamp cusRegisterTimestamp) {
        this.cusRegisterTimestamp = cusRegisterTimestamp;
        return this;
    }

    public CustomerBuilder withCusTier(Long cusTier) {
        this.cusTier = cusTier;
        return this;
    }

    public CustomerBuilder withCusTierLastEvaluated(Date cusTierLastEvaluated) {
        this.cusTierLastEvaluated = cusTierLastEvaluated;
        return this;
    }

    public CustomerBuilder withCusType(Integer cusType) {
        this.cusType = cusType;
        return this;
    }

    public CustomerBuilder withCusMerchantUserRegistered(Long cusMerchantUserRegistered) {
        this.cusMerchantUserRegistered = cusMerchantUserRegistered;
        return this;
    }

    public CustomerBuilder withCusLocation(Long cusLocation) {
        this.cusLocation = cusLocation;
        return this;
    }

    public CustomerBuilder withCusReceiveNotifications(Integer cusReceiveNotifications) {
        this.cusReceiveNotifications = cusReceiveNotifications;
        return this;
    }

    public CustomerBuilder withCusIsWhiteListed(Integer cusIsWhiteListed) {
        this.cusIsWhiteListed = cusIsWhiteListed;
        return this;
    }

    public CustomerBuilder withTier(Tier tier) {
        this.tier = tier;
        return this;
    }

    public CustomerBuilder withCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
        return this;
    }

    public CustomerBuilder withMerchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public CustomerBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerBuilder withCusRegisterStatus(Integer cusRegisterStatus) {
        this.cusRegisterStatus = cusRegisterStatus;
        return this;
    }

    public Customer build() {
        Customer customer = new Customer();
        customer.setCusCustomerNo(cusCustomerNo);
        customer.setCusMerchantNo(cusMerchantNo);
        customer.setCusUserNo(cusUserNo);
        customer.setCusIdType(cusIdType);
        customer.setCusIdNo(cusIdNo);
        customer.setCusEmail(cusEmail);
        customer.setCusMobile(cusMobile);
        customer.setCusFName(cusFName);
        customer.setCusLName(cusLName);
        customer.setCusLoyaltyId(cusLoyaltyId);
        customer.setCusStatus(cusStatus);
        customer.setCusUniqueLoyaltyIdentifier(cusUniqueLoyaltyIdentifier);
        customer.setCusRegisterTimestamp(cusRegisterTimestamp);
        customer.setCusTier(cusTier);
        customer.setCusTierLastEvaluated(cusTierLastEvaluated);
        customer.setCusType(cusType);
        customer.setCusMerchantUserRegistered(cusMerchantUserRegistered);
        customer.setCusLocation(cusLocation);
        customer.setCusReceiveNotifications(cusReceiveNotifications);
        customer.setCusIsWhiteListed(cusIsWhiteListed);
        customer.setTier(tier);
        customer.setCustomerProfile(customerProfile);
        customer.setMerchant(merchant);
        customer.setCreatedAt(createdAt);
        customer.setCreatedBy(createdBy);
        customer.setUpdatedAt(updatedAt);
        customer.setUpdatedBy(updatedBy);
        customer.setCusRegisterStatus(cusRegisterStatus);
        return customer;
    }
}
