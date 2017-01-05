package com.inspirenetz.api.core.service.impl;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.LoyaltyProgramValidator;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import com.inspirenetz.api.core.loyaltyengine.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.LoyaltyProgramRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.AttributeExtensionUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DataValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.*;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class LoyaltyProgramServiceImpl extends BaseServiceImpl<LoyaltyProgram> implements LoyaltyProgramService {


    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramServiceImpl.class);



    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    private SaleSKUService saleSKUService;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private DroolsEngineService droolsEngineService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private UserService userService;



    public LoyaltyProgramServiceImpl() {

        super(LoyaltyProgram.class);

    }



    @Override
    protected BaseRepository<LoyaltyProgram,Long> getDao() {
        return loyaltyProgramRepository;
    }

    @Override
    public LoyaltyProgram findByPrgProgramNo(Long prgProgramNo) {

        // Get the loyaltyPrograms
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findByPrgProgramNo(prgProgramNo);

        // Return the loyaltyProgram
        return loyaltyProgram;


    }

    @Override
    public Page<LoyaltyProgram> findByPrgMerchantNo(Long prgMerchantNo, Pageable pageable) {

        // Get the list of items
        Page<LoyaltyProgram> loyaltyProgramPage = loyaltyProgramRepository.findByPrgMerchantNo(prgMerchantNo,pageable);

        // Return the page
        return loyaltyProgramPage;

    }

    @Override
    public Page<LoyaltyProgram> findByPrgMerchantNoAndPrgProgramNameLike(Long prgMerchantNo, String query, Pageable pageable) {

        // Get the list of items
        Page<LoyaltyProgram> loyaltyProgramPage =  loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramNameLike(prgMerchantNo,query,pageable);

        // Return the page
        return loyaltyProgramPage;

    }

    @Override
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgStatus(Long prgMerchantNo, int prgStatus) {

        // Get the list of data
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramRepository.findByPrgMerchantNoAndPrgStatus(prgMerchantNo,prgStatus);

        // Return the loyaltyprogram list
        return loyaltyProgramList;

    }

    @Override
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgStatusAndPrgProgramNameLike(Long prgMerchantNo, int prgStatus,String query) {

        // Get the list of data
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramRepository.findByPrgMerchantNoAndPrgStatusAndPrgProgramNameLike(prgMerchantNo, prgStatus, query);

        // Return the loyaltyprogram list
        return loyaltyProgramList;

    }

    @Override
    public boolean isDuplicateProgramNameExisting(LoyaltyProgram loyaltyProgram) {

        // Get the loyaltyProgram information
        LoyaltyProgram exLoyaltyProgram = loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramName(loyaltyProgram.getPrgMerchantNo(), loyaltyProgram.getPrgProgramName());

        // If the brnId is 0L, then its a new loyaltyProgram so we just need to check if there is ano
        // ther loyaltyProgram code
        if ( loyaltyProgram.getPrgProgramNo() == null || loyaltyProgram.getPrgProgramNo() == 0L ) {

            // If the loyaltyProgram is not null, then return true
            if ( exLoyaltyProgram != null ) {

                return true;

            }

        } else {

            // Check if the loyaltyProgram is null
            if ( exLoyaltyProgram != null && loyaltyProgram.getPrgProgramNo().longValue() != exLoyaltyProgram.getPrgProgramNo().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public LoyaltyComputation getLoyaltyComputation(LoyaltyProgram loyaltyProgram) {

        // Check if the loyaltyProgram computation source is LoyaltyExtension,
        // in that case, return the LoyaltyProgramDroolsBased
        if( loyaltyProgram.getPrgComputationSource() == LoyaltyComputationSource.LOYALTY_EXTENSION ) {

            return new LoyaltyComputationDroolsBased(droolsEngineService);

        }

        // Check the program driver and then return the computation type
        if ( loyaltyProgram.getPrgProgramDriver() == LoyaltyProgramDriver.TRANSACTION_AMOUNT ) {

            return new LoyaltyComputationTransactionAmount();

        } else if ( loyaltyProgram.getPrgProgramDriver() ==  LoyaltyProgramDriver.PRODUCT_BASED) {

            return new LoyaltyComputationProductBased(loyaltyProgramSkuService,saleSKUService,productService, customerService, accountBundlingUtils,customerSubscriptionService);

        } else if ( loyaltyProgram.getPrgProgramDriver() == LoyaltyProgramDriver.PRODUCT_DISCOUNT_BASED) {

            return new LoyaltyComputationProductDiscountBased(saleSKUService);

        }

        // If nothing found, then return null
        return null;

    }

    @Override
    public boolean updateLoyaltyProgramStatus(Long prgProgramNo, Integer prgStatus, Long merchantNo, Long userNo) throws InspireNetzException {

        // Get the program information
        LoyaltyProgram loyaltyProgram = findByPrgProgramNo(prgProgramNo);

        // Check if the loyaltyProgram is found
        if ( loyaltyProgram == null || loyaltyProgram.getPrgProgramNo() == null) {

            // Log the response
            log.info("updateLoyaltyProgramStatus - Response : No loyalty program information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( loyaltyProgram.getPrgMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("updateLoyaltyProgramStatus - Response : You are not authorized to view the loyalty program");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Set the updated by field to the user number of the user
        loyaltyProgram.setUpdatedBy(Long.toString(userNo));

        // Set the status to the status set by user
        loyaltyProgram.setPrgStatus(prgStatus);


        // Saving the loyalty program object
        log.info("updateLoyaltyProgramStatus - Saving object : " + loyaltyProgram.toString());

        // SAve the program
        saveLoyaltyProgram(loyaltyProgram);



        // Return true
        return true;

    }

    @Override
    public List<LoyaltyProgramProductBasedItem> getLoyaltyProgramProductBasedItems(String filter, String query,Pageable pageable) {

        // Data to return
        List<LoyaltyProgramProductBasedItem> loyaltyProgramProductBasedItemList = new ArrayList<>(0);

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        //get the user location
        Long userLocation = authSessionUtils.getUserLocation();

        //if user is admin then set userLocation as 0
        if(authSessionUtils.getUserType() == UserType.MERCHANT_ADMIN){

            userLocation = 0L;
        }

        // Check the filter and get the details
        switch (filter) {
            case "brand":

                // Get the page
                Page<Brand> brandPage = brandService.findByBrnMerchantNoAndBrnNameLike(merchantNo, "%" + query + "%", pageable);

                // Get the list from the brand
                List<Brand> brandList = brandPage.getContent();

                // Convert to LoyaltyProgramProductBasedItem
                if (brandList != null && !brandList.isEmpty()) {

                    // Go through the list
                    for (Brand brand : brandList) {

                        // Create the object
                        LoyaltyProgramProductBasedItem loyaltyProgramProductBasedItem = new LoyaltyProgramProductBasedItem();

                        loyaltyProgramProductBasedItem.setCode(brand.getBrnCode());

                        loyaltyProgramProductBasedItem.setName(brand.getBrnName());

                        loyaltyProgramProductBasedItem.setType(Integer.toString(LoyaltyProgramSkuType.BRAND));

                        loyaltyProgramProductBasedItemList.add(loyaltyProgramProductBasedItem);

                    }

                }

                break;
            case "product":

                // Get the page for the product
                Page<Product> productPage = productService.findByPrdMerchantNoAndPrdLocationAndPrdNameLike(merchantNo,userLocation, "%" + query + "%", pageable);

                // Get the List
                List<Product> productList = productPage.getContent();

                // Convert to LoyaltyProgramProductBasedItem
                if (productList != null && !productList.isEmpty()) {

                    // Go through the list
                    for (Product product : productList) {


                        // Create the object
                        LoyaltyProgramProductBasedItem loyaltyProgramProductBasedItem = new LoyaltyProgramProductBasedItem();

                        loyaltyProgramProductBasedItem.setCode(product.getPrdCode());

                        loyaltyProgramProductBasedItem.setName(product.getPrdName());

                        loyaltyProgramProductBasedItem.setType(Integer.toString(LoyaltyProgramSkuType.PRODUCT));

                        loyaltyProgramProductBasedItemList.add(loyaltyProgramProductBasedItem);

                    }

                }

                break;
            case "productcategory":

                // Get the page for the product category
                Page<ProductCategory> productCategoryPage = productCategoryService.findByPcyMerchantNoAndPcyNameLike(merchantNo, "%" + query + "%", pageable);

                // GEt the Liset
                List<ProductCategory> productCategoryList = productCategoryPage.getContent();

                // Convert to LoyaltyProgramProductBasedItem
                if (productCategoryList != null && !productCategoryList.isEmpty()) {

                    // Go through the list
                    for (ProductCategory productCategory : productCategoryList) {

                        // Create the object
                        LoyaltyProgramProductBasedItem loyaltyProgramProductBasedItem = new LoyaltyProgramProductBasedItem();

                        loyaltyProgramProductBasedItem.setCode(productCategory.getPcyCode());

                        loyaltyProgramProductBasedItem.setName(productCategory.getPcyName());

                        loyaltyProgramProductBasedItem.setType(Integer.toString(LoyaltyProgramSkuType.PRODUCT_CATEGORY));

                        loyaltyProgramProductBasedItemList.add(loyaltyProgramProductBasedItem);

                    }

                }

                break;
        }


        // Return the list
        return loyaltyProgramProductBasedItemList;

    }

    @Override
    public LoyaltyProgram saveLoyaltyProgramDataFromParams(Map<String,Object> params) throws InspireNetzException {

        // The base object
        LoyaltyProgram baseObject = null;

        // Set of LoyaltySku to delete
        Set<LoyaltyProgramSku> delSet = null;

        // Get the LoyaltyProgram object
        LoyaltyProgram loyaltyProgramParamObj = getLoyaltyProgramObjectFromParams(params);

        // Check if the loyaltyProgram has got the id set
        if ( params.containsKey("prgProgramNo") && params.get("prgProgramNo") != null) {

            // Fetch the original value and set the fields
            baseObject = findByPrgProgramNo(loyaltyProgramParamObj.getPrgProgramNo());


            // Check if the object exists, otherwise throw not found error
            if ( baseObject == null ) {

                // Log the information
                log.info("saveLoyaltyProgramDataFromParams -> No loyalty program information found");

                // Throw new InspireNetzException
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }


            // Check if the merchant number of the base object is same as the
            // one in the session
            if ( baseObject.getPrgMerchantNo().longValue() != authSessionUtils.getMerchantNo().longValue() ){

                // Log the information
                log.info("saveLoyaltyProgramDataFromParams ->  Merchant is not authorized" );

                // Throw new InspirenetzException
                throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

            }

            // Return the baseObject
            baseObject.getLoyaltyProgramSkuSet().toString();

            // If the baseOBject loyaltyprogram sku set is not null, then
            // get the delSet
            if ( baseObject.getLoyaltyProgramSkuSet() != null ) {

                // Get the set
                delSet = loyaltyProgramSkuService.getRemovedLoyaltySku(baseObject.getLoyaltyProgramSkuSet(), loyaltyProgramParamObj.getLoyaltyProgramSkuSet());

            }

        }


        // Save the loyaltyProgramParamObj
        loyaltyProgramParamObj = saveLoyaltyProgram(loyaltyProgramParamObj);

        // If saved successfully , and baseObject is not null, check if there are
        // anything to delete
        if ( loyaltyProgramParamObj == null || loyaltyProgramParamObj.getPrgProgramNo() == null ) {

            // Log the information
            log.info("saveLoyaltyProgramDataFromParams -> Saving of loyalty program failed");

            // throw operation failed exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // If there are sku items removed, then delete them
        if ( delSet != null && !delSet.isEmpty() ){

            // Delete the objects
            loyaltyProgramSkuService.deleteLoyaltyProgramSkus(delSet);

        }


        // Return the loyaltyProgram
        return loyaltyProgramParamObj;

    }

    @Override
    public boolean validateAndDeleteLoyaltyProgram(Long prgProgramNo) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_LOYALTY_PROGRAM);

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the loyaltyProgram information
        LoyaltyProgram loyaltyProgram = findByPrgProgramNo(prgProgramNo);

        // If no data found, then set error
        if ( loyaltyProgram == null ) {

            // Log the response
            log.info("validateAndDeleteLoyaltyProgram - Response : No loyaltyProgram information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( loyaltyProgram.getPrgMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("validateAndDeleteLoyaltyProgram - Response : You are not authorized to delete the loyaltyProgram");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the loyaltyProgram and set the retData fields
        deleteLoyaltyProgram(prgProgramNo);

        // Return true
        return true;

    }

    @Override
    public LoyaltyProgram getLoyaltyProgramInfo(Long prgProgramNo) throws InspireNetzException {

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the program information
        LoyaltyProgram loyaltyProgram = findByPrgProgramNo(prgProgramNo);

        // Check if the loyaltyProgram is found
        if ( loyaltyProgram == null || loyaltyProgram.getPrgProgramNo() == null) {

            // Log the response
            log.info("getLoyaltyProgramInfo - Response : No brand information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }




        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( loyaltyProgram.getPrgMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getLoyaltyProgramInfo - Response : You are not authorized to view the brand");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Get the loyaltyProgramSkuSet
        loyaltyProgram.getLoyaltyProgramSkuSet().toString();


        // Return the object
        return loyaltyProgram;

    }

    /**
     * Function to get the LoyaltyProgram object from the params passed
     * The params are expected to be Map<String,Object>
     *
     *
     * @param params - Map of Map<String,Object>
     * @return       - LoyaltyProgram with the LoyaltyProgramSku attached as OneToMany relation
     */
    protected LoyaltyProgram getLoyaltyProgramObjectFromParams(Map<String,Object> params) {


        // Get the attributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = new AttributeExtendedEntityMap();

        // Add the params to the attributeExtendedEntityMap
        attributeExtendedEntityMap.putAll(params);



        // Create the object
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();


        // Parse the object into map
        loyaltyProgram = (LoyaltyProgram)  fromAttributeExtensionMap(loyaltyProgram,attributeExtendedEntityMap,AttributeExtensionMapType.ALL);



        // Check if the params contains the loyaltyProgramSkuSet
        if ( params.containsKey("loyaltyProgramSkuSet") ) {

            // Get the loyaltyProgramSkuData
            List<AttributeExtendedEntityMap> loyaltyProgramSkuList = attributeExtensionUtils.buildAttributeExtendedEntityMapFromMapList((List<Map<String,Object>>) params.get("loyaltyProgramSkuSet"));

            // Check if set exists
            if ( loyaltyProgramSkuList != null && !loyaltyProgramSkuList.isEmpty() ) {

                // Get the skuSets if any
                Set<LoyaltyProgramSku> loyaltyProgramSkuSet = loyaltyProgramSkuService.getLoyaltyProgramSkuFromParams(loyaltyProgramSkuList);

                // Add to the LoyaltyProgram
                loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);

            }

        }

        // Return the loyaltyprogram object
        return loyaltyProgram;

    }

    @Override
    public Page<LoyaltyProgram> searchLoyaltyPrograms(String filter, String query, Pageable pageable) {

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the data
        Page<LoyaltyProgram> loyaltyProgramPage = null;

        // Check if the filter and query is 0
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the list of all the items
            loyaltyProgramPage =  findByPrgMerchantNo(merchantNo,pageable);


        } else if (filter.equals("name") ) {

            // Get the item matching the name of the loyalty program
            loyaltyProgramPage = findByPrgMerchantNoAndPrgProgramNameLike(merchantNo,"%"+query+"%",pageable);

        }


        // return the page
        return loyaltyProgramPage;

    }

    @Override
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgProgramDriver(Long prgMerchantNo,Integer prgProgramDriver) {

        // Get the List of LoyaltyPrograms
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramDriver(prgMerchantNo,prgProgramDriver);

        // Iterate through the loop and get the sku
        for ( LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

            // Lazily load the sku set
            loyaltyProgram.getLoyaltyProgramSkuSet().toString();

        }

        // Return the list
        return loyaltyProgramList;

    }


    @Override
    public AttributeExtendedEntityMap toAttributeExtensionMap(Object obj, Integer attributeExtensionMapType) {

        // Get the LoyaltyProgram object
        LoyaltyProgram loyaltyProgram = (LoyaltyProgram) obj;

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = attributeExtensionUtils.getAttributeExtensionMapForObject(loyaltyProgram,loyaltyProgram.getLoyaltyProgramExtensionSet(),attributeExtensionMapType);

        // Return the attributeExtendedEntityMap object
        return attributeExtendedEntityMap;

    }

    @Override
    public Object fromAttributeExtensionMap(Object obj, AttributeExtendedEntityMap attributeExtendedEntityMap, Integer attributeExtensionMapType) {

        // Get the attributes map
        AttributeMap attributeMap = attributeService.getAttributesMapByName(0);

        // Create the LoyaltyProgram object
        LoyaltyProgram loyaltyProgram = (LoyaltyProgram) attributeExtensionUtils.createEntityFromAttributeExtensionMap(obj,attributeMap,attributeExtendedEntityMap,attributeExtensionMapType,this);

        // return the LoyaltyProgram object
        return loyaltyProgram;

    }

    @Override
    public void setExtFieldValue(Object obj, Attribute attribute, String value) {

        // Get the loyaltyProgram Object
        LoyaltyProgram loyaltyProgram = (LoyaltyProgram) obj;

        // Check if the field is set
        boolean isSet = attributeExtensionUtils.setExtFieldValue(loyaltyProgram.getLoyaltyProgramExtensionSet(),attribute,value);


        // Check if the isSet is true, otherwise , we need to set the data in the extFields
        if ( !isSet ) {

            // If the loyaltyProgramInfoSet is null, then intialize it
            if ( loyaltyProgram.getLoyaltyProgramExtensionSet() == null ) {

                loyaltyProgram.setLoyaltyProgramExtensionSet(new HashSet<LoyaltyProgramExtension>(0));

            }

            // Create the LoyaltyProgramExtension object
            LoyaltyProgramExtension loyaltyProgramExtension = new LoyaltyProgramExtension();

            // Set the attribute id
            loyaltyProgramExtension.setAttrId(attribute.getAtrId());

            // Set the value
            loyaltyProgramExtension.setAttrValue(value.toString());

            // Set the attribute
            loyaltyProgramExtension.setAttribute(attribute);

            // Add to the set
            loyaltyProgram.getLoyaltyProgramExtensionSet().add(loyaltyProgramExtension);

        }

    }

    @Override
    public String getExtFieldValue(Object obj, Attribute attribute) {

        // Get the LoyaltyProgram object
        LoyaltyProgram loyaltyProgram = (LoyaltyProgram)obj;

        // Return the attribute
        return attributeExtensionUtils.getExtFieldValue(loyaltyProgram.getLoyaltyProgramExtensionSet(), attribute);


    }




    @Override
    public LoyaltyProgram saveLoyaltyProgram(LoyaltyProgram loyaltyProgram) throws InspireNetzException {


        // Set the merchantNo to be the merchant number from session
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo into the object before saving so that
        // the object is always saved under the merchant user in session
        loyaltyProgram.setPrgMerchantNo(merchantNo);


        // Create the BindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(loyaltyProgram,"loyaltyProgram");

        // Create the Validator
        LoyaltyProgramValidator validator = new LoyaltyProgramValidator();

        // Validate using the LoyaltyProgramValidator object
        validator.validate(loyaltyProgram,result);



        // Check if there are validation errors for the purchse object
        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(result);

            // Log the response
            log.info("saveLoyaltyProgram - Response : Invalid Input - " + messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }



        // Check if the data exists
        boolean isExist = isDuplicateProgramNameExisting(loyaltyProgram);

        // Check the boolean value and if the entry already exists, then we need to show
        // the message as already exists
        if ( isExist ) {

            // Log the response
            log.info("saveLoyaltyProgram - Response : Loyalty program entry already exists");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        // Set the auditDetails
        if ( loyaltyProgram.getPrgProgramNo() == null ) {

            loyaltyProgram.setCreatedBy(authSessionUtils.getUserNo().toString());

        } else {

            loyaltyProgram.setUpdatedBy(authSessionUtils.getUserNo().toString());

        }


        // Save the loyaltyProgram
        loyaltyProgram = loyaltyProgramRepository.save(loyaltyProgram);

        // Return the loyaltyProgram object
        return loyaltyProgram;

    }

    @Override
    public boolean deleteLoyaltyProgram(Long prgProgramNo) {

        // Delete the loyaltyProgram
        loyaltyProgramRepository.delete(prgProgramNo);

        // Return true
        return true;
    }

    @Override
    public LoyaltyProgram validateAndSaveLoyaltyProgramDataFromParams(Map<String, Object> params) throws InspireNetzException {
        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_THE_LOYALTY_PROGRAM);

        return saveLoyaltyProgramDataFromParams(params);
    }


    @Override
    public List<LoyaltyProgram> searchLoyaltyProgramsForCustomer(String usrLoginId,Long merchantNo,String filter, String query) {

        // Variable holding the data
        List<LoyaltyProgram> loyaltyProgramPage =new ArrayList<>();

        List<LoyaltyProgram> loyaltyPrograms=new ArrayList<>();

        //User Login Id
        User user =userService.findByUsrLoginId(usrLoginId);


        if(user==null||user.getUsrUserNo()==null){

            //log the error
            log.error("No User Information Found");

            //throw exception
            return loyaltyPrograms;
        }

        List<Customer> customers=customerService.getUserMemberships(merchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the error
            log.error("No Customer Information Found");

            return loyaltyPrograms;

        }




        for(Customer customer:customers){


            // Check if the filter and query is 0
            if ( filter.equals("0") && query.equals("0") ) {

                // Get the list of all the items
                loyaltyProgramPage =  findByPrgMerchantNoAndPrgStatus(customer.getCusMerchantNo(), LoyaltyProgramStatus.ACTIVE);


            } else if (filter.equals("name") ) {

                // Get the item matching the name of the loyalty program
                loyaltyProgramPage = findByPrgMerchantNoAndPrgStatusAndPrgProgramNameLike(customer.getCusMerchantNo(), LoyaltyProgramStatus.ACTIVE, "%" + query + "%");

            }

            loyaltyPrograms.addAll(Lists.newArrayList((Iterable<LoyaltyProgram>) loyaltyProgramPage));
        }


        // return the page
        return loyaltyPrograms;

    }

}
