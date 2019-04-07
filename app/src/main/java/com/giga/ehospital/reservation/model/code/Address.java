package com.giga.ehospital.reservation.model.code;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

import org.greenrobot.greendao.annotation.Generated;

//@Entity(nameInDb = "addr_code")
@Entity
@Data
public class Address extends Page{

    @Id
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "address_id")
    private String addressId;
    @Property(nameInDb = "pre_id")
    private String preId;
    @Property(nameInDb = "address_name")
    private String addressName;
    @Property(nameInDb = "level")
    private String level;
    @Generated(hash = 1086993558)
    public Address(Long id, String addressId, String preId, String addressName,
            String level) {
        this.id = id;
        this.addressId = addressId;
        this.preId = preId;
        this.addressName = addressName;
        this.level = level;
    }
    @Generated(hash = 388317431)
    public Address() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddressId() {
        return this.addressId;
    }
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
    public String getPreId() {
        return this.preId;
    }
    public void setPreId(String preId) {
        this.preId = preId;
    }
    public String getAddressName() {
        return this.addressName;
    }
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    public String getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

//    private List children = new ArrayList<>();
}
