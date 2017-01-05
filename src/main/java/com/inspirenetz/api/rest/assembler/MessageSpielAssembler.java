package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.rest.controller.MessageSpielController;
import com.inspirenetz.api.rest.resource.MessageSpielResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 9/9/14.
 */
@Component
public class MessageSpielAssembler  extends ResourceAssemblerSupport<MessageSpiel,MessageSpielResource> {


    @Autowired
    private Mapper mapper;

    public MessageSpielAssembler() {

        super(MessageSpielController.class,MessageSpielResource.class);

    }

    @Override
    public MessageSpielResource toResource(MessageSpiel messageSpiel) {

        MessageSpielResource messageSpielResource= mapper.map(messageSpiel, MessageSpielResource.class);

        return messageSpielResource;

    }

    /**
     * Function to convert the list of MessageSpiel objects into an equivalent list
     * of MessageSpielResource objects
     *
     * @param  messageSpiels- The List object for the MessageSpiel objects
     * @return MessageSpielList - This list of MessageSpielResource objects
     */
    public List<MessageSpielResource> toResources(List<MessageSpiel> messageSpiels) {

        // Create the list that will hold the resources
        List<MessageSpielResource> messageSpielResourceList = new ArrayList<MessageSpielResource>();

        // Create the messageSpielResource object
        MessageSpielResource messageSpielResource = null;


        // Go through the messageSpiel and then create the messageSpiel Resource
        for(MessageSpiel messageSpiel : messageSpiels ) {

            // Get the message spiel resource
            messageSpielResource = toResource(messageSpiel);

            // Add the resource to the array list
            messageSpielResourceList.add(messageSpielResource);

        }


        // return the messageSpielResourceList
        return messageSpielResourceList;

    }
}
