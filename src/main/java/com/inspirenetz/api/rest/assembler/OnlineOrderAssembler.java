package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.rest.controller.OnlineOrderController;
import com.inspirenetz.api.rest.resource.OnlineOrderResource;
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
public class OnlineOrderAssembler extends ResourceAssemblerSupport<OnlineOrder,OnlineOrderResource> {

    @Autowired
    private Mapper mapper;

    public OnlineOrderAssembler() {

        super(OnlineOrderController.class,OnlineOrderResource.class);

    }

    @Override
    public OnlineOrderResource toResource(OnlineOrder onlineOrder) {

        // Create the OnlineOrderResource
        OnlineOrderResource onlineOrderResource = mapper.map(onlineOrder,OnlineOrderResource.class);

        // Return the onlineOrderResource
        return onlineOrderResource;
    }


    /**
     * Function to convert the list of OnlineOrder objects into an equivalent list
     * of OnlineOrderResource objects
     *
     * @param onlineOrderList - The List object for the OnlineOrder objects
     * @return onlineOrderResourceList - This list of OnlineOrderResource objects
     */
    public List<OnlineOrderResource> toResources(List<OnlineOrder> onlineOrderList) {

        // Create the list that will hold the resources
        List<OnlineOrderResource> onlineOrderResourceList = new ArrayList<OnlineOrderResource>();

        // Create the OnlineOrderResource object
        OnlineOrderResource onlineOrderResource = null;


        // Go through the onlineOrders and then create the onlineOrder resource
        for(OnlineOrder onlineOrder : onlineOrderList ) {

            // Get the OnlineOrderResource
            onlineOrderResource = mapper.map(onlineOrder,OnlineOrderResource.class);

            // Add the resource to the array list
            onlineOrderResourceList.add(onlineOrderResource);

        }


        // return the onlineOrderResoueceList
        return onlineOrderResourceList;

    }

}
