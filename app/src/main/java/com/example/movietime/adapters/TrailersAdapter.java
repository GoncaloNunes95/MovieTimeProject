package com.example.movietime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.R;
import com.example.movietime.data.Trailers;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private List<Trailers> trailers;
    private static ItemClickListener itemClickListener;

    public TrailersAdapter() {
        trailers = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrailersAdapter.TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.TrailersViewHolder holder, int position) {
        holder.bind(trailers.get(position));
    }

    @Override
    public int getItemCount() {
        return (trailers != null && trailers.size() > 0) ? trailers.size() : 0;
    }

    static class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView trailerFilme;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);

            trailerFilme = itemView.findViewById(R.id.textviewTrailers);
            trailerFilme.setOnClickListener(this);
        }

        public void bind(Trailers trailers) {

            trailerFilme.setText(trailers.getName());

        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setTrailers(List<Trailers> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }
}
