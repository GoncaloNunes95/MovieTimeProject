package com.example.movietime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.data.Filme;
import com.example.movietime.data.mapper.FilmeMapper;
import com.example.movietime.network.ApiService;
import com.example.movietime.network.response.FilmesResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesFragment extends Fragment implements FilmesAdapter.ItemMovieClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView listdados;
    private FilmesAdapter listaFilmesAdapter;
    View v;

    private String mParam1;
    private String mParam2;

    public PopularMoviesFragment() {
    }

    public static PopularMoviesFragment newInstance(String param1, String param2) {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        listdados = (RecyclerView) v.findViewById(R.id.recyclerView_popular_movies);
        configuracaoAdapter();
        lista_de_filmes();
        return v;

    }

    private void configuracaoAdapter() {

        listdados.setHasFixedSize(true);

        listaFilmesAdapter = new FilmesAdapter(this);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listdados.setLayoutManager(gridLayoutManager);
        listdados.setAdapter(listaFilmesAdapter);

    }

    private void lista_de_filmes() {

        String chaveAPI = BuildConfig.chaveAPI;
        ApiService.getInstance().FilmesPopulares(chaveAPI).enqueue(new Callback<FilmesResult>() {
            @Override
            public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {

                if (response.isSuccessful()) {
                    listaFilmesAdapter.setFilmes(FilmeMapper.ResponseToDominio(response.body().getResults()));
                } else {
                    Toast.makeText(getActivity(), R.string.error_obtain_lists, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FilmesResult> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.failure_obtain_lists, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemMovieClicked(Filme filme) {

        Intent goToDetails = new Intent(getContext(), TabbDetailsActivity.class);
        goToDetails.putExtra("MOVIE_DETAILS", filme);
        startActivity(goToDetails);

    }
}