package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Tier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface TierRepository extends  BaseRepository<Tier,Long> {


    public Tier findByTieId(Long tieId);
    public Tier findByTieParentGroupAndTieName(Long tieParentGroup, String tieName);
    public Tier findByTieName(String tieName);
    public List<Tier> findByTieParentGroup(Long tieParentGroup);


}
