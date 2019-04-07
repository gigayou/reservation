package com.giga.ehospital.reservation.model.reservation;

import com.giga.ehospital.reservation.model.base.Page;
import com.giga.ehospital.reservation.model.hospital.Calendar;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.giga.ehospital.reservation.model.system.DaoSession;
import com.giga.ehospital.reservation.model.hospital.CalendarDao;

//@Entity(nameInDb = "reservation_info")
@Entity
@Data
public class Reservation extends Page {

    @Property(nameInDb = "reservation_id")
    private String reservationId;
    @Property(nameInDb = "user_phone")
    private String userPhone;
    @Property(nameInDb = "patient_id")
    private String patientId;
    @Property(nameInDb = "patient_name")
    private String patientName;
    @Property(nameInDb = "admission_id")
    private String admissionId;
    @Property(nameInDb = "is_admission")
    private Integer isAdmission;
    @ToOne(joinProperty = "admissionId")
    private Calendar calendar;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1812844338)
    private transient ReservationDao myDao;
    @Generated(hash = 1174965192)
    public Reservation(String reservationId, String userPhone, String patientId,
            String patientName, String admissionId, Integer isAdmission) {
        this.reservationId = reservationId;
        this.userPhone = userPhone;
        this.patientId = patientId;
        this.patientName = patientName;
        this.admissionId = admissionId;
        this.isAdmission = isAdmission;
    }
    @Generated(hash = 283305752)
    public Reservation() {
    }
    public String getReservationId() {
        return this.reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public String getUserPhone() {
        return this.userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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
    public String getAdmissionId() {
        return this.admissionId;
    }
    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }
    public Integer getIsAdmission() {
        return this.isAdmission;
    }
    public void setIsAdmission(Integer isAdmission) {
        this.isAdmission = isAdmission;
    }
    @Generated(hash = 477516542)
    private transient String calendar__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1760303808)
    public Calendar getCalendar() {
        String __key = this.admissionId;
        if (calendar__resolvedKey == null || calendar__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CalendarDao targetDao = daoSession.getCalendarDao();
            Calendar calendarNew = targetDao.load(__key);
            synchronized (this) {
                calendar = calendarNew;
                calendar__resolvedKey = __key;
            }
        }
        return calendar;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1783110430)
    public void setCalendar(Calendar calendar) {
        synchronized (this) {
            this.calendar = calendar;
            admissionId = calendar == null ? null : calendar.getAdmissionId();
            calendar__resolvedKey = admissionId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 530608998)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getReservationDao() : null;
    }
}
