package com.example.movietime.moviedetails.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.movietime.adapters.ReviewsAdapter;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.data.mapper.ReviewsMapper;
import com.example.movietime.network.ApiService;
import com.example.movietime.network.response.ReviewsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DBHelper db;
    private Session session;
    private String email, password, user;
    private ReviewsAdapter listReviewsAdapter;
    private RecyclerView listreviews;
    private Filme filme;

    private String mParam1;
    private String mParam2;

    public ReviewsFragment() {
    }

    public static ReviewsFragment newInstance(String param1, String param2) {
        ReviewsFragment fragment = new ReviewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
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

        listreviews = (RecyclerView) view.findViewById(R.id.recyclerview_reviews);
        configuracaReviewsoAdapter();
        lista_de_reviews();

        return view;
    }

    private void configuracaReviewsoAdapter() {

        listreviews.setHasFixedSize(true);

        listReviewsAdapter = new ReviewsAdapter();

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        listreviews.setLayoutManager(gridLayoutManager);
        listreviews.setAdapter(listReviewsAdapter);

    }

    private void lista_de_reviews() {

        String chaveAPI = BuildConfig.chaveAPI;
        ApiService.getInstance().Reviews(String.valueOf(filme.getId()), chaveAPI).enqueue(new Callback<ReviewsResult>() {
            @Override
            public void onResponse(Call<ReviewsResult> call, Response<ReviewsResult> response) {

                if (response.isSuccessful()) {
                    listReviewsAdapter.setReviews(ReviewsMapper.ResponseToDominio(response.body().getResults()));
                } else {
                    Toast.makeText(getContext(), R.string.error_obtain_lists, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewsResult> call, Throwable t) {
                Toast.makeText(getContext(), R.string.failure_obtain_lists, Toast.LENGTH_LONG).show();
            }
        });
    }
}