package com.inspirenetz.api.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantRedemptionPartnerRepository;
import com.inspirenetz.api.core.service.MerchantRedemptionPartnerService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantRedemptionPartnerResource;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 25/6/15.
 */
@Service
public class MerchantRedemptionPartnerServiceImpl extends BaseServiceImpl<MerchantRedemptionPartner> implements MerchantRedemptionPartnerService {

    private static Logger log = LoggerFactory.getLogger(MerchantRedemptionPartnerServiceImpl.class);


    @Autowired
    MerchantRedemptionPartnerRepository merchantRedemptionPartnerRepository;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    @Autowired
    UserService userService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    private Mapper mapper;

    public MerchantRedemptionPartnerServiceImpl() {

        super(MerchantRedemptionPartner.class);

    }

    @Override
    protected BaseRepository<MerchantRedemptionPartner,Long> getDao() {
        return merchantRedemptionPartnerRepository;
    }


    @Override
    public List<MerchantRedemptionPartner> findByMrpMerchantNo(Long mrpMerchantNo) {
        return merchantRedemptionPartnerRepository.findByMrpMerchantNo(mrpMerchantNo);
    }

    @Override
    public MerchantRedemptionPartner findByMrpId(Long mrpId) {
        return merchantRedemptionPartnerRepository.findByMrpId(mrpId);
    }

    @Override
    public MerchantRedemptionPartner findByMrpMerchantNoAndMrpRedemptionMerchant(Long mrpMerchantNo, Long mrpRedemptionMerchant) {
        return merchantRedemptionPartnerRepository.findByMrpMerchantNoAndMrpRedemptionMerchant(mrpMerchantNo,mrpRedemptionMerchant);
    }

