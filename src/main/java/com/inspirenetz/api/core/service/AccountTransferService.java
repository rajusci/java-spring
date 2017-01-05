package com.inspirenetz.api.core.service;

import com.inspirenetz.api.rest.exception.InspireNetzException;

/**
 * Created by sandheepgr on 8/9/14.
 */
public interface AccountTransferService {


    public boolean transferAccount( String oldLoyaltyId, String newLoyaltyId ,Long merchantNo) throws InspireNetzException;

}
