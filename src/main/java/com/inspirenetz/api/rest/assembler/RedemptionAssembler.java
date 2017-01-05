package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.RedemptionType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.controller.RedemptionController;
import com.inspirenetz.api.rest.resource.RedemptionResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Component
public class RedemptionAssembler extends ResourceAssemblerSupport<Redemption, RedemptionResource> {
    @Autowired
    private Mapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    CatalogueService catalogueService;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;



    public RedemptionAssembler() {
        super(RedemptionController.class, RedemptionResource.class);
    }

    @Override
    public RedemptionResource toResource(Redemption redemption) {

        // Map the RedemptionResource
        RedemptionResource redemptionResource = mapper.map(redemption, RedemptionResource.class);

        // Check if the reward currency is set
        RewardCurrency rewardCurrency = redemption.getRewardCurrency();

        // If the reward currency is not null, then we need to set th e
        // reward currency name
        if ( rewardCurrency != null ) {

            redemptionResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

        }
        //Check if the redemption is from pay then add partner name and contact no to the response.

        if(redemption.getRdmType() == RedemptionType.PAY){

            //Get the redemption merchant details
            RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemCode(redemption.getRdmProductCode());

            //Set the vendor name
            redemptionResource.setRdmVendorName(redemptionMerchant.getRemName());

            //set the vendor contact no
            redemptionResource.setRdmVendorContactNo(redemptionMerchant.getRemContactMobile());

        }
        if(redemption.getRdmRef() != null && redemption.getRdmRef().length() != 0){

            //Set Redemption reference
            redemptionResource.setRdmRef(redemption.getRdmRef());
        }else{

            //Set Redemption reference
            redemptionResource.setRdmRef("");
        }


        /*// Check if the product is set
        Product product = redemption.getProduct();

        // if the product is not null,set the product name
        if ( product != null ) {

            redemptionResource.setPrdName(product.getPrdName());

        }*/

        Long rdmUser = redemption.getRdmUserNo() ==null?0L:redemption.getRdmUserNo();

        //if user not equal null
        User user =userService.findByUsrUserNo(rdmUser);

        if(user !=null){

            MerchantLocation merchantLocation = merchantLocationService.findByMelId(user.getUsrLocation());

            if(merchantLocation !=null){

                redemptionResource.setRdmLocation(merchantLocation.getMelLocation());
            }
        }

        Merchant merchant =merchantService.findByMerMerchantNo(redemption.getRdmMerchantNo() ==null?0L:redemption.getRdmMerchantNo());

        if(merchant !=null){

            redemptionResource.setRdmMerchantName(merchant.getMerMerchantName());
        }

        //Get the catalogue info
        Catalogue catalogue = getCatalogue(redemption.getRdmProductCode(), redemption.getRdmMerchantNo());

        //Check if the catalogue is null
        if(catalogue != null){

            //set the product name
            redemptionResource.setPrdName(catalogue.getCatDescription());

        }else {

            //set the product name
            redemptionResource.setPrdName("");
        }


        // Return the redemption resource
        return redemptionResource;


    }

    public List<RedemptionResource> toResources( List<Redemption> redemptionList) {

        // Create the List to return
        ArrayList<RedemptionResource> redemptionResourceList = new ArrayList<RedemptionResource>();


        // Go through each of the redemption item in the list
        for(Redemption redemption : redemptionList) {


            RedemptionResource redemptionResource = null;

            // Get the RedemptionResource
            redemptionResource = toResource(redemption);

            // Add the resource to the array list
            redemptionResourceList.add(redemptionResource);

        }

        return redemptionResourceList;

    }



    public List<RedemptionResource> toResources( Page<Redemption> redemptionPage ) {

        // Create the List to return
        ArrayList<RedemptionResource> redemptionResourceList = new ArrayList<RedemptionResource>();

        // Go through each of the redemption item in the list
        for(Redemption redemption : redemptionPage) {

            RedemptionResource redemptionResource = null;

            // Get the RedemptionResource
            redemptionResource = toResource(redemption);

            // Add the resource to the array list
            redemptionResourceList.add(redemptionResource);

        }

        return redemptionResourceList;

    }

    public Catalogue getCatalogue(String rdmProductCode, Long rdmMerchantNo){

        //Get the catalogue
        Catalogue catalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(rdmProductCode,rdmMerchantNo);

        //return the catalogue
        return catalogue;
    }
}
