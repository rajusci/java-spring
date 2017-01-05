package com.inspirenetz.api.rest.resource;

/**
 * Created by ameen on 7/7/15.
 */
public class CardTransactionReportResource extends BaseResource {

    public Double totalTopupAmount =0.0;
    public Double totalDebitAmount =0.0;
    public Double totalRefundAmount =0.0;
    public Double totalPromoAmount=0.0;

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

    @Override
    public String toString() {
        return "CardTransactionReportResource{" +
                "totalTopupAmount=" + totalTopupAmount +
                ", totalDebitAmount=" + totalDebitAmount +
                ", totalRefundAmount=" + totalRefundAmount +
                ", totalPromoAmount=" + totalPromoAmount +
                '}';
    }
}
