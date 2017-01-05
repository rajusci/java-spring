package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/3/14.
 */
public enum BulkUploadRawdataStatus {

    NEW(1),
    PROCESSED_SUCCESSFULLY(2),
    FAILED(3),
    WARNING(4)
    ;

    private int value;

    BulkUploadRawdataStatus(int value) {

        this.value = value;

    }
}
