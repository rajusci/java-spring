package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.domain.SaleSKUExtension;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SaleSKURepository;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.core.service.SaleSKUService;
import com.inspirenetz.api.util.AttributeExtensionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class SaleSKUServiceImpl extends BaseServiceImpl<SaleSKU> implements SaleSKUService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    SaleSKURepository saleSKURepository;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;



    public SaleSKUServiceImpl() {

        super(SaleSKU.class);

    }


    @Override
    protected BaseRepository<SaleSKU,Long> getDao() {
        return saleSKURepository;
    }



    @Override
    public List<SaleSKU> findBySsuSaleId(Long ssuSaleId) {

        // Get the data from the repository and store in the list
        List<SaleSKU> saleSKUList = saleSKURepository.findBySsuSaleId(ssuSaleId);

        // Return the list
        return saleSKUList;

    }

    @Override
    public SaleSKU findBySsuId(Long ssuId) {

        // Get the saleSKU for the given saleSKU id from the repository
        SaleSKU saleSKU = saleSKURepository.findBySsuId(ssuId);

        // Return the saleSKU
        return saleSKU;

    }



    @Override
    public SaleSKU saveSaleSku(SaleSKU saleSKU) {

        // Save the SaleSku object
        return saleSKURepository.save(saleSKU);

    }

    @Override
    public boolean deleteSaleSku(Long ssuId) {

        // Delete the SaleSKu
        saleSKURepository.delete(ssuId);

        // Return true
        return true;

    }


    /**
     * Function to get the SaleSKU set from the parameters passed as list of AttributeExtendedEntityMap
     *
     * @param params    - The list of parameters
     * @return          - The Set of SaleSKU objects
     */
    @Override
    public Set<SaleSKU> getSaleSKUFromParams(List<AttributeExtendedEntityMap> params) {

        // The set holding the data
        Set<SaleSKU> saleSKUs = new HashSet<>(0);

        // Go through the list and then map the content
        for ( AttributeExtendedEntityMap attributeExtendedEntityMap : params ) {

            // Create new SaleSKU object
            SaleSKU saleSKU = new SaleSKU();

            // Create the object from map
            fromAttributeExtensionMap(saleSKU,attributeExtendedEntityMap,AttributeExtensionMapType.ALL);

            // Add to the list
            saleSKUs.add(saleSKU);

        }

        return saleSKUs;

    }




    @Override
    public AttributeExtendedEntityMap toAttributeExtensionMap(Object obj, Integer attributeExtensionMapType) {

        // Get the SaleSKU object
        SaleSKU saleSKU = (SaleSKU) obj;

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = attributeExtensionUtils.getAttributeExtensionMapForObject(saleSKU,saleSKU.getSaleSKUExtensionSet(),attributeExtensionMapType);

        // Return the attributeExtendedEntityMap object
        return attributeExtendedEntityMap;

    }

    @Override
    public Object fromAttributeExtensionMap(Object obj, AttributeExtendedEntityMap attributeExtendedEntityMap, Integer attributeExtensionMapType) {

        // Get the attributes map
        AttributeMap attributeMap = attributeService.getAttributesMapByName(0);

        // Call teh function
        SaleSKU saleSKU = (SaleSKU) attributeExtensionUtils.createEntityFromAttributeExtensionMap(obj,attributeMap,attributeExtendedEntityMap,attributeExtensionMapType,this);

        // return the SaleSKU object
        return saleSKU;

    }

    @Override
    public void setExtFieldValue(Object obj, Attribute attribute, String value) {

        // Get the saleSKU Object
        SaleSKU saleSKU = (SaleSKU) obj;

        // Check if the field is set
        boolean isSet = attributeExtensionUtils.setExtFieldValue(saleSKU.getSaleSKUExtensionSet(),attribute,value);


        // Check if the isSet is true, otherwise , we need to set the data in the extFields
        if ( !isSet ) {

            // If the saleSKUInfoSet is null, then intialize it
            if ( saleSKU.getSaleSKUExtensionSet() == null ) {

                saleSKU.setSaleSKUExtensionSet(new HashSet<SaleSKUExtension>(0));

            }

            // Create the SaleSKUExtension object
            SaleSKUExtension saleSKUExtension = new SaleSKUExtension();

            // Set the attribute id
            saleSKUExtension.setAttrId(attribute.getAtrId());

            // Set the value
            saleSKUExtension.setAttrValue(value.toString());

            // Set the attribute
            saleSKUExtension.setAttribute(attribute);

            // Add to the set
            saleSKU.getSaleSKUExtensionSet().add(saleSKUExtension);
        }

    }

    @Override
    public String getExtFieldValue(Object obj, Attribute attribute) {

        // Get the SaleSKU object
        SaleSKU saleSKU = (SaleSKU)obj;

        // Return the attribute
        return attributeExtensionUtils.getExtFieldValue(saleSKU.getSaleSKUExtensionSet(), attribute);


    }

    /**
     * created by :Fayizkci
     * purpose:delete saleSKU
     * @param saleSKUSet
     */
    public void deleteSaleSKUSet(Set<SaleSKU> saleSKUSet){

        saleSKURepository.delete(saleSKUSet);

    }
}
