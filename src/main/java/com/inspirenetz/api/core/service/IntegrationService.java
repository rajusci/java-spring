package com.inspirenetz.api.core.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.util.APIResponseObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by sandheepgr on 14/9/14.
 */
public interface IntegrationService {

    public APIResponseObject placeRestGetAPICall(String url, Map<String, String> variables);
    public APIResponseObject placeRestPostAPICall(String url, Map<String, String> params);
    public boolean integrateJasperServerUserCreation(User user,Long merchantNo);
    public APIResponseObject placeRestJSONPostAPICall(String url, String json);

}
