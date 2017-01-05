package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.MerchantStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantServiceImpl extends BaseServiceImpl<Merchant> implements MerchantService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    GeneralUtils generalUtils;


    public MerchantServiceImpl() {

        super(Merchant.class);

    }


    @Override
    protected BaseRepository<Merchant,Long> getDao() {
        return merchantRepository;
    }



    @Override
    public boolean isDuplicateMerchantExist(Merchant merchant) {

        // Get the merchant information
        List<Merchant> exMerchantList = merchantRepository.findByMerMerchantNameOrMerUrlName(merchant.getMerMerchantName(),merchant.getMerUrlName());

        // If the brnId is 0L, then its a new merchant so we just need to check if there is ano
        // ther merchant code
        if ( merchant.getMerMerchantNo() == null || merchant.getMerMerchantNo() == 0L ) {

            // If the merchant is not null, then return true
            if ( exMerchantList != null && !exMerchantList.isEmpty() ) {

                return true;

            }

        } else {

            // Check if the merchant is null
            if ( exMerchantList != null && exMerchantList.size() > 1 ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Merchant findByMerMerchantNo(Long merMerchantNo) {

        // Get the merchant information
        Merchant merchant = merchantRepository.findByMerMerchantNo(merMerchantNo);

        // Set the related fields
        setRelatedFieldsForMerchant(merchant);

        // Return the merchant object
        return merchant;

    }

    @Override
    public Merchant findByMerMerchantNoAndMerMerchantNameLike(Long merMerchantNo,String query) {

        // Get the merchant information
        Merchant merchant = merchantRepository.findByMerMerchantNoAndMerMerchantNameLike(merMerchantNo,"%"+query+"%");

        // Set the related fields
        setRelatedFieldsForMerchant(merchant);

        // Return the merchant object
        return merchant;

    }

    @Override
    public Merchant findByMerMerchantName(String merMerchantName) {

        // Get the merchant information
        Merchant merchant = merchantRepository.findByMerMerchantName(merMerchantName);

        // Set the related fields
        setRelatedFieldsForMerchant(merchant);

        // Return the merchant object
        return  merchant;

    }

    @Override
    public Merchant findByMerUrlName(String merUrlName) {

        // Get the merchant information
        Merchant merchant = merchantRepository.findByMerUrlName(merUrlName);

        // Set the related fields
        setRelatedFieldsForMerchant(merchant);

        // Return the merchant object
        return merchant;

    }

    @Override
    public Page<Merchant> searchMerchants(String filter, String query, Pageable pageable) {

        // Variable holding the Merchant page
        Page<Merchant> merchantPage;

        // Check the filter and the call the appropriate function to
        // get the data
        if ( filter.equals("name") ) {

            merchantPage = merchantRepository.findByMerMerchantNameLike("%" + query + "%", pageable);

        } else if ( filter.equals("city") ) {

            merchantPage = merchantRepository.findByMerCityLike("%"+query+"%",pageable);

        } else {

            merchantPage = merchantRepository.findAll(pageable);
        }


        // Return the page
        return merchantPage;
    }

    @Override
    public void checkAdminRequestValid(Integer userType) throws InspireNetzException {

        // Check if the user type is Platform Admin
        if ( userType != UserType.ADMIN && userType != UserType.SUPER_ADMIN ) {

            // Log the information
            log.info("checkAdminRequestValid -> User is not a admin");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }
    }

    /**
     * Function to set the related fields for the Merchant object
     *
     * @param merchant      - The object for which the related field need to be
     * @return              - Object after setting the fields
     *                        Null if the passed field is null
     */
    private Merchant setRelatedFieldsForMerchant(Merchant merchant) {

        // If the object is null ,return
        if ( merchant == null ) {

            return merchant;

        }


        // Set the coverImage
        Image imgCoverImage = imageService.findByImgImageId(merchant.getMerCoverImage());

        // Set the image in the merchant object
        merchant.setImgCoverImage(imgCoverImage);


        // Return the object
        return merchant;

    }



    @Override
    public Merchant saveMerchant(Merchant merchant ) throws InspireNetzException {

        //if the merchant no is null save as a new merchant and save the merchant location
        if(merchant.getMerMerchantNo() == null){

            // Save the merchant
            merchant =  merchantRepository.save(merchant);

        } else {

            merchant = updateMerchant(merchant);

        }

        return merchant;
    }

    private Merchant updateMerchant(Merchant merchant) throws InspireNetzException {

        //for getting merchantLocations Id from the role object
        Set<MerchantLocation> merchantLocationList=merchant.getMerchantLocationSet();

        //intializing delete list
        Set<MerchantLocation> merchantLocationsToDelete=new HashSet<>();


        log.info(" updateMerchant :: updateMerchant merchantLocationList"+merchantLocationList);

        Pageable pageable = generalUtils.constructMerchantPageSpecification(0,300);

        //for fetching role access right based by role Id
        Page<MerchantLocation> merchantLocationsBaseList=merchantLocationService.findByMelMerchantNo(merchant.getMerMerchantNo(), pageable);

        log.info("updateMerchant :: updateMerchant base list from present"+merchantLocationsBaseList);

        //for holding present list into arrayList

        List<Long> merchantLocationDeleteList=new ArrayList<>();

        boolean toDelete=true;

        if(merchantLocationList!=null && merchantLocationsBaseList!=null){


            for(MerchantLocation merchantLocation :merchantLocationsBaseList){



                for(MerchantLocation merchantLocation1 : merchantLocationList){

                    //for getting value from Access List
                    Long melId = merchantLocation1.getMelId()==null ?0L:merchantLocation1.getMelId().longValue();

                    if(melId !=0L){

                        if(merchantLocation.getMelId().longValue()==merchantLocation1.getMelId().longValue()){

                            toDelete=false;

                            break;

                        }else{

                            toDelete=true;

                        }

                    }else{

                        continue;

                    }

                }

                if(toDelete==true){

                    merchantLocationsToDelete.add(merchantLocation);

                }


            }
        }

        // for deleting role access right
        if(merchantLocationsToDelete!=null){


            merchantLocationService.deleteMerchantLocationSet(merchantLocationsToDelete);

            log.info("Deleted Merchant Location Set");
        }
        // for updating role and role access rights

        merchant = merchantRepository.save(merchant);

        if(merchant.getMerMerchantNo() != null){

            log.info("Merchant Updated Successfully "+merchant.getMerMerchantNo());

        }

        return merchant;

    }

    @Override
    public boolean deleteMerchant(Long brnId) throws InspireNetzException {

        // Delete the merchant
        merchantRepository.delete(brnId);

        // return true
        return true;

    }

    @Override
    public Merchant validateAndSaveMerchant(Merchant merchant) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        return saveMerchant(merchant);
    }

    @Override
    public boolean validateAndDeleteMerchant(Long merId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_MERCHANT_USER);

        return deleteMerchant(merId);
    }

    @Override
    public Page<Merchant> getActiveMerchants(Long merMerchantNo,String query, Pageable pageable) {

        //page for promotions
        Page<Merchant> merchantPage ;

        //check if default merchant no then set it zero
        /*if(merMerchantNo.longValue()==generalUtils.getDefaultMerchantNo()){

            merMerchantNo=0L;

        }*/

        //check if merchantNo is 0
        if(merMerchantNo==null||merMerchantNo.longValue()==0){

            merchantPage =merchantRepository.findByMerStatusAndMerMerchantNameLike(MerchantStatus.ACCOUNT_ACTIVE, "%" + query + "%", pageable);

        }
        else {

            merchantPage =merchantRepository.findByMerMerchantNoAndMerStatusAndMerMerchantNameLike(merMerchantNo,MerchantStatus.ACCOUNT_ACTIVE,"%" + query + "%", pageable);

        } 
        return merchantPage;
    }

    @Override
    public List<Merchant> getConnectedMerchant(Long merchantNo) {

        //find connected merchant with the specified user
        Long userNo =authSessionUtils.getUserNo();

        //initialize merchant set
        SortedSet<Long> merchantNoSet =new TreeSet<>();

        //merchant list
        List<Merchant> merchantList =new ArrayList<>();

        //merchant check merchant number is zero
        if(merchantNo ==0){

            //get customer list connected with this user
            List<Customer> customerList =customerService.getCustomerDetailsBasedOnUserNo(userNo);

            //check if customer not null identify merchant number
            if(customerList !=null){

                for (Customer customer:customerList){

                    //add into set avoid duplicate
                    merchantNoSet.add(customer.getCusMerchantNo()==null?0L:customer.getCusMerchantNo());
                }
            }

        }else {

            //add merchant number into set
            merchantNoSet.add(merchantNo);
        }


       if(merchantNoSet.size()>0){

           for (Long merchantNo1:merchantNoSet){

               //find merchant number
               Merchant merchant =findByMerMerchantNo(merchantNo1);

               //merchant add into list
               merchantList.add(merchant);
           }
       }

       //return merchant list
   return merchantList;
    }

   @Override
    public Merchant findActiveMerchantsByMerMerchantNo(Long merMerchantNo) {

        // Get the merchant information
        Merchant merchant = merchantRepository.findByMerMerchantNoAndMerStatusNot(merMerchantNo,MerchantStatus.DEACTIVATED);

        // Set the related fields
        setRelatedFieldsForMerchant(merchant);

        // Return the merchant object
        return merchant;

    }

    @Override
    public List<Merchant> findActiveMerchants(Long merMerchantNo) {

        // Get the merchant information
        List<Merchant> merchants =new ArrayList<Merchant>();

        // /check if merchantNo is 0
        if(merMerchantNo==null||merMerchantNo.longValue()==0){

            merchants =merchantRepository.findByMerStatusNot(MerchantStatus.DEACTIVATED);

        }
        else {

            merchants =merchantRepository.findListByMerMerchantNoAndMerStatusNot(merMerchantNo, MerchantStatus.DEACTIVATED);

        }

        // Return the merchant object
        return merchants;

    }


}
