package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SyncStatus;

import java.sql.Date;
import java.util.List;

/**
 * Created by fayizkci on 11/09/15.
 */
public interface SyncStatusRepository extends  BaseRepository<SyncStatus,Long> {

    public SyncStatus findBySysId(Long sysId);
    public List<SyncStatus> findBySysMerchantNo(Long sysMerchantNo);
    public List<SyncStatus> findBySysMerchantNoAndSysLocation(Long sysMerchantNo,Long sysLocation);

    public List<SyncStatus> findBySysMerchantNoAndSysLocationAndSysTypeAndSysDateOrderBySysBatchDesc(Long sysMerchantNo, Long sysLocation,Integer sysType, Date sysDate);
}
