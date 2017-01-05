package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.LoyaltyProgramSkuType;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtension;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by sandheepgr on 21/5/14.
 */
@Entity
@Table(name = "LOYALTY_PROGRAM_SKU")
public class LoyaltyProgramSku extends AuditedEntity implements AttributeExtension {


    @Id
    @Column(name = "LPU_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lpuId;

    @Basic
    @Column(name = "LPU_PROGRAM_ID", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long lpuProgramId ;

    @Basic
    @Column(name = "LPU_ITEM_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int lpuItemType = LoyaltyProgramSkuType.PRODUCT_CATEGORY;

    @Basic
    @Column(name = "LPU_ITEM_CODE", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @NotNull(message = "{loyaltyprogramsku.lpuitemcode.notnull}")
    @NotEmpty(message = "{loyaltyprogramsku.lpuitemcode.notempty}")
    @Size(max=100,message = "{loyaltyprogramsku.lpuitemcode.size}")
    private String lpuItemCode = "";

    @Basic
    @Column(name = "LPU_REFERENCE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private String lpuReference = "";

    @Basic
    @Column(name = "LPU_ROLE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer lpuRole;


    @Basic
    @Column(name = "LPU_TRANSACTION_TYPE", nullable = true)
    private Integer lpuTransactionType = 0;


    @Basic
    @Column(name = "LPU_TIER", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long lpuTier = 0L;

    @Basic
    @Column(name = "LPU_PRG_RATIO_NUM", nullable = false, insertable = true, updatable = true, precision = 16,scale = 2)
    private double lpuPrgRatioNum = 1 ;

    @Basic
    @Column(name = "LPU_PRG_RATIO_DENO", nullable = false, insertable = true, updatable = true,  precision = 16,scale=2)
    private double lpuPrgRatioDeno = 1;

    @OneToMany(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name="LUE_LOYALTY_PROGRAM_SKU_ID",referencedColumnName = "LPU_ID" )
    private Set<LoyaltyProgramSkuExtension> loyaltyProgramSkuExtensionSet;


    @Transient
    private AttributeExtendedEntityMap fieldMap;


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

    public Integer getLpuTransactionType() {
        return lpuTransactionType;
    }

    public void setLpuTransactionType(Integer lpuTransactionType) {
        this.lpuTransactionType = lpuTransactionType;
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

    public String getLpuReference() {
        return lpuReference;
    }

    public void setLpuReference(String lpuReference) {
        this.lpuReference = lpuReference;
    }

    public Integer getLpuRole() {
        return lpuRole;
    }

    public void setLpuRole(Integer lpuRole) {
        this.lpuRole = lpuRole;
    }

    public Set<LoyaltyProgramSkuExtension> getLoyaltyProgramSkuExtensionSet() {
        return loyaltyProgramSkuExtensionSet;
    }

    public void setLoyaltyProgramSkuExtensionSet(Set<LoyaltyProgramSkuExtension> loyaltyProgramSkuExtensionSet) {
        this.loyaltyProgramSkuExtensionSet = loyaltyProgramSkuExtensionSet;
    }

    public AttributeExtendedEntityMap getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public String toString() {
        return "LoyaltyProgramSku{" +
                "lpuId=" + lpuId +
                ", lpuProgramId=" + lpuProgramId +
                ", lpuItemType=" + lpuItemType +
                ", lpuItemCode='" + lpuItemCode + '\'' +
                ", lpuTransactionType=" + lpuTransactionType +
                ", lpuTier=" + lpuTier +
                ", lpuPrgRatioNum=" + lpuPrgRatioNum +
                ", lpuPrgRatioDeno=" + lpuPrgRatioDeno +
                ", lpuReference=" + lpuReference +
                ", lpuRole=" + lpuRole +
                ", loyaltyProgramSkuExtensionSet=" + loyaltyProgramSkuExtensionSet +
                ", fieldMap=" + fieldMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyProgramSku that = (LoyaltyProgramSku) o;

        if (lpuItemType != that.lpuItemType) return false;
        if (lpuId != null ? !lpuId.equals(that.lpuId) : that.lpuId != null) return false;
        if (!lpuItemCode.equals(that.lpuItemCode)) return false;
        if (lpuProgramId != null ? !lpuProgramId.equals(that.lpuProgramId) : that.lpuProgramId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lpuId != null ? lpuId.hashCode() : 0;
        result = 31 * result + (lpuProgramId != null ? lpuProgramId.hashCode() : 0);
        result = 31 * result + lpuItemType;
        result = 31 * result + lpuItemCode.hashCode();
        result = 31 * result + (lpuRole!=null?lpuRole.hashCode():0);

        return result;
    }
}
