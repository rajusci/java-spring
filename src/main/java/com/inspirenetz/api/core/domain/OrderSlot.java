package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.OrderSlotSession;
import com.inspirenetz.api.core.dictionary.OrderSlotType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

/**
 * Created by sandheepgr on 29/7/14.
 */
@Entity
@Table(name = "ORDER_SLOTS")
public class OrderSlot extends AuditedEntity {

    @Id
    @Column(name = "ORT_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ortId;

    @Basic
    @Column(name = "ORT_TYPE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer ortType = OrderSlotType.ORT_TYPE_PICKUP;

    @Basic
    @Column(name = "ORT_MERCHANT_NO", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ortMerchantNo = 0L;

    @Basic
    @Column(name = "ORT_LOCATION", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ortLocation = 0L;

    @Basic
    @Column(name = "ORT_SESSION", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer ortSession = OrderSlotSession.ORT_SESSION_BREAKFAST;

    @Basic
    @Column(name = "ORT_STARTING_TIME", nullable = true, insertable = true, updatable = true, length = 8, precision = 0)
    private Time ortStartingTime;

    @Basic
    @Column(name = "ORT_DISPLAY_TITLE", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @NotEmpty(message = "{orderslot.ortdisplaytitle.notempty}")
    @NotNull(message = "{orderslot.ortdisplaytitle.notnull}")
    private String ortDisplayTitle ="";


    public Long getOrtId() {
        return ortId;
    }

    public void setOrtId(Long ortId) {
        this.ortId = ortId;
    }

    public Integer getOrtType() {
        return ortType;
    }

    public void setOrtType(Integer ortType) {
        this.ortType = ortType;
    }

    public Long getOrtMerchantNo() {
        return ortMerchantNo;
    }

    public void setOrtMerchantNo(Long ortMerchantNo) {
        this.ortMerchantNo = ortMerchantNo;
    }

    public Long getOrtLocation() {
        return ortLocation;
    }

    public void setOrtLocation(Long ortLocation) {
        this.ortLocation = ortLocation;
    }

    public Integer getOrtSession() {
        return ortSession;
    }

    public void setOrtSession(Integer ortSession) {
        this.ortSession = ortSession;
    }

    public Time getOrtStartingTime() {
        return ortStartingTime;
    }

    public void setOrtStartingTime(Time ortStartingTime) {
        this.ortStartingTime = ortStartingTime;
    }

    public String getOrtDisplayTitle() {
        return ortDisplayTitle;
    }

    public void setOrtDisplayTitle(String ortDisplayTitle) {
        this.ortDisplayTitle = ortDisplayTitle;
    }



    @Override
    public String toString() {
        return "OrderSlot{" +
                "ortId=" + ortId +
                ", ortType=" + ortType +
                ", ortMerchantNo=" + ortMerchantNo +
                ", ortLocation=" + ortLocation +
                ", ortSession=" + ortSession +
                ", ortStartingTime=" + ortStartingTime +
                ", ortDisplayTitle='" + ortDisplayTitle + '\'' +
                '}';
    }
}
