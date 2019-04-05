package com.giga.ehospital.reservation.model.reservation;

import com.giga.ehospital.reservation.model.base.Page;
import com.giga.ehospital.reservation.model.hospital.Calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation extends Page {
    private String reservationId;

    private String userPhone;

    private String patientId;

    private String patientName;

    private String admissionId;

    private Integer isAdmission;

    private Calendar calendar;
}
