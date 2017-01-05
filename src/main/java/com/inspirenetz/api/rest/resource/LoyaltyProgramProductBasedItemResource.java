package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 10/9/14.
 */
public class LoyaltyProgramProductBasedItemResource extends BaseResource {

    private String name;

    private String type;

    private String code;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "LoyaltyProgramProductBasedItemResource{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
