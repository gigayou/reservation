package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Hospital extends Page {
    private String hospitalId;

    private String hospitalManager;

    private String managerName;

    private String hospitalName;

    private String hospitalGrade;

    private String province;

    private String provinceName;

    private String city;

    private String cityName;

    private String county;

    private String countyName;

    private String detailAddr;

    private String hospitalPhone;

    private String introduction;

    private String isValid;

    private String hospitalPicture;

}
