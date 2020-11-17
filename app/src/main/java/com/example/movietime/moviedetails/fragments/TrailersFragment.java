package com.example.movietime.moviedetails.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movietime.BuildConfig;
import com.example.movietime.database.DBHelper;
import com.example.movietime.R;
import com.example.movietime.autentication.Session;
import com.example.movietime.adapters.TrailersAdapter;
import com.example.movietime.data.Filme;
import com.example.movietime.data.Trailers;
import com.example.movietime.data.mapper.TrailersMapper;
import com.example.movietime.network.ApiService;
import com.example.movietime.network.response.TrailersResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersFragment extends Fragment implements TrailersAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DBHelper db;
    private Session session;
    private String email, password, user;
    private RecyclerView listtrailers;
    private TrailersAdapter listTrailerAdapter;
    private Filme filme;
    public List<Trailers> trailers;

    private String mParam1;
    private String mParam2;

    public TrailersFragment() {
    }

    public static TrailersFragment newInstance(String param1, String param2) {
        TrailersFragment fragment = new TrailersFragment();
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
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);
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
                    trailers = TrailersMapper.ResponseToDominio(response.body().getResults());
                    listTrailerAdapter.setTrailers(trailers);
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