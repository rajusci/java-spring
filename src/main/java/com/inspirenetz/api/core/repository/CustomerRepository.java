package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
public interface CustomerRepository extends BaseRepository<Customer, Long>{

    public Customer findByCusLoyaltyIdAndCusMerchantNo(String cusLoyaltyId, Long cusMerchantNo);
    public Customer findByCusMobileAndCusMerchantNo(String cusMobile, Long cusMerchantNo);
    public Customer findByCusEmailAndCusMerchantNo(String cusEmail, Long cusMerchantNo);
    public Customer findByCusCustomerNo(Long cusCustomerNo);
    public List<Customer> findByCusUserNo(Long cusUserNo);
    public List<Customer> findByCusMobile(String cusMobile);
    public Customer findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatus(String cusLoyaltyId,Long cusMerchantNo,Integer cusRegisterStatus);
    public Customer findByCusMerchantNoAndCusMobile(Long cusMerchantNo,String cusMobile);
    public List<Customer> findByCusUserNoAndCusRegisterStatus(Long usrUserNo,int cusRegisterStatus);
    public Page<Customer> findByCusMerchantNo(Long cusMerchantNo,Pageable pageable);
    public Page<Customer> findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatus(String cusFName,Long cusMerchantNo,Integer cusRegisterStatus,Pageable pageable);
    public Page<Customer> findByCusLNameLikeAndCusMerchantNo(String cusLName,Long cusMerchantNo,Pageable pageable);
    public Page<Customer> findByCusMobileLikeAndCusMerchantNo(String cusMobile,Long cusMerchantNo,Pageable pageable);
    public Page<Customer> findByCusEmailLikeAndCusMerchantNo(String cusEmail,Long cusMerchantNo,Pageable pageable);
    public Page<Customer> findByCusLoyaltyIdLikeAndCusMerchantNo(String cusLoyaltyId,Long cusMerchantNo,Pageable pageable);
    public List<Customer> findByCusUserNoOrCusEmailOrCusMobileAndCusRegisterStatus(Long cusUserNo,String cusEmail,String cusMobile,Integer cusRegisterStatus);


    public Page<Customer> findByCusFNameLikeAndCusMerchantNo(String cusFName,Long cusMerchantNo,Pageable pageable);


    @Query("select C from Customer C where C.cusMerchantNo = ?1  and (C.cusLocation =?2 or C.cusLocation=?3)")
    public Page<Customer> findByCusMerchantNoAndCusLocationOrCusLocation(Long cusMerchantNo,Long cusLocation,Long userLocation,Pageable pageable);

    @Query("select C from Customer C where C.cusFName like?1 and C.cusMerchantNo = ?2 and C.cusRegisterStatus=?3 and (C.cusLocation =?4 or C.cusLocation=?5)")
    public Page<Customer> findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocationOrCusLocation(String cusFName,Long cusMerchantNo,Integer cusRegisterStatus,Long cusLocation,Long userLocation,Pageable pageable);

    @Query("select C from Customer C where C.cusFName like?1 and C.cusMerchantNo = ?2  and (C.cusLocation =?3 or C.cusLocation=?4)")
    public Page<Customer> findByCusFNameLikeAndCusMerchantNoAndCusLocationOrCusLocation(String cusFName,Long cusMerchantNo,Long cusLocation,Long userLocation,Pageable pageable);

    @Query("select C from Customer C where C.cusLName like?1 and C.cusMerchantNo = ?2  and (C.cusLocation =?3 or C.cusLocation=?4)")
    public Page<Customer> findByCusLNameLikeAndCusMerchantNoAndCusLocationOrCusLocation(String cusLName,Long cusMerchantNo,Long cusLocation,Long userLocation,Pageable pageable);

    @Query("select C from Customer C where C.cusMobile like?1 and C.cusMerchantNo = ?2  and (C.cusLocation =?3 or C.cusLocation=?4)")
    public Page<Customer> findByCusMobileLikeAndCusMerchantNoAndCusLocationOrCusLocation(String cusMobile,Long cusMerchantNo,Long cusLocation,Long userLocation,Pageable pageable);

    @Query("select C from Customer C where C.cusEmail like?1 and C.cusMerchantNo = ?2  and (C.cusLocation =?3 or C.cusLocation=?4)")
    public Page<Customer> findByCusEmailLikeAndCusMerchantNoAndCusLocationOrCusLocation(String cusEmail,Long cusMerchantNo,Long cusLocation,Long userLocation,Pageable pageable);

