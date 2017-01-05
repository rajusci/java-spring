package com.inspirenetz.api.webservices;

import com.inspirenetz.api.core.dictionary.CouponListItem;
import com.inspirenetz.api.core.dictionary.CouponOperationResponse;
import com.inspirenetz.api.core.dictionary.SalesSKU;
import com.inspirenetz.api.rest.resource.CouponResource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by sandheepgr on 10/8/14.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CouponWebService extends SpringBeanAutowiringSupport {

    @WebMethod
    public CouponListItem[] listCoupons(@WebParam(name="mMin") String mMin,
                                        @WebParam(name="loyaltyId") String loyaltyId) {

        // Get the coupon Resource
        CouponListItem couponListItem[] = new CouponListItem[1];
        couponListItem[0] = new CouponListItem();

        return couponListItem;


    }


    @WebMethod
    public CouponOperationResponse getDiscount(
                                @WebParam(name="mMin") String mMin,
                                @WebParam(name = "couponcode") String couponCode,
                                @WebParam(name = "loyaltyid") String loyaltyId,
                                @WebParam(name = "txnamount") Double txnAmount,
                                @WebParam(name = "sku")SalesSKU salesSku[]
                             ) {

        CouponOperationResponse couponOperationResponse = new CouponOperationResponse();
        couponOperationResponse.setDiscount("10");
        couponOperationResponse.setErrorcode("");
        couponOperationResponse.setStatus("success");

        /**
         * error codes
         *
         * ERR_INVALID_COUPON - Coupon is not valid ( expired / not available for customer
         * ERR_NOT_APPLICABLE - Coupon is not applicable to the customer
         * ERR_COUPON_EXPIRED - Coupon has expired
         *
         */


        return  couponOperationResponse;
    }


    @WebMethod
    public CouponOperationResponse applyCoupon(
                            @WebParam(name="mMin") String mMin,
                            @WebParam(name = "couponcode") String couponCode,
                            @WebParam(name = "loyaltyid") String loyaltyId,
                            @WebParam(name = "txnamount") Double txnAmount,
                            @WebParam(name = "sku")SalesSKU salesSku[]
    ) {

        CouponOperationResponse couponOperationResponse = new CouponOperationResponse();
        couponOperationResponse.setDiscount("10");
        couponOperationResponse.setErrorcode("");
        couponOperationResponse.setStatus("success");

        /**
         * error codes
         *
         * ERR_INVALID_COUPON - Coupon is not valid ( expired / not available for customer
         * ERR_NOT_APPLICABLE - Coupon is not applicable to the customer
         * ERR_COUPON_EXPIRED - Coupon has expired
         *
         */


        return  couponOperationResponse;

    }





}
