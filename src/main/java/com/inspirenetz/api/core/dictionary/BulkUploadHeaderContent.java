package com.inspirenetz.api.core.dictionary;

import java.util.Map;
import java.util.Set;

/**
 * Created by ameen on 4/9/15.
 */
public class BulkUploadHeaderContent {

   public String fileName;

    public Map<String,String> headerContent;


    public Map<String, String> getHeaderContent() {
        return headerContent;
    }

    public void setHeaderContent(Map<String, String> headerContent) {
        this.headerContent = headerContent;
    }
}
