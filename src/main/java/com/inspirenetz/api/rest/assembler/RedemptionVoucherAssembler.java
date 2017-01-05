package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.rest.controller.RedemptionVoucherController;
import com.inspirenetz.api.rest.resource.RedemptionVoucherResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneeshci on 25/9/14.
 */
@Component
public class RedemptionVoucherAssembler extends ResourceAssemblerSupport<RedemptionVoucher,RedemptionVoucherResource> {

    @Autowired
    private Mapper mapper;

    public RedemptionVoucherAssembler() {

        super(RedemptionVoucherController.class,RedemptionVoucherResource.class);

    }

    @Override
    public RedemptionVoucherResource toResource(RedemptionVoucher role) {

        // Create the RedemptionVoucherResource
        RedemptionVoucherResource roleResource = mapper.map(role,RedemptionVoucherResource.class);

        // Return the roleResource
        return roleResource;
    }


    /**
     * Function to convert the list of RedemptionVoucher objects into an equivalent list
     * of RedemptionVoucherResource objects
     *
     * @param redemptionVoucherList - The List object for the RedemptionVoucher objects
     * @return roleResourceList - This list of RedemptionVoucherResource objects
     */
    public List<RedemptionVoucherResource> toResources(List<RedemptionVoucher> redemptionVoucherList) {

        // Create the list that will hold the resources
        List<RedemptionVoucherResource> redemptionVoucherResources = new ArrayList<RedemptionVoucherResource>(0);

        // Create the RedemptionVoucherResource object
        RedemptionVoucherResource redemptionVoucherResource = null;

        // Go through the roles and then create the role resource
        for(RedemptionVoucher redemptionVoucher : redemptionVoucherList ) {

            redemptionVoucherResource = toResource(redemptionVoucher);

            // Add the resource to the array list
            redemptionVoucherResources.add(redemptionVoucherResource);

        }

        // return the roleResoueceList
        return redemptionVoucherResources;

    }

}
