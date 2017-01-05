package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.rest.controller.FunctionController;
import com.inspirenetz.api.rest.resource.FunctionResource;
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
public class FunctionAssembler extends ResourceAssemblerSupport<Function,FunctionResource> {

    @Autowired
    private Mapper mapper;

    public FunctionAssembler() {

        super(FunctionController.class,FunctionResource.class);

    }

    @Override
    public FunctionResource toResource(Function function) {

        // Create the FunctionResource
        FunctionResource functionResource = mapper.map(function,FunctionResource.class);

        // Return the functionResource
        return functionResource;
    }


    /**
     * Function to convert the list of Function objects into an equivalent list
     * of FunctionResource objects
     *
     * @param functionList - The List object for the Function objects
     * @return functionResourceList - This list of FunctionResource objects
     */
    public List<FunctionResource> toResources(List<Function> functionList) {

        // Create the list that will hold the resources
        List<FunctionResource> functionResourceList = new ArrayList<FunctionResource>();

        // Create the FunctionResource object
        FunctionResource functionResource = null;


        // Go through the functions and then create the function resource
        for(Function function : functionList ) {

            // Get the FunctionResource
            functionResource = mapper.map(function,FunctionResource.class);

            // Add the resource to the array list
            functionResourceList.add(functionResource);

        }


        // return the functionResoueceList
        return functionResourceList;

    }

}
