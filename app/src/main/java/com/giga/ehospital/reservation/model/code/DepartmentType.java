package com.giga.ehospital.reservation.model.code;

import com.contrarywind.interfaces.IPickerViewData;
import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

//@Entity(nameInDb = "department_code")
@Entity
@Data
public class DepartmentType extends Page implements IPickerViewData, Serializable {

    private static final long serialVersionUID = 6977402643899876678L;

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

    @Override
    public String getPickerViewText() {
        return departmentTypeName;
    }
}
