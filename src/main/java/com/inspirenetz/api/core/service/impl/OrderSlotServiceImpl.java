package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.OrderSlotRepository;
import com.inspirenetz.api.core.service.OrderSlotService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Time;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class OrderSlotServiceImpl extends BaseServiceImpl<OrderSlot> implements OrderSlotService {


    private static Logger log = LoggerFactory.getLogger(OrderSlotServiceImpl.class);


    @Autowired
    OrderSlotRepository orderSlotRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    public OrderSlotServiceImpl() {

        super(OrderSlot.class);

    }


    @Override
    protected BaseRepository<OrderSlot,Long> getDao() {

        return orderSlotRepository;

    }

    @Override
    public Page<OrderSlot> findByOrtMerchantNo(Long ortMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<OrderSlot> orderSlotList = orderSlotRepository.findByOrtMerchantNo(ortMerchantNo,pageable);

        // Return the list
        return orderSlotList;

    }

    @Override
    public OrderSlot findByOrtId(Long ortId) {

        // Get the orderSlot for the given orderSlot id from the repository
        OrderSlot orderSlot = orderSlotRepository.findByOrtId(ortId);

        // Return the orderSlot
        return orderSlot;


    }

    @Override
    public Page<OrderSlot> findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionOrderByOrtStartingTime(Long ortMerchantNo, Long ortLocation, Integer ortType, Integer ortSession,Pageable pageable) {

        // Get the OrderSlot page
        Page<OrderSlot> orderSlotPage = orderSlotRepository.searchOrderSlots(ortMerchantNo, ortLocation, ortType, ortSession, pageable);

        // Return the orderslot page
        return orderSlotPage;

    }

    @Override
    public OrderSlot findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(Long ortMerchantNo, Long ortLocation, Integer ortType, Integer ortSession, Time ortStartingTime) {

        // Get the OrderSlot information
        OrderSlot orderSlot = orderSlotRepository.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(ortMerchantNo, ortLocation, ortType, ortSession, ortStartingTime);

        // Return the orderSlot
        return orderSlot;

    }


    @Override
    public boolean isDuplicateOrderSlotExisting(OrderSlot orderSlot) {

        // Get the orderSlot information
        OrderSlot exOrderSlot = orderSlotRepository.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(orderSlot.getOrtMerchantNo(), orderSlot.getOrtLocation(), orderSlot.getOrtType(), orderSlot.getOrtSession(), orderSlot.getOrtStartingTime());

        // If the ortId is 0L, then its a new orderSlot so we just need to check if there is ano
        // ther orderSlot code
        if ( orderSlot.getOrtId() == null || orderSlot.getOrtId() == 0L ) {

            // If the orderSlot is not null, then return true
            if ( exOrderSlot != null ) {

                return true;

            }

        } else {

            // Check if the orderSlot is null
            if ( exOrderSlot != null && orderSlot.getOrtId().longValue() != exOrderSlot.getOrtId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }


    @Override
    public OrderSlot saveOrderSlot(OrderSlot orderSlot ) throws InspireNetzException {

        // Check if the orderSlot is already existing
        boolean isExist = isDuplicateOrderSlotExisting(orderSlot);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveOrderSlot - Response : OrderSlot code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // Save the orderSlot
        return orderSlotRepository.save(orderSlot);

    }


    @Override
    public boolean deleteOrderSlot(Long ortId) throws InspireNetzException {

        // Delete the orderSlot
        orderSlotRepository.delete(ortId);

        // return true
        return true;

    }

    @Override
    public OrderSlot validateAndSaveOrderSlot(OrderSlot orderSlot) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_ORDER_SLOT);

        return saveOrderSlot(orderSlot);
    }

    @Override
    public boolean validateAndDeleteOrderSlot(Long ortId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_ORDER_SLOT);

        return deleteOrderSlot(ortId);
    }

}
