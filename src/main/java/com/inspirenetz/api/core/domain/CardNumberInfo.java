package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by ameen on 20/10/15.
 */
@Entity
@Table(name = "CARD_NUMBER_INFO")
public class CardNumberInfo extends AuditedEntity{

    @Column(name = "CNI_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cniId;


    @Column(name = "CNI_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cniMerchantNo;

    @Column(name = "CNI_CARD_NUMBER",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String cniCardNumber;

    @Column(name = "CNI_CARD_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cniCardType;

    @Column(name = "CNI_BATCH_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cniBatchId;

    @Column(name = "CNI_PIN",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String cniPin;

    @Column(name = "CNI_CARD_STATUS")
    @Basic(fetch = FetchType.EAGER)
    private Integer cniCardStatus= IndicatorStatus.NO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CNI_CARD_TYPE",insertable = false,updatable = false)
    private CardType cardType;

    public Long getCniId() {
        return cniId;
    }

    public void setCniId(Long cniId) {
        this.cniId = cniId;
    }

    public Long getCniMerchantNo() {
        return cniMerchantNo;
    }

    public void setCniMerchantNo(Long cniMerchantNo) {
        this.cniMerchantNo = cniMerchantNo;
    }

    public String getCniCardNumber() {
        return cniCardNumber;
    }

    public void setCniCardNumber(String cniCardNumber) {
        this.cniCardNumber = cniCardNumber;
    }

    public Long getCniCardType() {
        return cniCardType;
    }

    public void setCniCardType(Long cniCardType) {
        this.cniCardType = cniCardType;
    }

    public Long getCniBatchId() {
        return cniBatchId;
    }

    public void setCniBatchId(Long cniBatchId) {
        this.cniBatchId = cniBatchId;
    }

    public Integer getCniCardStatus() {
        return cniCardStatus;
    }

    public void setCniCardStatus(Integer cniCardStatus) {
        this.cniCardStatus = cniCardStatus;
    }

    public String getCniPin() {
        return cniPin;
    }

    public void setCniPin(String cniPin) {
        this.cniPin = cniPin;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "CardNumberInfo{" +
                "cniId=" + cniId +
                ", cniMerchantNo=" + cniMerchantNo +
                ", cniCardNumber='" + cniCardNumber + '\'' +
                ", cniCardType=" + cniCardType +
                ", cniBatchId=" + cniBatchId +
                ", cniPin='" + cniPin + '\'' +
                ", cniCardStatus=" + cniCardStatus +
                ", cardType=" + cardType +
                '}';
    }
}
