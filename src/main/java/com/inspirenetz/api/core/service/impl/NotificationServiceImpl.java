package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationType;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.NotificationRepository;
import com.inspirenetz.api.core.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification> implements NotificationService {

    private static Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationServiceImpl() {

        super(Notification.class);

    }


    @Override
    protected BaseRepository<Notification,Long> getDao() {

        return notificationRepository;

    }


    @Override
    public Notification findByNtfNotificationId(Long ntfNotificationId) {

        // Get the notification
        Notification notification = notificationRepository.findByNtfNotificationId(ntfNotificationId);

        // Return the object
        return notification;

    }

    @Override
    public Page<Notification> findByNtfRecepientTypeAndNtfRecepient(Integer ntfRecepientType, Long ntfRecepient, Pageable pageable) {

        // Get the Notification page
        Page<Notification> notificationPage = notificationRepository.findByNtfRecepientTypeAndNtfRecepient(ntfRecepientType,ntfRecepient,pageable);

        // Return the page
        return notificationPage;

    }

    @Override
    public Page<Notification> findByNtfRecepientTypeAndNtfRecepientAndNtfStatus(Integer ntfRecepientType, Long ntfRecepient, Integer ntfStatus, Pageable pageable) {

        // Get the notitification
        Page<Notification> notificationPage = notificationRepository.findByNtfRecepientTypeAndNtfRecepientAndNtfStatus(ntfRecepientType,ntfRecepient,ntfStatus,pageable);

        // Return the notification
        return notificationPage;

    }

    @Override
    public Notification saveNotification(Notification Notification) {

        // Save the Notification
        Notification = notificationRepository.save(Notification);

        // Return the Notification object
        return Notification;

    }

    @Override
    public boolean deleteNotification(Long ntfNotificationId) {

        // Delete the Notification
        notificationRepository.delete(ntfNotificationId);

        // Return true
        return true;
    }

    /**
     *
     * Function to get the notifications
     *
     * @param  userType 	: The type of user for which the data is fetched
     * @param  userNo	 	: The user number for fetching the notification for user
     * @param  ntfType	    : Type of notification
     * @param  merchantNo   : Merchant number if the user type is merchant admin / merchant user
     * @param  ntfStatus    : notification status
     */

    @Override
    public List<Notification> getNotifications(Integer userType, Long userNo, Integer ntfType, Long merchantNo,Integer ntfStatus) {

        //List for holding notification recipient types
        List<Integer> ntfRecipientTypeList=new ArrayList<Integer>();

        //List for holding notification types
        List<Integer> ntfTypeList=new ArrayList<Integer>();

        //List for holding notifications
        List<Notification> notificationList=null;

        if(userType== UserType.SUPER_ADMIN || userType== UserType.ADMIN){

            //If the user type is  superadmin or admin, then we fetch the notification for Inspirenetz, or type admins
            ntfRecipientTypeList.addAll(Arrays.asList(NotificationRecepientType.INSPIRENETZ,NotificationRecepientType.ADMINS));

        }else if(userType==UserType.MERCHANT_ADMIN){

            // If the user type is the merchant admin, then fetch all the notifications meant for ALL_MERCHANT_ADMINS, ALL_MERCHANTS , TYPE_ADMINS
            ntfRecipientTypeList.addAll(Arrays.asList(NotificationRecepientType.ALL_MERCHANT_ADMINS,NotificationRecepientType.ALL_MERCHANTS,NotificationRecepientType.ADMINS));

        }else if(userType==UserType.MERCHANT_USER){

            // If the user is merchant user, fetch the notification for ALL_MERCHANTS
            ntfRecipientTypeList.add(NotificationRecepientType.ALL_MERCHANTS);


        }else if(userType==UserType.CUSTOMER){

            // If the usertype is a customer, then we fetch the notifications meant for all users
            ntfRecipientTypeList.add(NotificationRecepientType.ALL_USERS);

        }



        if(ntfType== NotificationType.ALL_MERCHANT_ACTIVITY){

            //This notification type is exclusively for Inspirenetz admins and Merchant admin
            //to view all the merchant users while getting data for a particular merchant
            ntfTypeList.addAll(Arrays.asList(NotificationType.MERCHANT_ADMIN_ACTIVITY,NotificationType.MERCHANT_USER_ACTIVITY));

        }else if(ntfType==NotificationType.INSPIRENETZ_ADMIN_ACTIVITY){

            // The following conditionals aree xclusively used by Inspirenetz Admins for
            // listing all the information for the InspireNetz admins
            ntfTypeList.addAll(Arrays.asList(NotificationType.ADMIN_ACTIVITY,NotificationType.SUPER_ADMIN_ACTIVITY));

        }else{

            ntfTypeList.add(ntfType);
        }

        // If the user type is the merchant admin or merchant user and merchnat no is passed,then Get the notifications created for user merchant
        if((userType==UserType.MERCHANT_ADMIN ||userType==UserType.MERCHANT_USER)&& (merchantNo!=0)){

            //Call Notification Repository
            notificationList=notificationRepository.getNotificationsForUserAndMerchant(ntfRecipientTypeList,ntfTypeList,NotificationRecepientType.USER,userNo,NotificationRecepientType.MERCHANT,merchantNo,ntfStatus);

        }else{

            notificationList=notificationRepository.getNotificationsForUser(ntfRecipientTypeList,ntfTypeList,NotificationRecepientType.USER,userNo,ntfStatus);

        }

        return notificationList;
    }

    /**
     *
     * Function to get the notifications count
     *
     * @param  userType 	: The type of user for which the data is fetched
     * @param  userNo	 	: The user number for fetching the notification for user
     * @param  ntfType	    : Type of notification
     * @param  merchantNo   : Merchant number if the user type is merchant admin / merchant user
     * @param  ntfStatus    : notification status
     */

    @Override
    public Integer getNotificationsCount(Integer userType, Long userNo, Integer ntfType, Long merchantNo,Integer ntfStatus) {

        //Fetch Notification List
        List<Notification> notificationList=getNotifications(userType,userNo,ntfType,merchantNo,ntfStatus);

        //Check if notification List null
        if(notificationList==null && notificationList.size()==0){

            return 0;

        }else{

            return notificationList.size();
        }
    }
}
