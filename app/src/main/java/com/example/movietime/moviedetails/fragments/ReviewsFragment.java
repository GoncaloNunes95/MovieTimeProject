package com.example.movietime.moviedetails.fragments;

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
import com.example.movietime.adapters.ReviewsAdapter;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.data.mapper.ReviewsMapper;
import com.example.movietime.database.DBHelper;
import com.example.movietime.moviedetails.activity.TabbDetailsActivity;
import com.example.movietime.network.ApiService;
import com.example.movietime.network.response.ReviewsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment {

    private DBHelper db;
    private Session session;
    private String email, password, user;
    private ReviewsAdapter listReviewsAdapter;
    private RecyclerView listreviews;
    private TextView tv_not_show_items;
    private Filme filme;
    int id;

    public ReviewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        db = new DBHelper(getContext());

        user = (SingletonUser.singleton().fetchValueString("Username"));
        email = (SingletonUser.singleton().fetchValueString("Email"));
        password = (SingletonUser.singleton().fetchValueString("Password"));

        //filme = (Filme) getActivity().getIntent().getSerializableExtra("MOVIE_DETAILS");

        TabbDetailsActivity activity = (TabbDetailsActivity)getActivity();
        Bundle results = activity.getData();
        id = results.getInt("id");

        tv_not_show_items = (TextView) view.findViewById(R.id.not_show_items);

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
        ApiService.getInstance().Reviews(String.valueOf(id), chaveAPI).enqueue(new Callback<ReviewsResult>() {
            @Override
            public void onResponse(Call<ReviewsResult> call, Response<ReviewsResult> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResults().size() > 0) {
                        listReviewsAdapter.setReviews(ReviewsMapper.ResponseToDominio(response.body().getResults()));
                        tv_not_show_items.setVisibility(View.GONE);
                    } else {
                        tv_not_show_items.setVisibility(View.VISIBLE);
                    }
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