package com.inspirenetz.api.util;

/**
 * Created by sandheepgr on 17/2/14.
 */
public abstract class APIResponseObjectFactory {


    public static APIResponseObject getJSONAPIResponseObject() {

        return new APIResponseObject();

    }
}
