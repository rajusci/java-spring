package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.UserResponseRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserResponseService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alameen on 8/11/14.
 */
@Service
public class UserResponseServiceImpl extends BaseServiceImpl<UserResponse> implements UserResponseService {


    // Create the Logger
    private static Logger log = LoggerFactory.getLogger(UserResponseServiceImpl.class);

    @Autowired
    private UserResponseRepository userResponseRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthSessionUtils authSessionUtils;



    public UserResponseServiceImpl() {

        super(UserResponse.class);

    }

    @Override
    protected BaseRepository<UserResponse, Long> getDao() {
        return userResponseRepository;
    }

    @Override
    public UserResponse saveUserResponse(UserResponse userResponse) {


        return userResponseRepository.save(userResponse);
    }

    @Override
    public UserResponse findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(Long urpUserNo, Long urpResponseItemType, Long urpResponseItemId,Integer urpResponseType) {

        UserResponse userResponse;

        userResponse =userResponseRepository.findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(urpUserNo,urpResponseItemType,urpResponseItemId,urpResponseType);

        return userResponse;
    }

    @Override
    public boolean deleteUserResponse(UserResponse userResponse) {

        userResponseRepository.delete(userResponse);

        return true;
    }

    /**
     * @Purpose for searching user response
     * @param filter: filter of search
     * @param query:Query of search
     * @param urpResponseItemId
     * @param urpResponseType
     * @param pageable
     * @return User Response
     * @throws InspireNetzException
     */
    @Override
    public Page<UserResponse> findByUrpResponseItemIdAndUrpResponseType(String filter,String query,Long urpResponseItemId, Integer urpResponseType,Pageable pageable) throws InspireNetzException {

        List<UserResponse> userResponseList =new ArrayList<>();

        Page<UserResponse> userResponsePage =null;

        if(query.equals("0")){

            Long merchantNo=authSessionUtils.getMerchantNo();
            //if query is zero fetch all data from user response based on the product and response type
            userResponsePage =userResponseRepository.findByUrpResponseItemIdAndUrpResponseType(urpResponseItemId,urpResponseType,pageable);

            //if we get only id and we want display loyalty id and customer name
            for(UserResponse userResponse:userResponsePage){

                //find user number each customer from user response table
                Long userNo =userResponse.getUrpUserNo();

                //find customer information from customer using the user number
                Customer customer1 = customerService.findByCusUserNoAndCusMerchantNo(userNo,merchantNo);

                //if customer is not null fetch the customer information
                if(customer1 !=null){

                    userResponse.setCusFName(customer1.getCusFName());

                    userResponse.setCusLName(customer1.getCusLName());

                    userResponse.setCusLoyaltyId(customer1.getCusLoyaltyId());

                    userResponseList.add(userResponse);


                }

                //return of function is pageable then convert list to pageable
                userResponsePage = new PageImpl<>(userResponseList);

            }
        }else if(filter.equalsIgnoreCase("loyaltyid")){

            //filter is not zero then find appropreate information based on the loyalty id
            Long merchantNo =authSessionUtils.getMerchantNo();

            //find customer information based on the loyalty id
            Customer customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(query,merchantNo);


            if(customer !=null){

                //if customer is not null get the information for user response based on the customer user number
                userResponsePage = userResponseRepository.findByUrpUserNoAndUrpResponseTypeAndUrpResponseItemId(customer.getCusUserNo(), urpResponseType, urpResponseItemId, pageable);

                for(UserResponse userResponse:userResponsePage){

                    Long userNo =userResponse.getUrpUserNo();

                    //get the customer information based on the user number
                    Customer customer1 = customerService.findByCusUserNoAndCusMerchantNo(userNo,merchantNo);

                    if(customer1 !=null){


                        userResponse.setCusFName(customer1.getCusFName());

                        userResponse.setCusLName(customer1.getCusLName());

                        userResponse.setCusLoyaltyId(customer1.getCusLoyaltyId());

                        userResponseList.add(userResponse);


                    }

                    //return type is pageable convert List to pageable object
                    userResponsePage = new PageImpl<>(userResponseList);
                }



            }else{

                // Log the response
                log.info("respons Search  - Response : Invalid Input - ");

                // Throw InspireNetzException with INVALID customer  as error
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }



        }


        return userResponsePage;
    }
       
}
