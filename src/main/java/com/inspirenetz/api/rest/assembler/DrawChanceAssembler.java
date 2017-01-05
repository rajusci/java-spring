package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.rest.controller.DrawChanceController;
import com.inspirenetz.api.rest.resource.DrawChanceResource;
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
public class DrawChanceAssembler extends ResourceAssemblerSupport<DrawChance,DrawChanceResource> {

    @Autowired
    private Mapper mapper;

    public DrawChanceAssembler() {

        super(DrawChanceController.class,DrawChanceResource.class);

    }

    @Override
    public DrawChanceResource toResource(DrawChance drawChance) {

        // Create the DrawChanceResource
        DrawChanceResource drawChanceResource = mapper.map(drawChance,DrawChanceResource.class);

        // Return the drawChanceResource
        return drawChanceResource;
    }


    /**
     * Function to convert the list of DrawChance objects into an equivalent list
     * of DrawChanceResource objects
     *
     * @param drawChanceList - The List object for the DrawChance objects
     * @return drawChanceResourceList - This list of DrawChanceResource objects
     */
    public List<DrawChanceResource> toResources(List<DrawChance> drawChanceList) {

        // Create the list that will hold the resources
        List<DrawChanceResource> drawChanceResourceList = new ArrayList<DrawChanceResource>();

        // Create the DrawChanceResource object
        DrawChanceResource drawChanceResource = null;


        // Go through the drawChances and then create the drawChance resource
        for(DrawChance drawChance : drawChanceList ) {

            // Add the resource to the array list
            drawChanceResourceList.add(toResource(drawChance));

        }


        // return the drawChanceResoueceList
        return drawChanceResourceList;

    }

}
