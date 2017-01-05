package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.LoyaltyProgramStatus;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.test.core.builder.LoyaltyProgramBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 22/5/14.
 */
public class LoyaltyProgramFixture {

    public static LoyaltyProgram standardLoyaltyProgram() {

        LoyaltyProgram loyaltyProgram = LoyaltyProgramBuilder.aLoyaltyProgram()
                .withPrgMerchantNo(1L)
                .withPrgProgramName("Standard Loyalty Program")
                .withPrgProgramDesc("1 point for every 10 spent")
                .withPrgProgramDriver(1)
                .withPrgRuleType(2)
                .withPrgRatioNum(10.0)
                .withPrgRatioDeno(100.0)
                .withPrgStartDate(DBUtils.covertToSqlDate("2010-12-31"))
                .withPrgEndDate(DBUtils.covertToSqlDate("2020-12-31"))
                .withPrgCurrencyId(1L)
                .withPrgStatus(LoyaltyProgramStatus.ACTIVE)
                .build();


        return loyaltyProgram;


    }



    public static LoyaltyProgram updatedStandardLoyaltyProgram(LoyaltyProgram loyaltyProgram) {

        loyaltyProgram.setPrgRatioNum(11.0);
        loyaltyProgram.setPrgRatioDeno(100.0);

        return loyaltyProgram;
    }



    public static Set<LoyaltyProgram> standardLoyaltyPrograms() {

        Set<LoyaltyProgram> loyaltyProgramSet = new HashSet<>(0);

        LoyaltyProgram loyaltyProgram1 = LoyaltyProgramBuilder.aLoyaltyProgram()
                .withPrgMerchantNo(1L)
                .withPrgProgramName("Standard Loyalty Program")
                .withPrgProgramDesc("1 point for every 10 spent")
                .withPrgProgramDriver(1)
                .withPrgCurrencyId(1L)
                .withPrgRuleType(2)
                .withPrgRatioNum(10.0)
                .withPrgRatioDeno(100.0)
                .build();

        loyaltyProgramSet.add(loyaltyProgram1);



        LoyaltyProgram loyaltyProgram2 = LoyaltyProgramBuilder.aLoyaltyProgram()
                .withPrgMerchantNo(1L)
                .withPrgProgramName("General Loyalty Program")
                .withPrgProgramDesc("1 point for every 10 spent")
                .withPrgProgramDriver(1)
                .withPrgCurrencyId(1L)
                .withPrgRuleType(1)
                .withPrgRatioNum(10.0)
                .build();

        loyaltyProgramSet.add(loyaltyProgram2);


        return loyaltyProgramSet;

    }
}

