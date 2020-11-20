package com.example.movietime.moviedetails.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.movietime.R;
import com.example.movietime.SingletonUser;
import com.example.movietime.autentication.Session;
import com.example.movietime.data.Filme;
import com.example.movietime.database.DBHelper;
import com.example.movietime.moviedetails.activity.TabbDetailsActivity;
import com.squareup.picasso.Picasso;

public class MovieFragment extends Fragment {

    private Filme filme;
    private ImageButton favorite;
    private DBHelper db;
    private String user;
    int id;
    Float average;
    String title, poster, date, overview;


    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        db = new DBHelper(getContext());

        user = (SingletonUser.singleton().fetchValueString("Username"));

        //filme = (Filme) getActivity().getIntent().getSerializableExtra("MOVIE_DETAILS");

        TabbDetailsActivity activity = (TabbDetailsActivity)getActivity();
        Bundle results = activity.getData();
        id = results.getInt("id");
        title = results.getString("title");
        poster = results.getString("poster");
        date = results.getString("date");
        average = results.getFloat("average");
        overview = results.getString("sinopse");

        favorite = (ImageButton) view.findViewById(R.id.favorite_btn);
        TextView movie_title = (TextView) view.findViewById(R.id.title_movie);
        ImageView movie_poster = (ImageView) view.findViewById(R.id.mini_poster);
        TextView release_date_movie = (TextView) view.findViewById(R.id.date);
        TextView vote_average = (TextView) view.findViewById(R.id.vote_average);
        TextView sinopse = (TextView) view.findViewById(R.id.sinopse);

//        movie_title.setText(filme.getOriginal_title());
//        release_date_movie.setText(filme.getRelease_date());
//        vote_average.setText(Float.toString(filme.getVote_average()));
//        sinopse.setText(filme.getSinopse());
//        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + filme.getPoster_path()).into(movie_poster);

        movie_title.setText(title);
        release_date_movie.setText(date);
        vote_average.setText(Float.toString(average));
        sinopse.setText(overview);
        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + poster).into(movie_poster);

        StatusFavorite();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = db.ValidarExistenciaFavorito(user, id);

                if (result.equals("1")) {
                    db.RemoveFavorito(user, id);
                    Toast.makeText(getContext(), R.string.removed_favorites, Toast.LENGTH_LONG).show();
                    favorite.setBackgroundResource(R.drawable.favorite_not_check);
                } else {
                    try {
                        long res = db.CriarFavorito(user, id, title, poster, date, String.valueOf(average), overview);
                        if (res > 0) {
                            Toast.makeText(getContext(), R.string.add_favorites, Toast.LENGTH_LONG).show();
                            favorite.setBackgroundResource(R.drawable.favorite_check);
                        } else {
                            Toast.makeText(getContext(), R.string.error_add_favorites, Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), R.string.error_add_favorite, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    private void StatusFavorite() {

        String result = db.ValidarExistenciaFavorito(user, id);

        if (result.equals("1"))
            favorite.setBackgroundResource(R.drawable.favorite_check);
        else
            favorite.setBackgroundResource(R.drawable.favorite_not_check);

    }
}