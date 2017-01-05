package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.CustomerRewardPoint;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.domain.Sale;

/**
 * Created by sandheepgr on 23/5/14.
 */
public interface LoyaltyComputation  {

    public boolean isProgramValidForTransaction(LoyaltyProgram loyaltyProgram,Sale sale);
    public CustomerRewardPoint calculatePoints(LoyaltyProgram loyaltyProgram,Sale sale);

}
