package com.nawdroidtv.layartancep21.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nawdroidtv.layartancep21.BuildConfig;
import com.nawdroidtv.layartancep21.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.CONTENT_URI;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_DATE;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_HEAD;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_LANG;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_NAME;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_PLOT;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_POST;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_RATI;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_VOTE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    String thumbnailHeader, thumbnail, movieName, movieVote, language, synopsis, rating, dateOfRelease;
    @BindView(R.id.title)
    TextView textToolbarTitle;
    @BindView(R.id.title_movie)
    TextView nameOfMovie;
    @BindView(R.id.vote_movie)
    TextView voteMovie;
    @BindView(R.id.language_movie)
    TextView languageMovie;
    @BindView(R.id.overview_movie)
    TextView plotSynopsis;
    @BindView(R.id.rating_movie)
    TextView userRating;
    @BindView(R.id.release_movie)
    TextView releaseDate;
    @BindView(R.id.thumbnail_image_header)
    ImageView imageViewHeader;
    @BindView(R.id.thumbnail_image)
    ImageView imageViewPoster;
    @BindView(R.id.img_favorit)
    ImageView imageViewFavorite;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private long no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        initCollapsingToolbar();
        showMovie();
        showFavorite();
    }

    private void showMovie() {
        thumbnailHeader = getIntent().getStringExtra("movie_header");
        thumbnail = getIntent().getStringExtra("movie_thumbnail");
        movieName = getIntent().getStringExtra("movie_title");
        movieVote = getIntent().getStringExtra("movie_vote");
        language = getIntent().getStringExtra("movie_language");
        synopsis = getIntent().getStringExtra("movie_overview");
        rating = getIntent().getStringExtra("movie_rating");
        dateOfRelease = getIntent().getStringExtra("movie_date");

        nameOfMovie.setText(movieName);
        voteMovie.setText(movieVote);
        languageMovie.setText(language);
        plotSynopsis.setText(synopsis);
        userRating.setText(rating);
        releaseDate.setText(dateOfRelease);

        String poster = BuildConfig.BASE_URL_IMG + "w500" + thumbnail;
        Glide.with(this)
                .load(poster)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(imageViewPoster);

        String header = BuildConfig.BASE_URL_IMG + "original" + thumbnailHeader;
        Glide.with(this)
                .load(header)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(imageViewHeader);
    }

    private boolean showFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String name;
        if (cursor.moveToFirst()) {
            do {
                no = cursor.getLong(0);
                name = cursor.getString(1);
                if (name.equals(getIntent().getStringExtra("movie_title"))) {
                    imageViewFavorite.setImageResource(R.drawable.ic_love_filled);
                    favorite = true;
                }
            } while (cursor.moveToNext());
        }
        return favorite;
    }

    public void initCollapsingToolbar() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    textToolbarTitle.setText(getString(R.string.app_name));
                    imageViewPoster.setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    textToolbarTitle.setText("");
                    imageViewPoster.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.img_favorit)
    public void onClick(View view) {
        if (showFavorite()) {
            deleteFavorite();
            imageViewFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_empty));
            Snackbar.make(view, R.string.action_remove,
                    Snackbar.LENGTH_SHORT).show();
        } else {
            saveFavorite();
            imageViewFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_filled));
            Snackbar.make(view, R.string.action_favorite,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    private void saveFavorite() {
        ContentValues values = new ContentValues();
        values.put(MOVIE_NAME, movieName);
        values.put(MOVIE_PLOT, synopsis);
        values.put(MOVIE_DATE, dateOfRelease);
        values.put(MOVIE_HEAD, thumbnailHeader);
        values.put(MOVIE_LANG, language);
        values.put(MOVIE_POST, thumbnail);
        values.put(MOVIE_RATI, rating);
        values.put(MOVIE_VOTE, movieVote);

        getContentResolver().insert(CONTENT_URI, values);
        setResult(101);
    }

    private void deleteFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "/" + no);
        getContentResolver().delete(uri, null, null);
    }
}
