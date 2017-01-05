package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 13/5/14.
 */
@Entity
@Table(name = "IMAGES")
public class Image {


    @Id
    @Column(name = "IMG_IMAGE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgImageId;

    @Basic
    @Column(name = "IMG_IMAGE_EXT", nullable = false)
    @Size(max=5,message = "{image.imgimageext.size}")
    private String imgImageExt;

    @Basic
    @Column(name = "IMG_IMAGE_TYPE", nullable = false)
    @Range(min=1L,max = 50L,message = "{image.imgimagetype.range}")
    private int imgImageType;

    @Basic
    @Column(name = "IMG_TIMESTAMP", nullable = false)
    private Timestamp imgTimestamp;

    @Basic
    @Column(name = "IMG_IN_USE_INDICATOR", nullable = false)
    private int imgInUseIndicator;


    @PrePersist
    protected void updateTimestamp() {

        imgTimestamp = new Timestamp(new java.util.Date().getTime());

    }

    public Long getImgImageId() {
        return imgImageId;
    }

    public void setImgImageId(Long imgImageId) {
        this.imgImageId = imgImageId;
    }

    public String getImgImageExt() {
        return imgImageExt;
    }

    public void setImgImageExt(String imgImageExt) {
        this.imgImageExt = imgImageExt;
    }

    public int getImgImageType() {
        return imgImageType;
    }

    public void setImgImageType(int imgImageType) {
        this.imgImageType = imgImageType;
    }

    public Timestamp getImgTimestamp() {
        return imgTimestamp;
    }

    public void setImgTimestamp(Timestamp imgTimestamp) {
        this.imgTimestamp = imgTimestamp;
    }

    public int getImgInUseIndicator() {
        return imgInUseIndicator;
    }

    public void setImgInUseIndicator(int imgInUseIndicator) {
        this.imgInUseIndicator = imgInUseIndicator;
    }



    @Override
    public String toString() {
        return "Image{" +
                "imgImageId=" + imgImageId +
                ", imgImageExt='" + imgImageExt + '\'' +
                ", imgImageType=" + imgImageType +
                ", imgTimestamp=" + imgTimestamp +
                ", imgInUseIndicator=" + imgInUseIndicator +
                '}';
    }
}
