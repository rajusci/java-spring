package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.CardMasterStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.service.CardMasterService;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.controller.CardMasterController;
import com.inspirenetz.api.rest.resource.CardMasterCompatibleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 25/2/15.
 */
@Component
public class CardMasterCompatibleAssembler extends ResourceAssemblerSupport<CardMaster,CardMasterCompatibleResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CardMasterService cardMasterService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private CustomerService customerService;


    public CardMasterCompatibleAssembler() {

        super(CardMasterController.class,CardMasterCompatibleResource.class);

    }

    @Override
    public CardMasterCompatibleResource toResource(CardMaster cardMaster) {

        CardMasterCompatibleResource cardMasterCompatibleResource=new CardMasterCompatibleResource();

        cardMasterCompatibleResource.setCard_balance(cardMaster.getCrmCardBalance()==null?0.0:cardMaster.getCrmCardBalance());

        cardMasterCompatibleResource.setCard_holder_name(cardMaster.getCrmCardHolderName()==null?"":cardMaster.getCrmCardHolderName());

        Integer crmCardStatus = cardMaster.getCrmCardStatus() ==null?0:cardMaster.getCrmCardStatus();


        CardType cardType=cardMaster.getCardType();

        if(cardType==null){

            cardMasterCompatibleResource.setIs_pin_enabled(false);

        }else{

            // Check if the pin is enabled for the cardtype, We need to verify
            // that the pin is valid
            if ( cardType.getCrtAllowPinIndicator() == IndicatorStatus.YES ){

                cardMasterCompatibleResource.setIs_pin_enabled(true);

            }else{

                cardMasterCompatibleResource.setIs_pin_enabled(false);

            }

            cardMasterCompatibleResource.setCard_type(cardType.getCrtType());

            cardMasterCompatibleResource.setCard_fixed_value(cardType.getCrtFixedValue());

            cardMasterCompatibleResource.setCard_min_topup(cardType.getCrtMinTopupValue());
        }


        if(crmCardStatus == CardMasterStatus.NEW || crmCardStatus ==CardMasterStatus.LOCKED || crmCardStatus ==CardMasterStatus.RETURNED){

            cardMasterCompatibleResource.setCard_status("invalid");

        }else if(crmCardStatus ==CardMasterStatus.EXPIRED){

            cardMasterCompatibleResource.setCard_status("expired");

        }else  if(crmCardStatus == CardMasterStatus.ACTIVE){

            cardMasterCompatibleResource.setCard_status("valid");
        } if(crmCardStatus == CardMasterStatus.REFUNDED){

            cardMasterCompatibleResource.setCard_status("valid");
        }if(crmCardStatus == CardMasterStatus.BALANCE_EXPIRED){

            cardMasterCompatibleResource.setCard_status("valid");
        }

        if(cardMaster.getCrmLoyaltyId()!=null && !cardMaster.getCrmLoyaltyId().equals("")){

            Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId(),cardMaster.getCrmMerchantNo());

            if(customer!=null){

                cardMasterCompatibleResource.setCusIdType(customer.getCusIdType());

                cardMasterCompatibleResource.setCusIdNo(customer.getCusIdNo());
            }

        }

        return  cardMasterCompatibleResource;
    }

    /**
     * Function to convert the list of CardMaster objects into an equivalent list
     * of CardMasterResource objects
     *
     * @param cardMasterList - The List object for the CardMaster objects
     * @return cardMasterResourceList - This list of CardMasterResource objects
     */
    public List<CardMasterCompatibleResource> toResources(List<CardMaster> cardMasterList) {

        // Create the list that will hold the resources
        List<CardMasterCompatibleResource> cardMasterCompatibleResources = new ArrayList<CardMasterCompatibleResource>();

        // Create the CardMasterResource object
        CardMasterCompatibleResource cardMasterResource = null;


        // Go through the cardMasters and then create the cardMaster resource
        for(CardMaster cardMaster : cardMasterList ) {

            // Get the CardMasterResource
            cardMasterResource = toResource(cardMaster);

            // Add the resource to the array list
            cardMasterCompatibleResources.add(cardMasterResource);

        }


        // return the cardMasterResoueceList
        return cardMasterCompatibleResources;
    }
}
