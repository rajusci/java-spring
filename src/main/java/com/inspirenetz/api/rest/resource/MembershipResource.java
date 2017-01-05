package com.inspirenetz.api.rest.resource;

import java.util.List;

/**
 * Created by sandheepgr on 14/12/16.
 */
public class MembershipResource {

    private CustomerResource customer;

    private List<CustomerRewardBalanceResource> customerRewardBalanceResourceList;




    public CustomerResource getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResource customer) {
        this.customer = customer;
    }

    public List<CustomerRewardBalanceResource> getCustomerRewardBalanceResourceList() {
        return customerRewardBalanceResourceList;
    }

    public void setCustomerRewardBalanceResourceList(List<CustomerRewardBalanceResource> customerRewardBalanceResourceList) {
        this.customerRewardBalanceResourceList = customerRewardBalanceResourceList;
    }




    @Override
    public String toString() {
        return "MembershipResource{" +
                "customer=" + customer +
                ", customerRewardBalanceResourceList=" + customerRewardBalanceResourceList +
                '}';
    }

}
