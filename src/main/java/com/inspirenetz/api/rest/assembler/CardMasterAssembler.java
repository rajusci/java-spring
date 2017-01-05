package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.service.CardMasterService;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.controller.CardMasterController;
import com.inspirenetz.api.rest.resource.CardMasterResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 15/4/14.
 */
@Component
public class CardMasterAssembler extends ResourceAssemblerSupport<CardMaster,CardMasterResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private CardMasterService cardMasterService;

    @Autowired
    private CustomerService customerService;


    public CardMasterAssembler() {

        super(CardMasterController.class,CardMasterResource.class);

    }

    @Override
    public CardMasterResource toResource(CardMaster cardMaster) {

        // Create the CardMasterResource
        CardMasterResource cardMasterResource = mapper.map(cardMaster,CardMasterResource.class);

        // Get the CardType
        CardType cardType = cardMaster.getCardType();

        // If the cardType is not null, then set the information
        if ( cardType != null ) {

            // Set the crtType
            cardMasterResource.setCrtType(cardType.getCrtType());

            cardMasterResource.setCrtName(cardType.getCrtName());

            cardMasterResource.setCrtFixedValue(cardType.getCrtFixedValue());

            cardMasterResource.setCrtMinTopupValue(cardType.getCrtMinTopupValue());

            cardMasterResource.setCrtMaxValue(cardType.getCrtMaxValue());

            cardMasterResource.setCrtExpiryOption(cardType.getCrtExpiryOption());

            cardMasterResource.setCrtExpiryDate(cardType.getCrtExpiryDate());

            cardMasterResource.setCrtExpiryDays(cardType.getCrtExpiryDays());

            cardMasterResource.setCrtMaxNumTxns(cardType.getCrtMaxNumTxns());

            // Set the expiredy flag
            cardMasterResource.setExpired(cardMasterService.isCardExpired(cardType,cardMaster));

            // Set the expiry text
            cardMasterResource.setExpiry(cardMasterService.getCardExpiry(cardType,cardMaster));

            //

            // Set the allowPinIndicator
            cardMasterResource.setCrtAllowPinIndicator(cardType.getCrtAllowPinIndicator());

            // Set the cardName
            cardMasterResource.setCardName(cardType.getCrtName());

            //set the total balance
            cardMasterResource.setTotalBalance(cardMaster.getCrmCardBalance() + cardMaster.getCrmPromoBalance());


        }

        if(cardMaster.getCrmLoyaltyId()!=null && !cardMaster.getCrmLoyaltyId().equals("")){

            Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId(),cardMaster.getCrmMerchantNo());

            if(customer!=null){

                cardMasterResource.setCusIdType(customer.getCusIdType());

                cardMasterResource.setCusIdNo(customer.getCusIdNo());
            }

        }

        //Check whether user pin is required for debit
        boolean isPinRequired = cardMasterService.isUserPinRequiredForDebit(cardMaster);

        //check if the pin is required
        if (isPinRequired){

            //set the cardmaster resourcce isPin required to true
            cardMasterResource.setIsPinRequired(true);
        }

        // Return the cardMasterResource
        return cardMasterResource;
    }

    /**
     * Function to convert the list of CardMaster objects into an equivalent list
     * of CardMasterResource objects
     *
     * @param cardMasterList - The List object for the CardMaster objects
     * @return cardMasterResourceList - This list of CardMasterResource objects
     */
    public List<CardMasterResource> toResources(List<CardMaster> cardMasterList) {

        // Create the list that will hold the resources
        List<CardMasterResource> cardMasterResourceList = new ArrayList<CardMasterResource>();

        // Create the CardMasterResource object
        CardMasterResource cardMasterResource = null;


        // Go through the cardMasters and then create the cardMaster resource
        for(CardMaster cardMaster : cardMasterList ) {

            // Get the CardMasterResource
            cardMasterResource = toResource(cardMaster);

            // Add the resource to the array list
            cardMasterResourceList.add(cardMasterResource);

        }


        // return the cardMasterResoueceList
        return cardMasterResourceList;

    }

}
