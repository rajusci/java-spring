package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.rest.controller.ModuleController;
import com.inspirenetz.api.rest.resource.ModuleResource;
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
public class ModuleAssembler extends ResourceAssemblerSupport<Module,ModuleResource> {

    @Autowired
    private Mapper mapper;

    public ModuleAssembler() {

        super(ModuleController.class,ModuleResource.class);

    }

    @Override
    public ModuleResource toResource(Module module) {

        // Create the ModuleResource
        ModuleResource moduleResource = mapper.map(module,ModuleResource.class);

        // Return the moduleResource
        return moduleResource;
    }


    /**
     * Function to convert the list of Module objects into an equivalent list
     * of ModuleResource objects
     *
     * @param moduleList - The List object for the Module objects
     * @return moduleResourceList - This list of ModuleResource objects
     */
    public List<ModuleResource> toResources(List<Module> moduleList) {

        // Create the list that will hold the resources
        List<ModuleResource> moduleResourceList = new ArrayList<ModuleResource>();

        // Create the ModuleResource object
        ModuleResource moduleResource = null;


        // Go through the modules and then create the module resource
        for(Module module : moduleList ) {

            // Get the ModuleResource
            moduleResource = mapper.map(module,ModuleResource.class);

            // Add the resource to the array list
            moduleResourceList.add(moduleResource);

        }


        // return the moduleResoueceList
        return moduleResourceList;

    }

}
