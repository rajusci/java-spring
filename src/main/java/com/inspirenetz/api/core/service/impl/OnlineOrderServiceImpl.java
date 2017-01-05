package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.OnlineOrderRepository;
import com.inspirenetz.api.core.service.OnlineOrderService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class OnlineOrderServiceImpl extends BaseServiceImpl<OnlineOrder> implements OnlineOrderService {


    // Initialize the logger
    private static Logger log = LoggerFactory.getLogger(OnlineOrderServiceImpl.class);

    @Autowired
    OnlineOrderRepository onlineOrderRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public OnlineOrderServiceImpl() {

        super(OnlineOrder.class);

    }


    @Override
    protected BaseRepository<OnlineOrder,Long> getDao() {
        return onlineOrderRepository;
    }



    @Override
    public OnlineOrder findByOrdId(Long ordId) {

        // Get the onlineOrder for the given onlineOrder id from the repository
        OnlineOrder onlineOrder = onlineOrderRepository.findByOrdId(ordId);

        // Return the onlineOrder
        return onlineOrder;


    }

    @Override
    public List<OnlineOrder> findByOrdMerchantNoAndOrdUniqueBatchTrackingId(Long ordMerchantNo, Integer ordUniqueBatchTrackingId) {

        // Get the list of orders for the given order id
        List<OnlineOrder> onlineOrderList = onlineOrderRepository.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(ordMerchantNo,ordUniqueBatchTrackingId);

        // return the list
        return onlineOrderList;

    }

    @Override
    public Page<OnlineOrder> searchOnlineOrders(Long ordMerchantNo, Long ordMerchantLocation, Long ordSlot, Integer ordStatus, String filter, String query, Pageable pageable) {

        // Page of data to be returned
        Page<OnlineOrder> onlineOrderPage;

        // Check the filter and then call the specific functionto get the requests
        if ( filter.equals("trackingid") ) {

            // Get the page for the tracking id
            onlineOrderPage = onlineOrderRepository.searchOnlineOrdersByTrackingId(ordMerchantNo, ordMerchantLocation, ordSlot, ordStatus, Integer.parseInt(query), pageable);


        } else if ( filter.equals("loyaltyid") ) {

            // Get the page for the loyalty id
            onlineOrderPage = onlineOrderRepository.searchOnlineOrdersByLoyaltyId(ordMerchantNo, ordMerchantLocation, ordSlot, ordStatus, query, pageable);

        } else if ( filter.equals("productcode") ) {

            // Get the page for the product
            onlineOrderPage = onlineOrderRepository.searchOnlineOrdersByProductCode(ordMerchantNo, ordMerchantLocation, ordSlot, ordStatus, query, pageable);

        } else {

            // Get the onlineOrderPage
            onlineOrderPage = onlineOrderRepository.searchOnlineOrders(ordMerchantNo,ordMerchantLocation,pageable);

        }


        // Return the object
        return onlineOrderPage;

    }



    @Override
    public OnlineOrder saveOnlineOrder(OnlineOrder onlineOrder ) throws InspireNetzException {

        // Save the onlineOrder
        return onlineOrderRepository.save(onlineOrder);

    }

    @Override
    public boolean deleteOnlineOrder(Long ordId) throws InspireNetzException {

        // Delete the onlineOrder
        onlineOrderRepository.delete(ordId);

        // return true
        return true;

    }

    @Override
    public OnlineOrder validateAndSaveOnlineOrder(OnlineOrder onlineOrder) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_ORDER_SLOT);

        return saveOnlineOrder(onlineOrder);
    }

    @Override
    public boolean validateAndDeleteOnlineOrder(Long ordId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_ORDER_SLOT);

        return deleteOnlineOrder(ordId);

    }

}
