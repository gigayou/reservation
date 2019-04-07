package com.giga.ehospital.reservation.model.system;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

@Entity
@Data
public class BackupDBFile {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "backup_date")
    private String date;

    private String filename;

    @Generated(hash = 852513391)
    public BackupDBFile(Long id, String date, String filename) {
        this.id = id;
        this.date = date;
        this.filename = filename;
    }

    @Generated(hash = 76522785)
    public BackupDBFile() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
