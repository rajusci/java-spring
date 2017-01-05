package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.rest.exception.InspireNetzException;

/**
 * Created by sandheepgr on 28/8/14.
 */
public interface AccountLinkingService {

    public boolean processLinkRequest(LinkRequest linkRequest, String loyaltyId) throws InspireNetzException;

}
