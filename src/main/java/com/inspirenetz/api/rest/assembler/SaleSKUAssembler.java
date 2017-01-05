package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityAssembler;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.core.service.SaleSKUService;
import com.inspirenetz.api.rest.controller.TransactionController;
import com.inspirenetz.api.rest.resource.SaleSKUResource;
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
public class SaleSKUAssembler extends ResourceAssemblerSupport<SaleSKU,SaleSKUResource> implements AttributeExtendedEntityAssembler<SaleSKU> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private SaleSKUService saleSKUService;

    @Autowired
    ProductService productService;


    public SaleSKUAssembler() {

        super(TransactionController.class,SaleSKUResource.class);

    }


    @Override
    public SaleSKUResource toResource(SaleSKU saleSKU) {

        // Create the SaleSKUResource
        SaleSKUResource saleSKUResource = mapper.map(saleSKU,SaleSKUResource.class);

        //find product name
        Product product=productService.findByPrdMerchantNoAndPrdCode(saleSKU.getMerchantNo()==null?0L:saleSKU.getMerchantNo(),saleSKU.getSsuProductCode());

        if(product !=null){

            saleSKUResource.setPrdName(product.getPrdName());
        }

        // Return the saleSKUResource
        return saleSKUResource;
    }

    /**
     * Function to convert the list of SaleSKU objects into an equivalent list
     * of SaleSKUResource objects
     *
     * @param saleSKUList - The List object for the SaleSKU objects
     * @return saleSKUResourceList - This list of SaleSKUResource objects
     */
    public List<SaleSKUResource> toResources(List<SaleSKU> saleSKUList,Long merchantNo) {

        // Create the list that will hold the resources
        List<SaleSKUResource> saleSKUResourceList = new ArrayList<SaleSKUResource>();

        // Create the SaleSKUResource object
        SaleSKUResource saleSKUResource = null;


        // Go through the saleSKUs and then create the saleSKU resource
        for(SaleSKU saleSKU : saleSKUList ) {

            // Get the SaleSKUResource
            saleSKU.setMerchantNo(merchantNo);

            saleSKUResource = toResource(saleSKU);

            // Add the resource to the array list
            saleSKUResourceList.add(saleSKUResource);

        }


        // return the saleSKUResoueceList
        return saleSKUResourceList;

    }





    @Override
    public AttributeExtendedEntityMap toAttibuteEntityMap(SaleSKU saleSKU) {

        // Create the SaleResource
        AttributeExtendedEntityMap saleSKUEntityMap = saleSKUService.toAttributeExtensionMap(saleSKU, AttributeExtensionMapType.ALL);

        // Return the saleSKUEntityMap
        return saleSKUEntityMap;

    }

    @Override
    public List<AttributeExtendedEntityMap> toAttibuteEntityMaps(Iterable<? extends SaleSKU> entities) {

        // Create the list that will hold the resources
        List<AttributeExtendedEntityMap> attributeExtendedEntityMapList = new ArrayList<AttributeExtendedEntityMap>();

        // Create the SaleResource object
        AttributeExtendedEntityMap attributeExtendedEntityMap = null;


        // Go through the saleSKU and then create the saleSKU resource
        for(SaleSKU saleSKU : entities ) {

            // Get the SaleResource
            attributeExtendedEntityMap = toAttibuteEntityMap(saleSKU);

            // Add the resource to the array list
            attributeExtendedEntityMapList.add(attributeExtendedEntityMap);

        }

        // return the saleResoueceList
        return attributeExtendedEntityMapList;

    }
}
