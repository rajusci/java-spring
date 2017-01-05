package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.rest.controller.PromotionalEventController;
import com.inspirenetz.api.rest.resource.PromotionalEventResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneehsci on 29/9/14.
 */
@Component
public class PromotionalEventAssembler extends ResourceAssemblerSupport<PromotionalEvent,PromotionalEventResource> {

    @Autowired
    private Mapper mapper;

    public PromotionalEventAssembler() {

        super(PromotionalEventController.class,PromotionalEventResource.class);

    }

    @Override
    public PromotionalEventResource toResource(PromotionalEvent promotionalEvent) {

        // Create the PromotionalEventResource
        PromotionalEventResource promotionalEventResource = mapper.map(promotionalEvent,PromotionalEventResource.class);

        // Return the promotionalEventResource
        return promotionalEventResource;
    }


    /**
     * Function to convert the list of PromotionalEvent objects into an equivalent list
     * of PromotionalEventResource objects
     *
     * @param promotionalEventList - The List object for the PromotionalEvent objects
     * @return promotionalEventResourceList - This list of PromotionalEventResource objects
     */
    public List<PromotionalEventResource> toResources(List<PromotionalEvent> promotionalEventList) {

        // Create the list that will hold the resources
        List<PromotionalEventResource> promotionalEventResourceList = new ArrayList<PromotionalEventResource>();

        // Create the PromotionalEventResource object
        PromotionalEventResource promotionalEventResource = null;


        // Go through the promotionalEvents and then create the promotionalEvent resource
        for(PromotionalEvent promotionalEvent : promotionalEventList ) {

            // Add the resource to the array list
            promotionalEventResourceList.add(mapper.map(promotionalEvent,PromotionalEventResource.class));

        }


        // return the promotionalEventResoueceList
        return promotionalEventResourceList;

    }

}
