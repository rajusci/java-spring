package com.inspirenetz.api.rest.resource;

/**
 * Created by ameen on 25/2/15.
 */
public class CardMasterCompatibleResource extends BaseResource {

    private String card_status;

    private Double card_balance;

    private String card_holder_name;

    private boolean is_pin_enabled;

    private Double card_min_topup;

    private Double card_fixed_value;

    private Integer card_type;

    private Integer cusIdType;

    private String cusIdNo="";

    public Double getCard_balance() {
        return card_balance;
    }

    public void setCard_balance(Double card_balance) {
        this.card_balance = card_balance;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getCard_status() {
        return card_status;
    }

    public void setCard_status(String card_status) {
        this.card_status = card_status;
    }

    public boolean isIs_pin_enabled() {
        return is_pin_enabled;
    }

    public void setIs_pin_enabled(boolean is_pin_enabled) {
        this.is_pin_enabled = is_pin_enabled;
    }

    public Double getCard_min_topup() {
        return card_min_topup;
    }

    public void setCard_min_topup(Double card_min_topup) {
        this.card_min_topup = card_min_topup;
    }

    public Double getCard_fixed_value() {
        return card_fixed_value;
    }

    public void setCard_fixed_value(Double card_fixed_value) {
        this.card_fixed_value = card_fixed_value;
    }

    public Integer getCard_type() {
        return card_type;
    }

    public void setCard_type(Integer card_type) {
        this.card_type = card_type;
    }

    public Integer getCusIdType() {
        return cusIdType;
    }

    public void setCusIdType(Integer cusIdType) {
        this.cusIdType = cusIdType;
    }

    public String getCusIdNo() {
        return cusIdNo;
    }

    public void setCusIdNo(String cusIdNo) {
        this.cusIdNo = cusIdNo;
    }

    @Override
    public String toString() {
        return "CardMasterCompatibleResource{" +
                "card_status='" + card_status + '\'' +
                ", card_balance=" + card_balance +
                ", card_holder_name='" + card_holder_name + '\'' +
                ", is_pin_enabled=" + is_pin_enabled +
                ", card_min_topup=" + card_min_topup +
                ", card_fixed_value=" + card_fixed_value +
                ", card_type=" + card_type +
                ", cusIdType=" + cusIdType +
                ", cusIdNo='" + cusIdNo + '\'' +
                '}';
    }
}
