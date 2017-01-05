package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.domain.LoyaltyProgramSkuExtension;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.LoyaltyProgramSkuRepository;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.core.service.LoyaltyProgramSkuService;
import com.inspirenetz.api.util.AttributeExtensionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class LoyaltyProgramSkuServiceImpl extends BaseServiceImpl<LoyaltyProgramSku> implements LoyaltyProgramSkuService {


    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramSkuServiceImpl.class);


    @Autowired
    LoyaltyProgramSkuRepository loyaltyProgramSkuRepository;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;

    @Autowired
    private AttributeService attributeService;


    public LoyaltyProgramSkuServiceImpl() {

        super(LoyaltyProgramSku.class);

    }



    @Override
    protected BaseRepository<LoyaltyProgramSku,Long> getDao() {
        return loyaltyProgramSkuRepository;
    }



    @Override
    public Page<LoyaltyProgramSku> findByLpuProgramId(Long lpuProgramId, Pageable pageable) {

        // Get the page
        Page<LoyaltyProgramSku> loyaltyProgramSkuPage = loyaltyProgramSkuRepository.findByLpuProgramId(lpuProgramId,pageable);

        // Return the page
        return loyaltyProgramSkuPage;

    }

    @Override
    public LoyaltyProgramSku findByLpuId(Long lpuId) {

        // Get the LoyaltyProgramSku
        LoyaltyProgramSku loyaltyProgramSku = loyaltyProgramSkuRepository.findByLpuId(lpuId);

        // Return the loyaltyProgramSku
        return loyaltyProgramSku;

    }

    @Override
    public LoyaltyProgramSku findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(Long lpuProgramId, int lpuItemType, String lpuItemCode) {

        // Get the  LoyaltyProgram
        LoyaltyProgramSku loyaltyProgramSku = loyaltyProgramSkuRepository.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(lpuProgramId,lpuItemType,lpuItemCode);

        // Return the item
        return loyaltyProgramSku;

    }


    @Override
    public boolean isLoyaltyProgramSkuExisting(LoyaltyProgramSku loyaltyProgramSku) {

        // Get the loyaltyProgramSku information
        LoyaltyProgramSku exLoyaltyProgramSku = loyaltyProgramSkuRepository.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(loyaltyProgramSku.getLpuProgramId(), loyaltyProgramSku.getLpuItemType(), loyaltyProgramSku.getLpuItemCode());

        // If the brnId is 0L, then its a new loyaltyProgramSku so we just need to check if there is ano
        // ther loyaltyProgramSku code
        if ( loyaltyProgramSku.getLpuId() == null || loyaltyProgramSku.getLpuId() == 0L ) {

            // If the loyaltyProgramSku is not null, then return true
            if ( exLoyaltyProgramSku != null ) {

                return true;

            }

        } else {

            // Check if the loyaltyProgramSku is null
            if ( exLoyaltyProgramSku != null && loyaltyProgramSku.getLpuId().longValue() != exLoyaltyProgramSku.getLpuId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public List<LoyaltyProgramSku> listRulesForLineItem(Long lpuProgramId, String brand, String category1, String category2, String category3, String product) {

        // Get the List of data
        List<LoyaltyProgramSku> loyaltyProgramSkuList = loyaltyProgramSkuRepository.listRulesForLineItem(lpuProgramId,brand,category1,category2,category3,product);

        // Return the list
        return loyaltyProgramSkuList;

    }

    @Override
    public Set<LoyaltyProgramSku> getLoyaltyProgramSkuFromParams(List<AttributeExtendedEntityMap> params) {

        // The Set holding the data
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);

        // Go through the list and then map the content
        for ( AttributeExtendedEntityMap attributeExtendedEntityMap : params ) {

            // Create new LoyaltyProgramSku object
            LoyaltyProgramSku loyaltyProgramSku = new LoyaltyProgramSku();

            // Create the object from the map
            fromAttributeExtensionMap(loyaltyProgramSku,attributeExtendedEntityMap, AttributeExtensionMapType.ALL);

            // Add to the list
            loyaltyProgramSkuSet.add(loyaltyProgramSku);

        }


        // Return the loyaltyProgramSKuSet
        return loyaltyProgramSkuSet;

    }

    @Override
    public Set<LoyaltyProgramSku> getRemovedLoyaltySku(Set<LoyaltyProgramSku> dbSet, Set<LoyaltyProgramSku> paramSet) {

        // Set holding items to delete
        Set<LoyaltyProgramSku> delSet = new HashSet<>(0);

        // Go through the dbSet and see if its present in the paramSet,
        for(LoyaltyProgramSku loyaltyProgramSku : dbSet ) {

            // Check if its existing in the paramset
            if ( !paramSet.contains(loyaltyProgramSku) ) {

                // Add to the set for deletion
                delSet.add(loyaltyProgramSku);

            }

        }


        // Return the set
        return delSet;

    }



    @Override
    public AttributeExtendedEntityMap toAttributeExtensionMap(Object obj, Integer attributeExtensionMapType) {

        // Get the LoyaltyProgramSku object
        LoyaltyProgramSku loyaltyProgramSku = (LoyaltyProgramSku) obj;

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = attributeExtensionUtils.getAttributeExtensionMapForObject(loyaltyProgramSku,loyaltyProgramSku.getLoyaltyProgramSkuExtensionSet(),attributeExtensionMapType);

        // Return the attributeExtendedEntityMap object
        return attributeExtendedEntityMap;

    }

    @Override
    public Object fromAttributeExtensionMap(Object obj, AttributeExtendedEntityMap attributeExtendedEntityMap, Integer attributeExtensionMapType) {

        // Get the attributes map
        AttributeMap attributeMap = attributeService.getAttributesMapByName(0);

        // Create the LoyaltyProgramSku object
        LoyaltyProgramSku loyaltyProgramSku = (LoyaltyProgramSku) attributeExtensionUtils.createEntityFromAttributeExtensionMap(obj,attributeMap,attributeExtendedEntityMap,attributeExtensionMapType,this);

        // return the LoyaltyProgramSku object
        return loyaltyProgramSku;

    }

    @Override
    public void setExtFieldValue(Object obj, Attribute attribute, String value) {

        // Get the loyaltyProgramSku Object
        LoyaltyProgramSku loyaltyProgramSku = (LoyaltyProgramSku) obj;

        // Check if the field is set
        boolean isSet = attributeExtensionUtils.setExtFieldValue(loyaltyProgramSku.getLoyaltyProgramSkuExtensionSet(),attribute,value);


        // Check if the isSet is true, otherwise , we need to set the data in the extFields
        if ( !isSet ) {

            // If the loyaltyProgramSkuInfoSet is null, then intialize it
            if ( loyaltyProgramSku.getLoyaltyProgramSkuExtensionSet() == null ) {

                loyaltyProgramSku.setLoyaltyProgramSkuExtensionSet(new HashSet<LoyaltyProgramSkuExtension>(0));

            }

            // Create the LoyaltyProgramSkuExtension object
            LoyaltyProgramSkuExtension loyaltyProgramSkuExtension = new LoyaltyProgramSkuExtension();

            // Set the attribute id
            loyaltyProgramSkuExtension.setAttrId(attribute.getAtrId());

            // Set the value
            loyaltyProgramSkuExtension.setAttrValue(value.toString());

            // Set the attribute
            loyaltyProgramSkuExtension.setAttribute(attribute);

            // Add to the set
            loyaltyProgramSku.getLoyaltyProgramSkuExtensionSet().add(loyaltyProgramSkuExtension);
        }

    }

    @Override
    public String getExtFieldValue(Object obj, Attribute attribute) {

        // Get the LoyaltyProgramSku object
        LoyaltyProgramSku loyaltyProgramSku = (LoyaltyProgramSku)obj;

        // Return the attribute
        return attributeExtensionUtils.getExtFieldValue(loyaltyProgramSku.getLoyaltyProgramSkuExtensionSet(), attribute);


    }




    @Override
    public LoyaltyProgramSku saveLoyaltyProgramSku(LoyaltyProgramSku loyaltyProgramSku ){

        // Save the loyaltyProgramSku
        return loyaltyProgramSkuRepository.save(loyaltyProgramSku);

    }

    @Override
    public boolean deleteLoyaltyProgramSku(Long brnId) {

        // Delete the loyaltyProgramSku
        loyaltyProgramSkuRepository.delete(brnId);

        // return true
        return true;

    }

    @Override
    public boolean deleteLoyaltyProgramSkus( Set<LoyaltyProgramSku> loyaltyProgramSkuSet ) {

        // Remove the set
        loyaltyProgramSkuRepository.delete(loyaltyProgramSkuSet);

        // return true
        return true;

    }

    @Override
    public List<LoyaltyProgramSku> findByLpuProgramId(Long lpuProgramId) {

        // Return the repository method call result
        return loyaltyProgramSkuRepository.findByLpuProgramId(lpuProgramId);

    }


}
