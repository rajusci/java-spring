package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class ModuleResource extends BaseResource {

    private Long mdlId;

    private String mdlName = "";

    private Integer mdlDefaultEnabled = IndicatorStatus.NO;

    private String mdlComment = "";


    public Long getMdlId() {

        return mdlId;
    }

    public void setMdlId(Long mdlId) {
        this.mdlId = mdlId;
    }

    public String getMdlName() {
        return mdlName;
    }

    public void setMdlName(String mdlName) {
        this.mdlName = mdlName;
    }

    public Integer getMdlDefaultEnabled() {
        return mdlDefaultEnabled;
    }

    public void setMdlDefaultEnabled(Integer mdlDefaultEnabled) {
        this.mdlDefaultEnabled = mdlDefaultEnabled;
    }

    public String getMdlComment() {
        return mdlComment;
    }

    public void setMdlComment(String mdlComment) {
        this.mdlComment = mdlComment;
    }
}
