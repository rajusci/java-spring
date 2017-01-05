package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ModuleRepository extends  BaseRepository<Module,Long> {


    public Module findByMdlId(Long mdlId);
    public Module findByMdlName(String mdlName);
    public Page<Module> findAll(Pageable pageable);
    public Page<Module> findByMdlNameLike(String mdlName, Pageable pageable);
    public List<Module> findByMdlNameLike(String mdlName);



}
