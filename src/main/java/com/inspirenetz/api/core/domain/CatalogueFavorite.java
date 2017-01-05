package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by alameen on 21/10/14.
 */
@Entity
@Table(name = "CATALOGUE_FAVORITE")
public class CatalogueFavorite {

    @Id
    @Column(name = "CAF_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAF_PRODUCT_NO")
    private Long cafProductNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAF_Loyalty_ID")
    private String cafLoyaltyId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAF_FAVORITE_FLAG")
    private Integer cafFavoriteFlag;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAF_MERCHANT_NO")
    private Long cafMerchantNo;


    public Long getCafId() {
        return cafId;
    }

    public void setCafId(Long cafId) {
        this.cafId = cafId;
    }

    public Long getCafProductNo() {
        return cafProductNo;
    }

    public void setCafProductNo(Long cafProductNo) {
        this.cafProductNo = cafProductNo;
    }

    public String getCafLoyaltyId() {
        return cafLoyaltyId;
    }

    public void setCafLoyaltyId(String cafLoyaltyId) {
        this.cafLoyaltyId = cafLoyaltyId;
    }

    public Long getCafMerchantNo() {
        return cafMerchantNo;
    }

    public void setCafMerchantNo(Long cafMerchantNo) {
        this.cafMerchantNo = cafMerchantNo;
    }

    public Integer getCafFavoriteFlag() {
        return cafFavoriteFlag;
    }

    public void setCafFavoriteFlag(Integer cafFavoriteFlag) {
        this.cafFavoriteFlag = cafFavoriteFlag;
    }


    @Override
    public String toString() {
        return "CatalogueFavorite{" +
                "cafId=" + cafId +
                ", cafProductNo=" + cafProductNo +
                ", cafLoyaltyId='" + cafLoyaltyId + '\'' +
                ", cafFavoriteFlag=" + cafFavoriteFlag +
                ",cafMerchantNo="+cafMerchantNo+
                '}';
    }
}
