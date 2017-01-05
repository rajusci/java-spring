package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.BulkUploadMapping;

import java.util.Set;

/**
 * Created by ameen on 10/9/15.
 */
public class BulkUploadMappingResource {

    public Set<BulkUploadMapping> data;

    public Set<BulkUploadMapping> getData() {
        return data;
    }

    public void setData(Set<BulkUploadMapping> data) {
        this.data = data;
    }
}
