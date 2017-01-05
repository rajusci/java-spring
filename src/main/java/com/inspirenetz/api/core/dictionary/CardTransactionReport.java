package com.inspirenetz.api.core.dictionary;

/**
 * Created by ameen on 7/7/15.
 */
public class CardTransactionReport {

    private Double totalTopupAmount;
    private  Double totalDebitAmount;
    private Double totalRefundAmount;
    private Double totalPromoAmount;

    public Double getTotalTopupAmount() {
        return totalTopupAmount;
    }

    public void setTotalTopupAmount(Double totalTopupAmount) {
        this.totalTopupAmount = totalTopupAmount;
    }

    public Double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public void setTotalDebitAmount(Double totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public Double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }


    public Double getTotalPromoAmount() {
        return totalPromoAmount;
    }

    public void setTotalPromoAmount(Double totalPromoAmount) {
        this.totalPromoAmount = totalPromoAmount;
    }


}
