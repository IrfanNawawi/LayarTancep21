package com.nawdroidtv.layartancep21.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
public class SearchFragment extends Fragment {

    @BindView(R.id.rvSearch)
    RecyclerView recyclerView;

    @BindView(R.id.searchMain)
    SearchView searchView;

    @BindView(R.id.shimmer_recycler_view)
    ShimmerRecyclerView shimmerRecyclerView;

    Unbinder unbinder;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Movie> movieList;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        unbinder = ButterKnife.bind(this, view);

        movieList = new ArrayList<>();
        initViews();


        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("sea_movie");
        } else {
            searchMovie("A");
        }

        searchEngine();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("sea_movie", movieList);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchView.setQueryHint("Search Movie");
        searchView.clearFocus();
        searchView.setIconified(false);
    }

    private void searchMovie(String key) {
        ApiService Service = ApiInterface.getRetrofit().create(ApiService.class);
        Call<MovieResponse> call = Service.getSearchMovie(BuildConfig.TMDB_API_KEY, BuildConfig.BASE_URL_LANG, key);
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

    private void searchEngine() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        shimmerRecyclerView.showShimmerAdapter();

        searchMovie("A");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }
}
