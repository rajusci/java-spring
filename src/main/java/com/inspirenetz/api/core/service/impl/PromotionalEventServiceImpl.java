package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PromotionalEventRepository;
import com.inspirenetz.api.core.service.PromotionalEventService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;


/**
 * Created by saneeshci on 29/9/14.
 */

@Service
public class PromotionalEventServiceImpl extends BaseServiceImpl<PromotionalEvent> implements PromotionalEventService {



    private static Logger log = LoggerFactory.getLogger(PromotionalEventServiceImpl.class);

    @Autowired
    PromotionalEventRepository promotionalEventRepository;


    @Autowired
    private AuthSessionUtils authSessionUtils;

    public PromotionalEventServiceImpl() {

        super(PromotionalEvent.class);

    }
    @Override
    protected BaseRepository<PromotionalEvent, Long> getDao() {
        return promotionalEventRepository;
    }

    @Override
    public PromotionalEvent findByPreId(Long preId) {

        // Getting data based by secid
        PromotionalEvent promotionalEvent=promotionalEventRepository.findByPreId(preId);

        //return the promotionalEvent object
        return promotionalEvent;
    }

    @Override
    public PromotionalEvent findByPreEventCode(String preEventCode) {

        return promotionalEventRepository.findByPreEventCode(preEventCode);
    }

    @Override
    public boolean isDuplicateEventExisting(PromotionalEvent promotionalEvent) {

        PromotionalEvent exPromotionalEvent = findByPreEventCode(promotionalEvent.getPreEventCode());

        //check whether preId is null
        if(promotionalEvent.getPreId() == null || promotionalEvent.getPreId() == 0L){

            //if an event exists with same code return true
            if(exPromotionalEvent != null){

                return true;
            }

        } else{

            if(exPromotionalEvent != null && exPromotionalEvent.getPreId().longValue() != promotionalEvent.getPreId().longValue()){

                return true;
            }
        }

        return false;
    }

    @Override
    public Page<PromotionalEvent> findByPreMerchantNo(Long merchantNo,Pageable pageable) throws InspireNetzException {

        Page<PromotionalEvent> promotionalEventList = promotionalEventRepository.findByPreMerchantNo(merchantNo,pageable);

        //return the security setting
        return promotionalEventList;


    }

    @Override
    public Page<PromotionalEvent> findByPreMerchantNoAndPreEventType(Long merchantNo, Integer preEventType, Pageable pageable) throws InspireNetzException {

        Page<PromotionalEvent > promotionalEvents  = promotionalEventRepository.findByPreMerchantNoAndPreEventType(merchantNo,preEventType,pageable);

        return promotionalEvents;

    }

    @Override
    public Page<PromotionalEvent> searchPromotionalEvents(String filter, String query, Long merchantNo , Pageable pageable) {

        Page<PromotionalEvent > promotionalEvents = null;
        if(filter.equals("name")){

            promotionalEvents = promotionalEventRepository.findByPreMerchantNoAndPreEventNameLike(merchantNo, "%" + query + "%", pageable);

        } else {

            promotionalEvents = promotionalEventRepository.findByPreMerchantNo(merchantNo, pageable);

        }

        return promotionalEvents;

    }

    @Override
    public Page<PromotionalEvent> getValidEventsByDate(Long merchantNo, Date date , Pageable pageable) {

        Page<PromotionalEvent > promotionalEvents = null;

        //call the repository method to get the data
        promotionalEvents = promotionalEventRepository.getValidEventsByMerchantNoAndDate(merchantNo,date,pageable);

        //return the data
        return promotionalEvents;
    }


    @Override
    public PromotionalEvent savePromotionalEvent(PromotionalEvent promotionalEvent) {

        //saving the role access right
        return promotionalEventRepository.save(promotionalEvent);
    }

    @Override
    public boolean validateAndDeletePromotionalEvent(Long preId) throws InspireNetzException {

        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();

        Integer userType = authSessionUtils.getUserType();

        // Get the event information
        PromotionalEvent promotionalEvent1 = findByPreId(preId);

        // Check if the role is found
        if ( promotionalEvent1 == null || promotionalEvent1.getPreId() == null) {

            // Log the response
            log.info("deletePromotionalEvent - Response : No Event information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletin
        if ( (userType.intValue() == UserType.MERCHANT_ADMIN  || userType.intValue() == UserType.MERCHANT_USER) && promotionalEvent1.getPreMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deletePromotionalEvent - Response : Not authorized");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the role and set the retData fields
        deletePromotionalEvent(promotionalEvent1);

        // Return true
        return true;

    }

    @Override
    public boolean deletePromotionalEvent(PromotionalEvent promotionalEvent) throws InspireNetzException {

        //for deleting the roll access right
        promotionalEventRepository.delete(promotionalEvent);

        return true;
    }

    @Override
    public boolean validateAndDeletePromotionalEvent(PromotionalEvent promotionalEvent) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_PROMOTIONAL_EVENT);

        return deletePromotionalEvent(promotionalEvent);
    }


    @Override
    public PromotionalEvent validateAndSavePromotionalEvent(PromotionalEvent promotionalEvent) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_PROMOTIONAL_EVENT);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        Long merchantNo = authSessionUtils.getMerchantNo();

        promotionalEvent.setPreMerchantNo(merchantNo);

        if (   userType != UserType.MERCHANT_ADMIN  && userType != UserType.MERCHANT_USER) {

            // Log the information
            log.info("Exception in :: validateAndSavePromotionalEvent UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Check if the MessageSpiel is already existing
        boolean isExists = isDuplicateEventExisting(promotionalEvent);

        // If duplicate is existing, then, we need to throw exception
        if ( isExists ) {

            // Log the information
            log.info("Exception in validateAndSavePromotionalEvent :: duplicate Entry Exist");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        // If the roll access right .getMsiId is  null, then set the created_by, else set the updated_by
        if ( promotionalEvent.getPreId() == null ) {

            promotionalEvent.setCreatedBy(auditDetails);

        } else {

            promotionalEvent.setUpdatedBy(auditDetails);

        }

        // Save the object
        promotionalEvent = savePromotionalEvent(promotionalEvent);

        // Check if the messageSpiel is saved
        if ( promotionalEvent.getPreId() == null ) {

            // Log the response
            log.info("validateAndSavePromotionalEvent - Response : Unable to save the message spiel information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // return the object
        return promotionalEvent;


    }





}
