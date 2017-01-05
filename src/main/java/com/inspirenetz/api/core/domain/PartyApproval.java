package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**s
 * Created by saneeshci on 29/8/14.
 */
@Entity
@Table(name="PARTY_APPROVAL")
public class PartyApproval extends AuditedEntity implements Serializable {

    @Column(name = "PAP_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long papId;

    @Column(name = "PAP_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer papType;

    @Column(name = "PAP_REQUEST",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long papRequest;

    @Column(name = "PAP_APPROVER",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long papApprover;

    @Column(name = "PAP_REQUESTOR",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long papRequestor;

    @Column(name = "PAP_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer papStatus;

    @Column(name = "PAP_SENT_DATE_TIME")
    private Timestamp papSentDateTime;

    @Column(name = "PAP_REFERENCE" , nullable = true)
    private String papReference;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "PAP_REQUESTOR",insertable =  false,updatable = false)
    private Customer reqCustomer;


    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        papSentDateTime = new Timestamp(timestamp.getTime());

    }

    @PreUpdate
    private void populateUpdateFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        papSentDateTime = new Timestamp(timestamp.getTime());

    }

    public Long getPapId() {
        return papId;
    }

    public void setPapId(Long papId) {
        this.papId = papId;
    }

    public Integer getPapType() {
        return papType;
    }

    public void setPapType(Integer papType) {
        this.papType = papType;
    }

    public Long getPapRequest() {
        return papRequest;
    }

    public void setPapRequest(Long papRequest) {
        this.papRequest = papRequest;
    }

    public Long getPapApprover() {
        return papApprover;
    }

    public void setPapApprover(Long papApprover) {
        this.papApprover = papApprover;
    }

    public Long getPapRequestor() {
        return papRequestor;
    }

    public void setPapRequestor(Long papRequestor) {
        this.papRequestor = papRequestor;
    }

    public Integer getPapStatus() {
        return papStatus;
    }

    public void setPapStatus(Integer papStatus) {
        this.papStatus = papStatus;
    }

    public Timestamp getPapSentDateTime() {
        return papSentDateTime;
    }

    public void setPapSentDateTime(Timestamp papSentDateTime) {
        this.papSentDateTime = papSentDateTime;
    }

    public Customer getReqCustomer() {
        return reqCustomer;
    }

    public void setReqCustomer(Customer reqCustomer) {
        this.reqCustomer = reqCustomer;
    }

    public String getPapReference() {
        return papReference;
    }

    public void setPapReference(String papReference) {
        this.papReference = papReference;
    }

    @Override
    public String toString() {
        return "PartyApproval{" +
                "papId=" + papId +
                ", papType=" + papType +
                ", papRequest=" + papRequest +
                ", papApprover=" + papApprover +
                ", papRequestor=" + papRequestor +
                ", papStatus=" + papStatus +
                ", papSentDateTime=" + papSentDateTime +
                '}';
    }
}
