package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.LinkRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class LinkRequestValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return LinkRequest.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the LinkRequestObject
        LinkRequest linkRequest = (LinkRequest)target;

        // Check the source customer number
        if (  linkRequest.getLrqSourceCustomer() == null | linkRequest.getLrqSourceCustomer() == 0L ) {

            errors.rejectValue("lrqSourceCustomer","{linkrequest.lrqsourcecustomer.notnull}");

        }


        // Check the parent customer
        if ( linkRequest.getLrqParentCustomer() == null || linkRequest.getLrqParentCustomer().equals("") ) {

            errors.rejectValue("lrqParentCustomer","{linkrequest.lrqparentcustomer.notempty}");

        }



    }
}