    @Query("select C from Customer C where C.cusLoyaltyId like?1 and C.cusMerchantNo = ?2 and (C.cusLocation =?3 or C.cusLocation=?4)")
    public Page<Customer> findByCusLoyaltyIdLikeAndCusMerchantNoAndCusLocationOrCusLocation(String cusLoyaltyId,Long cusMerchantNo,Long cusLocation,Long userLocation,Pageable pageable);


    @Query("select C from Customer C where C.cusMerchantNo = ?1 and ( ?2 = 0L or C.cusLocation = ?2 ) and C.cusMerchantUserRegistered =?3 and C.cusRegisterStatus=?4 ")
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatus(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,int cusRegisterStatus,Pageable pageable);

    @Query("select C from Customer C where C.cusMerchantNo = ?1 and ( ?2 = 0L or C.cusLocation = ?2 ) and C.cusMerchantUserRegistered =?3 and C.cusRegisterStatus=?4 and C.cusFName like ?5 ")
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusFNameLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,int cusRegisterStatus,String cusFName,Pageable pageable);

    @Query("select C from Customer C where C.cusMerchantNo = ?1 and ( ?2 = 0L or C.cusLocation = ?2 ) and C.cusMerchantUserRegistered =?3 and C.cusRegisterStatus=?4 and C.cusLName like ?5 ")
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusLNameLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,int cusRegisterStatus,String cusLName,Pageable pageable);


    @Query("select C from Customer C where C.cusMerchantNo = ?1 and ( ?2 = 0L or C.cusLocation = ?2 ) and C.cusMerchantUserRegistered =?3 and  C.cusRegisterStatus=?4 and C.cusMobile like ?5 ")
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusMobileLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,int cusRegisterStatus,String cusMobile,Pageable pageable);

    @Query("select C from Customer C where C.cusMerchantNo = ?1 and ( ?2 = 0L or C.cusLocation = ?2 ) and C.cusMerchantUserRegistered =?3 and  C.cusRegisterStatus=?4 and C.cusEmail like ?5 ")
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusEmailLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,int cusRegisterStatus,String cusEmail,Pageable pageable);

    @Query("select C from Customer C where C.cusMerchantNo = ?1 and ( ?2 = 0L or C.cusLocation = ?2 ) and C.cusMerchantUserRegistered =?3 and  C.cusRegisterStatus=?4 and C.cusLoyaltyId like ?5 ")
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusLoyaltyIdLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,int cusRegisterStatus,String cusLoyaltyId,Pageable pageable);

    //@Query("select c from Customer c where c.cusMerchantNo=?1 and ( (c.cusLoyaltyId <> '' and c.cusLoyaltyId = ?2) or (c.cusEmail <> '' and c.cusEmail = ?3)  or (c.cusMobile <> '' and c.cusMobile = ?4))")
    //public List<Customer> findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(Long cusMerchantNo,String cusLoyaltyId,String cusEmail , String cusMobile);

    @Query("select c from Customer c where c.cusMerchantNo = ?1 and ( (c.cusLoyaltyId <> '' and c.cusLoyaltyId = ?2)  or (c.cusEmail <> '' and c.cusEmail = ?3)  or (c.cusMobile <> '' and c.cusMobile = ?4) ) and (c.cusRegisterStatus=1)")
    public List<Customer>  findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(Long cusMerchantNo,String cusLoyaltyId,String cusEmail,String cusMobile);

    public List<Customer> findByCusMerchantNo(Long cusMerchantNo);

    public List<Customer> findByCusMobileAndCusRegisterStatus(String cusMobile,Integer cusRegisterStatus);

    public List<Customer> findByCusUserNoAndCusStatus(Long cusUserNo,Integer cusStatus);

    public List<Customer> findByCusMerchantNoAndCusUserNoAndCusStatus(Long cusMerchantNo,Long cusUserNo,Integer cusStatus);

    public Customer findByCusUserNoAndCusStatusAndCusMerchantNo(Long cusUserNo,int cusStatus,Long CusMerchant);
    public List<Customer> findByCusLoyaltyId(String cusLoyaltyId);
    public Customer findByCusUserNoAndCusMerchantNo(Long cusUserNo,Long cusMerchantNo);
    public Customer findByCusUserNoAndCusMerchantNoAndCusRegisterStatus(Long cusUserNo,Long cusMerchantNo,int cusRegisterStatus);
    public Customer findByCusMerchantNoAndCusMobileAndCusRegisterStatus(Long cusMerchantNo,String cusMobile,int cusRegisterStatus);
    public Customer findByCusMerchantNoAndCusEmailAndCusRegisterStatus(Long cusMerchantNo,String cusEmail,int cusRegisterStatus);



}
