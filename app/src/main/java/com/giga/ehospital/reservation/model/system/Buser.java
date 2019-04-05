package com.giga.ehospital.reservation.model.system;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Buser extends Page {
    private String loginId;

    private String userName;

    private Long roleId;

    private String roleName;

    private String loginPwd;
}
