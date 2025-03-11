package edu.uci.swe264p.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieListActivity extends AppCompatActivity {
    // Step 1: Add a RecyclerView widget
    private RecyclerView recyclerView;
    private List<Movie> movieList = new ArrayList<>();
    private MovieListAdapter adapter;

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "37847cc01c27783ff2fe7c5bc1d08bf8";
    private Retrofit retrofit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.rvMovieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieListAdapter(this, movieList);
        recyclerView.setAdapter(adapter);

        fetchTopRatedMovies();
    }


    private void fetchTopRatedMovies() {

        // initialize retrofit
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        // Create the API call
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        // Make the request
        Call<TopRatedResponse> call = movieApiService.getTopRatedMovies( API_KEY);
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body().getMovies());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Log.e("API_ERROR", "RESPONSE FAILED");
                    Toast.makeText(MovieListActivity.this, "Failed to get movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                Log.e("API_ERROR", "Network Error" + t.getMessage());
                Toast.makeText(MovieListActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
