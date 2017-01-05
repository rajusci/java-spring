package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.rest.controller.OrderSlotController;
import com.inspirenetz.api.rest.resource.OrderSlotResource;
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
public class OrderSlotAssembler extends ResourceAssemblerSupport<OrderSlot,OrderSlotResource> {

    @Autowired
    private Mapper mapper;

    public OrderSlotAssembler() {

        super(OrderSlotController.class,OrderSlotResource.class);

    }

    @Override
    public OrderSlotResource toResource(OrderSlot orderSlot) {

        // Create the OrderSlotResource
        OrderSlotResource orderSlotResource = mapper.map(orderSlot,OrderSlotResource.class);

        // Return the orderSlotResource
        return orderSlotResource;
    }


    /**
     * Function to convert the list of OrderSlot objects into an equivalent list
     * of OrderSlotResource objects
     *
     * @param orderSlotList - The List object for the OrderSlot objects
     * @return orderSlotResourceList - This list of OrderSlotResource objects
     */
    public List<OrderSlotResource> toResources(List<OrderSlot> orderSlotList) {

        // Create the list that will hold the resources
        List<OrderSlotResource> orderSlotResourceList = new ArrayList<OrderSlotResource>();

        // Create the OrderSlotResource object
        OrderSlotResource orderSlotResource = null;


        // Go through the orderSlots and then create the orderSlot resource
        for(OrderSlot orderSlot : orderSlotList ) {

            // Get the OrderSlotResource
            orderSlotResource = mapper.map(orderSlot,OrderSlotResource.class);

            // Add the resource to the array list
            orderSlotResourceList.add(orderSlotResource);

        }


        // return the orderSlotResoueceList
        return orderSlotResourceList;

    }

}
