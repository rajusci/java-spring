package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.test.core.builder.AttributeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 7/8/14.
 */
public class AttributeFixture {

    public static Attribute standardAttribute() {

        Attribute attribute = AttributeBuilder.anAttribute()
                .withAtrName("TEST_ATTRIBUTE1")
                .withAtrEntity(1)
                .withAtrDesc("TEST ATTRIBUTE DESC")
                .build();


        return attribute;

    }



    public static Attribute updatedStandardAttribute(Attribute attribute) {

        attribute.setAtrDesc("TEST ATTRIBUTE DESC UPDATED");
        return attribute;

    }



    public static Set<Attribute> standardAttributes() {

        Set<Attribute> attributeSet = new HashSet<>(0);


        Attribute attribute1 = AttributeBuilder.anAttribute()
                .withAtrName("TEST_ATTRIBUTE1")
                .withAtrEntity(1)
                .withAtrDesc("TEST ATTRIBUTE DESC")
                .build();

        attributeSet.add(attribute1);



        Attribute attribute2 = AttributeBuilder.anAttribute()
                .withAtrName("TEST_ATTRIBUTE2")
                .withAtrEntity(1)
                .withAtrDesc("TEST ATTRIBUTE DESC")
                .build();

        attributeSet.add(attribute2);



        return attributeSet;

    }
}
