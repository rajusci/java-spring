package com.inspirenetz.api.util;

import com.google.common.base.CaseFormat;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtensionService;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Component
public class AttributeExtensionUtils {


    public static final String ID_FIELD = "ATTR_ID";

    public static final String VALUE_FIELD = "ATTR_VALUE";

    public static final String ATTRIBUTE_FIELD = "ATTRIBUTE";



    public static final String METHOD_TYPE_GET= "GET";

    public static final String METHOD_TYPE_SET = "SET";


    @Autowired
    private Mapper mapper;



    /**
     * Function to get the Extended field value for the given attribute
     *
     * @param objects   : The list of object that need to be iterated to
     *                    find the attribute
     * @param attr      : The Attribute object that need to be checked
     * @return          : Return the value in case either id or value is matching
     *                    Return null if nothing matches
     */
    public  String getExtFieldValue(Set<?> objects,Attribute attr) {

        // If the objects are null, then return null
        if ( objects == null ) {

            return null;

        }


        // Iterate through each of the objects and then check if the
        // attribute id of the current object is equal to the id passed.
        // If they are matching then call the method
        for(Object instance : objects ) {


            // Get the class
            Class clazz = instance.getClass();

            // Get the attribute field
            Attribute attribute = getAttribute(clazz, instance);

            // Check if the attrId or attrName of the attribute matches with the attr passed
            if (
                    (attribute.getAtrId() != null && attribute.getAtrId() != 0L && attr.getAtrId()  == attribute.getAtrId() ) ||
                    (attribute.getAtrName() != null && !attribute.getAtrName().equals("")  && attribute.getAtrName().equals(attr.getAtrName()) )
               ) {

                // Return the response for the invokeMethod
                return invokeMethod(clazz, instance, VALUE_FIELD, METHOD_TYPE_GET, "").toString();

            }

        }

        // Return null
        return null;

    }


    /**
     * Function to set the value for an extendedAttribute using the id of
     * the attribute that need to be set
     *
     * @param objects       : The list of class objects that need to checked for the Attribute
     * @param attr          : The Attribute object that need to be checked for setting value
     * @param value         : The value to which we need to set the attribute
     *
     * @return              : Return true if the value was set successfully
     *                        Return false is setting value failed
     */
    public  boolean setExtFieldValue(Set<?> objects,Attribute attr,String value) {


        // If the objects are null, then we need to return false
        if ( objects == null ) {

            return false;

        }


        // Go through each of the objects instance and then invoke the method
        for(Object instance : objects ) {

            // Get the class object
            Class clazz = instance.getClass();

            // Get the attribute field
            Attribute attribute = getAttribute(clazz, instance);

            // If the attribute fields are not valid, then we need to return false
            if ( attribute.getAtrId() == null  ||
                 attribute.getAtrId() == 0L ||
                 attribute.getAtrName() == null ||
                 attribute.getAtrName().equals("")) {

                return false;

            }

            // Check if the attri
            if (  attribute.equals(attr) ) {

                // Invoke the method to set the value
                invokeMethod(clazz, instance, VALUE_FIELD, METHOD_TYPE_SET, value);

                // Return true
                return true;

            }

        }

        // Return false
        return false;
    }


    /**
     * Function to Get the attribute names as values as a Map
     * Here we pass the instance of the object and the Set if children
     *
     * @param instance      : The instance class
     * @param childObjects  : The set of child entities
     *
     * @return              : Return the AttributeExtesionMap with fieldNames as key and fieldValues as value
     */
    public AttributeExtendedEntityMap getAttributeExtensionMapForObject(Object instance,Set<?> childObjects,Integer attributeExtensionMapType) {

        // Create the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = new AttributeExtendedEntityMap();

        // If the attributeExtensionMapType is ALL, then we need to have it include
        // the master fields as well
        if ( attributeExtensionMapType == AttributeExtensionMapType.ALL) {

            // Go through the fields in instance
            Field mainObjFields[] = instance.getClass().getDeclaredFields();

            // Go through the mainObjFields
            for(Field field : mainObjFields ) {

                // Get the fieldName
                String fieldName = field.getName();

                // Convert the fieldName to UPPER_UNDERSCOre
                String formattedFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE,fieldName);

                // Get the fieldValue
                Object fieldValue = invokeMethod(instance.getClass(),instance,formattedFieldName,METHOD_TYPE_GET,"");

                // If the fieldValue is instance of Set object passed, then don't include it
                // Also if the fieldValue is an instance of set ( result of a OneToMany) then also ignore it
                // as it will cause no session available error when calling toString on the object
                if ( fieldValue == childObjects || fieldValue instanceof Set ) {

                    continue;

                }


                if  ( fieldValue != null ) {

                    // Get the value
                    attributeExtendedEntityMap.put(fieldName,fieldValue.toString());

                } else {

                    // Get the value
                    attributeExtendedEntityMap.put(fieldName,null);
                }
            }
        }


        // If the childObjects are null, then return
        if ( childObjects == null ) {

            // return the data
            return attributeExtendedEntityMap;

        }


