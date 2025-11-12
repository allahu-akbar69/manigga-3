package com.example.nigaa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nigaa.dao.CinemaDao;
import com.example.nigaa.dao.GenreDao;
import com.example.nigaa.dao.MovieDao;
import com.example.nigaa.entity.Cinema;
import com.example.nigaa.entity.Genre;
import com.example.nigaa.entity.Movie;

@Database(entities = {Genre.class, Cinema.class, Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    
    public abstract GenreDao genreDao();
    public abstract CinemaDao cinemaDao();
    public abstract MovieDao movieDao();
    
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "movie_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

