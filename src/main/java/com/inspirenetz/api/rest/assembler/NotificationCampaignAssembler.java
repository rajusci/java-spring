package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.rest.controller.NotificationCampaignController;
import com.inspirenetz.api.rest.resource.NotificationCampaignResource;
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
public class NotificationCampaignAssembler extends ResourceAssemblerSupport<NotificationCampaign,NotificationCampaignResource> {

    @Autowired
    private Mapper mapper;

    public NotificationCampaignAssembler() {

        super(NotificationCampaignController.class,NotificationCampaignResource.class);

    }

    @Override
    public NotificationCampaignResource toResource(NotificationCampaign drawChance) {

        // Create the NotificationCampaignResource
        NotificationCampaignResource drawChanceResource = mapper.map(drawChance,NotificationCampaignResource.class);

        // Return the drawChanceResource
        return drawChanceResource;
    }


    /**
     * Function to convert the list of NotificationCampaign objects into an equivalent list
     * of NotificationCampaignResource objects
     *
     * @param drawChanceList - The List object for the NotificationCampaign objects
     * @return drawChanceResourceList - This list of NotificationCampaignResource objects
     */
    public List<NotificationCampaignResource> toResources(List<NotificationCampaign> drawChanceList) {

        // Create the list that will hold the resources
        List<NotificationCampaignResource> drawChanceResourceList = new ArrayList<NotificationCampaignResource>();

        // Create the NotificationCampaignResource object
        NotificationCampaignResource drawChanceResource = null;


        // Go through the drawChances and then create the drawChance resource
        for(NotificationCampaign drawChance : drawChanceList ) {

            // Add the resource to the array list
            drawChanceResourceList.add(toResource(drawChance));

        }


        // return the drawChanceResoueceList
        return drawChanceResourceList;

    }

}
