package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CATALOGUE_CATEGORIES")
public class CatalogueCategory extends AuditedEntity {

    @Id
    @Column(name = "CAC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cacId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAC_NAME")
    @NotEmpty(message = "{cataloguecategory.cacname.notempty}")
    @Size(min=1,max=50,message = "{cataloguecategory.cacname.size}")
    private String cacName = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAC_DESCRIPTION")
    @Size(max=100,message = "{cataloguecategory.cacdescription.size}")
    private String cacDescription = "'";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAC_PARENT_GROUP")
    private Integer cacParentGroup = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAC_FIRST_LEVEL_IND")
    private Integer cacFirstLevelInd = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAC_IMAGE")
    private Long cacImage = ImagePrimaryId.PRIMARY_CATALOGUE_CATEGORY_IMAGE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CAC_IMAGE",insertable = false,updatable = false)
    private Image image;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CAC_MERCHANT_NO")
    private Long cacMerchantNo;


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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public Long getCacMerchantNo() {
        return cacMerchantNo;
    }

    public void setCacMerchantNo(Long cacMerchantNo) {
        this.cacMerchantNo = cacMerchantNo;
    }

    @Override
    public String toString() {
        return "CatalogueCategory{" +
                "cacId=" + cacId +
                ", cacName='" + cacName + '\'' +
                ", cacDescription='" + cacDescription + '\'' +
                ", cacParentGroup=" + cacParentGroup +
                ", cacFirstLevelInd=" + cacFirstLevelInd +
                ", cacImage=" + cacImage +
                ", image=" + image +
                ", cacMerchantNo=" + cacMerchantNo +
                '}';
    }
}
