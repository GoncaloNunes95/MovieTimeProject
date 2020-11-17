package com.example.movietime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.data.Reviews;

import java.util.ArrayList;
import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Reviews> reviews;

    public ReviewsAdapter() {
        reviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return (reviews != null && reviews.size() > 0) ? reviews.size() : 0;
    }

    static class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private TextView reviewFilme;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewFilme = itemView.findViewById(R.id.review_filme);
        }

        public void bind(Reviews reviews) {

            reviewFilme.setText(reviews.getContent());

        }
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
