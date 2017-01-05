package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OrderCatalogueService extends BaseService<OrderCatalogue> {

    public Page<OrderCatalogue> findByOrcMerchantNo(Long orcMerchantNo, Pageable pageable);
    public OrderCatalogue findByOrcProductNo(Long orcProductNo);
    public OrderCatalogue findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(Long orcMerchantNo, String orcProductCode,Long orcAvailableLocation);

    public Page<OrderCatalogue> searchOrderCatalogues(Long merchantNo, Long merchantLocation, String filter, String query, Pageable pageable);
    public boolean isDuplicateOrderCatalogueExisting(OrderCatalogue orderCatalogue);

    public OrderCatalogue saveOrderCatalogue(OrderCatalogue orderCatalogue) throws InspireNetzException;
    public boolean deleteOrderCatalogue(Long orcProductNo) throws InspireNetzException;

    public OrderCatalogue validateAndSaveOrderCatalogue(OrderCatalogue orderCatalogue) throws InspireNetzException;
    public boolean validateAndDeleteOrderCatalogue(Long orcProductNo) throws InspireNetzException;

}
