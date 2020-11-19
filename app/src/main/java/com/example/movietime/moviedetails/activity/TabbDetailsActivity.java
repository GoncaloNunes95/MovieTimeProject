package com.example.movietime.moviedetails.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.movietime.R;
import com.example.movietime.SingletonUser;
import com.example.movietime.autentication.LoginActivity;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.database.DBHelper;
import com.example.movietime.moviedetails.fragments.MovieFragment;
import com.example.movietime.ui.activity.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabbDetailsActivity extends AppCompatActivity {

    private Filme filme;
    private DBHelper db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabb_details);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        db = new DBHelper(this);
        session = new Session(this);

        if (!session.loggedin()) {
            session.setLoggedin(false);
            SingletonUser.singleton().logout();
            finish();
            startActivity(new Intent(TabbDetailsActivity.this, LoginActivity.class));
        }

        filme = (Filme) getIntent().getSerializableExtra("MOVIE_DETAILS");
    }

    public Bundle getData(){
        Bundle bundle = new Bundle();
        bundle.putInt("id", filme.getId());
        bundle.putString("title", filme.getOriginal_title());
        bundle.putString("poster", filme.getPoster_path());
        bundle.putString("date", filme.getRelease_date());
        bundle.putFloat("average", filme.getVote_average());
        bundle.putString("sinopse", filme.getSinopse());
        return bundle;
    }
}