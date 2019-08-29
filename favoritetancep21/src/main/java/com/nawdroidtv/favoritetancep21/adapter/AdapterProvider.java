package com.nawdroidtv.favoritetancep21.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nawdroidtv.favoritetancep21.BuildConfig;
import com.nawdroidtv.favoritetancep21.DetailProviderActivity;
import com.nawdroidtv.favoritetancep21.R;

import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_DATE;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_HEAD;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_LANG;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_NAME;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_PLOT;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_POST;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_RATI;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_VOTE;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.getColumnString;

/**
 * Created by Irfan Nawawi on 02/02/2019.
 */

public class AdapterProvider extends CursorAdapter {

    public AdapterProvider(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_favorite, parent, false);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvOverview = view.findViewById(R.id.tvOverview);
            ImageView imgMovie = view.findViewById(R.id.imgMovie);

            final String poster = BuildConfig.BASE_URL_IMG + "w185" + getColumnString(cursor, MOVIE_POST);
            Glide.with(context)
                    .load(poster)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                    .into(imgMovie);
            tvTitle.setText(getColumnString(cursor, MOVIE_NAME));
            tvOverview.setText(getColumnString(cursor, MOVIE_PLOT));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Favorite check", Toast.LENGTH_SHORT).show();
                    Intent data = new Intent(context, DetailProviderActivity.class);
                    data.putExtra("movie_header", getColumnString(cursor, MOVIE_HEAD));
                    data.putExtra("movie_thumbnail", getColumnString(cursor, MOVIE_POST));
                    data.putExtra("movie_title", getColumnString(cursor, MOVIE_NAME));
                    data.putExtra("movie_overview", getColumnString(cursor, MOVIE_PLOT));
                    data.putExtra("movie_title", getColumnString(cursor, MOVIE_NAME));
                    data.putExtra("movie_rating", getColumnString(cursor, MOVIE_RATI));
                    data.putExtra("movie_date", getColumnString(cursor, MOVIE_DATE));
                    data.putExtra("movie_vote", getColumnString(cursor, MOVIE_VOTE));
                    data.putExtra("movie_language", getColumnString(cursor, MOVIE_LANG));
                    context.startActivity(data);
                }
            });

        }
    }
}
