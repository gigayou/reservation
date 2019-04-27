package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

//@Entity(nameInDb = "doctor_info")
@Entity
@Data
public class Doctor extends Page implements Serializable {

    private static final long serialVersionUID = 6977402643855467865L;

    @Id
    @Property(nameInDb = "doctor_id")
    private String doctorId;
    @Property(nameInDb = "hospital_id")
    private String hospitalId;
    @Property(nameInDb = "hospital_name")
    private String hospitalName;
    @Property(nameInDb = "type_id")
    private Long typeId;
    @Property(nameInDb = "type_name")
    private String typeName;
    @Property(nameInDb = "doctor_name")
    private String doctorName;
    @Property(nameInDb = "sex")
    private Integer sex;
    @Property(nameInDb = "doctor_title")
    private String doctorTitle;
    @Property(nameInDb = "skill")
    private String skill;
    @Property(nameInDb = "introduction")
    private String introduction;
    @Property(nameInDb = "doctor_photo")
    private String doctorPhoto;
    @Property(nameInDb = "login_id")
    private String loginId;
    @Property(nameInDb = "login_pwd")
    private String loginPwd;
    @Generated(hash = 474076781)
    public Doctor(String doctorId, String hospitalId, String hospitalName,
            Long typeId, String typeName, String doctorName, Integer sex,
            String doctorTitle, String skill, String introduction,
            String doctorPhoto, String loginId, String loginPwd) {
        this.doctorId = doctorId;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.typeId = typeId;
        this.typeName = typeName;
        this.doctorName = doctorName;
        this.sex = sex;
        this.doctorTitle = doctorTitle;
        this.skill = skill;
        this.introduction = introduction;
        this.doctorPhoto = doctorPhoto;
        this.loginId = loginId;
        this.loginPwd = loginPwd;
    }
    @Generated(hash = 73626718)
    public Doctor() {
    }
    public String getDoctorId() {
        return this.doctorId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public String getHospitalId() {
        return this.hospitalId;
    }
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    public String getHospitalName() {
        return this.hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public Long getTypeId() {
        return this.typeId;
    }
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getDoctorName() {
        return this.doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public Integer getSex() {
        return this.sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getDoctorTitle() {
        return this.doctorTitle;
    }
    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }
    public String getSkill() {
        return this.skill;
    }
    public void setSkill(String skill) {
        this.skill = skill;
    }
    public String getIntroduction() {
        return this.introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getDoctorPhoto() {
        return this.doctorPhoto;
    }
    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }
    public String getLoginId() {
        return this.loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getLoginPwd() {
        return this.loginPwd;
    }
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
