package com.inspirenetz.api.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.dictionary.SaleResource;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.SaleValidator;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import com.inspirenetz.api.core.loyaltyengine.SaleTransactionProxy;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SaleRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.*;
import com.inspirenetz.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Selector;

import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class SaleServiceImpl extends BaseServiceImpl<Sale> implements SaleService {


    private static Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);


    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private SaleSKUService  saleSKUService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SaleTransactionProxy saleTransactionProxy;

    @Autowired
    private Reactor eventReactor;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private UserMessagingService userMessagingService;


    public SaleServiceImpl() {

        super(Sale.class);

    }


    @Override
    protected BaseRepository<Sale,Long> getDao() {
        return saleRepository;
    }

    @Override
    public Sale findBySalId(Long salId) {

        // Get the sale for the given sale id from the repository
        Sale sale = saleRepository.findBySalId(salId);

        // Return the sale
        return sale;


    }

    @Override
    public Page<Sale> findBySalMerchantNoAndSalDate(Long salMerchantNo, Date salDate,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Sale> saleList = saleRepository.findBySalMerchantNoAndSalDate(salMerchantNo, salDate, pageable);

        // Return the list
        return saleList;

    }

    @Override
    public Page<Sale> findBySalMerchantNoAndSalDateBetween(Long salMerchantNo,Date salStartDate,Date salEndDate,Pageable pageable) {

        // Get the sale using the sale code and the merchant number
        Page<Sale> saleList = saleRepository.findBySalMerchantNoAndSalDateBetween(salMerchantNo, salStartDate, salEndDate, pageable);

        // Return the sale object
        return saleList;

    }

    @Override
    public Page<Sale> findBySalMerchantNoAndSalLoyaltyId(Long salMerchantNo,String salLoyaltyId,Pageable pageable) {

        // Get the sale using the sale code and the merchant number
        Page<Sale> saleList = saleRepository.findBySalMerchantNoAndSalLoyaltyId(salMerchantNo, salLoyaltyId, pageable);

        // Return the sale object
        return saleList;

    }


    @Override
    public Page<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(Long salMerchantNo,String salLoyaltyId,Date salStartDate, Date salEndDate,Pageable pageable) {

        // Get the sale using the sale code and the merchant number
        Page<Sale> saleList = saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(salMerchantNo, salLoyaltyId, salStartDate, salEndDate, pageable);

        // Return the sale object
        return saleList;
    }

    @Override
    public boolean isDuplicateSale(Long salMerchantNo, String salLoyaltyId, String salPaymentReference, Date salDate, double salAmount) {
        // Get the SaleValidator
        SaleValidator validator = new SaleValidator();


        // Check if there is purchae information existing for the passed values
        Sale sale = saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalAmountAndSalPaymentReference(salMerchantNo,salLoyaltyId,salDate,salAmount,salPaymentReference);

        // If the sale object is valid and if there is a valid sale id, then we have
        // a sale entry already existing
        if ( sale != null && sale.getSalId() != null ) {

            return true;

        }

        // Else return false
        return false;

    }

    @Override
    public Sale saveSaleDataFromParams(Map<String,Object> params) throws InspireNetzException {


        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long userLocation = authSessionUtils.getUserLocation();

        // Get the userNumber
        Long userNo = authSessionUtils.getUserNo();



        // Get the sale object
        Sale sale = getSaleObjectFromParams(params);


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Set the created by field
        sale.setCreatedBy(auditDetails);


        // Set the merchantNo
        sale.setSalMerchantNo(merchantNo);

        // Set the user location
        sale.setSalLocation(userLocation);

        // Set the user number
        sale.setSalUserNo(userNo);

        // Log the sale data
        log.info("saveSaleDataFromParams :Sale object : " +sale);

        // Check if the customer is active for the the current save
        if ( !loyaltyEngineService.isCustomerActive(sale.getSalLoyaltyId(),sale.getSalMerchantNo()) ) {

            // Log the information
            log.info("saveSaleDataFromParams : No valid customer, rejecting data");

            // Return null
            return null;

        }

        // Save the sale object
        sale = saveSale(sale);

        // If the sale is not saved, then give error
        if ( sale == null || sale.getSalId() == null ) {

            // Log the response
            log.info("saveSale - Response : Unable to save the sale information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Return the Sale object
        return sale;

    }




    @Override
    public void processSaleTransactionForLoyalty(Sale sale) {

        // Call the proxy method
        saleTransactionProxy.processSaleTransactionForLoyalty(sale);

    }

    /**
     * Function to get the Sale object from the  params passed
     * The params are expectd to be Map<String,Object>
     *
     * @param params    - Map of Map<String,Object>
     * @return          - Sale object with the SaleSKU attached as OneToMany set
     */
    protected Sale getSaleObjectFromParams(Map<String,Object> params) {


        // Get the attributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = new AttributeExtendedEntityMap();

        // Add the params to the attributeExtendedEntityMap
        attributeExtendedEntityMap.putAll(params);


        // Create the Sale object
        Sale sale = new Sale();

        // Parse the object into map
        sale = (Sale) fromAttributeExtensionMap(sale, attributeExtendedEntityMap, AttributeExtensionMapType.ALL);



        // Check if the skuData is set
        if ( params.containsKey("saleSKUSet") ) {

            // We need to call the attributeExtensionUtils, otherwise we
            // get a cast exception for the LinkedHashMap
            List<AttributeExtendedEntityMap> skuDataList = attributeExtensionUtils.buildAttributeExtendedEntityMapFromMapList((List<Map<String,Object>>) params.get("saleSKUSet"));

            // Check if the list is not empty
            if ( skuDataList != null && !skuDataList.isEmpty() ) {

                // Get the skuSets if any
                Set<SaleSKU> saleSKUSet = saleSKUService.getSaleSKUFromParams(skuDataList);

                // Add to the SaleSKUSet to the Sale object
                sale.setSaleSKUSet(saleSKUSet);

            }

        }


        // Return the Sale object
        return sale;

    }

    @Override
    public Page<Sale> searchSales( String filter, String query,Date salStartDate, Date salEndDate, Pageable pageable ) {

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable storing page
        Page<Sale> salesPage = null;


        // Check if the salStartDate is set or not
        // If the start date is not set, then we need to set the date to the minimum value
        if ( salStartDate == null ){

            // Create the calendar object
            Calendar cal = Calendar.getInstance();

            // set Date portion to January 1, 1970
            cal.set( cal.YEAR, 1970 );
            cal.set( cal.MONTH, cal.JANUARY );
            cal.set( cal.DATE, 1 );

            salStartDate = new Date(cal.getTime().getTime());

        }


        // Check if the endDate is set, if not then we need to
        // set the date to the largest possible date
        if ( salEndDate == null ) {

            // Create the calendar object
            Calendar cal = Calendar.getInstance();

            // set Date portion to December 31, 9999
            cal.set( cal.YEAR, 9999 );
            cal.set( cal.MONTH, cal.DECEMBER );
            cal.set( cal.DATE, 31 );

            salEndDate = new Date(cal.getTime().getTime());

        }


        // Log the Request
        log.info("searchSale - Request params Filter - "+filter +" : - Query:"+query.toString() +
                " - salStartDate:"+salStartDate.toString()+
                " - salEndDate:"+salEndDate.toString()
        );



        // Check the filter and query
        if ( filter.equals("loyaltyid") ) {

            // Get the list of sales
            salesPage = findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(merchantNo, query, salStartDate, salEndDate, pageable);

        }


        // Return the salePage
        return salesPage;

    }

    @Override
    public Sale getSaleInfo(Long salId) throws InspireNetzException {

        // Get the merchantNo from the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the sales Information
        Sale sale = findBySalId(salId);

        // Check if the sale is found
        if ( sale == null || sale.getSalId() == null) {

            // Log the response
            log.info("getSaleInfo - Response : No sale information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( sale.getSalMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getSaleInfo - Response : You are not authorized to view the sale");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // If the type is sales sku , then get the sku information as well
        if ( sale.getSalType() == SaleType.ITEM_BASED_PURCHASE ) {

            // Fetch the sku data lazily
            sale.getSaleSKUSet().toString();


        }


        // return the Sale object
        return sale;

    }

    @Override
    @Transactional
    public Sale saveSale(Sale sale ) throws InspireNetzException {

        // Create the BindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sale,"sale");

        // Create the SaleValidator
        SaleValidator validator = new SaleValidator();

        // Validate using the SaleValidator object
        validator.validate(sale, result);


        // Check if there are validation errors for the purchse object
        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(result);

            // Log the response
            log.info("saveSale - Response : Invalid Input - " + messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }


        /*// Check if the sale data is already existing
        boolean isExist = isDuplicateSale(
                sale.getSalMerchantNo(),
                sale.getSalLoyaltyId(),
                sale.getSalPaymentReference(),
                sale.getSalDate(),
                sale.getSalAmount()
        );


        // Check the boolean value and if the entry already exists, then we need to show
        // the message as already exists
        if ( isExist ) {

            // Log the response
            log.info("saveSale - Response : Sale entry already exists");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }*/


        // Save the sale
        return saleRepository.save(sale);

    }

    @Override
    public boolean deleteSale(Long salId) {

        // Delete the sale
        saleRepository.delete(salId);

        // return true
        return true;

    }


    /**
     * Function to get the consolidated sale sku for the list of customers for a given period
     *
     * @param customerList - The List of customers for whom we need to consolidate the data
     * @param startDate    - The start date of transactions
     * @param endDate      - The endDate for transactions
     *
     * @return             - List of data
     */
    protected List<SaleSKU> getConsolidatedSaleSku(List<Customer> customerList ,Date startDate,Date endDate ) {

        // The list to be returned with the consolidated sku data for all the customer
        List<SaleSKU> saleSKUList = new ArrayList<SaleSKU>(0);

        // Iterate through he customer list and get the Sales data for each customer
        for ( Customer customer : customerList ) {

            // Get the Sales data
            List<Sale> saleList = saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),startDate,endDate);

            // Check if the list is valid
            if ( saleList != null && !saleList.isEmpty() ) {

                // Iterate through the list and then lazily load the SaleSet
                for( Sale sale : saleList ) {

                    // Lazily load the data
                    sale.getSaleSKUSet().toString();

                    // Add the sku set to the list
                    saleSKUList.addAll(sale.getSaleSKUSet());

                }

            }

        }


        // Return the list
        return saleSKUList;

    }


    /**
     * Function to get the consolidated amount of msf for a list of customers for a given
     * date range and a particular product
     *
     * This function will consolidate the amount after calculating the msf value
     *
     * @param customerList - The List of customers for whom we need to consolidate the data
     * @param startDate    - The start date of transactions
     * @param endDate      - The endDate for transactions
     * @param productCode  - The product code to be checked
     *
     * @return             - Return the amount consolidated msf value
     */
    @Override
    public Double getConsolidatedAmountExceedingMsfForCustomers(List<Customer> customerList,Date startDate, Date endDate, String productCode ) {

        // The double value to be returned
        double amount = 0;

        // Get the Consolidated List of SaleSKu
        List<SaleSKU> saleSKUList = getConsolidatedSaleSku(customerList,startDate,endDate);

        // Check if the list is not null and not emtpy
        if ( saleSKUList == null || saleSKUList.isEmpty() ) {

            // Return amount as 0
            return amount;

        }


        // Iterate through the list and find the value of the amount
        // by subtracting the msf from sale amount
        for ( SaleSKU saleSKU : saleSKUList ) {

            // We need to check if the type of the transaction is  a
            // bill payment for the postpaid
            if ( !saleSKU.getSsuProductCode().equals(productCode) ) {

                continue;

            }


            // Subtract the amount
            double diff = saleSKU.getSsuPrice() -  saleSKU.getSsuMsfValue();

            // Check if the diff is greater than 0, otherwise we ignore it.
            if ( diff > 0  ) {

                // Add to the amount
                amount += ( diff * saleSKU.getSsuQty());

            }

        }


        // return the amount finally
        return amount;

    }


    /**
     * Function to calculate the conslidated amount for a particular product
     *
     * @param customerList - The List of customers for whom we need to consolidate the data
     * @param startDate    - The start date of transactions
     * @param endDate      - The endDate for transactions
     * @param productCode  - The product code to be checked
     *
     * @return             - REturn the consolidated amount
     */
    @Override
    public Double getConsolidatedSaleAmountForCustomer(List<Customer> customerList,Date startDate, Date endDate, String productCode ) {

        // The double value to be returned
        double amount = 0;

        // Get the Consolidated List of SaleSKu
        List<SaleSKU> saleSKUList = getConsolidatedSaleSku(customerList, startDate, endDate);

        // Check if the list is not null and not emtpy
        if ( saleSKUList == null || saleSKUList.isEmpty() ) {

            // Return amount as 0
            return amount;

        }


        // Iterate through the list and find the value of the amount
        // by subtracting the msf from sale amount
        for ( SaleSKU saleSKU : saleSKUList ) {

            // We need to check if the type of the transaction is  a
            // bill payment for the postpaid
            if ( !saleSKU.getSsuProductCode().equals(productCode) ) {

                continue;

            }

            amount += saleSKU.getSsuPrice() * saleSKU.getSsuQty();

        }


        // return the amount finally
        return amount;

    }

    @Override
    public AttributeExtendedEntityMap toAttributeExtensionMap(Object obj, Integer attributeExtensionMapType) {

        // Get the Sale object
        Sale sale = (Sale) obj;

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = attributeExtensionUtils.getAttributeExtensionMapForObject(sale, sale.getSaleExtensionSet(), attributeExtensionMapType);

        // Return the attributeExtendedEntityMap object
        return attributeExtendedEntityMap;

    }

    @Override
    public Object fromAttributeExtensionMap(Object obj, AttributeExtendedEntityMap attributeExtendedEntityMap, Integer attributeExtensionMapType) {

        // Get the attributes map
        AttributeMap attributeMap = attributeService.getAttributesMapByName(0);

        // Create the Sale object
        Sale sale = (Sale) attributeExtensionUtils.createEntityFromAttributeExtensionMap(obj,attributeMap,attributeExtendedEntityMap,attributeExtensionMapType,this);

        // return the Sale object
        return sale;

    }

    @Override
    public void setExtFieldValue(Object obj, Attribute attribute, String value) {

        // Get the sale Object
        Sale sale = (Sale) obj;

        // Check if the field is set
        boolean isSet = attributeExtensionUtils.setExtFieldValue(sale.getSaleExtensionSet(),attribute,value);


        // Check if the isSet is true, otherwise , we need to set the data in the extFields
        if ( !isSet ) {

            // If the saleInfoSet is null, then intialize it
            if ( sale.getSaleExtensionSet() == null ) {

                sale.setSaleExtensionSet(new HashSet<SaleExtension>(0));

            }

            // Create the SaleExtension object
            SaleExtension saleExtension = new SaleExtension();

            // Set the attribute id
            saleExtension.setAttrId(attribute.getAtrId());

            // Set the value
            saleExtension.setAttrValue(value.toString());

            // Set the attribute
            saleExtension.setAttribute(attribute);

            // Add to the set
            sale.getSaleExtensionSet().add(saleExtension);
        }

    }

    @Override
    public String getExtFieldValue(Object obj, Attribute attribute) {

        // Get the Sale object
        Sale sale = (Sale)obj;

        // Return the attribute
        return attributeExtensionUtils.getExtFieldValue(sale.getSaleExtensionSet(), attribute);


    }

    @Override
    public void processSaleTransactionsForLoyalty(List<Sale> saleList) {

        // Get the Sale object
        for(Sale sale:saleList){

            // If the sale id is not null, then process the reactor
            if ( sale != null && sale.getSalId() != null ) {

                // Trigger the SALE_RECORDED event for reactor
                processSaleTransactionForLoyalty(sale);

            }
        }

    }

    @Override
    public List<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(Long salMerchantNo, String salLoyalityId,Date salDate, String salPaymentReference) {

        //get sale object
        List<Sale> saleList=saleRepository.findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(salMerchantNo,salLoyalityId,salDate,salPaymentReference);

        return saleList;
    }

    @Override
    public void UpdateSales(List<Sale> saleList,String auditDetails) throws InspireNetzException {

        long before = System.currentTimeMillis();

        log.info("UpdateSales: Start time" + before);


        for(Sale sale:saleList){

            // Set the auditDetails
            sale.setUpdatedBy(auditDetails);

            // Call the event
            triggerUpdateSaleAsync(sale);

        }

    }


    protected void triggerUpdateSaleAsync(Sale sale) {

        // Trigger the event
        eventReactor.notify(EventReactorCommand.ERC_UPDATE_SALES, Event.wrap(sale));

    }

    @Selector(value= EventReactorCommand.ERC_UPDATE_SALES,reactor = EventReactor.REACTOR_NAME)
    public void updateSaleAsync(Sale sale) throws InspireNetzException {

        // log the information
        log.info("Event received : ERC_UPDATE_SALES");

        //fetch sale object
        List<Sale> sales =saleRepository.findBySalMerchantNoAndSalLocationAndSalDateAndSalPaymentReference(sale.getSalMerchantNo(),sale.getSalLocation(),sale.getSalDate(),sale.getSalPaymentReference());

        //Check sales list null
        if(sales!=null&&sales.size()!=0){

            Sale fetchedSale=sales.get(0);

            //fetch transaction object corresponding to sale object
            Transaction transaction=transactionService.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(fetchedSale.getSalMerchantNo(), fetchedSale.getSalLoyaltyId(), fetchedSale.getSalId().toString(), fetchedSale.getSalLocation(), fetchedSale.getSalDate());

            //Check transaction object null
            if(transaction!=null && transaction.getTxnId()!=null){

                //Create PointDeductData object
                PointDeductData pointDeductData=createPointDeductObject(transaction,sale.getUpdatedBy());

                //reverse the points issued in last save
                boolean isPointDeducted=loyaltyEngineService.deductPointsProxy(pointDeductData);

                //Check transaction object null
                if(!isPointDeducted){

                    // Log the response
                    log.info("UpdateSales - Response : point deduction failed");

                    // Throw InspireNetzException with ERR_OPERATION_FAILED as error
                    throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
                }
            }

            //Update Sale Entry
            sale.setSalId(fetchedSale.getSalId());

            //find sale SKU Set
            List<SaleSKU> saleSKUList=saleSKUService.findBySsuSaleId(sale.getSalId());

            Set<SaleSKU> saleSKUSet=new HashSet<SaleSKU>(saleSKUList);

            saleSKUService.deleteSaleSKUSet(saleSKUSet);

        }


        try{

            sale=saveSale(sale);

        }catch (Exception e){

            log.info("exception for saving sale+---update sale"+e);
        }


        // If the sale returned is not null, then process the transaction
        if ( sale != null && sale.getSalId() != null ) {

            // Trigger the SALE_RECORDED event for reactor
            processSaleTransactionForLoyalty(sale);

        }

    }

    @Override
    public void saveSalesAll(List<Sale> saleList,String auditDetails) throws InspireNetzException {

        List<Sale> salesInsertList=new ArrayList<>();

        List<Sale> salesUpdateList=new ArrayList<>();

        for(Sale sale:saleList){

            //fetch sale object
            List<Sale> sales =saleRepository.findBySalMerchantNoAndSalLocationAndSalDateAndSalPaymentReference(sale.getSalMerchantNo(),sale.getSalLocation(),sale.getSalDate(),sale.getSalPaymentReference());

            //Check sales list null
            if(sales!=null&&sales.size()!=0){

                salesUpdateList.add(sale);

            }else{

                salesInsertList.add(sale);
            }

        }

        if(salesInsertList != null && !salesInsertList.isEmpty()){

            //iterate sale list and insert sale
            for(Sale sale :salesInsertList){

                //insert sale list
                try{

                    Sale sale1 = saveSale( sale);

                    //add list object
                    List<Sale> saleList1 =new ArrayList<>();

                    //add sale list object
                    saleList1.add(sale1);

                    // Call the loyalty processing for the sales list
                    processSaleTransactionsForLoyalty(saleList1);

                }catch (Exception e){

                    //log error information
                    log.info("Sale Saving Exception +----------"+e);

                }
            }


        }

        if(salesUpdateList != null && !salesUpdateList.isEmpty()){

            //update sales
            UpdateSales(salesUpdateList,auditDetails);

        }


    }

    @Override
    public List<Sale> findBySalMerchantNoAndSalLocationAndSalDateAndSalPaymentReference(Long salMerchantNo, Long salLocation, Date salDate, String salPaymentReference) {

        //get sale object
        List<Sale> saleList=saleRepository.findBySalMerchantNoAndSalLocationAndSalDateAndSalPaymentReference(salMerchantNo,salLocation,salDate,salPaymentReference);

        return saleList;
    }

    @Override
    public Sale processSaleFromQueue(SaleResource saleResource) throws InspireNetzException {

        // Get the customer object
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(saleResource.getSalLoyaltyId(), saleResource.getSalMerchantNo());

        // if the customer does not exist, then return false
        if ( customer == null || customer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Log that the transaction is rejected
            log.info("Rejecting transaction - No valid customer : " + saleResource.getSalLoyaltyId() + " - " + saleResource.getSalPaymentReference());

            // Return false
            return null;

        }



        // Log the start time
        long startTime = System.currentTimeMillis();

        // Create a set for the SaleSKU
        SaleSKU saleSKU = new SaleSKU(
                saleResource.getSsuProductCode(),
                saleResource.getSsuTransactionType(),
                saleResource.getSsuQty(),
                saleResource.getSsuPrice(),
                saleResource.getSsuMsfValue(),
                saleResource.getSsuContracted()
        );

        // Add this to a set
        Set<SaleSKU> saleSKUSet = new HashSet<>(0);

        // Add the item to the set
        saleSKUSet.add(saleSKU);

        // Get the sale object
        //Sale sale = getSaleObjectFromParams(params);
        Sale sale = new Sale(
                saleResource.getSalMerchantNo(),
                saleResource.getSalLoyaltyId(),
                saleResource.getSalLocation(),
                saleResource.getSalDate(),
                saleResource.getSalTime(),
                saleResource.getSalTimestamp(),
                saleResource.getSalType(),
                saleResource.getSalAmount(),
                saleResource.getSalTxnChannel(),
                saleResource.getSalQty(),
                saleResource.getSalPaymentReference(),
                saleResource.getSalQty(),
                saleSKUSet
        );

        // Add the set to the sale object
        sale.setSaleSKUSet(saleSKUSet);


        // Do the mapping
        //mapper.map(params,sale);

        // Log the information
        log.info("getSaleObjectFromParams -> LoyaltyEngine  - "+sale.getSalPaymentReference()+ " : " + (System.currentTimeMillis() - startTime));

       /* // Hold the audit details
        String auditDetails = Long.toString(sale.getSalUserNo());

        // Set the created by field
        sale.setCreatedBy(auditDetails);
*/
        // Log the sale data
        log.info("saveSaleDataFromParams :Sale object : " + sale);

       /* // Check if the customer is active for the the current save
        if ( !customerService.isCustomerValidForTransaction(sale.getSalLoyaltyId(), sale.getSalMerchantNo()) ) {

            // Log the information
            log.info("saveSaleDataFromParams : No valid customer, rejecting data");

            // Return null
            return null;

        }*/



        // Set the salId to a 0
        // Log the the time for saving
        log.info("saveSaleDataFromParams -> LoyaltyEngine - " + sale.getSalId() + " : " + (System.currentTimeMillis() - startTime));

        // Save the sale object
        sale = saveSale(sale);

        // Return the Sale object
        return sale;
    }

    private PointDeductData createPointDeductObject(Transaction transaction,String auditDetails) throws InspireNetzException{




        PointDeductData pointDeductData = new PointDeductData();


        //set values to the object
        pointDeductData.setMerchantNo(transaction.getTxnMerchantNo());
        pointDeductData.setLoyaltyId(transaction.getTxnLoyaltyId());
        pointDeductData.setRwdCurrencyId(transaction.getTxnRewardCurrencyId());
        pointDeductData.setRedeemQty(transaction.getTxnRewardQty());
        pointDeductData.setTxnLocation(transaction.getTxnLocation());
        pointDeductData.setTxnDate(new java.sql.Date(new java.util.Date().getTime()));
        pointDeductData.setInternalRef(transaction.getTxnLoyaltyId());
        pointDeductData.setExternalRef(transaction.getTxnInternalRef());

        //set audit details
        pointDeductData.setAuditDetails(auditDetails);


        pointDeductData.setTxnType(TransactionType.REWARD_ADJUSTMENT_DEDUCTING);



        //return the pointDeductData object
        return pointDeductData;
    }


    @Override
    public void sendSalesEBill(CustomerResource customerResource, com.inspirenetz.api.rest.resource.SaleResource saleResource,Map<String,Object>params)throws InspireNetzException{


        Long merchantNo=0L;

        //send notification message to customer
        HashMap<String,String> msgParams = new HashMap<>();

        if(customerResource==null){

            merchantNo=authSessionUtils.getMerchantNo();
        }

        if(customerResource!=null &&(customerResource.getCusMerchantNo()==null||customerResource.getCusMerchantNo()==0)){

            merchantNo=authSessionUtils.getMerchantNo();
        }

        if(customerResource!=null && customerResource.getCusMerchantNo()!=null){

            Merchant merchant=merchantService.findByMerMerchantNo(customerResource.getCusMerchantNo());

            if(merchant!=null){

                //add points to the map
                msgParams.put("#merchant",merchant.getMerMerchantName()+"");
            }
        }

        if(customerResource!=null){

            //send notification message to customer
            HashMap<String,String> smsParamsFromCustomer =getNotificationParamsForCustomer(customerResource);

            if(smsParamsFromCustomer!=null){

                msgParams.putAll(smsParamsFromCustomer);
            }

        }

        if(saleResource!=null){

            //send notification message to customer
            HashMap<String,String> smsParamsFromSale =getNotificationParamsForSaleResource(saleResource);

            if(smsParamsFromSale!=null){

                msgParams.putAll(smsParamsFromSale);
            }

            String lineItemHeader ="<th>Name</>\n" +
                    "<th>Qty</th>\n" +
                    "<th>Price</th>\n" +
                    "<th>Amount</th>\n" ;

            msgParams.put("#lineItemHeader",lineItemHeader);

            String lineItem=formatMessageForSalesSKU(saleResource.getSaleSKUResourceList());

            msgParams.put("#lineItem",lineItem);
        }





        if(params!=null){


            for (Map.Entry<String, Object> entry : params.entrySet()) {

                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                msgParams.put("#"+key,value);

            }
        }

        MessageWrapper messageWrapper =  generalUtils.getMessageWrapperObject(MessageSpielValue.E_BILL_FOR_SALES,customerResource.getCusLoyaltyId(),customerResource.getCusMobile(),customerResource.getCusEmail(),"",merchantNo,msgParams,MessageSpielChannel.ALL,IndicatorStatus.NO);

        //send sms
        userMessagingService.transmitNotification(messageWrapper);
    }

    private Map<String, String> getParameterMapForCustomerClass(CustomerResource customerResource) {

        //map holding the request key-value pair
        Map<String,String> requestParameters = new HashMap<>(0);

        //convert th cashback object to map
        requestParameters = mapper.convertValue(customerResource,Map.class);

        //return the map
        return requestParameters;

    }

    private HashMap<String,String> getNotificationParamsForCustomer(CustomerResource customerResource) {

        //get the parameters in api
        HashMap<String,String> smsParams = (HashMap<String, String>) getParameterMapForCustomerClass(customerResource);

        List<String> keyList = new ArrayList<>(0);

        HashMap<String,String> returnList = new HashMap<>(0);

        for (Map.Entry<String, String> entry : smsParams.entrySet()) {

            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            returnList.put("#"+key,value);

        }


        //return the map
        return returnList;

    }

    private Map<String, String> getParameterMapForSaleResourceClass(com.inspirenetz.api.rest.resource.SaleResource saleResource) {

        //map holding the request key-value pair
        Map<String,String> requestParameters = new HashMap<>(0);

        //convert th cashback object to map
        requestParameters = mapper.convertValue(saleResource,Map.class);

        //return the map
        return requestParameters;

    }

    private HashMap<String,String> getNotificationParamsForSaleResource(com.inspirenetz.api.rest.resource.SaleResource saleResource) {

        //get the parameters in api
        HashMap<String,String> smsParams = (HashMap<String, String>) getParameterMapForSaleResourceClass(saleResource);

        List<String> keyList = new ArrayList<>(0);

        HashMap<String,String> returnList = new HashMap<>(0);

        for (Map.Entry<String, String> entry : smsParams.entrySet()) {

            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            returnList.put("#"+key,value);

        }


        //return the map
        return returnList;

    }

    private String formatMessageForSalesSKU(List<SaleSKUResource> saleSKUResources) {

        String formatString ="";

        if(saleSKUResources==null){

            return formatString;
        }

        for(SaleSKUResource saleSKUResource:saleSKUResources){

            Double qty=saleSKUResource.getSsuQty();
            Double price=saleSKUResource.getSsuPrice();
            Double amount=qty*price;

            formatString+="<tr><td>"+saleSKUResource.getPrdName()+"</td>"+
                    "<td>"+saleSKUResource.getSsuQty()+"</td>"+
                    "<td>"+saleSKUResource.getSsuPrice()+"</td>"+
                    "<td>"+amount+"</td></tr>";
        }

        return formatString;
    }




}
