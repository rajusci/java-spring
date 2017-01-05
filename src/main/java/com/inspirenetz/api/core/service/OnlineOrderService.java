package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OnlineOrderService extends BaseService<OnlineOrder> {

    public OnlineOrder findByOrdId(Long ordId);
    public List<OnlineOrder> findByOrdMerchantNoAndOrdUniqueBatchTrackingId(Long ordMerchantNo, Integer ordUniqueBatchTrackingId);
    public Page<OnlineOrder> searchOnlineOrders(Long ordMerchantNo, Long ordMerchantLocation, Long ordSlot, Integer ordStatus,String filter, String query, Pageable pageable);

    public OnlineOrder saveOnlineOrder(OnlineOrder onlineOrder) throws InspireNetzException;
    public boolean deleteOnlineOrder(Long ordId) throws InspireNetzException;

    public OnlineOrder validateAndSaveOnlineOrder(OnlineOrder onlineOrder) throws InspireNetzException;
    public boolean validateAndDeleteOnlineOrder(Long ordId) throws InspireNetzException;

}
