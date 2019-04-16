package com.giga.ehospital.reservation.model.system;

import com.contrarywind.interfaces.IPickerViewData;
import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

//@Entity(nameInDb = "buser_info")
@Entity
@Data
public class Buser extends Page implements IPickerViewData, Serializable {

    private static final long serialVersionUID = 6977402643848372812L;

    @Id
    @Property(nameInDb = "login_id")
    private String loginId;
    @Property(nameInDb = "user_name")
    private String userName;
    @Property(nameInDb = "role_id")
    private Long roleId;
    @Property(nameInDb = "role_name")
    private String roleName;
    @Property(nameInDb = "login_pwd")
    private String loginPwd;
    @Generated(hash = 1624334554)
    public Buser(String loginId, String userName, Long roleId, String roleName,
            String loginPwd) {
        this.loginId = loginId;
        this.userName = userName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.loginPwd = loginPwd;
    }
    @Generated(hash = 792077950)
    public Buser() {
    }
    public String getLoginId() {
        return this.loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
    public String getLoginPwd() {
        return this.loginPwd;
    }
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    @Override
    public String getPickerViewText() {
        return userName;
    }
}
