package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;
import java.util.Set;

/**
 * Created by alameen on 2/2/15.
 */
public interface SpielTextService extends BaseService<SpielText> {

    public List<SpielText> findBySptRef(Long sptRef);

    public void deleteSpielTextSet(Set<SpielText> spielTexts);

    public SpielText saveSpielText(SpielText spielText);

    public SpielText getSpielText(Customer customer,String spielName,Integer channel) throws InspireNetzException;

    public SpielText findBySptRefAndSptChannelAndSptLocation(Long sptRef,Integer sptChannel,Long sptLocation);
}
