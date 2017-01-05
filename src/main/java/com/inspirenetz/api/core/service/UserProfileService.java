package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.rest.exception.InspireNetzException;

/**
 * Created by alameen on 24/10/14.
 */
public interface UserProfileService extends BaseService<UserProfile> {

    public UserProfile findByUspUserNo(Long uspUserNo);

    public UserProfile saveUserProfile(UserProfile userProfiler) throws InspireNetzException;

    public boolean delete(UserProfile userProfile);
}
