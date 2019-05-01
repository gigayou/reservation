package com.giga.ehospital.reservation.model;


import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

//@Entity(nameInDb = "user_info")
@Entity
@Data
public class User extends Page implements Serializable {

    private static final long serialVersionUID = 6977402643811234454L;

    @Id
    @Property(nameInDb = "user_phone")
    private String userPhone;
    @Property(nameInDb = "user_name")
    private String userName;
    @Property(nameInDb = "sex")
    private String sex;
    @Property(nameInDb = "province")
    private Integer province;
    @Property(nameInDb = "procinve_name")
    private String provinceName;
    @Property(nameInDb = "city")
    private Integer city;
    @Property(nameInDb = "city_name")
    private String cityName;
    @Property(nameInDb = "county")
    private Integer county;
    @Property(nameInDb = "county_name")
    private String countyName;
    @Property(nameInDb = "detail_addr")
    private String detailAddr;
    @Property(nameInDb = "user_pwd")
    private String userPwd;
    @Property(nameInDb = "gmt_create")
    private String gmtCreate;
    @Property(nameInDb = "gmt_modified")
    private String gmtModified;
    @Generated(hash = 2066016879)
    public User(String userPhone, String userName, String sex, Integer province,
            String provinceName, Integer city, String cityName, Integer county,
            String countyName, String detailAddr, String userPwd, String gmtCreate,
            String gmtModified) {
        this.userPhone = userPhone;
        this.userName = userName;
        this.sex = sex;
        this.province = province;
        this.provinceName = provinceName;
        this.city = city;
        this.cityName = cityName;
        this.county = county;
        this.countyName = countyName;
        this.detailAddr = detailAddr;
        this.userPwd = userPwd;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getUserPhone() {
        return this.userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getProvince() {
        return this.province;
    }
    public void setProvince(Integer province) {
        this.province = province;
    }
    public String getProvinceName() {
        return this.provinceName;
    }
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    public Integer getCity() {
        return this.city;
    }
    public void setCity(Integer city) {
        this.city = city;
    }
    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public Integer getCounty() {
        return this.county;
    }
    public void setCounty(Integer county) {
        this.county = county;
    }
    public String getCountyName() {
        return this.countyName;
    }
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    public String getDetailAddr() {
        return this.detailAddr;
    }
    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }
    public String getUserPwd() {
        return this.userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getGmtCreate() {
        return this.gmtCreate;
    }
    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public String getGmtModified() {
        return this.gmtModified;
    }
    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
