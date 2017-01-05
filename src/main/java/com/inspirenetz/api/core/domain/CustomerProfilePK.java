package com.inspirenetz.api.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sandheepgr on 29/3/14.
 */
public class CustomerProfilePK implements Serializable {

    @Id
    @Column(name = "CSP_MERCHANT_NO")
    private Long cspMerchantNo;

    @Id
    @Column(name = "CSP_LOYALTY_ID")
    private String cspLoyaltyId;

}
