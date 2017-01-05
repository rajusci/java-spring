package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Setting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SettingRepository extends  BaseRepository<Setting,Long> {


    public Setting findBySetId(Long setId);
    public Setting findBySetName(String setName);
    public Page<Setting> findAll(Pageable pageable);
    public Page<Setting> findBySetNameLike(String setName, Pageable pageable);

    public List<Setting> findBySetSessionStoreInd(Integer indicatorStatus);
    public List<Setting> findBySetNameLike(String setName);

}
