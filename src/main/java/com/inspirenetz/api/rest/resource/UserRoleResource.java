package com.inspirenetz.api.rest.resource;



/**
 * Created by ameenci on 10/9/14.
 */
public class UserRoleResource extends BaseResource {


    private Long uerId;
    private Long uerRole;
    private Long uerUserId;

    public Long getUerId() {
        return uerId;
    }

    public void setUerId(Long uerId) {
        this.uerId = uerId;
    }

    public Long getUerRole() {
        return uerRole;
    }

    public void setUerRole(Long uerRole) {
        this.uerRole = uerRole;
    }

    public Long getUerUserId() {
        return uerUserId;
    }

    public void setUerUserId(Long uerUserId) {
        this.uerUserId = uerUserId;
    }

    @Override
    public String toString() {
        return "UserRoleResource{" +
                "uerId=" + uerId +
                ", uerRole=" + uerRole +
                ", uerUserId=" + uerUserId +
                '}';
    }
}
