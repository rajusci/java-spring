package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/3/14.
 */
public enum EVoucherStatus {

    PROCESSED(1),
    SENT(2),
    FAILED(3),
    ACCEPTED(4);


    private int value;

    EVoucherStatus(int value) {

        this.value = value;

    }
}
