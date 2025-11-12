package com.example.nigaa.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.nigaa.entity.Cinema;

import java.util.List;

@Dao
public interface CinemaDao {
    @Query("SELECT * FROM cinemas")
    List<Cinema> getAll();
    
    @Query("SELECT * FROM cinemas WHERE maRap = :id")
    Cinema getById(int id);
    
    @Insert
    void insert(Cinema cinema);
    
    @Insert
    void insertAll(List<Cinema> cinemas);
    
    @Update
    void update(Cinema cinema);
    
    @Delete
    void delete(Cinema cinema);
}

