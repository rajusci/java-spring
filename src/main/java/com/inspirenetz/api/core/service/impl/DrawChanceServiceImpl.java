package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.domain.validator.DrawChanceValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.DrawChanceRepository;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.DrawChanceService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class DrawChanceServiceImpl extends BaseServiceImpl<DrawChance> implements DrawChanceService {


    private static Logger log = LoggerFactory.getLogger(DrawChanceServiceImpl.class);


    @Autowired
    DrawChanceRepository drawChanceRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public DrawChanceServiceImpl() {

        super(DrawChance.class);

    }


    @Override
    protected BaseRepository<DrawChance,Long> getDao() {
        return drawChanceRepository;
    }



    @Override
    public DrawChance findByDrcId(Long drcId) throws InspireNetzException {

        // Get the drawChance for the given drawChance id from the repository
        DrawChance drawChance = drawChanceRepository.findByDrcId(drcId);

        // Return the drawChance
        return drawChance;

    }

    @Override
    public DrawChance getCustomerDrawChances(Long drcCustomerNo,Integer drcDrawType) {


        // Get the drawChance for the given drawChance id from the repository
        DrawChance drawChance = drawChanceRepository.findByDrcCustomerNoAndDrcType(drcCustomerNo, drcDrawType);

        //return the object
        return drawChance;

    }

    @Override
    public List<DrawChance> findByDrcType(Integer drcType) {

        //get the draw chances list
        List<DrawChance> drawChances = drawChanceRepository.findByDrcTypeAndDrcStatus(drcType, DrawChanceStatus.NEW);

        for(DrawChance drawChance : drawChances){

            drawChance.setDrcStatus(DrawChanceStatus.RETRIEVED);

        }

        drawChanceRepository.save(drawChances);

        //return the list
        return  drawChances;

    }


    @Override
    public DrawChance validateAndSaveDrawChance(DrawChance drawChance ) throws InspireNetzException {

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        DrawChanceValidator validator = new DrawChanceValidator();

        // Create the bindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(drawChance,"drawChance");

        // Validate the request
        validator.validate(drawChance,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveDrawChance - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // If the drawChance.getLrqId is  null, then set the created_by, else set the updated_by
        if ( drawChance.getDrcId() == null ) {

            drawChance.setCreatedBy(auditDetails);

        } else {

            drawChance.setUpdatedBy(auditDetails);

        }

        drawChance = saveDrawChance(drawChance);

        // Check if the drawChance is saved
        if ( drawChance.getDrcId() == null ) {

            // Log the response
            log.info("validateAndSaveDrawChance - Response : Unable to save the drawChance information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return drawChance;


    }

    @Override
    public DrawChance saveDrawChance(DrawChance drawChance ){

        // Save the drawChance
        return drawChanceRepository.save(drawChance);

    }


    @Override
    public boolean deleteDrawChance(Long rolId) {

        // Delete the drawChance
        drawChanceRepository.delete(rolId);

        // return true
        return true;

    }

    /**
     * @date:20-12-2014
     * @param loyaltyId
     * @param drcDrawType
     * @purpose:find customer draw chance
     * @return
     */

    @Override
    public DrawChance getDrawChancesByLoyaltyId(String loyaltyId, Integer drcDrawType) {

        //fetch default merchant number
        Long merchantNo =generalUtils.getDefaultMerchantNo();

        //find customer object with default merchant no
        Customer customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        //intialize draw chance object
        DrawChance drawChance=null;


        //if customer is null return
        if(customer ==null){

            log.info("DrawChanceServiceImpl::getDrawChancesByLoyaltyId-->Customer Is null");

            return drawChance;
        }

        //find the customer draw chance
        drawChance = getCustomerDrawChances(customer.getCusCustomerNo(),drcDrawType);


        return drawChance;
    }

    /**
     * @date:24-12-2014
     * @purpose:expiring draw chances for customer
     * @param customer
     */

    @Override
    public void expiringDrawChance(Customer customer) throws InspireNetzException {

        //get customer number
        Long customerNo =customer.getCusCustomerNo()==null?0L:customer.getCusCustomerNo();

        //find the draw chances (raffle ticket) for the customer
        DrawChance drawChance =drawChanceRepository.findByDrcCustomerNoAndDrcType(customerNo, DrawType.RAFFLE_TICKET);

        if(drawChance ==null){

            return;
        }

        //get the raffle chances for displaying activity
        double raffleChance=drawChance.getDrcChances()==null?0L:drawChance.getDrcChances();

        //set draw chance to zero
        drawChance.setDrcChances(0L);

        //save the draw chance
        drawChanceRepository.save(drawChance);





    }

}

