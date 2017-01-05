package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.RoleAccessRight;

import java.util.List;

/**
 * Created by ameenci on 9/9/14.
 */
public interface RoleAccessRightRepository extends BaseRepository<RoleAccessRight,Long>{

    public RoleAccessRight findByRarId(Long rarId);
    public List<RoleAccessRight>  findByRarRole(Long rarRole);
    public List<RoleAccessRight>  findByRarRoleAndRarFunctionCode(Long rarRole,Long rarFunctionCode);



}