    @Override
    public MerchantRedemptionPartner saveMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner) {
        return merchantRedemptionPartnerRepository.save(merchantRedemptionPartner);
    }

    @Override
    public void deleteMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner) {
         merchantRedemptionPartnerRepository.delete(merchantRedemptionPartner);
    }

    @Override
    public MerchantRedemptionPartner validateAndSaveMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner) {
        return saveMerchantRedemptionPartner(merchantRedemptionPartner);
    }

    @Override
    public Page<MerchantRedemptionPartner> getRedemptionPartnerFormAdminFilter(Long merchantNo,String filter,String query) {

        //initialise merchant redemption merchant
        Page<MerchantRedemptionPartner> merchantRedemptionPartners =null;

        //check redemption merchant list
        List<RedemptionMerchant> redemptionMerchantList =null;

        List<MerchantRedemptionPartner> merchantRedemptionPartnerList =null;

        //check filer conditions
        if(filter.equals("name")){

            //get redemption merchant name based on filer condition
            redemptionMerchantList =redemptionMerchantService.searchRedemptionMerchants("%" + query + "%");

        }else {

            //get default redemption merchant
            redemptionMerchantList =redemptionMerchantService.findAll();
        }


        //initialise merchant redemption merchant list
        MerchantRedemptionPartner merchantRedemptionPartners1 =null;

        //get redemption merchant list from merchant
        List<MerchantRedemptionPartner> redemptionPartners =findByMrpMerchantNo(merchantNo);

        //get redemption merchant list from merchant
        List<MerchantRedemptionPartner> redemptionPartners1 =new ArrayList<>();

        //check merchant redemption merchant is null or empty
        if(redemptionPartners ==null ||redemptionPartners.isEmpty()){

            //create merchant redemption merchant list
            merchantRedemptionPartnerList =new ArrayList<>();

            for (RedemptionMerchant merchantRedemptionPartner:redemptionMerchantList){

                //convert redemption merchant entry to merchant redemption merchant
                merchantRedemptionPartners1 =new MerchantRedemptionPartner();

                //map redemption merchant field into merchant settings
                merchantRedemptionPartners1 =setRelatedField(merchantRedemptionPartner,merchantRedemptionPartners1,merchantNo);

                //add into list
                merchantRedemptionPartnerList.add(merchantRedemptionPartners1);

            }

            //return merchant redemption merchant list
            return new PageImpl<>(merchantRedemptionPartnerList);

        }

        //check present field
        boolean hasPresent= false;

        //else iterate redemption merchant merchant redemption merchant list
        for (RedemptionMerchant redemptionMerchant:redemptionMerchantList){

            for (MerchantRedemptionPartner merchantRedemptionPartner:redemptionPartners){

                //check if the redemption merchant id is present  inthe merchant redemption merchant
                if(merchantRedemptionPartner.getMrpRedemptionMerchant().longValue() == redemptionMerchant.getRemNo().longValue()){

                    hasPresent =true;

                    //set redemption merchant name
                    merchantRedemptionPartner.setRedemptionMerchantName(redemptionMerchant.getRemName());

                    //add into merchant list
                    redemptionPartners1.add(merchantRedemptionPartner);

                }

            }

            //check if its present or not
            if(!hasPresent){

                //set related field
                merchantRedemptionPartners1 =new MerchantRedemptionPartner();

                merchantRedemptionPartners1 =setRelatedField(redemptionMerchant,merchantRedemptionPartners1,merchantNo);

                redemptionPartners1.add(merchantRedemptionPartners1);

            }

            //set flag in false for next iteration
            hasPresent =false;
        }

        //return
        return new PageImpl<>(redemptionPartners1);
    }

    @Override
    public List<MerchantRedemptionPartner> findByMrpMerchantNoAndMrpEnabled(Long merchantNo) {
        return merchantRedemptionPartnerRepository.findByMrpMerchantNoAndMrpEnabled(merchantNo,IndicatorStatus.YES);
    }

    @Override
    public List<Merchant> getMerchantsForRedemptionPartner(String userLoginId) throws InspireNetzException {

        //get the user details
        User user = userService.findByUsrLoginId(userLoginId);

        //check the user object
        if(user == null){

            return null;
        }

        //get the redemption merchant number
        Long redemptionMerchantNumber = user.getUsrThirdPartyVendorNo();

        List<Merchant> merchantList = new ArrayList<>();

        merchantList = getMerchantsListByRedemptionPartner(redemptionMerchantNumber);

        //return the list
        return merchantList;


    }

    @Override
    public List<Merchant> getMerchantsListByRedemptionPartner(Long redemptionPartnerNo){

        //initialize merchant list
        List<Merchant> merchantList = new ArrayList<>();

        //get the list partners for redemption merchants
        List<MerchantRedemptionPartner> merchantRedemptionPartners  = findByMrpRedemptionMerchantAndMrpEnabled(redemptionPartnerNo, IndicatorStatus.YES);

        //iterate through the list and get merchant details
        for(MerchantRedemptionPartner merchantRedemptionPartner : merchantRedemptionPartners){

            //get the merchant details
            Merchant merchant = merchantService.findByMerMerchantNo(merchantRedemptionPartner.getMrpMerchantNo());

            if(merchant != null){

                //add to the list
                merchantList.add(merchant);

            }


        }

        return merchantList;
    }

    @Override
    public List<MerchantRedemptionPartner> findByMrpRedemptionMerchantAndMrpEnabled(Long redemptionPartnerNo, int mrpEnabled) {

        return merchantRedemptionPartnerRepository.findByMrpRedemptionMerchantAndMrpEnabled(redemptionPartnerNo,mrpEnabled);

    }

    @Override
    public List<Merchant> getMerchantPartnersByPartnerCode(String partnerCode){

        RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemCode(partnerCode);

        List<Merchant> merchants = new ArrayList<>(0);

        if(redemptionMerchant != null){

            merchants = getMerchantsListByRedemptionPartner(redemptionMerchant.getRemNo());
        }

        return merchants;
    }
    @Override
    public List<RedemptionMerchant> getPartnersForMerchant(Long merchantNo) throws InspireNetzException {

        //initialize merchant list
        List<RedemptionMerchant> merchantList = new ArrayList<>();

        //get the list partners for redemption merchants
        List<MerchantRedemptionPartner> merchantRedemptionPartners  = findByMrpMerchantNoAndMrpEnabled(merchantNo);

        //iterate through the list and get merchant details
        for(MerchantRedemptionPartner merchantRedemptionPartner : merchantRedemptionPartners){

            //get the merchant details
            RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(merchantRedemptionPartner.getMrpRedemptionMerchant());

            if(redemptionMerchant != null){

                //add to the list
                merchantList.add(redemptionMerchant);

            }


        }

        //return the list
        return merchantList;


    }

    @Override
    public List<MerchantRedemptionPartner> findByMrpRedemptionMerchant(Long mrpRedemptionMerchant) {

        List<MerchantRedemptionPartner> merchantRedemptionPartners = merchantRedemptionPartnerRepository.findByMrpRedemptionMerchant(mrpRedemptionMerchant);

        return merchantRedemptionPartners;
    }



    private MerchantRedemptionPartner setRelatedField(RedemptionMerchant redemptionMerchant, MerchantRedemptionPartner merchantRedemptionPartners1, Long merchantNo) {

        //set related field only its calling for redemption merchant not present in the list
        merchantRedemptionPartners1.setMrpEnabled(IndicatorStatus.NO);

        //set merchant number
        merchantRedemptionPartners1.setMrpMerchantNo(merchantNo);

        //redemption merchant number
        merchantRedemptionPartners1.setMrpRedemptionMerchant(redemptionMerchant.getRemNo());

        //merchant name
        merchantRedemptionPartners1.setRedemptionMerchantName(redemptionMerchant.getRemName());

        //return parntener
        return merchantRedemptionPartners1;

    }

    @Override
    public double getCashbackQtyForAmount(Double ratioDeno, double amount) {

        // Get the reward qty
        double rewardQty = amount * ratioDeno;

        // Return the reward qty
        return rewardQty;
    }


    @Override
    public List<MerchantRedemptionPartnerResource> getMerchantRedemptionPartnerResources(Long mrpRedemptionMerchant) {

        // Get the list of entries
        List<MerchantRedemptionPartner> merchantRedemptionPartners = findByMrpRedemptionMerchant(mrpRedemptionMerchant);

        // List holding the resources
        List<MerchantRedemptionPartnerResource> merchantRedemptionPartnerResourceList = new ArrayList<>(0);

        // Check if there are entries
        if ( merchantRedemptionPartners == null || merchantRedemptionPartners.isEmpty() ) {

            // Log the infomration
            log.info("No redemption partner entries found");

            // return empty object
            return merchantRedemptionPartnerResourceList;
        }

        // Iterate through the merchantRedemptionPartners
        for( MerchantRedemptionPartner mrp : merchantRedemptionPartners ) {

            // check if the status is enabled
            if ( mrp.getMrpEnabled() == IndicatorStatus.NO ) {

                // continue the loop
                continue;

            }

            // Create the object
            MerchantRedemptionPartnerResource merchantRedemptionPartnerResource = mapper.map(mrp, MerchantRedemptionPartnerResource.class);

            // add to the list
            merchantRedemptionPartnerResourceList.add(merchantRedemptionPartnerResource);

        };

        // return the list
        return merchantRedemptionPartnerResourceList;
    }


}
