package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by alameen on 8/11/14.
 */
public interface UserResponseRepository extends  BaseRepository<UserResponse,Long>  {

    public UserResponse findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(Long urpUserNo,Long urpResponseItemType,Long urpResponseItemId, Integer urpResponseType);

    public Page<UserResponse> findByUrpResponseItemIdAndUrpResponseType(Long urpResponseItemId,Integer urpResponseType,Pageable pageable);

    public Page<UserResponse> findByUrpUserNoAndUrpResponseTypeAndUrpResponseItemId(Long urpUserNo,Integer urpResponseType,Long urpItemId,Pageable pageable);

}
