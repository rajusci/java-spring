package com.inspirenetz.api.core.dictionary;

/**
 * Created by ameen on 26/11/15.
 */
public class CardTransferAuthenticationRequest {

    public Integer authenticationType=CardAuthType.OTP;

    public Integer getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(Integer authenticationType) {
        this.authenticationType = authenticationType;
    }
}
