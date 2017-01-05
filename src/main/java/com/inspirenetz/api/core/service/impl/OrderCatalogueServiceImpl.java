package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.OrderCatalogueRepository;
import com.inspirenetz.api.core.service.OrderCatalogueService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class OrderCatalogueServiceImpl extends BaseServiceImpl<OrderCatalogue> implements OrderCatalogueService {


    private static Logger log = LoggerFactory.getLogger(OrderCatalogueServiceImpl.class);


    @Autowired
    OrderCatalogueRepository orderCatalogueRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public OrderCatalogueServiceImpl() {

        super(OrderCatalogue.class);

    }


    @Override
    protected BaseRepository<OrderCatalogue,Long> getDao() {
        return orderCatalogueRepository;
    }

    @Override
    public Page<OrderCatalogue> findByOrcMerchantNo(Long orcMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<OrderCatalogue> orderCatalogueList = orderCatalogueRepository.findByOrcMerchantNo(orcMerchantNo,pageable);

        // Return the list
        return orderCatalogueList;

    }

    @Override
    public OrderCatalogue findByOrcProductNo(Long orcProductNo) {

        // Get the orderCatalogue for the given orderCatalogue id from the repository
        OrderCatalogue orderCatalogue = orderCatalogueRepository.findByOrcProductNo(orcProductNo);

        // Return the orderCatalogue
        return orderCatalogue;


    }

    @Override
    public OrderCatalogue findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(Long orcMerchantNo,String orcCode,Long orcAvailableLocation) {

        // Get the orderCatalogue using the orderCatalogue code and the merchant number
        OrderCatalogue orderCatalogue = orderCatalogueRepository.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orcMerchantNo, orcCode, orcAvailableLocation);

        // Return the orderCatalogue object
        return orderCatalogue;

    }

    @Override
    public Page<OrderCatalogue> searchOrderCatalogues(Long merchantNo, Long merchantLocation, String filter, String query, Pageable pageable) {

        // Check the filter and query and return the page
        if ( filter.equals("code") ) {

            // Get the page
            Page<OrderCatalogue> orderCataloguePage = orderCatalogueRepository.findByOrcMerchantNoAndOrcAvailableLocationAndOrcProductCodeLike(merchantNo, merchantLocation, "%" + query + "%", pageable);

            // Return the page
            return orderCataloguePage;

        } else if ( filter.equals("name") ) {

            // Get the page
            Page<OrderCatalogue> orderCataloguePage = orderCatalogueRepository.findByOrcMerchantNoAndOrcAvailableLocationAndOrcDescriptionLike(merchantNo, merchantLocation, "%" + query + "%", pageable);

            // Return the page
            return orderCataloguePage;


        } else {

            // Get the page for the merchant and location
            Page<OrderCatalogue> orderCataloguePage = orderCatalogueRepository.findByOrcMerchantNoAndOrcAvailableLocation(merchantNo, merchantLocation, pageable);

            // Return the page
            return orderCataloguePage;

        }
    }


    @Override
    public boolean isDuplicateOrderCatalogueExisting(OrderCatalogue orderCatalogue) {

        // Get the orderCatalogue information
        OrderCatalogue exOrderCatalogue = orderCatalogueRepository.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());

        // If the orcProductNo is 0L, then its a new orderCatalogue so we just need to check if there is ano
        // ther orderCatalogue code
        if ( orderCatalogue.getOrcProductNo() == null || orderCatalogue.getOrcProductNo() == 0L ) {

            // If the orderCatalogue is not null, then return true
            if ( exOrderCatalogue != null ) {

                return true;

            }

        } else {

            // Check if the orderCatalogue is null
            if ( exOrderCatalogue != null && orderCatalogue.getOrcProductNo().longValue() != exOrderCatalogue.getOrcProductNo().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public OrderCatalogue saveOrderCatalogue(OrderCatalogue orderCatalogue ) throws InspireNetzException {

        // Check if the orderCatalogue is existing
        boolean isExist = isDuplicateOrderCatalogueExisting(orderCatalogue);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveOrderCatalogue - Response : OrderCatalogue code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        // Save the orderCatalogue
        return orderCatalogueRepository.save(orderCatalogue);

    }

    @Override
    public boolean deleteOrderCatalogue(Long orcProductNo) throws InspireNetzException {

       // Delete the orderCatalogue
        orderCatalogueRepository.delete(orcProductNo);

        // return true
        return true;

    }

    @Override
    public OrderCatalogue validateAndSaveOrderCatalogue(OrderCatalogue orderCatalogue) throws InspireNetzException {


        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_ORDER_CATALOGUE);

        return saveOrderCatalogue(orderCatalogue);
    }

    @Override
    public boolean validateAndDeleteOrderCatalogue(Long orcProductNo) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_ORDER_CATALOGUE);

        return deleteOrderCatalogue(orcProductNo);
    }

}
