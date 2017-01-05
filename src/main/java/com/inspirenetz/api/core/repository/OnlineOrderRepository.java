package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.OnlineOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OnlineOrderRepository extends  BaseRepository<OnlineOrder,Long> {

    public OnlineOrder findByOrdId(Long ordId);
    public List<OnlineOrder> findByOrdMerchantNoAndOrdUniqueBatchTrackingId(Long ordMerchantNo, Integer ordUniqueBatchTrackingId);


    @Query("select O from OnlineOrder O where O.ordMerchantNo = ?1 and O.ordOrderLocation = ?2  group by O.ordUniqueBatchTrackingId order by O.ordId DESC")
    public Page<OnlineOrder> searchOnlineOrders(Long ordMerchantNo,Long ordOrderLocation, Pageable pageable);

    @Query("select O from OnlineOrder O where O.ordMerchantNo = ?1 and O.ordOrderLocation = ?2 and ( O.ordOrderSlot = 0 or O.ordOrderSlot = ?3 ) and (O.ordStatus = 0 or O.ordStatus = ?4) and O.ordUniqueBatchTrackingId = ?5 group by O.ordUniqueBatchTrackingId order by O.ordId DESC")
    public Page<OnlineOrder> searchOnlineOrdersByTrackingId(Long ordMerchantNo,Long ordOrderLocation,Long ordOrderSlot,Integer ordStatus,Integer ordUniqueTrackingId,Pageable pageable);

    @Query("select O from OnlineOrder O where O.ordMerchantNo = ?1 and O.ordOrderLocation = ?2 and ( O.ordOrderSlot = 0 or O.ordOrderSlot = ?3 ) and (O.ordStatus = 0 or O.ordStatus = ?4) and O.ordLoyaltyId = ?5  group by O.ordUniqueBatchTrackingId order by O.ordId DESC")
    public Page<OnlineOrder> searchOnlineOrdersByLoyaltyId(Long ordMerchantNo,Long ordOrderLocation,Long ordOrderSlot,Integer ordStatus,String ordLoyaltyId,Pageable pageable);

    @Query("select O from OnlineOrder O where O.ordMerchantNo = ?1 and O.ordOrderLocation = ?2 and ( O.ordOrderSlot = 0 or O.ordOrderSlot = ?3 ) and (O.ordStatus = 0 or O.ordStatus = ?4) and O.ordProductCode = ?5 group by O.ordUniqueBatchTrackingId order by O.ordId DESC")
    public Page<OnlineOrder> searchOnlineOrdersByProductCode(Long ordMerchantNo,Long ordOrderLocation,Long ordOrderSlot,Integer ordStatus,String ordProductCode,Pageable pageable);


}
