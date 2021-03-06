package com.example.movietime.main.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.BuildConfig;
import com.example.movietime.R;
import com.example.movietime.adapters.FilmesAdapter;
import com.example.movietime.data.Filme;
import com.example.movietime.data.mapper.FilmeMapper;
import com.example.movietime.moviedetails.activity.TabbDetailsActivity;
import com.example.movietime.network.ApiService;
import com.example.movietime.network.response.FilmesResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements FilmesAdapter.ItemMovieClickListener {

    private RecyclerView listdados;
    private FilmesAdapter listaFilmesAdapter;
    private String query;

    public SearchFragment(String query) {
        this.query = query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        listdados = (RecyclerView) v.findViewById(R.id.recyclerView_search);

        configuracaoAdapter();
        lista_de_filmes(query);
        return v;
    }

    private void configuracaoAdapter() {

        listdados.setHasFixedSize(true);

        listaFilmesAdapter = new FilmesAdapter(this);

        RecyclerView.LayoutManager gridLayoutManager;
        int orientaion = getResources().getConfiguration().orientation;

        if (orientaion == Configuration.ORIENTATION_LANDSCAPE){
            gridLayoutManager = new GridLayoutManager(getContext(), 4);
        }
        else {
            gridLayoutManager = new GridLayoutManager(getContext(), 2);
        }

        listdados.setLayoutManager(gridLayoutManager);
        listdados.setAdapter(listaFilmesAdapter);

    }

    private void lista_de_filmes(String search) {

        String chaveAPI = BuildConfig.chaveAPI;
        ApiService.getInstance().SearchMovies(search, chaveAPI).enqueue(new Callback<FilmesResult>() {
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