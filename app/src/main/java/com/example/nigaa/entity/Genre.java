package com.example.nigaa.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genres")
public class Genre {
    @PrimaryKey(autoGenerate = true)
    private int maTheLoai;
    
    private String tenTheLoai;
    private boolean hanhDong;
    private boolean tinhCam;
    private boolean haiHuoc;
    
    public Genre() {
    }
    
    public Genre(String tenTheLoai, boolean hanhDong, boolean tinhCam, boolean haiHuoc) {
        this.tenTheLoai = tenTheLoai;
        this.hanhDong = hanhDong;
        this.tinhCam = tinhCam;
        this.haiHuoc = haiHuoc;
    }
    
    public int getMaTheLoai() {
        return maTheLoai;
    }
    
    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }
    
    public String getTenTheLoai() {
        return tenTheLoai;
    }
    
    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
    
    public boolean isHanhDong() {
        return hanhDong;
    }
    
    public void setHanhDong(boolean hanhDong) {
        this.hanhDong = hanhDong;
    }
    
    public boolean isTinhCam() {
        return tinhCam;
    }
    
    public void setTinhCam(boolean tinhCam) {
        this.tinhCam = tinhCam;
    }
    
    public boolean isHaiHuoc() {
        return haiHuoc;
    }
    
    public void setHaiHuoc(boolean haiHuoc) {
        this.haiHuoc = haiHuoc;
    }
    
    public String getCategoriesString() {
        StringBuilder sb = new StringBuilder();
        if (hanhDong) sb.append("Hành động, ");
        if (tinhCam) sb.append("Tình cảm, ");
        if (haiHuoc) sb.append("Hài hước, ");
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return tenTheLoai;
    }
}

