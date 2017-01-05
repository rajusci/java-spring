package com.inspirenetz.api.util;

import org.dozer.CustomConverter;

import java.util.Collection;

/**
 * Created by sandheepgr on 13/8/14.
 */
public class InDozerCollectionConverter implements CustomConverter {


    @Override
    public Object convert(Object destField, Object sourceField, Class<?> destClass, Class<?> sourceClass) {

        if ( sourceField == null ) {

            return destField;

        }
        if ( sourceField instanceof  String ) {

            return destField;

        } else if ( sourceField instanceof Collection ) {

            return sourceField.toString();

        }


        return destField;

    }
}
