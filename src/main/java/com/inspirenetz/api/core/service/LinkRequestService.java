package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface LinkRequestService extends BaseService<LinkRequest> {


    public LinkRequest findByLrqId(Long lrqId);
    public Page<LinkRequest> findByLrqSourceCustomerLrqMerchantNo(Long lrqSourceCustomer,Long lrqMerchantNo,Pageable pageable);
    public Page<LinkRequest> searchLinkRequests(String filter,String query,Long merchantNo,Pageable pageable) throws InspireNetzException;
    public LinkRequest getLinkRequestInfo(Long lrqId) throws InspireNetzException;
    public Long saveUnlinkingRequest(String primaryLoyaltyId, String childLoyaltyId,String lrqInitiator,Integer lrqSource, Long merchantNo,boolean isUnregister) throws InspireNetzException;

    void checkAccountLinkingStatus(Customer primary);

    public LinkRequest createLinkRequest(Long merchantNo, String primaryLoyaltyId, String childLoyaltyId, String lrqInitiator, Integer lrqSource) throws InspireNetzException;

    public LinkRequest validateAndSaveLinkRequest(LinkRequest linkRequest ) throws InspireNetzException;
    public LinkRequest saveLinkRequest(LinkRequest linkRequest);
    public boolean validateAndDeleteLinkRequest(Long lrqId) throws InspireNetzException;
    public boolean deleteLinkRequest(Long lrqId);


    public boolean unlinkAllRequest(String loyaltyId, Long merchantNo,boolean isUnregisterRequest) throws InspireNetzException;

    public boolean unlinkCustomerAccounts(Customer customer) throws InspireNetzException;
}
