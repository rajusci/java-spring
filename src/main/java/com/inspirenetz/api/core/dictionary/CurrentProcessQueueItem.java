package com.inspirenetz.api.core.dictionary;

import java.util.Date;

/**
 * Created by sandheepgr on 29/4/16.
 */
public class CurrentProcessQueueItem {

    public String key;

    public Date expiry;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }


    @Override
    public String toString() {
        return "CurrentProcessQueueItem{" +
                "key='" + key + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}
