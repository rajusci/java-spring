package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface NotificationService extends BaseService<Notification> {

    public Notification findByNtfNotificationId(Long ntfNotificationId);
    public Page<Notification> findByNtfRecepientTypeAndNtfRecepient(Integer ntfRecepientType , Long ntfRecepient,Pageable pageable);
    public Page<Notification> findByNtfRecepientTypeAndNtfRecepientAndNtfStatus(Integer ntfRecepientType , Long ntfRecepient, Integer ntfStatus, Pageable pageable);
    
    public Notification saveNotification(Notification notification);
    public boolean deleteNotification(Long ntfNotificationId);

    public List<Notification> getNotifications(Integer userType,Long userNo,Integer ntfType,Long merchantNo,Integer ntfStatus);

    public Integer getNotificationsCount(Integer userType,Long userNo,Integer ntfType,Long merchantNo,Integer ntfStatus);

}
