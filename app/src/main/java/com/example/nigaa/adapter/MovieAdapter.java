package com.example.nigaa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nigaa.R;
import com.example.nigaa.database.AppDatabase;
import com.example.nigaa.entity.Cinema;
import com.example.nigaa.entity.Genre;
import com.example.nigaa.entity.Movie;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private OnMovieClickListener listener;
    private AppDatabase database;
    
    public interface OnMovieClickListener {
        void onEditClick(Movie movie);
        void onDeleteClick(Movie movie);
    }
    
    public MovieAdapter(List<Movie> movieList, OnMovieClickListener listener, AppDatabase database) {
        this.movieList = movieList;
        this.listener = listener;
        this.database = database;
    }
    
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvMovieName.setText(movie.getTenPhim());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(movie.getNgayKhoiChieu());
        holder.tvReleaseDate.setText("Ngày khởi chiếu: " + sdf.format(date));
        
        holder.tvTicketPrice.setText(String.format("Giá vé: %.0f VNĐ", movie.getGiaVe()));
        
        // Load genre and cinema info
        if (database != null) {
            Genre genre = database.genreDao().getById(movie.getMaTheLoai());
            Cinema cinema = database.cinemaDao().getById(movie.getMaRap());
            
            if (genre != null) {
                holder.tvGenre.setText("Thể loại: " + genre.getTenTheLoai());
            }
            if (cinema != null) {
                holder.tvCinema.setText("Rạp: " + cinema.getTenRap());
            }
        }
        
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(movie);
            }
        });
        
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(movie);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }
    
    public void updateList(List<Movie> newList) {
        this.movieList = newList;
        notifyDataSetChanged();
    }
    
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieName;
        TextView tvReleaseDate;
        TextView tvTicketPrice;
        TextView tvGenre;
        TextView tvCinema;
        ImageButton btnEdit;
        ImageButton btnDelete;
        
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            tvTicketPrice = itemView.findViewById(R.id.tvTicketPrice);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvCinema = itemView.findViewById(R.id.tvCinema);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

