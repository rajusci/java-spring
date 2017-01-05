package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.NotificationCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface NotificationCampaignRepository extends  BaseRepository<NotificationCampaign,Long> {

    public NotificationCampaign findByNtcId(Long ntcId);
    public Page<NotificationCampaign> findByNtcMerchantNo(Long ntcMerchantNo,Pageable pageable);
    public Page<NotificationCampaign> findByNtcNameLikeAndNtcMerchantNo(String ntcName,Long ntcMerchantNo,Pageable pageable);

}
