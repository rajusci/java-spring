package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.util.AccountBundlingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Component
public class CustomerHelper {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;


    /**
     * function to get the customer information for the given merchantno and loyalty Id
     *
     * @param merchantNo    - The merchant number of the merchant
     * @param loyaltyId     - The loyalty id for the customer
     *
     * @return              - Return the customer information
     */
    public Customer getCustomerInfo(Long merchantNo, String loyaltyId) {

        return customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

    }

    public Customer getCustomerInfoByMobile(Long merchantNo, String mobile) {

        return customerService.findByCusMobileAndCusMerchantNo(mobile,merchantNo);

    }

    /**
     * Get the tier for the customer
     * This will calculate the effective tier for the customer
     * based on the bundling informaiton
     *
     * @param customer  - The customer object
     * @return          - Return the tier.
     *
     */
    public Tier getTier(Customer customer) {

        return accountBundlingUtils.getEffectiveTierForCustomer(customer);

    }
}
