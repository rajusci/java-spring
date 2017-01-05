package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 28/3/14.
 */
public enum EmailQueueStatus {

    NEW(1),
    SENT(2),
    FAILED(3),
    BLOCKED(4);

    private int value;


    EmailQueueStatus(int value) {

        this.value = value;

    }
}
