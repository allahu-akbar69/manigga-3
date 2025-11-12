package com.example.nigaa.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.nigaa.entity.Genre;

import java.util.List;

@Dao
public interface GenreDao {
    @Query("SELECT * FROM genres")
    List<Genre> getAll();
    
    @Query("SELECT * FROM genres WHERE maTheLoai = :id")
    Genre getById(int id);
    
    @Query("SELECT * FROM genres WHERE tenTheLoai LIKE '%' || :search || '%'")
    List<Genre> searchByName(String search);
    
    @Query("SELECT * FROM genres WHERE " +
           "(:hanhDong = 0 OR hanhDong = 1) AND " +
           "(:tinhCam = 0 OR tinhCam = 1) AND " +
           "(:haiHuoc = 0 OR haiHuoc = 1)")
    List<Genre> searchByCategory(boolean hanhDong, boolean tinhCam, boolean haiHuoc);
    
    @Query("SELECT * FROM genres WHERE " +
           "tenTheLoai LIKE '%' || :search || '%' AND " +
           "(:hanhDong = 0 OR hanhDong = 1) AND " +
           "(:tinhCam = 0 OR tinhCam = 1) AND " +
           "(:haiHuoc = 0 OR haiHuoc = 1)")
    List<Genre> searchByNameAndCategory(String search, boolean hanhDong, boolean tinhCam, boolean haiHuoc);
    
    @Insert
    void insert(Genre genre);
    
    @Insert
    void insertAll(List<Genre> genres);
    
    @Update
    void update(Genre genre);
    
    @Delete
    void delete(Genre genre);
}

