package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.CustomerRewardPoint;
import com.inspirenetz.api.core.dictionary.PointRewardData;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.drools.KnowledgeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sandheepgr on 10/9/14.
 */
public interface DroolsEngineService {


    public CustomerRewardPoint processLoyaltyTransaction(LoyaltyProgram loyaltyProgram, Sale sale) throws InspireNetzException;


}
