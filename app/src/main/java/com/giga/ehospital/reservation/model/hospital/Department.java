package com.giga.ehospital.reservation.model.hospital;

import com.contrarywind.interfaces.IPickerViewData;
import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

//@Entity(nameInDb = "hospital_department")
@Entity
@Data
public class Department extends Page implements Serializable, IPickerViewData {

    private static final long serialVersionUID = 6977402643848876543L;

    @Id
    @Property(nameInDb = "department_id")
    private String departmentId;
    @Property(nameInDb = "hospital_id")
    private String hospitalId;
    @Property(nameInDb = "type_id")
    private Long typeId;
    @Property(nameInDb = "type_name")
    private String typeName;
    @Property(nameInDb = "department_name")
    private String departmentName;
    @Generated(hash = 1227665085)
    public Department(String departmentId, String hospitalId, Long typeId,
            String typeName, String departmentName) {
        this.departmentId = departmentId;
        this.hospitalId = hospitalId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.departmentName = departmentName;
    }
    @Generated(hash = 355406289)
    public Department() {
    }
    public String getDepartmentId() {
        return this.departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getHospitalId() {
        return this.hospitalId;
    }
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String getPickerViewText() {
        return departmentName;
    }
}
