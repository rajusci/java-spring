package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.TierGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by saneeshci on 20/8/14.
 */
public interface TierGroupRepository extends  BaseRepository<TierGroup,Long> {

    @Query("select T from TierGroup T where T.tigMerchantNo = ?1 and ( ?2 = 0L or T.tigLocation = ?2 or T.tigLocation = 0L )")
    public Page<TierGroup> searchTierGroups(Long tigMerchantNo,Long tigLocation, Pageable pageable);

    @Query("select T from TierGroup T where T.tigMerchantNo = ?1 and ( ?2 = 0L or T.tigLocation = ?2 or T.tigLocation = 0L ) and T.tigName like ?3")
    public Page<TierGroup> searchTierGroupsByName(Long tigMerchantNo,Long tigLocation, String tigName,Pageable pageable);


    public TierGroup findByTigId(Long tigId);
    public List<TierGroup> findByTigMerchantNo(Long tigMerchantNo);
    public TierGroup findByTigMerchantNoAndTigName(Long tigMerchantNo, String tigName);


}
