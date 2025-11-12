package com.example.nigaa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import com.example.nigaa.adapter.ViewPagerAdapter;
import com.example.nigaa.database.AppDatabase;
import com.example.nigaa.entity.Cinema;
import com.example.nigaa.entity.Genre;
import com.example.nigaa.entity.Movie;
import com.example.nigaa.fragment.MovieListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieActionListener {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton fab;
    private AppDatabase database;
    private ViewPagerAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        database = AppDatabase.getInstance(this);
        initializeSampleData();
        
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fab = findViewById(R.id.fab);
        
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Thể loại phim");
                    } else {
                        tab.setText("Danh sách phim");
                    }
                }).attach();
        
        fab.setOnClickListener(v -> showAddMovieDialog(null));
    }
    
    private void initializeSampleData() {
        // Check if data already exists
        if (database.genreDao().getAll().isEmpty()) {
            // Add sample genres
            Genre genre1 = new Genre("Phim Hành Động", true, false, false);
            Genre genre2 = new Genre("Phim Tình Cảm", false, true, false);
            Genre genre3 = new Genre("Phim Hài", false, false, true);
            Genre genre4 = new Genre("Phim Hành Động - Tình Cảm", true, true, false);
            Genre genre5 = new Genre("Phim Hài - Hành Động", true, false, true);
            Genre genre6 = new Genre("Phim Khoa Học Viễn Tưởng", true, false, false);
            Genre genre7 = new Genre("Phim Kinh Dị", false, false, false);
            Genre genre8 = new Genre("Phim Tài Liệu", false, true, false);
            Genre genre9 = new Genre("Phim Hoạt Hình", false, false, true);
            
            database.genreDao().insert(genre1);
            database.genreDao().insert(genre2);
            database.genreDao().insert(genre3);
            database.genreDao().insert(genre4);
            database.genreDao().insert(genre5);
            database.genreDao().insert(genre6);
            database.genreDao().insert(genre7);
            database.genreDao().insert(genre8);
            database.genreDao().insert(genre9);
        }
        
        if (database.cinemaDao().getAll().isEmpty()) {
            // Add sample cinemas
            Cinema cinema1 = new Cinema("CGV Vincom", "cgv@example.com", "123 Nguyễn Huệ, Q1, TP.HCM");
            Cinema cinema2 = new Cinema("Lotte Cinema", "lotte@example.com", "456 Lê Lợi, Q1, TP.HCM");
            Cinema cinema3 = new Cinema("Galaxy Cinema", "galaxy@example.com", "789 Điện Biên Phủ, Q.Bình Thạnh, TP.HCM");
            
            database.cinemaDao().insert(cinema1);
            database.cinemaDao().insert(cinema2);
            database.cinemaDao().insert(cinema3);
        }
    }
    
    private void showAddMovieDialog(Movie movie) {
        // Check if genres and cinemas exist
        List<Genre> genres = database.genreDao().getAll();
        if (genres.isEmpty()) {
            Toast.makeText(this, "Vui lòng thêm thể loại phim trước", Toast.LENGTH_LONG).show();
            return;
        }
        
        List<Cinema> cinemas = database.cinemaDao().getAll();
        if (cinemas.isEmpty()) {
            Toast.makeText(this, "Vui lòng thêm rạp chiếu trước", Toast.LENGTH_LONG).show();
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_movie, null);
        builder.setView(dialogView);
        
        Spinner spinnerGenre = dialogView.findViewById(R.id.spinnerGenre);
        Spinner spinnerCinema = dialogView.findViewById(R.id.spinnerCinema);
        EditText etMovieName = dialogView.findViewById(R.id.etMovieName);
        EditText etTicketPrice = dialogView.findViewById(R.id.etTicketPrice);
        Button btnSelectDate = dialogView.findViewById(R.id.btnSelectDate);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        
        // Load genres
        ArrayAdapter<Genre> genreAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);
        
        // Load cinemas
        ArrayAdapter<Cinema> cinemaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cinemas);
        cinemaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCinema.setAdapter(cinemaAdapter);
        
        final long[] selectedDate = new long[1];
        Calendar calendar = Calendar.getInstance();
        
        if (movie != null) {
            etMovieName.setText(movie.getTenPhim());
            etTicketPrice.setText(String.valueOf((int) movie.getGiaVe()));
            selectedDate[0] = movie.getNgayKhoiChieu();
            calendar.setTimeInMillis(selectedDate[0]);
            
            // Set selected genre and cinema
            for (int i = 0; i < genres.size(); i++) {
                if (genres.get(i).getMaTheLoai() == movie.getMaTheLoai()) {
                    spinnerGenre.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < cinemas.size(); i++) {
                if (cinemas.get(i).getMaRap() == movie.getMaRap()) {
                    spinnerCinema.setSelection(i);
                    break;
                }
            }
            
            builder.setTitle("Sửa phim");
        } else {
            selectedDate[0] = System.currentTimeMillis();
            builder.setTitle("Thêm phim mới");
        }
        
        btnSelectDate.setText(String.format("%02d/%02d/%04d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)));
        
        btnSelectDate.setOnClickListener(v -> {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar selectedCal = Calendar.getInstance();
                        selectedCal.set(year, month, dayOfMonth);
                        selectedCal.set(Calendar.HOUR_OF_DAY, 0);
                        selectedCal.set(Calendar.MINUTE, 0);
                        selectedCal.set(Calendar.SECOND, 0);
                        selectedCal.set(Calendar.MILLISECOND, 0);
                        
                        // Check if selected date is in the past
                        if (selectedCal.before(today)) {
                            Toast.makeText(this, "Ngày khởi chiếu không được trong quá khứ!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        
                        calendar.set(year, month, dayOfMonth);
                        selectedDate[0] = selectedCal.getTimeInMillis();
                        btnSelectDate.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            
            // Set minimum date to today
            datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
            datePickerDialog.show();
        });
        
        AlertDialog dialog = builder.create();
        
        btnSave.setOnClickListener(v -> {
            String movieName = etMovieName.getText().toString().trim();
            String priceStr = etTicketPrice.getText().toString().trim();
            
            if (movieName.isEmpty()) {
                etMovieName.setError("Vui lòng nhập tên phim");
                return;
            }
            
            if (priceStr.isEmpty()) {
                etTicketPrice.setError("Vui lòng nhập giá vé");
                return;
            }
            
            double price;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    etTicketPrice.setError("Giá vé phải lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                etTicketPrice.setError("Giá vé không hợp lệ");
                return;
            }
            
            // Validate date is not in the past
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            
            Calendar selectedDateCal = Calendar.getInstance();
            selectedDateCal.setTimeInMillis(selectedDate[0]);
            selectedDateCal.set(Calendar.HOUR_OF_DAY, 0);
            selectedDateCal.set(Calendar.MINUTE, 0);
            selectedDateCal.set(Calendar.SECOND, 0);
            selectedDateCal.set(Calendar.MILLISECOND, 0);
            
            if (selectedDateCal.before(today)) {
                Toast.makeText(this, "Ngày khởi chiếu không được trong quá khứ!", Toast.LENGTH_SHORT).show();
                return;
            }
            
            Genre selectedGenre = (Genre) spinnerGenre.getSelectedItem();
            Cinema selectedCinema = (Cinema) spinnerCinema.getSelectedItem();
            
            if (movie == null) {
                // Add new movie
                Movie newMovie = new Movie(
                        selectedGenre.getMaTheLoai(),
                        selectedCinema.getMaRap(),
                        movieName,
                        selectedDate[0],
                        price
                );
                database.movieDao().insert(newMovie);
                Toast.makeText(this, "Thêm phim thành công", Toast.LENGTH_SHORT).show();
            } else {
                // Update existing movie
                movie.setTenPhim(movieName);
                movie.setGiaVe(price);
                movie.setNgayKhoiChieu(selectedDate[0]);
                movie.setMaTheLoai(selectedGenre.getMaTheLoai());
                movie.setMaRap(selectedCinema.getMaRap());
                database.movieDao().update(movie);
                Toast.makeText(this, "Cập nhật phim thành công", Toast.LENGTH_SHORT).show();
            }
            
            // Refresh movie list fragment immediately
            refreshMovieListFragment();
            
            dialog.dismiss();
        });
        
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        
        dialog.show();
    }
    
    @Override
    public void onEditMovie(Movie movie) {
        showAddMovieDialog(movie);
    }
    
    private void refreshMovieListFragment() {
        // Method 1: Try to find fragment by tag (most reliable for ViewPager2)
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 1);
        
        if (fragment instanceof MovieListFragment) {
            ((MovieListFragment) fragment).refreshData();
            return;
        }
        
        // Method 2: Search in all fragments (including nested fragments)
        refreshMovieListFragmentRecursive(getSupportFragmentManager().getFragments());
        
        // Method 3: If fragment not created yet, refresh when it's created
        // This ensures refresh happens even if fragment is not loaded
        viewPager.post(() -> {
            Fragment f = getSupportFragmentManager()
                    .findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 1);
            if (f instanceof MovieListFragment) {
                ((MovieListFragment) f).refreshData();
            }
        });
    }
    
    private void refreshMovieListFragmentRecursive(List<Fragment> fragments) {
        if (fragments == null) return;
        
        for (Fragment fragment : fragments) {
            if (fragment instanceof MovieListFragment) {
                ((MovieListFragment) fragment).refreshData();
                return;
            }
            // Check child fragments if any
            if (fragment.getChildFragmentManager() != null) {
                refreshMovieListFragmentRecursive(fragment.getChildFragmentManager().getFragments());
            }
        }
    }
}
