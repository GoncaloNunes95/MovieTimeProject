package com.example.movietime.moviedetails.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.BuildConfig;
import com.example.movietime.R;
import com.example.movietime.SingletonUser;
import com.example.movietime.adapters.TrailersAdapter;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.data.Trailers;
import com.example.movietime.data.mapper.TrailersMapper;
import com.example.movietime.database.DBHelper;
import com.example.movietime.network.ApiService;
import com.example.movietime.network.response.TrailersResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersFragment extends Fragment implements TrailersAdapter.ItemClickListener {

    private DBHelper db;
    private Session session;
    private String email, password, user;
    private RecyclerView listtrailers;
    private TrailersAdapter listTrailerAdapter;
    private Filme filme;
    public List<Trailers> trailers;
    private TextView tv_not_show_items;

    public TrailersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);
        db = new DBHelper(getContext());

        user = (SingletonUser.singleton().fetchValueString("Username"));
        email = (SingletonUser.singleton().fetchValueString("Email"));
        password = (SingletonUser.singleton().fetchValueString("Password"));

        filme = (Filme) getActivity().getIntent().getSerializableExtra("MOVIE_DETAILS");

        tv_not_show_items = (TextView) view.findViewById(R.id.not_show_items);

        listtrailers = (RecyclerView) view.findViewById(R.id.recyclerview_trailers);
        configuracaTrailersAdapter();
        loadTrailer();

        return view;
    }

    private void configuracaTrailersAdapter() {
        listtrailers.setHasFixedSize(true);

        listTrailerAdapter = new TrailersAdapter();

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        listtrailers.setLayoutManager(gridLayoutManager);
        listTrailerAdapter.setClickListener(TrailersFragment.this);
        listtrailers.setAdapter(listTrailerAdapter);
    }

    private void loadTrailer() {

        String chaveAPI = BuildConfig.chaveAPI;
        ApiService.getInstance().Trailers(String.valueOf(filme.getId()), chaveAPI).enqueue(new Callback<TrailersResult>() {
            @Override
            public void onResponse(Call<TrailersResult> call, Response<TrailersResult> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResults().size() > 0) {
                        trailers = TrailersMapper.ResponseToDominio(response.body().getResults());
                        listTrailerAdapter.setTrailers(trailers);
                        tv_not_show_items.setVisibility(View.GONE);
                    } else {
                        tv_not_show_items.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), R.string.error_obtain_lists, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TrailersResult> call, Throwable t) {
                Toast.makeText(getContext(), R.string.failure_obtain_lists, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent goToYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + trailers.get(position).getKey()));
        startActivity(goToYoutube);
    }

}