package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.test.core.builder.SecuritySettingBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public class SecuritySettingFixture {

    public static SecuritySetting standardSecuritySetting(){

        SecuritySetting securitySetting = SecuritySettingBuilder.aSecuritySetting()

                .withSecId(1L)
                .withSecIdleSessionTimeout(15L)
                .withSecPwdInitialEnabled("Y")
                .withSecPwdLength(6L)
                .withSecPwdExpiration(70L)
                .build();


        return securitySetting;


    }


    public static SecuritySetting updatedStandSecuritySetting(SecuritySetting securitySetting) {

        securitySetting.setSecPwdLength(10L);


        return securitySetting;

    }


    public static Set<SecuritySetting> standardSecuritySettings() {

        Set<SecuritySetting> securitySettings = new HashSet<SecuritySetting>(0);

        SecuritySetting securitySetting  = SecuritySettingBuilder.aSecuritySetting()
                .withSecId(1L)
                .withSecIdleSessionTimeout(15L)
                .withSecPwdInitialEnabled("Y")
                .withSecPwdLength(6L)
                .build();

        securitySettings.add(securitySetting);

        SecuritySetting securitySetting1 = SecuritySettingBuilder.aSecuritySetting()
                .withSecId(1L)
                .withSecIdleSessionTimeout(15L)
                .withSecPwdInitialEnabled("Y")
                .withSecPwdLength(6L)
                .build();

        securitySettings.add(securitySetting1);



        return securitySettings;



    }
}
