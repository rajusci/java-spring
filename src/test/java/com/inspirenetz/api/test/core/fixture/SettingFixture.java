package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.SettingDataType;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.test.core.builder.SettingBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class SettingFixture {

    public static Setting standardSetting() {

        Setting setting = SettingBuilder.aSetting()
                .withSetName("NEW SETTING")
                .withSetDataType(SettingDataType.NUMBER)
                .withSetDefaultValue("198")
                .build();


        return setting;
    }



    public static Setting updatedStandardSetting(Setting setting) {

        setting.setSetDescription("this is a test setting");
        return setting;
    }


    public static Set<Setting> standardSettings() {

        Set<Setting>  settingSet = new HashSet<>(0);

        Setting setting1 = SettingBuilder.aSetting()
                .withSetName("NEW SETTING")
                .withSetDataType(SettingDataType.NUMBER)
                .withSetDefaultValue("198")
                .build();

        settingSet.add(setting1);


        Setting setting2 = SettingBuilder.aSetting()
                .withSetName("NEW SETTING2")
                .withSetDataType(SettingDataType.STRING)
                .withSetDefaultValue("HELLO")
                .build();

        settingSet.add(setting2);


        return settingSet;

    }
}
