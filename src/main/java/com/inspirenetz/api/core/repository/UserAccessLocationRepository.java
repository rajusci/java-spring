package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.UserAccessLocation;

import java.util.List;

/**
 * Created by ameenci on 10/9/14.
 */
public interface UserAccessLocationRepository  extends  BaseRepository<UserAccessLocation ,Long> {


   public UserAccessLocation findByUalId(Long ualId);
   public List<UserAccessLocation> findByUalUserId(Long ualUserId);

}
