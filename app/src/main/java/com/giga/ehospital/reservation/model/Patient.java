package com.giga.ehospital.reservation.model;

import com.giga.ehospital.reservation.model.base.Page;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

//@Entity(nameInDb = "patient_info")
@Entity
@Data
public class Patient extends Page implements Serializable {

    private static final long serialVersionUID = 6977402643877548876L;

    @Id
    @Property(nameInDb = "patient_id")
    private String patientId;
    @Property(nameInDb = "patient_name")
    private String patientName;
    @Property(nameInDb = "user_id")
    private String userId;
    @Property(nameInDb = "card_type")
    private String cardType;
    @Property(nameInDb = "id_card")
    private String idCard;
    @Property(nameInDb = "relation")
    private String relation;
    @Property(nameInDb = "gmt_create")
    private String gmtCreate;
    @Property(nameInDb = "gmt_modified")
    private String gmtModified;
    @Generated(hash = 545797549)
    public Patient(String patientId, String patientName, String userId,
            String cardType, String idCard, String relation, String gmtCreate,
            String gmtModified) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.userId = userId;
        this.cardType = cardType;
        this.idCard = idCard;
        this.relation = relation;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }
    @Generated(hash = 1655646460)
    public Patient() {
    }
    public String getPatientId() {
        return this.patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public String getPatientName() {
        return this.patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCardType() {
        return this.cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getIdCard() {
        return this.idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getRelation() {
        return this.relation;
    }
    public void setRelation(String relation) {
        this.relation = relation;
    }
    public String getGmtCreate() {
        return this.gmtCreate;
    }
    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public String getGmtModified() {
        return this.gmtModified;
    }
    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
