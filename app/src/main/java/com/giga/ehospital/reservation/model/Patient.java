package com.giga.ehospital.reservation.model;

import android.support.annotation.Size;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient extends Page {
    private String patientId;

    private String patientName;

    private String userId;

    private String cardType;

    private String idCard;

    private String relation;

    private String gmtCreate;

    private String gmtModified;
}
