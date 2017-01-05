package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.MerchantSettlementValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantSettlementRepository;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.MerchantSettlementService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import reactor.spring.annotation.Selector;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by saneeshci on 21/9/15.
 */
@Service
public class MerchantSettlementServiceImpl extends BaseServiceImpl<MerchantSettlement> implements MerchantSettlementService {


    private static Logger log = LoggerFactory.getLogger(MerchantSettlementServiceImpl.class);


    @Autowired
    MerchantSettlementRepository merchantSettlementRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    UserService userService;

    public MerchantSettlementServiceImpl() {

        super(MerchantSettlement.class);

    }


    @Override
    protected BaseRepository<MerchantSettlement,Long> getDao() {
        return merchantSettlementRepository;
    }



    @Override
    public MerchantSettlement findByMesId(Long mesId) throws InspireNetzException {

        // Get the merchantSettlement for the given merchantSettlement id from the repository
        MerchantSettlement merchantSettlement = merchantSettlementRepository.findByMesId(mesId);

        // Return the merchantSettlement
        return merchantSettlement;

    }

    @Override
    public List<MerchantSettlement> findByMesVendorNo(Long mesVendorNo) {


        // Get the merchantSettlement for the given merchantSettlement id from the repository
        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNo(mesVendorNo);

        //return the object
        return merchantSettlements;

    }

    @Override
    public List<MerchantSettlement> findByMesVendorNoAndMesIsSettled(Long mesVendorNo,Integer mesIsSettled) {

        //get the draw chances list
        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesIsSettled(mesVendorNo, mesIsSettled);

        //return the list
        return  merchantSettlements;

    }


    @Override
    public MerchantSettlement validateAndSaveMerchantSettlement(MerchantSettlement merchantSettlement ) throws InspireNetzException {

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        MerchantSettlementValidator validator = new MerchantSettlementValidator();

        // Create the bindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(merchantSettlement,"merchantSettlement");

        // Validate the request
        validator.validate(merchantSettlement,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveMerchantSettlement - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // If the merchantSettlement.getLrqId is  null, then set the created_by, else set the updated_by
        if ( merchantSettlement.getMesId() == null ) {

            merchantSettlement.setCreatedBy(auditDetails);

        } else {

            merchantSettlement.setUpdatedBy(auditDetails);

        }

        merchantSettlement = saveMerchantSettlement(merchantSettlement);

        // Check if the merchantSettlement is saved
        if ( merchantSettlement.getMesId() == null ) {

            // Log the response
            log.info("validateAndSaveMerchantSettlement - Response : Unable to save the merchantSettlement information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return merchantSettlement;


    }

    @Override
    public MerchantSettlement saveMerchantSettlement(MerchantSettlement merchantSettlement ){

        // Save the merchantSettlement
        return merchantSettlementRepository.save(merchantSettlement);

    }


    @Override
    public boolean deleteMerchantSettlement(Long mesId) {

        // Delete the merchantSettlement
        merchantSettlementRepository.delete(mesId);

        // return true
        return true;

    }

    @Override
    public List<MerchantSettlement> findByMesVendorNoAndMesLocation(Long mesVendorNo, Long mesLocation) {

        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesLocation(mesVendorNo,mesLocation);

        return merchantSettlements;
    }

    @Override
    public List<MerchantSettlement> findByMesVendorNoMesLocationAndMesDateBetween(Long mesVendorNo, Long mesLocation, Date startDate, Date endDate) {

        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesLocationAndMesDateBetween(mesVendorNo, mesLocation, startDate, endDate);

        return merchantSettlements;
    }

    @Override
    public List<MerchantSettlement> findByMesVendorNoAndMesDateBetween(Long mesVendorNo, Date startDate, Date endDate) {

        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesDateBetween(mesVendorNo, startDate, endDate);

        return merchantSettlements;
    }

    @Override
    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);

        calendar.add(Calendar.DATE,-1);

        startDate = new Date(calendar.getTime().getTime());

        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(mesVendorNo, mesLocation, mesSettlementType, mesIsSettled, startDate);

        return merchantSettlements;
    }

    @Override
    public List<MerchantSettlement> findByMesMerchantNoAndMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(Long mesMerchantNo,Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate){

    Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);

        calendar.add(Calendar.DATE,-1);

        startDate = new Date(calendar.getTime().getTime());

        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesMerchantNoAndMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(mesMerchantNo,mesVendorNo, mesLocation, mesSettlementType, mesIsSettled, startDate);

        return merchantSettlements;
    }

