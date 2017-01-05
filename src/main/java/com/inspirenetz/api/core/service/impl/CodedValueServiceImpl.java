package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.CodedValueIndex;
import com.inspirenetz.api.core.dictionary.CodedValueMap;
import com.inspirenetz.api.core.domain.CodedValue;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CodedValueRepository;
import com.inspirenetz.api.core.service.CodedValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CodedValueServiceImpl extends BaseServiceImpl<CodedValue> implements CodedValueService {

    private static Logger log = LoggerFactory.getLogger(CodedValueServiceImpl.class);


    @Autowired
    CodedValueRepository codedValueRepository;


    public CodedValueServiceImpl() {

        super(CodedValue.class);

    }


    @Override
    protected BaseRepository<CodedValue,Long> getDao() {
        return codedValueRepository;
    }





    @Override
    public boolean isDuplicateCodedValueExisting(CodedValue codedValue) {

        // Get the codedValue information
        CodedValue exCodedValue = codedValueRepository.findByCdvIndexAndCdvCodeValue(codedValue.getCdvIndex(),codedValue.getCdvCodeValue());

        // If the brnId is 0L, then its a new codedValue so we just need to check if there is ano
        // ther codedValue code
        if ( codedValue.getCdvCodeId() == null || codedValue.getCdvCodeId() == 0L ) {

            // If the codedValue is not null, then return true
            if ( exCodedValue != null ) {

                return true;

            }

        } else {

            // Check if the codedValue is null
            if ( exCodedValue != null && codedValue.getCdvCodeId().longValue() != exCodedValue.getCdvCodeId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public HashMap<Integer, String> getCodedValueMapForIndex(Integer cdvIndex) {

        // Get the list of item based on the index
        List<CodedValue> codedValueList = codedValueRepository.findByCdvIndex(cdvIndex);

        // Create the HashMap for storing the information
        HashMap<Integer,String>  codedValues = new HashMap<>(0);

        // Check if the list is not null
        if ( codedValueList == null ) {

            return codedValues;

        }


        // Go through the list and add the items to the codedValues HashMap
        for(CodedValue codedValue : codedValueList) {

            codedValues.put(codedValue.getCdvCodeValue(),codedValue.getCdvCodeLabel());

        }

        // Return the populated codedValues
        return codedValues;

    }

    @Override
    public HashMap<Integer, List<CodedValueMap>> getCodedValueMap() {

        // Get the list of the CodedValues
        List<CodedValue>  codedValueList = codedValueRepository.getOrderedCodedValues();

        // The HashMap to store the information
        HashMap<Integer,List<CodedValueMap>> codedValueMap = new HashMap<>(0);

        // List holding the individual cdvIndex codedValueMap
        List<CodedValueMap> codedValueItemList = new ArrayList<>(0);


        // Variable holding the previous cdvIndex
        int cdvPrevIndex = -1;


        // Go through each of the entry in the codedValueList
        for( CodedValue codedValue : codedValueList ) {

            // If the previous index and current items index are different
            // the the one is a new one,
            // Add the
            if ( cdvPrevIndex  != codedValue.getCdvIndex() ) {

                // If the codedValueItemList is not null,
                // then add it to the codedValueMap
                if ( codedValueItemList != null ) {

                    codedValueMap.put(cdvPrevIndex,codedValueItemList);

                }


                // Set the cdvPreIndex = currCdvIndex
                cdvPrevIndex = codedValue.getCdvIndex();

                // Re initialize the hashmap
                codedValueItemList = new ArrayList<>(0);

            }



            // Create the CodedValueMap object
            CodedValueMap cdvMapItem = new CodedValueMap();

            // Set the cdvKey
            cdvMapItem.setCdvKey(codedValue.getCdvCodeValue());

            // Set the cdvValue
            cdvMapItem.setCdvValue(codedValue.getCdvCodeLabel());

            // Add the cdvMapItem to the codedValueItemList
            codedValueItemList.add(cdvMapItem);


        }


        // Insert the final item if its not null
        if ( codedValueItemList != null ) {

            codedValueMap.put(cdvPrevIndex,codedValueItemList);

        }

        // Return the codedValueMap
        return codedValueMap;

    }

    @Override
    public HashMap<Integer, List<CodedValueMap>> getCodedValueMapByIndex(Integer cdvIndex) {

        // Get the list of item based on the index
        List<CodedValue> codedValueList = codedValueRepository.findByCdvIndex(cdvIndex);

        // The HashMap to store the information
        HashMap<Integer,List<CodedValueMap>> codedValueMap = new HashMap<>(0);

        // List holding the individual cdvIndex codedValueMap
        List<CodedValueMap> codedValueItemList = new ArrayList<>(0);





        // Go through each of the entry in the codedValueList
        for( CodedValue codedValue : codedValueList ) {


            // Create the CodedValueMap object
            CodedValueMap cdvMapItem = new CodedValueMap();

            // Set the cdvKey
            cdvMapItem.setCdvKey(codedValue.getCdvCodeValue());

            // Set the cdvValue
            cdvMapItem.setCdvValue(codedValue.getCdvCodeLabel());

            // Add the cdvMapItem to the codedValueItemList
            codedValueItemList.add(cdvMapItem);


        }


        // Insert the final item if its not null
        if ( codedValueItemList != null ) {

            codedValueMap.put(cdvIndex,codedValueItemList);

        }

        // Return the codedValueMap
        return codedValueMap;

    }


    @Override
    public CodedValue findByCdvCodeId(Long cdvCodeId) {

        // Get the CodedValue;
        CodedValue codedValue = codedValueRepository.findByCdvCodeId(cdvCodeId);

        // Return the codedvalue
        return codedValue;

    }

    @Override
    public Page<CodedValue> findByCdvIndex(int cdvIndex, Pageable pageable) {

        // Get the coded value page
        Page<CodedValue> codedValuePage = codedValueRepository.findByCdvIndex(cdvIndex,pageable);

        // Return the codedValuePage
        return codedValuePage;

    }

    @Override
    public CodedValue findByCdvIndexAndCdvCodeValue(int cdvIndex, int cdvCodeValue) {

        // Get the codedValue
        CodedValue codedValue = codedValueRepository.findByCdvIndexAndCdvCodeValue(cdvIndex,cdvCodeValue);

        // Return the codedValue
        return codedValue;

    }




    @Override
    public CodedValue saveCodedValue(CodedValue codedValue ){

        // Save the codedValue
        return codedValueRepository.save(codedValue);

    }

    @Override
    public boolean deleteCodedValue(Long brnId) {

        // Delete the codedValue
        codedValueRepository.delete(brnId);

        // return true
        return true;

    }

    /**
     * @purpose:convert catalogue product category into compatable format
     * @return List
     */

    @Override
    public List<CodedValue> getCompatableCdvIndex() {


        List<CodedValue> codedValues =codedValueRepository.findByCdvIndex(CodedValueIndex.CATALOGUE_PRODUCT_CATEGORY);

        List codedValueList =new ArrayList<HashMap>();

        Map map;

        //check product catalogue product category is null or not
        if(codedValues !=null){

            //convert catalogue category into compatable format
            for(CodedValue codedValue:codedValues){

                map =new HashMap();

                map.put("category_no",codedValue.getCdvCodeValue());

                map.put("category_name",codedValue.getCdvCodeLabel());

                codedValueList.add(map);

            }


        }

        return codedValueList;
    }

    @Override
    public CodedValue getCodedValueId(Integer cdvIndex, String cdvCodeLabel) {
        return null;
    }

    /**
     *
     * @param cdvCodeLabel
     * @return
     */
    @Override
    public CodedValue findByCdvCodeLabel(String cdvCodeLabel) {


        return codedValueRepository.findByCdvCodeLabel(cdvCodeLabel);
    }

    @Override
    public CodedValue findByCdvLabelAndCdvIndex(String cdvLabel, int cdvIndex) {
        return codedValueRepository.findByCdvCodeLabelAndCdvIndex(cdvLabel,cdvIndex);
    }

    @Override
    public List<CodedValue> findByCdvIndex(int cdvIndex) {

        // Get the coded value
        List<CodedValue> codedValues = codedValueRepository.findByCdvIndex(cdvIndex);

        // Return the codedValues
        return codedValues;

    }
}
