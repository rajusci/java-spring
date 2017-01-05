package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.SyncStatus;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SyncStatusService extends BaseService<SyncStatus> {


    public SyncStatus findBySysId(Long sysId);

    public Long getLastBatchIndex(Long sysMerchantNo,Long  sysLocation,Integer sysType,Date sysDate);

    public SyncStatus getRecentSyncStatusByType(Long sysMerchantNo,Long  sysLocation,Integer sysType,Date sysDate);


    public List<SyncStatus> findBySysMerchantNoAndSysLocationAndSysTypeAndSysDate(Long sysMerchantNo,Long  sysLocation,Integer sysType,Date sysDate);

    public SyncStatus saveSyncStatus(SyncStatus syncStatus);

    public List<Map<String, Object>> listSyncStatus(Long sysMerchantNo, Long sysLocation,Date sysDate);


    public void deleteSyncStatus(SyncStatus syncStatus);



    }
