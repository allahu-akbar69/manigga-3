package com.example.nigaa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nigaa.R;
import com.example.nigaa.adapter.GenreAdapter;
import com.example.nigaa.database.AppDatabase;
import com.example.nigaa.entity.Genre;

import java.util.List;

public class GenreListFragment extends Fragment {
    private RecyclerView recyclerView;
    private GenreAdapter adapter;
    private AppDatabase database;
    private SearchView searchView;
    private CheckBox cbHanhDong, cbTinhCam, cbHaiHuoc;
    private LinearLayout filterLayout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        database = AppDatabase.getInstance(requireContext());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerViewGenres);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        filterLayout = view.findViewById(R.id.filterLayout);
        cbHanhDong = view.findViewById(R.id.cbHanhDong);
        cbTinhCam = view.findViewById(R.id.cbTinhCam);
        cbHaiHuoc = view.findViewById(R.id.cbHaiHuoc);
        
        cbHanhDong.setOnCheckedChangeListener((buttonView, isChecked) -> performSearch());
        cbTinhCam.setOnCheckedChangeListener((buttonView, isChecked) -> performSearch());
        cbHaiHuoc.setOnCheckedChangeListener((buttonView, isChecked) -> performSearch());
        
        loadGenres();
        
        return view;
    }
    
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_genre_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch();
                return false;
            }
            
            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch();
                return false;
            }
        });
        
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    private void performSearch() {
        String query = searchView != null ? searchView.getQuery().toString() : "";
        boolean hanhDong = cbHanhDong.isChecked();
        boolean tinhCam = cbTinhCam.isChecked();
        boolean haiHuoc = cbHaiHuoc.isChecked();
        
        List<Genre> results;
        if (query.isEmpty() && !hanhDong && !tinhCam && !haiHuoc) {
            results = database.genreDao().getAll();
        } else if (query.isEmpty()) {
            results = database.genreDao().searchByCategory(hanhDong, tinhCam, haiHuoc);
        } else {
            results = database.genreDao().searchByNameAndCategory(query, hanhDong, tinhCam, haiHuoc);
        }
        
        adapter.updateList(results);
    }
    
    private void loadGenres() {
        List<Genre> genres = database.genreDao().getAll();
        adapter = new GenreAdapter(genres);
        recyclerView.setAdapter(adapter);
    }
}

