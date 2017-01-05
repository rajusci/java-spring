package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.UserRole;

import java.util.List;

/**
 * Created by ameenci on 10/9/14.
 */
public interface UserRoleRepository extends BaseRepository<UserRole,Long> {

    public UserRole findByUerId(Long uerId);
    public List<UserRole> findByUerUserId(Long uerUserId);
}
