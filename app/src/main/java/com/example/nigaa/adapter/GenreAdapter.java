package com.example.nigaa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nigaa.R;
import com.example.nigaa.entity.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private List<Genre> genreList;
    
    public GenreAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }
    
    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.tvGenreName.setText(genre.getTenTheLoai());
        holder.tvCategories.setText("Danh mục: " + genre.getCategoriesString());
    }
    
    @Override
    public int getItemCount() {
        return genreList != null ? genreList.size() : 0;
    }
    
    public void updateList(List<Genre> newList) {
        this.genreList = newList;
        notifyDataSetChanged();
    }
    
    static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenreName;
        TextView tvCategories;
        
        GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenreName = itemView.findViewById(R.id.tvGenreName);
            tvCategories = itemView.findViewById(R.id.tvCategories);
        }
    }
}

