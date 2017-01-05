package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.PaymentStatus;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PaymentStatusRepository;
import com.inspirenetz.api.core.service.PaymentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class PaymentStatusServiceImpl extends BaseServiceImpl<PaymentStatus> implements PaymentStatusService {


    private static Logger log = LoggerFactory.getLogger(PaymentStatusServiceImpl.class);


    @Autowired
    PaymentStatusRepository paymentStatusRepository;


    public PaymentStatusServiceImpl() {

        super(PaymentStatus.class);

    }



    @Override
    protected BaseRepository<PaymentStatus,Long> getDao() {
        return paymentStatusRepository;
    }

    @Override
    public Page<PaymentStatus> findByPysMerchantNo(Long pysMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<PaymentStatus> paymentStatusList = paymentStatusRepository.findByPysMerchantNo(pysMerchantNo, pageable);

        // Return the list
        return paymentStatusList;

    }

    @Override
    public Page<PaymentStatus> searchPaymentStatus(Long pysMerchantNo, Date pysDate, Integer pysModule, String filter, String query, Pageable pageable) {

        // Page<PaymentStatus>
        Page<PaymentStatus> paymentStatusPage;
        // Check the filter and then get the data
        if ( filter.equals("internalref") ) {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNoAndPysDateAndPysModuleAndPysInternalRef(pysMerchantNo,pysDate,pysModule,query,pageable);

        } else if ( filter.equals("loyaltyid") ) {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNoAndPysDateAndPysModuleAndPysLoyaltyId(pysMerchantNo,pysDate,pysModule,query,pageable);

        } else if ( filter.equals("txnno") ) {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNoAndPysDateAndPysModuleAndPysTransactionNumber(pysMerchantNo, pysDate, pysModule, query, pageable);

        } else if ( filter.equals("approvalcode") ) {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNoAndPysDateAndPysModuleAndPysTranApprovalCode(pysMerchantNo, pysDate, pysModule, query, pageable);

        } else if ( filter.equals("receiptno") ) {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNoAndPysDateAndPysModuleAndPysTranReceiptNumber(pysMerchantNo, pysDate, pysModule, query, pageable);

        } else if ( filter.equals("authid") ) {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNoAndPysDateAndPysModuleAndPysTranAuthId(pysMerchantNo, pysDate, pysModule, query, pageable);

        } else {

            paymentStatusPage = paymentStatusRepository.findByPysMerchantNo(pysMerchantNo,pageable);

        }


        // Return the page
        return paymentStatusPage;

    }

    @Override
    public PaymentStatus findByPysId(Long pysId) {

        // Get the paymentStatus for the given paymentStatus id from the repository
        PaymentStatus paymentStatus = paymentStatusRepository.findByPysId(pysId);

        // Return the paymentStatus
        return paymentStatus;


    }




    @Override
    public PaymentStatus savePaymentStatus(PaymentStatus paymentStatus ){

        // Save the paymentStatus
        return paymentStatusRepository.save(paymentStatus);

    }

    @Override
    public boolean deletePaymentStatus(Long pysId) {

        // Delete the paymentStatus
        paymentStatusRepository.delete(pysId);

        // return true
        return true;

    }

}
