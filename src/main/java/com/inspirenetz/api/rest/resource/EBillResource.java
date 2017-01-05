package com.inspirenetz.api.rest.resource;

import java.util.HashMap;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class EBillResource extends BaseResource {

    private CustomerResource customerResource;

    private SaleResource saleResource;

    private HashMap<String,Object> additionalParams;


    public CustomerResource getCustomerResource() {
        return customerResource;
    }

    public void setCustomerResource(CustomerResource customerResource) {
        this.customerResource = customerResource;
    }

    public SaleResource getSaleResource() {
        return saleResource;
    }

    public void setSaleResource(SaleResource saleResource) {
        this.saleResource = saleResource;
    }

    public HashMap<String, Object> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(HashMap<String, Object> additionalParams) {
        this.additionalParams = additionalParams;
    }

    @Override
    public String toString() {
        return "EBillResource{" +
                "customerResource=" + customerResource +
                ", saleResource=" + saleResource +
                ", additionalParams=" + additionalParams +
                '}';
    }
}
