package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SpielText;

import java.util.List;

/**
 * Created by alameen on 2/2/15.
 */
public interface SpielTextRepository extends BaseRepository<SpielText,Long> {

    public List<SpielText> findBySptRef(Long sptRef);

    public SpielText findBySptRefAndSptChannelAndSptLocation(Long sptRef,Integer sptChannel,Long sptLocation);
}
