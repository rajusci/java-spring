package com.inspirenetz.api.core.segmentation;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;

/**
 * Created by sandheepgr on 28/5/14.
 */
public interface Segmentation {

    public boolean isCustomerValidMember(CustomerSegment customerSegment,Customer customer);

}
