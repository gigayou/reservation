package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Calendar extends Page {
    private String admissionId;
    
    private String hospitalId;

    private String hospitalName;

    private String departmentId;

    private String departmentName;

    private String doctorId;

    private String doctorName;

    private String admissionDate;

    private String admissionPeriod;

    private Integer admissionNum;

    private Integer remainingNum;

    private Integer isValid;
}
