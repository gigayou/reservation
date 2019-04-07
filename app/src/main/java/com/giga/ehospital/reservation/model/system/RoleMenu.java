package com.giga.ehospital.reservation.model.system;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

//@Entity(nameInDb = "role_menu")
@Entity
@Data
public class RoleMenu {
    @Id
    @Property(nameInDb = "role_id")
    private Long role_id;
//    @Id
    @Property(nameInDb = "menu_id")
    private Long menu_id;
    @Generated(hash = 1010816221)
    public RoleMenu(Long role_id, Long menu_id) {
        this.role_id = role_id;
        this.menu_id = menu_id;
    }
    @Generated(hash = 2037816289)
    public RoleMenu() {
    }
    public Long getRole_id() {
        return this.role_id;
    }
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
    public Long getMenu_id() {
        return this.menu_id;
    }
    public void setMenu_id(Long menu_id) {
        this.menu_id = menu_id;
    }
}
