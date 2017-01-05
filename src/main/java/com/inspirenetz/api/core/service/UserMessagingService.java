package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.MessageWrapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandheepgr on 18/9/14.
 */
public interface UserMessagingService {

    /*//This Method Not using now,instead of this use transmit notification
    public boolean sendSMS(String spielName,String mobile, HashMap<String,String> fields) throws InspireNetzException;

    //This Method Not using now,instead of this use transmit notification
    public boolean sendEmail(String spielName,String mailId, HashMap<String,String> fields) throws InspireNetzException;*/

    boolean transmitNotification(MessageWrapper messageWrapper) throws InspireNetzException;

    public APIResponseObject transmitSMS(Map<String, String> parameters,Long merchantNo) throws InspireNetzException;

    boolean transmitSMSNotifications(Customer customer, MessageWrapper messageWrapper) throws InspireNetzException;

    boolean transmitMailNotifications(Customer customer, MessageWrapper messageWrapper) throws InspireNetzException;

    public APIResponseObject transmitMail(Map<String, String> parameters, Long merchantNo) throws InspireNetzException;

    boolean transmitBulkNotification(Integer channel,String to,String subject,Long merchantNo,String message);
}
