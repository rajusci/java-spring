package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.NotificationCampaignResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface NotificationCampaignService extends BaseService<NotificationCampaign> {


    public NotificationCampaign findByNtcId(Long ntcId) throws InspireNetzException;
    public Page<NotificationCampaign> findByNtcNameAndNtcMerchantNo(String ntcName,Long ntcMerchantNo,Pageable pageable);
    public Page<NotificationCampaign> findByNtcMerchantNo(Long ntcMerchantNo,Pageable pageable);


    public NotificationCampaign validateAndSaveNotificationCampaign(NotificationCampaignResource notificationCampaignResource) throws InspireNetzException;
    public NotificationCampaign saveNotificationCampaign(NotificationCampaign notificationCampaign);
    public void sendNotification(NotificationCampaign notificationCampaign);
    public boolean deleteNotificationCampaign(Long rvrId);

    Page<NotificationCampaign> searchNotificationCampaigns(String searchField, String query, Pageable pageable);
}
