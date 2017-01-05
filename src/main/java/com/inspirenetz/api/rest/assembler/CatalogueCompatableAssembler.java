package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.CodedValueIndex;
import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.CodedValueService;
import com.inspirenetz.api.core.service.CustomerRewardBalanceService;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.controller.CatalogueController;
import com.inspirenetz.api.rest.resource.CatalogueCompatableResource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alameen on 15/12/14.
 */
@Component
public class CatalogueCompatableAssembler extends ResourceAssemblerSupport<Catalogue,CatalogueCompatableResource> {


    @Autowired
    CodedValueService codedValueService;

    @Autowired
    private Mapper mapper;

    @Autowired
    ImageService imageService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    private Environment environment;





    public CatalogueCompatableAssembler() {

        super(CatalogueController.class,CatalogueCompatableResource.class);

    }


    public List<CatalogueCompatableResource> toResources(List<Catalogue> catalogueList,Long merchantNo,String cusLoyaltyId){

        // Create the list that will hold the resources
        List<CatalogueCompatableResource> catalogueCompatableResourcesList = new ArrayList<CatalogueCompatableResource>();





        for(Catalogue catalogue:catalogueList){

            Integer customerHasPoint =0;

            CatalogueCompatableResource catalogueCompatableResource =new CatalogueCompatableResource();

            catalogueCompatableResource.setCat_prd_no(catalogue.getCatProductNo()==null?0L:catalogue.getCatProductNo());

            String catPrdCategoryText ="";

            //for setting product category test
            if(catalogue.getCatCategory() !=null){

                int catCategory = catalogue.getCatCategory();

                //getting coded value for catcategory
                CodedValue codedValue = codedValueService.findByCdvIndexAndCdvCodeValue(CodedValueIndex.CATALOGUE_PRODUCT_CATEGORY,catCategory);

                if(codedValue !=null){

                    catPrdCategoryText =codedValue.getCdvCodeLabel();
                }

            }



            catalogueCompatableResource.setCat_prd_category_text(catPrdCategoryText);

            catalogueCompatableResource.setCat_prd_category(catalogue.getCatCategory()==null?0:catalogue.getCatCategory());

            catalogueCompatableResource.setCat_prd_code(catalogue.getCatProductCode()==null?"":catalogue.getCatProductCode());

            catalogueCompatableResource.setCat_prd_desc(catalogue.getCatDescription() == null ? "" : catalogue.getCatDescription());

            catalogueCompatableResource.setCat_prd_long_desc(catalogue.getCatLongDescription()==null?"":catalogue.getCatLongDescription());



            // Check if the image exists
            Image image = catalogue.getImage();

            //get the imageUrl
            String imageUrl = environment.getProperty("IMAGE_PATH_URL");


            //If the image is set, then we need to set the image path
            if ( image !=  null ) {

                // Get the image path
                String imagePath  = imageService.getPathForImage(image, ImagePathType.MOBILE);

                // Set the imagePath
                catalogueCompatableResource.setCat_image(imageUrl + imagePath);


            }

            catalogueCompatableResource.setCat_image_id(catalogue.getCatProductImage()==null?0L:catalogue.getCatProductImage());

            catalogueCompatableResource.setCat_rwd_points(catalogue.getCatNumPoints()==null?0.0:catalogue.getCatNumPoints());

            catalogueCompatableResource.setCat_cash(catalogue.getCatPartialCash()==null?0.0:catalogue.getCatPartialCash());

            //find reward balance
            CustomerRewardBalance customerRewardBalance =customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(cusLoyaltyId,merchantNo,catalogue.getCatRewardCurrencyId());

            if(customerRewardBalance !=null){

                Double rewardBalance = customerRewardBalance.getCrbRewardBalance();

                if(rewardBalance !=null){


                    //check customer has balance   if its true set 1 otherwise set o
                    if(rewardBalance>=catalogue.getCatNumPoints()){

                        customerHasPoint =1;
                    }
                }

                catalogueCompatableResource.setCat_rwd_balance(rewardBalance==null?0.0:rewardBalance);




            }

            catalogueCompatableResource.setCat_rwd_currency(catalogue.getCatRewardCurrencyId()==null?0:catalogue.getCatRewardCurrencyId());

            //for setting merchant name
            Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);

            if(merchant !=null){

                catalogueCompatableResource.setCat_merchant_name(merchant.getMerMerchantName()==null?"":merchant.getMerMerchantName());
            }


            catalogueCompatableResource.setCat_has_balance(customerHasPoint);

            catalogueCompatableResourcesList.add(catalogueCompatableResource);




        }


        return catalogueCompatableResourcesList;

    }

    @Override
    public CatalogueCompatableResource toResource(Catalogue catalogue) {

        CatalogueCompatableResource catalogueCompatableResource =new CatalogueCompatableResource();

        return catalogueCompatableResource;
    }
}
