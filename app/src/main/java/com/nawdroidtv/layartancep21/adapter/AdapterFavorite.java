package com.nawdroidtv.layartancep21.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.nawdroidtv.layartancep21.model.Favorite;
import com.nawdroidtv.layartancep21.view.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder> {

    private Context context;
    private Cursor movieList;

    public AdapterFavorite(Context context) {
        this.context = context;
    }

    public Cursor getMovieList() {
        return movieList;
    }

    public void setMovieList(Cursor movieList) {
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        final Favorite favorite = getItem(i);
        final String poster = BuildConfig.BASE_URL_IMG + "w500" + favorite.getPosterPath();
        Glide.with(context)
                .load(poster)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(myViewHolder.imgMovie);
        myViewHolder.tvTitle.setText(favorite.getOriginalTitle());
        myViewHolder.tvDesc.setText(favorite.getOverview());

        final Bundle masterMovie = new Bundle();
        masterMovie.putString("movie_header", favorite.getBackdropPath());
        masterMovie.putString("movie_thumbnail", favorite.getPosterPath());
        masterMovie.putString("movie_title", favorite.getOriginalTitle());
        masterMovie.putString("movie_overview", favorite.getOverview());
        masterMovie.putString("movie_rating", Double.toString(favorite.getVoteAverage()));
        masterMovie.putString("movie_date", favorite.getReleaseDate());
        masterMovie.putString("movie_vote", Integer.toString(favorite.getVoteCount()));
        masterMovie.putString("movie_language", favorite.getOriginalLanguage());

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
        if (movieList == null) return 0;
        return movieList.getCount();
    }

    private Favorite getItem(int i) {
        if (!movieList.moveToPosition(i)) {
            throw new IllegalStateException("Movie Invalid");
        }
        return new Favorite(movieList);
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
