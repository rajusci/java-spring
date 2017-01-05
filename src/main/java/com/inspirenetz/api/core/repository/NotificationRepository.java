package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Notification;
import org.antlr.analysis.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface NotificationRepository extends BaseRepository<Notification,Long> {

    public Notification findByNtfNotificationId(Long ntfNotificationId);
    public Page<Notification> findByNtfRecepientTypeAndNtfRecepient(Integer ntfRecepientType , Long ntfRecepient,Pageable pageable);
    public Page<Notification> findByNtfRecepientTypeAndNtfRecepientAndNtfStatus(Integer ntfRecepientType , Long ntfRecepient, Integer ntfStatus, Pageable pageable);

    @Query("select N from Notification N where N.ntfRecepientType in(?1) and N.ntfType in (?2)  or (N.ntfRecepientType=?3 and N.ntfRecepient=?4)and N.ntfActivityUserNo <> ?4  and N.ntfStatus = ?5")
    public List<Notification> getNotificationsForUser(List<Integer> ntfRecepientTypes,List<Integer> ntfTypes,Integer ntfRecepientTypeUser,Long ntfUserNo,Integer ntfStatus);

    @Query("select N from Notification N where N.ntfRecepientType in(?1) and N.ntfType in (?2)  or (N.ntfRecepientType=?3 and N.ntfRecepient=?4) and N.ntfActivityUserNo <> ?4 or (N.ntfRecepientType=?5 and N.ntfRecepient=?6) and N.ntfStatus = ?7")
    public List<Notification> getNotificationsForUserAndMerchant(List<Integer> ntfRecepientTypes,List<Integer> ntfTypes,Integer ntfRecepientTypeUser,Long ntfUserNo,Integer ntfRecepientTypeMerchant,Long merchantNo, Integer ntfStatus);


}
