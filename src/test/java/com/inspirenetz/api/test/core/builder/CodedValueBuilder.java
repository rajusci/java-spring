package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CodedValue;

/**
 * Created by sandheepgr on 19/5/14.
 */
public class CodedValueBuilder {
    private Long cdvCodeId;
    private int cdvIndex;
    private int cdvCodeValue;
    private String cdvCodeLabel;

    private CodedValueBuilder() {
    }

    public static CodedValueBuilder aCodedValue() {
        return new CodedValueBuilder();
    }

    public CodedValueBuilder withCdvCodeId(Long cdvCodeId) {
        this.cdvCodeId = cdvCodeId;
        return this;
    }

    public CodedValueBuilder withCdvIndex(int cdvIndex) {
        this.cdvIndex = cdvIndex;
        return this;
    }

    public CodedValueBuilder withCdvCodeValue(int cdvCodeValue) {
        this.cdvCodeValue = cdvCodeValue;
        return this;
    }

    public CodedValueBuilder withCdvCodeLabel(String cdvCodeLabel) {
        this.cdvCodeLabel = cdvCodeLabel;
        return this;
    }

    public CodedValue build() {
        CodedValue codedValue = new CodedValue();
        codedValue.setCdvCodeId(cdvCodeId);
        codedValue.setCdvIndex(cdvIndex);
        codedValue.setCdvCodeValue(cdvCodeValue);
        codedValue.setCdvCodeLabel(cdvCodeLabel);
        return codedValue;
    }
}
