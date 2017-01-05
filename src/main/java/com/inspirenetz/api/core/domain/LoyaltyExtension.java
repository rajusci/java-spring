package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by saneesh-ci on 10/9/14.
 */
@Entity
@Table(name="LOYALTY_EXTENSION")
public class LoyaltyExtension extends AuditedEntity implements Serializable {

    @Column(name = "LEX_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lexId;

    @Column(name = "LEX_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String lexName;

    @Column(name = "LEX_DESCRIPTION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String lexDescription;

    @Column(name = "LEX_FILE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String lexFile;


    @Column(name = "LEX_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
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
        return "LoyaltyExtension{" +
                "lexId=" + lexId +
                ", lexName='" + lexName + '\'' +
                ", lexDescription='" + lexDescription + '\'' +
                ", lexFile='" + lexFile + '\'' +
                ", lexMerchantNo=" + lexMerchantNo +
                '}';
    }
}
