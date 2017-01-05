package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AttributeRepository extends  BaseRepository<Attribute,Long> {

    public Attribute findByAtrId(Long atrId);
    public Attribute findByAtrName(String atrName);
    public List<Attribute> findByAtrEntity(Integer atrEntity);
    public List<Attribute> findAll();
    public Page<Attribute> findByAtrNameLike(String atrName,Pageable pageable);
    public Page<Attribute> findAll(Pageable pageable);

}
