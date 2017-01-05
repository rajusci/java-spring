package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.test.core.builder.DrawChanceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class DrawChanceFixture {




    public static DrawChance standardDrawChance() {


        DrawChance drawChance = DrawChanceBuilder.aDrawChance()
                .withDrcCustomerNo(100L)
                .withDrcChances(100L)
                .withDrcStatus(1)
                .withDrcType(DrawType.RAFFLE_TICKET)
                .build();


        return drawChance;


    }


    public static DrawChance updatedStandardDrawChance(DrawChance drawChance) {

        drawChance.setDrcChances(10L);

        return drawChance;

    }


    public static Set<DrawChance> standardDrawChances() {

        Set<DrawChance> drawChances = new HashSet<DrawChance>(0);

        DrawChance drawChanceA  = DrawChanceBuilder.aDrawChance()
                .withDrcCustomerNo(100L)
                .withDrcChances(100L)
                .withDrcType(DrawType.RAFFLE_TICKET)
                .build();

        drawChances.add(drawChanceA);



        DrawChance drawChanceB = DrawChanceBuilder.aDrawChance()
                .withDrcCustomerNo(101L)
                .withDrcChances(100L)
                .withDrcType(DrawType.RAFFLE_TICKET)
                .build();

        drawChances.add(drawChanceB);



        return drawChances;



    }
}
