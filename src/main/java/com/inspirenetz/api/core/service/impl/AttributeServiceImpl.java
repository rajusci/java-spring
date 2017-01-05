package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.AttributeRepository;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AttributeExtensionUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class AttributeServiceImpl extends BaseServiceImpl<Attribute> implements AttributeService {


    private static Logger log = LoggerFactory.getLogger(AttributeServiceImpl.class);


    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;


    public AttributeServiceImpl() {

        super(Attribute.class);

    }


    @Override
    protected BaseRepository<Attribute,Long> getDao() {
        return attributeRepository;
    }



    @Override
    public Attribute findByAtrId(Long atrId) {

        // Get the attribute for the given attribute id from the repository
        Attribute attribute = attributeRepository.findByAtrId(atrId);

        // Return the attribute
        return attribute;


    }

    @Override
    public List<Attribute> findByAtrEntity(Integer atrEntity) {

        // Get the List of attributes for the given entity
        List<Attribute> attributeList = attributeRepository.findByAtrEntity(atrEntity);

        // return the list
        return attributeList;

    }

    @Override
    public Attribute findByAtrName(String atrName) {

        // Get the Attribute by the name of the attribute
        Attribute attribute =  attributeRepository.findByAtrName(atrName);

        // return the attribute
        return attribute;

    }

    @Override
    public AttributeMap getAttributesMapByName(Integer atrEntity) {

        // Create the HashMap holding the Attribute objects by name as key
        // and value as the object itself
        AttributeMap attributeMap = new AttributeMap();

        // List of attributes
        List<Attribute> attributeList;


        // If the entity is not 0 or null, then we will get the list for the entity only
        if ( atrEntity != 0 && atrEntity != null ) {

            attributeList = attributeRepository.findByAtrEntity(atrEntity);

        } else {

            attributeList = attributeRepository.findAll();

        }


        // Iterate through the list and create the HashMap
        for(Attribute attribute : attributeList ) {

            // Format the attributeKey
            String key = attributeExtensionUtils.getFormattedFieldName(attribute.getAtrName());

            // Set the key and value
            attributeMap.put(key,attribute);

        }




        // Return the attributeMap
        return attributeMap;

    }



    @Override
    public boolean isDuplicateAttributeExisting(Attribute attribute) {

        // Get the attribute information
        Attribute exAttribute = attributeRepository.findByAtrName(attribute.getAtrName());

        // If the atrId is 0L, then its a new attribute so we just need to check if there is ano
        // ther attribute code
        if ( attribute.getAtrId() == null || attribute.getAtrId() == 0L ) {

            // If the attribute is not null, then return true
            if ( exAttribute != null ) {

                return true;

            }

        } else {

            // Check if the attribute is null
            if ( exAttribute != null && attribute.getAtrId().longValue() != exAttribute.getAtrId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Attribute saveAttribute(Attribute attribute ) throws InspireNetzException {


        // Save the attribute
        return attributeRepository.save(attribute);

    }

    @Override
    public Attribute validateAndSaveAttribute(Attribute attribute) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_ATTRIBUTE_EXTENSION);

        attribute = saveAttribute(attribute);

        return attribute;

    }

    @Override
    public boolean validateAndDeleteAttribute(Long atrId) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_ATTRIBUTE_EXTENSION);

        deleteAttribute(atrId);

        return true;

    }

    @Override
    public boolean deleteAttribute(Long atrId) throws InspireNetzException {


        // Delete the attribute
        attributeRepository.delete(atrId);

        // return true
        return true;

    }

    @Override
    public Page<Attribute> findAttributeNameLike(String filter,String query,Pageable pageable) {


        Page<Attribute> attributePage;
        if(filter.equalsIgnoreCase("name")){

             attributePage=attributeRepository.findByAtrNameLike("%"+query+"%",pageable);

        }else{

            attributePage=attributeRepository.findAll(pageable);
        }

        return attributePage;
    }


}
