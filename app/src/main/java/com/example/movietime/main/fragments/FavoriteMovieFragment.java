package com.example.movietime.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.database.DBHelper;
import com.example.movietime.adapters.FilmesAdapter;
import com.example.movietime.R;
import com.example.movietime.moviedetails.activity.TabbDetailsActivity;
import com.example.movietime.data.Filme;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieFragment extends Fragment implements FilmesAdapter.ItemMovieClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView listdados;
    View v;
    private DBHelper db;
    private String email, password, user;
    private List<Filme> filmes;
    private FilmesAdapter listaFilmesAdapter;

    public FavoriteMovieFragment() {
    }

    public static FavoriteMovieFragment newInstance(String param1, String param2) {
        FavoriteMovieFragment fragment = new FavoriteMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        listdados = (RecyclerView) v.findViewById(R.id.recyclerView_favorite_movies);

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

        String result = db.SelectFavoritosUser(user);

        if (result.equals("1")) {
            Cursor cursor = configuracaoAdapter();
            listaFilmesAdapter.setFilmes(lista_de_filmes(cursor));
            return v;
        }
        return null;
    }

    private Cursor configuracaoAdapter() {

        Cursor c = db.mostrarFavoritos(user);

        listdados.setHasFixedSize(true);

        listaFilmesAdapter = new FilmesAdapter(this);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listdados.setLayoutManager(gridLayoutManager);
        listdados.setAdapter(listaFilmesAdapter);

        return c;

    }

    private List<Filme> lista_de_filmes(Cursor c) {

        List<Filme> filmes = new ArrayList<>();

        do{
            Filme filme = new Filme(c.getInt(1),
                    c.getString(3),
                    c.getString(2),
                    c.getString(4),
                    c.getFloat(5),
                    c.getString(6));

            filmes.add(filme);
        }while (c.moveToNext());
        return filmes;
    }


    @Override
    public void onItemMovieClicked(Filme filme) {

        Intent goToDetails = new Intent(getContext(), TabbDetailsActivity.class);
        goToDetails.putExtra("MOVIE_DETAILS", filme);
        startActivityForResult(goToDetails, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            String resultado = db.SelectFavoritosUser(user);

            if (resultado.equals("1")) {
                Cursor c = configuracaoAdapter();
                listaFilmesAdapter.setFilmes(lista_de_filmes(c));
            }
            else {
                listaFilmesAdapter.setFilmes(new ArrayList<>());
            }
        }
    }
}