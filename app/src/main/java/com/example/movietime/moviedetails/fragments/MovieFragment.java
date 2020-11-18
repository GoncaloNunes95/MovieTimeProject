package com.example.movietime.moviedetails.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietime.database.DBHelper;
import com.example.movietime.R;
import com.example.movietime.autentication.Session;
import com.example.movietime.autentication.LoginActivity;
import com.example.movietime.data.Filme;
import com.squareup.picasso.Picasso;

public class MovieFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Filme filme;
    private ImageButton favorite;
    private DBHelper db;
    private Session session;
    private String user, email, password;

    private String mParam1;
    private String mParam2;

    public MovieFragment() {

    }

    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        db = new DBHelper(getContext());

        SharedPreferences prefs = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        String nomeArmazenado = prefs.getString("Username", null);
        String EmailArmazenado = prefs.getString("Email", null);
        String PasswordArmazenado = prefs.getString("Password", null);

        if ((nomeArmazenado != null) && (EmailArmazenado != null) && (PasswordArmazenado != null)) {
            user = prefs.getString("Username", "");
            email = prefs.getString("Email", "");
            password = prefs.getString("Password", "");
        }

        filme = (Filme) getActivity().getIntent().getSerializableExtra("MOVIE_DETAILS");

        favorite = (ImageButton) view.findViewById(R.id.favorite_btn);
        TextView movie_title = (TextView) view.findViewById(R.id.title_movie);
        ImageView movie_poster = (ImageView) view.findViewById(R.id.mini_poster);
        TextView release_date_movie = (TextView) view.findViewById(R.id.date);
        TextView vote_average = (TextView) view.findViewById(R.id.vote_average);
        TextView sinopse = (TextView) view.findViewById(R.id.sinopse);

        movie_title.setText(filme.getOriginal_title());
        release_date_movie.setText(filme.getRelease_date());
        vote_average.setText(Float.toString(filme.getVote_average()));
        sinopse.setText(filme.getSinopse());
        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + filme.getPoster_path()).into(movie_poster);

        StatusFavorite();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = db.ValidarExistenciaFavorito(user, filme.getId());

                if (result.equals("1")) {
                    db.RemoveFavorito(user, filme.getId());
                    Toast.makeText(getContext(), R.string.removed_favorites, Toast.LENGTH_LONG).show();
                    favorite.setBackgroundResource(R.drawable.favorite_not_check);
                } else {
                    long res = db.CriarFavorito(user, filme.getId(), filme.getOriginal_title(), filme.getPoster_path(), filme.getRelease_date(), String.valueOf(filme.getVote_average()), filme.getSinopse());
                    if (res > 0) {
                        Toast.makeText(getContext(), R.string.add_favorites, Toast.LENGTH_LONG).show();
                        favorite.setBackgroundResource(R.drawable.favorite_check);
                    } else {
                        Toast.makeText(getContext(), R.string.error_add_favorites, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    private void StatusFavorite() {

        String result = db.ValidarExistenciaFavorito(user, filme.getId());

        if (result.equals("1"))
            favorite.setBackgroundResource(R.drawable.favorite_check);
        else
            favorite.setBackgroundResource(R.drawable.favorite_not_check);

    }

    private void logout() {

        session.setLoggedin(false);
        SharedPreferences prefs = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        getActivity().finish();
        startActivity(new Intent(getContext(), LoginActivity.class));

    }
}