package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.MerchantPartnerTransactionSearchType;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantPartnerTransactionRepository;
import com.inspirenetz.api.core.service.MerchantPartnerTransactionService;
import com.inspirenetz.api.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;


/**
 * Created by abhi on 13/7/16.
 */
@Service
public class MerchantPartnerTransactionServiceImpl extends BaseServiceImpl<MerchantPartnerTransaction> implements MerchantPartnerTransactionService{

    private static Logger log = LoggerFactory.getLogger(MerchantPartnerTransactionServiceImpl.class);

    @Autowired
    MerchantPartnerTransactionRepository merchantPartnerTransactionRepository;

    public MerchantPartnerTransactionServiceImpl(){

        super(MerchantPartnerTransaction.class);

    }

    @Override
    protected BaseRepository<MerchantPartnerTransaction,Long> getDao(){
        return merchantPartnerTransactionRepository;
    }

    @Override
    public MerchantPartnerTransaction findByMptId(Long mptId){

        //call the repository method
        MerchantPartnerTransaction merchantPartnerTransaction = merchantPartnerTransactionRepository.findByMptId(mptId);

        //return the transaction
        return merchantPartnerTransaction;

    }

    @Override
    public Page<MerchantPartnerTransaction> findByMptProductNo(Long mptProductNo,Pageable pageable){

        //get the List of transactions based on product number
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNo(mptProductNo, pageable);

        // Return the pageable object
        return merchantPartnerTransactionPage;

    }

    @Override
    public Page<MerchantPartnerTransaction> findByMptMerchantNoAndMptTxnDateBetween(Long mptMerchantNo,Date startDate,Date endDate,Pageable pageable){

        //get the transactions
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptTxnDateBetween(mptMerchantNo,startDate,endDate, pageable);

        // Return the pageable object
        return merchantPartnerTransactionPage;

    }

    @Override
    public Page<MerchantPartnerTransaction> findByMptPartnerNo(Long mptPartnerNo, Pageable pageable){

        // get the transactions
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptPartnerNo(mptPartnerNo, pageable);

        // Return the pageable object
        return merchantPartnerTransactionPage;

    }

    @Override
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptMerchantNo(Long mptProductNo, Long mptMerchantNo, Pageable pageable){

        //get the transactions
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNoAndMptMerchantNo(mptProductNo, mptMerchantNo, pageable);

        // Return the pageable object
        return merchantPartnerTransactionPage;
    }

    @Override
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptPartnerNo(Long mptProductNo, Long mptPartnerNo, Pageable pageable){

        //get the list of transactions
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNoAndMptPartnerNo(mptProductNo, mptPartnerNo, pageable);

        // Return the pageable object
        return  merchantPartnerTransactionPage;

    }

    @Override
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptMerchantNoAndMptTxnDateBetween(Long mptProductNo, Long mptMerchantNo, Date startDate, Date endDate, Pageable pageable) {

        // Check if the startTimestamp is set or not
        // If the start timestamp is not set, then we need to set the date to the minimum value
        if ( startDate == null ){

            // Set the startTimestamp to an early value
            startDate = DBUtils.covertToSqlDate("1970-01-01");

        }


        // Check if the endTimestamp is set, if not then we need to
        // set the timestamp to the largest possible date
        if ( endDate == null ) {

            // Set the end time stamp
            endDate = DBUtils.covertToSqlDate("9999-12-31");

        }

        // Get the CardTransctionPage
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNoAndMptMerchantNoAndMptTxnDateBetween(mptProductNo, mptMerchantNo, startDate, endDate, pageable);

        // Return the pageable object
        return merchantPartnerTransactionPage;
 }

