package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.test.core.builder.RedemptionBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.*;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class RedemptionFixture {

    
    
    public static Redemption standardCatalogueRedemption() {

        Redemption redemption = RedemptionBuilder.aRedemption()
                .withRdmMerchantNo(1L)
                .withRdmDate(DBUtils.covertToSqlDate("2014-05-10"))
                .withRdmTime(DBUtils.convertToSqlTime("10:00:00"))
                .withRdmDeliveryInd(DeliveryIndicator.DELIVERY_REQUIRED)
                .withRdmType(RedemptionType.CATALOGUE)
                .withRdmLoyaltyId("999988887777666441")
                .withRdmProductCode("PRD1002")
                .withRdmQty(1)
                .withRdmRewardCurrencyId(1L)
                .withRdmRewardCurrencyQty(29)
                .withRdmDeliveryAddr1("#30 , Linked Stree")
                .withRdmDeliveryAddr2("Lal Street")
                .withRdmDeliveryCity("Bangalore")
                .withRdmDeliveryPostcode("56001")
                .withRdmContactNumber("9999888877")
                .withRdmUniqueBatchTrackingId("100011")
                .withRdmUserNo(12L)
                .build();

        return redemption;
    }


    public static Redemption updatedStandardCatalogueRedemption(Redemption redemption) {

        redemption.setRdmDeliveryAddr1("#31, Linked streed");
        redemption.setRdmContactNumber("9999888871");

        return redemption;

    }


    public static Set<Redemption> getStandardRedemptions() {

        Set<Redemption> redemptions = new HashSet<Redemption>();

        Redemption redemption1 = RedemptionBuilder.aRedemption()
                .withRdmMerchantNo(1L)
                .withRdmDate(DBUtils.covertToSqlDate("2014-05-10"))
                .withRdmTime(DBUtils.convertToSqlTime("10:00:00"))
                .withRdmDeliveryInd(DeliveryIndicator.DELIVERY_REQUIRED)
                .withRdmLoyaltyId("9999888877776661")
                .withRdmProductCode("PRD1002")
                .withRdmQty(1)
                .withRdmRewardCurrencyId(1L)
                .withRdmRewardCurrencyQty(29)
                .withRdmDeliveryAddr1("#30 , Linked Stree")
                .withRdmDeliveryAddr2("Lal Street")
                .withRdmDeliveryCity("Bangalore")
                .withRdmDeliveryPostcode("56001")
                .withRdmContactNumber("9999888877")
                .withRdmUniqueBatchTrackingId("100011")
                .withRdmUserNo(12L)
                .build();

        redemptions.add(redemption1);


        Redemption redemption2 = RedemptionBuilder.aRedemption()
                .withRdmMerchantNo(1L)
                .withRdmDate(DBUtils.covertToSqlDate("2014-05-10"))
                .withRdmTime(DBUtils.convertToSqlTime("10:00:00"))
                .withRdmDeliveryInd(DeliveryIndicator.DELIVERY_NOT_REQUIRED)
                .withRdmLoyaltyId("9999888877776661")
                .withRdmQty(1)
                .withRdmCashbackAmount(29)
                .withRdmRewardCurrencyId(1L)
                .withRdmRewardCurrencyQty(29)
                .withRdmContactNumber("9999888877")
                .withRdmUniqueBatchTrackingId("100011")
                .withRdmUserNo(12L)
                .build();

        redemptions.add(redemption2);


        return  redemptions;

    }


    public  static RedemptionCatalogue standardRedemptionCatalogue() {

        RedemptionCatalogue redemptionCatalogue = new RedemptionCatalogue();
        redemptionCatalogue.setCatMerchantNo(1L);
        redemptionCatalogue.setCatQty(1);
        redemptionCatalogue.setCatProductCode("PRD1001");
        redemptionCatalogue.setCatProductNo(8L);

        return redemptionCatalogue;

    }


    public static CatalogueRedemptionRequest standardCatalogueRedemptionRequest() {
        
        CatalogueRedemptionRequest catalogueRedemptionRequest = new CatalogueRedemptionRequest();

        List<RedemptionCatalogue> redemptionCatalogueList = new ArrayList<>();
        

        RedemptionCatalogue redemptionCatalogue = standardRedemptionCatalogue();
        redemptionCatalogueList.add(redemptionCatalogue);

        catalogueRedemptionRequest.setRedemptionCatalogues(redemptionCatalogueList);


        catalogueRedemptionRequest.setLoyaltyId("9999888877776661");
        catalogueRedemptionRequest.setMerchantNo(1L);
        catalogueRedemptionRequest.setAddress1("#31, Lambert steet");
        catalogueRedemptionRequest.setAddress2("42nd cross");
        catalogueRedemptionRequest.setCity("Bangalore");
        catalogueRedemptionRequest.setState("Karnataka");
        catalogueRedemptionRequest.setCountry("India");
        catalogueRedemptionRequest.setPincode("560001");
        catalogueRedemptionRequest.setContactNumber("9538828853");
        catalogueRedemptionRequest.setUserNo(1L);
        catalogueRedemptionRequest.setUserLocation(1L);

        return catalogueRedemptionRequest;

    }


    public static CashBackRedemptionRequest standardCashbackRedemptionRequest() {

        CashBackRedemptionRequest cashbackRedemptionRequest = new CashBackRedemptionRequest();

        cashbackRedemptionRequest.setMerchantNo(1L);
        cashbackRedemptionRequest.setUserNo(1L);
        cashbackRedemptionRequest.setLoyaltyId("9999888877776661");
        cashbackRedemptionRequest.setUserLocation(1L);
        cashbackRedemptionRequest.setAmount(10);
        cashbackRedemptionRequest.setTxnRef("10001");
        cashbackRedemptionRequest.setRewardCurrencyId(1L);

        return cashbackRedemptionRequest;


    }
}
