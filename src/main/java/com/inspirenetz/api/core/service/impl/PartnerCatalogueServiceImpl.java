package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.PartnerCatalogueStatus;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.validator.PartnerCatalogueValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PartnerCatalogueRepository;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PartnerCatalogueService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PartnerCatalogueResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class PartnerCatalogueServiceImpl extends BaseServiceImpl<PartnerCatalogue> implements PartnerCatalogueService {


    private static Logger log = LoggerFactory.getLogger(PartnerCatalogueServiceImpl.class);


    @Autowired
    PartnerCatalogueRepository partnerCatalogueRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    UserService userService ;

    @Autowired
    private Mapper mapper;

    public PartnerCatalogueServiceImpl() {

        super(PartnerCatalogue.class);

    }


    @Override
    protected BaseRepository<PartnerCatalogue,Long> getDao() {
        return partnerCatalogueRepository;
    }



    @Override
    public PartnerCatalogue findByPacId(Long pacId) throws InspireNetzException {

        // Get the partnerCatalogue for the given partnerCatalogue id from the repository
        PartnerCatalogue partnerCatalogue = partnerCatalogueRepository.findByPacId(pacId);

        // Return the partnerCatalogue
        return partnerCatalogue;

    }

    @Override
    public PartnerCatalogue findByPacCodeAndPacPartnerNo(String pacCode, Long pacPartnerNo) {

        PartnerCatalogue partnerCatalogue = partnerCatalogueRepository.findByPacCodeAndPacPartnerNo(pacCode,pacPartnerNo);

        return partnerCatalogue;
    }


    @Override
    public Page<PartnerCatalogue> findByPacPartnerNo(Long pacPartnerNo,Pageable pageable) {

        //get the draw chances list
        Page<PartnerCatalogue> partnerCatalogues = partnerCatalogueRepository.findByPacPartnerNo(pacPartnerNo,pageable);

        //return the list
        return  partnerCatalogues;

    }

    @Override
    public Page<PartnerCatalogue> findByPacCategoryAndPacPartnerNo(Integer pacCategory,Long pacPartnerNo,Pageable pageable) {

        //get the draw chances list
        Page<PartnerCatalogue> partnerCatalogues = partnerCatalogueRepository.findByPacCategoryAndPacPartnerNo(pacCategory, pacPartnerNo,pageable);

        //return the list
        return  partnerCatalogues;

    }

    @Override
    public Page<PartnerCatalogue> findByPacStatusAndPacPartnerNo(Integer pacStatus, Long pacPartnerNo, Pageable pageable) {

        return partnerCatalogueRepository.findByPacStatusAndPacPartnerNo(pacStatus,pacPartnerNo,pageable);
    }

    @Override
    public Page<PartnerCatalogue> findByPacCategoryAndPacStatusAndPacPartnerNo(Integer pacCategory,Integer pacStatus, Long pacPartnerNo, Pageable pageable) {

        return partnerCatalogueRepository.findByPacCategoryAndPacStatusAndPacPartnerNo(pacCategory,pacStatus,pacPartnerNo,pageable);

    }

    @Override
    public List<PartnerCatalogue> findByPacNameLikeAndPacPartnerNo(String pacName,Long pacPartnerNo) {

        //get the draw chances list
        List<PartnerCatalogue> partnerCatalogues = partnerCatalogueRepository.findByPacNameLikeAndPacPartnerNo("%" + pacName + "%", pacPartnerNo);

        //return the list
        return  partnerCatalogues;

    }

    @Override
    public Page<PartnerCatalogue> findByPacNameLikeAndPacPartnerNo(String pacName, Long pacPartnerNo, Pageable pageable) {

        Page<PartnerCatalogue> partnerCatalogues = partnerCatalogueRepository.findByPacNameLikeAndPacPartnerNo(pacName,pacPartnerNo,pageable);

        return partnerCatalogues;
    }

    @Override
    public Page<PartnerCatalogue> findByPacCodeLikeAndPacPartnerNo(String pacCode, Long pacPartnerNo, Pageable pageable) {

        Page<PartnerCatalogue> partnerCatalogues = partnerCatalogueRepository.findByPacCodeLikeAndPacPartnerNo(pacCode,pacPartnerNo,pageable);

        return partnerCatalogues;

    }

    @Override
    public PartnerCatalogue validateAndSavePartnerCatalogue(PartnerCatalogue partnerCatalogue ) throws InspireNetzException {

       /* PartnerCatalogue partnerCatalogue = new PartnerCatalogue();

        // Map the customer
        mapper.map(partnerCatalogueResource,partnerCatalogue);*/

        String userLoginId = authSessionUtils.getUserLoginId();

        User user = userService.findByUsrLoginId(userLoginId);

        if(user == null || user.getUsrThirdPartyVendorNo() == null || user.getUsrThirdPartyVendorNo() == 0){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT);

        }

        partnerCatalogue.setPacPartnerNo(user.getUsrThirdPartyVendorNo());

        partnerCatalogue.setPacAddedUser(userLoginId);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        PartnerCatalogueValidator validator = new PartnerCatalogueValidator();

        // Create the bindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(partnerCatalogue,"partnerCatalogue");

        // Validate the request
        validator.validate(partnerCatalogue,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSavePartnerCatalogue - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // If the partnerCatalogue.getLrqId is  null, then set the created_by, else set the updated_by
        if ( partnerCatalogue.getPacId() == null ) {

            partnerCatalogue.setCreatedBy(auditDetails);

        } else {

            partnerCatalogue.setUpdatedBy(auditDetails);

        }

        //check the validity of the partner catalogue code
        boolean isDuplicate = isDuplicateCatalogueExisting(partnerCatalogue);

        //check if the code is a duplicate
        if(isDuplicate){

            //log the error
            log.error("validateAndSavePartnerCatalogue : Partner catalogue , pacCode is already existing"+partnerCatalogue);

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_PARTNER_CATALOGUE_CODE_DUPLICATE);


        }
        partnerCatalogue = savePartnerCatalogue(partnerCatalogue);

        // Check if the partnerCatalogue is saved
        if ( partnerCatalogue.getPacId() == null ) {

            // Log the response
            log.info("validateAndSavePartnerCatalogue - Response : Unable to save the partnerCatalogue information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return partnerCatalogue;


    }

    @Override
    public boolean isDuplicateCatalogueExisting(PartnerCatalogue partnerCatalogue) {

        // Get the catalogue information
        PartnerCatalogue exCatalogue = partnerCatalogueRepository.findByPacCodeAndPacPartnerNo(partnerCatalogue.getPacCode(), partnerCatalogue.getPacPartnerNo());

        // If the brnId is 0L, then its a new catalogue so we just need to check if there is ano
        // ther catalogue code
        if ( partnerCatalogue.getPacId() == null || partnerCatalogue.getPacId() == 0L ) {

            // If the catalogue is not null, then return true
            if ( exCatalogue != null ) {

                return true;

            }

        } else {

            // Check if the catalogue is null
            if ( exCatalogue != null && partnerCatalogue.getPacId().longValue() != exCatalogue.getPacId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public PartnerCatalogue savePartnerCatalogue(PartnerCatalogue partnerCatalogue){

        // Save the partnerCatalogue
        return partnerCatalogueRepository.save(partnerCatalogue);

    }


    @Override
    public boolean deletePartnerCatalogue(Long rolId) {

        // Delete the partnerCatalogue
        partnerCatalogueRepository.delete(rolId);

        // return true
        return true;

    }

    @Override
    public boolean updatePartnerCatalogueStatus(Long pacId, Integer pacStatus, Long pacPartnerNo, Long userNo) throws InspireNetzException {

        // Get the parter catalogue based on id
        PartnerCatalogue partnerCatalogue = findByPacId(pacId);

        // Check if the partnerCatalogue is found
        if ( partnerCatalogue == null || partnerCatalogue.getPacId() == null) {

            // Log the response
            log.info("updatepartnerCatalogueStatus - Response : No catalogue information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's partner number and the current
        // authenticated partner number are same.
        // We need to raise an error if not
        if ( partnerCatalogue.getPacPartnerNo().longValue() != pacPartnerNo ) {

            // Log the response
            log.info("updatepartnerCatalogueStatus - Response : You are not authorized to view the partner catalogue");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Set the updated by field to the user number of the user
        partnerCatalogue.setUpdatedBy(Long.toString(userNo));

        // Set the status to the status set by user
        partnerCatalogue.setPacStatus(pacStatus);

        // Saving the loyalty program object
        log.info("updatePartnerCatalogueStatus - Saving object : " + partnerCatalogue.toString());

        // SAve the program
        savePartnerCatalogue(partnerCatalogue);

        // Return true
        return true;

    }

    @Override
    public Page<PartnerCatalogue> getPartnerCatalogue(String filter, String query, String userLoginId, Pageable pageable){

        User user = userService.findByUsrLoginId(userLoginId);

        if(user == null || user.getUsrThirdPartyVendorNo() == null || user.getUsrThirdPartyVendorNo() == 0){

            return null;
        }
        Long partnerNo = user.getUsrThirdPartyVendorNo();

        Page<PartnerCatalogue > partnerCatalogues  = null ;

        if(filter.equals("0") && query.equals("0")){

               partnerCatalogues = findByPacPartnerNo(partnerNo,pageable);

        } else if(filter.equals("code")){

            partnerCatalogues = findByPacCodeLikeAndPacPartnerNo("%"+query+"%", partnerNo, pageable);

        } else if(filter.equals("name")){

            partnerCatalogues = findByPacNameLikeAndPacPartnerNo("%"+query+"%", partnerNo, pageable);
        }

        return partnerCatalogues;
    }

    @Override
    public Page<PartnerCatalogue> searchPartnerCatalogues(Integer category, Long productNo, String userLoginId, Pageable pageable) throws InspireNetzException {

        Page<PartnerCatalogue > partnerCatalogues = null;

        User user =userService.findByUsrLoginId(userLoginId);

        Long partnerNo = user.getUsrThirdPartyVendorNo();

        if(productNo != null && productNo != 0 ){

            PartnerCatalogue partnerCatalogue = findByPacId(productNo);

            List<PartnerCatalogue> partnerCatalogues1 = new ArrayList<>(0);

            partnerCatalogues1.add(partnerCatalogue);

            partnerCatalogues  = new PageImpl<>(partnerCatalogues1);

        } else if(category != null && category != 0 ){

            if(partnerNo != null && partnerNo != 0){

                partnerCatalogues = findByPacCategoryAndPacPartnerNo(category,partnerNo,pageable);
            }

        } else {


                partnerCatalogues = findByPacPartnerNo(partnerNo, pageable);

        }

        return partnerCatalogues;

    }

    @Override
    public Page<PartnerCatalogue> searchPartnerCatalogueForMerchant(Integer category,Long partnerNo, Pageable pageable) throws InspireNetzException {

        Page<PartnerCatalogue > partnerCatalogues = null;

        if(category != null && category != 0 ){

            partnerCatalogues = findByPacCategoryAndPacStatusAndPacPartnerNo(category, IndicatorStatus.YES,partnerNo,pageable);

        } else {


            partnerCatalogues = findByPacStatusAndPacPartnerNo(IndicatorStatus.YES,partnerNo, pageable);

        }

        return partnerCatalogues;

    }

    @Override
    public void deductStockFromPartnerCatalogue(Long pacId, Long catAvailableStock) throws InspireNetzException {

        PartnerCatalogue partnerCatalogue = findByPacId(pacId);

        if(partnerCatalogue != null){

            Double currentStock = partnerCatalogue.getPacStock();

            currentStock -= catAvailableStock;

            partnerCatalogue.setPacStock(currentStock);

            savePartnerCatalogue(partnerCatalogue);
        }
    }


}

