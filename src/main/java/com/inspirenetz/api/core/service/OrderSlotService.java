package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Time;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OrderSlotService extends BaseService<OrderSlot> {

    public Page<OrderSlot> findByOrtMerchantNo(Long ortMerchantNo, Pageable pageable);
    public OrderSlot findByOrtId(Long ortId);
    public Page<OrderSlot> findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionOrderByOrtStartingTime(Long ortMerchantNo, Long ortLocation,Integer ortType,Integer ortSession,Pageable pageable);
    public OrderSlot findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(Long ortMerchantNo, Long ortLocation,Integer ortType,Integer ortSession,Time ortStartingTime);
    public boolean isDuplicateOrderSlotExisting(OrderSlot orderSlot);

    public OrderSlot saveOrderSlot(OrderSlot orderSlot) throws InspireNetzException;
    public boolean deleteOrderSlot(Long ortId) throws InspireNetzException;

    public OrderSlot validateAndSaveOrderSlot(OrderSlot orderSlot) throws InspireNetzException;
    public boolean validateAndDeleteOrderSlot(Long ortId) throws InspireNetzException;



}
