package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/3/14.
 */
public enum EVoucherPaymentStatus {

    NOT_PAID(0),
    PAID(1),
    FAILED(2);

    private int value;

    EVoucherPaymentStatus(int value) {

        this.value = value;

    }
}
