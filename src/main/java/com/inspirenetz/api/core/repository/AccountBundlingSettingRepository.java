package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AccountBundlingSettingRepository extends  BaseRepository<AccountBundlingSetting,Long> {


    public AccountBundlingSetting findByAbsId(Long absId);
    public List<AccountBundlingSetting>  findByAbsMerchantNo(Long absMerchantNo);
    public AccountBundlingSetting findByAbsMerchantNoAndAbsLocation(Long absMerchantNo, Long absLocation);

    @Query("select max(p.pasChangedAt) from PasswordHistory p where p.pasHistoryUserNo=?1")
    public Integer  findByMaxPasCha(Long pasHistoryUserNo);

    public List<AccountBundlingSetting> findAll();
}
