package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

/**
 * Created by alameen on 8/11/14.
 */
public interface UserResponseService {

    public UserResponse saveUserResponse(UserResponse userResponse);

    public UserResponse findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(Long urpUserNo,Long urpResponseItemType,Long urpResponseItemId, Integer urpResponseType);

    public boolean deleteUserResponse(UserResponse userResponse);

    public Page<UserResponse> findByUrpResponseItemIdAndUrpResponseType(String filter,String query,Long urpResponseItemId,Integer urpResponseType,Pageable pageable) throws InspireNetzException;



}
