package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 20/4/14.
 */
@Entity
@Table(name = "WALLET_MERCHANT_LOCATION")
public class WalletMerchantLocation {

    @Id
    @Column(name = "WML_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(fetch = FetchType.EAGER)
    private int wmlId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "WML_NAME", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    private String wmlName;



    public int getWmlId() {
        return wmlId;
    }

    public void setWmlId(int wmlId) {
        this.wmlId = wmlId;
    }

    public String getWmlName() {
        return wmlName;
    }

    public void setWmlName(String wmlName) {
        this.wmlName = wmlName;
    }


    @Override
    public String toString() {
        return "WalletMerchantLocation{" +
                "wmlId=" + wmlId +
                ", wmlName='" + wmlName + '\'' +
                '}';
    }
}
