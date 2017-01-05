package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.core.domain.validator.MessageSpielValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MessageSpielRepository;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.core.service.SpielTextService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 8/9/14.
 */
@Service
public class MessageSpielServiceImpl extends BaseServiceImpl<MessageSpiel> implements MessageSpielService{


    private static Logger log = LoggerFactory.getLogger(MessageSpielServiceImpl.class);

    @Autowired
    MessageSpielRepository messageSpielRepository;

    @Autowired
    SpielTextService spielTextService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    public MessageSpielServiceImpl() {

        super(MessageSpiel.class);

    }

    @Override
    protected BaseRepository<MessageSpiel, Long> getDao() {
        return messageSpielRepository;
    }

    @Override
    public Page<MessageSpiel> searchMessageSpiel(String filter, String query, Pageable pageable) {

        // Variable holding the page
        Page<MessageSpiel> messageSpielPage;

        // First check the filter and query
        if ( filter.equals("name") ) {

            // Get the data specific to name
            messageSpielPage = messageSpielRepository.findByMsiMerchantNoAndMsiNameLike(authSessionUtils.getMerchantNo(),"%"+query+"%",pageable);


        } else {

            // GEt all the data
            messageSpielPage = messageSpielRepository.findByMsiMerchantNo(authSessionUtils.getMerchantNo(),pageable);
        }


        // Return the page object
        return messageSpielPage;

    }

//    @Override
//    public List<MessageSpiel> findByMsiFunctionCode(Long msiFunctionCode) {
//
//        // Get all message spiel list based on msi function code
////        List<MessageSpiel> messageSpielList=messageSpielRepository.findByMsiFunctionCode(msiFunctionCode);
//
//        return messageSpielList;
//    }

    @Override
    public MessageSpiel findByMsiId(Long msiId) {

        //get message spiel object based by id
        MessageSpiel messageSpiel=messageSpielRepository.findByMsiId(msiId);

        return messageSpiel;
    }

    @Override
    public MessageSpiel findByMsiName(String msiName) {

        //get message spiel object based by name
        MessageSpiel messageSpiel=messageSpielRepository.findByMsiNameAndMsiMerchantNo(msiName,authSessionUtils.getMerchantNo());

        return messageSpiel;
    }

    @Override
    public MessageSpiel findByMsiNameAndMsiMerchantNo(String msiName,Long msiMerchantNo) {

        //get message spiel object based by name
        MessageSpiel messageSpiel=messageSpielRepository.findByMsiNameAndMsiMerchantNo(msiName,msiMerchantNo);

        return messageSpiel;
    }

    @Override
    public MessageSpiel saveMessageSpiel(MessageSpiel messageSpiel) {

       //saving the message spiel
        return messageSpielRepository.save(messageSpiel);
    }

    @Override
    public boolean deleteMessageSpiel(Long msiId) {

        //for deleting the message spiel
        messageSpielRepository.delete(msiId);

        return true;
    }

