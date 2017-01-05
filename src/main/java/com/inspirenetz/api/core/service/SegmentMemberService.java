package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.SegmentMemberDetails;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface SegmentMemberService extends BaseService<SegmentMember> {


    public SegmentMember findBySgmId(Long sgmId);
    public SegmentMember findBySgmSegmentIdAndSgmCustomerNo(Long sgmSegmentId,Long sgmCustomerNo);
    public Page<SegmentMember> findBySgmSegmentId(Long sgmSegmentId,Pageable pageable);
    public List<SegmentMember> findBySgmCustomerNo(Long sgmCustomerNo);
    public Map<Long,SegmentMember> getSegmentMemberMapBySegmentId(List<SegmentMember> segmentMemberList);

    public boolean isDuplicateSegmentMemberExisting(SegmentMember SegmentMember);
    public SegmentMember saveSegmentMember(SegmentMember SegmentMember) throws InspireNetzException;
    public boolean deleteSegmentMember(Long sgmId) throws InspireNetzException;

    public SegmentMember validateAndSaveSegmentMember(SegmentMember SegmentMember) throws InspireNetzException;
    public boolean validateAndDeleteSegmentMember(Long sgmId) throws InspireNetzException;

    public boolean removeMemberForSegmentUpdations(CustomerSegment customerSegment,Customer customer);

    public Page<SegmentMemberDetails> getMemberDetails(Long sgmSegmentId,Pageable pageable);

    public SegmentMember assignCustomerToSegment(String loyaltyId, String segmentName,Long merchantNo) throws InspireNetzException;

}
