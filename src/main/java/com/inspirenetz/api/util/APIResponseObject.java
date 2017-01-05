package com.inspirenetz.api.util;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class APIResponseObject extends HashMap {

    public APIResponseObject() {
        super();

        setData("");
        setStatus(APIResponseStatus.success);
    }


    public void setData(Object object) {

        put("data",object);

    }

    public Object getData() {

        return get("data");

    }

    public void setErrorCode(APIErrorCode code) {

        put("errorcode",code);

    }

    public String getErrorCode() {

        return get("errorcode").toString();

    }

    public void setStatus(APIResponseStatus status) {

        put("status",status);

    }

    public String getStatus() {

        return get("status").toString();

    }

    public void setErrorDesc(String errordesc) {

        put("errordesc",errordesc);

    }

    public void setPageNumber(int pageNumber) {

        put("pagenumber",Integer.toString(pageNumber));

    }

    public void setTotalPages(int totalPages) {

        put("totalpages",Integer.toString(totalPages));

    }

    public void setTotalElements(Long totalElements) {

        put("totalelements",totalElements.toString());

    }

    public void setBalance(Object object) {

        put("balance",object);

    }


    /**
     * Function to set the pageable parameters to the object
     *
     * @param pageObj   - The Page object from which we need to set the information
     */
    public void setPageableParams(Page pageObj) {

        if(pageObj ==null){

         return;

        }
        // Set the current page number
        setPageNumber(pageObj.getNumber());

        // Set the total pages
        setTotalPages(pageObj.getTotalPages());

        // Set the total elements in all pages
        setTotalElements(pageObj.getTotalElements());

    }
}
