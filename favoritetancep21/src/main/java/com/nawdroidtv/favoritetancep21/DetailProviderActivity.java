package com.nawdroidtv.favoritetancep21;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProviderActivity extends AppCompatActivity {

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
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbarPro)
    Toolbar toolbar;
    private long no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_provider);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        initCollapsingToolbar();
        showMovie();
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
}