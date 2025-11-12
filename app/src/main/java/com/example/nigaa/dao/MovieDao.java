package com.example.nigaa.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.nigaa.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY ngayKhoiChieu DESC, giaVe DESC")
    List<Movie> getAll();
    
    @Query("SELECT * FROM movies WHERE maPhim = :id")
    Movie getById(int id);
    
    @Query("SELECT * FROM movies WHERE " +
           "ngayKhoiChieu >= :fromYear AND ngayKhoiChieu <= :toYear " +
           "ORDER BY ngayKhoiChieu DESC, giaVe DESC")
    List<Movie> filterByYear(long fromYear, long toYear);
    
    @Query("SELECT COUNT(*) FROM movies m " +
           "INNER JOIN genres g ON m.maTheLoai = g.maTheLoai " +
           "WHERE g.hanhDong = 1")
    int countActionMovies();
    
    @Query("SELECT COUNT(*) FROM movies m " +
           "INNER JOIN genres g ON m.maTheLoai = g.maTheLoai " +
           "WHERE g.tinhCam = 1")
    int countRomanceMovies();
    
    @Query("SELECT COUNT(*) FROM movies m " +
           "INNER JOIN genres g ON m.maTheLoai = g.maTheLoai " +
           "WHERE g.haiHuoc = 1")
    int countComedyMovies();
    
    @Insert
    void insert(Movie movie);
    
    @Update
    void update(Movie movie);
    
    @Delete
    void delete(Movie movie);
}