    @Override
    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate, Date endDate) {

        List<MerchantSettlement> merchantSettlements = merchantSettlementRepository.findByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(mesVendorNo, mesLocation, mesSettlementType, mesIsSettled, startDate, endDate);

        return merchantSettlements;
    }

    @Override
    public List<MerchantSettlement> makeSettlement(String settlements) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DO_MERCHANT_SETTLEMENT);

        String[] settlementIds = settlements.split(",");

        List<MerchantSettlement> merchantSettlements = new ArrayList<>(0);

        for(String mesId : settlementIds){

            if(mesId != null && !mesId.equals("")){

                //get the settlement's for the received ids
                MerchantSettlement merchantSettlement = findByMesId(Long.parseLong(mesId));

                if(merchantSettlement.getMesIsSettled() == IndicatorStatus.NO){

                    merchantSettlement.setMesIsSettled(IndicatorStatus.YES);

                    merchantSettlements.add(merchantSettlement);

                }
            }

        }

        merchantSettlementRepository.save(merchantSettlements);

        return merchantSettlements;

    }

    @Override
    public List<MerchantSettlement> searchSettlements(Long mesVendorNo, Long mesLocation, Date fromDate, Date endDate) throws InspireNetzException {

        //check the validity of the date ranges
        boolean isDateRangeValid = checkDateRangeValidity(fromDate,endDate);

        if(isDateRangeValid){

            List<MerchantSettlement> merchantSettlements = new ArrayList<>(0);

            merchantSettlements = findByMesVendorNoMesLocationAndMesDateBetween(mesVendorNo,mesLocation,fromDate,endDate);

            return merchantSettlements;

        } else {

            log.error("searchSettlements : Date range is not valid");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_DATE_RANGE);


        }

    }

    private boolean checkDateRangeValidity(Date fromDate,Date endDate){

        //get the time difference in milli seconds
        long diff = endDate.getTime() - fromDate.getTime();

        //get the difference in days
        long difference = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        //check if the difference is more than 30 days
        if(difference > 30L){

            return false;
        }

        return true;

    }

    private class MerchantSettlementComparator  implements Comparator<MerchantSettlement> {

        public MerchantSettlementComparator() {


        }


        @Override
        public int compare(MerchantSettlement merchantSettlementA, MerchantSettlement merchantSettlementB) {



            // Set the comparing rules
            if ( merchantSettlementA.getMesDate().before(merchantSettlementB.getMesDate())) {

                return -1;

            } else if ( merchantSettlementA.getMesDate().after(merchantSettlementB.getMesDate()) ) {

                return 1;

            } else {

                return 0;

            }

        }

    }

    @Override
    public List<MerchantSettlement> sortSettlementsBasedOnDate(List<MerchantSettlement> merchantSettlements) {

        // Get the comparator
        MerchantSettlementComparator  merchantSettlementComparator = new MerchantSettlementComparator();

        // order the list
        Collections.sort(merchantSettlements ,merchantSettlementComparator);

        // Return the sortedList
        return merchantSettlements;

    }

    @Override
    @Selector(value= EventReactorCommand.ERC_MARK_AS_SETTLED,reactor = EventReactor.REACTOR_NAME)
    public void markBatchAsSettled(List<MerchantSettlement> merchantSettlements) {

        merchantSettlementRepository.save(merchantSettlements);

    }

    @Override
    public void addSettlementEntryForPartnerTransaction(Catalogue catalogue, PartnerCatalogue partnerCatalogue) {

        MerchantSettlement merchantSettlement = new MerchantSettlement();

        User user = userService.findByUsrLoginId(partnerCatalogue.getPacAddedUser());

        merchantSettlement.setMesAmount(partnerCatalogue.getPacCost());
        merchantSettlement.setMesDate(new Date(System.currentTimeMillis()));
        merchantSettlement.setMesInternalRef(catalogue.getCatProductCode());
        merchantSettlement.setMesIsSettled(IndicatorStatus.NO);
        merchantSettlement.setMesLoyaltyId(partnerCatalogue.getPacAddedUser());
        merchantSettlement.setMesMerchantNo(catalogue.getCatMerchantNo());
        merchantSettlement.setMesSettlementType(MerchantSettlementType.BANK_PAYMENT);
        merchantSettlement.setMesVendorNo(partnerCatalogue.getPacPartnerNo());
        merchantSettlement.setMesLocation(user.getUsrLocation());
        merchantSettlement.setMesSettlementRef(catalogue.getCatProductCode());
        saveMerchantSettlement(merchantSettlement);

    }


    @Override
    public boolean deleteMerchantSettlementEntry(Redemption redemption, RedemptionMerchant redemptionMerchant) {

        //Get the merchant Settlement details
        MerchantSettlement merchantSettlement = findByMesMerchantNoAndMesVendorNoAndMesDateAndMesInternalRefAndMesLoyaltyId(redemption.getRdmMerchantNo(), redemptionMerchant.getRemNo(), redemption.getRdmDate(), redemption.getRdmId().toString(), redemption.getRdmLoyaltyId());

        //Delete the settlement entry
        deleteMerchantSettlement(merchantSettlement.getMesId());

        return true;
    }


    @Override
    public MerchantSettlement findByMesMerchantNoAndMesVendorNoAndMesDateAndMesInternalRefAndMesLoyaltyId(Long mesMerchantNo, Long mesVendorNo, Date mesDate, String mesInternalRef, String mesLoyaltyId)  {

       //Get the merchantSettlement details
        MerchantSettlement merchantSettlement = merchantSettlementRepository.findByMesMerchantNoAndMesVendorNoAndMesDateAndMesInternalRefAndMesLoyaltyId(mesMerchantNo, mesVendorNo, mesDate,mesInternalRef,mesLoyaltyId);

        // Return merchant settlement object
        return merchantSettlement;
    }

    @Override
    public List<MerchantSettlement> findByMesMerchantNoAndMesVendorNoAndMesIsSettled(Long mesMerchantNo,Long mesVendorNo,  Integer mesIsSettled){

        return merchantSettlementRepository.findByMesMerchantNoAndMesVendorNoAndMesIsSettled(mesMerchantNo,mesVendorNo,mesIsSettled);

    }
}

