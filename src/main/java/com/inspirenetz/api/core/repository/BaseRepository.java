package com.inspirenetz.api.core.repository;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by sandheepgr on 16/2/14.
 */
public interface BaseRepository<T, ID extends java.io.Serializable> extends CrudRepository<T, ID> {
    //public T findByUid(String uid);
    //public void deleteByUid(String uid);
}
