package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 20/4/14.
 */
@Entity
@Table(name = "WALLET_MERCHANT")
public class WalletMerchant {


    @Id
    @Column(name = "WMT_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(fetch = FetchType.EAGER)
    private Long wmtMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "WMT_NAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String wmtName;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "WMT_LOCATION", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer wmtLocation;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "WMT_IMAGE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer wmtImage;

    public Long getWmtMerchantNo() {
        return wmtMerchantNo;
    }

    public void setWmtMerchantNo(Long wmtMerchantNo) {
        this.wmtMerchantNo = wmtMerchantNo;
    }

    public String getWmtName() {
        return wmtName;
    }

    public void setWmtName(String wmtName) {
        this.wmtName = wmtName;
    }

    public Integer getWmtLocation() {
        return wmtLocation;
    }

    public void setWmtLocation(Integer wmtLocation) {
        this.wmtLocation = wmtLocation;
    }

    public Integer getWmtImage() {
        return wmtImage;
    }

    public void setWmtImage(Integer wmtImage) {
        this.wmtImage = wmtImage;
    }


    @Override
    public String toString() {
        return "WalletMerchant{" +
                "wmtMerchantNo=" + wmtMerchantNo +
                ", wmtName='" + wmtName + '\'' +
                ", wmtLocation=" + wmtLocation +
                ", wmtImage=" + wmtImage +
                '}';
    }
}