    @Override
    public boolean isDuplicateMessageSpielExisting(MessageSpiel messageSpiel) {

        // For checking message spiel is already existing or not
        MessageSpiel exMessageSpiel=messageSpielRepository.findByMsiNameAndMsiMerchantNo(messageSpiel.getMsiName(),authSessionUtils.getMerchantNo());

        // For checking message spiel id is null
        if ( messageSpiel.getMsiId() == null || messageSpiel.getMsiId() == 0L ) {

            // If the brand is not null, then return true
            if ( exMessageSpiel != null ) {

                return true;

            }

        } else {

            // Check if the message spiel  is null
            if ( exMessageSpiel != null && exMessageSpiel.getMsiId().longValue() != messageSpiel.getMsiId().longValue() ) {

                return true;

            }
        }

        //check if the channel and location is duplicate or not
        Set<SpielText> spielTextSet = messageSpiel.getSpielTexts();

        Integer duplicationFlag =0;

        //iterate set and check the duplication
        for (SpielText spielText:spielTextSet){


            for (SpielText spielText1:spielTextSet){

                //check its duplicate or not
                if(spielText1.getSptLocation().longValue()== spielText.getSptLocation().longValue() && spielText1.getSptChannel()==spielText.getSptChannel()){


                    //update duplicate flag one time
                    duplicationFlag =duplicationFlag+1;
                }
            }

            //check duplicate flag equal 1 if set zero and continue otherwise break
            if(duplicationFlag >1){

                log.info("Error for duplicate with channel and location");

                return true;

            }else {

                duplicationFlag =0;

                continue;
            }



        }

        // Return false;
        return false;


    }
    @Override
    public MessageSpiel validateAndSaveMessageSpiel(MessageSpiel messageSpiel) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MESSAGE_SPIEL);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        if ( userType != UserType.SUPER_ADMIN  &&  userType != UserType.ADMIN && userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndSaveMessageSpielImpl UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        //set merchant No
        messageSpiel.setMsiMerchantNo(authSessionUtils.getMerchantNo());

        // Create the Validator
        MessageSpielValidator validator = new MessageSpielValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(messageSpiel,"messageSpiel");

        // Validate the request
        validator.validate(messageSpiel,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveMessageSpiel - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // Check if the MessageSpiel is already existing
        boolean isExists = isDuplicateMessageSpielExisting(messageSpiel);

        // If duplicate is existing, then, we need to throw exception
        if ( isExists ) {

            // Log the information
            log.info("Exception in validateAndSaveMessageSpiel :: duplicate Entry Exist");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        // If the messageSpiel.getMsiId is  null, then set the created_by, else set the updated_by
        if ( messageSpiel.getMsiId() == null ) {

            messageSpiel.setCreatedBy(auditDetails);

            // Save the object
            messageSpiel = saveMessageSpiel(messageSpiel);

        } else {

            messageSpiel.setUpdatedBy(auditDetails);

            //update message spiel
            messageSpiel = updateMessageSpiel(messageSpiel);

        }

        // Check if the messageSpiel is saved
        if ( messageSpiel.getMsiId() == null ) {

            // Log the response
            log.info("validateAndSaveMessageSpiel - Response : Unable to save the message spiel information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // return the object
        return messageSpiel;


    }

    /**
     * @purpose :update messageSpiel with child
     * @param messageSpiel
     * @return
     */
    private MessageSpiel updateMessageSpiel(MessageSpiel messageSpiel) {


        //for getting roleAccessRights Id from the role object
        Set<SpielText> currentSpielList=messageSpiel.getSpielTexts();

        //initialise delete list
        Set<SpielText> spielDeleteSet=new HashSet<>();

        log.info("MessageSpielImpl :: updateMessageSpiel "+messageSpiel);

        //for fetching role access right based by role Id
        List<SpielText> spielTextBaseList=spielTextService.findBySptRef(messageSpiel.getMsiId());

        log.info("MessageSpielServiceImpl :: update Message spiel text base list from present"+spielTextBaseList);

        //for holding present list into arrayList
        List<Long> messageSpielDeletingList=new ArrayList<>();

        boolean toDelete=true;

        if(currentSpielList!=null && spielTextBaseList!=null){

            for(SpielText spielText :spielTextBaseList){

                for(SpielText spielText1 : currentSpielList){

                    //for getting value from Access List
                    Long sptId = spielText1.getSptId()==null ?0L:spielText1.getSptId().longValue();

                    if(sptId !=0L){

                        if(spielText.getSptId().longValue()==spielText1.getSptId().longValue()){

                            toDelete=false;

                            break;

                        }else{

                            toDelete=true;

                        }

                    }else{


                        continue;

                    }

                }

                if(toDelete==true){

                    spielDeleteSet.add(spielText);

                }


            }
        }

        // for deleting spielText
        if(spielDeleteSet!=null){

            spielTextService.deleteSpielTextSet(spielDeleteSet);

            log.info("delete Message Spiel details");
        }

        // for updating role and role access rights
        messageSpiel=messageSpielRepository.save(messageSpiel);


        return messageSpiel;

    }

    @Override
    public boolean validateAndDeleteMessageSpiel(Long msiId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_MESSAGE_SPIEL);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        // for checking authorisation of user
        if ( userType != UserType.SUPER_ADMIN && userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndDeleteMessageSpiel UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Get the message spiel information
        MessageSpiel messageSpiel = findByMsiId(msiId);

        // Check if the messageSpiel is found
        if ( messageSpiel == null || messageSpiel.getMsiId() == null) {

            // Log the response
            log.info("deleteMessage spiel - Response : No Message spiel information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Delete the linkRequest and set the retData fields
        deleteMessageSpiel(msiId);

        // Return true
        return true;

    }



}
