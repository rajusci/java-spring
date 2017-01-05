package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CodedValue;
import com.inspirenetz.api.test.core.builder.CodedValueBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 19/5/14.
 */
public class CodedValueFixture {

    public static CodedValue standardCodedValue() {

        CodedValue  codedValue = CodedValueBuilder.aCodedValue()
                .withCdvIndex(1000)
                .withCdvCodeValue(1)
                .withCdvCodeLabel("Grocery")
                .build();

        return codedValue;

    }


    public static CodedValue updatedStandardCodedValue(CodedValue codedValue) {

        codedValue.setCdvCodeLabel("New Grocery");
        return codedValue;

    }



    public static Set<CodedValue> standardCodedValues() {

        Set<CodedValue> codedValueSet = new HashSet<>();


        CodedValue  grocery = CodedValueBuilder.aCodedValue()
                .withCdvIndex(1000)
                .withCdvCodeValue(1)
                .withCdvCodeLabel("Grocery")
                .build();

        codedValueSet.add(grocery);


        CodedValue  household = CodedValueBuilder.aCodedValue()
                .withCdvIndex(1000)
                .withCdvCodeValue(2)
                .withCdvCodeLabel("House Hold")
                .build();

        codedValueSet.add(household);


        return codedValueSet;



    }
}
