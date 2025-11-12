package com.example.nigaa.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cinemas")
public class Cinema {
    @PrimaryKey(autoGenerate = true)
    private int maRap;
    
    private String tenRap;
    private String emailLienHe;
    private String diaChi;
    
    public Cinema() {
    }
    
    public Cinema(String tenRap, String emailLienHe, String diaChi) {
        this.tenRap = tenRap;
        this.emailLienHe = emailLienHe;
        this.diaChi = diaChi;
    }
    
    public int getMaRap() {
        return maRap;
    }
    
    public void setMaRap(int maRap) {
        this.maRap = maRap;
    }
    
    public String getTenRap() {
        return tenRap;
    }
    
    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }
    
    public String getEmailLienHe() {
        return emailLienHe;
    }
    
    public void setEmailLienHe(String emailLienHe) {
        this.emailLienHe = emailLienHe;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    @Override
    public String toString() {
        return tenRap;
    }
}

