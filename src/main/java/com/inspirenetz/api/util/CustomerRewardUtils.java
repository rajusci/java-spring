package com.inspirenetz.api.util;

import com.inspirenetz.api.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 13/5/14.
 */
@Component
public class CustomerRewardUtils {

    @Autowired
    CustomerRewardActivityService customerRewardActivityService;

    @Autowired
    CustomerService customerService;


    //This line will guarantee the BeansManager class will be injected last
    @Autowired
    private Set<InjectableReward> injectableRewards = new HashSet<>();

    public CustomerRewardActivityService getCustomerRewardActivityService() {
        return customerRewardActivityService;
    }

    public void setCustomerRewardActivityService(CustomerRewardActivityService customerRewardActivityService) {
        this.customerRewardActivityService = customerRewardActivityService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    //This method will make sure all the injectable classes will get the BeansManager in its steady state,
    //where it's class members are ready to be set
    @PostConstruct
    private void inject() {
        for (InjectableReward injectableItem : injectableRewards) {
            injectableItem.inject(this);
        }
    }
}
