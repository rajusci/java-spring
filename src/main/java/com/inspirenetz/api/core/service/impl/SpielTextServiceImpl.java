package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SpielTextRepository;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.core.service.SpielTextService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by alameen on 2/2/15.
 */
@Service
public class SpielTextServiceImpl extends BaseServiceImpl<SpielText> implements SpielTextService {


    private static Logger log = LoggerFactory.getLogger(SpielTextServiceImpl.class);


    @Autowired
    SpielTextRepository spielTextRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MessageSpielService messageSpielService;


    public SpielTextServiceImpl() {

        super(SpielText.class);

    }


    @Override
    protected BaseRepository<SpielText,Long> getDao() {
        return spielTextRepository;
    }

    @Override
    public List<SpielText> findBySptRef(Long sptRef) {

        return spielTextRepository.findBySptRef(sptRef);
    }

    @Override
    public void deleteSpielTextSet(Set<SpielText> spielTexts) {

        spielTextRepository.delete(spielTexts);
    }

    @Override
    public SpielText saveSpielText(SpielText spielText) {

        return spielTextRepository.save(spielText);
    }

    @Override
    public SpielText getSpielText(Customer customer, String spielName, Integer channel) throws InspireNetzException {

        //first get the customer location from customer object
        Long cusLocation =customer.getCusLocation()==null?0L:customer.getCusLocation();

        //get spielId with reference of spiel name
        MessageSpiel messageSpiel =messageSpielService.findByMsiNameAndMsiMerchantNo(spielName,customer.getCusMerchantNo());

        //check message spiel is null or not if is null throw error
        if(messageSpiel ==null){

           return null;
        }

        //find description based on channel message spiel and location
        SpielText spielText =findBySptRefAndSptChannelAndSptLocation(messageSpiel.getMsiId(), channel, cusLocation);

        //spiel text is null throw error
        if(spielText ==null){

           //get default content of spiel
            SpielText spielText1 =findBySptRefAndSptChannelAndSptLocation(messageSpiel.getMsiId(),channel,0L);

            //spiel text is null return null
            if (spielText1 ==null){

                return null;
            }

            //return spiel text
            return spielText1;
        }


        return spielText;
    }

    @Override
    public SpielText findBySptRefAndSptChannelAndSptLocation(Long sptRef, Integer sptChannel, Long sptLocation) {
        return spielTextRepository.findBySptRefAndSptChannelAndSptLocation(sptRef,sptChannel,sptLocation);
    }
}
