package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;

/**
 * Created by sandheepgr on 1/8/14.
 */
public class CatalogueCategoryResource extends BaseResource {

    private Long cacId;

    private String cacName = "";

    private String cacDescription = "'";

    private Integer cacParentGroup = 0;

    private Integer cacFirstLevelInd = IndicatorStatus.NO;

    private Long cacImage = ImagePrimaryId.PRIMARY_CATALOGUE_CATEGORY_IMAGE;

    private String cacImagePath = "";




    public Long getCacId() {
        return cacId;
    }

    public void setCacId(Long cacId) {
        this.cacId = cacId;
    }

    public String getCacName() {
        return cacName;
    }

    public void setCacName(String cacName) {
        this.cacName = cacName;
    }

    public String getCacDescription() {
        return cacDescription;
    }

    public void setCacDescription(String cacDescription) {
        this.cacDescription = cacDescription;
    }

    public Integer getCacParentGroup() {
        return cacParentGroup;
    }

    public void setCacParentGroup(Integer cacParentGroup) {
        this.cacParentGroup = cacParentGroup;
    }

    public Integer getCacFirstLevelInd() {
        return cacFirstLevelInd;
    }

    public void setCacFirstLevelInd(Integer cacFirstLevelInd) {
        this.cacFirstLevelInd = cacFirstLevelInd;
    }

    public Long getCacImage() {
        return cacImage;
    }

    public void setCacImage(Long cacImage) {
        this.cacImage = cacImage;
    }

    public String getCacImagePath() {
        return cacImagePath;
    }

    public void setCacImagePath(String cacImagePath) {
        this.cacImagePath = cacImagePath;
    }


    @Override
    public String toString() {
        return "CatalogueCategoryResource{" +
                "cacId=" + cacId +
                ", cacName='" + cacName + '\'' +
                ", cacDescription='" + cacDescription + '\'' +
                ", cacParentGroup=" + cacParentGroup +
                ", cacFirstLevelInd=" + cacFirstLevelInd +
                ", cacImage=" + cacImage +
                ", cacImagePath='" + cacImagePath + '\'' +
                '}';
    }
}
