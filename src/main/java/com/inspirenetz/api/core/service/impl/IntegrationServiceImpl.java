package com.inspirenetz.api.core.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.IntegrationService;
import com.inspirenetz.api.core.service.MerchantSettingService;
import com.inspirenetz.api.util.APIResponseObject;
import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by sandheepgr on 14/9/14.
 */
@Service
public class IntegrationServiceImpl implements IntegrationService {

    private static Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment environment;

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Override
    public APIResponseObject placeRestGetAPICall(String url, Map<String, String> variables) {

        // Create the response object
        APIResponseObject retData = new APIResponseObject();


        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Create a HttpHeader indicating to use the ipbased auth
        HttpHeaders httpHeaders = new HttpHeaders();

        // Set the header value
        httpHeaders.set("in-username","system");

        // Create the HttpEntity
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        // Call the entity method
        entity = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET,entity,String.class,variables);


        // Check if the entity is not null and statusCode is 200
        if ( entity == null ) {

            // Set the reData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode as failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        } else  {

            // Create an object mapper
            try {

                retData = mapper.readValue(entity.getBody(),APIResponseObject.class);

            } catch (IOException e) {

                // Print the stacktrace
                e.printStackTrace();

                // Set the reData to failed
                retData.setData(APIResponseStatus.failed);

                // Set the errorcode as failed
                retData.setErrorCode(APIErrorCode.ERR_EXCEPTION);

            }

        }

        // Return the retData object
        return retData;
    }

    @Override
    public APIResponseObject placeRestPostAPICall(String url, Map<String, String> params) {


        MultiValueMap<String,String> postParams = new LinkedMultiValueMap<String,String>(0);

        // Set the params
        postParams.setAll(params);

        // Create the response object
        APIResponseObject retData = new APIResponseObject();

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Cer
        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();

        ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<>(0);

        messageConverters.add(formHttpMessageConverter);
        messageConverters.add(stringHttpMessageConverter);


        restTemplate.setMessageConverters(messageConverters);

        // Create a HttpHeader indicating to use the ipbased auth
        HttpHeaders httpHeaders = new HttpHeaders();

        // Set the header value
        httpHeaders.set("in-username","system");

        // Create the HttpEntity
        HttpEntity<?> requestEntity =
                new HttpEntity<MultiValueMap<String,String>>(postParams, httpHeaders);

        // Call the entity method
        ResponseEntity responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.POST,requestEntity,String.class);


        // Check if the entity is not null and statusCode is 200
        if ( responseEntity == null ) {

            // Set the reData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode as failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        } else  {

            // Create an object mapper
            try {

                retData = mapper.readValue(responseEntity.getBody().toString(),APIResponseObject.class);

            } catch (IOException e) {

                // Print the stacktrace
                e.printStackTrace();

                // Set the reData to failed
                retData.setData(APIResponseStatus.failed);

                // Set the errorcode as failed
                retData.setErrorCode(APIErrorCode.ERR_EXCEPTION);

            }

        }

        // Return the retData object
        return retData;
    }

    @Override
    public boolean integrateJasperServerUserCreation(User user,Long merchantNo) throws RuntimeException {

        //boolean jasper integration flag
        boolean jasperIntegration =false;

        try {

            //check the user enable jasper server settings
            boolean merchantUserSettings =merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_JASPER_USER,merchantNo);

            //if its call the jasper creation integartion call
            if(merchantUserSettings){

                //get jasper url
                String jasperUrl =environment.getProperty("jasper.jasperurl");

                //get jasper username and  password
                String jasperUserName =environment.getProperty("jasper.username");

                String jasperPassword =environment.getProperty("jasper.password");

                //create jasper rest client object
                RestClientConfiguration configuration = new RestClientConfiguration(jasperUrl);

                //configure into jasper client
                JasperserverRestClient client = new JasperserverRestClient(configuration);

                //set authentication session
                Session session = client.authenticate(jasperUserName, jasperPassword);

                //create client user object
                ClientUser jasperUser = new ClientUser()
                        .setUsername(user.getUsrUserNo().toString())
                        .setPassword(user.getUsrUserNo().toString())

                        .setEnabled(true)
                        .setExternallyDefined(false)
                        .setFullName(user.getUsrFName());

                client
                        .authenticate(jasperUserName, jasperPassword)
                        .usersService()
                        .username(jasperUser.getUsername())
                        .createOrUpdate(jasperUser);


                Set<ClientRole> roles = new HashSet<>();

                ClientRole defaultRole = client
                        .authenticate(jasperUserName, jasperPassword)
                        .rolesService()
                        .roleName("ROLE_USER")
                        .get()
                        .getEntity();

                roles.add(defaultRole);

                //get jasper user role user role from settings
                MerchantSetting merchantSetting =null;

                //defined the jasper user role
                if(user.getUsrUserType() == UserType.MERCHANT_ADMIN){

                    merchantSetting=merchantSettingService.getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_JASPER_ADMIN_ROLE,merchantNo);

                }else {

                    merchantSetting=merchantSettingService.getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_JASPER_USER_ROLE,merchantNo);
                }


                //check settings is null or not
                if(merchantSetting !=null){

                    //get the value
                    String jasperRole =(merchantSetting.getMesValue() ==null|| merchantSetting.getMesValue().equals(""))?"":merchantSetting.getMesValue();

                    if(!jasperRole.equals("")){

                        //Granting new user with admin role
                        ClientRole role = client
                                .authenticate(jasperUserName, jasperPassword)
                                .rolesService()
                                .roleName(jasperRole)
                                .get()
                                .getEntity();

                        //set role for user
                        roles.add(role);
                        jasperUser.setRoleSet(roles);

                    }

                }
                client
                        .authenticate(jasperUserName, jasperPassword)
                        .usersService()
                        .username(jasperUser.getUsername())
                        .createOrUpdate(jasperUser);
            }

            //log the information
            log.info("Jasper Integration Call successfully execute with the user number"+user.getUsrUserNo());

            jasperIntegration =true;

        } catch(Exception e){

            log.info("Exception occured for creating jasper user userno is:"+user.getUsrUserNo());

        }
        return jasperIntegration;
    }

    @Override
    public APIResponseObject placeRestJSONPostAPICall(String url, String json) {

        // Create the response object
        APIResponseObject retData = new APIResponseObject();

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Cer
        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();

        ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<>(0);

        messageConverters.add(formHttpMessageConverter);
        messageConverters.add(stringHttpMessageConverter);

        restTemplate.setMessageConverters(messageConverters);

        // Create a HttpHeader indicating to use the ipbased auth
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Set the header value
        httpHeaders.set("in-username","system");

        HttpEntity<String> entity = new HttpEntity<String>(json,httpHeaders);

        // Call the entity method
        ResponseEntity responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.POST,entity,String.class);


        // Check if the responseEntity is not null
        if ( responseEntity == null ) {

            return null;

        } else  {

            // Create an object mapper
            try {

                retData = mapper.readValue(responseEntity.getBody().toString(),APIResponseObject.class);

            } catch (IOException e) {

                // Print the stacktrace
                e.printStackTrace();

                // Set the reData to failed
                retData.setData(APIResponseStatus.failed);

                // Set the errorcode as failed
                retData.setErrorCode(APIErrorCode.ERR_EXCEPTION);

            }

        }

        // Return the retData object
        return retData;

    }

}
