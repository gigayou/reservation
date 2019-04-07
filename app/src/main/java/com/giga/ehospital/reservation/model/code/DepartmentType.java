package com.giga.ehospital.reservation.model.code;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

//@Entity(nameInDb = "department_code")
@Entity
@Data
public class DepartmentType extends Page {
    @Id
    @Property(nameInDb = "department_type_id")
    private Long departmentTypeId;
    @Property(nameInDb = "department_type_name")
    private String departmentTypeName;
    @Property(nameInDb = "remark")
    private String remark;
    @Generated(hash = 1995406530)
    public DepartmentType(Long departmentTypeId, String departmentTypeName,
            String remark) {
        this.departmentTypeId = departmentTypeId;
        this.departmentTypeName = departmentTypeName;
        this.remark = remark;
    }
    @Generated(hash = 1504161107)
    public DepartmentType() {
    }
    public Long getDepartmentTypeId() {
        return this.departmentTypeId;
    }
    public void setDepartmentTypeId(Long departmentTypeId) {
        this.departmentTypeId = departmentTypeId;
    }
    public String getDepartmentTypeName() {
        return this.departmentTypeName;
    }
    public void setDepartmentTypeName(String departmentTypeName) {
        this.departmentTypeName = departmentTypeName;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
