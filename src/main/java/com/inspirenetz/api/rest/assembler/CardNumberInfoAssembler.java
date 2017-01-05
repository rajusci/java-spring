package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.controller.CardNumberInfoController;
import com.inspirenetz.api.rest.resource.CardNumberInfoResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 21/10/15.
 */
@Component
public class CardNumberInfoAssembler extends ResourceAssemblerSupport<CardNumberInfo,CardNumberInfoResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CardTypeService cardTypeService;

    public CardNumberInfoAssembler() {

        super(CardNumberInfoController.class,CardNumberInfoResource.class);

    }

    @Override
    public CardNumberInfoResource toResource(CardNumberInfo cardNumberInfo) {

        // Create the CardNumberInfoResource
        CardNumberInfoResource cardNumberInfoResource = mapper.map(cardNumberInfo,CardNumberInfoResource.class);

        if(cardNumberInfo.getCniPin()==null ||cardNumberInfo.getCniPin().equals("0")){

            cardNumberInfoResource.setCniPinEnabled(IndicatorStatus.NO);

        }else{

            cardNumberInfoResource.setCniPinEnabled(IndicatorStatus.YES);
        }

        // Get the CardType
        CardType cardType = cardNumberInfo.getCardType();

        // If the cardType is not null, then set the information
        if(cardType!=null && cardType.getCrtId()!=null && cardType.getCrtId()!=0){

            cardNumberInfoResource.setCrtName(cardType.getCrtName());
            cardNumberInfoResource.setCrtType(cardType.getCrtType());
            cardNumberInfoResource.setCrtFixedValue(cardType.getCrtFixedValue());
            cardNumberInfoResource.setCrtMinTopupValue(cardType.getCrtMinTopupValue());
            cardNumberInfoResource.setCrtMaxValue(cardType.getCrtMaxValue());
            cardNumberInfoResource.setCrtExpiryOption(cardType.getCrtExpiryOption());
            cardNumberInfoResource.setCrtExpiryDate(cardType.getCrtExpiryDate());
            cardNumberInfoResource.setCrtExpiryDays(cardType.getCrtExpiryDays());
            cardNumberInfoResource.setCrtMaxNumTxns(cardType.getCrtMaxNumTxns());
        }



        // Return the cardNumberInfoResource
        return cardNumberInfoResource;
    }


    /**
     * Function to convert the list of CardNumberInfo objects into an equivalent list
     * of CardNumberInfoResource objects
     *
     * @param cardNumberInfoList - The List object for the CardNumberInfo objects
     * @return cardNumberInfoResourceList - This list of CardNumberInfoResource objects
     */
    public List<CardNumberInfoResource> toResources(List<CardNumberInfo> cardNumberInfoList) {

        // Create the list that will hold the resources
        List<CardNumberInfoResource> cardNumberInfoResourceList = new ArrayList<CardNumberInfoResource>();

        // Create the CardNumberInfoResource object
        CardNumberInfoResource cardNumberInfoResource = null;


        // Go through the cardNumberInfos and then create the cardNumberInfo resource
        for(CardNumberInfo cardNumberInfo : cardNumberInfoList ) {

            // Get the CardNumberInfoResource
            cardNumberInfoResource = mapper.map(cardNumberInfo,CardNumberInfoResource.class);


            if(cardNumberInfo.getCniPin()==null ||cardNumberInfo.getCniPin().equals("0")){

                cardNumberInfoResource.setCniPinEnabled(IndicatorStatus.NO);

            }else{

                cardNumberInfoResource.setCniPinEnabled(IndicatorStatus.YES);
            }

            // Get the CardType
            CardType cardType = cardNumberInfo.getCardType();

            // If the cardType is not null, then set the information
            if(cardType!=null && cardType.getCrtId()!=null && cardType.getCrtId()!=0){

                cardNumberInfoResource.setCrtName(cardType.getCrtName());
                cardNumberInfoResource.setCrtType(cardType.getCrtType());
                cardNumberInfoResource.setCrtFixedValue(cardType.getCrtFixedValue());
                cardNumberInfoResource.setCrtMinTopupValue(cardType.getCrtMinTopupValue());
                cardNumberInfoResource.setCrtMaxValue(cardType.getCrtMaxValue());
                cardNumberInfoResource.setCrtExpiryOption(cardType.getCrtExpiryOption());
                cardNumberInfoResource.setCrtExpiryDate(cardType.getCrtExpiryDate());
                cardNumberInfoResource.setCrtExpiryDays(cardType.getCrtExpiryDays());
                cardNumberInfoResource.setCrtMaxNumTxns(cardType.getCrtMaxNumTxns());
            }

            // Add the resource to the array list
            cardNumberInfoResourceList.add(cardNumberInfoResource);

        }


        // return the cardNumberInfoResoueceList
        return cardNumberInfoResourceList;

    }


}