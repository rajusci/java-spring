package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;

/**
 * Created by saneeshci on 29/8/14.
 */
public interface PartyApprovalService extends BaseService<PartyApproval> {

    public PartyApproval findByPapId(Long papId);
    public PartyApproval getPartyApprovalForLinkRequest(Long papApprover,Long papRequestor,Integer papType);
    public List<PartyApproval> findByPapApproverAndPapType(Long papApprover,Integer papType);

    List<PartyApproval > getPartyApproval(Long papApprover, Long papRequestor, Integer papType, Long papRequest);

    public Integer sendApproval(Customer requestor, Customer approver, Long requestId, Integer papType, String toLoyaltyId, String reference) throws InspireNetzException;


    PartyApproval getExistingPartyApproval(Customer approver, Customer requestor, Integer papType, Long refId);

    public PartyApproval savePartyApproval(PartyApproval partyApproval);
    public boolean deletePartyApproval(Long brnId);

    boolean isPartyApprovalRequestExpired(PartyApproval partyApprosval);
    boolean changePartyApprovalForRequest(Long merchantNo, String requestor, String approver, Integer papType, Integer papStatus) throws InspireNetzException;

    boolean changePartyApprovalForRedemption(Long merchantNo, String requestor, String approver, Integer papType, String papReference, Integer papStatus) throws InspireNetzException;

    boolean validateAndChangePartyApprovalForRequest(Long merchantNo, String requestor, String approver, Integer papType, Integer papStatus) throws InspireNetzException;

    public List<PartyApproval> findByPapApprover(Long merchantNo,String loyaltyId);

    List<PartyApproval> getPendingPartyApproval(Long merchantNo) throws InspireNetzException;

    public void expiringLinkRequest(String cusLoyaltyId,Long merchantNo,Integer requestType) throws InspireNetzException;

    public List<PartyApproval> getPartyApprovalForRedemption(Long approver,Long requestor,Integer papType,String papReference);

    void changePartyApproval(Long merchantNo, String requestorLoyaltyId, String approverLoyaltyId, Integer requestType, String prdCode, Integer status, Integer requestType1) throws InspireNetzException;

    boolean changePartyApprovalForTransferPointRequest(Long merchantNo, String approverLoyaltyId, String requestorLoyaltyId, Integer requestType, String reference, Integer status) throws InspireNetzException;
}
