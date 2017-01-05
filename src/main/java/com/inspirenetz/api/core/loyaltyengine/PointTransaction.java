package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.PointRewardData;
import com.inspirenetz.api.core.domain.Transaction;

/**
 * Created by sandheepgr on 23/5/14.
 */
public interface PointTransaction {

    public boolean updateRewardTables(PointRewardData pointRewardData);
    public void addNotifications(PointRewardData pointRewardData);
    public boolean addTransaction(Transaction transaction);


}


