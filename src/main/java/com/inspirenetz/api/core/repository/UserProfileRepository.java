package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.UserProfile;

/**
 * Created by alameen on 24/10/14.
 */
public interface UserProfileRepository extends BaseRepository<UserProfile,Long>{

    public UserProfile findByUspUserNo(Long uspUserNo);
}
