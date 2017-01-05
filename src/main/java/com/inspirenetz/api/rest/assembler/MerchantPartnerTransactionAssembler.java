package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import com.inspirenetz.api.rest.controller.MerchantPartnerTransactionController;
import com.inspirenetz.api.rest.resource.MerchantPartnerTransactionResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhi on 13/7/16.
 */
@Component
public class MerchantPartnerTransactionAssembler extends ResourceAssemblerSupport<MerchantPartnerTransaction,MerchantPartnerTransactionResource> {

    @Autowired
    private Mapper mapper;

    public MerchantPartnerTransactionAssembler() {

        super(MerchantPartnerTransactionController.class,MerchantPartnerTransactionResource.class);

    }

    @Override
    public MerchantPartnerTransactionResource toResource(MerchantPartnerTransaction merchantPartnerTransaction){

        //Get the merchant partner transaction object
        MerchantPartnerTransactionResource merchantPartnerTransactionResource = mapper.map(merchantPartnerTransaction,MerchantPartnerTransactionResource.class);

        //Return merchantPartnerTransactionResource
        return merchantPartnerTransactionResource;


    }

    /**
     * @purpose - Function to convert the list of merchantPartnerTransactions to equivalent list of merchantPartnerTransactionResource objects.
     * @param merchantPartnerTransactionList - The List object for the  merchantPartnerTransaction object
     * @return returns list of merchantPartnerTransactions object
     */
    public List<MerchantPartnerTransactionResource> toResources(List<MerchantPartnerTransaction> merchantPartnerTransactionList){

        // create an arraylst to hold the resources
        List<MerchantPartnerTransactionResource> merchantPartnerTransactionResourceList = new ArrayList<MerchantPartnerTransactionResource>();

        //create a MerchantPartnerTransactionResource object
        MerchantPartnerTransactionResource merchantPartnerTransactionResource = null;

        //Go through the transaction list
        for(MerchantPartnerTransaction merchantPartnerTransaction : merchantPartnerTransactionList){

            //Get the resource
            merchantPartnerTransactionResource = mapper.map(merchantPartnerTransaction, MerchantPartnerTransactionResource.class);

            //Add the resource to the array list
            merchantPartnerTransactionResourceList.add(merchantPartnerTransactionResource);
        }

        // Return the resource list
        return merchantPartnerTransactionResourceList;

    }

}
