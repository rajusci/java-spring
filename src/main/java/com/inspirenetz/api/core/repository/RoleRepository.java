package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface RoleRepository extends  BaseRepository<Role,Long> {

    public Role findByRolId(Long rolId);
    @Query("select R from Role R where  R.rolIsDefaultRole = 1 or R.rolMerchantNo = ?1")
    public List<Role> getAllRoles(Long rolMerchantNo);

    public Page<Role> findByRolMerchantNo(Long rolMerchantNo,Pageable pageable);

    public Page<Role> findByRolMerchantNoAndRolNameLike(Long rolMerchantNo,String rolName,Pageable pageable);

    public List<Role> findByRolUserType(Integer rolUserType);
}
