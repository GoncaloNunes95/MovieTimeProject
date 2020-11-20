package com.example.movietime.main.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.BottomNavigationBehavior;
import com.example.movietime.R;
import com.example.movietime.SingletonUser;
import com.example.movietime.adapters.FilmesAdapter;
import com.example.movietime.autentication.EditProfile;
import com.example.movietime.autentication.LoginActivity;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.database.DBHelper;
import com.example.movietime.main.fragments.FavoriteMovieFragment;
import com.example.movietime.main.fragments.PopularMoviesFragment;
import com.example.movietime.main.fragments.SearchFragment;
import com.example.movietime.main.fragments.TopRatedMoviesFragment;
import com.example.movietime.main.fragments.UpComingMoviesFragment;
import com.example.movietime.moviedetails.activity.TabbDetailsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FilmesAdapter.ItemMovieClickListener {

    private BottomNavigationView bottomNavigationView;
    private DBHelper db;
    private Session session;
    private int flag;

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
            session.setLoggedin(false);
            SingletonUser.singleton().logout();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
                    flag = 0;
                    break;

                case R.id.best_classificados:
                    fragment = new TopRatedMoviesFragment();
                    flag = 1;
                    break;

                case R.id.upcoming:
                    fragment = new UpComingMoviesFragment();
                    flag = 2;
                    break;
                case R.id.favorites:
                    fragment = new FavoriteMovieFragment();
                    flag = 3;
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_superior, menu);

        MenuItem menuItem = menu.findItem(R.id.search_btn);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Fragment fragment = new SearchFragment(query);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        int searchCloseButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        searchView.findViewById(searchCloseButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                if (flag == 0)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new PopularMoviesFragment()).commit();
                if (flag == 1)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new TopRatedMoviesFragment()).commit();
                if (flag == 2)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new UpComingMoviesFragment()).commit();
                if (flag == 3)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteMovieFragment()).commit();
            }
        });
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.btnLogout:
                session.setLoggedin(false);
                SingletonUser.singleton().logout();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;

            case R.id.update_profile:
                startActivity(new Intent(MainActivity.this, EditProfile.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemMovieClicked(Filme filme) {

        Intent goToDetails = new Intent(this, TabbDetailsActivity.class);
        goToDetails.putExtra("MOVIE_DETAILS", filme);
        startActivity(goToDetails);

    }
}