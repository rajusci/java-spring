package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Module;

import java.util.Date;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class ModuleBuilder {
    private Long mdlId;
    private String mdlName = "";
    private Integer mdlDefaultEnabled = IndicatorStatus.NO;
    private String mdlComment = "";
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private ModuleBuilder() {
    }

    public static ModuleBuilder aModule() {
        return new ModuleBuilder();
    }

    public ModuleBuilder withMdlId(Long mdlId) {
        this.mdlId = mdlId;
        return this;
    }

    public ModuleBuilder withMdlName(String mdlName) {
        this.mdlName = mdlName;
        return this;
    }

    public ModuleBuilder withMdlDefaultEnabled(Integer mdlDefaultEnabled) {
        this.mdlDefaultEnabled = mdlDefaultEnabled;
        return this;
    }

    public ModuleBuilder withMdlComment(String mdlComment) {
        this.mdlComment = mdlComment;
        return this;
    }

    public ModuleBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ModuleBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ModuleBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ModuleBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Module build() {
        Module module = new Module();
        module.setMdlId(mdlId);
        module.setMdlName(mdlName);
        module.setMdlDefaultEnabled(mdlDefaultEnabled);
        module.setMdlComment(mdlComment);
        module.setCreatedAt(createdAt);
        module.setCreatedBy(createdBy);
        module.setUpdatedAt(updatedAt);
        module.setUpdatedBy(updatedBy);
        return module;
    }
}
