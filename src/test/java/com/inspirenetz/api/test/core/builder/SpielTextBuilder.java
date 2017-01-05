package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.SpielText;

import java.util.Date;

/**
 * Created by alameen on 3/2/15.
 */
public class SpielTextBuilder {
    private Long sptId;
    private Long sptRef;
    private Integer  sptChannel;
    private Long sptLocation ;
    private String sptDescription;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SpielTextBuilder() {
    }

    public static SpielTextBuilder aSpielText() {
        return new SpielTextBuilder();
    }

    public SpielTextBuilder withSptId(Long sptId) {
        this.sptId = sptId;
        return this;
    }

    public SpielTextBuilder withSptRef(Long sptRef) {
        this.sptRef = sptRef;
        return this;
    }

    public SpielTextBuilder withSptChannel(Integer sptChannel) {
        this.sptChannel = sptChannel;
        return this;
    }

    public SpielTextBuilder withSptLocation(Long sptLocation) {
        this.sptLocation = sptLocation;
        return this;
    }

    public SpielTextBuilder withSptDescription(String sptDescription) {
        this.sptDescription = sptDescription;
        return this;
    }

    public SpielTextBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SpielTextBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SpielTextBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SpielTextBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SpielTextBuilder but() {
        return aSpielText().withSptId(sptId).withSptRef(sptRef).withSptChannel(sptChannel).withSptLocation(sptLocation).withSptDescription(sptDescription).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public SpielText build() {
        SpielText spielText = new SpielText();
        spielText.setSptId(sptId);
        spielText.setSptRef(sptRef);
        spielText.setSptChannel(sptChannel);
        spielText.setSptLocation(sptLocation);
        spielText.setSptDescription(sptDescription);
        spielText.setCreatedAt(createdAt);
        spielText.setCreatedBy(createdBy);
        spielText.setUpdatedAt(updatedAt);
        spielText.setUpdatedBy(updatedBy);
        return spielText;
    }
}
