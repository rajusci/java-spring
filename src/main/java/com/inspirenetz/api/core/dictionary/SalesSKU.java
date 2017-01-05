package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 27/7/14.
 */
public class SalesSKU {

    private String itemCode;

    private Double price;

    private Double qty;

    private Double msf;



    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getMsf() {
        return msf;
    }

    public void setMsf(Double msf) {
        this.msf = msf;
    }


}
