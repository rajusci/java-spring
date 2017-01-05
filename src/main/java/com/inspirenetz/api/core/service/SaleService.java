package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.SaleResource;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtension;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtensionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SaleService extends BaseService<Sale>,AttributeExtensionService {
    
    public Sale findBySalId(Long salId);
    public Page<Sale> findBySalMerchantNoAndSalDate(Long salMerchantNo, Date salDate,Pageable pageable);
    public Page<Sale> findBySalMerchantNoAndSalDateBetween(Long salMerchantNo, Date salStartDate, Date salEndDate,Pageable pageable);
    public Page<Sale> findBySalMerchantNoAndSalLoyaltyId(Long salMerchantNo, String salLoyaltyId,Pageable pageable);
    public Page<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(Long salMerchantNo, String salLoyaltyId, Date salStartDate, Date salEndDate,Pageable pageable);
    public Sale saveSaleDataFromParams(Map<String,Object >params) throws InspireNetzException;
    public Sale getSaleInfo(Long salId) throws InspireNetzException;
    public void processSaleTransactionForLoyalty(Sale sale);
    public Page<Sale> searchSales( String filter, String query,Date salStartDate, Date salEndDate, Pageable pageable );
    public Double getConsolidatedSaleAmountForCustomer(List<Customer> customerList,Date startDate, Date endDate, String productCode );
    public Double getConsolidatedAmountExceedingMsfForCustomers(List<Customer> customerList,Date startDate, Date endDate, String productCode ) ;

    public boolean isDuplicateSale(Long salMerchantNo, String salLoyaltyId, String salPaymentReference,Date salDate,double salAmount );
    public Sale saveSale(Sale purchase) throws InspireNetzException;
    public boolean deleteSale(Long salId);
    public void processSaleTransactionsForLoyalty(List<Sale> saleList);
    public List<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(Long salMerchantNo,String salLoyalityId,Date salDate,String salPaymentReference);
    public void UpdateSales(List<Sale> saleList,String auditDetails) throws InspireNetzException;

    public void saveSalesAll(List<Sale> saleList,String auditDetails) throws InspireNetzException;
    public List<Sale> findBySalMerchantNoAndSalLocationAndSalDateAndSalPaymentReference(Long salMerchantNo,Long salLocation,Date salDate,String salPaymentReference);

    Sale processSaleFromQueue(SaleResource saleResource) throws InspireNetzException;
    public void sendSalesEBill(CustomerResource customerResource, com.inspirenetz.api.rest.resource.SaleResource saleResource,Map<String,Object>params)throws InspireNetzException;

    public void updateSaleAsync(Sale sale) throws InspireNetzException;

}
