package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.MerchantSettlementCycleValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantSettlementCycleRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import reactor.core.Reactor;
import reactor.event.Event;

import java.sql.Date;
import java.util.*;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class MerchantSettlementCycleServiceImpl extends BaseServiceImpl<MerchantSettlementCycle> implements MerchantSettlementCycleService {


    private static Logger log = LoggerFactory.getLogger(MerchantSettlementCycleServiceImpl.class);


    @Autowired
    MerchantSettlementCycleRepository merchantSettlementCycleRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    MerchantSettlementService merchantSettlementService;

    @Autowired
    MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    @Autowired
    private Reactor eventReactor;

    private final int REQUEST_UPDATE_RATE=2;


    public MerchantSettlementCycleServiceImpl() {

        super(MerchantSettlementCycle.class);

    }


    @Override
    protected BaseRepository<MerchantSettlementCycle,Long> getDao() {
        return merchantSettlementCycleRepository;
    }



    @Override
    public MerchantSettlementCycle findByMscId(Long mscId) throws InspireNetzException {

        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleRepository.findByMscId(mscId);

        // Return the merchantSettlementCycle
        return merchantSettlementCycle;

    }

    @Override
    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo,Long mscRedemptionMerchant,Long mscMerchantLocation,Date mscStartDate,Date mscEndDate){


        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        List<MerchantSettlementCycle> merchantSettlementCycles = merchantSettlementCycleRepository.findByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo,mscRedemptionMerchant,mscMerchantLocation,mscStartDate,mscEndDate);

        //return the object
        return merchantSettlementCycles;

    }

    @Override
    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscRedemptionMerchant, Date mscStartDate, Date mscEndDate){


        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        List<MerchantSettlementCycle> merchantSettlementCycles = merchantSettlementCycleRepository.findByMscMerchantNoAndMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo, mscRedemptionMerchant, mscStartDate, mscEndDate);

        //return the object
        return merchantSettlementCycles;

    }

    @Override
    public List<MerchantSettlementCycle> findByMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscRedemptionMerchant,Long mscMerchantLocation,Date mscStartDate,Date mscEndDate){


        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        List<MerchantSettlementCycle> merchantSettlementCycles = merchantSettlementCycleRepository.findByMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(mscRedemptionMerchant, mscMerchantLocation, mscStartDate, mscEndDate);

        //return the object
        return merchantSettlementCycles;

    }

    @Override
    public List<MerchantSettlementCycle> findByMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(Long mscRedemptionMerchant, Date mscStartDate, Date mscEndDate){


        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        List<MerchantSettlementCycle> merchantSettlementCycles = merchantSettlementCycleRepository.findByMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(mscRedemptionMerchant, mscStartDate, mscEndDate);

        //return the object
        return merchantSettlementCycles;

    }

    @Override
    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscMerchantLocation, java.sql.Date mscStartDate, java.sql.Date mscEndDate){


        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        List<MerchantSettlementCycle> merchantSettlementCycles = merchantSettlementCycleRepository.findByMscMerchantNoAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo, mscMerchantLocation, mscStartDate, mscEndDate);

        //return the object
        return merchantSettlementCycles;

    }

    @Override
    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, java.sql.Date mscStartDate, java.sql.Date mscEndDate){


        // Get the merchantSettlementCycle for the given merchantSettlementCycle id from the repository
        List<MerchantSettlementCycle> merchantSettlementCycles = merchantSettlementCycleRepository.findByMscMerchantNoAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo, mscStartDate, mscEndDate);

        //return the object
        return merchantSettlementCycles;

    }

    @Override
    public MerchantSettlementCycle validateAndSaveMerchantSettlementCycle(MerchantSettlementCycle merchantSettlementCycle ) throws InspireNetzException {

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        MerchantSettlementCycleValidator validator = new MerchantSettlementCycleValidator();

        // Create the bindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(merchantSettlementCycle,"merchantSettlementCycle");

        // Validate the request
        validator.validate(merchantSettlementCycle,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveMerchantSettlementCycle - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // If the merchantSettlementCycle.getLrqId is  null, then set the created_by, else set the updated_by
        if ( merchantSettlementCycle.getMscId() == null ) {

            merchantSettlementCycle.setCreatedBy(auditDetails);

        } else {

            merchantSettlementCycle.setUpdatedBy(auditDetails);

        }

        merchantSettlementCycle = saveMerchantSettlementCycle(merchantSettlementCycle);

        // Check if the merchantSettlementCycle is saved
        if ( merchantSettlementCycle.getMscId() == null ) {

            // Log the response
            log.info("validateAndSaveMerchantSettlementCycle - Response : Unable to save the merchantSettlementCycle information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return merchantSettlementCycle;


    }

    @Override
    public MerchantSettlementCycle saveMerchantSettlementCycle(MerchantSettlementCycle merchantSettlementCycle ){

        // Save the merchantSettlementCycle
        return merchantSettlementCycleRepository.save(merchantSettlementCycle);

    }


    @Override
    public boolean deleteMerchantSettlementCycle(Long rolId) {

        // Delete the merchantSettlementCycle
        merchantSettlementCycleRepository.delete(rolId);

        // return true
        return true;

    }

    /**
     * @date:20-12-2014
     * @purpose:find customer draw chance
     * @return
     */

    @Override
    public Date getLastCycleGeneratedDate(Long mscMerchantNo,Long mscRedemptionMerchant,Long mscMerchantLocation) {

        Date lastGeneratedDate = merchantSettlementCycleRepository.findLastGeneratedSettlementCycle(mscMerchantNo,mscRedemptionMerchant,mscMerchantLocation);

        return lastGeneratedDate;
    }

    @Override
    public boolean generateMerchantSettlementCycle(RedemptionMerchant redemptionMerchant,Long merchantNo){

        //get the merchant locations from redemption merchant object
        Set<RedemptionMerchantLocation> redemptionMerchantLocations = redemptionMerchant.getRedemptionMerchantLocations();



        Set<MerchantRedemptionPartner> merchantRedemptionPartners=new HashSet<MerchantRedemptionPartner>();

        if(merchantNo==0L){

            merchantRedemptionPartners.addAll(merchantRedemptionPartnerService.findByMrpRedemptionMerchant(redemptionMerchant.getRemNo()));


        }else{

            merchantRedemptionPartners.add(merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(merchantNo, redemptionMerchant.getRemNo()));


        }

        //Iterate through merchant redemption partner
        for(MerchantRedemptionPartner merchantRedemptionPartner:merchantRedemptionPartners){

            try{
                RedemptionMerchant fetchedRedemptionMerchant=redemptionMerchantService.findByRemNo(merchantRedemptionPartner.getMrpRedemptionMerchant());

                //get the last generated date for cycle
                Date lastGeneratedDate = getCycleGenerateDate(merchantRedemptionPartner.getMrpMerchantNo(), redemptionMerchant.getRemNo(), 0L, redemptionMerchant);

                //call the method to generated cycle for the location
                generateSettlementCycleForLocation(fetchedRedemptionMerchant,merchantRedemptionPartner.getMrpMerchantNo(),0L,lastGeneratedDate);

                //generate cycles for all the locations
                for(RedemptionMerchantLocation redemptionMerchantLocation : redemptionMerchantLocations){


                    //get the last cycle generated date
                    lastGeneratedDate = getCycleGenerateDate(merchantRedemptionPartner.getMrpMerchantNo(), redemptionMerchant.getRemNo(), redemptionMerchantLocation.getRmlId(), redemptionMerchant);

                    //generate settlement cycle for the location
                    generateSettlementCycleForLocation(fetchedRedemptionMerchant,merchantRedemptionPartner.getMrpMerchantNo(),redemptionMerchantLocation.getRmlId(),lastGeneratedDate);

                }

            }catch (InspireNetzException ex){

            }

        }


        //get the redemption merchant details
        return true;
    }

    @Override
    public boolean generateMerchantSettlementCycleFromMerchant(Long merchantNo,Long redemptionMerchantNo) throws InspireNetzException{

        Set<MerchantRedemptionPartner> merchantRedemptionPartners=new HashSet<MerchantRedemptionPartner>();

        if(redemptionMerchantNo==0L){

            merchantRedemptionPartners.addAll(merchantRedemptionPartnerService.findByMrpMerchantNo(merchantNo));

        }else{

            merchantRedemptionPartners.add(merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(merchantNo, redemptionMerchantNo));

        }

        //Iterate through merchant redemption partner
        for(MerchantRedemptionPartner merchantRedemptionPartner:merchantRedemptionPartners){

            RedemptionMerchant redemptionMerchant=redemptionMerchantService.findByRemNo(merchantRedemptionPartner.getMrpRedemptionMerchant());

            //get the merchant locations from redemption merchant object
            Set<RedemptionMerchantLocation> redemptionMerchantLocations = redemptionMerchant.getRedemptionMerchantLocations();

            //get the last generated date for cycle
            Date lastGeneratedDate = getCycleGenerateDate(merchantNo, redemptionMerchant.getRemNo(), 0L, redemptionMerchant);

            //call the method to generated cycle for the location
            generateSettlementCycleForLocation(redemptionMerchant, merchantNo, 0L, lastGeneratedDate);

            //generate cycles for all the locations
            for(RedemptionMerchantLocation redemptionMerchantLocation : redemptionMerchantLocations){

                //get the last cycle generated date
                lastGeneratedDate = getCycleGenerateDate(merchantNo, redemptionMerchant.getRemNo(), redemptionMerchantLocation.getRmlId(), redemptionMerchant);

                //generate settlement cycle for the location
                generateSettlementCycleForLocation(redemptionMerchant,merchantNo,redemptionMerchantLocation.getRmlId(),lastGeneratedDate);

            }

        }

        //get the redemption merchant details
        return true;
    }

    @Override
    public List<MerchantSettlementCycle> searchMerchantSettlementCycle(Long mscMerchantNo, Long mscRedemptionMerchant, Long mscMerchantLocation, Date mscStartDate, Date mscEndDate) {
        mscStartDate = addDaysToTheDate(mscStartDate,-1);

        mscEndDate = addDaysToTheDate(mscEndDate,1);

        try{



            if(mscMerchantNo==0L){

                RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(mscRedemptionMerchant);

                if(redemptionMerchant!=null){

                    generateMerchantSettlementCycle(redemptionMerchant,mscMerchantNo);
                }

            }else{

                    generateMerchantSettlementCycleFromMerchant(mscMerchantNo, mscRedemptionMerchant);

            }

        }catch (InspireNetzException ex){

            ex.printStackTrace();

            // Log the response
            log.error("searchMerchantSettlementCycle - Response : Invalid Input - " + ex.toString());
        }

        List<MerchantSettlementCycle> merchantSettlementCycles =new ArrayList<MerchantSettlementCycle>();

        if(mscMerchantNo==0){

            if(mscMerchantLocation == 0){

                merchantSettlementCycles.addAll(findByMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(mscRedemptionMerchant, mscStartDate, mscEndDate));
            } else {

                merchantSettlementCycles.addAll(findByMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(mscRedemptionMerchant, mscMerchantLocation, mscStartDate, mscEndDate));

            }

        }else{

            if(mscRedemptionMerchant==0){


                if(mscMerchantLocation == 0){

                    merchantSettlementCycles.addAll(findByMscMerchantNoAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo, mscStartDate, mscEndDate));

                } else {

                    merchantSettlementCycles.addAll(findByMscMerchantNoAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo,  mscMerchantLocation, mscStartDate, mscEndDate));

                }

            }else{

                if(mscMerchantLocation == 0){

                    merchantSettlementCycles.addAll(findByMscMerchantNoAndMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo, mscRedemptionMerchant, mscStartDate, mscEndDate));

                } else {

                    merchantSettlementCycles.addAll(findByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(mscMerchantNo, mscRedemptionMerchant, mscMerchantLocation, mscStartDate, mscEndDate));

                }

            }

        }


        return merchantSettlementCycles;

    }
    


    /*
     This method generates the pending settlement cycles for the given location.
     It will fetch all the unsettled requests with type as bank payment for the given location.
     And depends on the merchants settlement frequency , cycles will be generated based on either weekly
     or monthly cycles.
     */
    private boolean generateSettlementCycleForLocation(RedemptionMerchant redemptionMerchant,Long merchantNo,Long location, Date lastGeneratedDate) {

        //get all the pending cycles for the particular location
        List<MerchantSettlement> merchantSettlements = merchantSettlementService.findByMesMerchantNoAndMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(merchantNo, redemptionMerchant.getRemNo(), location, MerchantSettlementType.BANK_PAYMENT, IndicatorStatus.NO, (Date) lastGeneratedDate);

       /* if(merchantSettlements == null || merchantSettlements.size() == 0){

            log.info("generateSettlementCycleForLocation : No settlements found ");

            return false;

        }*/
        //get the settlement frequency for the merchant
        Integer settlementFrequency = redemptionMerchant.getRemSettlementFrequency();

        //get the last generated date
        lastGeneratedDate = addDaysToTheDate(lastGeneratedDate, 1);

        //get the start date for the cycle
        Date startDate = getEffectiveStartDate(lastGeneratedDate, settlementFrequency);


        //generate the settlement cycle week based
        generateSettlementCycles(startDate, location, settlementFrequency, merchantSettlements,redemptionMerchant.getRemNo(),merchantNo);


        return true;
    }

    private Date getEffectiveStartDate(Date startDate, Integer settlementFrequency) {

        //check settlement frequency
        if(settlementFrequency == MerchantSettlementFrequency.WEEKLY){

            //get the last monday
            return getLastMondayForDate(startDate);

        } else {

            //get the first day of month
            return getFirstDayOfMonth(startDate);
        }
    }

    /*
    Method will get all the pending settlement requests for a location
    And depends on the settlement frequency the cycles will be created
     */
    private void generateSettlementCycles(Date startDate, Long location,int settlementFrequency, List<MerchantSettlement> merchantSettlements,Long remNo,Long merchantNo) {

        //get the end date for the settlement
        Date endDate = getEffectiveEndDate(startDate, settlementFrequency);

        //if the end date is greater than today's date , there is no need to generate the cycle
        if(endDate.after(new Date(System.currentTimeMillis()))){

            //return the control
            return ;

        }

        //sort the settlements based on request created date
        merchantSettlements = merchantSettlementService.sortSettlementsBasedOnDate(merchantSettlements);

        while(!endDate.after(new java.util.Date(System.currentTimeMillis()))){

            //get all the settlement requests during the start - end date period
            List<MerchantSettlement> toSettle = getSettlementRequestForPeriod(startDate,endDate,merchantSettlements);

            if(toSettle != null && toSettle.size() >0){

                //add settlement cycle entry
                addSettlementCycleEntry(toSettle,startDate,endDate);

                merchantSettlements = removeSettlementsFromToSettleList(merchantSettlements,toSettle);


            } else  {

                addSettlementCycleForNoSettlement(startDate,endDate,location,remNo,merchantNo);
            }

            //calculate the new start date by adding 1 day to the end date
            startDate = addDaysToTheDate(endDate, 1);

            //calculate the new end date by adding 7 day to the end date
            endDate = getEndDateForNextCycle(endDate, settlementFrequency);


        }
    }

    private void addSettlementCycleForNoSettlement(Date startDate, Date endDate, Long location, Long remNo, Long merchantNo) {

        MerchantSettlementCycle merchantSettlementCycle = new MerchantSettlementCycle();

        merchantSettlementCycle.setMscAmount(0.0);
        merchantSettlementCycle.setMscStartDate(startDate);
        merchantSettlementCycle.setMscEndDate(endDate);
        merchantSettlementCycle.setMscMerchantNo(merchantNo);
        merchantSettlementCycle.setMscRedemptionMerchant(remNo);
        merchantSettlementCycle.setMscMerchantLocation(location);
        merchantSettlementCycle.setMscStatus(MerchantSettlementCycleStatus.NOTHING_TO_SETTLE);

        saveMerchantSettlementCycle(merchantSettlementCycle);
    }

    private Date getEffectiveEndDate(Date startDate, int settlementFrequency) {

        //check settlement frequency
        if(settlementFrequency == MerchantSettlementFrequency.WEEKLY){

            //get the last monday
            return getNextSunday(startDate);

        } else {

            //get the first day of month
            return getLastDayOfMonth(startDate);
        }
    }


    private Date getEndDateForNextCycle(Date endDate, int settlementFrequency) {

        //check settlement frequency
        if(settlementFrequency == MerchantSettlementFrequency.WEEKLY){

            //add 7 days to the current end date and return
            return addDaysToTheDate(endDate,7);

        } else {

            //add a day to the end date ,this will change the date to next month
            Date startDate = addDaysToTheDate(endDate,1);

            //return the last date of next month
            return getLastDayOfMonth(startDate);
        }

    }

    private Date getFirstDayOfMonth(Date startDate) {

        //get calendar instance
        Calendar calendar = Calendar.getInstance();

        //set the date
        calendar.setTime(startDate);

        //set date to first day of month
        calendar.set(Calendar.DAY_OF_MONTH,1);

        startDate = new Date(calendar.getTime().getTime());

        //return date
        return startDate;
    }

    private Date getLastDayOfMonth(Date startDate) {

        //get calendar instance
        Calendar calendar = Calendar.getInstance();

        //set the date
        calendar.setTime(startDate);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        startDate = new Date(calendar.getTime().getTime());

        //return date
        return startDate;
    }

    Date addDaysToTheDate(Date date,int days){

        //get a calendar instance to work on date.
        Calendar calendar = Calendar.getInstance();

        //set the time as last generated date
        calendar.setTime(date);

        //add one day to last generated date, else the monday for the week will be previous week
        calendar.add(Calendar.DATE,days);

        //get the updated last generated date , this will be one day added to the last generated cycles end date
        date = new Date(calendar.getTime().getTime());

        return date;


    }

    /*

    Method removes the already cycle generated entries from the list of
    settlement requests
     */
    private List<MerchantSettlement> removeSettlementsFromToSettleList(List<MerchantSettlement> merchantSettlements, List<MerchantSettlement> toSettle) {

        //remove all the already cycle generated list
        merchantSettlements.removeAll(toSettle);

        //return the updated list
        return merchantSettlements;
    }

    /*
     Method returns the valid requests from a list of settlement requests.
     Based on the date range
     */
    private List<MerchantSettlement> getSettlementRequestForPeriod(Date startDate, Date endDate, List<MerchantSettlement> merchantSettlements) {

        //initialise array to hold the requests
        List<MerchantSettlement> toSettle = new ArrayList<>(0);

        //iterate throught the requests and get all the settlements entries before the enddate
        for(MerchantSettlement merchantSettlement : merchantSettlements){

            //if the request generated date is not after the end date, add it to the settlement list
            if(!merchantSettlement.getMesDate().after(endDate)){

                //add the request
                toSettle.add(merchantSettlement);

            } else {

                break;
            }
        }

        //return the list
        return toSettle;
    }

    /*
     Method will set the values to MerchantSettlementCycle object ,
     finds the total unsettled amount for the period and saves the cycle
     */
    private void addSettlementCycleEntry(List<MerchantSettlement> toSettle, Date startDate, Date endDate) {

        //get new object for merchant settlement cycle
        MerchantSettlementCycle merchantSettlementCycle = new MerchantSettlementCycle();

        //set the cycle start date
        merchantSettlementCycle.setMscStartDate(startDate);

        //set the cycle end date
        merchantSettlementCycle.setMscEndDate(endDate);

        //initialise the total amount as 0
        Double totalAmount = 0.0;

        //if the provided list is not null and contains request set fields in cycle request
        if(toSettle != null && toSettle.size() > 0){

            //set merchant location
            merchantSettlementCycle.setMscMerchantLocation(toSettle.get(0).getMesLocation());

            //set merchant redemption no
            merchantSettlementCycle.setMscRedemptionMerchant(toSettle.get(0).getMesVendorNo());

            //set merchant no
            merchantSettlementCycle.setMscMerchantNo(toSettle.get(0).getMesMerchantNo());

        }

        //loop through the settlements
        for(MerchantSettlement merchantSettlement : toSettle){

            //add the amount to the total amount variable
            totalAmount += merchantSettlement.getMesAmount();

        }

        //set the cycle's total amount
        merchantSettlementCycle.setMscAmount(totalAmount);

        //set status as not settled
        merchantSettlementCycle.setMscStatus(MerchantSettlementCycleStatus.UNSETTLED);

        //save the cycle
        saveMerchantSettlementCycle(merchantSettlementCycle);

    }


    private Date getCycleGenerateDate(Long merchantNo,Long remNo,Long location,RedemptionMerchant redemptionMerchant){

        //get the last generated date
        Date lastGeneratedDate = getLastCycleGeneratedDate(merchantNo, remNo, location);

        //if no entry is generated yet , last generated will be null
        //in this case return the merchant's created date
        if(lastGeneratedDate == null){

            //get created at for merchant
            lastGeneratedDate = new Date(redemptionMerchant.getCreatedAt().getTime());

        }

        //return last generated date
        return lastGeneratedDate;
    }

    /*
    Method returns the monday for a date. Weekly based cycles are generated from
    monday to sunday. For a provided date this method returns the monday.
     */
    Date getLastMondayForDate(Date date){

        //get a calendar instance
        Calendar calendar = Calendar.getInstance();

        //set the time as provided date
        calendar.setTime(date);

        //get the week day of the date
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

        //difference from monday
        int diff = weekDay-Calendar.MONDAY;

        //if difference is less than 0, provided date is sunday , deduct 6 days in this case to get
        //the previous monday
        if(diff < 0){

            //deduct 6 days
            calendar.add(Calendar.DATE, -6);

        } else if(diff>0){

            //in other cases deduct the difference
            calendar.add(Calendar.DATE,-diff);

        }

        date = new Date(calendar.getTime().getTime());

        //return date
        return date;
    }

    /*
   Method returns the sunday for a date. Weekly based cycles are generated from
   monday to sunday. For a provided date this method returns the sunday.
    */
    Date getNextSunday(Date date){

        //get the calendr instance
        Calendar calendar = Calendar.getInstance();

        //set provided date to calendar
        calendar.setTime(date);

        //set first date of week as monday
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        //get the week day of the date
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

        //get the difference
        int diff = 7 + (Calendar.SUNDAY-weekDay);

        //add the difference from sunday to the date
        if(diff < 7){

            //adding the calculated date difference to date
            calendar.add(Calendar.DATE, diff);

        }

        date = new Date(calendar.getTime().getTime());

        //return date
        return date;

    }


    @Override
    public boolean markCycleAsSettled(Long mscId) throws InspireNetzException {

        //get the cycle details
        MerchantSettlementCycle merchantSettlementCycle = findByMscId(mscId);

        //check data retrieved
        if(merchantSettlementCycle == null){

            //log error
            log.error("markCycleAsSettled : No settlement cycle found for request, MSC ID:"+mscId);

            //throw no data exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        //check the cycle status
        if(merchantSettlementCycle.getMscStatus() == IndicatorStatus.YES){

            //log the error
            log.error("markCycleAsSettled : Cycle is already settled");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_SETTLEMENT_CYCLE_ALREADY_SETTLED);

        }

        //get all the unsettled requests for the date period
        List<MerchantSettlement> merchantSettlements = merchantSettlementService.findByMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(merchantSettlementCycle.getMscRedemptionMerchant(),merchantSettlementCycle.getMscMerchantLocation(), MerchantSettlementType.BANK_PAYMENT, IndicatorStatus.NO,merchantSettlementCycle.getMscStartDate(),merchantSettlementCycle.getMscEndDate());

        boolean isMarked = false;

        //set
        int j=REQUEST_UPDATE_RATE;

        for(int i=0;i<merchantSettlements.size();){

            //if requests size is less than j index , set j to size
            if(merchantSettlements.size() < j){

                j =  merchantSettlements.size();

            }

            //get the sublist
            List<MerchantSettlement> listToUpdate = merchantSettlements.subList(i,j);

            //mark the list as updated
            isMarked = markSettlementListAsUpdated(listToUpdate);

            //copy value of j to i
            i= j;

            //if j is equal list size break
            if(j == (merchantSettlements.size())){

                break;
            }

            //update j
            j = j + REQUEST_UPDATE_RATE;

        }

        if(isMarked){

            //set fields
            merchantSettlementCycle.setMscStatus(IndicatorStatus.YES);
            merchantSettlementCycle.setMscSettledBy(authSessionUtils.getUserLoginId());
            merchantSettlementCycle.setMscSettledDate(new Date(System.currentTimeMillis()));

            //update cycle as settled
            merchantSettlementCycle = saveMerchantSettlementCycle(merchantSettlementCycle);

        }

        //return
        return true;

    }

    private boolean markSettlementListAsUpdated(List<MerchantSettlement> listToUpdate) {


        for(MerchantSettlement merchantSettlement : listToUpdate){

            merchantSettlement.setMesIsSettled(MerchantSettlementStatus.SETTLED);

        }

        Customer customer = new Customer();

        try{

            // Send the sale received event to the reactor
            eventReactor.notify(EventReactorCommand.ERC_MARK_AS_SETTLED, Event.wrap(listToUpdate));


        } catch( RuntimeException e) {

            log.error("Marking items as settled");

            e.printStackTrace();

            return false;


        }

        return true;

    }

}

