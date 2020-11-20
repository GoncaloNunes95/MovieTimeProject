package com.example.movietime.main.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.R;
import com.example.movietime.SingletonUser;
import com.example.movietime.adapters.FilmesAdapter;
import com.example.movietime.data.Filme;
import com.example.movietime.database.DBHelper;
import com.example.movietime.moviedetails.activity.TabbDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieFragment extends Fragment implements FilmesAdapter.ItemMovieClickListener {

    private RecyclerView listdados;
    View v;
    private DBHelper db;
    private String user;
    private List<Filme> filmes;
    private FilmesAdapter listaFilmesAdapter;

    public FavoriteMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        listdados = (RecyclerView) v.findViewById(R.id.recyclerView_favorite_movies);

        db = new DBHelper(getContext());

        user = (SingletonUser.singleton().fetchValueString("Username"));

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

        do {
            Filme filme = new Filme(c.getInt(1),
                    c.getString(3),
                    c.getString(2),
                    c.getString(4),
                    c.getFloat(5),
                    c.getString(6));

            filmes.add(filme);
        } while (c.moveToNext());
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
            } else {
                listaFilmesAdapter.setFilmes(new ArrayList<>());
            }
        }
    }
}