    @Override
    public Page<MerchantPartnerTransaction> getMerchantPartnerTransactionsForPartner(Long mptProductNo, Long mptMerchantNo, Long mptPartnerNo, Date startDate, Date endDate, Pageable pageable) {

        Page<MerchantPartnerTransaction>  merchantPartnerTransactionPage = null;

        // Check if the filters are empty
        if(mptProductNo.longValue() == 0l && mptMerchantNo == 0l){

            //get the transacton object
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptPartnerNoAndMptTxnDateBetween(mptPartnerNo,startDate,endDate, pageable);

        } else if (mptMerchantNo != null && mptMerchantNo.longValue() != 0 && mptProductNo != null && mptProductNo.longValue() != 0) {

            //get the transacton object
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(mptPartnerNo,mptProductNo,mptMerchantNo,startDate,endDate,pageable);

        } else if( mptMerchantNo != null && mptMerchantNo.longValue() != 0 && (mptProductNo == null || mptProductNo.longValue() == 0)) {

            //get the transactions based on the merchant
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptPartnerNoAndMptTxnDateBetween(mptMerchantNo,mptPartnerNo,startDate,endDate,pageable);

        } else if( mptProductNo != null && mptProductNo.longValue() != 0 & ( mptMerchantNo == null || mptMerchantNo.longValue() == 0)) {

            // Get the transactions based on product and the partner
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNoAndMptPartnerNoAndMptTxnDateBetween(mptProductNo,mptPartnerNo,startDate,endDate,pageable);

        }



        return  merchantPartnerTransactionPage;
    }


    /*@Override
    public Page<MerchantPartnerTransaction> searchMerchantPartnerTransactions(Long mptProductNo, Long mptMerchantNo, Timestamp startTimestamp, Timestamp endTimestamp,Pageable pageable) {

        // Check if the startTimestamp is set or not
        // If the start timestamp is not set, then we need to set the date to the minimum value
        if ( startTimestamp == null ){

            // Set the startTimestamp to an early value
            startTimestamp = Date.("1970-01-01");

        }


        // Check if the endTimestamp is set, if not then we need to
        // set the timestamp to the largest possible date
        if ( endTimestamp == null ) {

            // Set the end time stamp
            endTimestamp = DBUtils.convertToSqlTimestamp("9999-12-31 00:00:00");

        }

        // Get the CardTransctionPage
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionRepository.searchMerchantPartnerTransactions(mptProductNo,mptMerchantNo,startTimestamp,endTimestamp,pageable);

        // Return the pageable object
        return merchantPartnerTransactionPage;

    }
*/
    /*@Override
    public Page<MerchantPartnerTransaction> getMerchantPartnerTransactionsForPartner(Long mptProductNo,Long mptMerchantNo,Long mptPartnerNo, Date startDate, Date endDate,Pageable pageable){

        //check for null value
       if( mptPartnerNo == null){

           //set mptPartnerNo to zero
           mptPartnerNo = 0l;
       }

        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = null;


        // Variable holding the search type.
        Integer mptSearchType = 0;

        //check the request is from partner portal or merchant portal
        if ( mptPartnerNo == null || mptPartnerNo == 0l ) {

            //Set the search type as MERCHANT
            mptSearchType = MerchantPartnerTransactionSearchType.MERCHANT;

        }else if(mptPartnerNo != null && mptPartnerNo != 0l  ){

            // Set the search type as PARTNER
            mptSearchType = MerchantPartnerTransactionSearchType.PARTNER;

        }

        //Check if the search is from partner portal
        if(mptSearchType.intValue() == MerchantPartnerTransactionSearchType.PARTNER ){

            // Check if the filters are empty
            if(mptProductNo.longValue() == 0l && mptMerchantNo == 0l){

            //get the transacton object
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptPartnerNo(mptPartnerNo, pageable);

            } else if (mptMerchantNo != null && mptMerchantNo.longValue() != 0 && mptProductNo != null && mptProductNo.longValue() != 0) {

                //get the transacton object
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(mptPartnerNo,mptProductNo,mptMerchantNo,startDate,endDate,pageable);

            } else if( mptMerchantNo != null && mptMerchantNo.longValue() != 0 && (mptProductNo == null || mptProductNo.longValue() == 0)) {

                //get the transactions based on the merchant
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptPartnerNo(mptMerchantNo,mptPartnerNo,pageable);

            } else if( mptProductNo != null && mptProductNo.longValue() != 0 & ( mptMerchantNo == null || mptMerchantNo.longValue() == 0)) {

                // Get the transactions based on product and the partner
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNoAndMptPartnerNo(mptProductNo,mptPartnerNo,pageable);

            }
            //Check if the search is from merchant portal
        } else if(mptSearchType.intValue() == MerchantPartnerTransactionSearchType.MERCHANT ){

            // Check if the filters are empty
            if(mptProductNo.longValue() == 0 && mptPartnerNo == 0){

                //get the transacton object
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNo(mptMerchantNo, pageable);

            } else if (mptMerchantNo != null && mptMerchantNo.longValue() != 0 && mptProductNo != null && mptProductNo.longValue() != 0) {

                //get the transacton object
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(mptPartnerNo,mptProductNo,mptMerchantNo,startDate,endDate,pageable);

            } else if( mptPartnerNo != null && mptPartnerNo.longValue() != 0 && (mptProductNo == null || mptProductNo.longValue() == 0)) {

                //get the transactions based on the merchant
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptPartnerNo(mptMerchantNo,mptPartnerNo,pageable);

            } else if( mptProductNo != null && mptProductNo.longValue() != 0 & ( mptPartnerNo == null || mptPartnerNo.longValue() == 0)) {

                // Get the transactions based on product and the partner
                merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptProductNoAndMptMerchantNo(mptProductNo,mptMerchantNo,pageable);

            }
    }

        return merchantPartnerTransactionPage;

    }
*/
    @Override
    public Page<MerchantPartnerTransaction> findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(Long mptPartnerNo, Long mptProductNo, Long mptMerchantNo, Date startDate, Date endDate, Pageable pageable) {

        return merchantPartnerTransactionRepository.findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(mptPartnerNo,mptProductNo,mptMerchantNo,startDate,endDate,pageable);
    }


