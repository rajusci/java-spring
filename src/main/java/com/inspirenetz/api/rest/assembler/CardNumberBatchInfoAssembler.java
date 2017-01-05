package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.controller.CardNumberBatchInfoController;
import com.inspirenetz.api.rest.resource.CardNumberBatchInfoResource;
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
public class CardNumberBatchInfoAssembler extends ResourceAssemblerSupport<CardNumberBatchInfo,CardNumberBatchInfoResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    CardTypeService cardTypeService;

    public CardNumberBatchInfoAssembler() {

        super(CardNumberBatchInfoController.class,CardNumberBatchInfoResource.class);

    }

    @Override
    public CardNumberBatchInfoResource toResource(CardNumberBatchInfo cardNumberBatchInfo) {



        CardNumberBatchInfoResource cardNumberBatchInfoResource = mapper.map(cardNumberBatchInfo,CardNumberBatchInfoResource.class);

        //find the card type name
        Long cardNo = cardNumberBatchInfo.getCnbCardType() ==null ?0L:cardNumberBatchInfo.getCnbCardType();

        CardType cardType = cardTypeService.findByCrtId(cardNo);

        //set the value
        if(cardType !=null){

            //set the card type information
           cardNumberBatchInfoResource.setCnbCardName(cardType.getCrtName());
        }

        // Return the cardNumberBatchInfoResource
        return cardNumberBatchInfoResource;
    }


    /**
     * Function to convert the list of CardNumberBatchInfo objects into an equivalent list
     * of CardNumberBatchInfoResource objects
     *
     * @param cardNumberBatchInfoList - The List object for the CardNumberBatchInfo objects
     * @return cardNumberBatchInfoResourceList - This list of CardNumberBatchInfoResource objects
     */
    public List<CardNumberBatchInfoResource> toResources(List<CardNumberBatchInfo> cardNumberBatchInfoList) {

        // Create the list that will hold the resources
        List<CardNumberBatchInfoResource> cardNumberBatchInfoResourceList = new ArrayList<CardNumberBatchInfoResource>();

        // Create the CardNumberBatchInfoResource object
        CardNumberBatchInfoResource cardNumberBatchInfoResource = null;


        // Go through the cardNumberBatchInfos and then create the cardNumberBatchInfo resource
        for(CardNumberBatchInfo cardNumberBatchInfo : cardNumberBatchInfoList ) {

            // Get the CardNumberBatchInfoResource
            cardNumberBatchInfoResource = mapper.map(cardNumberBatchInfo,CardNumberBatchInfoResource.class);

            // Add the resource to the array list
            cardNumberBatchInfoResourceList.add(cardNumberBatchInfoResource);

        }


        // return the cardNumberBatchInfoResoueceList
        return cardNumberBatchInfoResourceList;

    }
}
