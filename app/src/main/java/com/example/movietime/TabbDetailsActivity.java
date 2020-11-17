package com.example.movietime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.movietime.data.Filme;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.movietime.ui.main.SectionsPagerAdapter;

public class TabbDetailsActivity extends AppCompatActivity {

    private Filme filme;
    private String email, password, user;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_Back:
                startActivity(new Intent(TabbDetailsActivity.this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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