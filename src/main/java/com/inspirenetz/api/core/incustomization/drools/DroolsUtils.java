package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.service.DroolsEngineService;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Component
public class DroolsUtils {

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(DroolsEngineService.class);

    // Get the root path for the RULES directory
    @Value("${rules_file_root}")
    private String rulesRootDirectory;

    /**
     * Function to get the KnowledgeBase object from the drools file
     * that is involved in the processing
     *
     * @param filename  - Name of the file( here we just pass the filename with the extension as the path is predefined)
     * @return          - The KnowlegeBase object if there are no error
     *                    Null if the knowlegebaes creation failed
     */
    public KnowledgeBase createKnowledgeBase(String filename) {

        // Create an instance of the KnowledgeBuilder
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add drl file into builder
        File fileRules = new File(rulesRootDirectory + "/" + filename);

        // Add the file to the builder
        builder.add(ResourceFactory.newFileResource(fileRules), ResourceType.DRL);

        // Check if the builder has errors
        if (builder.hasErrors()) {

            // Log the information
            log.info("DroolsUtils -> createKnowledgeBase -> Builder failed : " + builder.getErrors().toString());

            // Return null
            return null;

        }

        // Create the knowledgeBase from the factory
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();

        //Add to Knowledge Base packages from the builder which are actually
        // the rules from the drl file.
        knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());

        // Return the know
        return knowledgeBase;
    }

}
