package com.giga.ehospital.reservation.model.code;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentType extends Page {
    private Long departmentTypeId;

    private String departmentTypeName;

    private String remark;
}
