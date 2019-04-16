package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;


//@Entity(nameInDb = "hospital_info")
@Entity
@Data
public class Hospital extends Page implements Serializable {

    private static final long serialVersionUID = 6977402643848374753L;

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
    @Property(nameInDb = "hospital_addr")
    private String hospitalAddr;
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
    @Generated(hash = 2008717252)
    public Hospital(String hospitalId, String hospitalManager, String managerName,
            String hospitalName, String hospitalGrade, String hospitalAddr,
            String detailAddr, String hospitalPhone, String introduction,
            String isValid, String hospitalPicture) {
        this.hospitalId = hospitalId;
        this.hospitalManager = hospitalManager;
        this.managerName = managerName;
        this.hospitalName = hospitalName;
        this.hospitalGrade = hospitalGrade;
        this.hospitalAddr = hospitalAddr;
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
    public String getHospitalAddr() {
        return this.hospitalAddr;
    }
    public void setHospitalAddr(String hospitalAddr) {
        this.hospitalAddr = hospitalAddr;
    }

}
