package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.PartyApproval;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by saneeshci on 29/8/14.
 */
public interface PartyApprovalRepository extends  BaseRepository<PartyApproval,Long> {

    public PartyApproval findByPapId(Long papId);
    public PartyApproval findByPapApproverAndPapRequestorAndPapType(Long papApprover,Long papRequestor,Integer papType);
    public List<PartyApproval> findByPapApproverAndPapType(Long papApprover,Integer papType);
    public List<PartyApproval> findByPapApproverAndPapRequestorAndPapTypeAndPapRequest(Long papApprover,Long papRequestor,Integer papType,Long papRequest);
    public List<PartyApproval> findByPapApproverAndPapRequestorAndPapTypeAndPapReferenceOrderByPapIdDesc(Long papApprover,Long papRequestor,Integer papType,String papReference);


    @Query("select p from PartyApproval p where p.papApprover=?1 and ( (p.papStatus = '1' or p.papStatus = '2'))")
    public List<PartyApproval> findByPapApprover(Long papApprover);

    @Query("select p from PartyApproval p where p.papRequestor=?1 and ( (p.papStatus = '1' or p.papStatus = '2'))")
    public List<PartyApproval> findByPapRequestor(Long papRequestor);
}
