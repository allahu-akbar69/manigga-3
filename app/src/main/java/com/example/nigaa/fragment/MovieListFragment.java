package com.example.nigaa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nigaa.R;
import com.example.nigaa.adapter.MovieAdapter;
import com.example.nigaa.database.AppDatabase;
import com.example.nigaa.entity.Movie;

import java.util.Calendar;
import java.util.List;

public class MovieListFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private AppDatabase database;
    private EditText etFromYear, etToYear;
    private Button btnFilter;
    private TextView tvStatistics;
    private LinearLayout filterLayout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getInstance(requireContext());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        filterLayout = view.findViewById(R.id.filterLayout);
        etFromYear = view.findViewById(R.id.etFromYear);
        etToYear = view.findViewById(R.id.etToYear);
        btnFilter = view.findViewById(R.id.btnFilter);
        tvStatistics = view.findViewById(R.id.tvStatistics);
        
        btnFilter.setOnClickListener(v -> filterMovies());
        
        loadMovies();
        updateStatistics();
        
        return view;
    }
    
    private void filterMovies() {
        String fromYearStr = etFromYear.getText().toString().trim();
        String toYearStr = etToYear.getText().toString().trim();
        
        List<Movie> movies;
        if (fromYearStr.isEmpty() && toYearStr.isEmpty()) {
            movies = database.movieDao().getAll();
        } else {
            Calendar cal = Calendar.getInstance();
            long fromYear = 0;
            long toYear = Long.MAX_VALUE;
            
            try {
                if (!fromYearStr.isEmpty()) {
                    int year = Integer.parseInt(fromYearStr);
                    if (year < 1900 || year > 2100) {
                        etFromYear.setError("Năm không hợp lệ (1900-2100)");
                        return;
                    }
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, 0);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    fromYear = cal.getTimeInMillis();
                }
                
                if (!toYearStr.isEmpty()) {
                    int year = Integer.parseInt(toYearStr);
                    if (year < 1900 || year > 2100) {
                        etToYear.setError("Năm không hợp lệ (1900-2100)");
                        return;
                    }
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, 11);
                    cal.set(Calendar.DAY_OF_MONTH, 31);
                    cal.set(Calendar.HOUR_OF_DAY, 23);
                    cal.set(Calendar.MINUTE, 59);
                    cal.set(Calendar.SECOND, 59);
                    cal.set(Calendar.MILLISECOND, 999);
                    toYear = cal.getTimeInMillis();
                }
                
                if (fromYear > toYear) {
                    etToYear.setError("Năm kết thúc phải lớn hơn năm bắt đầu");
                    return;
                }
                
                movies = database.movieDao().filterByYear(fromYear, toYear);
            } catch (NumberFormatException e) {
                if (!fromYearStr.isEmpty()) {
                    etFromYear.setError("Năm không hợp lệ");
                }
                if (!toYearStr.isEmpty()) {
                    etToYear.setError("Năm không hợp lệ");
                }
                return;
            }
        }
        
        adapter.updateList(movies);
    }
    
    private void loadMovies() {
        List<Movie> movies = database.movieDao().getAll();
        adapter = new MovieAdapter(movies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onEditClick(Movie movie) {
                if (getActivity() instanceof OnMovieActionListener) {
                    ((OnMovieActionListener) getActivity()).onEditMovie(movie);
                }
            }
            
            @Override
            public void onDeleteClick(Movie movie) {
                new android.app.AlertDialog.Builder(requireContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa phim \"" + movie.getTenPhim() + "\"?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            database.movieDao().delete(movie);
                            loadMovies();
                            updateStatistics();
                            android.widget.Toast.makeText(requireContext(), "Đã xóa phim thành công", android.widget.Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        }, database);
        recyclerView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when fragment becomes visible
        refreshData();
    }
    
    private void updateStatistics() {
        int actionCount = database.movieDao().countActionMovies();
        int romanceCount = database.movieDao().countRomanceMovies();
        int comedyCount = database.movieDao().countComedyMovies();
        
        String stats = String.format("Thống kê:\n" +
                "Hành động: %d phim\n" +
                "Tình cảm: %d phim\n" +
                "Hài hước: %d phim",
                actionCount, romanceCount, comedyCount);
        tvStatistics.setText(stats);
    }
    
    public void refreshData() {
        loadMovies();
        updateStatistics();
    }
    
    public interface OnMovieActionListener {
        void onEditMovie(Movie movie);
    }
}

