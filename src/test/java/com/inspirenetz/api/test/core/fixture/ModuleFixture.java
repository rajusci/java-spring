package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.test.core.builder.ModuleBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class ModuleFixture {

    public static Module standardModule() {

        Module module = ModuleBuilder.aModule()
                .withMdlName("TEST MODULE")
                .build();


        return module;

    }


    public static Module updatedStandardModule(Module module) {

        module.setMdlComment("This is a sampel comment");
        return module;

    }



    public static Set<Module> standardModules() {

        Set<Module> moduleSet = new HashSet<>(0);

        Module module1 = ModuleBuilder.aModule()
                .withMdlName("TEST MODULE")
                .build();

        moduleSet.add(module1);



        Module module2 = ModuleBuilder.aModule()
                .withMdlName("TEST MODULE2")
                .build();

        moduleSet.add(module2);


        return moduleSet;


    }
}
