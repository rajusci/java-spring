package com.inspirenetz.api.core.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.IDN;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private static Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);


    private final Class< T > clazz;

    public BaseServiceImpl( final Class< T > clazzToSet ){
        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    @Override
    public T create(T entity) {
        T target = getDao().save(entity);
        return target;
    }

    @Override
    public T update(T entity) {
        T target = getDao().save(entity);
        return target;
    }

    @Override
    public List<T> findAll() {
        List<T> targets = Lists.newArrayList(getDao().findAll());
        return targets;
    }

    /*
    @Override
    public T findByUid(String uid) {
        T target = getDao().findByUid(uid);
        return target;
    }
    */

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    /*
    @Override
    public void deleteByUid(String uid) {
        getDao().delete(getDao().findByUid(uid));
    }
    */

    @Override
    public T suspend(T entity){
        return entity;
    }

    @Override
    public List<T> saveAll(List<T> tList){
        return Lists.newArrayList(getDao().save(tList));

    }

    protected abstract BaseRepository<T,Long> getDao();
}