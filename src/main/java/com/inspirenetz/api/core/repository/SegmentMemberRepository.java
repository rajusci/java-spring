package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SegmentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface SegmentMemberRepository extends BaseRepository<SegmentMember,Long> {

    public SegmentMember findBySgmId(Long sgmId);
    public SegmentMember findBySgmSegmentIdAndSgmCustomerNo(Long sgmSegmentId,Long sgmCustomerNo);
    public Page<SegmentMember> findBySgmSegmentId(Long sgmSegmentId,Pageable pageable);
    public List<SegmentMember> findBySgmCustomerNo(Long sgmCustomerNo);
    public Page<SegmentMember> findBySgmSegmentIdAndSgmMerchantNo(Long sgmSegmentId,Long sgmMerchantNo,Pageable pageable);


}