        // Now go through the object set and add to the map
        for(Object object : childObjects) {

            // Get the Attribute object
            Attribute attribute = getAttribute(object.getClass(),object);

            // Get the fieldName
            String fieldName = attribute.getAtrName();

            // Format the fieldName to camelCase
            fieldName  = getFormattedFieldName(fieldName);

            // Get the field Value
            String fieldValue = invokeMethod(object.getClass(),object,VALUE_FIELD,METHOD_TYPE_GET,"").toString();

            // Add the value to the map
            attributeExtendedEntityMap.put(fieldName,fieldValue);
        }


        return attributeExtendedEntityMap;
    }


    /**
     * Function to create the entity from a AttributeExtensionMap
     *
     * @param obj                           - The object that need to be populated with fields
     * @param attributeMap                  - The attribute map for the entity
     * @param attributeExtendedEntityMap    - The attributedExtendedEntityMap for the entity
     * @param attributeExtensionMapType     - The mapping type ( all or just extended attributes only)
     * @param attributeExtensionService     - The AttributeExtensionService object
     *
     * @return  - Return the object whose field are set using the attributeExtendedEntityMap
     */
    public Object createEntityFromAttributeExtensionMap(Object obj,AttributeMap attributeMap,AttributeExtendedEntityMap attributeExtendedEntityMap,Integer attributeExtensionMapType,AttributeExtensionService attributeExtensionService) {

        // If the attributeExtensionMapType is ALL, then we need to have it include
        // the master fields as well
        if ( attributeExtensionMapType == AttributeExtensionMapType.ALL) {

            // Do the mapping of fields using mapper instance for the
            // master object
            mapper.map(attributeExtendedEntityMap,obj);


        }


        // Go through the Attributes and if its not a field in the main object, set it
        // to the sub object
        for(Map.Entry<String,Object> entry : attributeExtendedEntityMap.entrySet()) {

            // get the key
            String attrName = entry.getKey();

            // Format the attrName
            attrName = getFormattedFieldName(attrName);

            // Get the Attribute for the attrName
            Attribute attribute = attributeMap.get(attrName);

            // If the attribute does not exist, the continue
            if ( attribute == null ) {

                continue;

            }

            // Set the extFieldValue
            attributeExtensionService.setExtFieldValue(obj, attribute, entry.getValue().toString());

        }
        
        // Return object
        return obj;


    }


    /**
     * Function to get the Attribute field defined in a class
     *
     * @param clazz         - The class from which we need to extract the field
     * @param instance      - The object instance
     *
     * @return              - Return the Attribute object on success,
     *                        Return null on failure
     */
    public  Attribute getAttribute(Class clazz, Object instance) {

        try {

            // Get the methodName in camelCase
            String methodName = getFormattedFieldName(METHOD_TYPE_GET + "_" + ATTRIBUTE_FIELD);

            // Get the method
            Method method = clazz.getMethod(methodName);

            // Invoke the method and return the vlaue
            return (Attribute)method.invoke(instance);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        // finally return null
        return null;

    }


    /**
     * Function to invoke a method in the class by passing class, instance , field name and type of
     * function (getter/setter)
     *
     * @param clazz         : The Class object
     * @param instance      : The instance object
     * @param field         : The field name
     * @param type          : Type ( GET/SET)
     * @param data          : Data to set in case of setter method invocation
     *
     *
     * @return              : Return the value in case on getter
     */
    public  Object invokeMethod(Class clazz, Object instance, String field, String type, String data) {

        // Add the type as prefix to the field
        String methodName = type + "_" + field;

        // Convert the camelCase
        methodName = getFormattedFieldName(methodName);



        // Get the method
        try {


            // If the calling method type is getter, then we just invoke the
            // method and return the value
            if ( type.equals(METHOD_TYPE_GET) ) {

                // Get the method
                Method method = clazz.getDeclaredMethod(methodName);

                // Invoke the method
                return method.invoke(instance);

            } else {

                // Get the method with the parameter class
                Method method = clazz.getDeclaredMethod(methodName,String.class);

                // Call the invoke with the instance and data to be passed
                return method.invoke(instance,data);

            }


        } catch (NoSuchMethodException e) {

            // Print stack trace
            e.printStackTrace();

            // Return null
            return null;

        } catch (InvocationTargetException e) {

            // Print stack trace
            e.printStackTrace();

            // Return null
            return null;

        } catch (IllegalAccessException e) {

            // Print stack trace
            e.printStackTrace();

            // Return null
            return null;

        }


    }


    /**
     * Function to get the formatted field name,
     * This function will check if the field name is camel cased or not
     * If the field is not set, then function will convert to camelCase
     * and return the name
     *
     * @param fieldName - The fieldName that need to be formatted
     * @return          - Formatted fieldName in camelCase
     */
    public  String getFormattedFieldName(String fieldName) {

        // flag showing if the string need to be formatted
        boolean needFormatting = false;

        // Check if the string starts with a uppercase , then
        // the needFormatting should be set to true
        if ( Character.isUpperCase(fieldName.charAt(0))) {

            // Set the flag to true
            needFormatting = true;

        }



        // Check if the fieldName contains underscore/hyphen
        // If the text contains underscope/hypen, we need to format it
        if ( fieldName.contains("_") ) {

            needFormatting = true;

        }


        // If the needFormatting is true, then we need to
        // format the string to camecase
        if ( needFormatting ) {

            // Format the fieldName to camelcase
            fieldName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,fieldName);

        }



        // Return the fieldName
        return fieldName;

    }


    /**
     *
     * Function to build the AttributeExtendedEntityMap from a Map object
     *
     * @param map       - The Map object from which we need to build the attribute entity
     * @return          - The AttributeExtendedEntityMap object with the contents of the map
     */
    public AttributeExtendedEntityMap buildAttributeExtendedEntityMapFromMap(Map<String,String> map ) {

        // Create the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = new AttributeExtendedEntityMap();

        // Create the map object
        attributeExtendedEntityMap.putAll(map);

        // Return the attributeExtendedEntityMap
        return attributeExtendedEntityMap;


    }

    public List<AttributeExtendedEntityMap> buildAttributeExtendedEntityMapFromMapList(List<Map<String,Object>> mapList) {

        // List holding the return value
        List<AttributeExtendedEntityMap> attributeExtendedEntityMapList = new ArrayList<>(0);

        // Iterate through the objects anre add to the list
        for ( Map<String,Object> map : mapList ) {

            // Create the AttributeExtendedEntityMap
            AttributeExtendedEntityMap attributeExtendedEntityMap = new AttributeExtendedEntityMap();

            // Create the map object
            attributeExtendedEntityMap.putAll(map);

            // Add to the list
            attributeExtendedEntityMapList.add(attributeExtendedEntityMap);

        }

        // Return the list
        return attributeExtendedEntityMapList;

    }



    /**
     * Function to get the extended value for a given attribute id
     *
     * @param objects   : The class objects that need to be checked for the attribute
     * @param attrId    : The attribute id that we need to get the value for
     *
     * @return          : Return value if the the field is found
     *                    Return null if the value is not found
     */
    public  String getExtValueForId(Set<?> objects,Long attrId) {


        // If the objects are null, then return null
        if ( objects == null ) {

            return null;

        }



        // Iterate through each of the objects and then check if the
        // attribute id of the current object is equal to the id passed.
        // If they are matching then call the method
        for(Object instance : objects ) {


            // Get the class
            Class clazz = instance.getClass();

            // Get the attribute field
            Attribute attribute = getAttribute(clazz, instance);

            // Check if the attri
            if ( attribute.getAtrId() == attrId ) {

                // Return the response for the invokeMethod
                return invokeMethod(clazz, instance, VALUE_FIELD, METHOD_TYPE_GET, "").toString();

            }

        }

        // Return null
        return null;

    }


    /**
     * Function to set the value for an extendedAttribute using the id of
     * the attribute that need to be set
     *
     * @param objects       : Thhe list of class objects that need to checked for the Attribute
     * @param attrId        : The id of the attribute that need to be set
     * @param value         : The value to which we need to set the attribute
     *
     * @return              : Return true if the value was set successfully
     *                        Return false is setting value failed
     */
    public  boolean setExtValueForId(Set<?> objects,Long attrId,String value) {


        // If the objects are null, then we need to return false
        if ( objects == null ) {

            return false;

        }


        // Go through each of the objects instance and then invoke the method
        for(Object instance : objects ) {

            // Get the class object
            Class clazz = instance.getClass();

            // Get the attribute field
            Attribute attribute = getAttribute(clazz, instance);

            // Check if the attri
            if ( attribute.getAtrId() == attrId ) {

                // Invoke the method to set the value
                invokeMethod(clazz, instance, VALUE_FIELD, METHOD_TYPE_SET, value);

                // Return true
                return true;

            }

        }

        // Return false
        return false;
    }


    /**
     * Function to return extended attribute value for a given attribute name
     *
     * @param objects       : The list of objects that need to be checked
     * @param fieldName     : The field name using which we need to fetch the data
     *
     *
     * @return              : Return value on success
     *                        Return null if the field is not found or failed
     */
    public  String getExtValueForName(Set<?> objects,String fieldName) {



        // Convert the fieldName to camelCase
        fieldName = getFormattedFieldName(fieldName);

        // Iterate through each of the objects and check if the attribute
        // is of the given name
        for(Object instance : objects ) {

            // Get the class
            Class clazz = instance.getClass();

            // Get the attribute field
            Attribute attribute = getAttribute(clazz, instance);

            // Check if the attri
            if ( getFormattedFieldName(attribute.getAtrName()).equals(fieldName) ) {

                // Call the invokeMethod function
                return invokeMethod(clazz, instance, VALUE_FIELD, METHOD_TYPE_GET, "").toString();

            }
        }


        // Return null if nothing is executed.
        return null;

    }


}
