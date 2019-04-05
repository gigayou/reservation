package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department extends Page {
    private String departmentId;

    private String hospitalId;

    private Long typeId;

    private String typeName;

    private String departmentName;
}
