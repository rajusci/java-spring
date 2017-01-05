package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public interface RoleAccessRightService extends BaseService<RoleAccessRight>{

    RoleAccessRight  findByRarId(Long rarId);
    List<RoleAccessRight> findByRarRole(Long rarRole);
    List<RoleAccessRight>  findByRarRoleAndRarFunctionCode(Long rarRole,Long rarFunctionCode);

    public RoleAccessRight validateAndSaveRoleAccessRight(RoleAccessRight roleAccessRight ) throws InspireNetzException;
    public RoleAccessRight saveRoleAccessRight(RoleAccessRight roleAccessRight);
    public boolean validateAndDeleteRoleAccessRight(Long rarId) throws InspireNetzException;
    public boolean deleteRoleAccessRight(Long rarId);

    public void deleteRoleAccessRightSet(Set<RoleAccessRight> roleAccessRightSet);

}
