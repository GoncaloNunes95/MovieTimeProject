package com.example.movietime.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class PopularMoviesFragment extends Fragment implements FilmesAdapter.ItemMovieClickListener {

    private RecyclerView listdados;
    private FilmesAdapter listaFilmesAdapter;
    View v;
    int i = 1;

    public PopularMoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        listdados = (RecyclerView) v.findViewById(R.id.recyclerView_popular_movies);
        configuracaoAdapter();
        lista_de_filmes(1);
        getMorePopularMovies();
        return v;

    }

    private void configuracaoAdapter() {

        listdados.setHasFixedSize(true);

        listaFilmesAdapter = new FilmesAdapter(this);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listdados.setLayoutManager(gridLayoutManager);
        listdados.setAdapter(listaFilmesAdapter);

    }

    private void lista_de_filmes(int i) {

        String chaveAPI = BuildConfig.chaveAPI;
        ApiService.getInstance().FilmesPopulares(i, chaveAPI).enqueue(new Callback<FilmesResult>() {
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

    private void getMorePopularMovies() {
        i = 1;
        listdados.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!listdados.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (i == 1000)
                        return;
                    else
                        i++;
                    listdados.scrollToPosition(0);
                    lista_de_filmes(i);
                } else if (!listdados.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (i == 1)
                        return;
                    else
                        i--;
                    listdados.scrollToPosition(listaFilmesAdapter.getItemCount() - 1);
                    lista_de_filmes(i);
                }
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