package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

//@Entity(nameInDb = "admission_calendar")
@Entity
@Data
public class Calendar extends Page {

    @Id
    @Property(nameInDb = "admission_id")
    private String admissionId;
    @Property(nameInDb = "hospital_id")
    private String hospitalId;
    @Property(nameInDb = "hospital_name")
    private String hospitalName;
    @Property(nameInDb = "department_id")
    private String departmentId;
    @Property(nameInDb = "department_name")
    private String departmentName;
    @Property(nameInDb = "doctor_id")
    private String doctorId;
    @Property(nameInDb = "doctor_name")
    private String doctorName;
    @Property(nameInDb = "admission_date")
    private String admissionDate;
    @Property(nameInDb = "admission_period")
    private String admissionPeriod;
    @Property(nameInDb = "admission_num")
    private Integer admissionNum;
    @Property(nameInDb = "remaining_num")
    private Integer remainingNum;
    @Property(nameInDb = "is_valid")
    private Integer isValid;
    @Generated(hash = 1752182838)
    public Calendar(String admissionId, String hospitalId, String hospitalName,
            String departmentId, String departmentName, String doctorId,
            String doctorName, String admissionDate, String admissionPeriod,
            Integer admissionNum, Integer remainingNum, Integer isValid) {
        this.admissionId = admissionId;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.admissionDate = admissionDate;
        this.admissionPeriod = admissionPeriod;
        this.admissionNum = admissionNum;
        this.remainingNum = remainingNum;
        this.isValid = isValid;
    }
    @Generated(hash = 2039519234)
    public Calendar() {
    }
    public String getAdmissionId() {
        return this.admissionId;
    }
    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
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
    public String getDepartmentId() {
        return this.departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getDoctorId() {
        return this.doctorId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public String getDoctorName() {
        return this.doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getAdmissionDate() {
        return this.admissionDate;
    }
    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }
    public String getAdmissionPeriod() {
        return this.admissionPeriod;
    }
    public void setAdmissionPeriod(String admissionPeriod) {
        this.admissionPeriod = admissionPeriod;
    }
    public Integer getAdmissionNum() {
        return this.admissionNum;
    }
    public void setAdmissionNum(Integer admissionNum) {
        this.admissionNum = admissionNum;
    }
    public Integer getRemainingNum() {
        return this.remainingNum;
    }
    public void setRemainingNum(Integer remainingNum) {
        this.remainingNum = remainingNum;
    }
    public Integer getIsValid() {
        return this.isValid;
    }
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
