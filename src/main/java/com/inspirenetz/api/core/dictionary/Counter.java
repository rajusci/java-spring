package com.inspirenetz.api.core.dictionary;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sandheepgr on 10/5/14.
 */

public enum Counter {

    INSTANCE;

    private final AtomicLong value = new AtomicLong();

    public long generate() {
        return value.incrementAndGet();
    }

}
