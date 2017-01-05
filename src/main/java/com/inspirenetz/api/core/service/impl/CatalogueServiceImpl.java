package com.inspirenetz.api.core.service.impl;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.loyaltyengine.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CatalogueRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CatalogueResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.hibernate.mapping.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class CatalogueServiceImpl extends BaseServiceImpl<Catalogue> implements CatalogueService {

    private static Logger log = LoggerFactory.getLogger(CatalogueServiceImpl.class);

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    RedemptionService redemptionService;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;

    @Autowired
    RedemptionVoucherService redemptionVoucherService;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private ProductService productService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    CustomerService customerService;

    @Autowired
    Environment environment;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    CodedValueService codedValueService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    DrawChanceService drawChanceService;

    @Autowired
    CatalogueFavoriteService catalogueFavoriteService;

    @Autowired
    RedemptionVoucherSourceService redemptionVoucherSourceService;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    PartnerCatalogueService partnerCatalogueService;

    @Autowired
    MerchantPartnerTransactionService merchantPartnerTransactionService;

    @Autowired
    UserService userService;

    @Autowired
    MerchantSettlementService merchantSettlementService;

    public CatalogueServiceImpl() {

        super(Catalogue.class);

    }


    @Override
    protected BaseRepository<Catalogue,Long> getDao() {
        return catalogueRepository;
    }

    @Override
    public Catalogue findByCatProductNo(Long catProductNo) {

        // Get the catalogues
        Catalogue catalogue = catalogueRepository.findByCatProductNo(catProductNo);

        // Return the catalogue
        return catalogue;


    }
    @Override
    public Catalogue findByCatProductNoAndCatDescriptionLike(Long catProductNo,String query) {

        // Get the catalogues
        Catalogue catalogue = catalogueRepository.findByCatProductNoAndCatDescriptionLike(catProductNo, query);

        // Return the catalogue
        return catalogue;


    }

    @Override
    public Page<Catalogue> findByCatMerchantNo(Long catMerchantNo,Pageable pageable) {


        //for get page number and page size from  pageable request and add sorting parameter for displaying last created one into top
        int page = pageable.getPageNumber();

        int pageSize =pageable.getPageSize();

        //create request with sorting parameter 
        Pageable newPageableRequest = new PageRequest(page,pageSize, new Sort(Sort.Direction.DESC,"catProductNo"));

        Page<Catalogue> catalogueList = catalogueRepository.findByCatMerchantNo(catMerchantNo, newPageableRequest);

        // Return the list
        return catalogueList;

    }

    @Override
    public Catalogue findByCatProductCodeAndCatMerchantNo(String catProductCode,Long catMerchantNo) {

        // Get the catalogue
        Catalogue catalogue = catalogueRepository.findByCatProductCodeAndCatMerchantNo(catProductCode, catMerchantNo);

        // Return the catalogue object
        return catalogue;

    }

    @Override
    public Page<Catalogue> findByCatMerchantNoAndCatDescriptionLike(Long catMerchantNo, String query, Pageable pageable) {

        // Get the data
        Page<Catalogue> cataloguePage = catalogueRepository.findByCatMerchantNoAndCatDescriptionLike(catMerchantNo, query, pageable);

        // Return page
        return cataloguePage;

    }

    @Override
    public Page<Catalogue> searchCatalogueByCurrencyAndCategory(Long catMerchantNo, Long catRewardCurrencyId, Integer catCategory, String loyaltyId,Integer channel, Pageable pageable) throws InspireNetzException {

        // Get the Catalogue page
        Page<Catalogue> cataloguePage = catalogueRepository.searchCatalogueByCurrencyAndCategory(catMerchantNo, catRewardCurrencyId, catCategory, pageable);

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,catMerchantNo);

        List<Catalogue> validCatalogues = new ArrayList<>(0);

        //check whether the catalogue items are valid for the customer
        for(Catalogue catalogue : cataloguePage){

            try{

                CatalogueRedemptionItemRequest catalogueRequest = redemptionService.getRedemptionRequestObject(customer, catalogue);

                catalogueRequest.setChannel(channel);

                //check the item is valid for the customer
                boolean isValidItem = redemptionService.checkGeneralRulesValidity(catalogueRequest);

                //if item is valid , add it to the return list
                if(isValidItem){

                    validCatalogues.add(catalogue);

                }


            }catch (InspireNetzException e){

                //do nothing
            }


        }

        cataloguePage = new PageImpl<>(validCatalogues);

        // Return the page
        return cataloguePage;

    }

    @Override
    public CatalogueRedemption getCatalogueRedemption(Catalogue catalogue) throws InspireNetzException {

        //get the catalogue Redemption type
        Integer redemptionType = catalogue.getCatRedemptionType();

        //Return the Implementation respective implementation of the catalogue redemption class
        if(redemptionType == CatalogueRedemptionType.VOUCHER_BASED){

            return new CatalogueRedemptionVoucherType(redemptionService, redemptionMerchantService, redemptionVoucherService,userMessagingService,this,generalUtils,merchantSettingService);

        }  else if(redemptionType == CatalogueRedemptionType.DYNAMIC_VOUCHER_SOURCE){

            return new CatalogueRedemptionDynamicVoucher(redemptionService, redemptionMerchantService, redemptionVoucherService,userMessagingService,this,generalUtils,redemptionVoucherSourceService);

        } else if(redemptionType == CatalogueRedemptionType.PRODUCT) {

            return new CatalogueRedemptionProduct(redemptionService,userMessagingService,this,generalUtils);

        }else{

            //log error
            log.error("Catalogue redemption : Invalid Redemption Type");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }
    }


    @Override
    public boolean isDuplicateCatalogueExisting(Catalogue catalogue) {

        // Get the catalogue information
        Catalogue exCatalogue = catalogueRepository.findByCatProductCodeAndCatMerchantNo(catalogue.getCatProductCode(), catalogue.getCatMerchantNo());

        // If the brnId is 0L, then its a new catalogue so we just need to check if there is ano
        // ther catalogue code
        if ( catalogue.getCatProductNo() == null || catalogue.getCatProductNo() == 0L ) {

            // If the catalogue is not null, then return true
            if ( exCatalogue != null ) {

                return true;

            }

        } else {

            // Check if the catalogue is null
            if ( exCatalogue != null && catalogue.getCatProductNo().longValue() != exCatalogue.getCatProductNo().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
        
    }



    @Override
    public Catalogue saveCatalogue(Catalogue catalogue) {

        // Save the catalogue
        catalogue = catalogueRepository.save(catalogue);

        // Return the catalogue object
        return catalogue;

    }

    @Override
    public boolean deleteCatalogue(Long catProductNo) {

        // Delete the catalogue
        catalogueRepository.delete(catProductNo);

        // Return true
        return true;
    }

    @Override
    public Catalogue validateAndSaveCatalogue(Catalogue catalogue) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CATALOGUE);

        return saveCatalogue(catalogue);
    }

    @Override
    public boolean validateAndDeleteCatalogue(Long catProductNo) throws InspireNetzException {
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_CATALOGUE);

        return deleteCatalogue(catProductNo);

    }

    @Override
    public List<Catalogue> getCatalogueListCompatible(String category, String loyaltyId, Long merchantNo,String query) throws InspireNetzException {


        List<Catalogue> catalogueList=new ArrayList<>();

        //checking the query 0 or not
        String queryCheck =query.equals("0")?"0":"%"+query+"%";
//
//        Page<Catalogue> catalogues = null;
//
//        //check all reward currencies and get the catalogue items
//        for(CustomerRewardBalance customerRewardBalance : customerRewardBalances){
//
//            //get the reward currency id
//            Long rwdCurrencyId = customerRewardBalance.getCrbRewardCurrency();
//
//            //get the catalogue list
//            catalogues = searchCatalogueByCurrencyAndCategory(merchantNo, rwdCurrencyId, category, loyaltyId, pageable);
//
//            //add the list to the main list
//            catalogueList.addAll(catalogues.getContent());
//
//        }

            //check prd category null or not if not null
           if(category.equals("0")){

               catalogueList= catalogueRepository.searchCatalogueByCategoryAndCatDescription(merchantNo,Integer.parseInt(category),queryCheck);

           }else{

               //find coded value
               CodedValue categoryValue =codedValueService.findByCdvCodeLabel(category);

               if(categoryValue !=null){

                   catalogueList= catalogueRepository.searchCatalogueByCategoryAndCatDescription(merchantNo,categoryValue.getCdvCodeValue(),queryCheck);
               }


           }

           //for checking catalogue is valid for customer
            Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            List<Catalogue> validCatalogues = new ArrayList<>(0);
            //check whether the catalogue items are valid for the customer
            for(Catalogue catalogue : catalogueList){

                try{

                    CatalogueRedemptionItemRequest catalogueRequest = redemptionService.getRedemptionRequestObject(customer, catalogue);

                    //check the item is valid for the customer
                    boolean isValidItem = redemptionService.checkGeneralRulesValidity(catalogueRequest);

                    //if item is valid , add it to the return list
                    //if(isValidItem){

                        validCatalogues.add(catalogue);

                   // }


                }catch (InspireNetzException e){

                    //do nothing
                }


            }


        return validCatalogues;
    }

    @Override
    public List<Catalogue> getCatalogueFavourites(String loyaltyId, Long merchantNo) {

        //get the favourites of the customer
        List<CatalogueFavorite> catalogueFavorites = catalogueFavoriteService.findByCafLoyaltyId(loyaltyId);

        List<Catalogue> catalogues = new ArrayList<>();

        Catalogue catalogue = null;

        //get the catalogue item for each favourite item
        for(CatalogueFavorite catalogueFavorite : catalogueFavorites){

            if(catalogueFavorite.getCafFavoriteFlag() == IndicatorStatus.YES){

                catalogue = findByCatProductNo(catalogueFavorite.getCafProductNo());

                if(catalogue != null){

                    catalogues.add(catalogue);
                }
            }

        }

        return catalogues;
    }

    @Override
    public List<Catalogue> getCatalogueFavourites(String loyaltyId, Long merchantNo,String query) {

        //get the favourites of the customer
        List<CatalogueFavorite> catalogueFavorites = catalogueFavoriteService.findByCafLoyaltyIdAndCafMerchantNoAndCafFavoriteFlag(loyaltyId, merchantNo,IndicatorStatus.YES);

        List<Catalogue> catalogues = new ArrayList<>();

        Catalogue catalogue = null;

        //get the catalogue item for each favourite item
        for(CatalogueFavorite catalogueFavorite : catalogueFavorites){


                catalogue = findByCatProductNoAndCatDescriptionLike(catalogueFavorite.getCafProductNo(), query);

                if(catalogue != null){

                    catalogues.add(catalogue);
                }

        }

        return catalogues;
    }



    /**
     * @purpose :find expiry date of redemption voucher
     * @param catalogue
     * @return date
     * @date 06-02-2015
     *
     */

    @Override
    public Date getExpiryDateForVoucher(Catalogue catalogue,Date rvrCreatedDate) throws InspireNetzException {

        //check the catalogue item is present or not
        if(catalogue ==null){

            log.info("CatalogueServiceImpl ->getExpiryDateForVoucher::Received Catalogue is null");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CATALOGUE);
        }

        //declare date
        Date date =null;

        //check expiry options
        Integer redemptionVoucherExpiryOption = catalogue.getCatRedemptionVoucherExpiry()==null?0:catalogue.getCatRedemptionVoucherExpiry();

        if(redemptionVoucherExpiryOption ==0){

            log.info("CatalogueServiceImpl ->getExpiryDateForVoucher::Received Catalogue is doesn't have any expiry option");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_VOUCHER_EXPIRY_OPTION);
        }

        //check expiry option
        if(redemptionVoucherExpiryOption == RedemptionVoucherExpiryOption.EXPIRYDATE){

            //get the expiry date
            date=catalogue.getCatRedemptionVoucherExpiryDate();

            if(date ==null){

                log.info("CatalogueServiceImpl ->getExpiryDateForVoucher::Invalid date");

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_VOUCHER_EXPIRY_DATE);
            }

            return date;

        }else if(redemptionVoucherExpiryOption == RedemptionVoucherExpiryOption.EXPIRYAFTER){



            //first to convert sql date to calender object
            Calendar calendarDate = Calendar.getInstance();

            //set created date to calendar
            calendarDate.setTime(rvrCreatedDate);

            //find day for expiry
            Integer daysForExpiry = catalogue.getCatRedemptionVoucherExpiryDateAfter()==null?0:catalogue.getCatRedemptionVoucherExpiryDateAfter();

            //add days for calender object
            calendarDate.add(Calendar.DATE,daysForExpiry);

            //convert util date to sql date
            Date expiryDate= new Date(calendarDate.getTimeInMillis());

            return  expiryDate;

        }

        return null;
   


    }

    @Override
    public Page<Catalogue> getPublicCatalogues(Long catMerchantNo,Integer catCategory,Integer catChannel, String query, Pageable pageable) {

        //Page to return result
        Page<Catalogue> cataloguePage;

        List<Catalogue> catalogues=new ArrayList<>();

        //check if default merchant no then set it zero
       /* if(catMerchantNo==generalUtils.getDefaultMerchantNo()){

            catMerchantNo=0L;

        }*/

        //check if merchantNo is provided or not
        if(catMerchantNo==null ||catMerchantNo.longValue()==0){


            catalogues=catalogueRepository.listCatalogueByCatCategoryAndCatDescriptionLike(catCategory,"%"+query+"%");


        }else{

            catalogues=catalogueRepository.listCatalogueByCatMerchantNoAndCatCategoryAndCatDescriptionLike(catMerchantNo,catCategory,"%"+query+"%");

        }

        List<Catalogue> validCatalogues = new ArrayList<>(0);

        //check whether the catalogue items are valid
        for(Catalogue catalogue : catalogues){

            //check the item is valid
            boolean isValidItem = isCatalogueValidForChannelAndCustomerType(catalogue,catChannel,0);

            //if item is valid , add it to the return list
            if(isValidItem){

                validCatalogues.add(catalogue);

            }

        }

        //get reward currency name
        for(Catalogue catalogue:validCatalogues){

            if(catalogue.getCatRewardCurrencyId()!=null){

                //get reward currency name by merchantNo and reward currency id
                RewardCurrency rewardCurrency=rewardCurrencyService.findByRwdCurrencyId(catalogue.getCatRewardCurrencyId());

                //set reward currency name
                catalogue.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
            }

            Merchant merchant=merchantService.findByMerMerchantNo(catalogue.getCatMerchantNo());

            if(merchant!=null){

                catalogue.setMerMerchantName(merchant.getMerMerchantName());
            }

            if(catalogue.getCatCategory()!=null){

                CodedValue codedValue=codedValueService.findByCdvIndexAndCdvCodeValue(CodedValueIndex.CATALOGUE_PRODUCT_CATEGORY,catalogue.getCatCategory());

                if(codedValue!=null){

                    catalogue.setCatCategoryName(codedValue.getCdvCodeLabel());

                }else{

                    catalogue.setCatCategoryName("");
                }
            }

        }
        cataloguePage = new PageImpl<>(validCatalogues,pageable,validCatalogues.size());


        return cataloguePage;
    }

    @Override
    public boolean isCatalogueValidForChannelAndCustomerType(Catalogue catalogue,Integer catChannel,Integer catCustomerType) {

        //get the allowed channels for the catalogue item
        String catChannels= catalogue.getCatChannelValues();

        if(catChannels !=null){

            //check whether the channel is valid or not
            boolean isValidChannel = generalUtils.isTokenizedValueExists(catChannels,",",catChannel+"");

            if(!isValidChannel){

                return false;
            }
        }

        /*Check current date is in between the prescribed time limit of catalogue redemption*/

        //Get the current date
        Date currentDate = new Date(new java.util.Date().getTime());

        if(catalogue.getCatStartDate() == null || catalogue.getCatEndDate() == null ){

            return false;

        }

        // Check if the date is valid
        if ( currentDate.compareTo(catalogue.getCatStartDate()) < 0 ||
                currentDate.compareTo(catalogue.getCatEndDate()) > 0 ) {

            return false;

        }

        //check for stock availability
        if(catalogue.getCatAvailableStock() <= 0){

            return false;

        }

        //check for customer type
        if(catCustomerType!=0&&catalogue.getCatCustomerType()!=catCustomerType){

            return false;
        }

        //if all above check,return valid
        return true;
    }

    @Override
    public Page<Catalogue> listCataloguesUser(Long catMerchantNo,String usrLoginId,String filter, String query, String categories, String merchants, Integer channel, Pageable pageable) throws InspireNetzException{


        //CatalogueList
        Page<Catalogue> catalogues = null;

        //CatalogueList
        List<Catalogue> catalogueList = new ArrayList<>(0) ;

        List<Catalogue> favourites = new ArrayList<>(0) ;

        //CatalogueList
        List<Catalogue> validCatalogues = new ArrayList<>(0);

        Set<Catalogue> validCatalogueSet=new HashSet<>();


        //Filtered catalogue
        List<Catalogue> filteredCatalogue=new ArrayList<>();

        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);

        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");

            //throw exception
            return new PageImpl<>(filteredCatalogue);
        }

        //get member customers,if catMerchantNo is zero or default merchant no return all members
        List<Customer> customers=customerService.getUserMemberships(catMerchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return new PageImpl<>(filteredCatalogue);

        }

        //merchant list for search
        List<Long> merchantList=new ArrayList<Long>();

        //Array list to hold categories
        ArrayList<Integer> categoriesList=new ArrayList<>();

        //check merchantFilter specified or not
        if(merchants.equals("0")){

            for(Customer customer:customers){

               merchantList.add(customer.getCusMerchantNo());
            }


        }else{

            merchantList=generalUtils.ConvertStringToLongList(merchants, ","); 
        }

        //check category exist or not
        if(categories.equals("")){

            List<CodedValue> codedValues=codedValueService.findByCdvIndex(CodedValueIndex.CATALOGUE_PRODUCT_CATEGORY);

            for(CodedValue codedValue:codedValues){

                categoriesList.add(codedValue.getCdvCodeValue());
            }

            categoriesList.add(0);

        }else{

            categoriesList=generalUtils.ConvertStringToIntegerList(categories,",");


        }


        //get favourites for user
        if(categoriesList.contains(0)){

            for(Customer customer:customers){

                if((filter.equals("0") && query.equals("0"))|| filter.equalsIgnoreCase("name")){

                    List<Catalogue> favouriteCatalogues=getCatalogueFavourites(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),(query.equals("0")?"%%":"%"+query+"%"));

                    favourites.addAll(favouriteCatalogues);
                }

            }
        }

        //get catalogues within filter condition except
        if((filter.equals("0") && query.equals("0"))|| filter.equalsIgnoreCase("name")){

            //check categories null
            catalogues=catalogueRepository.listCataloguesByCatDescriptionAndCatCategoriesAndCatMerchants((query.equals("0")?"%%":"%"+query+"%"),categoriesList,merchantList,pageable);
        }

        //convert catalogue page into array list
        catalogueList.addAll(Lists.newArrayList((Iterable<Catalogue>) catalogues));


        //check catalogues null
        if(catalogueList==null || catalogueList.isEmpty()){

            //log the error
            log.error("No Catalogues  Found");

            //throw exception
            return new PageImpl<>(filteredCatalogue);

        }



        //iterate through customer objects to check merchant filter
        for(Customer customer:customers){

            //iterate through customer objects to check merchant filter
            for(Catalogue catalogue:catalogueList){

                try{

                    CatalogueRedemptionItemRequest catalogueRequest = redemptionService.getRedemptionRequestObject(customer, catalogue);

                    if(channel!=null && channel.intValue()!=0)
                    {
                        catalogueRequest.setChannel(channel);
                    }

                    boolean isValidItem =false;


                        //check the item is valid for the customer
                    isValidItem = redemptionService.checkGeneralRulesValidity(catalogueRequest);



                    //if item is valid , add it to the return list
                    if(isValidItem){

                    validCatalogueSet.add(catalogue);

                     }


                }catch (InspireNetzException e){

                    //do nothing
                }


            }

        }


        //convert set into list
        validCatalogues.addAll(validCatalogueSet);




        //get reward currency name and filtering
        for(Catalogue catalogue:validCatalogues){

            boolean isExist=false;

            for(Catalogue favCatalogue:favourites){

                if(catalogue.getCatProductNo()==favCatalogue.getCatProductNo()){

                    isExist=true;

                    break;
                }

            }

            if(isExist){

                continue;
            }

            if(catalogue.getCatRewardCurrencyId()!=null){

                //get reward currency name by merchantNo and reward currency id
                RewardCurrency rewardCurrency=rewardCurrencyService.findByRwdCurrencyId(catalogue.getCatRewardCurrencyId());

                //set reward currency name
                catalogue.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
            }

            Merchant merchant=merchantService.findByMerMerchantNo(catalogue.getCatMerchantNo());

            if(merchant!=null){

                catalogue.setMerMerchantName(merchant.getMerMerchantName());
            }

            filteredCatalogue.add(catalogue);

        }


        return new PageImpl<>(filteredCatalogue);
    }


    @Override
    public List<Catalogue> getCatalogueFavouritesForUser(String usrLoginId,Long merchantNo,String query) throws InspireNetzException{


        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);


        List<Catalogue> catalogueList = new ArrayList<>();

        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");

            //throw exception
            return catalogueList;
        }

        //get member customers,if catMerchantNo is zero or default merchant no return all members
        List<Customer> customers=customerService.getUserMemberships(merchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return catalogueList;

        }

        Set<Catalogue> validCatalogueSet=new HashSet<>();

        for(Customer customer:customers){

            //get the favourites of the customer
            List<Catalogue> catalogues = getCatalogueFavourites(customer.getCusLoyaltyId(), customer.getCusMerchantNo(), "%" + query + "%");

            for(Catalogue catalogue:catalogues){

                try{

                    CatalogueRedemptionItemRequest catalogueRequest = redemptionService.getRedemptionRequestObject(customer, catalogue);

                    boolean isValidItem =false;


                    //check the item is valid for the customer
                    isValidItem = redemptionService.checkGeneralRulesValidity(catalogueRequest);



                    //if item is valid , add it to the return list
                    if(isValidItem){

                        Merchant merchant=merchantService.findByMerMerchantNo(catalogue.getCatMerchantNo());

                        if(merchant!=null){

                            catalogue.setMerMerchantName(merchant.getMerMerchantName());
                        }

                        validCatalogueSet.add(catalogue);

                    }


                }catch (InspireNetzException e){

                    //do nothing
                }


            }

        }

        //convert set into list
        catalogueList.addAll(validCatalogueSet);

        //get reward currency name
        for(Catalogue catalogue:catalogueList){

            if(catalogue.getCatRewardCurrencyId()!=null){

                //get reward currency name by merchantNo and reward currency id
                RewardCurrency rewardCurrency=rewardCurrencyService.findByRwdCurrencyId(catalogue.getCatRewardCurrencyId());

                //set reward currency name
                catalogue.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
            }

        }


        return catalogueList;
    }

    @Override
    public Page<Catalogue> searchCatalogueByCurrencyAndCategoryCustomerPortal(Long merchantNo, Long rewardCurrencyId,String filter,String query, int channel, Pageable pageable) throws InspireNetzException {


        Page<Catalogue> cataloguePage =null;

        //get user number
        Long cusUserNo =authSessionUtils.getUserNo();

        //find the customer based on user number
        Customer customer =customerService.findByCusUserNoAndCusMerchantNo(cusUserNo,merchantNo);

        //check the customer null or register status 0
        if(customer ==null || customer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO){

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        // Check if the filter and query are specified
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the data for all the catalogues
           cataloguePage = searchCatalogueByCurrencyAndCategory(merchantNo, rewardCurrencyId, Integer.parseInt(query),customer.getCusLoyaltyId(), channel, pageable);

        } else if ( filter.equalsIgnoreCase("name") ) {

            // Get the data for all the catalogues
           cataloguePage = findByCatMerchantNoAndCatDescriptionLike(merchantNo, "%" + query + "%", pageable);

        } else if ( filter.equals("category") ) {

            // Get the data for all the catalogues
            cataloguePage =searchCatalogueByCurrencyAndCategory(merchantNo, rewardCurrencyId, Integer.parseInt(query), customer.getCusLoyaltyId(), channel, pageable);


        }

        //return catalogue
        return  cataloguePage;
    }

    @Override
    public Page<Catalogue> searchCatalogueByCatMerchantNoAndCatCategoryAndSortOption(String usrLoginId,Long catMerchantNo,Integer catCategory,String filter,String query,Integer sortOption,Integer channel,Pageable pageable)throws InspireNetzException
    {

        Page<Catalogue> cataloguePage =null;

        List<Customer> customers=new ArrayList<Customer>();

        List<Catalogue> catalogues=new ArrayList<>();

        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);



        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");


            throw new InspireNetzException(APIErrorCode.ERR_USER_NOT_FOUND);

        }

        customers=customerService.getUserMemberships(catMerchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);


       /* if(catMerchantNo.longValue()==0){

            customers.addAll(customerService.findByCusUserNoAndCusRegisterStatus(usrUserNo, IndicatorStatus.YES));

        }else{

            customers.add(customerService.findByCusUserNoAndCusMerchantNoAndCusRegisterStatus(usrUserNo, catMerchantNo, IndicatorStatus.YES));

        }*/

        //check the customer null or register status 0
        if(customers ==null || customers.size()==0){

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        // Check if the filter and query are specified
        if ( filter.equals("0") && query.equals("0") ) {

            query = "";

        } else if ( filter.equalsIgnoreCase("name") ) {

            query="%" + query + "%";

        }

        for(Customer customer :customers){

            List<Catalogue> catalogueList=catalogueRepository.listCatalogueByCatMerchantNoAndCatCategoryAndCatDescriptionLike(customer.getCusMerchantNo(),catCategory,query);

            //check whether the catalogue items are valid for the customer
            for(Catalogue catalogue : catalogueList){

                try{

                    CatalogueRedemptionItemRequest catalogueRequest = redemptionService.getRedemptionRequestObject(customer, catalogue);

                    if(channel!=null && channel.intValue()!=0)
                    {
                        catalogueRequest.setChannel(channel);
                    }

                    //check the item is valid for the customer
                    boolean isValidItem = redemptionService.checkGeneralRulesValidity(catalogueRequest);

                    //if item is valid , add it to the return list
                    if(isValidItem){

                        if(catalogue.getCatRewardCurrencyId()!=null){

                            //get reward currency name by merchantNo and reward currency id
                            RewardCurrency rewardCurrency=rewardCurrencyService.findByRwdCurrencyId(catalogue.getCatRewardCurrencyId());

                            if(rewardCurrency!=null){

                                //set reward currency name
                                catalogue.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

                            }
                        }

                        Merchant merchant=merchantService.findByMerMerchantNo(catalogue.getCatMerchantNo());

                        if(merchant!=null){

                            catalogue.setMerMerchantName(merchant.getMerMerchantName());
                        }

                        if(catalogue.getCatCategory()!=null){

                            CodedValue codedValue=codedValueService.findByCdvIndexAndCdvCodeValue(CodedValueIndex.CATALOGUE_PRODUCT_CATEGORY,catalogue.getCatCategory());

                            if(codedValue!=null){

                                catalogue.setCatCategoryName(codedValue.getCdvCodeLabel());

                            }else{

                                catalogue.setCatCategoryName("");
                            }
                        }



                        catalogues.add(catalogue);

                    }


                }catch (InspireNetzException e){

                    log.info("Catalogue list for user : eligibility check failed for "+catalogue);
                }


            }

        }

        BeanComparator fieldComparator = new BeanComparator("catProductNo");

        if(sortOption==CatalogueSortOptionCodedValues.RECENT)
        {
            fieldComparator=new BeanComparator("catProductNo");

            Collections.sort(catalogues,fieldComparator);

        }else if(sortOption==CatalogueSortOptionCodedValues.POINT_HIGH_TO_LOW){

            BeanComparator reverseOrderBeanComparator = new BeanComparator("catNumPoints", new ReverseComparator(new ComparableComparator()));
            Collections.sort(catalogues, reverseOrderBeanComparator);


        }else if(sortOption==CatalogueSortOptionCodedValues.POINT_LOW_TO_HIGH){

            fieldComparator=new BeanComparator("catNumPoints");

            Collections.sort(catalogues,fieldComparator);

        }else if(sortOption==CatalogueSortOptionCodedValues.ALPHABETICAL_ORDER){

            fieldComparator=new BeanComparator("catDescription",new ASCIICaseInsensitiveComparator());

            Collections.sort(catalogues,fieldComparator);

        }






        //return catalogue
        return  convertToPage(catalogues,pageable);
    }

    @Override
    public Page<Catalogue> findByCatTypeAndCatMerchantNo(Integer catType, Long merchantNo, Pageable pageable) {

        return catalogueRepository.findByCatTypeAndCatMerchantNo(catType,merchantNo,pageable);

    }

    public Page<Catalogue> convertToPage(List<Catalogue> list,Pageable pageable){

        int start=pageable.getOffset();

        int size=pageable.getPageSize();

        int end=start+size>list.size()?list.size():start+size;

        List<Catalogue> subList=new ArrayList<Catalogue>();

        try{

            if(start<end){

                subList=list.subList(start,end);



            }


        }catch (IndexOutOfBoundsException  ex){


        }catch (IllegalArgumentException  ex){


        }catch (Exception  ex){


        }

        Page<Catalogue> page=new PageImpl<>(subList,pageable,list.size());

        return page;

    }

    @Override
    public boolean updateCatalogueStockOfPartnerProduct(Long stock, Long pacId,Long merchantNo) throws InspireNetzException {

        PartnerCatalogue partnerCatalogue = partnerCatalogueService.findByPacId(pacId);

        Catalogue catalogue = findByCatPartnerProductAndCatMerchantNo(pacId,merchantNo);

        if(catalogue != null && partnerCatalogue != null && partnerCatalogue.getPacStock().intValue() > stock){

            catalogue.setCatAvailableStock(catalogue.getCatAvailableStock() + stock);

            catalogue = saveCatalogue(catalogue);

            partnerCatalogueService.deductStockFromPartnerCatalogue(pacId,stock);

            catalogue.setCatAvailableStock(stock);

            merchantPartnerTransactionService.addMerchantPartnerTransaction(catalogue);

            merchantSettlementService.addSettlementEntryForPartnerTransaction(catalogue,partnerCatalogue);

            return true;
        }

        throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_STOCK);
    }

    @Override
    public Catalogue findByCatPartnerProductAndCatMerchantNo(Long pacId, Long merchantNo) {

        return catalogueRepository.findByCatPartnerProductAndCatMerchantNo(pacId,merchantNo);

    }

}
