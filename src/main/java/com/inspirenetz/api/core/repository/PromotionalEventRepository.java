package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.PromotionalEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneeshci on 30/9/14.
 */
public interface PromotionalEventRepository extends BaseRepository<PromotionalEvent,Long>{

    public PromotionalEvent findByPreId(Long rarId);
    public Page<PromotionalEvent> findByPreMerchantNo(Long merchantNo,Pageable pageable);
    public Page<PromotionalEvent> findByPreMerchantNoAndPreEventType(Long merchantNo,Integer preEventType,Pageable pageable);
    public Page<PromotionalEvent> findByPreMerchantNoAndPreEventNameLike(Long merchantNo,String preEventName,Pageable pageable);
    public PromotionalEvent findByPreEventCode(String preEventCode);

    @Query("select P from PromotionalEvent P where P.preMerchantNo  = ?1 and P.preStartDate < ?2 and P.preEndDate > ?2")
    public Page<PromotionalEvent> getValidEventsByMerchantNoAndDate(Long merchantNo,Date date,Pageable pageable);


}
