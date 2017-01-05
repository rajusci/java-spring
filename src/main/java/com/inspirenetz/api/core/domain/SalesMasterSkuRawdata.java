package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Created by sandheepgr on 27/4/14.
 */
@Entity
@Table(name = "SALES_MASTER_SKU_RAWDATA")
public class SalesMasterSkuRawdata {

    @Id
    @Column(name = "SMU_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smuId;

    @Basic
    @Column(name = "SMU_PARENT_BATCH_INDEX", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long smuParentBatchIndex;

    @Basic
    @Column(name = "SMU_PARENT_ROWINDEX", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smuParentRowIndex;

    @Basic
    @Column(name = "SMU_ITEM_CODE", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @NotNull
    @NotEmpty
    private String smuItemCode;

    @Basic
    @Column(name = "SMU_ITEM_QTY", nullable = false, insertable = true, updatable = true, length = 5, precision = 2)
    private Double smuItemQty = new Double(1);

    @Basic
    @Column(name = "SMU_ITEM_PRICE", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double smuItemPrice = new Double(0);

    @Basic
    @Column(name = "SMU_ITEM_DISCOUNT_PERCENT", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double smuItemDiscountPercent = new Double(0);

    @Basic
    @Column(name = "SMU_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smuStatus = 1 ;



    public Long getSmuId() {
        return smuId;
    }

    public void setSmuId(Long smuId) {
        this.smuId = smuId;
    }

    public Long getSmuParentBatchIndex() {
        return smuParentBatchIndex;
    }

    public void setSmuParentBatchIndex(Long smuParentBatchIndex) {
        this.smuParentBatchIndex = smuParentBatchIndex;
    }

    public int getSmuParentRowIndex() {
        return smuParentRowIndex;
    }

    public void setSmuParentRowIndex(int smuParentRowIndex) {
        this.smuParentRowIndex = smuParentRowIndex;
    }

    public String getSmuItemCode() {
        return smuItemCode;
    }

    public void setSmuItemCode(String smuItemCode) {
        this.smuItemCode = smuItemCode;
    }

    public Double getSmuItemQty() {
        return smuItemQty;
    }

    public void setSmuItemQty(Double smuItemQty) {
        this.smuItemQty = smuItemQty;
    }

    public Double getSmuItemPrice() {
        return smuItemPrice;
    }

    public void setSmuItemPrice(Double smuItemPrice) {
        this.smuItemPrice = smuItemPrice;
    }

    public Double getSmuItemDiscountPercent() {
        return smuItemDiscountPercent;
    }

    public void setSmuItemDiscountPercent(Double smuItemDiscountPercent) {
        this.smuItemDiscountPercent = smuItemDiscountPercent;
    }

    public int getSmuStatus() {
        return smuStatus;
    }

    public void setSmuStatus(int smuStatus) {
        this.smuStatus = smuStatus;
    }


}
