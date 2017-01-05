package com.inspirenetz.api.util;

import com.inspirenetz.api.core.dictionary.PartyApprovalType;
import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.core.service.Injectable;
import com.inspirenetz.api.core.service.PartyApprovalService;
import com.inspirenetz.api.core.service.TransferPointService;
import com.inspirenetz.api.core.service.impl.TransferPointServiceImpl;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by sandheepgr on 13/5/14.
 */
@Component
public class TransferPointUtils {

    @Autowired
    TransferPointService transferPointService;

    @Autowired
    PartyApprovalService partyApprovalService;


    //This line will guarantee the BeansManager class will be injected last
    @Autowired
    private Set<Injectable> injectables = new HashSet<>();

    public TransferPointService getTransferPointService() {
        return transferPointService;
    }

    public void setTransferPointService(TransferPointService transferPointService) {
        this.transferPointService = transferPointService;
    }

    public PartyApprovalService getPartyApprovalService() {
        return partyApprovalService;
    }

    public void setPartyApprovalService(PartyApprovalService partyApprovalService) {
        this.partyApprovalService = partyApprovalService;
    }



    //This method will make sure all the injectable classes will get the BeansManager in its steady state,
    //where it's class members are ready to be set
    @PostConstruct
    private void inject() {
        for (Injectable injectableItem : injectables) {
            injectableItem.inject(this);
        }
    }
}
