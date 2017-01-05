package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface CustomerActivityRepository extends  BaseRepository<CustomerActivity,Long> {

    public CustomerActivity findByCuaId(Long cuaId);
    public Page<CustomerActivity> findByCuaMerchantNoAndCuaRecordStatus(Long cuaMerchantNo,Integer cuaRecordStatus, Pageable pageable);
    public List<CustomerActivity> findByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatus(Long cuaMerchantNo,String cuaLoyaltyId,Integer cuaRecordStatus);
    public Page<CustomerActivity> findByCuaMerchantNoAndCuaRecordStatusAndCuaDateBetween(Long cuaMerchantNo,Integer cuaRecordStatus,Date fromDate,Date toDate, Pageable pageable);
    public Page<CustomerActivity> findByCuaMerchantNoAndCuaLoyaltyIdAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(Long cuaMerchantNo,String cuaLoyaltyId,Integer cuaActivityType,Integer cuaRecordStatus,Date cuaDate,Date toDate, Pageable pageable);
    public Page<CustomerActivity> findByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween(Long cuaMerchantNo,String cuaLoyaltyId,Integer cuaRecordStatus,Date cuaDate,Date toDate, Pageable pageable);
    public Page<CustomerActivity> findByCuaMerchantNoAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(Long cuaMerchantNo,Integer cuaActivityType,Integer cuaRecordStatus,Date cuaDate,Date toDate, Pageable pageable);

    public Page<CustomerActivity> findByCuaRecordStatusAndCuaDateBetween(Integer cuaRecordStatus,Date fromDate,Date toDate, Pageable pageable);
    public Page<CustomerActivity> findByCuaLoyaltyIdAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(String cuaLoyaltyId,Integer cuaActivityType,Integer cuaRecordStatus,Date cuaDate,Date toDate, Pageable pageable);
    public Page<CustomerActivity> findByCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween(String cuaLoyaltyId,Integer cuaRecordStatus,Date cuaDate,Date toDate, Pageable pageable);
    public Page<CustomerActivity> findByCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(Integer cuaActivityType,Integer cuaRecordStatus,Date cuaDate,Date toDate, Pageable pageable);



    @Query("select CA from CustomerActivity CA where  ( ?1 = '"+0+"' or CA.cuaLoyaltyId = ?1 ) and ( ?2 = 0 or CA.cuaActivityType = ?2) and CA.cuaDate between ?3 and ?4 and CA.cuaMerchantNo = ?5 and CA.cuaRecordStatus = ?6")
    public Page<CustomerActivity> searchActivities(String cuaLoyaltyId,Integer cuaActivityType,Date fromDate, Date toDate,Long cuaMerchantNo,Integer cuaRecordStatus,Pageable pageable);

}
