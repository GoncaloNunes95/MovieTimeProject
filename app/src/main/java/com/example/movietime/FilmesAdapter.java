package com.example.movietime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.data.Filme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.FilmesViewHolder> {

    private List<Filme> filmes;
    private static ItemMovieClickListener itemMovieClickListener;

    public FilmesAdapter(ItemMovieClickListener itemMovieClickListener) {
        FilmesAdapter.itemMovieClickListener = itemMovieClickListener;
        filmes = new ArrayList<>();
    }

    @NonNull
    @Override
    public FilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filmes, parent, false);
        return new FilmesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmesViewHolder holder, int position) {
        holder.bind(filmes.get(position));
    }

    @Override
    public int getItemCount() {
        return (filmes != null && filmes.size() > 0) ? filmes.size() : 0;
    }

    static class FilmesViewHolder extends RecyclerView.ViewHolder {

        private final ImageView poster_filme;
        private Filme filme;

        public FilmesViewHolder(@NonNull View itemView) {
            super(itemView);

            poster_filme = itemView.findViewById(R.id.image_filme);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemMovieClickListener != null) {

                        itemMovieClickListener.onItemMovieClicked(filme);

                    }
                }
            });

        }

        public void bind(Filme filme) {

            this.filme = filme;
            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + filme.getPoster_path()).into(poster_filme);

        }
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
        notifyDataSetChanged();
    }

    public interface ItemMovieClickListener {
        void onItemMovieClicked(Filme filme);
    }
}
