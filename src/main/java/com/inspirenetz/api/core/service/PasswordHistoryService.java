package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.PasswordHistory;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.Date;
import java.util.List;

/**
 * Created by ameenci on 9/10/14.
 */
public interface PasswordHistoryService extends BaseService<PasswordHistory> {

    public PasswordHistory savePasswordHistory(Long usrUserNo,String usrUserPassword);

    public List<PasswordHistory> findByPasHistoryUserNo(Long pasHistoryUserNo);

    public Date findByMaxPasChangedAt(Long pasHistoryUserNo);

    public boolean delete(Long passwordHistoryId);

    public boolean matchedPreviousHistory(Long passHistoryCount,String password, Long pasHistoryUserNo) throws InspireNetzException;

    public boolean isPasswordExpired(User user) throws InspireNetzException;

    public boolean setLoggedInDetails(User user);


}
