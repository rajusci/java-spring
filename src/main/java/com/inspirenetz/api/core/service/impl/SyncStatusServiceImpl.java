package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.SyncProcessStatus;
import com.inspirenetz.api.core.dictionary.SyncType;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SyncStatusRepository;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.SyncStatusService;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class SyncStatusServiceImpl extends BaseServiceImpl<SyncStatus> implements SyncStatusService {


    private static Logger log = LoggerFactory.getLogger(SyncStatusServiceImpl.class);


    @Autowired
    SyncStatusRepository syncStatusRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantService merchantService;

    @Autowired
    MerchantLocationService merchantLocationService;


    public SyncStatusServiceImpl() {

        super(SyncStatus.class);

    }


    @Override
    protected BaseRepository<SyncStatus,Long> getDao() {
        return syncStatusRepository;
    }

    @Override
    public SyncStatus findBySysId(Long sysId){

        return syncStatusRepository.findBySysId(sysId);
    }

    @Override
    public Long getLastBatchIndex(Long sysMerchantNo, Long sysLocation,Integer sysType, Date sysDate) {

        List<SyncStatus> syncStatuses= syncStatusRepository.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDateOrderBySysBatchDesc(sysMerchantNo,sysLocation,sysType,sysDate);

        if(syncStatuses!=null&& syncStatuses.size()>0){

            return syncStatuses.get(0).getSysBatch();
        }

        return 0L;
    }

    @Override
    public SyncStatus getRecentSyncStatusByType(Long sysMerchantNo, Long sysLocation,Integer sysType, Date sysDate) {

        List<SyncStatus> syncStatuses= syncStatusRepository.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDateOrderBySysBatchDesc(sysMerchantNo,sysLocation,sysType,sysDate);

        if(syncStatuses!=null&& syncStatuses.size()>0){

            return syncStatuses.get(0);
        }

        return null;
    }

    @Override
    public List<SyncStatus> findBySysMerchantNoAndSysLocationAndSysTypeAndSysDate(Long sysMerchantNo, Long sysLocation,Integer sysType, Date sysDate) {

        List<SyncStatus> syncStatuses= syncStatusRepository.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDateOrderBySysBatchDesc(sysMerchantNo,sysLocation,sysType,sysDate);



        return syncStatuses;
    }

    @Override
    public SyncStatus saveSyncStatus(SyncStatus syncStatus) {

        return syncStatusRepository.save(syncStatus);
    }

    @Override
    public List<Map<String, Object>> listSyncStatus(Long sysMerchantNo, Long sysLocation,Date sysDate){


        //create list for returning membership data
        List<Map<String , Object> > syncStatusList = new ArrayList<>();

        List<Merchant> merchants=new ArrayList<Merchant>();

        if(sysMerchantNo==null||sysMerchantNo.longValue()==0){

           merchants=merchantService.findActiveMerchants(0L);

        }else{

            merchants=merchantService.findActiveMerchants(sysMerchantNo);
        }

        for(Merchant merchant : merchants){



            List<MerchantLocation>merchantLocations= merchantLocationService.findByMerchantNoAndMerchantLocation(merchant.getMerMerchantNo(),sysLocation);

            for(MerchantLocation merchantLocation:merchantLocations){

                //map holds the merhant details
                Map<String ,Object > syncStatusData = new HashMap<>();

                //put the merchant details to the map
                syncStatusData.put("sysMerchantNo",merchant.getMerMerchantNo()+"");
                syncStatusData.put("sysMerchantName",merchant.getMerMerchantName());
                syncStatusData.put("sysLocation",merchantLocation.getMelId()+"");
                syncStatusData.put("sysLocationName",merchantLocation.getMelLocation());
                syncStatusData.put("sysDate",sysDate);

                SyncStatus cusSyncStatus=getRecentSyncStatusByType(merchant.getMerMerchantNo(), merchantLocation.getMelId(), SyncType.CUSTOMERS, sysDate);

                if(cusSyncStatus!=null){

                    syncStatusData.put("sysCusLastSyncDate",cusSyncStatus.getSysDate());
                    syncStatusData.put("sysCusLastSyncTime",cusSyncStatus.getSysTimestamp());
                    syncStatusData.put("sysCusLastSyncStatus",cusSyncStatus.getSysStatus());
                }else{

                    syncStatusData.put("sysCusLastSyncDate","");
                    syncStatusData.put("sysCusLastSyncTime","");
                    syncStatusData.put("sysCusLastSyncStatus", SyncProcessStatus.FAILED);
                }

                SyncStatus itemSyncStatus=getRecentSyncStatusByType(merchant.getMerMerchantNo(), merchantLocation.getMelId(), SyncType.ITEMS, sysDate);

                if(itemSyncStatus!=null){

                    syncStatusData.put("sysItmLastSyncDate",itemSyncStatus.getSysDate());
                    syncStatusData.put("sysItmLastSyncTime",itemSyncStatus.getSysTimestamp());
                    syncStatusData.put("sysItmLastSyncStatus",itemSyncStatus.getSysStatus());

                }else{

                    syncStatusData.put("sysItmLastSyncDate","");
                    syncStatusData.put("sysItmLastSyncTime","");
                    syncStatusData.put("sysItmLastSyncStatus", SyncProcessStatus.FAILED);
                }

                SyncStatus salSyncStatus=getRecentSyncStatusByType(merchant.getMerMerchantNo(), merchantLocation.getMelId(), SyncType.SALES, sysDate);

                if(salSyncStatus!=null){

                    syncStatusData.put("sysSalLastSyncDate",salSyncStatus.getSysDate());
                    syncStatusData.put("sysSalLastSyncTime",salSyncStatus.getSysTimestamp());
                    syncStatusData.put("sysSalLastSyncStatus",salSyncStatus.getSysStatus());

                }else{

                    syncStatusData.put("sysSalLastSyncDate","");
                    syncStatusData.put("sysSalLastSyncTime","");
                    syncStatusData.put("sysSalLastSyncStatus", SyncProcessStatus.FAILED);
                }

                syncStatusList.add(syncStatusData);

            }

        }

        return syncStatusList;
    }


    @Override
    public void deleteSyncStatus(SyncStatus syncStatus){

        syncStatusRepository.delete(syncStatus);
    }
}
