package com.inspirenetz.api.core.dictionary;

/**
 * Created by ameen on 11/1/16.
 */
public class CardNumberDetails {
    public String crmCardNumber;
    public Double crmTopupAmount=0.0;
    public Integer crmActivationType=CardActivationType.CARD_TYPE;
    public String crmActivationPin="0";




    public String getCrmCardNumber() {
        return crmCardNumber;
    }

    public void setCrmCardNumber(String crmCardNumber) {
        this.crmCardNumber = crmCardNumber;
    }

    public Double getCrmTopupAmount() {
        return crmTopupAmount;
    }

    public void setCrmTopupAmount(Double crmTopupAmount) {
        this.crmTopupAmount = crmTopupAmount;
    }

    public Integer getCrmActivationType() {
        return crmActivationType;
    }

    public void setCrmActivationType(Integer crmActivationType) {
        this.crmActivationType = crmActivationType;
    }

    public String getCrmActivationPin() {
        return crmActivationPin;
    }

    public void setCrmActivationPin(String crmActivationPin) {
        this.crmActivationPin = crmActivationPin;
    }

    @Override
    public String toString() {
        return "CardNumberDetails{" +
                "crmCardNumber='" + crmCardNumber + '\'' +
                ", crmTopupAmount=" + crmTopupAmount +
                ", crmActivationType=" + crmActivationType +
                ", crmActivationPin='" + crmActivationPin + '\'' +
                '}';
    }
}
