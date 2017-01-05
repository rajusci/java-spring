package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.DrawChance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface DrawChanceRepository extends  BaseRepository<DrawChance,Long> {

    public DrawChance findByDrcId(Long drcId);
    public DrawChance findByDrcCustomerNoAndDrcType(Long drcCustomerNo,Integer drcType);
    public List<DrawChance> findByDrcTypeAndDrcStatus(Integer drcType,Integer drcStatus);

}
