package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;


/**
 * Created by saneeshci on 30/9/14.
 */
public interface PromotionalEventService extends BaseService<PromotionalEvent>{

    PromotionalEvent  findByPreId(Long preId);
    public PromotionalEvent findByPreEventCode(String preEventCode);

    public boolean isDuplicateEventExisting(PromotionalEvent promotionalEvent);

    Page<PromotionalEvent> findByPreMerchantNo(Long merchantNo , Pageable pageable) throws InspireNetzException;
    Page<PromotionalEvent> findByPreMerchantNoAndPreEventType(Long merchantNo ,Integer preEventType, Pageable pageable) throws InspireNetzException;

    Page<PromotionalEvent> searchPromotionalEvents(String filter, String query , Long merchatNo,Pageable pageable);

    Page<PromotionalEvent> getValidEventsByDate(Long merchantNo, Date date,Pageable pageable);

    public PromotionalEvent validateAndSavePromotionalEvent(PromotionalEvent promotionalEvent) throws InspireNetzException;
    public PromotionalEvent savePromotionalEvent(PromotionalEvent promotionalEvent);

    public boolean validateAndDeletePromotionalEvent(Long preId) throws InspireNetzException;
    public boolean deletePromotionalEvent(PromotionalEvent promotionalEvent) throws InspireNetzException;

    public boolean validateAndDeletePromotionalEvent(PromotionalEvent promotionalEvent) throws InspireNetzException;

}
