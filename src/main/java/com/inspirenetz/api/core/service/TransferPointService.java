package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface TransferPointService  {

    public boolean transferPoints(TransferPointRequest transferPointRequest) throws InspireNetzException;

}
