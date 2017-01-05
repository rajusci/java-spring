package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/3/14.
 */
public enum CustomerMasterRawdataStatus {

    NEW(1),
    PROCESSED_SUCCESSFULLY(2),
    FAILED(3);

    private int value;

    CustomerMasterRawdataStatus(int value) {

        this.value = value;

    }
}
