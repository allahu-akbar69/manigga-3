package com.example.nigaa.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "movies",
        foreignKeys = {
            @ForeignKey(entity = Genre.class,
                    parentColumns = "maTheLoai",
                    childColumns = "maTheLoai",
                    onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Cinema.class,
                    parentColumns = "maRap",
                    childColumns = "maRap",
                    onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("maTheLoai"), @Index("maRap")})
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int maPhim;
    
    private int maTheLoai;
    private int maRap;
    private String tenPhim;
    private long ngayKhoiChieu; // Store as timestamp
    private double giaVe;
    
    public Movie() {
    }
    
    public Movie(int maTheLoai, int maRap, String tenPhim, long ngayKhoiChieu, double giaVe) {
        this.maTheLoai = maTheLoai;
        this.maRap = maRap;
        this.tenPhim = tenPhim;
        this.ngayKhoiChieu = ngayKhoiChieu;
        this.giaVe = giaVe;
    }
    
    public int getMaPhim() {
        return maPhim;
    }
    
    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }
    
    public int getMaTheLoai() {
        return maTheLoai;
    }
    
    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }
    
    public int getMaRap() {
        return maRap;
    }
    
    public void setMaRap(int maRap) {
        this.maRap = maRap;
    }
    
    public String getTenPhim() {
        return tenPhim;
    }
    
    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }
    
    public long getNgayKhoiChieu() {
        return ngayKhoiChieu;
    }
    
    public void setNgayKhoiChieu(long ngayKhoiChieu) {
        this.ngayKhoiChieu = ngayKhoiChieu;
    }
    
    public double getGiaVe() {
        return giaVe;
    }
    
    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }
}

