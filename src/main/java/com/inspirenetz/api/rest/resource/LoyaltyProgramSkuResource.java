package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 21/5/14.
 */
public class LoyaltyProgramSkuResource  extends  BaseResource {

    private Long lpuId;

    private Long lpuProgramId ;

    private int lpuItemType ;

    private String lpuItemCode;

    private Long lpuTier;

    private double lpuPrgRatioNum;

    private double lpuPrgRatioDeno;


    public Long getLpuId() {
        return lpuId;
    }

    public void setLpuId(Long lpuId) {
        this.lpuId = lpuId;
    }

    public Long getLpuProgramId() {
        return lpuProgramId;
    }

    public void setLpuProgramId(Long lpuProgramId) {
        this.lpuProgramId = lpuProgramId;
    }

    public int getLpuItemType() {
        return lpuItemType;
    }

    public void setLpuItemType(int lpuItemType) {
        this.lpuItemType = lpuItemType;
    }

    public String getLpuItemCode() {
        return lpuItemCode;
    }

    public void setLpuItemCode(String lpuItemCode) {
        this.lpuItemCode = lpuItemCode;
    }

    public Long getLpuTier() {
        return lpuTier;
    }

    public void setLpuTier(Long lpuTier) {
        this.lpuTier = lpuTier;
    }

    public double getLpuPrgRatioNum() {
        return lpuPrgRatioNum;
    }

    public void setLpuPrgRatioNum(double lpuPrgRatioNum) {
        this.lpuPrgRatioNum = lpuPrgRatioNum;
    }

    public double getLpuPrgRatioDeno() {
        return lpuPrgRatioDeno;
    }

    public void setLpuPrgRatioDeno(double lpuPrgRatioDeno) {
        this.lpuPrgRatioDeno = lpuPrgRatioDeno;
    }
}
