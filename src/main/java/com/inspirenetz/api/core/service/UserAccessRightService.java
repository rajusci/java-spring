package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface UserAccessRightService extends BaseService<UserAccessRight> {

    public List<UserAccessRight> findByUarUserNo(Long uarUserNo);
    public UserAccessRight findByUarUarId(Long uarUarId);
    public UserAccessRight findByUarUserNoAndUarFunctionCode(Long uarUserNo,Long uarFunctionCode);
    public boolean isDuplicateUserAccessRightExisting(UserAccessRight userAccessRight);
    public HashMap<Long,String> getUarAsMap(Long uarUserNo) throws InspireNetzException;

    public UserAccessRight saveUserAccessRight(UserAccessRight userAccessRight);
    public boolean deleteUserAccessRight(Long uarId);
    public boolean deleteUserAccessRightSet(Set<UserAccessRight> userAccessRights);
    public boolean updateUserAccessRights(User user);
    public Set<UserAccessRight> setUserAccessRights(User user);

}
