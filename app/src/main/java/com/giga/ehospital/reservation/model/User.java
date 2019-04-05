package com.giga.ehospital.reservation.model;


import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Page {
    private String userPhone;

    private String userName;

    private String sex;

    private Integer province;

    private String provinceName;

    private Integer city;

    private String cityName;

    private Integer county;

    private String countyName;

    private String detailAddr;

    private String userPwd;

    private String gmtCreate;

    private String gmtModified;
}
