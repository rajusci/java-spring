package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class UserAccessRightResource extends BaseResource {

    private Long uarUarId;

    private Long uarUserNo =  0L;

    private Long uarFunctionCode = 0L;

    private String uarAccessEnableFlag = "N";


    public Long getUarUarId() {
        return uarUarId;
    }

    public void setUarUarId(Long uarUarId) {
        this.uarUarId = uarUarId;
    }

    public Long getUarUserNo() {
        return uarUserNo;
    }

    public void setUarUserNo(Long uarUserNo) {
        this.uarUserNo = uarUserNo;
    }

    public Long getUarFunctionCode() {
        return uarFunctionCode;
    }

    public void setUarFunctionCode(Long uarFunctionCode) {
        this.uarFunctionCode = uarFunctionCode;
    }

    public String getUarAccessEnableFlag() {
        return uarAccessEnableFlag;
    }

    public void setUarAccessEnableFlag(String uarAccessEnableFlag) {
        this.uarAccessEnableFlag = uarAccessEnableFlag;
    }


}
