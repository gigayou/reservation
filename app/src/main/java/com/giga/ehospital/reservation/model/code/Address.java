package com.giga.ehospital.reservation.model.code;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Address extends Page {
    private Long id;

    private String addressId;

    private String preId;

    private String addressName;

    private String level;

    private List children = new ArrayList<>();
}
