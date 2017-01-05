package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.CardMasterOperationResponse;
import com.inspirenetz.api.core.service.CardMasterService;
import com.inspirenetz.api.rest.controller.CardMasterController;
import com.inspirenetz.api.rest.resource.CardMasterOperationCompatibleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by ameen on 25/2/15.
 */
@Component
public class CardMasterOperationCompatibleAssembler extends ResourceAssemblerSupport<CardMasterOperationResponse,CardMasterOperationCompatibleResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CardMasterService cardMasterService;


    public CardMasterOperationCompatibleAssembler() {

        super(CardMasterController.class,CardMasterOperationCompatibleResource.class);

    }


    @Override
    public CardMasterOperationCompatibleResource toResource(CardMasterOperationResponse cardMasterOperationResponse) {

        CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =new CardMasterOperationCompatibleResource();

        cardMasterOperationCompatibleResource.setBalance(cardMasterOperationResponse.getBalance());

        cardMasterOperationCompatibleResource.setTxn_ref(cardMasterOperationResponse.getTxnref());

        return cardMasterOperationCompatibleResource;
    }
}
