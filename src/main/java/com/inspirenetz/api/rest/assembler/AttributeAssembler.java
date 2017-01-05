package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.rest.controller.AttributeController;
import com.inspirenetz.api.rest.resource.AttributeResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 16/9/14.
 */
@Component
public class AttributeAssembler extends ResourceAssemblerSupport<Attribute,AttributeResource> {

    @Autowired
    private Mapper mapper;

    public AttributeAssembler() {

        super(AttributeController.class,AttributeResource.class);

    }

    @Override
    public AttributeResource toResource(Attribute attribute) {

        AttributeResource attributeResource= mapper.map(attribute, AttributeResource.class);

        return attributeResource;

    }

    /**
     * Function to convert the list of Attributel objects into an equivalent list
     * of AttributelResource objects
     *
     * @param  attributes- The List object for the Attributel objects
     * @return AttributelList - This list of AttributelResource objects
     */
    public List<AttributeResource> toResources(List<Attribute> attributes) {

        // Create the list that will hold the resources
        List<AttributeResource> attributeResourceList = new ArrayList<AttributeResource>();

        // Create the AttributelResource object
        AttributeResource attributeResource = null;


        // Go through the Attributel and then create the Attributel Resource
        for(Attribute attribute : attributes ) {

            // Get the message spiel resource
            attributeResource = toResource(attribute);

            // Add the resource to the array list
            attributeResourceList.add(attributeResource);

        }


        // return the AttributelResourceList
        return attributeResourceList;

    }
}
