package com.giga.ehospital.reservation.model.system;

import com.giga.ehospital.reservation.model.base.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends Page {
    private Long roleId;
    
    private String roleName;
}
