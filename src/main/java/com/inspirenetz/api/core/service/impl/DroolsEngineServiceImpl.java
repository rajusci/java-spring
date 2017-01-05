package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.drools.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Service
public class DroolsEngineServiceImpl implements DroolsEngineService {

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(DroolsEngineService.class);

    @Autowired
    private DroolsLoyaltyEngine droolsLoyaltyEngine;



    @Override
    public CustomerRewardPoint processLoyaltyTransaction(LoyaltyProgram loyaltyProgram, Sale sale) throws InspireNetzException {

        // Call the DroolsEngineService
        return droolsLoyaltyEngine.processTransaction(loyaltyProgram,sale);

    }




}
