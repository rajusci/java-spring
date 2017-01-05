package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 4/8/14.
 */
public class ImageUploadResponse {

    private Long imgId;

    private String standardPath = "";

    private String mobilePath = "";

    private String thumbnailPath = "";

    private String status;


    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public String getStandardPath() {
        return standardPath;
    }

    public void setStandardPath(String standardPath) {
        this.standardPath = standardPath;
    }

    public String getMobilePath() {
        return mobilePath;
    }

    public void setMobilePath(String mobilePath) {
        this.mobilePath = mobilePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
