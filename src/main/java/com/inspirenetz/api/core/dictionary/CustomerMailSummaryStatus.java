package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/3/14.
 */
public enum CustomerMailSummaryStatus {

    SENT(1),
    INVALID_PERIOD(2),
    NO_CONTENT(3),
    SENT_FAILED(4);

    private int value;

    CustomerMailSummaryStatus(int value) {

        this.value = value;

    }
}
