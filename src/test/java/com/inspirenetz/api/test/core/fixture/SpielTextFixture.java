package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.test.core.builder.SpielTextBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 3/2/15.
 */
public class SpielTextFixture {
    public static SpielText standardSpielText() {

        SpielText spielText = SpielTextBuilder.aSpielText()
                .withSptChannel(1)
                .withSptLocation(1L)
                .withSptDescription("testingpupose")
                .build();


        return spielText;
    }



    public static SpielText updatedStandardSpielText(SpielText spielText) {

        spielText.setSptDescription("this is a test spielText");
        return spielText;
    }


    public static Set<SpielText> standardSpielTexts() {

        Set<SpielText>  spielTextSet = new HashSet<>(0);

        SpielText spielText1 = SpielTextBuilder.aSpielText()
                .withSptChannel(1)
                .withSptLocation(1L)
                .withSptDescription("testingpupose")
                .build();
        spielTextSet.add(spielText1);


        SpielText spielText2 = SpielTextBuilder.aSpielText()
                .withSptChannel(1)
                .withSptLocation(2L)
                .withSptDescription("testingpupose2")
                .build();

        spielTextSet.add(spielText2);


        return spielTextSet;

    }
}


