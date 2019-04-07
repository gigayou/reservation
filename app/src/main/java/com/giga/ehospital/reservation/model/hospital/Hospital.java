package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;


//@Entity(nameInDb = "hospital_info")
@Entity
@Data
public class Hospital extends Page {

    @Id
    @Property(nameInDb = "hospital_id")
    private String hospitalId;
    @Property(nameInDb = "hospital_manager")
    private String hospitalManager;
    @Property(nameInDb = "manager_name")
    private String managerName;
    @Property(nameInDb = "hospital_name")
    private String hospitalName;
    @Property(nameInDb = "hospital_grade")
    private String hospitalGrade;
    @Property(nameInDb = "province")
    private String province;
    @Property(nameInDb = "province_name")
    private String provinceName;
    @Property(nameInDb = "city")
    private String city;
    @Property(nameInDb = "city_name")
    private String cityName;
    @Property(nameInDb = "county")
    private String county;
    @Property(nameInDb = "county_name")
    private String countyName;
    @Property(nameInDb = "detail_addr")
    private String detailAddr;
    @Property(nameInDb = "hospital_phone")
    private String hospitalPhone;
    @Property(nameInDb = "introduction")
    private String introduction;
    @Property(nameInDb = "is_valid")
    private String isValid;
    @Property(nameInDb = "hospital_picture")
    private String hospitalPicture;
    @Generated(hash = 195423734)
    public Hospital(String hospitalId, String hospitalManager, String managerName,
            String hospitalName, String hospitalGrade, String province,
            String provinceName, String city, String cityName, String county,
            String countyName, String detailAddr, String hospitalPhone,
            String introduction, String isValid, String hospitalPicture) {
        this.hospitalId = hospitalId;
        this.hospitalManager = hospitalManager;
        this.managerName = managerName;
        this.hospitalName = hospitalName;
        this.hospitalGrade = hospitalGrade;
        this.province = province;
        this.provinceName = provinceName;
        this.city = city;
        this.cityName = cityName;
        this.county = county;
        this.countyName = countyName;
        this.detailAddr = detailAddr;
        this.hospitalPhone = hospitalPhone;
        this.introduction = introduction;
        this.isValid = isValid;
        this.hospitalPicture = hospitalPicture;
    }
    @Generated(hash = 1301721268)
    public Hospital() {
    }
    public String getHospitalId() {
        return this.hospitalId;
    }
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    public String getHospitalManager() {
        return this.hospitalManager;
    }
    public void setHospitalManager(String hospitalManager) {
        this.hospitalManager = hospitalManager;
    }
    public String getManagerName() {
        return this.managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public String getHospitalName() {
        return this.hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public String getHospitalGrade() {
        return this.hospitalGrade;
    }
    public void setHospitalGrade(String hospitalGrade) {
        this.hospitalGrade = hospitalGrade;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getProvinceName() {
        return this.provinceName;
    }
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCounty() {
        return this.county;
    }
    public void setCounty(String county) {
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
    public String getHospitalPhone() {
        return this.hospitalPhone;
    }
    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }
    public String getIntroduction() {
        return this.introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getIsValid() {
        return this.isValid;
    }
    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
    public String getHospitalPicture() {
        return this.hospitalPicture;
    }
    public void setHospitalPicture(String hospitalPicture) {
        this.hospitalPicture = hospitalPicture;
    }

}
