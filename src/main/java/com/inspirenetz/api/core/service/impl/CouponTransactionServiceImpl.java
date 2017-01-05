package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.CouponTransactionCountType;
import com.inspirenetz.api.core.domain.Coupon;
import com.inspirenetz.api.core.domain.CouponTransaction;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CouponTransactionRepository;
import com.inspirenetz.api.core.service.CouponService;
import com.inspirenetz.api.core.service.CouponTransactionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CouponTransactionServiceImpl extends BaseServiceImpl<CouponTransaction> implements CouponTransactionService {


    private static Logger log = LoggerFactory.getLogger(CouponTransactionServiceImpl.class);


    @Autowired
    CouponTransactionRepository couponTransactionRepository;

    @Autowired
    private CouponService couponService;


    public CouponTransactionServiceImpl() {

        super(CouponTransaction.class);

    }


    @Override
    protected BaseRepository<CouponTransaction,Long> getDao() {
        return couponTransactionRepository;
    }



    @Override
    public Page<CouponTransaction> findByCptMerchantNo(Long cptMerchantNo, Pageable pageable) {

        // Get the transaction page
        Page<CouponTransaction> couponTransactionPage = couponTransactionRepository.findByCptMerchantNo(cptMerchantNo,pageable);

        // Return the page
        return couponTransactionPage;

    }

    @Override
    public Page<CouponTransaction> findByCptMerchantNoAndCptCouponCode(Long cptMerchantNo, String cptCouponCode, Pageable pageable) {

        // Get the coupon transaction page
        Page<CouponTransaction> couponTransactionPage = couponTransactionRepository.findByCptMerchantNoAndCptCouponCode(cptMerchantNo, cptCouponCode, pageable);

        // Returnthe page
        return couponTransactionPage;

    }

    @Override
    public Page<CouponTransaction> findByCptMerchantNoAndCptLoyaltyId(Long cptMerchantNo, String cptLoyaltyId, Pageable pageable) {

        // Get the coupon transactions page
        Page<CouponTransaction> couponTransactionPage = couponTransactionRepository.findByCptMerchantNoAndCptLoyaltyId(cptMerchantNo, cptLoyaltyId, pageable);

        // Return the page
        return couponTransactionPage;
    }

    @Override
    public Page<CouponTransaction> findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId(Long cptMerchantNo, String cptCouponCode, String cptLoyaltyId, Pageable pageable) {

        // Get the CouponTransaction page
        Page<CouponTransaction> couponTransactionPage = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId(cptMerchantNo,cptCouponCode, cptLoyaltyId, pageable);

        // Return the page
        return couponTransactionPage;

    }

    @Override
    public CouponTransaction findByCptId(Long cptId) {

        // Get the CouponTransaction
        CouponTransaction  couponTransaction = couponTransactionRepository.findByCptId(cptId);

        // Return the object
        return couponTransaction;

    }

    @Override
    public CouponTransaction findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(Long cptMerchantNo, String cptCouponCode, String cptLoyaltyId, Long cptPurchaseId) {

        // Get the CouponTransaction for the fields specified
        CouponTransaction couponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(cptMerchantNo,cptCouponCode,cptLoyaltyId,cptPurchaseId);

        // Return the CouponTransaction object
        return couponTransaction;

    }

    @Override
    public Map<Integer, Integer> getCouponTransactionCount(CouponTransaction couponTransaction) {


        // The Map holding the transaction count for the coupon code given
        Map<Integer,Integer>  couponTransactionCountMap = new HashMap<>(0);



        // First get the overall count ( loyaltyid = 0, purchaseid = 0 )
        CouponTransaction overallCouponTransaction =  couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),"0",0L);

        // Check if the overallTransactionCount exists
        if ( overallCouponTransaction != null ) {

            couponTransactionCountMap.put(CouponTransactionCountType.OVERALL_COUNT,overallCouponTransaction.getCptCouponCount());

        } else {

            couponTransactionCountMap.put(CouponTransactionCountType.OVERALL_COUNT,0);

        }



        // Get the transaction count for the particular customer
        CouponTransaction customerCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),couponTransaction.getCptLoyaltyId(),0L);

        // Check if the customerCouponTransaction count exists
        if ( customerCouponTransaction != null ) {

            couponTransactionCountMap.put(CouponTransactionCountType.CUSTOMER_COUNT,customerCouponTransaction.getCptCouponCount());

        } else {

            couponTransactionCountMap.put(CouponTransactionCountType.CUSTOMER_COUNT,0);

        }




        // Get the transaction count for the specific transaction
        CouponTransaction transactionCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),couponTransaction.getCptLoyaltyId(),couponTransaction.getCptPurchaseId());

        // Check if the transaction coupon transaciont exists
        if ( transactionCouponTransaction != null ) {

            couponTransactionCountMap.put(CouponTransactionCountType.TRANSACTION_COUNT,transactionCouponTransaction.getCptCouponCount());

        } else {

            couponTransactionCountMap.put(CouponTransactionCountType.TRANSACTION_COUNT,0);

        }


        // Return the map
        return couponTransactionCountMap;

    }

    @Override
    public boolean recordCouponAccept(CouponTransaction couponTransaction) throws InspireNetzException {


        // Get the coupon object for the given coupon code
        Coupon coupon = couponService.findByCpnMerchantNoAndCpnCouponCode(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode());

        // Check if the coupon is valid
        if ( coupon == null || coupon.getCpnCouponId() == null ) {

            return false;

        }

        // Get the overall CouponTransaction object
        CouponTransaction overallCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),"0",0L);

        // Check if the overallCouponTransaction exists
        if ( overallCouponTransaction == null ) {

            // Create a new object
            overallCouponTransaction = new CouponTransaction();

            overallCouponTransaction.setCptMerchantNo(couponTransaction.getCptMerchantNo());
            overallCouponTransaction.setCptCouponCode(couponTransaction.getCptCouponCode());
            overallCouponTransaction.setCptLoyaltyId("0");
            overallCouponTransaction.setCptPurchaseId(0L);
            overallCouponTransaction.setCptCouponCount(1);

        } else {

            // Add the coupont count
            overallCouponTransaction.setCptCouponCount(overallCouponTransaction.getCptCouponCount() + 1);

        }

        // SAve the overallCouponTransaction
        overallCouponTransaction = couponTransactionRepository.save(overallCouponTransaction);





        // Add the customer specific coupon transaction only if the loyalty id is present
        // in the coupon transaction
        if ( couponTransaction.getCptLoyaltyId() != null && !couponTransaction.getCptLoyaltyId().equals("") && !couponTransaction.getCptLoyaltyId().equals("0") ) {

            // Get the customercouponTransaction
            CouponTransaction customerCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),couponTransaction.getCptLoyaltyId(),0L);

            // Check if the customerCouponTransaction exists
            if ( customerCouponTransaction == null ) {

                // Create a new object
                customerCouponTransaction = new CouponTransaction();

                customerCouponTransaction.setCptMerchantNo(couponTransaction.getCptMerchantNo());
                customerCouponTransaction.setCptCouponCode(couponTransaction.getCptCouponCode());
                customerCouponTransaction.setCptLoyaltyId(couponTransaction.getCptLoyaltyId());
                customerCouponTransaction.setCptPurchaseId(0L);
                customerCouponTransaction.setCptCouponCount(1);

            } else {

                customerCouponTransaction.setCptCouponCount( customerCouponTransaction.getCptCouponCount() + 1);
            }


            // save the customerCouponTransaction
            customerCouponTransaction = couponTransactionRepository.save(customerCouponTransaction);

        }




        // Add the transaction based coupon count only if the  purchase id is set
        if( couponTransaction.getCptPurchaseId() != null && couponTransaction.getCptPurchaseId() != 0L ) {

            // Get the transactionCouponTransaction
            CouponTransaction transactionCouponTransaction =  couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),couponTransaction.getCptLoyaltyId(),couponTransaction.getCptPurchaseId());

            // Check if the transactionCouponTransaction exists
            if( transactionCouponTransaction  == null ) {

                // Create new object
                transactionCouponTransaction = new CouponTransaction();

                transactionCouponTransaction.setCptMerchantNo(couponTransaction.getCptMerchantNo());
                transactionCouponTransaction.setCptCouponCode(couponTransaction.getCptCouponCode());
                transactionCouponTransaction.setCptLoyaltyId(couponTransaction.getCptLoyaltyId());
                transactionCouponTransaction.setCptPurchaseId(couponTransaction.getCptPurchaseId());
                transactionCouponTransaction.setCptCouponCount(1);

            } else {

                transactionCouponTransaction.setCptCouponCount( transactionCouponTransaction.getCptCouponCount() + 1);

            }

            // Save the transactionCouponTransaction
            transactionCouponTransaction = couponTransactionRepository.save(transactionCouponTransaction);


        }


        // Finally increment the coupon accept count for the Coupon object
        coupon.setCpnAcceptCount(coupon.getCpnAcceptCount() + 1);

        // Save the coupon
        coupon = couponService.saveCoupon(coupon);


        // Return true
        return true;

    }

    @Override
    public boolean revertCouponAccept(CouponTransaction couponTransaction) throws InspireNetzException {

        // Get the coupon object for the given coupon code
        Coupon coupon = couponService.findByCpnMerchantNoAndCpnCouponCode(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode());

        // Check if the coupon is valid
        if ( coupon == null || coupon.getCpnCouponId() == null ) {

            return false;

        }

        // Get the overall CouponTransaction object
        CouponTransaction overallCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),"0",0L);

        // Check if the overallCouponTransaction exists
        if ( overallCouponTransaction != null ) {

            // Reduce the coupon coun
            overallCouponTransaction.setCptCouponCount(overallCouponTransaction.getCptCouponCount() - 1);

            // SAve the overallCouponTransaction
            couponTransactionRepository.save(overallCouponTransaction);

        }



        // Add the customer specific coupon transaction only if the loyalty id is present
        // in the coupon transaction
        if ( couponTransaction.getCptLoyaltyId() != null && !couponTransaction.getCptLoyaltyId().equals("") && !couponTransaction.getCptLoyaltyId().equals("0") ) {

            // Get the customercouponTransaction
            CouponTransaction customerCouponTransaction = couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),couponTransaction.getCptLoyaltyId(),0L);

            // Check if the customerCouponTransaction exists
            if ( customerCouponTransaction != null ) {

                //Reduce the coupon count
                customerCouponTransaction.setCptCouponCount( customerCouponTransaction.getCptCouponCount() - 1);

                // Save the coupon transaction
                couponTransactionRepository.save(customerCouponTransaction);

            }

        }




        // Add the transaction based coupon count only if the  purchase id is set
        if( couponTransaction.getCptPurchaseId() != null && couponTransaction.getCptPurchaseId() != 0L ) {

            // Get the transactionCouponTransaction
            CouponTransaction transactionCouponTransaction =  couponTransactionRepository.findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(couponTransaction.getCptMerchantNo(),couponTransaction.getCptCouponCode(),couponTransaction.getCptLoyaltyId(),couponTransaction.getCptPurchaseId());

            // Check if the transactionCouponTransaction exists
            if( transactionCouponTransaction  != null ) {

                // Reduce the coupon count
                transactionCouponTransaction.setCptCouponCount( transactionCouponTransaction.getCptCouponCount() - 1);

                // Save the CouponTransaction
                couponTransactionRepository.save(transactionCouponTransaction);

            }

        }


        // Finally decrement the coupon accept count for the Coupon object
        coupon.setCpnAcceptCount(coupon.getCpnAcceptCount() - 1);

        // Save the coupon
        couponService.saveCoupon(coupon);



        // Return true
        return true;

    }



    @Override
    public CouponTransaction saveCouponTransaction(CouponTransaction couponTransaction ){

        // Save the couponTransaction
        return couponTransactionRepository.save(couponTransaction);

    }

    @Override
    public boolean deleteCouponTransaction(Long brnId) {

        // Delete the couponTransaction
        couponTransactionRepository.delete(brnId);

        // return true
        return true;

    }

}
