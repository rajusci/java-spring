package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.*;

/**
 * Created by sandheepgr on 16/2/14.
 */
public class CustomerCompatibleResource extends BaseResource{


    private Long customerno;

    private String loyalty_id;

    private String firstname;

    private String lastname;

    private String email;

    private String mobile;

    private String address;

    private String city;

    private String pincode ;

    private java.sql.Date birthday;

    private java.sql.Date anniversary;

    public Long getCustomerno() {
        return customerno;
    }

    public void setCustomerno(Long customerno) {
        this.customerno = customerno;
    }

    public String getLoyalty_id() {

        return loyalty_id;
    }

    public void setLoyalty_id(String loyalty_id) {

        this.loyalty_id = loyalty_id;
    }

    public String getFirstname() {

        return firstname;
    }

    public void setFirstname(String firstname) {

        this.firstname = firstname;
    }

    public String getLastname() {

        return lastname;
    }

    public void setLastname(String lastname) {

        this.lastname = lastname;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getMobile() {

        return mobile;
    }

    public void setMobile(String mobile) {

        this.mobile = mobile;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getPincode() {

        return pincode;
    }

    public void setPincode(String pincode) {

        this.pincode = pincode;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public java.sql.Date getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(java.sql.Date anniversary) {
        this.anniversary = anniversary;
    }


    @Override
    public String toString() {
        return "CustomerResourceCompatible{" +
                "customerno="+customerno+'\''+
                "loyalty_id='" + loyalty_id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", pincode='" + pincode + '\'' +
                ", customerbirthday=" + birthday +
                ", customeranniversary=" + anniversary +
                '}';
    }
}
