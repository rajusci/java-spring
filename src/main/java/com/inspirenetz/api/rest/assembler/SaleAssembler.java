package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityAssembler;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.SaleService;
import com.inspirenetz.api.rest.controller.SaleController;
import com.inspirenetz.api.rest.resource.SaleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sandheepgr on 27/4/14.
 */
@Component
public class SaleAssembler extends ResourceAssemblerSupport<Sale,SaleResource> implements AttributeExtendedEntityAssembler<Sale> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private SaleService saleService;


    public SaleAssembler() {

        super(SaleController.class,SaleResource.class);
        
    }



    @Override
    public  AttributeExtendedEntityMap toAttibuteEntityMap(Sale sale) {

        // Create the SaleResource
        AttributeExtendedEntityMap saleResource = saleService.toAttributeExtensionMap(sale, AttributeExtensionMapType.ALL);

        // Return the saleResource
        return saleResource;

    }



    /**
     * Function to convert the list of Sale objects into an equivalent list
     * of SaleResource objects
     *
     * @param saleList - The List object for the Sale objects
     * @return saleResourceList - This list of SaleResource objects
     */
    public List<AttributeExtendedEntityMap> toAttibuteEntityMaps(Iterable<? extends Sale> saleList) {

        // Create the list that will hold the resources
        List<AttributeExtendedEntityMap> attributeExtendedEntityMapList = new ArrayList<AttributeExtendedEntityMap>();

        // Create the SaleResource object
        AttributeExtendedEntityMap attributeExtendedEntityMap = null;


        // Go through the sales and then create the sale resource
        for(Sale sale : saleList ) {

            // Get the SaleResource
            attributeExtendedEntityMap = toAttibuteEntityMap(sale);

            // Add the resource to the array list
            attributeExtendedEntityMapList.add(attributeExtendedEntityMap);

        }

        // return the saleResoueceList
        return attributeExtendedEntityMapList;

    }



    @Override
    public SaleResource toResource(Sale sale) {

        // Create the SaleResource
        SaleResource saleResource = mapper.map(sale,SaleResource.class);

        // Return the saleResource
        return saleResource;
    }



    /**
     * Function to convert the list of Sale objects into an equivalent list
     * of SaleResource objects
     *
     * @param saleList - The List object for the Sale objects
     * @return saleResourceList - This list of SaleResource objects
     */
    public List<SaleResource> toResources(List<Sale> saleList) {

        // Create the list that will hold the resources
        List<SaleResource> saleResourceList = new ArrayList<SaleResource>();

        // Create the SaleResource object
        SaleResource saleResource = null;


        // Go through the sales and then create the sale resource
        for(Sale sale : saleList ) {

            // Get the SaleResource
            saleResource = mapper.map(sale,SaleResource.class);

            // Add the resource to the array list
            saleResourceList.add(saleResource);

        }


        // return the saleResoueceList
        return saleResourceList;

    }



}
