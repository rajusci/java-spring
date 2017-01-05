package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface RoleService extends BaseService<Role> {


    public Role findByRolId(Long rolId) throws InspireNetzException;
    public Page<Role> searchRoles(String filter,String query,Pageable pageable);
    public List<Role> getRolesByUserType(Integer userType) throws InspireNetzException;

    public Role validateAndSaveRole(Role role) throws InspireNetzException;
    public Role saveRole(Role role);
    public boolean validateAndDeleteRole(Long rolId) throws InspireNetzException;
    public boolean deleteRole(Long rolId);

    public List<Role> getAllRoles() throws InspireNetzException;



}
