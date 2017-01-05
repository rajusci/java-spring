package com.inspirenetz.api.rest.resource;

import javax.persistence.Column;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class CustomerActivityResource extends BaseResource {



    private Long cuaId;
    private Long cuaMerchantNo;
    private String cuaLoyaltyId;
    private Integer cuaActivityType;
    private Date cuaDate;
    private Time cuaTime;
    private String cuaRemarks;
    private String cuaParams ;

    public Long getCuaId() {
        return cuaId;
    }

    public void setCuaId(Long cuaId) {
        this.cuaId = cuaId;
    }

    public Long getCuaMerchantNo() {
        return cuaMerchantNo;
    }

    public void setCuaMerchantNo(Long cuaMerchantNo) {
        this.cuaMerchantNo = cuaMerchantNo;
    }

    public String getCuaLoyaltyId() {
        return cuaLoyaltyId;
    }

    public void setCuaLoyaltyId(String cuaLoyaltyId) {
        this.cuaLoyaltyId = cuaLoyaltyId;
    }

    public Integer getCuaActivityType() {
        return cuaActivityType;
    }

    public void setCuaActivityType(Integer cuaActivityType) {
        this.cuaActivityType = cuaActivityType;
    }

    public Date getCuaDate() {
        return cuaDate;
    }

    public void setCuaDate(Date cuaDate) {
        this.cuaDate = cuaDate;
    }

    public Time getCuaTime() {
        return cuaTime;
    }

    public void setCuaTime(Time cuaTime) {
        this.cuaTime = cuaTime;
    }

    public String getCuaRemarks() {
        return cuaRemarks;
    }

    public void setCuaRemarks(String cuaRemarks) {
        this.cuaRemarks = cuaRemarks;
    }

    public String getCuaParams() {
        return cuaParams;
    }

    public void setCuaParams(String cuaParams) {
        this.cuaParams = cuaParams;
    }

    @Override
    public String toString() {
        return "CustomerActivityResource{" +
                "cuaId=" + cuaId +
                ", cuaMerchantNo=" + cuaMerchantNo +
                ", cuaLoyaltyId='" + cuaLoyaltyId + '\'' +
                ", cuaActivityType=" + cuaActivityType +
                ", cuaDate=" + cuaDate +
                ", cuaTime=" + cuaTime +
                ", cuaRemarks='" + cuaRemarks + '\'' +
                ", cuaParams='" + cuaParams + '\'' +
                '}';
    }


}
