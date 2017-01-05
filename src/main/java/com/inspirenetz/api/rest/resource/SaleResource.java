package com.inspirenetz.api.rest.resource;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 27/4/14.
 */
public class SaleResource extends BaseResource {



    private Long salId;

    private Long salMerchantNo;

    private int salType;

    private Date salDate;

    private Time salTime;

    private String salLoyaltyId;

    private Double salAmount;

    private int salCurrency = 608;

    private int salPaymentMode;

    private Long salLocation;

    private int salTxnChannel;

    private String salPaymentReference;

    private int salStatus;

    private Double salQuantity;

    private int salDayOfWeek;

    private Long salUserNo;

    private List<SaleSKUResource> saleSKUResourceList;



    public Long getSalId() {
        return salId;
    }

    public void setSalId(Long salId) {
        this.salId = salId;
    }

    public Long getSalMerchantNo() {
        return salMerchantNo;
    }

    public void setSalMerchantNo(Long salMerchantNo) {
        this.salMerchantNo = salMerchantNo;
    }

    public int getSalType() {
        return salType;
    }

    public void setSalType(int salType) {
        this.salType = salType;
    }

    public Date getSalDate() {
        return salDate;
    }

    public void setSalDate(Date salDate) {
        this.salDate = salDate;
    }

    public Time getSalTime() {
        return salTime;
    }

    public void setSalTime(Time salTime) {
        this.salTime = salTime;
    }

    public String getSalLoyaltyId() {
        return salLoyaltyId;
    }

    public void setSalLoyaltyId(String salLoyaltyId) {
        this.salLoyaltyId = salLoyaltyId;
    }

    public Double getSalAmount() {
        return salAmount;
    }

    public void setSalAmount(Double salAmount) {
        this.salAmount = salAmount;
    }

    public int getSalCurrency() {
        return salCurrency;
    }

    public void setSalCurrency(int salCurrency) {
        this.salCurrency = salCurrency;
    }

    public int getSalPaymentMode() {
        return salPaymentMode;
    }

    public void setSalPaymentMode(int salPaymentMode) {
        this.salPaymentMode = salPaymentMode;
    }

    public Long getSalLocation() {
        return salLocation;
    }

    public void setSalLocation(Long salLocation) {
        this.salLocation = salLocation;
    }

    public int getSalTxnChannel() {
        return salTxnChannel;
    }

    public void setSalTxnChannel(int salTxnChannel) {
        this.salTxnChannel = salTxnChannel;
    }

    public String getSalPaymentReference() {
        return salPaymentReference;
    }

    public void setSalPaymentReference(String salPaymentReference) {
        this.salPaymentReference = salPaymentReference;
    }

    public int getSalStatus() {
        return salStatus;
    }

    public void setSalStatus(int salStatus) {
        this.salStatus = salStatus;
    }

    public Double getSalQuantity() {
        return salQuantity;
    }

    public void setSalQuantity(Double salQuantity) {
        this.salQuantity = salQuantity;
    }

    public int getSalDayOfWeek() {
        return salDayOfWeek;
    }

    public void setSalDayOfWeek(int salDayOfWeek) {
        this.salDayOfWeek = salDayOfWeek;
    }

    public Long getSalUserNo() {
        return salUserNo;
    }

    public void setSalUserNo(Long salUserNo) {
        this.salUserNo = salUserNo;
    }

    public List<SaleSKUResource> getSaleSKUResourceList() {
        return saleSKUResourceList;
    }

    public void setSaleSKUResourceList(List<SaleSKUResource> saleSKUResourceList) {
        this.saleSKUResourceList = saleSKUResourceList;
    }
}
