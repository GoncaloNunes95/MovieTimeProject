package com.example.movietime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DBHelper db;
    private String username, email, password, user;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setSubtitle("Movies");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logotipo);

        db = new DBHelper(this);
        session = new Session(this);

        if (!session.loggedin()) {
            logout();
        }

        SharedPreferences prefs = MainActivity.this.getSharedPreferences("User", Context.MODE_PRIVATE);
        String nomeArmazenado = prefs.getString("Username", null);
        String EmailArmazenado = prefs.getString("Email", null);
        String PasswordArmazenado = prefs.getString("Password", null);

        if ((nomeArmazenado != null) && (EmailArmazenado != null) && (PasswordArmazenado != null)) {
            user = prefs.getString("Username", "");
            email = prefs.getString("Email", "");
            password = prefs.getString("Password", "");
        }

        bottomNavigationView = findViewById(R.id.btn_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(nav_click);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PopularMoviesFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_click = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {

                case R.id.populares:
                    fragment = new PopularMoviesFragment();
                    break;

                case R.id.best_classificados:
                    fragment = new TopRatedMoviesFragment();
                    break;

                case R.id.upcoming:
                    fragment = new UpComingMoviesFragment();
                    break;
                case R.id.favorites:
                    fragment = new FavoriteMovieFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_superior, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.btnLogout:
                logout();
                break;

            case R.id.update_profile:
                startActivity(new Intent(MainActivity.this, EditProfile.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        session.setLoggedin(false);
        SharedPreferences prefs = MainActivity.this.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }

}