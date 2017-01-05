package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.PaymentStatus;
import com.inspirenetz.api.rest.controller.PaymentStatusController;
import com.inspirenetz.api.rest.resource.PaymentStatusResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 15/4/14.
 */
@Component
public class PaymentStatusAssembler extends ResourceAssemblerSupport<PaymentStatus,PaymentStatusResource> {

    @Autowired
    private Mapper mapper;

    public PaymentStatusAssembler() {

        super(PaymentStatusController.class,PaymentStatusResource.class);

    }

    @Override
    public PaymentStatusResource toResource(PaymentStatus paymentStatus) {

        // Create the PaymentStatusResource
        PaymentStatusResource paymentStatusResource = mapper.map(paymentStatus,PaymentStatusResource.class);

        // Return the paymentStatusResource
        return paymentStatusResource;
    }


    /**
     * Function to convert the list of PaymentStatus objects into an equivalent list
     * of PaymentStatusResource objects
     *
     * @param paymentStatusList - The List object for the PaymentStatus objects
     * @return paymentStatusResourceList - This list of PaymentStatusResource objects
     */
    public List<PaymentStatusResource> toResources(List<PaymentStatus> paymentStatusList) {

        // Create the list that will hold the resources
        List<PaymentStatusResource> paymentStatusResourceList = new ArrayList<PaymentStatusResource>();

        // Create the PaymentStatusResource object
        PaymentStatusResource paymentStatusResource = null;


        // Go through the paymentStatuss and then create the paymentStatus resource
        for(PaymentStatus paymentStatus : paymentStatusList ) {

            // Get the PaymentStatusResource
            paymentStatusResource = mapper.map(paymentStatus,PaymentStatusResource.class);

            // Add the resource to the array list
            paymentStatusResourceList.add(paymentStatusResource);

        }


        // return the paymentStatusResoueceList
        return paymentStatusResourceList;

    }

}
