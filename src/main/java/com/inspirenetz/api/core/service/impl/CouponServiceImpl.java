package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CouponRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon> implements CouponService {


    private static Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);


    @Autowired
    CouponRepository couponRepository;

    @Autowired
    private CouponDistributionService couponDistributionService;

    @Autowired
    private CouponTransactionService couponTransactionService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ImageService imageService;

    @Autowired
    private Environment environment;




    public CouponServiceImpl() {

        super(Coupon.class);

    }


    @Override
    protected BaseRepository<Coupon,Long> getDao() {
        return couponRepository;
    }




    @Override
    public Page<Coupon> findByCpnMerchantNo(Long cpnMerchantNo, Pageable pageable) {

        Page<Coupon> couponPage = couponRepository.findByCpnMerchantNo(cpnMerchantNo,pageable);

        return couponPage;

    }

    @Override
    public Coupon findByCpnCouponId(Long cpnCouponId) {

        Coupon coupon = couponRepository.findByCpnCouponId(cpnCouponId);

        return coupon;

    }

    @Override
    public Coupon findByCpnMerchantNoAndCpnCouponCode(Long cpnMerchantNo, String cpnCode) {

        Coupon coupon = couponRepository.findByCpnMerchantNoAndCpnCouponCode(cpnMerchantNo, cpnCode);

        return coupon;

    }

    @Override
    public Coupon findByCpnMerchantNoAndCpnCouponName(Long cpnMerchantNo, String cpnCouponName) {

        Coupon coupon = couponRepository.findByCpnMerchantNoAndCpnCouponName(cpnMerchantNo,cpnCouponName);

        return coupon;

    }

    @Override
    public Page<Coupon> findByCpnMerchantNoAndCpnCouponNameLike(Long cpnMerchantNo, String cpnName, Pageable pageable) {

        Page<Coupon> couponPage = couponRepository.findByCpnMerchantNoAndCpnCouponNameLike(cpnMerchantNo,cpnName,pageable);

        return couponPage;

    }

    @Override
    public double calculateCouponValue(Coupon coupon, Purchase purchase) {

        // The value to be returned
        double retValue = 0;

        // Check the coupon type
        if ( coupon.getCpnValueType() == CouponValueType.AMOUNT ) {

            retValue = coupon.getCpnValue();

        } else if ( coupon.getCpnValueType() == CouponValueType.PERCENTAGE ) {

            // Check if the amount is 0, then set the retValue to be 0
            if ( purchase.getPrcAmount() == 0 ) {

                return 0;

            }


            // Calculate the coupon value
            retValue = ( purchase.getPrcAmount() * (coupon.getCpnValue() / 100.0));

            // Check if the cap amount is set, if true, then we need to
            // make sure that the amount is less than or equal  to the cap amount
            if ( coupon.getCpnCapAmount() != null && coupon.getCpnCapAmount() != 0 ) {

                // Check if the retValue is greater than the cap  amount, if true, then set the
                // retValue to the be the capamout, else the retValue is the retValue itself
                retValue = retValue > coupon.getCpnCapAmount() ? coupon.getCpnCapAmount():retValue;

            }

        }

        // Return the retValue
        return retValue;

    }

    @Override
    public boolean isCouponGeneralRulesValid(Coupon coupon, Purchase purchase) {

        // first check the validity of the date
        //
        // Get the current date
        Date currDate = new Date( new java.util.Date().getTime());

        // Compare the expiry date for the coupon to the currDate
        if ( currDate.compareTo(coupon.getCpnExpiryDt())  > 0  ) {

            return false;

        }


        // If the prcId is null, then we need to set the prcId to 0
        if ( purchase.getPrcId() == null ) {

            purchase.setPrcId(0L);

        }

        // Create the CouponTransaction object
        CouponTransaction couponTransaction = new CouponTransaction();

        // Set the fields
        couponTransaction.setCptCouponCode(coupon.getCpnCouponCode());
        couponTransaction.setCptMerchantNo(coupon.getCpnMerchantNo());
        couponTransaction.setCptLoyaltyId(purchase.getPrcLoyaltyId());
        couponTransaction.setCptPurchaseId(purchase.getPrcId());

        // Get the data for the transaction count
        Map<Integer,Integer> couponTransactionMap = couponTransactionService.getCouponTransactionCount(couponTransaction);

        // Now check the acceptance type for the Coupon and see if its valid
        if ( coupon.getCpnAcceptType() == CouponAcceptType.ONCE ) {

            // If the overall coupon accept count is not 0, then we need to return false
            // as the coupon is set to be used only once
            if ( couponTransactionMap.get(CouponTransactionCountType.OVERALL_COUNT) != 0 ) {

                return false;

            }

        } else if ( coupon.getCpnAcceptType() == CouponAcceptType.SPECIFIC_LIMIT ) {

            // Check if the coupon accept count + 1, > greater than the limit
            // If false, then return false
            if ( couponTransactionMap.get(CouponTransactionCountType.OVERALL_COUNT) + 1 >
                    coupon.getCpnAcceptLimit() ) {

                return false;
            }

        }


        // Check if the coupon count is valid for the customer
        // Check if the current coupon count will be greater
        if ( couponTransactionMap.get(CouponTransactionCountType.CUSTOMER_COUNT) + 1 >
                coupon.getCpnMaxCouponsPerCustomer() ) {

            return false;

        }



        // Check if the coupon count is valid for the transaction
        if ( couponTransactionMap.get(CouponTransactionCountType.TRANSACTION_COUNT) + 1 >
                coupon.getCpnMaxCouponsPerTransaction() ) {

            return false;

        }



        // Finally return true
        return true;

    }

    @Override
    public List<Coupon> getCustomerCoupons(Customer customer) {

        // The list holding the Coupon objects available for the Customer
        List<Coupon> couponList = new ArrayList<>(0);


        // Get the list of distributted coupons for the customer
        List<CouponDistribution> couponDistributionList = couponDistributionService.getCouponDistributionForCustomers(customer);

        // Check if couponDistributionList contains any data
        if ( couponDistributionList == null || couponDistributionList.isEmpty() ) {

            // Return the empty list
            return couponList;

        }



        // Create the Purchase object
        Purchase purchase = new Purchase();

        // set the loyalty id as the loyalty id for the customer
        purchase.setPrcLoyaltyId(customer.getCusLoyaltyId());



        // Go through each of the coupon and  check if the rules are valid
        for(CouponDistribution couponDistribution : couponDistributionList ) {

            // Get the coupon for the given coupon code
            Coupon coupon = findByCpnMerchantNoAndCpnCouponCode(couponDistribution.getCodMerchantNo(),couponDistribution.getCodCouponCode());

            // If the coupon is null, then continue with the next iteration
            if ( coupon == null || coupon.getCpnCouponId() == null ) {

                continue;

            }



            // check the validity of the coupon for the current customer
            boolean isValid = isCouponGeneralRulesValid(coupon, purchase);

            // If the coupon is valid for distribution, then add it to the coupon list
            if ( isValid ) {

                couponList.add(coupon);

            }

        }


        // Return the couponList finally
        return couponList;
    }

    @Override
    public boolean isDuplicateCouponExisting(Coupon coupon) {

        // Get the coupon information
        Coupon exCoupon = couponRepository.findByCpnMerchantNoAndCpnCouponName(coupon.getCpnMerchantNo(), coupon.getCpnCouponName());

        // If the brnId is 0L, then its a new coupon so we just need to check if there is ano
        // ther coupon code
        if ( coupon.getCpnCouponId() == null || coupon.getCpnCouponId() == 0L ) {

            // If the coupon is not null, then return true
            if ( exCoupon != null ) {

                return true;

            }

        } else {

            // Check if the coupon is null
            if ( exCoupon != null && coupon.getCpnCouponId().longValue() != exCoupon.getCpnCouponId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public boolean isCouponCodeValid(Coupon coupon) {

        // Check the type of the coupon code
        if ( coupon.getCpnCouponCodeType() == CouponCodeType.FIXED ) {

            // Check if the coupon code is already existing
            Coupon exCoupon = findByCpnMerchantNoAndCpnCouponCode(coupon.getCpnMerchantNo(),coupon.getCpnCouponCode());

            if ( exCoupon != null && (coupon.getCpnCouponId().longValue()!= exCoupon.getCpnCouponId().longValue())) {

                return false;

            }

        } else if ( coupon.getCpnCouponCodeType() == CouponCodeType.RANGE ) {

            // Check if the coupon code range is valid
            List<Coupon> couponList = couponRepository.findSameRangeCoupons(coupon.getCpnMerchantNo(),coupon.getCpnCouponCodeFrom(),coupon.getCpnCouponCodeTo());

            // If the list is not empty, then the coupon range is overlapping for some other coupons
            // defined for the merchant
            if ( couponList != null && !couponList.isEmpty() ) {

                // Check if the coupon list is greater than 1
                // In that case we have atleas 1 overlapping
                if ( couponList.size() > 1 ) {

                    return false;

                } else {

                    // We need to check if the single item in the list is the
                    // same coupon we are checking ( update)
                    Coupon exCoupon = couponList.get(0);

                    // If the coupon id for the fetched coupon is not same, then
                    // its overlapping
                    if ( coupon.getCpnCouponId() !=  exCoupon.getCpnCouponId() ) {

                        return false;

                    }
                }
            }
        }


        // Finally return true
        return true;

    }

    @Override
    public boolean validateCoupon(Coupon coupon, Purchase purchase,List<PurchaseSKU> purchaseSKUList) {

        // Check the validity of the coupon distribution
        boolean isValid = isCouponGeneralRulesValid(coupon, purchase);


        // if the coupon is not valid for distribution, then it cannoot be
        // evalulated for the transaction as well
        if ( !isValid ) {

            return false;

        }



        // Check for the general rules for the coupon
        //
        // Check for the purhcase amount
        if ( purchase.getPrcAmount() < coupon.getCpnMinTxnAmount() ) {

            return false;

        }


        // Check if the coupon value type is bulk order and if yes, then we need
        // to have the purchaseSKu list populated
        if ( coupon.getCpnValueType() == CouponValueType.BULK_ORDER &&
                ( purchaseSKUList == null || purchaseSKUList.isEmpty() )  ) {

            // TODO add the checking for the bulk order coupon validity
            // number of item qty Vs the number of items free etc
            return false;

        }



        // Finally return true
        return true;

    }

    @Override
    public double evaluateCoupon(Coupon coupon,Purchase purchase, List<PurchaseSKU> purchaseSKUList ) {

        // First validate the coupon
        boolean isValid = validateCoupon(coupon,purchase,purchaseSKUList);

        // Check if the coupon is valid
        if ( !isValid ) {

            return 0;

        }


        // Get the coupon value
        double couponValue = calculateCouponValue(coupon,purchase);

        // Return the couponValue
        return couponValue;

    }



    @Override
    public Coupon saveCoupon(Coupon coupon ) throws InspireNetzException {

        // Save the coupon
        return couponRepository.save(coupon);

    }

    @Override
    public boolean deleteCoupon(Long brnId) throws InspireNetzException {

        // Delete the coupon
        couponRepository.delete(brnId);

        // return true
        return true;

    }

    @Override
    public Coupon validateAndSaveCoupon(Coupon coupon) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_COUPON);

        return saveCoupon(coupon);
    }

    @Override
    public boolean validateAndDeleteCoupon(Long cpnId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_COUPON);

        return deleteCoupon(cpnId);

    }

    @Override
    public List<Coupon> getCustomerCouponsCompatible(String loyaltyid, Long merchantNo) throws InspireNetzException {

        //get the customer data
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyid , merchantNo);

        //check whether the customerexists
        if(customer == null){

            //log error
            log.error("getCustomerCoupons : No Customer Information Found");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }
        //calling the service method for retrieving the customer coupons
        List<Coupon> coupons = getCustomerCoupons(customer);

        //format the coupon data to compatible data
        for(Coupon coupon : coupons){

            //if coupon type is range then set the coupon type to couponCodeFroms
            if(coupon.getCpnCouponCodeType() == CouponCodeType.RANGE){

                coupon.setCpnCouponCode(coupon.getCpnCouponCodeFrom());

            }

        }

        return coupons;

    }

    @Override
    public List<Map<String ,String >> getCouponsCompatible (String loyaltyId, Long merchantNo) throws InspireNetzException {

        //get coupons
        List<Coupon> coupons = getCustomerCouponsCompatible(loyaltyId, merchantNo);

        List<Map<String , String >> couponData = new ArrayList<>();

        Map<String ,String > coupon = new HashMap<>();

        String imageUrl = environment.getProperty("IMAGE_PATH_URL");

        for(Coupon couponObj : coupons){

            coupon = new HashMap<>();

            coupon.put("coupon_code",couponObj.getCpnCouponCode());
            coupon.put("coupon_name",couponObj.getCpnCouponName());
            coupon.put("coupon_text",couponObj.getCpnCouponText());

            Image couponImage = couponObj.getImage();

            String imagePath = imageService.getPathForImage(couponImage,ImagePathType.MOBILE);

            coupon.put("coupon_image",imageUrl + imagePath);

            couponData.add(coupon);
        }

        return couponData;
    }

    @Override
    public Page<Coupon> getCouponsForUser(String usrLoginId, Long merchantNo) throws InspireNetzException {

        // List holding the coupons
        List<Coupon> couponList = new ArrayList<>();

        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);



        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");


            return new PageImpl<>(couponList);
        }

        //get member customers,if catMerchantNo is zero or default merchant no return all members
        List<Customer> customers=customerService.getUserMemberships(merchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return new PageImpl<>(couponList);

        }



        // Go through of the customer list and get the coupons for the customer
        for(Customer customer : customers ) {

            // Get the List of coupons for customer
            List<Coupon> cusCoupons = getCustomerCouponsCompatible(customer.getCusLoyaltyId(), customer.getCusMerchantNo());

            //  If the list is empty continue iteration
            if ( cusCoupons == null ||  cusCoupons.isEmpty() ) {

                // Continue the iteration
                continue;

            }


            // Add the list
            couponList.addAll(cusCoupons);

        }


        // Finally return the user coupons
        return new PageImpl<>(couponList);
    }


}
