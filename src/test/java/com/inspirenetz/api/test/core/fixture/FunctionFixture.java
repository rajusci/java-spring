package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.FunctionType;
import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.test.core.builder.FunctionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class FunctionFixture {

    public static Function standardFunction() {

        Function function = FunctionBuilder.aFunction()
                .withFncFunctionName("TEST FUNCTION1")
                .withFncDescription("Test description")
                .withFncType(FunctionType.ADMIN_ACTIVITY)
                .withFncAdminEnabled(UserAccessRightAccessEnableFlag.ENABLED)
                .build();


        return function;

    }



    public static  Function updatedStandardFunction(Function function) {

        function.setFncDescription("New description");
        return function;

    }


    public static Set<Function> standardFunctions() {

        Set<Function> functionSet = new HashSet<>(0);

        Function function1 = FunctionBuilder.aFunction()
                .withFncFunctionName("TEST FUNCTION1")
                .withFncDescription("Test description")
                .withFncType(FunctionType.ADMIN_ACTIVITY)
                .withFncAdminEnabled(UserAccessRightAccessEnableFlag.ENABLED)
                .build();

        functionSet.add(function1);


        Function function2 = FunctionBuilder.aFunction()
                .withFncFunctionName("TEST FUNCTION2")
                .withFncDescription("Test description")
                .withFncType(FunctionType.MERCHANT_USER_ACTIVITY)
                .withFncMerchantUserEnabled(UserAccessRightAccessEnableFlag.ENABLED)
                .build();

        functionSet.add(function2);


        return functionSet;


    }
}
