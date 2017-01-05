package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.core.repository.TransferPointSettingRepository;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.service.TransferPointSettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class TransferPointSettingServiceImpl extends BaseServiceImpl<TransferPointSetting> implements TransferPointSettingService {


    private static Logger log = LoggerFactory.getLogger(TransferPointSettingServiceImpl.class);


    @Autowired
    TransferPointSettingRepository transferPointSettingRepository;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    public TransferPointSettingServiceImpl() {

        super(TransferPointSetting.class);

    }

    @Override
    protected BaseRepository<TransferPointSetting,Long> getDao() {
        return transferPointSettingRepository;
    }




    @Override
    public TransferPointSetting findByTpsId(Long tpsId) {

        // Get the transferPointSetting for the given transferPointSetting id from the repository
        TransferPointSetting transferPointSetting = transferPointSettingRepository.findByTpsId(tpsId);

        // Return the transferPointSetting
        return transferPointSetting;


    }

    @Override
    public List<TransferPointSetting> findByTpsMerchantNo(Long tpsMerchantNo) {

        // GEt the List
        List<TransferPointSetting> transferPointSettingList = transferPointSettingRepository.findByTpsMerchantNo(tpsMerchantNo);

        // REturn the list
        return transferPointSettingList;

    }

    @Override
    public TransferPointSetting findByTpsMerchantNoAndTpsLocation(Long tpsMerchantNo, Long tpsLocation) {

        // Get the transferPointSetting using the transferPointSetting code and the merchant number
        TransferPointSetting transferPointSetting = transferPointSettingRepository.findByTpsMerchantNoAndTpsLocation(tpsMerchantNo, tpsLocation);

        // Return the transferPointSetting object
        return transferPointSetting;

    }

    @Override
    public TransferPointSetting getDefaultTransferPointSetting(Long tpsMerchantNo) {

        // Get the global settings ( created by the merchant admin with the location as 0L
        TransferPointSetting transferPointSetting =  transferPointSettingRepository.findByTpsMerchantNoAndTpsLocation(tpsMerchantNo,0L);

        // Return the object
        return transferPointSetting;

    }

    @Override
    public TransferPointSetting getTransferPointSettingInfoForUser( ) throws InspireNetzException {


        // Check the userType
        Integer userType = authSessionUtils.getUserType();

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the merchantLocation
        Long merchantLocation =authSessionUtils.getUserLocation();



        // If the userType is admin, set the location to 0
        if ( userType == UserType.MERCHANT_ADMIN ) {

            merchantLocation = 0L;

        }



        // Get the accoutn bundling for the merchantNo and location
        TransferPointSetting transferPointSetting =  transferPointSettingRepository.findByTpsMerchantNoAndTpsLocation(merchantNo,merchantLocation);

        // If no data is found, then throw no data found exceptoin
        if ( transferPointSetting == null ) {

            // Log the response
            log.info("getTransferPointSettingInfoForUser - Response : No transferPointSetting information found");

            // Throw not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Return the object
        return transferPointSetting;


    }





    @Override
    public TransferPointSetting saveTransferPointSettingForUser(TransferPointSetting transferPointSetting ) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_TRANSFER_POINT_SETTING);

        // Get the merchant number of the merchant
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long location = authSessionUtils.getUserLocation();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();


        // If the userType is admin, then check if the location is set, then
        // we will use the location, otherwise, set the location to 0
        if ( userType == UserType.MERCHANT_ADMIN ) {

            // Check if the location is null
            if ( transferPointSetting.getTpsLocation() == null ) {

                // Set the location to 0
                transferPointSetting.setTpsLocation(0l);

            }

        } else if ( userType ==UserType.MERCHANT_USER ) {

            // Set the location as the lcoation for th euser
            transferPointSetting.setTpsLocation(location);

        }


        // Set the merchantNo to the merchant number of the merchant admin
        transferPointSetting.setTpsMerchantNo(merchantNo);



        // Get the auditDetails
        String auditDetails = authSessionUtils.getUserNo().toString();

        // If the transferPointSetting.getTpsId is  null, then set the created_by, else set the updated_by
        if ( transferPointSetting.getTpsId() == null ) {

            transferPointSetting.setCreatedBy(auditDetails);

        } else {

            transferPointSetting.setUpdatedBy(auditDetails);

        }



        // save the transferPointSetting object and get the result
        transferPointSetting = saveTransferPointSetting(transferPointSetting);


        // Check if the transferPoint setting is saved
        if ( transferPointSetting.getTpsId() == null ) {

            // Log the response
            log.info("saveTransferPointSetting - Response : Unable to save the transferPointSetting information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Return the object
        return transferPointSetting;

    }

    @Override
    public TransferPointSetting saveTransferPointSetting(TransferPointSetting transferPointSetting) {

        return transferPointSettingRepository.save(transferPointSetting);

    }

    @Override
    public boolean removeTransferPointSetting(Long tpsId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_TRANSFER_POINT_SETTING);

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the transferPointSetting information
        TransferPointSetting transferPointSetting = findByTpsId(tpsId);



        // Check if the transferPointSetting is found
        if ( transferPointSetting == null || transferPointSetting.getTpsId() == null) {

            // Log the response
            log.info("deleteTransferPointSetting - Response : No transferPointSetting information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( transferPointSetting.getTpsMerchantNo().longValue() != merchantNo || authSessionUtils.getUserType() != UserType.MERCHANT_ADMIN ) {

            // Log the response
            log.info("deleteTransferPointSetting - Response : You are not authorized to delete the transferPointSetting");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Delete the transferPointSetting
        deleteTransferPointSetting(tpsId);

        // return true
        return true;

    }

    @Override
    public boolean deleteTransferPointSetting(Long tpsId) throws InspireNetzException {

        // Delete the item
        transferPointSettingRepository.delete(tpsId);

        // Return true
        return true;



    }

}
