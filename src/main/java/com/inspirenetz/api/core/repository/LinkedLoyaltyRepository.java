package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LinkedLoyalty;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 11/3/14.
 */
public interface LinkedLoyaltyRepository extends BaseRepository<LinkedLoyalty,Long> {

    public LinkedLoyalty findByLilId(Long lilId);
    public LinkedLoyalty findByLilChildCustomerNo(Long lilChildCustomerNo);
    public List<LinkedLoyalty> findByLilParentCustomerNo(Long lilParentCustomerNo);

    @Query("select count (l.lilParentCustomerNo) from LinkedLoyalty l where l.lilParentCustomerNo=?1")
    public Long  findByCountLilParentCustomerNo(Long lilParentCustomerNo);

    @Query("select L from LinkedLoyalty L where L.lilParentCustomerNo =?1 or L.lilChildCustomerNo =?1")
    public List<LinkedLoyalty> getLinkedAccounts(Long lilCustomerNo);

}