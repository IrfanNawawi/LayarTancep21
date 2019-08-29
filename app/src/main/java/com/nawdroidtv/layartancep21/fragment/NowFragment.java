package com.nawdroidtv.layartancep21.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nawdroidtv.layartancep21.BuildConfig;
import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.adapter.AdapterMovie;
import com.nawdroidtv.layartancep21.api.ApiInterface;
import com.nawdroidtv.layartancep21.api.ApiService;
import com.nawdroidtv.layartancep21.model.Movie;
import com.nawdroidtv.layartancep21.model.MovieResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowFragment extends Fragment {

    @BindView(R.id.rvNowPlaying)
    RecyclerView recyclerView;

    @BindView(R.id.shimmer_recycler_view)
    ShimmerRecyclerView shimmerRecyclerView;

    Unbinder unbinder;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Movie> movieList;

    public NowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now, container, false);

        unbinder = ButterKnife.bind(this, view);
        movieList = new ArrayList<>();
        initViews();

        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("now_movie");
        } else {
            nowMovie();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("now_movie", movieList);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void nowMovie() {
        ApiService Service = ApiInterface.getRetrofit().create(ApiService.class);
        Call<MovieResponse> call = Service.getNowPlayingMovie(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                shimmerRecyclerView.hideShimmerAdapter();
                MovieResponse MovieResponse = response.body();
                movieList = MovieResponse.getResults();
                AdapterMovie adapter = new AdapterMovie(getContext(), movieList);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                shimmerRecyclerView.hideShimmerAdapter();
                Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        shimmerRecyclerView.showShimmerAdapter();

        nowMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }
}
