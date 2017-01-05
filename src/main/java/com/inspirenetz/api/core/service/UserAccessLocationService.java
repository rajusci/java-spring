package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public interface UserAccessLocationService  extends BaseService<UserAccessLocation> {

    public UserAccessLocation findByUalId(Long ualId);
    public List<UserAccessLocation> findByUalUserId(Long ualUserId);

    public boolean validateAndDeleteUserAccessLocation(Long ualId) throws InspireNetzException;
    public UserAccessLocation validateAndSaveUserAccessLocation(UserAccessLocation userAccessLocation ) throws InspireNetzException;
    public UserAccessLocation saveUserAccessLocation(UserAccessLocation userAccessLocation);
    public boolean deleteUserAccessLocation(Long ualId);
    public boolean deleteUserAccessLocationSet(Set<UserAccessLocation> userAccessLocations);
    public boolean updateUserAccessLocation(User user);
}
