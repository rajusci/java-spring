package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface FunctionRepository extends  BaseRepository<Function,Long> {


    public Function findByFncFunctionCode(Long fncFunctionCode);
    public Function findByFncFunctionName(String fncFunctionName);
    public List<Function> findAll();
    public Page<Function> findAll(Pageable pageable);
    public Page<Function> findByFncFunctionNameLike(String fncFunctionName, Pageable pageable);

    @Query("select f from Function f where f.fncAdminEnabled=?1 or f.fncMerchantAdminEnabled=?2 or f.fncMerchantUserEnabled=?3 or f.fncCustomerEnabled=?4 or f.fncVendorUserEnabled =?5 ")
    public List<Function> getFunctionsForUserType(String adminEnabled,String mAdminEnabled,String mUserEnabled,String customerEnabled,String fncVendorUserEnabled);

    @Query("select f from Function f where (f.fncAdminEnabled=?1 or f.fncMerchantAdminEnabled=?2 or f.fncMerchantUserEnabled=?3 or f.fncCustomerEnabled=?4) and f.fncFunctionName like ?5")
    public List<Function> findByUserTypeAndFunctionNameLike(String adminEnabled,String mAdminEnabled,String mUserEnabled,String customerEnabled,String fncFunctionName);



}
