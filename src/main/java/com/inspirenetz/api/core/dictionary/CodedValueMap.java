package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/6/14.
 */
public class CodedValueMap {


    private Integer cdvKey;

    private String cdvValue;


    public Integer getCdvKey() {
        return cdvKey;
    }

    public void setCdvKey(Integer cdvKey) {
        this.cdvKey = cdvKey;
    }

    public String getCdvValue() {
        return cdvValue;
    }

    public void setCdvValue(String cdvValue) {
        this.cdvValue = cdvValue;
    }


    @Override
    public String toString() {
        return "CodedValueMap{" +
                "cdvKey=" + cdvKey +
                ", cdvValue='" + cdvValue + '\'' +
                '}';
    }
}
