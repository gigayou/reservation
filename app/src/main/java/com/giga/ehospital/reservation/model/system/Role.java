package com.giga.ehospital.reservation.model.system;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

//@Entity(nameInDb = "role")
@Entity
@Data
public class Role extends Page {
    @Id
    @Property(nameInDb = "role_id")
    private Long roleId;
    @Property(nameInDb = "role_name")
    private String roleName;
    @Generated(hash = 1026819107)
    public Role(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    @Generated(hash = 844947497)
    public Role() {
    }
    public Long getRoleId() {
        return this.roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return this.roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