    @Override
    public MerchantPartnerTransaction saveMerchantPartnerTransaction(MerchantPartnerTransaction merchantPartnerTransaction){

        // Save the MerchantPartnerTransaction
        return merchantPartnerTransactionRepository.save(merchantPartnerTransaction);

    }

    @Override
    public void addMerchantPartnerTransaction(Catalogue catalogue) {

        MerchantPartnerTransaction merchantPartnerTransaction = new MerchantPartnerTransaction();

        merchantPartnerTransaction.setMptMerchantNo(catalogue.getCatMerchantNo());
        merchantPartnerTransaction.setMptPartnerNo(catalogue.getCatRedemptionMerchant());
        merchantPartnerTransaction.setMptProductNo(catalogue.getCatPartnerProduct());
        merchantPartnerTransaction.setMptPrice(catalogue.getCatProductCost());
        merchantPartnerTransaction.setMptQuantity(catalogue.getCatAvailableStock().intValue());
        merchantPartnerTransaction.setMptTxnDate(new Date(System.currentTimeMillis()));

        merchantPartnerTransaction = saveMerchantPartnerTransaction(merchantPartnerTransaction);

    }
    @Override
    public Page<MerchantPartnerTransaction> getMerchantPartnerTransactionsForMerchant(Long mptProductNo, Long mptMerchantNo, Long mptPartnerNo, Date startDate, Date endDate, Pageable pageable) {

        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = null;

        // Check if the filters are empty
        if(mptPartnerNo == null || mptPartnerNo == 0){

            //get the transacton object
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptTxnDateBetween(mptMerchantNo,startDate,endDate, pageable);

        } else if( mptPartnerNo != null && mptPartnerNo.longValue() != 0 ) {

            //get the transactions based on the merchant
            merchantPartnerTransactionPage = merchantPartnerTransactionRepository.findByMptMerchantNoAndMptPartnerNoAndMptTxnDateBetween(mptMerchantNo,mptPartnerNo,startDate,endDate,pageable);

        }
        return merchantPartnerTransactionPage;
    }

}
