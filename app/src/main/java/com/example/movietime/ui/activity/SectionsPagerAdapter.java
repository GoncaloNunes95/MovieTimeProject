package com.example.movietime.ui.activity;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.movietime.R;
import com.example.movietime.data.Filme;
import com.example.movietime.moviedetails.fragments.MovieFragment;
import com.example.movietime.moviedetails.fragments.ReviewsFragment;
import com.example.movietime.moviedetails.fragments.TrailersFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Filme filme;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Filme filme) {
        super(fm);
        mContext = context;
        this.filme = filme;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = MovieFragment.newInstance(filme);
                break;
            case 1:
                fragment = TrailersFragment.newInstance(filme);
                break;
            case 2:
                fragment = ReviewsFragment.newInstance(filme);
                break;
        }
        return fragment;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}