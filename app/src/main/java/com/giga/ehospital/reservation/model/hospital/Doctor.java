package com.giga.ehospital.reservation.model.hospital;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doctor extends Page {
    private String doctorId;

    private String hospitalId;

    private String hospitalName;

    private Long typeId;

    private String typeName;

    private String doctorName;

    private String sex;

    private String doctorTitle;

    private String skill;

    private String introduction;

    private String doctorPhoto;

    private String loginId;

    private String loginPwd;
}
