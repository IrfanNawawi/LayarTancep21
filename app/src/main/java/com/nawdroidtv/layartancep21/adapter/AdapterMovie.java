package com.nawdroidtv.layartancep21.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nawdroidtv.layartancep21.BuildConfig;
import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.model.Movie;
import com.nawdroidtv.layartancep21.view.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public AdapterMovie(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        final String poster = BuildConfig.BASE_URL_IMG + "w500" + movieList.get(i).getPosterPath();
        Glide.with(context)
                .load(poster)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(myViewHolder.imgMovie);
        myViewHolder.tvTitle.setText(movieList.get(i).getOriginalTitle());
        myViewHolder.tvDesc.setText(movieList.get(i).getOverview());

        final Bundle masterMovie = new Bundle();
        masterMovie.putString("movie_header", movieList.get(i).getBackdropPath());
        masterMovie.putString("movie_thumbnail", movieList.get(i).getPosterPath());
        masterMovie.putString("movie_title", movieList.get(i).getOriginalTitle());
        masterMovie.putString("movie_overview", movieList.get(i).getOverview());
        masterMovie.putString("movie_rating", Double.toString(movieList.get(i).getVoteAverage()));
        masterMovie.putString("movie_date", movieList.get(i).getReleaseDate());
        masterMovie.putString("movie_vote", Integer.toString(movieList.get(i).getVoteCount()));
        masterMovie.putString("movie_language", movieList.get(i).getOriginalLanguage());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent varIntent = new Intent(context, DetailActivity.class);
                varIntent.putExtras(masterMovie);
                varIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(varIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgMovie)
        ImageView imgMovie;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvOverview)
        TextView tvDesc;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
