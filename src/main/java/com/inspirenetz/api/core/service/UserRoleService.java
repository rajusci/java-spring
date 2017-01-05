package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public interface UserRoleService extends BaseService<UserRole> {

    UserRole findByUerId(Long uerId);
    List<UserRole> findByUerUserId(Long uerUserId);

    public UserRole saveUserRole(UserRole userRole);
    public boolean deleteUserRole(Long uarId);
    public UserRole validateAndSaveUserRole(UserRole userRole ) throws InspireNetzException;
    public boolean validateAndDeleteUserRole(Long uerId) throws InspireNetzException;
    public boolean deleteUserRoleSet(Set<UserRole> userRoles);

    public boolean updateUserRole(User user);

}
