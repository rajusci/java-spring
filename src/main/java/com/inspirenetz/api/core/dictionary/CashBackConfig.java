package com.inspirenetz.api.core.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 17/10/15.
 */
public class CashBackConfig {

   String cashBackName;

   List<String> validations = new ArrayList<>(0);

   String preapi;

   String postapi;

   List<String> notifications  = new ArrayList<>(0);

   String customerSpiel;

   String merchantSpiel;

   Long rwdCurrencyId ;

    public String getCashBackName() {
        return cashBackName;
    }

    public void setCashBackName(String cashBackName) {
        this.cashBackName = cashBackName;
    }

    public List<String> getValidations() {
        return validations;
    }

    public void setValidations(List<String> validations) {
        this.validations = validations;
    }

    public String getPreapi() {
        return preapi;
    }

    public void setPreapi(String preapi) {
        this.preapi = preapi;
    }

    public String getPostapi() {
        return postapi;
    }

    public void setPostapi(String postapi) {
        this.postapi = postapi;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public String getCustomerSpiel() {
        return customerSpiel;
    }

    public void setCustomerSpiel(String customerSpiel) {
        this.customerSpiel = customerSpiel;
    }

    public String getMerchantSpiel() {
        return merchantSpiel;
    }

    public void setMerchantSpiel(String merchantSpiel) {
        this.merchantSpiel = merchantSpiel;
    }

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }
}
