package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.UserAccessRight;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface UserAccessRightRepository extends  BaseRepository<UserAccessRight,Long> {

    public List<UserAccessRight> findByUarUserNo(Long uarUserNo);
    public UserAccessRight findByUarUarId(Long uarUarId);
    public UserAccessRight findByUarUserNoAndUarFunctionCode(Long uarUserNo,Long uarFunctionCode);

}
