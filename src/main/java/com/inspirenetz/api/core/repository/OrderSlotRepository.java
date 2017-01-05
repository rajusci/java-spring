package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.OrderSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OrderSlotRepository extends  BaseRepository<OrderSlot,Long> {

    public OrderSlot findByOrtId(Long ortId);
    public Page<OrderSlot> findByOrtMerchantNo(Long ortMerchantNo, Pageable pageable);
    public OrderSlot findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionAndOrtStartingTime(Long ortMerchantNo, Long ortLocation,Integer ortType,Integer ortSession,Time ortStartingTime);

    @Query("select O from OrderSlot O where O.ortMerchantNo = ?1 and O.ortLocation = ?2 and (?3 = 0 or O.ortType = ?3 ) and ( ?4 = 0 or O.ortSession = ?4) order by O.ortStartingTime asc ")
    public Page<OrderSlot> searchOrderSlots(Long ortMerchantNo, Long ortLocation, Integer ortType, Integer ortSession, Pageable pageable);


}
