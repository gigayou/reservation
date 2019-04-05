package com.giga.ehospital.reservation.model.system;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Menu {
    private Long menuId;

    private String menuName;

    private Long parentMenuId;

    private String url;

    private String menuIcon;

    private Integer menuIndex;

    private Boolean isLeaf;

    private Integer menuLevel;

    private List children = new ArrayList<>();
}
