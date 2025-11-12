package com.example.nigaa.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nigaa.fragment.GenreListFragment;
import com.example.nigaa.fragment.MovieListFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new GenreListFragment();
        } else {
            return new MovieListFragment();
        }
    }
    
    @Override
    public int getItemCount() {
        return 2;
    }
}

