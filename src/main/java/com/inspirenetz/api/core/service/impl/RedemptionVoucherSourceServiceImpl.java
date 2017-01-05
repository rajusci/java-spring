package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.domain.VoucherCode;
import com.inspirenetz.api.core.domain.validator.RedemptionVoucherSourceValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RedemptionVoucherSourceRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUniqueIdGenerator;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.sql.DataSource;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class RedemptionVoucherSourceServiceImpl extends BaseServiceImpl<RedemptionVoucherSource> implements RedemptionVoucherSourceService {


    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherSourceServiceImpl.class);


    @Autowired
    RedemptionVoucherSourceRepository redemptionVoucherSourceRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private DataSource dataSource;

    @Autowired
    VoucherCodeService voucherCodeService;

    @Autowired
    FileUploadService fileUploadService;


    public RedemptionVoucherSourceServiceImpl() {

        super(RedemptionVoucherSource.class);

    }


    @Override
    protected BaseRepository<RedemptionVoucherSource,Long> getDao() {
        return redemptionVoucherSourceRepository;
    }



    @Override
    public RedemptionVoucherSource findByRvsId(Long rvsId) throws InspireNetzException {

        // Get the redemptionVoucherSource for the given redemptionVoucherSource id from the repository
        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceRepository.findByRvsId(rvsId);

        // Return the redemptionVoucherSource
        return redemptionVoucherSource;

    }

    @Override
    public Page<RedemptionVoucherSource> findByRvsMerchantNoAndRvsNameLike(Long rvsMerchantNo, String rvsName,Pageable pageable) throws InspireNetzException {

        Page<RedemptionVoucherSource> redemptionVoucherSources = redemptionVoucherSourceRepository.findByRvsMerchantNoAndRvsNameLike(rvsMerchantNo,rvsName,pageable);

        return redemptionVoucherSources;
    }

    @Override
    public Page<RedemptionVoucherSource> findByRvsMerchantNo(Long rvsMerchantNo, Pageable pageable) throws InspireNetzException {

        Page<RedemptionVoucherSource> redemptionVoucherSources = redemptionVoucherSourceRepository.findByRvsMerchantNo(rvsMerchantNo, pageable);

        return redemptionVoucherSources; 
    }

    @Override
    public String getRedemptionVoucherCode(RedemptionVoucherSource redemptionVoucherSource) throws InspireNetzException {

        //check the redemption voucher source's type
        if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_FIXED){

            return redemptionVoucherSource.getRvsCode();

        } else if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_RANGE){

            return getVoucherCodeForVoucherTypeRange(redemptionVoucherSource);

        } else if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_FILE){

            return getVoucherCodeFromFile(redemptionVoucherSource);

        } else if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_GENERATED){

            return getGeneratedVoucherCode(redemptionVoucherSource);

        }  else if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_NO_CODE){

            return "";
        }

        return null;
    }

    private String getGeneratedVoucherCode(RedemptionVoucherSource redemptionVoucherSource) {

        // Create the UniqueIdGenerator
        DBUniqueIdGenerator generator = new DBUniqueIdGenerator(dataSource);

        // Get the trackingId
        Long uniqueId = generator.getNextUniqueId(UniqueIdType.REDEMPTION_VOUCHER_UNIQUE_ID);

        //get voucher code(prefix + uniqueId)
        String voucherCode = redemptionVoucherSource.getRvsPrefix()+""+uniqueId;

        //return voucherCode
        return voucherCode;

    }

    private String getVoucherCodeFromFile(RedemptionVoucherSource redemptionVoucherSource) throws InspireNetzException {

        //get the voucher code
        String voucherCode = voucherCodeService.getVoucherCode(redemptionVoucherSource);

        //check if voucher code is null ,else return
        if(voucherCode != null && !voucherCode.equals("")){


            //return code
           return voucherCode;

        } else{

            //set rvsStatus as failed
            redemptionVoucherSource.setRvsStatus(VoucherStatus.FAILED);

            //save the voucher
            saveRedemptionVoucherSource(redemptionVoucherSource);

            //log error
            log.error("getVoucherCodeFromFile : No voucher code found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_VOUCHER_CODES);
        }



    }


    private String getVoucherCodeForVoucherTypeRange(RedemptionVoucherSource redemptionVoucherSource) throws InspireNetzException {

        //get the prefix
        String voucherPrefix = redemptionVoucherSource.getRvsPrefix();

        //get the ending value
        Long voucherEndValue = redemptionVoucherSource.getRvsCodeEnd();

        //get the index
        Long voucherIndex = redemptionVoucherSource.getRvsIndex();

        Long nextIndex = voucherIndex+1;

        String  voucherCode = "";

        //if the voucher is the first one used , return start value
        if(voucherIndex.longValue() == 0){

            //return the start value
            voucherCode =  voucherPrefix + ""+ redemptionVoucherSource.getRvsCodeStart();

            nextIndex = redemptionVoucherSource.getRvsCodeStart();

        }else if(nextIndex > voucherEndValue){

            throw new InspireNetzException(APIErrorCode.ERR_NO_VOUCHER_CODES);

        }else {

            //increment the index
            voucherCode= voucherPrefix + ""+ nextIndex;
        }


        //increment the rvsIndex
        redemptionVoucherSource.setRvsIndex(nextIndex);

        //save the voucher source
        saveRedemptionVoucherSource(redemptionVoucherSource);

        //return the code
        return voucherCode;

    }

    @Override
    public String getVoucherCode(Long rvsId) throws InspireNetzException {

       //get the redemption voucher source
        RedemptionVoucherSource redemptionVoucherSource = findByRvsId(rvsId);

        //get the voucher code
        String voucherCode = getRedemptionVoucherCode(redemptionVoucherSource);

        //return the obtained voucher code
        return voucherCode;


    }

    @Override
    public Page<RedemptionVoucherSource> searchVoucherSources(String filter, String query, Long merchantNo, Pageable pageable) throws InspireNetzException {

        Page<RedemptionVoucherSource> redemptionVoucherSourcePage = null;

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            if(merchantNo ==0L){

                redemptionVoucherSourcePage =redemptionVoucherSourceRepository.findAll(pageable);
            }else {


                redemptionVoucherSourcePage = findByRvsMerchantNo(merchantNo, pageable);
            }



        } else if ( filter.equalsIgnoreCase("name") ) {

            if(merchantNo ==0L){

                redemptionVoucherSourcePage = redemptionVoucherSourceRepository.findByRvsNameLike("%" + query + "%", pageable);

            }else {

                redemptionVoucherSourcePage = redemptionVoucherSourceRepository.findByRvsMerchantNoAndRvsNameLike(merchantNo, "%" + query + "%", pageable);
            }


        }


        return redemptionVoucherSourcePage;
    }

    @Override
    public RedemptionVoucherSource validateAndSaveRedemptionVoucherSource(RedemptionVoucherSource redemptionVoucherSource) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_REDEMPTION_VOUCHER_SOURCE);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        RedemptionVoucherSourceValidator validator = new RedemptionVoucherSourceValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(redemptionVoucherSource,"redemptionVoucherSource");

        // Validate the request
        validator.validate(redemptionVoucherSource,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveRedemptionVoucherSource - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_CATALOGUE_PREFERENCE_NULL,result);

        }

        // If the catalogueDisplayPreference.getLrqId is  null, then set the created_by, else set the updated_by
        if ( redemptionVoucherSource.getRvsId() == null ) {

            //set audited entity field
            redemptionVoucherSource.setCreatedBy(auditDetails);

            //check if the type is range
            if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_RANGE){

                //set voucher index as the start value
                redemptionVoucherSource.setRvsIndex(0L);


            } else if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_FILE){

                //set voucher status as processing
                redemptionVoucherSource.setRvsStatus(VoucherStatus.PROCESSING);

                redemptionVoucherSource.setRvsIndex(0L);
            }


        } else {

            //check whether the request is for updating range type voucher source , dont allow updation if true
            if(redemptionVoucherSource.getRvsType() != VoucherSourceType.VOC_TYPE_FIXED){

                //log error
                log.info("validateAndSaveRedemptionVoucherSource - Update not allowed for Redemption voucher source range type");

                //throw error
                throw new InspireNetzException(APIErrorCode.ERR_VOUCHER_SOURCE_RANGE_UPDATE_NOT_ALLOWED);
            }

            //set updated values
            redemptionVoucherSource.setUpdatedBy(auditDetails);


            }


        //call the save method
        redemptionVoucherSource = saveRedemptionVoucherSource(redemptionVoucherSource);

        //check whether the request is for updating range type voucher source , dont allow updation if true
        if(redemptionVoucherSource.getRvsType() == VoucherSourceType.VOC_TYPE_FILE){

            //get the file upload path
            String uploadRoot = fileUploadService.getFileUploadPathForType(FileUploadPath.FILE_UPLOAD_ROOT);

            //get the complete file path
            String filePath = uploadRoot + redemptionVoucherSource.getFilePath();

            //call processing method
            voucherCodeService.processBatchFile(filePath,redemptionVoucherSource);

        }

        //return the object
        return redemptionVoucherSource;

    }

    @Override
    public RedemptionVoucherSource saveRedemptionVoucherSource(RedemptionVoucherSource redemptionVoucherSource) {

        //save the object
        redemptionVoucherSource = redemptionVoucherSourceRepository.save(redemptionVoucherSource);

        //return the object
        return redemptionVoucherSource;
    }

    @Override
    public boolean deleteRedemptionVoucherSource(Long rvsId) {

        //delete the object
        redemptionVoucherSourceRepository.delete(rvsId);

        //return true
        return true;
    }

    @Override
    public boolean validateAndDeleteRedemptionVoucherSource(Long rvsId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_VOUCHER_SOURCE);

        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the redemptionVoucherSource information
        RedemptionVoucherSource redemptionVoucherSource = findByRvsId(rvsId);

        // Check if the redemptionVoucherSource is found
        if ( redemptionVoucherSource == null || redemptionVoucherSource.getRvsId() == null) {

            // Log the response
            log.info("deleteRedemptionVoucherSource - Response : No redemptionVoucherSource information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletin
        if ( redemptionVoucherSource.getRvsMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteRedemptionVoucherSource - Response : No redemptionVoucherSource information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the redemptionVoucherSource and set the retData fields
        deleteRedemptionVoucherSource(rvsId);

        // Return true
        return true;

    }

    @Override
    public Page<RedemptionVoucherSource> findByRvsMerchantNoAndRvsStatus(Long rvsMerchantNo, Integer rvsStatus, Pageable pageable) {

        Page<RedemptionVoucherSource > redemptionVoucherSources = null ;

        redemptionVoucherSources = redemptionVoucherSourceRepository.findByRvsMerchantNoAndRvsStatus(rvsMerchantNo,rvsStatus,pageable);

        return redemptionVoucherSources;
    }

    @Override
    public Page<RedemptionVoucherSource> getActiveVoucherSources(Long rvsMerchantNo, Pageable pageable) {

        Page<RedemptionVoucherSource> redemptionVoucherSources = null;

        //get all vouchers with active status
        redemptionVoucherSources = findByRvsMerchantNoAndRvsStatus(rvsMerchantNo,VoucherStatus.ACTIVE,pageable);

        //return the list
        return redemptionVoucherSources;
    }

    /*
    * This method will be called once the reading from file is completed or failed
    * */
     @Override
     public void updateVoucherSourceStatus(RedemptionVoucherSource redemptionVoucherSource,Integer status){

        //set the status
        redemptionVoucherSource.setRvsStatus(status);

        //save the change
        redemptionVoucherSourceRepository.save(redemptionVoucherSource);

    }
}

