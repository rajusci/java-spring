package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.OrderSlotSession;
import com.inspirenetz.api.core.dictionary.OrderSlotType;
import com.inspirenetz.api.core.domain.OrderSlot;

import java.sql.Time;

/**
 * Created by sandheepgr on 29/7/14.
 */
public class OrderSlotBuilder {
    private Long ortId;
    private Integer ortType = OrderSlotType.ORT_TYPE_PICKUP;
    private Long ortMerchantNo = 0L;
    private Long ortLocation = 0L;
    private Integer ortSession = OrderSlotSession.ORT_SESSION_BREAKFAST;
    private Time ortStartingTime;
    private String ortDisplayTitle;

    private OrderSlotBuilder() {
    }

    public static OrderSlotBuilder anOrderSlot() {
        return new OrderSlotBuilder();
    }

    public OrderSlotBuilder withOrtId(Long ortId) {
        this.ortId = ortId;
        return this;
    }

    public OrderSlotBuilder withOrtType(Integer ortType) {
        this.ortType = ortType;
        return this;
    }

    public OrderSlotBuilder withOrtMerchantNo(Long ortMerchantNo) {
        this.ortMerchantNo = ortMerchantNo;
        return this;
    }

    public OrderSlotBuilder withOrtLocation(Long ortLocation) {
        this.ortLocation = ortLocation;
        return this;
    }

    public OrderSlotBuilder withOrtSession(Integer ortSession) {
        this.ortSession = ortSession;
        return this;
    }

    public OrderSlotBuilder withOrtStartingTime(Time ortStartingTime) {
        this.ortStartingTime = ortStartingTime;
        return this;
    }

    public OrderSlotBuilder withOrtDisplayTitle(String ortDisplayTitle) {
        this.ortDisplayTitle = ortDisplayTitle;
        return this;
    }

    public OrderSlot build() {
        OrderSlot orderSlot = new OrderSlot();
        orderSlot.setOrtId(ortId);
        orderSlot.setOrtType(ortType);
        orderSlot.setOrtMerchantNo(ortMerchantNo);
        orderSlot.setOrtLocation(ortLocation);
        orderSlot.setOrtSession(ortSession);
        orderSlot.setOrtStartingTime(ortStartingTime);
        orderSlot.setOrtDisplayTitle(ortDisplayTitle);
        return orderSlot;
    }
}
