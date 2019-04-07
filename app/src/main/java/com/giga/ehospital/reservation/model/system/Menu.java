package com.giga.ehospital.reservation.model.system;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

import org.greenrobot.greendao.annotation.Generated;

//@Entity(nameInDb = "menu")
@Entity
@Data
public class Menu {

    @Property(nameInDb = "menu_id")
    private Long menuId;
    @Property(nameInDb = "menu_name")
    private String menuName;
    @Property(nameInDb = "parent_menu_id")
    private Long parentMenuId;
    @Property(nameInDb = "url")
    private String url;
    @Property(nameInDb = "menu_icon")
    private String menuIcon;
    @Property(nameInDb = "menu_index")
    private Integer menuIndex;
    @Property(nameInDb = "is_leaf")
    private Boolean isLeaf;
    @Property(nameInDb = "menu_level")
    private Integer menuLevel;
    @Generated(hash = 488875927)
    public Menu(Long menuId, String menuName, Long parentMenuId, String url,
            String menuIcon, Integer menuIndex, Boolean isLeaf, Integer menuLevel) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.parentMenuId = parentMenuId;
        this.url = url;
        this.menuIcon = menuIcon;
        this.menuIndex = menuIndex;
        this.isLeaf = isLeaf;
        this.menuLevel = menuLevel;
    }
    @Generated(hash = 1631206187)
    public Menu() {
    }
    public Long getMenuId() {
        return this.menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public String getMenuName() {
        return this.menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public Long getParentMenuId() {
        return this.parentMenuId;
    }
    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMenuIcon() {
        return this.menuIcon;
    }
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
    public Integer getMenuIndex() {
        return this.menuIndex;
    }
    public void setMenuIndex(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }
    public Boolean getIsLeaf() {
        return this.isLeaf;
    }
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    public Integer getMenuLevel() {
        return this.menuLevel;
    }
    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

//    private List children = new ArrayList<>();
}
