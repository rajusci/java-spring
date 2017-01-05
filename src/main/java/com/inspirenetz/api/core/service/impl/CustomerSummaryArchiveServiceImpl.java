package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.CustomerSummaryArchive;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerSummaryArchiveRepository;
import com.inspirenetz.api.core.service.CustomerSummaryArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CustomerSummaryArchiveServiceImpl extends BaseServiceImpl<CustomerSummaryArchive> implements CustomerSummaryArchiveService {

    private static Logger log = LoggerFactory.getLogger(CustomerSummaryArchiveServiceImpl.class);


    @Autowired
    CustomerSummaryArchiveRepository customerSummaryArchiveRepository;


    public CustomerSummaryArchiveServiceImpl() {

        super(CustomerSummaryArchive.class);

    }


    @Override
    protected BaseRepository<CustomerSummaryArchive,Long> getDao() {
        return customerSummaryArchiveRepository;
    }


    @Override
    public CustomerSummaryArchive findByCsaId(Long csaId) {

        //Get the CustomerSummaryArchive
        CustomerSummaryArchive customerSummaryArchive = customerSummaryArchiveRepository.findByCsaId(csaId);

        // Return the object
        return customerSummaryArchive;

    }

    @Override
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyId(Long csaMerchantNo, String csaLoyaltyId) {

        // Get the List of CustomerSummaryArchive data
        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyId(csaMerchantNo,csaLoyaltyId);

        // Retunr the list
        return customerSummaryArchiveList;

    }

    @Override
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(Long csaMerchantNo, String csaLoyaltyId, Long csaLocation) {

        // Get the list the CustomerSummaryArchive information
        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(csaMerchantNo,csaLoyaltyId,csaLocation);

        // Return the list
        return customerSummaryArchiveList;

    }

    @Override
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(Long csaMerchantNo, String csaLoyaltyId, Long csaLocation, int csaPeriodYyyy) {

        // Get the list
        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(csaMerchantNo,csaLoyaltyId,csaLocation,csaPeriodYyyy);

        // Return the list
        return customerSummaryArchiveList;

    }

    @Override
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(Long csaMerchantNo, String csaLoyaltyId, Long csaLocation, int csaPeriodYyyy, int csaPeriodQq) {

        // Get the list
        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(csaMerchantNo,csaLoyaltyId,csaLocation,csaPeriodYyyy,csaPeriodQq);

        // Return the list
        return customerSummaryArchiveList;

    }

    @Override
    public CustomerSummaryArchive findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(Long csaMerchantNo, String csaLoyaltyId, Long csaLocation, int csaPeriodYyyy, int csaPeriodQq, int csaPeriodMm) {

        // Get the CustomersummaryArchive object for the primary key
        CustomerSummaryArchive customerSummaryArchive = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(csaMerchantNo,csaLoyaltyId,csaLocation,csaPeriodYyyy,csaPeriodQq,csaPeriodMm);

        // Return the object
        return customerSummaryArchive;

    }

    @Override
    public CustomerSummaryArchive saveCustomerSummaryArchive(CustomerSummaryArchive customerSummaryArchive) {

        // Save the object
        customerSummaryArchive = customerSummaryArchiveRepository.save(customerSummaryArchive);

        // Return object
        return customerSummaryArchive;

    }

    @Override
    public boolean deleteCustomerSummaryArchive(CustomerSummaryArchive customerSummaryArchive) {

        // Delete the object
        customerSummaryArchiveRepository.delete(customerSummaryArchive);

        // Return true
        return true;

    }
}
