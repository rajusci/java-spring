package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by saneeshci on 09/09/14.
 */
@Entity
@Table(name="ROLES")
public class Role extends AuditedEntity {


    @Column(name = "ROL_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    @Column(name = "ROL_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rolName;

    @Column(name = "ROL_DESCRIPTION" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rolDescription;

    @Column(name = "ROL_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rolMerchantNo;

    @Column(name = "ROL_USER_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer rolUserType;

    @Column(name = "ROL_IS_DEFAULT_ROLE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer rolIsDefaultRole = 0;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="RAR_ROLE")
    public Set<RoleAccessRight> roleAccessRightSet;

    public Set<RoleAccessRight> getRoleAccessRightSet() {
        return roleAccessRightSet;
    }

    public void setRoleAccessRightSet(Set<RoleAccessRight> roleAccessRightSet) {
        this.roleAccessRightSet = roleAccessRightSet;
    }




    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getRolDescription() {
        return rolDescription;
    }

    public void setRolDescription(String rolDescription) {
        this.rolDescription = rolDescription;
    }

    public Long getRolMerchantNo() {
        return rolMerchantNo;
    }

    public void setRolMerchantNo(Long rolMerchantNo) {
        this.rolMerchantNo = rolMerchantNo;
    }

    public Integer getRolUserType() {
        return rolUserType;
    }

    public void setRolUserType(Integer rolUserType) {
        this.rolUserType = rolUserType;
    }

    public Integer getRolIsDefaultRole() {
        return rolIsDefaultRole;
    }

    public void setRolIsDefaultRole(Integer rolIsDefaultRole) {
        this.rolIsDefaultRole = rolIsDefaultRole;
    }




    @Override
    public String toString() {
        return "Role{" +
                "rolId=" + rolId +
                ", rolName='" + rolName + '\'' +
                ", rolDescription='" + rolDescription + '\'' +
                ", rolMerchantNo=" + rolMerchantNo +
                ", rolUserType=" + rolUserType +
                ", rolIsDefaultRole=" + rolIsDefaultRole +
                ", roleAccessRightSet=" + roleAccessRightSet +
                '}';
    }
}
