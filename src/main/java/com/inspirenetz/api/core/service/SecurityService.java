package com.inspirenetz.api.core.service;

import com.inspirenetz.api.rest.exception.InspireNetzException;

/**
 * Created by saneeshci on 07/10/15.
 */
public interface SecurityService {

    public boolean enablePinForCustomer(String loyaltyId, String pin, Long merchantNo, int type) throws InspireNetzException;

    public boolean disablePinForCustomer(String loyaltyId, Long merchantNo, int type) throws InspireNetzException;

    public boolean validatePinForCustomer(String loyaltyId, String pin, Long merchantNo) throws InspireNetzException;

    public boolean resetPinForCustomer(String loyaltyId, Long merchantNo) throws InspireNetzException;

    public boolean disablePinWithValidation(String loyaltyId, String pin, Long merchantNo, int type) throws InspireNetzException;

    public boolean resetPinWithValidation(String loyaltyId, String oldPin, String newPin, Long merchantNo) throws InspireNetzException;
}
