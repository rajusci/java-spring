package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.PasswordHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by ameenci on 9/10/14.
 */
public interface PasswordHistoryRepository extends BaseRepository<PasswordHistory,Long> {

    public List<PasswordHistory> findByPasHistoryUserNo(Long pasHistoryUserNo);

    @Query("select max(p.pasChangedAt) from PasswordHistory p where p.pasHistoryUserNo=?1")
    public Date findByMaxPasChangedAt(Long pasHistoryUserNo);

    @Query("select p from PasswordHistory p where p.pasHistoryUserNo=?1 order by p.pasChangedAt desc")
    public List<PasswordHistory> findByLastChangedAtDate(Long pasHistoryUserNo);


}
