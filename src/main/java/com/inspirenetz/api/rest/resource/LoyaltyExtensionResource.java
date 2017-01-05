package com.inspirenetz.api.rest.resource;

import java.sql.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class LoyaltyExtensionResource extends BaseResource {


    private Long lexId;

    private String lexName;

    private String lexDescription;

    private String lexFile;

    private Long lexMerchantNo;

    public Long getLexId() {
        return lexId;
    }

    public void setLexId(Long lexId) {
        this.lexId = lexId;
    }

    public String getLexName() {
        return lexName;
    }

    public void setLexName(String lexName) {
        this.lexName = lexName;
    }

    public String getLexDescription() {
        return lexDescription;
    }

    public void setLexDescription(String lexDescription) {
        this.lexDescription = lexDescription;
    }

    public String getLexFile() {
        return lexFile;
    }

    public void setLexFile(String lexFile) {
        this.lexFile = lexFile;
    }

    public Long getLexMerchantNo() {
        return lexMerchantNo;
    }

    public void setLexMerchantNo(Long lexMerchantNo) {
        this.lexMerchantNo = lexMerchantNo;
    }

    @Override
    public String toString() {
        return "LoyaltyExtensionResource{" +
                "lexId=" + lexId +
                ", lexName='" + lexName + '\'' +
                ", lexDescription='" + lexDescription + '\'' +
                ", lexFile='" + lexFile + '\'' +
                ", lexMerchantNo=" + lexMerchantNo +
                '}';
    }
}
