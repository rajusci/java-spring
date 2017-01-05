package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 28/4/14.
 */
public class CustomerRewardBalanceCompatibleResource extends BaseResource {




    Long rwd_id;

    String rwd_name;

    Double rwd_balance;

    Double rwd_cashback_value;


    public CustomerRewardBalanceCompatibleResource() {

        super();

    }


    public Long getRwd_id() {
        return rwd_id;
    }

    public void setRwd_id(Long rwd_id) {
        this.rwd_id = rwd_id;
    }

    public String getRwd_name() {
        return rwd_name;
    }

    public void setRwd_name(String rwd_name) {
        this.rwd_name = rwd_name;
    }

    public Double getRwd_balance() {
        return rwd_balance;
    }

    public void setRwd_balance(Double rwd_balance) {
        this.rwd_balance = rwd_balance;
    }

    public Double getRwd_cashback_value() {
        return rwd_cashback_value;
    }

    public void setRwd_cashback_value(Double rwd_cashback_value) {
        this.rwd_cashback_value = rwd_cashback_value;
    }
}
