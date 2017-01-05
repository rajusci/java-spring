package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityAssembler;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.LoyaltyProgramSkuService;
import com.inspirenetz.api.rest.controller.LoyaltyProgramSkuController;
import com.inspirenetz.api.rest.resource.LoyaltyProgramSkuResource;
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
public class LoyaltyProgramSkuAssembler extends ResourceAssemblerSupport<LoyaltyProgramSku,LoyaltyProgramSkuResource> implements AttributeExtendedEntityAssembler<LoyaltyProgramSku> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;


    public LoyaltyProgramSkuAssembler() {

        super(LoyaltyProgramSkuController.class,LoyaltyProgramSkuResource.class);

    }



    @Override
    public AttributeExtendedEntityMap toAttibuteEntityMap(LoyaltyProgramSku loyaltyProgramSku) {

        // Create the LoyaltyProgramSkuResource
        AttributeExtendedEntityMap loyaltyProgramSkuResource = loyaltyProgramSkuService.toAttributeExtensionMap(loyaltyProgramSku, AttributeExtensionMapType.ALL);

        // Return the loyaltyProgramSkuResource
        return loyaltyProgramSkuResource;

    }



    /**
     * Function to convert the list of LoyaltyProgramSku objects into an equivalent list
     * of LoyaltyProgramSkuResource objects
     *
     * @param loyaltyProgramSkuList - The List object for the LoyaltyProgramSku objects
     * @return loyaltyProgramSkuResourceList - This list of LoyaltyProgramSkuResource objects
     */
    public List<AttributeExtendedEntityMap> toAttibuteEntityMaps(Iterable<? extends LoyaltyProgramSku> loyaltyProgramSkuList) {

        // Create the list that will hold the resources
        List<AttributeExtendedEntityMap> attributeExtendedEntityMapList = new ArrayList<AttributeExtendedEntityMap>();

        // Create the LoyaltyProgramSkuResource object
        AttributeExtendedEntityMap attributeExtendedEntityMap = null;


        // Go through the loyaltyProgramSkus and then create the loyaltyProgramSku resource
        for(LoyaltyProgramSku loyaltyProgramSku : loyaltyProgramSkuList ) {

            // Get the LoyaltyProgramSkuResource
            attributeExtendedEntityMap = toAttibuteEntityMap(loyaltyProgramSku);

            // Add the resource to the array list
            attributeExtendedEntityMapList.add(attributeExtendedEntityMap);

        }

        // return the loyaltyProgramSkuResoueceList
        return attributeExtendedEntityMapList;

    }

    

    @Override
    public LoyaltyProgramSkuResource toResource(LoyaltyProgramSku loyaltyProgramSku) {

        // Create the LoyaltyProgramSkuResource
        LoyaltyProgramSkuResource loyaltyProgramSkuResource = mapper.map(loyaltyProgramSku,LoyaltyProgramSkuResource.class);

        // Return the loyaltyProgramSkuResource
        return loyaltyProgramSkuResource;
    }


    /**
     * Function to convert the list of LoyaltyProgramSku objects into an equivalent list
     * of LoyaltyProgramSkuResource objects
     *
     * @param loyaltyProgramSkuList - The List object for the LoyaltyProgramSku objects
     * @return loyaltyProgramSkuResourceList - This list of LoyaltyProgramSkuResource objects
     */
    public List<LoyaltyProgramSkuResource> toResources(List<LoyaltyProgramSku> loyaltyProgramSkuList) {

        // Create the list that will hold the resources
        List<LoyaltyProgramSkuResource> loyaltyProgramSkuResourceList = new ArrayList<LoyaltyProgramSkuResource>();

        // Create the LoyaltyProgramSkuResource object
        LoyaltyProgramSkuResource loyaltyProgramSkuResource = null;


        // Go through the loyaltyProgramSkus and then create the loyaltyProgramSku resource
        for(LoyaltyProgramSku loyaltyProgramSku : loyaltyProgramSkuList ) {

            // Get the LoyaltyProgramSkuResource
            loyaltyProgramSkuResource = mapper.map(loyaltyProgramSku,LoyaltyProgramSkuResource.class);

            // Add the resource to the array list
            loyaltyProgramSkuResourceList.add(loyaltyProgramSkuResource);

        }


        // return the loyaltyProgramSkuResoueceList
        return loyaltyProgramSkuResourceList;

    }

}
