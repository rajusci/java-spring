package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;

import java.util.Date;
import java.util.Set;

/**
 * Created by alameen on 3/2/15.
 */
public class MessageSpielBuilder {
    public Set<SpielText> spielTexts;
    private Long msiId;
    private String msiName;
    private String msiTariffClass;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private MessageSpielBuilder() {
    }

    public static MessageSpielBuilder aMessageSpiel() {
        return new MessageSpielBuilder();
    }

    public MessageSpielBuilder withMsiId(Long msiId) {
        this.msiId = msiId;
        return this;
    }

    public MessageSpielBuilder withMsiName(String msiName) {
        this.msiName = msiName;
        return this;
    }

    public MessageSpielBuilder withMsiTariffClass(String msiTariffClass) {
        this.msiTariffClass = msiTariffClass;
        return this;
    }

    public MessageSpielBuilder withSpielTexts(Set<SpielText> spielTexts) {
        this.spielTexts = spielTexts;
        return this;
    }

    public MessageSpielBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MessageSpielBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MessageSpielBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MessageSpielBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MessageSpielBuilder but() {
        return aMessageSpiel().withMsiId(msiId).withMsiName(msiName).withMsiTariffClass(msiTariffClass).withSpielTexts(spielTexts).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public MessageSpiel build() {
        MessageSpiel messageSpiel = new MessageSpiel();
        messageSpiel.setMsiId(msiId);
        messageSpiel.setMsiName(msiName);
        messageSpiel.setMsiTariffClass(msiTariffClass);
        messageSpiel.setSpielTexts(spielTexts);
        messageSpiel.setCreatedAt(createdAt);
        messageSpiel.setCreatedBy(createdBy);
        messageSpiel.setUpdatedAt(updatedAt);
        messageSpiel.setUpdatedBy(updatedBy);
        return messageSpiel;
    }
}
