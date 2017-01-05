package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 12/3/14.
 */
public interface UserRepository extends BaseRepository<User,Long> {

    public User findByUsrUserNo(Long usrUserNo);
    public User findByUsrLoginId(String usrLoginId);
    public User findByUsrEmail(String usrEmail);
    public User findByUsrMobile(String usrMobile);
    public User findByUsrLoginIdOrUsrEmail(String usrLoginId,String usrEmail);
    public User findByUsrLoginIdAndUsrUserType(String usrLoginId, Integer usrUserTYpe);
    public User findByUsrLoginIdAndUsrRegisterStatus(String usrLoginId,int usrRegisterStatus);
    public User findByUsrFNameAndUsrMerchantNo(String usrFName,Long usrMerchantNo);

    public Page<User> findByUsrUserTypeAndUsrLoginIdLike(Integer usrUserType,String usrLoginId,Pageable pageable);
    public Page<User> findByUsrUserTypeAndUsrFNameLike(Integer usrUserType,String usrFName,Pageable pageable);

    public List<User> findByUsrMerchantNo(Long usrMerchantNo);
    public Page<User> findByUsrUserType(Integer usrUserType,Pageable pageable);

    @Query("select U from User U where U.usrMerchantNo = ?1 and ( ?2 = 0 or U.usrUserType = ?2 ) ")
    public Page<User> findByUsrMerchantNo(Long usrMerchantNo, Integer usrUserType, Pageable pageable);

    @Query("select U from User U where U.usrMerchantNo = ?1 and ( ?2 = 0 or U.usrUserType = ?2 ) and U.usrFName like ?3 ")
    public Page<User> findByUsrMerchantNoAndUsrFNameLike(Long usrMerchantNo, Integer usrUserType, String usrFName, Pageable pageable);

    @Query("select U from User U where U.usrMerchantNo = ?1 and ( ?2 = 0 or U.usrUserType = ?2 ) and U.usrLoginId like ?3 ")
    public Page<User> findByUsrMerchantNoAndUsrLoginIdLike(Long usrMerchantNo, Integer usrUserType, String usrLoginId, Pageable pageable);

    @Query("select U from User U where U.usrMerchantNo = ?1 and ( U.usrUserType = ?2 ) and U.usrLoginId like ?3 ")
    public Page<User> findByUsrMerchantNoAndUsrUserTypeAndUsrLoginIdLike(Long usrMerchantNo, Integer usrUserType, String usrLoginId, Pageable pageable);

    @Query("select U from User U where U.usrMerchantNo = ?1 and ( U.usrUserType = ?2 ) and (U.usrThirdPartyVendorNo =?3 ) and  U.usrLoginId like ?4 ")
    public Page<User> findByUsrMerchantNoAndUsrUserTypeAndUsrThirdPartyVendorNoAndUsrLoginIdLike(Long usrMerchantNo, Integer usrUserType,Long usrThirdPartyVendorNo, String usrLoginId, Pageable pageable);

    @Query("select U from User U where (U.usrUserType = 2 or U.usrUserType = 3) and  U.usrLoginId like ?1 ")
    public Page<User> findByUsrLoginIdLike(String usrLoginId, Pageable pageable);

    @Query("select U from User U where (U.usrUserType = 2 or U.usrUserType = 3) and  U.usrFName like ?1 ")
    public Page<User> findByUsrFNameLike(String usrFName, Pageable pageable);

    @Query("select U from User U where U.usrUserType = 2 or U.usrUserType = 3 ")
    public Page<User> findByUser( Pageable pageable);

    public Page<User> findByUsrUserTypeAndUsrThirdPartyVendorNo(Integer usrUserType,Long usrThirdPartyVendorNo,Pageable pageable);

    public List<User> findByUsrUserTypeAndUsrThirdPartyVendorNo(Integer usrUserType,Long usrThirdPartyVendorNo);

    public List<User> findByUsrUserTypeAndUsrMerchantNo(int userType,Long usrMerchantNo);

    public User findByUsrUserCode(String usrUserCode);

}

