package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.SpielText;

import java.util.Set;

/**
 * Created by ameenci on 9/9/14.
 */
public class MessageSpielResource extends BaseResource {


    private Long msiId;

    private String msiName;

    private String msiTariffClass;

    public Set<SpielText> spielTexts;

    private String msiDescription;

    public Long getMsiId() {
        return msiId;
    }

    public void setMsiId(Long msiId) {
        this.msiId = msiId;
    }

    public String getMsiName() {
        return msiName;
    }

    public void setMsiName(String msiName) {
        this.msiName = msiName;
    }

    public String getMsiTariffClass() {
        return msiTariffClass;
    }

    public void setMsiTariffClass(String msiTariffClass) {
        this.msiTariffClass = msiTariffClass;
    }

    public Set<SpielText> getSpielTexts() {
        return spielTexts;
    }

    public void setSpielTexts(Set<SpielText> spielTexts) {
        this.spielTexts = spielTexts;
    }

    public String getMsiDescription() {
        return msiDescription;
    }

    public void setMsiDescription(String msiDescription) {
        this.msiDescription = msiDescription;
    }

    @Override
    public String toString() {
        return "MessageSpielResource{" +
                "msiId=" + msiId +
                ", msiName='" + msiName + '\'' +
                ", msiTariffClass='" + msiTariffClass + '\'' +
                ", spielTexts=" + spielTexts +
                ", msiDescription='" + msiDescription + '\'' +
                '}';
    }
}
