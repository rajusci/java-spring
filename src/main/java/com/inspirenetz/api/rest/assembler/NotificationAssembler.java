package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.rest.controller.CustomerActivityController;
import com.inspirenetz.api.rest.controller.NotificationController;
import com.inspirenetz.api.rest.resource.CustomerActivityResource;
import com.inspirenetz.api.rest.resource.NotificationResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayizkci on 17/3/15.
 */
@Component
public class NotificationAssembler extends ResourceAssemblerSupport<Notification,NotificationResource> {


    @Autowired
    private Mapper mapper;

    public NotificationAssembler() {

        super(NotificationController.class,NotificationResource.class);

    }

    @Override
    public NotificationResource toResource(Notification notification) {

        // Create the NotificationResource
        NotificationResource notificationResource = mapper.map(notification,NotificationResource.class);

        // Return the notificationResource
        return notificationResource;
    }


    /**
     * Function to convert the list of Notification objects into an equivalent list
     * of NotificationResource objects
     *
     * @param notificationList - The List object for the Notification objects
     * @return notificationResource - This list of NotificationResource objects
     */
    public List<NotificationResource> toResources(List<Notification> notificationList) {

        // Create the list that will hold the resources
        List<NotificationResource> notificationResourceList = new ArrayList<NotificationResource>();

        // Create the NotificationResource object
        NotificationResource notificationResource = null;


        // Go through the notifications and then create the notificationResource resource
        for(Notification notification : notificationList ) {

            // Get the NotificationResource
            notificationResource = toResource(notification);

            // Add the resource to the array list
            notificationResourceList.add(notificationResource);

        }


        // return the notificationResourceList
        return notificationResourceList;

    }

}
