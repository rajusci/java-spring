package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.core.repository.CatalogueFavoriteRepository;
import com.inspirenetz.api.core.service.CatalogueFavoriteService;
import com.inspirenetz.api.core.service.CatalogueService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.logging.resources.logging;

import java.util.List;

/**
 * Created by alameen on 21/10/14.
 */
@Service
public class CatalogueFavoriteServiceImpl extends BaseServiceImpl<CatalogueFavorite> implements CatalogueFavoriteService{

    private static Logger log = LoggerFactory.getLogger(CatalogueServiceImpl.class);

    @Autowired
    CatalogueFavoriteRepository catalogueFavoriteRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    CatalogueService catalogueService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    public CatalogueFavoriteServiceImpl() {

        super(CatalogueFavorite.class);

    }

    @Override
    protected BaseRepository<CatalogueFavorite,Long> getDao() {
        return catalogueFavoriteRepository;
    }

    @Override
    public List<CatalogueFavorite> findByCafLoyaltyId(String loyaltyId) {

        List<CatalogueFavorite> catalogueFavoriteList = catalogueFavoriteRepository.findByCafLoyaltyId(loyaltyId);

        return catalogueFavoriteList;

    }

    @Override
    public List<CatalogueFavorite> findByCafLoyaltyIdAndCafMerchantNo(String loyaltyId,Long cafMerchantNo) {

        List<CatalogueFavorite> catalogueFavoriteList = catalogueFavoriteRepository.findByCafLoyaltyIdAndCafMerchantNo(loyaltyId,cafMerchantNo);

        return catalogueFavoriteList;

    }

    @Override
    public List<CatalogueFavorite> findByCafLoyaltyIdAndCafMerchantNoAndCafFavoriteFlag(String loyaltyId,Long cafMerchantNo,Integer cafFavoriteFlag) {

        List<CatalogueFavorite> catalogueFavoriteList = catalogueFavoriteRepository.findByCafLoyaltyIdAndCafMerchantNoAndCafFavoriteFlag(loyaltyId, cafMerchantNo, cafFavoriteFlag);

        return catalogueFavoriteList;

    }

    @Override
    public CatalogueFavorite saveCatalogueFavorite(CatalogueFavorite catalogueFavorite) {


        //for catalogue favorite is present or not if it present then update
        if(catalogueFavorite !=null){

            CatalogueFavorite searchFavouriteIsPresent =catalogueFavoriteRepository.findByCafLoyaltyIdAndCafProductNo(catalogueFavorite.getCafLoyaltyId(),catalogueFavorite.getCafProductNo());

            if(searchFavouriteIsPresent !=null){

                catalogueFavorite.setCafId(searchFavouriteIsPresent.getCafId());
            }
        }

        return catalogueFavoriteRepository.save(catalogueFavorite);
    }

    @Override
    public CatalogueFavorite findByCafLoyaltyIdAndCafProductNo(String cafLoyaltyId, Long cafProductNo) {

        CatalogueFavorite catalogueFavorite =null;

        if(cafLoyaltyId !=null && cafProductNo !=null){


             catalogueFavorite = catalogueFavoriteRepository.findByCafLoyaltyIdAndCafProductNo(cafLoyaltyId,cafProductNo );
        }

        return catalogueFavorite;
    }

    @Override
    public boolean delete(CatalogueFavorite catalogueFavorite) {

        catalogueFavoriteRepository.delete(catalogueFavorite);

        return true;
    }

    @Override
    public CatalogueFavorite saveCatalogueFavoriteForUser(String usrLoginId,Long cafProductNo,Integer cafFavoriteFlag) throws InspireNetzException{

        //loginId
        User user=userService.findByUsrLoginId(usrLoginId);

        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
        }

        Catalogue catalogue=catalogueService.findByCatProductNo(cafProductNo);

        //check if catalogue exist or not
        if(catalogue==null||catalogue.getCatProductNo()==null){

            //log the info
            log.info("No Catalogue Information Found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
        }

        List<Customer> customers=customerService.getUserMemberships(catalogue.getCatMerchantNo(),user.getUsrUserNo(), CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return null;

        }
        Customer customer=customers.get(0);

        CatalogueFavorite catalogueFavorite=new CatalogueFavorite();

        catalogueFavorite.setCafLoyaltyId(customer.getCusLoyaltyId());

        catalogueFavorite.setCafProductNo(catalogue.getCatProductNo());

        catalogueFavorite.setCafFavoriteFlag(cafFavoriteFlag);

        catalogueFavorite.setCafMerchantNo(customer.getCusMerchantNo());

        CatalogueFavorite searchFavouriteIsPresent =catalogueFavoriteRepository.findByCafLoyaltyIdAndCafProductNoAndCafMerchantNo(catalogueFavorite.getCafLoyaltyId(), catalogueFavorite.getCafProductNo(), catalogueFavorite.getCafMerchantNo());

        if(searchFavouriteIsPresent ==null||searchFavouriteIsPresent.getCafId()==0){

            return catalogueFavoriteRepository.save(catalogueFavorite);

        }

        catalogueFavorite.setCafId(searchFavouriteIsPresent.getCafId());

        return catalogueFavoriteRepository.save(catalogueFavorite);
    }

}
