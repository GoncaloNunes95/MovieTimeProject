package com.example.movietime.moviedetails.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.movietime.R;
import com.example.movietime.autentication.LoginActivity;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.database.DBHelper;
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
            logout();
        }

        filme = (Filme) getIntent().getSerializableExtra("MOVIE_DETAILS");
    }

    private void logout() {

        session.setLoggedin(false);
        SharedPreferences prefs = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        finish();
        startActivity(new Intent(this, LoginActivity.class));

    }
}