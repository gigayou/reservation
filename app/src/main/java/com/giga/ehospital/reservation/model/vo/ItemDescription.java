package com.giga.ehospital.reservation.model.vo;

import com.giga.ehospital.reservation.R;

public class ItemDescription {
    private String name;

    private int iconRes;

    public ItemDescription() {
    }

    public ItemDescription(String name) {
        this(name, R.mipmap.icon_grid_popup);
    }

    public ItemDescription(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
